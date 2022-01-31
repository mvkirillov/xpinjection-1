package ru.mvideo.xpinjection.adaptors.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mvideo.xpinjection.adaptors.repository.entity.TalkEntity

@Repository
interface TalkRepository : CrudRepository<TalkEntity, Long>{
}