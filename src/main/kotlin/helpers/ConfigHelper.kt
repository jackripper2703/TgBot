package org.example.helpers

import java.io.FileInputStream
import java.util.*

object ConfigHelper {
    private const val CONFIG_FILE_PATH = "src/main/resources/config.properties"
    private val properties: Properties = Properties()

    init {
        loadProperties()
    }

    private fun loadProperties() {
        FileInputStream(CONFIG_FILE_PATH).use { input ->
            properties.load(input)
        }
    }

    fun getProperty(key: String): String? {
        return properties.getProperty(key)
    }

    var token = ConfigHelper.getProperty("bot.token")
        ?: throw IllegalArgumentException("Токен бота не найден в конфигурационном файле.")
}
