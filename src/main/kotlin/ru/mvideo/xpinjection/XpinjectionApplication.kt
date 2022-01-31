package ru.mvideo.xpinjection

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class XpinjectionApplication

fun main(args: Array<String>) {
    runApplication<XpinjectionApplication>(*args)
}
