package ru.mvideo.xpinjection.adaptors.repository

import org.springframework.data.repository.CrudRepository
import ru.mvideo.xpinjection.adaptors.repository.entity.ConferenceEntity

interface ConferenceRepository : CrudRepository<ConferenceEntity, Long> {
}