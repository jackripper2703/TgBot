package org.example

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import org.example.handlers.handleCallbackQuery
import org.example.handlers.MessageHandler
import com.github.kotlintelegrambot.dispatcher.text
import org.example.helpers.ConfigHelper

fun main() {
    val bot = bot {
        token = ConfigHelper.token

        dispatch {
            text {
                MessageHandler.handleTextMessage(bot, message)
            }

            callbackQuery {
                handleCallbackQuery(bot).invoke(this)
            }
        }
    }
    bot.startPolling()
}
