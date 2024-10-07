package org.example.Keyboards

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton

object Keyboards {

    val buttonMain = InlineKeyboardButton.CallbackData("Главное меню", "keyboardMain")

    val buttonEvents = InlineKeyboardButton.CallbackData("Мероприятия", "events")
    val buttonEventsList = InlineKeyboardButton.CallbackData("Список мероприятий", "eventsList")
    val buttonEventsCreate = InlineKeyboardButton.CallbackData("Создать новое мероприятие", "selectYear")

    val buttonSecretSanta = InlineKeyboardButton.CallbackData("Тайный Санта", "secretSanta")
    val buttonSecretSantaRegister = InlineKeyboardButton.CallbackData("Участвовать", "secretSantaRegister")
    val buttonSecretSantaList = InlineKeyboardButton.CallbackData("Показать список участников", "secretSantaList")
    val buttonSecretSantaStart = InlineKeyboardButton.CallbackData("Запуск рапределения", "secretSantaStart")
    val buttonSecretSantaWho = InlineKeyboardButton.CallbackData("Чей я тайный санты?", "secretSantaWho")

    val buttonWishList = InlineKeyboardButton.CallbackData("Желания", "wishList")
    val buttonWishListCreate = InlineKeyboardButton.CallbackData("Новое желание", "wishListCreate")
    val buttonWishListList = InlineKeyboardButton.CallbackData("Список желаний", "wishListList")
    val buttonWishListOther = InlineKeyboardButton.CallbackData("Желание других участников", "wishListOther")

    val keyboardBack = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonMain),
        )
    )

    val keyboardMain = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonEvents),
            listOf(buttonSecretSanta),
            listOf(buttonWishList),
        )
    )

    val keyboardEvents = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonEventsList),
            listOf(buttonEventsCreate),
            listOf(buttonMain),
        )
    )

    val keyboardWishList = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonWishListCreate),
            listOf(buttonWishListList),
            listOf(buttonWishListOther),
            listOf(buttonMain),
        )
    )

    val keyboardSecretSanta = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonSecretSantaRegister),
            listOf(buttonSecretSantaList),
            listOf(buttonSecretSantaStart),
            listOf(buttonMain),
        )
    )

    val keyboardSecretSantaInProgress = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonSecretSantaWho),
            listOf(buttonMain),
        )
    )

    // Создаем клавиатуру для выбора года
    fun createYearKeyboard(): InlineKeyboardMarkup {
        val buttons = (2024..2026).map { year ->
            InlineKeyboardButton.CallbackData(text = "$year", callbackData = "selectYear:$year")
        }.chunked(3)
        return InlineKeyboardMarkup.create(buttons)
    }

    // Создаем клавиатуру для выбора месяца
    fun createMonthKeyboard(year: Int): InlineKeyboardMarkup {
        val buttons = (1..12).map { month ->
            InlineKeyboardButton.CallbackData(text = "$month", callbackData = "selectMonth:$year:$month")
        }.chunked(3)  // Разбиваем по 3 кнопки в ряд

        return InlineKeyboardMarkup.create(buttons)
    }

    // Создаем клавиатуру для выбора дня
    fun createDayKeyboard(year: Int, month: Int): InlineKeyboardMarkup {
        val daysInMonth = when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (year % 4 == 0) 29 else 28
            else -> 30
        }

        val buttons = (1..daysInMonth).map { day ->
            InlineKeyboardButton.CallbackData(text = "$day", callbackData = "selectDay:$year:$month:$day")
        }.chunked(7)  // Разбиваем по 7 кнопок в ряд (неделя)

        return InlineKeyboardMarkup.create(buttons)
    }
}