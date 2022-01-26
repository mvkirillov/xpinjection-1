package ru.mvideo.xpinjection.adaptors.repository

import org.springframework.data.repository.CrudRepository
import ru.mvideo.xpinjection.adaptors.repository.entity.ConferenceEntity
import java.util.*

interface ConferenceRepository : CrudRepository<ConferenceEntity, Long> {
    fun findByName(name: String) : Optional<ConferenceEntity>
}