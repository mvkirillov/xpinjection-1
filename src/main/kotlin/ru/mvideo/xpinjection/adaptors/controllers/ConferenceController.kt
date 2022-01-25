package ru.mvideo.xpinjection.adaptors.controllers

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.mvideo.xpinjection.adaptors.dto.Conference
import ru.mvideo.xpinjection.adaptors.dto.ConferenceStatus
import ru.mvideo.xpinjection.adaptors.dto.Talk
import ru.mvideo.xpinjection.service.ConferenceService
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
class ConferenceController(val conferenceService: ConferenceService) {

    @PostMapping(path = ["/conferences"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addConference(@RequestBody @Valid conference: Conference): ConferenceStatus {
        return ConferenceStatus(conferenceService.addConference(conference))
    }

    @GetMapping(path = ["/conferences"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getConferences(): List<Conference> {
        return conferenceService.getAllConferences()
    }

    @PutMapping(path = ["/conferences/{conference_id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateConference(
        @RequestParam @NotBlank conferenceId: Long,
        @RequestBody @Valid conference: Conference
    ): ConferenceStatus {
        return ConferenceStatus(conferenceService.updateConferenceById(conferenceId, conference))
    }

    @PostMapping(path = ["/conferences/{conference_id}/talks"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addTalkToConference(@RequestParam @NotBlank conferenceId: Long, @RequestBody @Valid talk: Talk): ConferenceStatus {
        return ConferenceStatus(conferenceService.addTalkToConference(conferenceId, talk))
    }


    @GetMapping(path = ["/conferences/{conference_id}/talks"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getTalks(@RequestParam @NotBlank conferenceId: Long): List<Talk> {
        return conferenceService.getAllTalksForConference(conferenceId)
    }
}