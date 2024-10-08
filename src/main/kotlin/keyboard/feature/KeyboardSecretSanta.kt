package org.example.keyboard.feature

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.example.keyboard.Keyboard.buttonMain

object KeyboardSecretSanta {

    // Тайный Санта
    private val buttonSecretSantaRegister = InlineKeyboardButton.CallbackData("Участвовать", "secretSantaRegister")
    private val buttonSecretSantaList =
        InlineKeyboardButton.CallbackData("Показать список участников", "secretSantaList")
    private val buttonSecretSantaStart = InlineKeyboardButton.CallbackData("Запуск распределения", "secretSantaStart")
    private val buttonSecretSantaWho = InlineKeyboardButton.CallbackData("Чей я тайный санта?", "secretSantaWho")

    // Меню "Тайный Санта"
    val keyboardSecretSanta = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonSecretSantaRegister),
            listOf(buttonSecretSantaList),
            listOf(buttonSecretSantaStart),
            listOf(buttonMain),
        )
    )

    // В процессе проведения "Тайного Санты"
    val keyboardSecretSantaInProgress = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonSecretSantaWho),
            listOf(buttonMain),
        )
    )
}