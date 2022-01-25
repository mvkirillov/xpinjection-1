package ru.mvideo.xpinjection.adaptors.dto

import javax.validation.constraints.Pattern

class Talk(
    val name: String,
    val description: String,
    val author: Author,
    @Pattern(regexp = "LECTURE|MASTER_CLASS|WORKSHOP")
    val type: String
)