package ru.mvideo.xpinjection.adaptors.controllers

import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.mvideo.xpinjection.adaptors.dto.Conference
import ru.mvideo.xpinjection.adaptors.dto.ConferenceStatus
import ru.mvideo.xpinjection.adaptors.dto.Talk
import ru.mvideo.xpinjection.service.ConferenceService

@RestController
class ConferenceController(
    val conferenceService: ConferenceService
) {

    @Validated
    @PostMapping(
        path = ["/conferences"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun addConference(
        @RequestBody conference: Conference
    ): ConferenceStatus {
        return ConferenceStatus(
            conferenceService.addConference(conference)
        )
    }

    @GetMapping(
        path = ["/conferences"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getConferences(): List<Conference> {
        return conferenceService.getAllConferences()
    }

    @Validated
    @PutMapping(
        path = ["/conferences/{conference_id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateConference(
        @PathVariable("conference_id") conferenceId: Long,
        @RequestBody conference: Conference
    ): ConferenceStatus {
        return ConferenceStatus(
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
    ): ConferenceStatus {
        return ConferenceStatus(
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