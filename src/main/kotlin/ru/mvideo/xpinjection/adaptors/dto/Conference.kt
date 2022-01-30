package ru.mvideo.xpinjection.adaptors.dto

import java.time.LocalDate
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class Conference(
    val id: Long?,
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val topic: String,
    val fromDate: LocalDate,
    val toDate: LocalDate,
    @field:Min(value = 101L, message = "minimal number participant is 101")
    val numberParticipants: Long
)