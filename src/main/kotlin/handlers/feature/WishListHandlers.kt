package org.example.handlers.feature

import com.github.kotlintelegrambot.Bot
import org.example.handlers.editMessage
import org.example.keyboard.Keyboard.MOCK
import org.example.keyboard.Keyboard.keyboardBack
import org.example.keyboard.feature.KeyboardWishList.keyboardWishList

fun handleWishListQuery(bot: Bot, data: String, userId: Long, messageId: Long?) {


    when (data) {
        "wishList" -> editMessage(bot, userId, messageId, "Раздел желаний", keyboardWishList)
        "wishListCreate" -> editMessage(bot, userId, messageId, MOCK, keyboardBack)
        "wishListList" -> editMessage(bot, userId, messageId, MOCK, keyboardBack)
        "wishListOther" -> editMessage(bot, userId, messageId, MOCK, keyboardBack)
    }
}
