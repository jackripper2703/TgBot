package org.example.handlers.feature

import com.github.kotlintelegrambot.Bot
import org.example.handlers.editMessage
import org.example.keyboard.Keyboard.MOCK
import org.example.keyboard.Keyboard.keyboardBack
import org.example.keyboard.feature.KeyboardSecretSanta.keyboardSecretSanta
import org.example.keyboard.feature.KeyboardSecretSanta.keyboardSecretSantaInProgress
import org.example.state.SecretSantaState

fun handleSecretSantaQuery(bot: Bot, data: String, userId: Long, messageId: Long?) {
    val secretSantaInterface = if (SecretSantaState.secretSantaNow) {
        keyboardSecretSantaInProgress
    } else {
        keyboardSecretSanta
    }
    when {
        data == "secretSanta" ->
            editMessage(bot, userId, messageId, "Раздел Тайного Санты", secretSantaInterface)
        data == "secretSantaRegister" ->
            editMessage(bot, userId, messageId, MOCK, keyboardBack)
        data == "secretSantaList" ->
            editMessage(bot, userId, messageId, MOCK, keyboardBack)
        data == "secretSantaStart" -> {
            SecretSantaState.secretSantaNow = true
            editMessage(bot, userId, messageId, MOCK, keyboardBack)
        }
        data == "secretSantaWho" ->
            editMessage(bot, userId, messageId, MOCK, keyboardBack
        )
    }
}