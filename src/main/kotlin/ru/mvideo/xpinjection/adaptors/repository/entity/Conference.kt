package ru.mvideo.xpinjection.adaptors.repository.entity

import org.hibernate.Hibernate
import org.hibernate.annotations.NaturalId
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "conference")
class Conference(
    @NaturalId
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "topic", nullable = false)
    var topic: String,

    @Column(name = "fromDate", nullable = false)
    var fromDate: LocalDate,

    @Column(name = "toDate", nullable = false)
    var toDate: LocalDate,

    @Column(name = "numberParticipants", nullable = false)
    var numberParticipants: Long,

    @OneToMany
    @JoinTable(
        name = "talk_at_conference",
        joinColumns = [JoinColumn(name = "conference_id")],
        inverseJoinColumns = [JoinColumn(name = "talk_id")]
    )
    var talks: Set<Talk> = emptySet()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conference_id_seq")
    @SequenceGenerator(name = "conference_id_seq", sequenceName = "conference_id_seq", allocationSize = 1)
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Conference

        return name == other.name
    }

    override fun hashCode(): Int = Objects.hash(name);

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(name = $name , id = $id , topic = $topic , fromDate = $fromDate , toDate = $toDate , numberParticipants = $numberParticipants )"
    }

}