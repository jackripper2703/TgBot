package org.example.keyboard.feature

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.example.keyboard.Keyboard.buttonMain
import org.example.keyboard.Keyboard.createKeyboardFromRange
import org.example.store.EventsStore

object KeyboardEvent {

    // Мероприятия
    private val buttonEventsList = InlineKeyboardButton.CallbackData("Список мероприятий", "eventsList")
    private val buttonEventsCreate = InlineKeyboardButton.CallbackData("Создать новое мероприятие", "selectYear")

    // Меню "Мероприятия"
    val keyboardEvents = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonEventsList),
            listOf(buttonEventsCreate),
            listOf(buttonMain),
        )
    )

    // Генерация клавиатуры с кнопками для выбора года
    fun createYearKeyboard(): InlineKeyboardMarkup {
        return createKeyboardFromRange(2024..2026, "selectYear")
    }

    // Генерация клавиатуры с кнопками для выбора месяца
    fun createMonthKeyboard(year: Int): InlineKeyboardMarkup {
        return createKeyboardFromRange(1..12, "selectMonth:$year")
    }

    // Генерация клавиатуры с кнопками для выбора дня
    fun createDayKeyboard(year: Int, month: Int): InlineKeyboardMarkup {
        val daysInMonth = getDaysInMonth(year, month)
        return createKeyboardFromRange(1..daysInMonth, "selectDay:$year:$month")
    }

    // Генерация клавиатуры с кнопками для событий
    fun createEventsKeyboard(): InlineKeyboardMarkup {
        val buttons = EventsStore.events.map { event ->
            InlineKeyboardButton.CallbackData(
                text = event.name,
                callbackData = "eventId:${event.id}"
            )
        }.chunked(2)

        return InlineKeyboardMarkup.create(buttons)
    }

    // Определение количества дней в месяце
    fun getDaysInMonth(year: Int, month: Int): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (year % 4 == 0) 29 else 28
            else -> 30
        }
    }
}