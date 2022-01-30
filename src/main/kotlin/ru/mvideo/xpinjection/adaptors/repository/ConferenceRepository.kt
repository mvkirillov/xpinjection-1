package ru.mvideo.xpinjection.adaptors.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import ru.mvideo.xpinjection.adaptors.repository.entity.ConferenceEntity
import java.util.*

interface ConferenceRepository : CrudRepository<ConferenceEntity, Long> {
    fun findByName(name: String) : Optional<ConferenceEntity>

    @Query("select c from ConferenceEntity c join fetch TalkEntity")
    fun findAllWithTalks(): List<ConferenceEntity>
}