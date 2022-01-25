package ru.mvideo.xpinjection.service

import ru.mvideo.xpinjection.adaptors.dto.Conference
import ru.mvideo.xpinjection.adaptors.dto.Talk

interface ConferenceService {
    fun addConference(conference: Conference): Long

    fun getAllConferences(): List<Conference>

    fun updateConferenceById(conferenceId: Long, conference: Conference): Long

    fun addTalkToConference(conferenceId: Long, talk: Talk): Long

    fun getAllTalksForConference(conferenceId: Long): List<Talk>
}