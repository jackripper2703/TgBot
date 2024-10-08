package org.example.handlers.feature

import com.github.kotlintelegrambot.Bot
import org.example.handlers.editMessage
import org.example.keyboard.Keyboard.keyboardMain
import org.example.state.EventState.resetEventState

fun handleBaseQuery(bot: Bot, data: String, userId: Long, messageId: Long?) {
    when {
        data == "keyboardMain" -> {
            editMessage(bot, userId, messageId, "Выберите действие:", keyboardMain)
            resetEventState()
        }
    }
}