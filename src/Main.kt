import animals.Lion
import animals.Wolf
import animals.Parrot
import kotlin.concurrent.thread

// Зоопарк
class Zoo() {
    private var commandIsWritten: Boolean = false // Вводится ли сейчас команда

    private val parrot = Parrot()
    private val wolf = Wolf()
    private val lion = Lion()

    // Запустить обработчик команд
    fun startCommandHandler() {
        thread {
            while (true) {
                val command: String? = readLine()

                if (commandIsWritten) {
                    println("You entered command: $command")
                } else {
                    print("Enter your command: /")
                }

                commandIsWritten = !commandIsWritten
            }
        }
    }

    // Запустить таймер
    fun startTimer() {
        thread {
            while (true) {
                if (!commandIsWritten) {
                    parrot.vote()
                    wolf.vote()
                    lion.vote()
                }

                Thread.sleep(1000)
            }
        }
    }
}

fun main() {
    val zoo = Zoo()

    zoo.startCommandHandler()
    zoo.startTimer()
}
