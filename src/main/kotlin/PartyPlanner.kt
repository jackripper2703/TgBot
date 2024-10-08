package org.example

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import org.example.handlers.handleCallbackQuery
import org.example.handlers.MessageHandler
import com.github.kotlintelegrambot.dispatcher.text
import org.example.helpers.ConfigHelper
import org.example.models.Event
import org.example.store.EventsStore

fun main() {

    val selectedDate = "01:01:2024" // Например, выбранная дата
    val description = "Описание мероприятия" // Вводимое описание

    EventsStore.events.add(Event(date = selectedDate, description = description))

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
