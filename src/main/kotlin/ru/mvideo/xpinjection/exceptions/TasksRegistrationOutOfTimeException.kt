package ru.mvideo.xpinjection.exceptions

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate

class TasksRegistrationOutOfTimeException(
        conferenceName: String,
        conferenceStartDate: LocalDate,
        endRegistrationDate: LocalDate
) : ResponseStatusException(BAD_REQUEST, "Task for conference: $conferenceName which starts on $conferenceStartDate must be applied before $endRegistrationDate")
