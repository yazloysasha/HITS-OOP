package organization

import interfaces.IUtil

/*
 * Обработчик команд
 */

class Command(
    val eventStart: () -> Unit,
    val eventEnd: () -> Unit,
    val eventCommand: (List<String>) -> Unit
): IUtil {
    private var active = true
    private var lives = true

    override fun destroy() {
        lives = false
    }

    override suspend fun launch() {
        while (lives) {
            val line = readln()
            if (line.isEmpty()) {
                if (active) eventEnd() else eventStart()
                active = !active
            }

            val args = line.split(" ")
            eventCommand(args)
        }
    }
}