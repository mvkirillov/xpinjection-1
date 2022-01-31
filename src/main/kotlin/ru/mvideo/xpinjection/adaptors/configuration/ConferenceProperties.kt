package ru.mvideo.xpinjection.adaptors.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Period

@ConfigurationProperties(prefix = "app.conference")
@Component
class ConferenceProperties {
    val maxAuthorTasksCount: Int = 0
    val timeBeforeConference: Period = Period.ZERO
}