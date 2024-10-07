package org.example

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton

object Keyboards {
    /*
    1. Удаление будет реализовываться через сущности в списках
    2. Нужно разобраться, как создаются динамичные списки, например список участников тайного санта
    3. Голосовалка будет приходить с постом о мероприятии
     */

    val buttonMain = InlineKeyboardButton.CallbackData("Главное меню", "keyboardMain")
    
    val buttonEvents = InlineKeyboardButton.CallbackData("Мероприятия", "events")
    val buttonEventsList = InlineKeyboardButton.CallbackData("Список мероприятий", "eventsList")
    val buttonEventsCreate = InlineKeyboardButton.CallbackData("Создать новое мероприятие", "eventCreate")
    val buttonEventCreateDate = InlineKeyboardButton.CallbackData("12.12.2020", "eventDate")
    
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

    val keyboardEventDate = InlineKeyboardMarkup.create(
        listOf(
            listOf(buttonEventCreateDate),
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
}