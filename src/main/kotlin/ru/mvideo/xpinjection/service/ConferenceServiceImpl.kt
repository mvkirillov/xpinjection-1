package ru.mvideo.xpinjection.service

import org.springframework.stereotype.Service
import ru.mvideo.xpinjection.adaptors.dto.Author
import ru.mvideo.xpinjection.adaptors.dto.Conference
import ru.mvideo.xpinjection.adaptors.dto.Talk
import ru.mvideo.xpinjection.adaptors.repository.ConferenceRepository
import ru.mvideo.xpinjection.adaptors.repository.TalkRepository
import ru.mvideo.xpinjection.adaptors.repository.entity.AuthorEntity
import ru.mvideo.xpinjection.adaptors.repository.entity.ConferenceEntity
import ru.mvideo.xpinjection.adaptors.repository.entity.TalkEntity
import ru.mvideo.xpinjection.adaptors.repository.entity.TalkType
import ru.mvideo.xpinjection.exceptions.ConferenceAlreadyExistsException
import ru.mvideo.xpinjection.exceptions.ConferenceNotFoundException

@Service
class ConferenceServiceImpl(
    val conferenceRepository: ConferenceRepository,
    val talkRepository: TalkRepository
) : ConferenceService {
    override fun addConference(conference: Conference): Long {
        with(conference) {
            conferenceRepository.findByName(name).ifPresent {
                throw ConferenceAlreadyExistsException(name)
            }

            return conferenceRepository.save(
                ConferenceEntity(
                    name,
                    topic,
                    fromDate,
                    toDate,
                    numberParticipants
                )
            ).id
        }
    }

    override fun getAllConferences(): List<Conference> {
        return conferenceRepository.findAll().map {
            Conference(
                id = it.id,
                name = it.name,
                topic = it.topic,
                fromDate = it.fromDate,
                toDate = it.toDate,
                numberParticipants = it.numberParticipants
            )
        }
    }

    override fun updateConferenceById(conferenceId: Long, conference: Conference): Long {
        val entity = ConferenceEntity(
            conference.name,
            conference.topic,
            conference.fromDate,
            conference.toDate,
            conference.numberParticipants
        )
        entity.id = conferenceId
        return conferenceRepository.save(
            entity
        ).id
    }

    override fun addTalkToConference(conferenceId: Long, talk: Talk): Long {
        val conferenceEntity =
            conferenceRepository.findById(conferenceId)
                .orElseThrow { throw ConferenceNotFoundException("Conference with id $conferenceId not found") }
        val authorEntity = AuthorEntity(talk.author.name)
        authorEntity.id = talk.author.id
        val talkEntity = TalkEntity(
            name = talk.name,
            description = talk.description,
            authorEntity = authorEntity,
            type = TalkType.valueOf(talk.type)
        )
        talkEntity.conferenceEntities.add(conferenceEntity)
        talkRepository.save(talkEntity)
        return conferenceEntity.id
    }

    override fun getAllTalksForConference(conferenceId: Long): List<Talk> {
        return conferenceRepository.findById(conferenceId)
            .orElseThrow { throw ConferenceNotFoundException("Conference with id $conferenceId not found") }.talkEntities.toList()
            .map { Talk(it.name, it.description, Author(it.authorEntity.id, it.authorEntity.name), it.type.toString()) }
    }
}