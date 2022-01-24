package ru.mvideo.xpinjection.domain

import java.time.LocalDate

class Conference(
    val name: String,
    val topic: String,
    val date: LocalDate,
    val numberParticipants: Long,
    val talks: Set<Talk> = emptySet()
)