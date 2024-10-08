package org.example.handlers

import org.example.store.EventsStore
import org.example.state.EventState
import org.example.models.Event
import org.example.state.SecretSantaState
import org.example.models.User
import org.example.store.UsersStore

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import org.example.helpers.ConfigHelper
import org.example.keyboard.Keyboard.keyboardMain

object MessageHandler {

    // Получаем chat_id из конфигурации
    private val chatId =
        ConfigHelper.getProperty("chat_id")?.toLongOrNull() ?: error("Chat ID is not configured properly.")

    // Обрабатываем текстовые сообщения
    fun handleTextMessage(bot: Bot, message: Message) {
        val command = message.text ?: return
        val username = message.from?.username ?: "Unknown User"

        logCommand(username, command)

        if (EventState.isWaitingForDescription) {
            handleEventDescription(bot, message)
            return
        }

        if (message.chat.type == "private") {
            handlePrivateCommands(bot, message, command)
        }
    }

    // Логирование команды
    private fun logCommand(username: String, command: String) {
        println("[$username] Received command: $command")
    }

    // Обработка ввода описания мероприятия
    private fun handleEventDescription(bot: Bot, message: Message) {
        val description = message.text ?: run {
            bot.sendMessage(
                chatId = ChatId.fromId(message.chat.id), text = "Ошибка: Описание мероприятия не может быть пустым."
            )
            return
        }
        val selectedDate = EventState.selectedDate ?: run {
            bot.sendMessage(
                chatId = ChatId.fromId(message.chat.id), text = "Ошибка: Не выбрана дата мероприятия."
            )
            return
        }

        // Создание нового мероприятия и уведомление
        createAndNotifyEvent(bot, message, selectedDate, description)

        // Сброс состояния
        EventState.isWaitingForDescription = false
        EventState.selectedDate = null
    }

    // Создание мероприятия и уведомление пользователей
    private fun createAndNotifyEvent(bot: Bot, message: Message, _date: String, _description: String) {
        EventsStore.events.add(Event(date = _date, description = _description))
        // Уведомляем пользователя о создании мероприятия
        bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = "Описание мероприятия: \n\n\"$_description\" \n\nУспешно сохранено.\n\nВыберите действие:",
            replyMarkup = keyboardMain // Здесь добавляем клавиатуру
        )

        // Уведомляем группу о создании мероприятия
        bot.sendMessage(
            chatId = ChatId.fromId(chatId), text = "Создано новое мероприятие:\n\n $_description \n\nДата: $_date"
        )
    }

    // Обработка команд в приватном чате
    private fun handlePrivateCommands(bot: Bot, message: Message, command: String) {
        when (command) {
            "/start" -> {
                handleStartCommand(bot, message) // Вызов метода для обработки /start
            }

            "/secretSantaRestart" -> SecretSantaState.restartSecretSanta(bot, message.chat.id)
            else -> sendUnknownCommandResponse(bot, message.chat.id)
        }
    }

    // Обработка команды /start
    private fun handleStartCommand(bot: Bot, message: Message) {
        val userId = message.from!!.id
        val username = message.from!!.username ?: "Unknown User"

        // Проверьте, существует ли пользователь уже
        if (UsersStore.users.none { it.userId == userId }) {
            // Создание нового пользователя
            val newUser = User(userId = userId, username = username)

            // Добавление пользователя в UserStore
            UsersStore.users.add(newUser)

            bot.sendMessage(
                chatId = ChatId.fromId(message.chat.id), // Преобразуем Long в ChatId
                text = "Добро пожаловать, $username!"
            )
        } else {
            bot.sendMessage(
                chatId = ChatId.fromId(message.chat.id), // Преобразуем Long в ChatId
                text = "С возвращением, $username!"
            )
        }

        // После приветственного сообщения отправляем главную клавиатуру
        sendMainKeyboard(bot, message.chat.id)
    }

    // Отправка главного меню
    private fun sendMainKeyboard(bot: Bot, chatId: Long) {
        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = "Выберите действие:",
            replyMarkup = keyboardMain // Здесь добавляем клавиатуру
        )
    }

    // Ответ на неизвестную команду
    private fun sendUnknownCommandResponse(bot: Bot, chatId: Long) {
        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = "Неизвестная команда. Пожалуйста, выберите действие из меню.",
            replyMarkup = keyboardMain
        )
    }
}
