package ru.mvideo.xpinjection.domain

import java.time.LocalDate

class Conference(
    val name: String,
    val topic: String,
    val fromDate: LocalDate,
    val toDate: LocalDate,
    val numberParticipants: Long,
    val talks: Set<Talk> = emptySet()
)