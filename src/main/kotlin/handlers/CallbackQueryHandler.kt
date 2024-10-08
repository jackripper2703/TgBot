package org.example.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatcher.handlers.HandleCallbackQuery
import org.example.handlers.feature.handleBaseQuery
import org.example.handlers.feature.handleEventQuery
import org.example.handlers.feature.handleSecretSantaQuery
import org.example.handlers.feature.handleWishListQuery

fun handleCallbackQuery(bot: Bot): HandleCallbackQuery = {

    val userId = callbackQuery.from.id
    val username = callbackQuery.from.username ?: "Unknown User"
    val messageId = callbackQuery.message?.messageId
    val data = callbackQuery.data

    println("[$username] Received callback query: $data")

    handleEventQuery(bot, data, userId, messageId)
    handleWishListQuery(bot, data, userId, messageId)
    handleSecretSantaQuery(bot, data, userId, messageId)
    handleBaseQuery(bot, data, userId, messageId)

    bot.answerCallbackQuery(callbackQuery.id)
}
