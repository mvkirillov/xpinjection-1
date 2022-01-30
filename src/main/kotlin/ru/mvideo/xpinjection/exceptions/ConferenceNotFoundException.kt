package ru.mvideo.xpinjection.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ConferenceNotFoundException(conferenceId: Long) : ResponseStatusException(HttpStatus.BAD_REQUEST,"Conference with id $conferenceId not found")