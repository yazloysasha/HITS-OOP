package organization

/*
 * Обработчик команд
 */

class Command(
    val eventStart: () -> Unit,
    val eventEnd: () -> Unit
) {
    private var active = true

    fun launch() {
        while (true) {
            val command: String? = readLine()

            if (active) {
                println("You entered command: ${command}")
                eventEnd()
            } else {
                print("Enter your command: /")
                eventStart()
            }

            active = !active
        }
    }

    init {
        print("Enter your command: /")
    }
}