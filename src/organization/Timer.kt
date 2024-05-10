package organization

import interfaces.IUtil
import kotlinx.coroutines.*

/*
 * Таймер
 */

class Timer(val event: () -> Unit): IUtil {
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

    override fun destroy() {
        lives = false
    }

    override suspend fun launch() {
        while (lives) {
            if (active) event()

            delay(1000)
        }
    }
}