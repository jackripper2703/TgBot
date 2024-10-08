package org.example.Keyboards

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.example.store.EventsStore

object Keyboards {

    // Общая кнопка возврата в главное меню
    val buttonMain = InlineKeyboardButton.CallbackData("Главное меню", "keyboardMain")

    // Мероприятия
    private val buttonEvents = InlineKeyboardButton.CallbackData("Мероприятия", "events")
    private val buttonEventsList = InlineKeyboardButton.CallbackData("Список мероприятий", "eventsList")
    private val buttonEventsCreate = InlineKeyboardButton.CallbackData("Создать новое мероприятие", "selectYear")

    // Тайный Санта
    private val buttonSecretSanta = InlineKeyboardButton.CallbackData("Тайный Санта", "secretSanta")
    private val buttonSecretSantaRegister = InlineKeyboardButton.CallbackData("Участвовать", "secretSantaRegister")
    private val buttonSecretSantaList = InlineKeyboardButton.CallbackData("Показать список участников", "secretSantaList")
    private val buttonSecretSantaStart = InlineKeyboardButton.CallbackData("Запуск распределения", "secretSantaStart")
    private val buttonSecretSantaWho = InlineKeyboardButton.CallbackData("Чей я тайный санта?", "secretSantaWho")

    // Желания
    private val buttonWishList = InlineKeyboardButton.CallbackData("Желания", "wishList")
    private val buttonWishListCreate = InlineKeyboardButton.CallbackData("Новое желание", "wishListCreate")
    private val buttonWishListList = InlineKeyboardButton.CallbackData("Список желаний", "wishListList")
    private val buttonWishListOther = InlineKeyboardButton.CallbackData("Желания других участников", "wishListOther")

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

    // Меню "Мероприятия"
    val keyboardEvents = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonEventsList),
            listOf(buttonEventsCreate),
            listOf(buttonMain),
        )
    )

    // Меню "Желания"
    val keyboardWishList = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonWishListCreate),
            listOf(buttonWishListList),
            listOf(buttonWishListOther),
            listOf(buttonMain),
        )
    )

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
                text = event.date,
                callbackData = "eventDate:${event.id}" // Данные для callback
            )
        }.chunked(2) // Два события в строке

        return InlineKeyboardMarkup.create(buttons)
    }

    // Вспомогательные функции:

    // Создание клавиатуры с одной кнопкой
    private fun createSingleButtonKeyboard(button: InlineKeyboardButton): InlineKeyboardMarkup {
        return InlineKeyboardMarkup.create(listOf(listOf(button)))
    }

    // Генерация клавиатуры на основе диапазона чисел
    private fun createKeyboardFromRange(range: IntRange, callbackPrefix: String): InlineKeyboardMarkup {
        val buttons = range.map { value ->
            InlineKeyboardButton.CallbackData(
                text = "$value",
                callbackData = "$callbackPrefix:$value"
            )
        }.chunked(3)  // Кнопки по 3 в строке (можно изменить по желанию)

        return InlineKeyboardMarkup.create(buttons)
    }

    // Определение количества дней в месяце
    private fun getDaysInMonth(year: Int, month: Int): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (year % 4 == 0) 29 else 28
            else -> 30
        }
    }
}
