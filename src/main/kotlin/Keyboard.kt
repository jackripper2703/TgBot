package org.example

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton

object Keyboards {
    // Главное меню = main
    val keyboard00 = InlineKeyboardMarkup.create(
        listOf(
            listOf(InlineKeyboardButton.CallbackData("Написать в чат через 5 минут", "chat")),
            listOf(InlineKeyboardButton.CallbackData("Мероприятия", "events")),
            listOf(InlineKeyboardButton.CallbackData("Список желаний", "wishList")),
            listOf(InlineKeyboardButton.CallbackData("Раздел голосования", "vote")),
            listOf(InlineKeyboardButton.CallbackData("Запуск тайного Санты", "secretSanta")),
        )
    )
    // Мероприятия = events
    val keyboard01 = InlineKeyboardMarkup.create(
        listOf(
            listOf(InlineKeyboardButton.CallbackData("Актуальные мероприятия", "events_active")),
            listOf(InlineKeyboardButton.CallbackData("Создать новое мероприятие", "events_create")),
            listOf(InlineKeyboardButton.CallbackData("Отменить мероприятие", "events_cancel")),
            listOf(InlineKeyboardButton.CallbackData("Главное меню", "main")),
        )
    )
    // Список желаний = wishList
    val keyboard02 = InlineKeyboardMarkup.create(
        listOf(
            listOf(InlineKeyboardButton.CallbackData("Новое желание", "wishList_create")),
            listOf(InlineKeyboardButton.CallbackData("Список желаний", "wishList_list")),
            listOf(InlineKeyboardButton.CallbackData("Удалить желание", "wishList_delete")),
            listOf(InlineKeyboardButton.CallbackData("Желание других участников", "wishList_other")),
            listOf(InlineKeyboardButton.CallbackData("Главное меню", "main")),
        )
    )
    // Запуск голосования = vote
    val keyboard03 = InlineKeyboardMarkup.create(
            listOf(InlineKeyboardButton.CallbackData("Создать голосование", "vote_create")),
            listOf(InlineKeyboardButton.CallbackData("Главное меню", "main")),

    )
    // Завершить голосования = vote
    val keyboard030 = InlineKeyboardMarkup.create(
            listOf(InlineKeyboardButton.CallbackData("Закончить голосование", "vote_finish")),
            listOf(InlineKeyboardButton.CallbackData("Главное меню", "main")),

        )
    // Запуск тайного Санты = secretSanta
    val keyboard04 = InlineKeyboardMarkup.create(
        listOf(
            listOf(InlineKeyboardButton.CallbackData("Участвовать", "secretSanta_register")),
            listOf(InlineKeyboardButton.CallbackData("Показать список участников", "secretSanta_list")),
            listOf(InlineKeyboardButton.CallbackData("Запуск рапределения", "secretSanta_start")),
            listOf(InlineKeyboardButton.CallbackData("Главное меню", "main")),
        )
    )
    // Запуск тайного Санты = secretSanta (Запущенное мероприятие)
    val keyboard040 = InlineKeyboardMarkup.create(
        listOf(
            listOf(InlineKeyboardButton.CallbackData("Чей я тайный санты?", "secretSanta_who")),
            listOf(InlineKeyboardButton.CallbackData("Главное меню", "main")),
        )
    )
}