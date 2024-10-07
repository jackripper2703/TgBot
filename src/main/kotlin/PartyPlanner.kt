package org.example

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import org.example.helpers.ConfigHelper

fun main() {
//     На времена, когда сделаю оповещение в группу
//    val chatId = ConfigHelper.getProperty("chat_id")!!.toLong()
    var secretSantaNow = false
    var voteNow = false

    var secretSantaInterface = if (secretSantaNow) {Keyboards.keyboard04} else {Keyboards.keyboard040}
    var voteInterface = if (voteNow) {Keyboards.keyboard03} else {Keyboards.keyboard030}

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
                                replyMarkup = Keyboards.keyboard00
                            )
                        }

                        "/secretSanta_restart" -> {
                            secretSantaNow = false
                            bot.sendMessage(
                                chatId = ChatId.fromId(message.chat.id),
                                text = "Выберите действие:",
                                replyMarkup = Keyboards.keyboard00
                            )
                        }

                        else -> {
                            bot.sendMessage(
                                chatId = ChatId.fromId(message.chat.id),
                                text = "Нажмите /start, чтобы увидеть доступные команды."
                            )
                        }
                    }
                } else return@text
            }


            callbackQuery {
                val userId = callbackQuery.from.id

                val messageId = callbackQuery.message?.messageId ?: return@callbackQuery
                println("click")
                when (callbackQuery.data) {
                    "main" -> {
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = "Выберите действие:",
                            replyMarkup = Keyboards.keyboard00
                        )
                    }

                    "events" -> {
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = "Управление мероприятиями",
                            replyMarkup = Keyboards.keyboard01
                        )
                    }

                    "vote" -> {
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = "Голосование",
                            replyMarkup = voteInterface
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

                    "wishList" -> {
                        bot.editMessageText(
                            chatId = ChatId.fromId(userId),
                            messageId = messageId,
                            text = "Раздел желаний",
                            replyMarkup = Keyboards.keyboard02
                        )
                    }

                }
                bot.answerCallbackQuery(callbackQuery.id)
            }
        }
    }
    bot.startPolling()
}
