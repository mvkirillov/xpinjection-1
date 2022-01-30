package ru.mvideo.xpinjection.exceptions

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.server.ResponseStatusException

class ConferenceWrongSameDatesException : ResponseStatusException(BAD_REQUEST, "conference has the same start and end time or start date > end date")
