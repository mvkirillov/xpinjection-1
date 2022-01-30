package ru.mvideo.xpinjection.adaptors.dto

import java.time.LocalDate

class ConferenceWithTalks(
    val id: Long,
    val name: String,
    val topic: String,
    val fromDate: LocalDate,
    val toDate: LocalDate,
    val numberParticipants: Long,
    val talks: Set<Talk>
)