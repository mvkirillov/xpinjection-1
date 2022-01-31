package ru.mvideo.xpinjection.service

import org.springframework.stereotype.Service
import ru.mvideo.xpinjection.adaptors.configuration.ConferenceProperties
import ru.mvideo.xpinjection.adaptors.dto.*
import ru.mvideo.xpinjection.adaptors.repository.AuthorRepository
import ru.mvideo.xpinjection.adaptors.repository.ConferenceRepository
import ru.mvideo.xpinjection.adaptors.repository.TalkRepository
import ru.mvideo.xpinjection.adaptors.repository.entity.AuthorEntity
import ru.mvideo.xpinjection.adaptors.repository.entity.ConferenceEntity
import ru.mvideo.xpinjection.adaptors.repository.entity.TalkEntity
import ru.mvideo.xpinjection.adaptors.repository.entity.TalkType
import ru.mvideo.xpinjection.exceptions.*
import java.time.LocalDate
import javax.transaction.Transactional

@Service
class ConferenceServiceImpl(
    val conferenceRepository: ConferenceRepository,
    val talkRepository: TalkRepository,
    val conferenceProperties: ConferenceProperties,
    val authorRepository: AuthorRepository
) : ConferenceService {

    override fun addConference(
        conference: Conference
    ): Long {
        with(conference) {
            conferenceRepository.findByName(name).ifPresent {
                throw ConferenceAlreadyExistsException(name)
            }
            // todo надо написать кастомный валидатор дат
            if (conference.fromDate >= conference.toDate) {
                throw ConferenceWrongSameDatesException()
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

    override fun getAllConferences(): List<ConferenceWithTalks> {
        return conferenceRepository.findAllWithTalks()
            .map {
                ConferenceWithTalks(
                    id = it.id,
                    name = it.name,
                    topic = it.topic,
                    fromDate = it.fromDate,
                    toDate = it.toDate,
                    numberParticipants = it.numberParticipants,
                    talks = it.talks.mapTo(hashSetOf()) { talkEntity ->
                        Talk(
                            name = talkEntity.name,
                            description = talkEntity.description,
                            author = Author(talkEntity.author.id, talkEntity.author.name),
                            type = talkEntity.type.name
                        )
                    }
                )
            }
    }

    @Transactional
    override fun updateConferenceById(
        conferenceId: Long,
        conference: UpdateConferenceDto
    ): Long {
        val fromDb = conferenceRepository.findByName(conference.name)
        if (fromDb.isEmpty) {
            throw ConferenceNotFoundException(conferenceId)
        }
        // todo надо написать кастомный валидатор дат
        if (conference.fromDate >= conference.toDate) {
            throw ConferenceWrongSameDatesException()
        }
        val conferenceFromDb = fromDb.get()

        with(conference) {
            conferenceFromDb.topic = this.topic
            conferenceFromDb.fromDate = this.fromDate
            conferenceFromDb.toDate = this.toDate
            conferenceFromDb.numberParticipants = this.numberParticipants
        }
        return conferenceRepository.save(
            conferenceFromDb
        ).id
    }

    override fun addTalkToConference(conferenceId: Long, talk: Talk): Long {

        val conferenceEntity =
            conferenceRepository.findById(conferenceId)
                .orElseThrow { throw ConferenceNotFoundException(conferenceId) }

        if(LocalDate.now().isAfter(conferenceEntity.fromDate.minus(conferenceProperties.timeBeforeConference))){
            throw TasksRegistrationOutOfTimeException(conferenceEntity.name, conferenceEntity.fromDate, conferenceEntity.fromDate.minus(conferenceProperties.timeBeforeConference))
        }

        if(!authorRepository.existsById(talk.author.id)){
            throw AuthorNotFoundException(talk.author.id)
        }

        val authorEntity = AuthorEntity(talk.author.name)
        authorEntity.id = talk.author.id
        val talkEntity = TalkEntity(
            name = talk.name,
            description = talk.description,
            author = authorEntity,
            type = TalkType.valueOf(talk.type)
        )

        talkEntity.conferenceEntities.add(conferenceEntity)
        if(talkRepository.countByConferenceIdAndName(conferenceId, talkEntity.name) != 0L) {
            throw TaskAlreadyExistsException(talkEntity.name)
        }

        if (talkRepository.countByAuthorAndConference(authorEntity.name, conferenceId) >= conferenceProperties.maxAuthorTasksCount){
            throw TasksInConferenceOutOfLimitException(authorEntity.name, conferenceProperties.maxAuthorTasksCount)
        }

        talkRepository.save(talkEntity)
        return conferenceEntity.id
    }

    override fun getAllTalksForConference(conferenceId: Long): List<Talk> {
        return conferenceRepository.findById(conferenceId)
            .orElseThrow { throw ConferenceNotFoundException(conferenceId) }.talks.toList()
            .map { Talk(it.name, it.description, Author(it.author.id, it.author.name), it.type.toString()) }
    }
}