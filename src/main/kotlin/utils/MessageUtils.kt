package org.example.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup

fun editMessage(
    bot: Bot, userId: Long, messageId: Long?, text: String, keyboard: InlineKeyboardMarkup? = null
) {
    bot.editMessageText(
        chatId = ChatId.fromId(userId), messageId = messageId, text = text, replyMarkup = keyboard
    )
}
