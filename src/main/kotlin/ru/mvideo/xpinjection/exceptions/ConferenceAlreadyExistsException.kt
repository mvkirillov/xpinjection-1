package ru.mvideo.xpinjection.exceptions

import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.web.server.ResponseStatusException

class ConferenceAlreadyExistsException(
    name: String
) : ResponseStatusException(CONFLICT, "conference with name: $name already exists")
