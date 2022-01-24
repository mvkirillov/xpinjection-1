package ru.mvideo.xpinjection.adaptors.repository.entity

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "author")
class Author(
    @Column(name = "name", nullable = false)
    var name: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    @SequenceGenerator(name = "author_id_seq", sequenceName = "author_id_seq", allocationSize = 1)
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other === null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Author

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}