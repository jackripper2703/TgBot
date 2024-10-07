package org.example.handlers

import org.example.models.EventStore
import org.example.state.EventState
import org.example.models.Event
import org.example.Keyboards.Keyboards
import org.example.state.SecretSantaState

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import org.example.helpers.ConfigHelper

object MessageHandler {

    val chatId = ConfigHelper.getProperty("chat_id")!!.toLong()

    fun handleTextMessage(bot: Bot, message: Message) {
        if (EventState.isWaitingForDescription) {
            val description = message.text ?: return
            val selectedDate = EventState.selectedDate ?: return

            // Создаем и сохраняем новое мероприятие
            EventStore.events.add(Event(selectedDate, description))

            // Уведомляем пользователя о создании мероприятия
            bot.sendMessage(
                chatId = ChatId.fromId(message.chat.id),
                text = "Описание мероприятия: \n\n\"$description\" \n\nУспешно сохранено.\n\nВыберите действие:",
                replyMarkup = Keyboards.keyboardMain
            )

            // Уведомляем группу о создании мероприятия
            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = "Создано новое мероприятие:\n\n $description \n\nДата: $selectedDate"
            )

            // Сбрасываем состояние
            EventState.isWaitingForDescription = false
            EventState.selectedDate = null
            return
        }

        // Существующая логика обработки команд
        val command = message.text ?: return
        val username = message.from?.username ?: "Unknown User"
        println("[$username] Received command: $command")

        if (message.chat.type == "private") {
            when (command) {
                "/start" -> {
                    bot.sendMessage(
                        chatId = ChatId.fromId(message.chat.id),
                        text = "Выберите действие:",
                        replyMarkup = Keyboards.keyboardMain
                    )
                }

                "/secretSantaRestart" -> {
                    SecretSantaState.secretSantaNow = false
                    bot.sendMessage(
                        chatId = ChatId.fromId(message.chat.id),
                        text = "Состояние Тайного Санты сброшено. Выберите действие:",
                        replyMarkup = Keyboards.keyboardMain
                    )
                }
            }
        }
    }
}
