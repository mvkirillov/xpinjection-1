package ru.mvideo.xpinjection.adaptors.repository.entity

import org.hibernate.Hibernate
import org.hibernate.annotations.NaturalId
import java.util.*
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
@Table(name = "talk")
class Talk(
    @NaturalId
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @ManyToOne(fetch = LAZY)
    var author: Author,

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    var type: TalkType,

    @OneToMany(fetch = LAZY)
    @JoinTable(
        name = "talk_at_conference",
        joinColumns = [JoinColumn(name = "talk_id")],
        inverseJoinColumns = [JoinColumn(name = "conference_id")]
    )
    var conferences: Set<Conference> = emptySet()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "talk_id_seq")
    @SequenceGenerator(name = "talk_id_seq", sequenceName = "talk_id_seq", allocationSize = 1)
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Talk

        return name == other.name
    }

    override fun hashCode(): Int = Objects.hash(name);

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(name = $name , id = $id , description = $description , type = $type )"
    }

}

enum class TalkType {
    LECTURE, MASTER_CLASS, WORKSHOP
}