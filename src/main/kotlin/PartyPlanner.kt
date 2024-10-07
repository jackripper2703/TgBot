package org.example

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import org.example.helpers.ConfigHelper

fun main() {

    val MOCK = "Пока еще не сделал, держу как макет"

    val chatId = ConfigHelper.getProperty("chat_id")!!.toLong()

    var secretSantaNow = false

    var secretSantaInterface = if (secretSantaNow) {
        Keyboards.keyboardSecretSantaInProgress
    } else {
        Keyboards.keyboardSecretSanta
    }

    val bot = bot {
        token = ConfigHelper.getProperty("bot.token")
            ?: throw IllegalArgumentException("Токен бота не найден в конфигурационном файле.")

        dispatch {
            text {
                val command = message.text ?: return@text

                if (message.chat.type.toString() == "private") {
                    when (command) {
                        "/start" -> {
                            bot.sendMessage(
                                chatId = ChatId.fromId(message.chat.id),
                                text = "Выберите действие:",
                                replyMarkup = Keyboards.keyboardMain
                            )
                        }

                        "/secretSantaRestart" -> {
                            secretSantaNow = false
                            bot.sendMessage(
                                chatId = ChatId.fromId(message.chat.id),
                                text = """
                                    secretSantaNow = false
                                    
                                    Выберите действие:
                                    """.trimIndent(),
                                replyMarkup = Keyboards.keyboardMain
                            )
                        }
                    }
                } else return@text
            }


            callbackQuery {
                val userId = callbackQuery.from.id
                val messageId = callbackQuery.message?.messageId ?: return@callbackQuery

                when (callbackQuery.data) {

                    "keyboardMain" -> {
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = "Выберите действие:",
                            replyMarkup = Keyboards.keyboardMain
                        )
                    }

                    "events" -> {
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = "Управление мероприятиями",
                            replyMarkup = Keyboards.keyboardEvents
                        )
                    }

                    "eventsList" -> {
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = MOCK,
                            replyMarkup = Keyboards.keyboardBack
                        )
                    }

                    "eventCreate" -> {
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = MOCK,
                            replyMarkup = Keyboards.keyboardEventDate
                        )
                    }

                    "eventDate" -> {
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = "Введите описание",
                            replyMarkup = Keyboards.keyboardBack
                        )
                    }

                    "wishList" -> { // Список желаний
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = "Раздел желаний",
                            replyMarkup = Keyboards.keyboardWishList
                        )
                    }

                    "wishListCreate" -> {
                        // Создание нового желания
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = MOCK,
                            replyMarkup = Keyboards.keyboardBack
                        )
                    }

                    "wishListList" -> {
                        // Вывод списка желаний
                        // Должен быть уникальным для каждого участника
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = MOCK,
                            replyMarkup = Keyboards.keyboardBack
                        )
                    }

                    "wishListOther" -> {
                        // Вывод участников
                        // Переход отдельно в каждого для изучения его списка
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = MOCK,
                            replyMarkup = Keyboards.keyboardBack
                        )
                    }

                    "secretSanta" -> {
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = "Раздел Тайного Санты",
                            replyMarkup = secretSantaInterface
                        )
                    }

                    "secretSantaRegister" -> {
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = MOCK, // Добавление в общий список
                            replyMarkup = Keyboards.keyboardBack
                        )
                    }

                    "secretSantaList" -> {
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = MOCK, // вывод общего списка участников
                            replyMarkup = Keyboards.keyboardBack
                        )
                    }

                    "secretSantaStart" -> {
                        !secretSantaNow
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = "Вы являетесь Тайным Сантой для ${callbackQuery.from.username}",
                            replyMarkup = Keyboards.keyboardBack
                        )
                    }

                    "secretSantaWho" -> {
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = "Вы являетесь Тайным Сантой для ${callbackQuery.from.username}",
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

        }
    }
    bot.startPolling()
}
