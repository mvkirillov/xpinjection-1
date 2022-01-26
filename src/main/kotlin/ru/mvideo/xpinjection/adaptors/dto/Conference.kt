package ru.mvideo.xpinjection.adaptors.dto

import java.time.LocalDate
import javax.validation.constraints.Min

class Conference(
    val id: Long?,
    val name: String,
    val topic: String,
    val fromDate: LocalDate,
    val toDate: LocalDate,
    @field:Min(value = 101L, message = "minimal number participant is 101")
    val numberParticipants: Long
)