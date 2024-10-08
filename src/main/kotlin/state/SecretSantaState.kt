package org.example.state

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import org.example.keyboard.Keyboard.keyboardMain

object SecretSantaState {
    var secretSantaNow: Boolean = false

    // Сброс состояния Тайного Санты
    fun restartSecretSanta(bot: Bot, chatId: Long) {
        secretSantaNow = false
        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = "Состояние Тайного Санты сброшено. Выберите действие:",
            replyMarkup = keyboardMain
        )
    }
}
