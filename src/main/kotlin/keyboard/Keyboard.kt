package org.example.keyboard

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton

object Keyboard {

    val MOCK = "Пока еще не сделал, держу как макет"

    // Общая кнопка возврата в главное меню
    val buttonMain = InlineKeyboardButton.CallbackData("Главное меню", "keyboardMain")

    val buttonEvents = InlineKeyboardButton.CallbackData("Мероприятия", "events")
    val buttonSecretSanta = InlineKeyboardButton.CallbackData("Тайный Санта", "secretSanta")
    val buttonWishList = InlineKeyboardButton.CallbackData("Желания", "wishList")

    // Клавиатура с кнопкой "Главное меню"
    val keyboardBack = createSingleButtonKeyboard(buttonMain)

    // Главное меню
    val keyboardMain = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonEvents),
            listOf(buttonSecretSanta),
            listOf(buttonWishList),
        )
    )

    // Создание клавиатуры с одной кнопкой
    fun createSingleButtonKeyboard(button: InlineKeyboardButton): InlineKeyboardMarkup {
        return InlineKeyboardMarkup.create(listOf(listOf(button)))
    }

    // Генерация клавиатуры на основе диапазона чисел
    fun createKeyboardFromRange(range: IntRange, callbackPrefix: String): InlineKeyboardMarkup {
        val buttons = range.map { value ->
            InlineKeyboardButton.CallbackData(
                text = "$value",
                callbackData = "$callbackPrefix:$value"
            )
        }.chunked(if (range.count() == 12) 3 else 7)

        return InlineKeyboardMarkup.create(buttons)
    }
}
