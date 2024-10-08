package org.example.models

import java.util.UUID

class Wishlist(
    val id: String = UUID.randomUUID().toString(),
    val description: String,
    val name: String = description.first().toString()
)