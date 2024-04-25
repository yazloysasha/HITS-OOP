import java.util.Scanner
import kotlin.concurrent.thread

// Любое животное
open class Animal() {
    var satiety: Int = 0 // Сытость
    open var threshold: Int = 0 // Порог сытости
    open var voice: String = "" // Голос

    val name: String? // Название животного
        get() = this::class.qualifiedName

    val status: String // Статус животного
        get() = if (isHungry()) "HUNGRY" else "WELL-FED"

    // Голодно ли сейчас животное
    fun isHungry(): Boolean {
        return satiety < threshold
    }

    // Подать голос
    fun vote() {
        println("$name speaks: $voice")
    }
}

// Попугай
class Parrot: Animal() {
    override var threshold = 4
    override var voice = "Chirik-chirik"
}

// Волк
class Wolf: Animal() {
    override var threshold = 8
    override var voice = "Gav-gav"
}

// Лев
class Lion: Animal() {
    override var threshold = 16
    override var voice = "R-r-r"
}

// Зоопарк
class Zoo() {
    var commandIsWritten: Boolean = false // Вводится ли сейчас команда

    val parrot = Parrot()
    val wolf = Wolf()
    val lion = Lion()

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
