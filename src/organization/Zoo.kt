package organization

import people.*
import animals.*
import interfaces.IZoo
import kotlin.math.min
import kotlinx.coroutines.*

/*
 * Зоопарк
 */

class Zoo: IZoo {
    private val parrots = mutableListOf<Parrot>()
    private val wolfs = mutableListOf<Wolf>()
    private val lions = mutableListOf<Lion>()

    private val employees = mutableListOf<Employee>()
    private val visitors = mutableListOf<Visitor>()

    override fun checkStatus() {
        print("[Zoo] Parrots: ${parrots.size}")
        print(" | Wolfs: ${wolfs.size}")
        print(" | Lions: ${lions.size}")
        print(" | Employees: ${employees.size}")
        println(" | Visitors: ${visitors.size}")
    }

    override fun helpCommand() {
        println("Available commands:")
        println("* /help - list of commands")
        print("* /add <parrot|wolf|lion|employee|visitor> <name?> <sex?:MALE|FEMALE>")
        println(" <job?> <parrot?|wolf?|lion?> - add new animal or person")
        println("* /remove <parrot|wolf|lion|employee|visitor> <index> - remove animal or person")
        print("* /edit <parrot|wolf|lion|employee|visitor> <index> [<satiety> |")
        println(" <name> <job?> <parrot?|wolf?|lion?>] - edit animal or person")
        print("* /status <zoo|parrot|wolf|lion|employee|visitor> <index?>")
        println(" - view the status of a zoo, animal or person")
        println("* /vote <parrot|wolf|lion> <index> - order an animal voice")
        println("* /end - finish the program")
        println("To start typing a command, press ENTER")
        println("To start the timer again, press ENTER")
    }

    override fun addCommand(args: List<String>) {
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

                val freeAnimal = getFreeAnimal(animal)
                val newEmployee = Employee(name, sex, job, freeAnimal)
                freeAnimal?.employee = newEmployee

                title = "Employee $name"
                employees.add(newEmployee)
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

    override fun removeCommand(args: List<String>) {
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

        var title = ""

        when (type) {
            "parrot" -> {
                if (parrots.isEmpty()) {
                    return println("There are no parrots in the zoo yet...")
                }
                index = min(index, parrots.size - 1)
                title = "Parrot #${index + 1}"
                parrots[index].kill()
                parrots.removeAt(index)
            }
            "wolf" -> {
                if (wolfs.isEmpty()) {
                    return println("There are no wolfs in the zoo yet...")
                }
                index = min(index, wolfs.size - 1)
                title = "Wolf #${index + 1}"
                wolfs[index].kill()
                wolfs.removeAt(index)
            }
            "lion" -> {
                if (lions.isEmpty()) {
                    return println("There are no lions in the zoo yet...")
                }
                index = min(index, lions.size - 1)
                title = "Lion #${index + 1}"
                lions[index].kill()
                lions.removeAt(index)
            }
            "employee" -> {
                if (employees.isEmpty()) {
                    return println("There are no employees in the zoo yet...")
                }
                index = min(index, employees.size - 1)
                title = "Employee #${index + 1} (${employees[index].name})"
                employees[index].kick()
                employees.removeAt(index)
            }
            "visitor" -> {
                if (visitors.isEmpty()) {
                    return println("There are no employees in the zoo yet...")
                }
                index = min(index, visitors.size - 1)
                title = "Visitor #${index + 1} (${visitors[index].name})"
                visitors.removeAt(index)
            }
            else -> return println("Unknown type: $type")
        }

        println("$title kicked out of the zoo...")
    }

    override fun editCommand(args: List<String>) {
        if (args.size < 4) {
            return println("Not enough arguments in the command...")
        }

        val type = args[1]
        var index = args[2].toIntOrNull()

        if (index == null || index < 1) {
            return println("Index can only be a natural number...")
        } else {
            index--
        }

        val satiety = args[3].toIntOrNull()
        if (satiety == null && (type == "parrot" || type == "wolf" || type == "lion")) {
            return println("Index can only be a natural number...")
        }

        var title = ""

        when (type) {
            "parrot" -> {
                if (parrots.isEmpty()) {
                    return println("There are no parrots in the zoo yet...")
                }
                index = min(index, parrots.size - 1)
                title = "Parrot"
                parrots[index].edit(satiety!!)
            }
            "wolf" -> {
                if (wolfs.isEmpty()) {
                    return println("There are no wolfs in the zoo yet...")
                }
                index = min(index, wolfs.size - 1)
                title = "Wolf"
                wolfs[index].edit(satiety!!)
            }
            "lion" -> {
                if (lions.isEmpty()) {
                    return println("There are no lions in the zoo yet...")
                }
                index = min(index, lions.size - 1)
                title = "Lion"
                lions[index].edit(satiety!!)
            }
            "employee" -> {
                if (employees.isEmpty()) {
                    return println("There are no employees in the zoo yet...")
                }
                if (args.size < 6) {
                    return println("Not enough arguments in the command...")
                }
                index = min(index, employees.size - 1)
                title = "Employee"

                val name = args[3]
                val job = args[4]
                val animal = args[5]

                val freeAnimal = getFreeAnimal(animal)
                freeAnimal?.employee = employees[index]

                employees[index].edit(name, job, freeAnimal)
            }
            "visitor" -> {
                if (visitors.isEmpty()) {
                    return println("There are no employees in the zoo yet...")
                }
                index = min(index, visitors.size - 1)
                title = "Visitor"
                visitors[index].edit(args[3])
            }
            else -> return println("Unknown type: $type")
        }

        println("$title #${index + 1} successfully edited")
    }

    override fun statusCommand(args: List<String>) {
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

    override fun voteCommand(args: List<String>) {
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
    override fun endCommand() {
        println("Finishing program...")

        command.destroy()
        timer.destroy()
    }

    override fun getFreeAnimal(type: String): Animal? {
        var animals: List<Animal>? = null

        when (type) {
            "parrot" -> animals = parrots
            "wolf" -> animals = wolfs
            "lion" -> animals = lions
        }

        animals?.forEach { animal ->
            if (animal.employee == null) {
                return animal
            }
        }

        return null
    }

    override fun passAnimals(animals: List<Animal>) {
        var number = 1

        animals.forEach { animal ->
            animal.reduceSatiety(number)
            number++
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
                "edit" -> editCommand(args)
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

            employees.forEachIndexed { index, employee ->
                if (employee.animal != null && employee.animal?.status == "HUNGRY") {
                    print("Employee #${index + 1} (${employee.name}, ${employee.job})")
                    println(" fed ${employee.animal?.name}")
                    employee.animal?.feed()
                }
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
