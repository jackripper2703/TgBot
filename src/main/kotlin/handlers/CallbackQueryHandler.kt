package org.example.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatcher.handlers.HandleCallbackQuery
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.example.Keyboards.Keyboards
import org.example.Keyboards.Keyboards.buttonMain
import org.example.state.SecretSantaState
import org.example.store.EventsStore
import org.example.state.EventState
import org.example.state.EventState.resetEventState

fun handleCallbackQuery(bot: Bot): HandleCallbackQuery = {

    val MOCK = "Пока еще не сделал, держу как макет"
    val userId = callbackQuery.from.id
    val username = callbackQuery.from.username ?: "Unknown User"
    val messageId = callbackQuery.message?.messageId
    val data = callbackQuery.data

    val secretSantaInterface = if (SecretSantaState.secretSantaNow) {
        Keyboards.keyboardSecretSantaInProgress
    } else {
        Keyboards.keyboardSecretSanta
    }

    println("[$username] Received callback query: $data")

    when {
        data == "selectYear" -> editMessage(
            bot, userId, messageId,
            "Выбери год:", Keyboards.createYearKeyboard()
        )

        data.startsWith("selectYear") -> {
            val selectedYear = data.split(":")[1].toInt()
            editMessage(
                bot, userId, messageId,
                "Выбран год: $selectedYear. Выберите месяц:",
                Keyboards.createMonthKeyboard(selectedYear)
            )
        }

        data.startsWith("selectMonth") -> {
            val (selectedYear, selectedMonth) = data.split(":").let { it[1].toInt() to it[2].toInt() }
            editMessage(
                bot, userId, messageId,
                "Выбран месяц: $selectedMonth. Выберите день:",
                Keyboards.createDayKeyboard(selectedYear, selectedMonth)
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
                bot, userId, messageId,
                "Дата выбрана: $selectedDate. Пожалуйста, введите описание мероприятия:",
                Keyboards.keyboardBack
            )
        }

        data == "keyboardMain" -> {
            editMessage(bot, userId, messageId, "Выберите действие:", Keyboards.keyboardMain)
            resetEventState()
        }

        data == "events" -> editMessage(bot, userId, messageId, "Управление мероприятиями", Keyboards.keyboardEvents)

        data == "eventsList" -> {
            if (EventsStore.events.isNotEmpty()) {

                bot.editMessageText(
                    chatId = ChatId.fromId(userId),
                    messageId = messageId,
                    text = "Список мероприятий:\n",
                    replyMarkup = Keyboards.createEventsKeyboard()
                )
            } else {
                bot.editMessageText(
                    chatId = ChatId.fromId(userId),
                    messageId = messageId,
                    text = "Список мероприятий пуст... (",
                    replyMarkup = Keyboards.keyboardBack
                )
            }
        }


        data.startsWith("deleteEvent:") -> {
            val eventId = data.split(":")[1]
            val eventToRemove = EventsStore.events.find { it.id == eventId }

            if (eventToRemove != null) {
                EventsStore.events.remove(eventToRemove) // Удаляем событие по ID
                bot.sendMessage(
                    chatId = ChatId.fromId(userId),
                    text = "Событие с ID $eventId удалено.",
                    replyMarkup = Keyboards.keyboardBack
                )
            }
        }

        data.startsWith("eventDate:") -> {
            val eventDate = data.split(":")[1]
            val event = EventsStore.events.find { it.date == eventDate }

            event?.let {
                val messageText = "Дата события: ${it.date}\nОписание: ${it.description}"
                bot.sendMessage(
                    chatId = ChatId.fromId(userId),
                    text = messageText,
                    replyMarkup = InlineKeyboardMarkup.create(
                        listOf(
                            listOf(InlineKeyboardButton.CallbackData("Удалить событие", "deleteEvent:${it.date}")),
                            listOf(buttonMain ?: InlineKeyboardButton.CallbackData("Главное меню", "keyboardMain"))
                        )
                    )
                )
            } ?: run {
                bot.sendMessage(ChatId.fromId(userId), "Событие не найдено.")
            }
        }

        data == "wishList" -> editMessage(bot, userId, messageId, "Раздел желаний", Keyboards.keyboardWishList)

        data == "wishListCreate" -> editMessage(bot, userId, messageId, MOCK, Keyboards.keyboardBack)

        data == "wishListList" -> editMessage(bot, userId, messageId, MOCK, Keyboards.keyboardBack)

        data == "wishListOther" -> editMessage(bot, userId, messageId, MOCK, Keyboards.keyboardBack)

        data == "secretSanta" -> editMessage(bot, userId, messageId, "Раздел Тайного Санты", secretSantaInterface)

        data == "secretSantaRegister" -> editMessage(bot, userId, messageId, MOCK, Keyboards.keyboardBack)

        data == "secretSantaList" -> editMessage(bot, userId, messageId, MOCK, Keyboards.keyboardBack)

        data == "secretSantaStart" -> {
            SecretSantaState.secretSantaNow = true
            editMessage(bot, userId, messageId, "Вы являетесь Тайным Сантой для $username", Keyboards.keyboardBack)
        }

        data == "secretSantaWho" -> editMessage(
            bot,
            userId,
            messageId,
            "Вы являетесь Тайным Сантой для $username",
            Keyboards.keyboardBack
        )

        else -> bot.sendMessage(ChatId.fromId(userId), "Неизвестная команда.")
    }

    bot.answerCallbackQuery(callbackQuery.id)
}

private fun editMessage(
    bot: Bot,
    userId: Long,
    messageId: Long?,
    text: String,
    keyboard: InlineKeyboardMarkup? = null
) {
    bot.editMessageText(
        chatId = ChatId.fromId(userId),
        messageId = messageId,
        text = text,
        replyMarkup = keyboard
    )
}
