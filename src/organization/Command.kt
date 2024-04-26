package organization

/*
 * Обработчик команд
 */

class Command(
    val eventStart: () -> Unit,
    val eventEnd: () -> Unit,
    val eventCommand: (List<String>) -> Unit
) {
    private var active = true
    private var lives = true

    // Разрушить обработчик команд
    fun destroy() {
        lives = false
    }

    fun launch() {
        while (lives) {
            val args = readln().split(" ")

            if (active) {
                eventCommand(args)
                if (args[0] != "help" && args[0] != "end") {
                    eventEnd()
                }
            } else {
                print("Enter your command: /")
                eventStart()
            }

            if (args[0] != "help") {
                active = !active
            }
        }
    }
}