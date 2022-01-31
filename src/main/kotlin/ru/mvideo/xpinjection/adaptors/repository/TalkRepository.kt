package ru.mvideo.xpinjection.adaptors.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mvideo.xpinjection.adaptors.repository.entity.AuthorEntity
import ru.mvideo.xpinjection.adaptors.repository.entity.ConferenceEntity
import ru.mvideo.xpinjection.adaptors.repository.entity.TalkEntity

@Repository
interface TalkRepository : CrudRepository<TalkEntity, Long> {
    fun existsByConferenceEntitiesAndName(conferenceEntities: MutableSet<ConferenceEntity>, name: String): Boolean
    fun findAllByAuthorAndConferenceEntities(author: AuthorEntity, conferenceEntities: MutableSet<ConferenceEntity>): List<TalkEntity>
}