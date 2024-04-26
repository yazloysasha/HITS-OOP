package organization

/*
 * Обработчик команд
 */

class Command(
    val eventStart: () -> Unit,
    val eventEnd: () -> Unit,
    val destroyTimer: () -> Unit
) {
    private var active = true
    private var lives = true

    // Введите команду
    private fun enterCommand() {
        print("Enter your command: /")
    }

    // Помощь по командам
    private fun helpCommand() {
        println("Available commands:")
        println("* /help - list of commands")
        println("* /end - finish the program")
        println("To start typing a command, press ENTER")
        println("To start the timer again, press ENTER")
    }

    // Завершить программу
    private fun endCommand() {
        println("Finishing program...")

        lives = false
        destroyTimer()
    }

    fun launch() {
        while (lives) {
            val args = readln().split(" ")

            if (active) {
                when (args[0]) {
                    "help" -> helpCommand()
                    "end" -> endCommand()
                }
                if (args[0] != "help") {
                    eventEnd()
                }
            } else {
                enterCommand()
                eventStart()
            }

            if (args[0] != "help") {
                active = !active
            }
        }
    }

    init {
        helpCommand()
    }
}