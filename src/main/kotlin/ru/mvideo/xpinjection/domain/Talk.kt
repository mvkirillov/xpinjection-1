package ru.mvideo.xpinjection.domain

class Talk(
    val name: String,
    val description: String,
    val author: Author,
    val type: TalkType
)