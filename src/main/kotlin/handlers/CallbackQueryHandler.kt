package org.example.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatcher.handlers.HandleCallbackQuery
import com.github.kotlintelegrambot.entities.ChatId
import org.example.Keyboards.Keyboards
import org.example.state.SecretSantaState
import org.example.helpers.ConfigHelper
import org.example.state.EventState

fun handleCallbackQuery(bot: Bot): HandleCallbackQuery = {

    val MOCK = "Пока еще не сделал, держу как макет"

    val secretSantaInterface = if (SecretSantaState.secretSantaNow) {
        Keyboards.keyboardSecretSantaInProgress
    } else {
        Keyboards.keyboardSecretSanta
    }

    val userId = callbackQuery.from.id
    val username = callbackQuery.from.username ?: "Unknown User"
    val messageId = callbackQuery.message?.messageId
    val data = callbackQuery.data

    println("[$username] Received callback query: $data")

    when {
        data == "selectYear" -> {
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = "Выбери год:",
                replyMarkup = Keyboards.createYearKeyboard()
            )
        }

        data.startsWith("selectYear") -> {
            val selectedYear = data.split(":")[1].toInt()
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = "Выбран год: $selectedYear. Выберите месяц:",
                replyMarkup = Keyboards.createMonthKeyboard(selectedYear)
            )
        }

        data.startsWith("selectMonth") -> {
            val selectedYear = data.split(":")[1].toInt()
            val selectedMonth = data.split(":")[2].toInt()
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = "Выбран месяц: $selectedMonth. Выберите день:",
                replyMarkup = Keyboards.createDayKeyboard(selectedYear, selectedMonth)
            )
        }

        data.startsWith("selectDay") -> {
            val selectedYear = data.split(":")[1].toInt()
            val selectedMonth = data.split(":")[2].toInt()
            val selectedDay = data.split(":")[3].toInt()
            val selectedDate = "$selectedDay:$selectedMonth:$selectedYear"

            EventState.selectedDate = selectedDate
            EventState.isWaitingForDescription = true

            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = "Дата выбрана: $selectedDate. Пожалуйста, введите описание мероприятия:",
                replyMarkup = Keyboards.keyboardBack
            )
        }


        data == "keyboardMain" -> {
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = "Выберите действие:",
                replyMarkup = Keyboards.keyboardMain
            )
            EventState.isWaitingForDescription = false
            EventState.selectedDate = null
        }

        data == "events" -> {
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = "Управление мероприятиями",
                replyMarkup = Keyboards.keyboardEvents
            )
        }

        data == "eventsList" -> {
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = MOCK,
                replyMarkup = Keyboards.keyboardBack
            )
        }

        data == "eventDate" -> {
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = "Введите описание",
                replyMarkup = Keyboards.keyboardBack
            )
        }

        data == "wishList" -> { // Список желаний
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = "Раздел желаний",
                replyMarkup = Keyboards.keyboardWishList
            )
        }

        data == "wishListCreate" -> {
            // Создание нового желания
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = MOCK,
                replyMarkup = Keyboards.keyboardBack
            )
        }

        data == "wishListList" -> {
            // Вывод списка желаний
            // Должен быть уникальным для каждого участника
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = MOCK,
                replyMarkup = Keyboards.keyboardBack
            )
        }

        data == "wishListOther" -> {
            // Вывод участников
            // Переход отдельно в каждого для изучения его списка
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = MOCK,
                replyMarkup = Keyboards.keyboardBack
            )
        }

        data == "secretSanta" -> {
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = "Раздел Тайного Санты",
                replyMarkup = secretSantaInterface
            )
        }

        data == "secretSantaRegister" -> {
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = MOCK, // Добавление в общий список
                replyMarkup = Keyboards.keyboardBack
            )
        }

        data == "secretSantaList" -> {
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = MOCK, // вывод общего списка участников
                replyMarkup = Keyboards.keyboardBack
            )
        }

        data == "secretSantaStart" -> {
            SecretSantaState.secretSantaNow = true
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = "Вы являетесь Тайным Сантой для $username",
                replyMarkup = Keyboards.keyboardBack
            )
        }

        data == "secretSantaWho" -> {
            bot.editMessageText(
                chatId = ChatId.fromId(userId),
                messageId = messageId,
                text = "Вы являетесь Тайным Сантой для $username",
                replyMarkup = Keyboards.keyboardBack
            )
        }

        else -> {
            bot.sendMessage(
                chatId = ChatId.fromId(userId),
                text = "Неизвестная команда."
            )
        }
    }

    bot.answerCallbackQuery(callbackQuery.id)
}
