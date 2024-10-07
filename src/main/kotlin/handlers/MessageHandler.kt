package handlers

import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import org.example.Keyboards
import org.example.SecretSantaState

object MessageHandler {

    fun handleTextMessage(bot: com.github.kotlintelegrambot.Bot, message: Message) { // Измените здесь
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
                        text = """
                            secretSantaNow = false
                            
                            Выберите действие:
                            """.trimIndent(),
                        replyMarkup = Keyboards.keyboardMain
                    )
                }
            }
        }
    }
}
