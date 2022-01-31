package ru.mvideo.xpinjection.exceptions

import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.web.server.ResponseStatusException

class TaskAlreadyExistsException(
    name: String
) : ResponseStatusException(CONFLICT, "task with name: $name already exists")
