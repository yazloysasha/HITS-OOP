package organization

import animals.*
import kotlin.math.min
import kotlinx.coroutines.*

/*
 * Зоопарк
 */

class Zoo {
    private val parrots = mutableListOf<Parrot>()
    private val wolfs = mutableListOf<Wolf>()
    private val lions = mutableListOf<Lion>()

    // Помощь по командам
    private fun helpCommand() {
        println("Available commands:")
        println("* /help - list of commands")
        println("* /add <parrot|wolf|lion> - add new animal or person")
        println("* /remove <parrot|wolf|lion> <index> - remove animal or person")
        println("* /end - finish the program")
        println("To start typing a command, press ENTER")
        println("To start the timer again, press ENTER")
    }

    // Добавить новый объект
    private fun addCommand(args: List<String>) {
        if (args.size < 2) {
            return println("Not enough arguments in the command...")
        }

        val type = args[1]
        var name = ""

        when (type) {
            "parrot" -> {
                name = "Parrot"
                parrots.add(Parrot())
            }
            "wolf" -> {
                name = "Wolf"
                wolfs.add(Wolf())
            }
            "lion" -> {
                name = "Lion"
                lions.add(Lion())
            }
            else -> return println("Unknown type: $type")
        }

        println("$name was invited to the zoo")
    }

    // Удалить объект
    private fun removeCommand(args: List<String>) {
        if (args.size < 3) {
            return println("Not enough arguments in the command...")
        }

        val type = args[1]
        var index = args[2].toIntOrNull()

        if (index == null || index < 1) {
            return println("Index can only be a natural number...")
        } else {
            index--
        }

        var name = ""

        when (type) {
            "parrot" -> {
                if (parrots.isEmpty()) {
                    return println("There are no parrots in the zoo yet...")
                }
                name = "Parrot"
                index = min(index, parrots.size - 1)
                parrots.removeAt(index)
            }
            "wolf" -> {
                if (wolfs.isEmpty()) {
                    return println("There are no wolfs in the zoo yet...")
                }
                name = "Wolf"
                index = min(index, wolfs.size - 1)
                wolfs.removeAt(index)
            }
            "lion" -> {
                if (lions.isEmpty()) {
                    return println("There are no lions in the zoo yet...")
                }
                name = "Lion"
                index = min(index, lions.size - 1)
                lions.removeAt(index)
            }
            else -> return println("Unknown type: $type")
        }

        println("$name (${index + 1}) kicked out of the zoo...")
    }

    // Завершить программу
    private fun endCommand() {
        println("Finishing program...")

        command.destroy()
        timer.destroy()
    }

    // Пройти по виду животного
    private fun passAnimals(animals: List<Animal>) {
        var index = 0

        animals.forEach { animal ->
            animal.reduceSatiety()

            if (animal.status == "HUNGRY") {
                println("${animal.name} (${index + 1}) is hungry!")
            }

            index++
        }
    }

    private val command = Command(
        // Событие, которое нужно запустить перед отправкой команды
        fun() {
            timer.stop()
        },

        // Событие, которое нужно запустить после отправки команды
        fun() {
            println("The zoo is alive again!")
            timer.start()
        },

        // Событие для обработки команд
        fun(args: List<String>) {
            when (args[0]) {
                "help" -> helpCommand()
                "add" -> addCommand(args)
                "remove" -> removeCommand(args)
                "end" -> endCommand()
            }
        }
    )

    private val timer = Timer(
        // Событие, происходящее каждый тик
        fun() {
            passAnimals(parrots)
            passAnimals(wolfs)
            passAnimals(lions)
        }
    )

    suspend fun launch() = coroutineScope {
        launch { command.launch() }
        launch { timer.launch() }
    }

    init {
        helpCommand()
    }
}