package ru.mvideo.xpinjection.adaptors.controllers

import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.mvideo.xpinjection.adaptors.dto.*
import ru.mvideo.xpinjection.service.ConferenceService
import javax.validation.Valid

@RestController
class ConferenceController(
    val conferenceService: ConferenceService
) {

    @PostMapping(
        path = ["/conferences"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun addConference(
        @Valid @RequestBody conference: Conference
    ): ConferenceIdContainer {
        return ConferenceIdContainer(
            conferenceService.addConference(conference)
        )
    }

    @GetMapping(
        path = ["/conferences"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getConferences(): List<ConferenceWithTalks> {
        return conferenceService.getAllConferences()
    }

    @PutMapping(
        path = ["/conferences/{conference_id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateConference(
        @PathVariable("conference_id") conferenceId: Long,
        @Valid @RequestBody conference: UpdateConferenceDto
    ): ConferenceIdContainer {
        return ConferenceIdContainer(
            conferenceService.updateConferenceById(
                conferenceId,
                conference
            )
        )
    }

    @Validated
    @PostMapping(
        path = ["/conferences/{conference_id}/talks"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun addTalkToConference(
        @PathVariable("conference_id") conferenceId: Long,
        @RequestBody talk: Talk
    ): ConferenceIdContainer {
        return ConferenceIdContainer(
            conferenceService.addTalkToConference(
                conferenceId,
                talk
            )
        )
    }


    @GetMapping(
        path = ["/conferences/{conference_id}/talks"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getTalks(
        @PathVariable("conference_id") conferenceId: Long
    ): List<Talk> {
        return conferenceService.getAllTalksForConference(conferenceId)
    }
}