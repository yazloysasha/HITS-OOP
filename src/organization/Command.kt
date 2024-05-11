package organization

import interfaces.IUtil
import interfaces.IZooCommands

/*
 * Обработчик команд
 */

class Command(private val zoo: IZooCommands): IUtil {
    private var active = true
    private var lives = true

    override fun destroy() {
        lives = false
    }

    override suspend fun launch() {
        while (lives) {
            val line = readln()
            if (line.isEmpty()) {
                if (active) zoo.startCommand() else zoo.stopCommand()
                active = !active
            }

            val args = line.split(" ")
            when (args[0]) {
                "help" -> zoo.helpCommand()
                "add" -> zoo.addCommand(args)
                "remove" -> zoo.removeCommand(args)
                "edit" -> zoo.editCommand(args)
                "status" -> zoo.statusCommand(args)
                "vote" -> zoo.voteCommand(args)
                "end" -> zoo.endCommand()
            }
        }
    }
}