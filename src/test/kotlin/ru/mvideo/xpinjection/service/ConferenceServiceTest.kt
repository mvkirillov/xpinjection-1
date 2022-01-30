package ru.mvideo.xpinjection.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
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
import ru.mvideo.xpinjection.exceptions.ConferenceWrongSameDatesException
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
class ConferenceServiceTest {
    @InjectMocks
    lateinit var service: ConferenceServiceImpl

    @Mock
    lateinit var conferenceRepo: ConferenceRepository

    @Mock
    lateinit var talksRepo: TalkRepository

    @Test
    fun addConferenceThrowExceptionIfConferenceAlreadyExists() {
        whenever(conferenceRepo.findByName(any())).thenReturn(
            Optional.of(
                ConferenceEntity(
                    "", "", LocalDate.now(), LocalDate.now(), 1234
                )
            )
        )
        assertThrows<ConferenceAlreadyExistsException> {
            service.addConference(Conference(123L, "", "", LocalDate.now(), LocalDate.now(), 21122))
        }
    }

    @Test
    fun addConferenceThrowExceptionIfWrongDatesByEquals() {
        assertThrows<ConferenceWrongSameDatesException> {
            service.addConference(Conference(123L, "", "", LocalDate.now(), LocalDate.now(), 21122))
        }
    }

    @Test
    fun addConferenceThrowExceptionIfWrongDatesByStartBiggestThanEnd() {
        assertThrows<ConferenceWrongSameDatesException> {
            service.addConference(Conference(123L, "", "", LocalDate.now().plusDays(1), LocalDate.now(), 21122))
        }
    }

    @Test
    fun testAddConferenceHappyPath() {
        val conference = Conference(123L, "", "", LocalDate.now(), LocalDate.now().plusDays(2), 21122)
        val entity = ConferenceEntity("", "", LocalDate.now(), LocalDate.now().plusDays(2), 21122)
        whenever(conferenceRepo.save(entity)).thenReturn(entity.apply { this.id = 123 })

        val id = service.addConference(conference)
        verify(conferenceRepo).save(entity)
        assert(id == 123L)
    }

    @Test
    fun getAllIfEmpty() {
        whenever(conferenceRepo.findAllWithTalks()).thenReturn(emptyList())
        assert(service.getAllConferences().isEmpty())
    }

    @Test
    fun getAllIfOneConferencePresentAndOneTalk() {
        val author = AuthorEntity("author")
        val conferences = listOf(
            ConferenceEntity(
                "abc", "topic", LocalDate.now(),
                LocalDate.now().plusDays(2), 1234L,
                setOf(TalkEntity("1", "", author, TalkType.LECTURE))
            )
        )
        whenever(conferenceRepo.findAllWithTalks())
            .thenReturn(
                conferences
            )
        val allConferences = service.getAllConferences()
        assert(allConferences.isNotEmpty())
        // хорошо бы проверить маппинг
    }

    // todo нужна проверка апдейта
}