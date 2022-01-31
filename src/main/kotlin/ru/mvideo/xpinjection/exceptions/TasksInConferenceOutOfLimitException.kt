package ru.mvideo.xpinjection.exceptions

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.server.ResponseStatusException

class TasksInConferenceOutOfLimitException(
    name: String,
    limit: Int
) : ResponseStatusException(BAD_REQUEST, "author with name: $name already has $limit tasks")
