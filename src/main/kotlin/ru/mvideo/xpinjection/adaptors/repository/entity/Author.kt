package ru.mvideo.xpinjection.adaptors.repository.entity

import org.hibernate.Hibernate
import org.hibernate.annotations.NaturalId
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "author")
class Author(
    @NaturalId(mutable = false)
    @Column(name = "name", nullable = false)
    val name: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    @SequenceGenerator(name = "author_id_seq", sequenceName = "author_id_seq", allocationSize = 1)
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Author

        return name == other.name
    }

    override fun hashCode(): Int = Objects.hash(name);

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(name = $name , id = $id )"
    }

}