package organization

import animals.Lion
import animals.Wolf
import animals.Parrot
import kotlinx.coroutines.*

class Zoo {
    private val parrot = Parrot()
    private val wolf = Wolf()
    private val lion = Lion()

    private val command = Command(
        // Событие, которое нужно запустить перед отправкой команды
        fun() {
            timer.stop()
        },

        // Событие, которое нужно запустить после отправки команды
        fun() {
            timer.start()
        }
    )

    private val timer = Timer(
        // Событие, происходящее каждый тик
        fun() {
            parrot.vote()
            wolf.vote()
            lion.vote()
        }
    )

    suspend fun launch() = coroutineScope {
        launch { command.launch() }
        launch { timer.launch() }
    }
}