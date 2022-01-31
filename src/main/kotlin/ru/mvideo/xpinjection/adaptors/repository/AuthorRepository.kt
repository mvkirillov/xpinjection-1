package ru.mvideo.xpinjection.adaptors.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mvideo.xpinjection.adaptors.repository.entity.AuthorEntity

@Repository
interface AuthorRepository : CrudRepository<AuthorEntity, Long> {
}