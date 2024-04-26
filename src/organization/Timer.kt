package organization

import kotlinx.coroutines.*

/*
 * Таймер
 */

class Timer(val event: () -> Unit) {
    private var active = false
    private var lives = true

    // Запустить таймер
    fun start() {
        active = true
    }

    // Остановить таймер
    fun stop() {
        active = false
    }

    // Разрушить таймер
    fun destroy() {
        lives = false
    }

    suspend fun launch() {
        while (lives) {
            if (active) event()

            delay(1000)
        }
    }
}