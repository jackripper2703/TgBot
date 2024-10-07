package org.example

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import org.example.handlers.handleCallbackQuery
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import org.example.helpers.ConfigHelper

fun main() {

    val bot = bot {
        token = ConfigHelper.getProperty("bot.token")
            ?: throw IllegalArgumentException("Токен бота не найден в конфигурационном файле.")

        dispatch {
            text {
                val command = message.text ?: return@text
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
                                chatId = ChatId.fromId(message.chat.id), text = """
                                    secretSantaNow = false
                                    
                                    Выберите действие:
                                    """.trimIndent(), replyMarkup = Keyboards.keyboardMain
                            )
                        }
                    }
                } else return@text
            }

            callbackQuery {
                handleCallbackQuery(bot).invoke(this)
            }

        }
    }
    bot.startPolling()
}
