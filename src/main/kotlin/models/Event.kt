package org.example.models

import java.util.UUID

data class Event(
    val id: String = UUID.randomUUID().toString(),
    val date: String,
    val description: String,
    val name: String = description.split(" ").firstOrNull() ?: "",
    val participants: MutableList<User> = mutableListOf()
)
