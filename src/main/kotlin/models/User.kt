package org.example.models

class User(
    val userId: Long,
    val username: String,
    val wishlist: ArrayList<Wishlist> = ArrayList()
)
