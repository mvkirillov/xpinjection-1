package ru.mvideo.xpinjection.adaptors.dto

import org.hibernate.Hibernate
import org.hibernate.annotations.NaturalId
import java.util.*
import javax.persistence.*


class Author(
    var id: Long,
    val name: String
) {
}