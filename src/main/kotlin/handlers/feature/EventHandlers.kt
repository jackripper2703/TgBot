package org.example.handlers.feature

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.example.handlers.MessageHandler.chatId
import org.example.handlers.editMessage
import org.example.keyboard.Keyboard.buttonMain
import org.example.keyboard.Keyboard.keyboardBack
import org.example.keyboard.feature.KeyboardEvent.createDayKeyboard
import org.example.keyboard.feature.KeyboardEvent.createEventsKeyboard
import org.example.keyboard.feature.KeyboardEvent.createMonthKeyboard
import org.example.keyboard.feature.KeyboardEvent.createYearKeyboard
import org.example.keyboard.feature.KeyboardEvent.keyboardEvents
import org.example.state.EventState
import org.example.store.EventsStore.events

fun handleEventQuery(bot: Bot, data: String, userId: Long, messageId: Long?) {
    when {

        data == "events" -> editMessage(bot, userId, messageId, "Управление мероприятиями", keyboardEvents)

        data == "eventsList" -> {
            if (events.isNotEmpty()) {

                bot.editMessageText(
                    chatId = ChatId.fromId(userId),
                    messageId = messageId,
                    text = "Список мероприятий:\n",
                    replyMarkup = createEventsKeyboard()
                )
            } else {
                bot.editMessageText(
                    chatId = ChatId.fromId(userId),
                    messageId = messageId,
                    text = "Список мероприятий пуст... (",
                    replyMarkup = keyboardBack
                )
            }
        }

// Обработка нажатия кнопки с eventUUID
        data.startsWith("eventUUID:") -> {
            val eventUUID = data.split(":")[1] // Извлекаем UUID события
            val eventIndex = events.indexOfFirst { it.id == eventUUID } // Ищем событие по UUID

            if (eventIndex != -1) {
                val messageText = """
                    |Дата события: ${events[eventIndex].date}
                    |Описание:
                    |${events[eventIndex].description}
                    """.trimMargin()

                editMessage(
                    bot, userId, messageId, messageText, InlineKeyboardMarkup.create(
                        listOf(
                            listOf(
                                InlineKeyboardButton.CallbackData(
                                    text = "Удалить событие", callbackData = "deleteEvent:${events[eventIndex].id}"
                                ), buttonMain
                            )
                        )
                    )
                )
            }
        }

        data.startsWith("deleteEvent:") -> {
            val eventId = data.split(":")[1]
            val eventToRemove = events.find { it.id == eventId }

            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = """
                    |Событие отменилось:
                    |
                    |${eventToRemove!!.name}
                    |
                    |Дата: ${eventToRemove!!.date}
                    """.trimMargin()
            )

            if (eventToRemove != null) {
                events.remove(eventToRemove) }
            editMessage(bot, userId, messageId,
                "Событие с ID $eventId удалено.",
                keyboardBack
                )

        }

        data == "eventCreate" -> editMessage(
            bot, userId, messageId, "Выбери год:", createYearKeyboard()
        )

        data.startsWith("selectYear") -> {
            val selectedYear = data.split(":")[1].toInt()
            editMessage(
                bot,
                userId,
                messageId,
                """
                |Выбран год: ${selectedYear}
                |Выберите месяц:"
                """.trimMargin(),
                createMonthKeyboard(selectedYear)
            )
        }

        data.startsWith("selectMonth") -> {
            val (selectedYear, selectedMonth) = data.split(":").let { it[1].toInt() to it[2].toInt() }
            editMessage(
                bot,
                userId,
                messageId,
                "Выбран месяц: $selectedMonth. Выберите день:",
                createDayKeyboard(selectedYear, selectedMonth)
            )
        }

        data.startsWith("selectDay") -> {
            val splitData = data.split(":")

            val selectedYear = splitData[1].toInt()
            val selectedMonth = splitData[2].toInt()
            val selectedDay = splitData[3].toInt()

            val selectedDate = "$selectedDay:$selectedMonth:$selectedYear"

            EventState.selectedDate = selectedDate
            EventState.isWaitingForDescription = true

            editMessage(
                bot, userId, messageId, """
                    |Дата выбрана: $selectedDate.
                    |Пожалуйста, введите описание мероприятия:
                    |!!!Первое слово будет названием!!!
                """.trimMargin(), keyboardBack
            )
        }
    }
}
