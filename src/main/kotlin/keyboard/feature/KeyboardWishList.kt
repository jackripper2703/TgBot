package org.example.keyboard.feature

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.example.keyboard.Keyboard.buttonMain

object KeyboardWishList {

    // Желания
    private val buttonWishListCreate = InlineKeyboardButton.CallbackData("Новое желание", "wishListCreate")
    private val buttonWishListList = InlineKeyboardButton.CallbackData("Список желаний", "wishListList")
    private val buttonWishListOther = InlineKeyboardButton.CallbackData("Желания других участников", "wishListOther")

    // Меню "Желания"
    val keyboardWishList = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonWishListCreate),
            listOf(buttonWishListList),
            listOf(buttonWishListOther),
            listOf(buttonMain),
        )
    )
}