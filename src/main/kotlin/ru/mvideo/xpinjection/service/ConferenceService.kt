package ru.mvideo.xpinjection.service

import ru.mvideo.xpinjection.adaptors.dto.Conference
import ru.mvideo.xpinjection.adaptors.dto.ConferenceWithTalks
import ru.mvideo.xpinjection.adaptors.dto.Talk
import ru.mvideo.xpinjection.adaptors.dto.UpdateConferenceDto

interface ConferenceService {
    fun addConference(conference: Conference): Long

    fun getAllConferences(): List<ConferenceWithTalks>

    fun updateConferenceById(conferenceId: Long, conference: UpdateConferenceDto): Long

    fun addTalkToConference(conferenceId: Long, talk: Talk): Long

    fun getAllTalksForConference(conferenceId: Long): List<Talk>
}