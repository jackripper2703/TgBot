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

    val chatId =
        ConfigHelper.getProperty("chat_id")?.toLongOrNull() ?: error("Chat ID is not configured properly.")

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

    private fun logCommand(username: String, command: String) {
        println("[$username] Received command: $command")
    }

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

        createAndNotifyEvent(bot, message, selectedDate, description)

        EventState.isWaitingForDescription = false
        EventState.selectedDate = null
    }

    private fun createAndNotifyEvent(bot: Bot, message: Message, _date: String, _description: String) {
        EventsStore.events.add(Event(date = _date, description = _description))

        bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = """
                    |Описание мероприятия:
                    |
                    |$_description
                    |
                    |Дата: $_date
                    |
                    |Успешно сохранено.
                    |
                    |Выберите действие:
                    """.trimMargin(),
            replyMarkup = keyboardMain
        )

        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = """
                    |Новое мероприятие:
                    |
                    |$_description
                    |
                    |Дата: $_date
                    |
                    """.trimMargin()
        )
    }

    private fun handlePrivateCommands(bot: Bot, message: Message, command: String) {
        when (command) {
            "/start" -> { handleStartCommand(bot, message) }
            "/secretSantaRestart" -> SecretSantaState.restartSecretSanta(bot, message.chat.id)
            else -> sendUnknownCommandResponse(bot, message.chat.id)
        }
    }

    private fun handleStartCommand(bot: Bot, message: Message) {
        val userId = message.from!!.id
        val username = message.from!!.username ?: "Unknown User"

        if (UsersStore.users.none { it.userId == userId }) {

            val newUser = User(userId = userId, username = username)

            UsersStore.users.add(newUser)

            bot.sendMessage(
                chatId = ChatId.fromId(message.chat.id),
                text = "Добро пожаловать, $username!"
            )
        } else {
            bot.sendMessage(
                chatId = ChatId.fromId(message.chat.id),
                text = "С возвращением, $username!"
            )
        }
        sendMainKeyboard(bot, message.chat.id)
    }

    private fun sendMainKeyboard(bot: Bot, chatId: Long) {
        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = "Выберите действие:",
            replyMarkup = keyboardMain
        )
    }

    private fun sendUnknownCommandResponse(bot: Bot, chatId: Long) {
        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = "Неизвестная команда. Пожалуйста, выберите действие из меню.",
            replyMarkup = keyboardMain
        )
    }
}
