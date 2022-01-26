package ru.mvideo.xpinjection.adaptors.ui

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.mvideo.xpinjection.adaptors.controllers.ConferenceController
import ru.mvideo.xpinjection.adaptors.dto.Conference
import ru.mvideo.xpinjection.exceptions.ConferenceAlreadyExistsException
import ru.mvideo.xpinjection.service.ConferenceService
import java.time.LocalDate


@ExtendWith(value = [SpringExtension::class])
@WebMvcTest(value = [ConferenceController::class])
class ConferenceControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var bookService: ConferenceService

    @Test
    fun testConflictIfSecondConferenceAdded() {
        whenever(bookService.addConference(any())).thenThrow(ConferenceAlreadyExistsException::class.java)
        mockMvc.perform(
            post("/conferences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    asJsonString(
                        Conference(
                            123L,
                            "firstName4",
                            "lastName4",
                            LocalDate.now(),
                            LocalDate.now(),
                            123L
                        )
                    )
                )
        )
            .andExpect(
                status().isConflict
            )

    }

    @Test
    fun testValidationNumberOfParticipants() {
        mockMvc.perform(
            post("/conferences")
                .content(
                    asJsonString(
                        Conference(
                            123L,
                            "firstName4",
                            "lastName4",
                            LocalDate.now(),
                            LocalDate.now(),
                            10L
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)

    }

    private val om = ObjectMapper().findAndRegisterModules()
    fun asJsonString(obj: Any): String {
        return try {
            om.writeValueAsString(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}