package ru.mvideo.xpinjection.adaptors.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mvideo.xpinjection.adaptors.repository.entity.TalkEntity

@Repository
interface TalkRepository : CrudRepository<TalkEntity, Long> {

    /*@Query(value = "select count(*) from talk t join talk_at_conference tac on t.id = tac.talk_id where t.name =:name and tac.conference_id =:conferenceId", nativeQuery = true)
    fun countByConferenceIdAndName(conferenceId: Long, name: String): Long*/


    @Query(value = "select count(*) from talk t join author a on t.author_id = a.id join talk_at_conference tac on t.id = tac.talk_id where a.name =:authorName and tac.conference_id =:conferenceId", nativeQuery = true)
    fun countByAuthorAndConference(authorName: String, conferenceId: Long): Long

    fun countByNameAndConferenceEntitiesId(name:String, conferenceId: Long): Long
}