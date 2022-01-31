package ru.mvideo.xpinjection.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class AuthorNotFoundException(conferenceId: Long) : ResponseStatusException(HttpStatus.BAD_REQUEST,"Author with id $conferenceId not found")