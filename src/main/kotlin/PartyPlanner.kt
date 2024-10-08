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
    EventsStore.events.add(Event(date = "01:01:2024", description = "Кинотеатр мероприятия"))
    EventsStore.events.add(Event(date = "01:02:2024", description = "Ресторан мероприятия"))
    EventsStore.events.add(Event(date = "01:03:2024", description = "Музей мероприятия"))
    EventsStore.events.add(Event(date = "01:04:2024", description = "Митап мероприятия"))

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
