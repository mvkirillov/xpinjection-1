package ru.mvideo.xpinjection.adaptors.repository.entity

import org.hibernate.Hibernate
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
@Table(name = "talk")
class Talk(
    @Column(name = "name", nullable = false)
    var name: String,

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
        if (other === null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Talk

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}

enum class TalkType {
    LECTURE, MASTER_CLASS, WORKSHOP
}