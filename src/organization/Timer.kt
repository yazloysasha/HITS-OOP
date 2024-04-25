package organization

import kotlinx.coroutines.*

/*
 * Таймер
 */

class Timer(val event: () -> Unit) {
    private var active = false

    fun start() {
        active = true
    }

    fun stop() {
        active = false
    }

    suspend fun launch() {
        while (true) {
            if (active) event()

            delay(1000)
        }
    }
}