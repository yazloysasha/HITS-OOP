package organization

import people.*
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

    private val employees = mutableListOf<Employee>()
    private val visitors = mutableListOf<Visitor>()

    // Проверить статус зоопарка
    private fun checkStatus() {
        print("[Zoo] Parrots: ${parrots.size}")
        print(" | Wolfs: ${wolfs.size}")
        print(" | Lions: ${lions.size}")
        print(" | Employees: ${employees.size}")
        println(" | Visitors: ${visitors.size}")
    }

    // Помощь по командам
    private fun helpCommand() {
        println("Available commands:")
        println("* /help - list of commands")
        print("* /add <parrot|wolf|lion|employee|visitor> <name?> <sex?:MALE|FEMALE>")
        println(" <job?> <parrot?|wolf?|lion?> - add new animal or person")
        println("* /remove <parrot|wolf|lion|employee|visitor> <index> - remove animal or person")
        print("* /status <zoo|parrot|wolf|lion|employee|visitor> <index?>")
        println(" - view the status of a zoo, animal or person")
        println("* /vote <parrot|wolf|lion> <index> - order an animal voice")
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
        var title = ""

        when (type) {
            "parrot" -> {
                title = "Parrot"
                parrots.add(Parrot())
            }
            "wolf" -> {
                title = "Wolf"
                wolfs.add(Wolf())
            }
            "lion" -> {
                title = "Lion"
                lions.add(Lion())
            }
            "employee" -> {
                if (args.size < 6) {
                    return println("Not enough arguments in the command...")
                }

                val name = args[2]
                val sex = args[3]
                val job = args[4]
                val animal = args[5]

                title = "Employee $name"
                employees.add(Employee(name, sex, job, animal))
            }
            "visitor" -> {
                if (args.size < 4) {
                    return println("Not enough arguments in the command...")
                }

                val name = args[2]
                val sex = args[3]

                title = "Visitor $name"
                visitors.add(Visitor(name, sex))
            }
            else -> return println("Unknown type: $type")
        }

        println("$title was invited to the zoo")
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
                index = min(index, parrots.size - 1)
                name = "Parrot"
                parrots.removeAt(index)
            }
            "wolf" -> {
                if (wolfs.isEmpty()) {
                    return println("There are no wolfs in the zoo yet...")
                }
                index = min(index, wolfs.size - 1)
                name = "Wolf"
                wolfs.removeAt(index)
            }
            "lion" -> {
                if (lions.isEmpty()) {
                    return println("There are no lions in the zoo yet...")
                }
                index = min(index, lions.size - 1)
                name = "Lion"
                lions.removeAt(index)
            }
            "employee" -> {
                if (employees.isEmpty()) {
                    return println("There are no employees in the zoo yet...")
                }
                index = min(index, employees.size - 1)
                name = "Employee ${employees[index].name}"
                employees.removeAt(index)
            }
            "visitor" -> {
                if (visitors.isEmpty()) {
                    return println("There are no employees in the zoo yet...")
                }
                index = min(index, visitors.size - 1)
                name = "Visitor ${visitors[index].name}"
                visitors.removeAt(index)
            }
            else -> return println("Unknown type: $type")
        }

        println("$name (${index + 1}) kicked out of the zoo...")
    }

    // Проверить статус объекта
    private fun statusCommand(args: List<String>) {
        if (args.size < 2) {
            return println("Not enough arguments in the command...")
        }

        val type = args[1]

        if (type == "zoo") {
            return checkStatus()
        }

        if (args.size < 3) {
            return println("Not enough arguments in the command...")
        }

        var index = args[2].toIntOrNull()

        if (index == null || index < 1) {
            return println("Index can only be a natural number...")
        } else {
            index--
        }

        when (type) {
            "parrot" -> {
                if (parrots.isEmpty()) {
                    return println("There are no parrots in the zoo yet...")
                }
                index = min(index, parrots.size - 1)
                parrots[index].checkStatus()
            }
            "wolf" -> {
                if (wolfs.isEmpty()) {
                    return println("There are no wolfs in the zoo yet...")
                }
                index = min(index, wolfs.size - 1)
                wolfs[index].checkStatus()
            }
            "lion" -> {
                if (lions.isEmpty()) {
                    return println("There are no lions in the zoo yet...")
                }
                index = min(index, lions.size - 1)
                lions[index].checkStatus()
            }
            "employee" -> {
                if (employees.isEmpty()) {
                    return println("There are no employees in the zoo yet...")
                }
                index = min(index, employees.size - 1)
                employees[index].checkStatus()
            }
            "visitor" -> {
                if (visitors.isEmpty()) {
                    return println("There are no visitors in the zoo yet...")
                }
                index = min(index, visitors.size - 1)
                visitors[index].checkStatus()
            }
            else -> return println("Unknown type: $type")
        }
    }

    // Приказать животному подать голос
    private fun voteCommand(args: List<String>) {
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

        when (type) {
            "parrot" -> {
                if (parrots.isEmpty()) {
                    return println("There are no parrots in the zoo yet...")
                }
                index = min(index, parrots.size - 1)
                parrots[index].vote()
            }
            "wolf" -> {
                if (wolfs.isEmpty()) {
                    return println("There are no wolfs in the zoo yet...")
                }
                index = min(index, wolfs.size - 1)
                wolfs[index].vote()
            }
            "lion" -> {
                if (lions.isEmpty()) {
                    return println("There are no lions in the zoo yet...")
                }
                index = min(index, lions.size - 1)
                lions[index].vote()
            }
            else -> return println("Unknown type: $type")
        }
    }

    // Завершить программу
    private fun endCommand() {
        println("Finishing program...")

        command.destroy()
        timer.destroy()
    }

    // Пройти по виду животного
    private fun passAnimals(animals: List<Animal>) {
        var number = 1

        animals.forEach { animal ->
            animal.reduceSatiety(number)
            number++
        }
    }

    // Сотрудники проходят по животным и кормят их
    private fun feedAnimals(employee: Employee, employeeNumber: Int, animals: List<Animal>) {
        var animalNumber = 1

        animals.forEach { animal ->
            if (animal.status == "HUNGRY") {
                print("Employee #$employeeNumber (${employee.name}, ${employee.job})")
                println(" fed ${animal.name} #$animalNumber")
                return animal.feed()
            }
            animalNumber++
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
                "status" -> statusCommand(args)
                "vote" -> voteCommand(args)
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

            var number = 1

            employees.forEach { employee ->
                when (employee.animal) {
                    "parrot" -> feedAnimals(employee, number, parrots)
                    "wolf" -> feedAnimals(employee, number, wolfs)
                    "lion" -> feedAnimals(employee, number, lions)
                }
                number++
            }
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
