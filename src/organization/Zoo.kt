package organization

import people.*
import animals.*
import interfaces.*
import kotlinx.coroutines.*
import kotlin.random.Random

/*
 * Зоопарк
 */

class Zoo : IZooStorage, IZooCommands, ITick {
    override val entities = mutableListOf<Entity>()

    override val enclosures: List<Enclosure>
        get() = getListOfEntities()
    override val employees: List<Employee>
        get() = getListOfEntities()
    override val visitors: List<Visitor>
        get() = getListOfEntities()

    override val shop = Shop()

    // В команде недостаточно аргументов
    private fun notEnoughArguments() {
        println("Not enough arguments in the command...")
    }

    // Получить элемент по ID
    private inline fun <reified T> getEntityById(id: String): T? {
        return entities.find {
            id == it.id && T::class.isInstance(it)
        } as T?
    }

    // Получить количество элементов определённого типа
    private inline fun <reified T> getCountOfEntities(): Int {
        var count = 0

        entities.forEach {
            if (T::class.isInstance(it)) count++
        }

        return count
    }

    // Получить список из элементов определённого типа
    private inline fun <reified T> getListOfEntities(): List<T> {
        return entities.filterIsInstance<T>()
    }

    // Получить доступный для заселения вольер
    private fun getAvailableEnclosure(name: String): Enclosure? {
        return entities.find {
            Enclosure::class.isInstance(it) && (it as Enclosure).addingIsAvailable(name)
        } as Enclosure?
    }

    // Создать 16 случайных животных и распределить по вольерам
    private fun createAnimals() {
        val names = arrayOf("Parrot", "Wolf", "Lion")

        for (i in 0..<16) {
            val name = names[Random.nextInt(names.size)]
            var enclosure = getAvailableEnclosure(name)

            if (enclosure == null) {
                enclosure = Enclosure()
                entities.add(enclosure)
            }

            val animal = when (name) {
                "Parrot" -> Parrot()
                "Wolf" -> Wolf()
                "Lion" -> Lion()
                else -> Animal()
            }

            enclosure.addAnimal(animal)
            entities.add(animal)
        }
    }

    // Проверить статус зоопарка
    private fun checkStatus() {
        val enclosuresCount = getCountOfEntities<Enclosure>()
        val animalsCount = getCountOfEntities<Animal>()
        val employeesCount = getCountOfEntities<Employee>()
        val visitorsCount = getCountOfEntities<Visitor>()

        print("[Zoo] Enclosures: $enclosuresCount")
        print(" | Animals: $animalsCount")
        print(" | Employees: $employeesCount")
        println(" | Visitors: $visitorsCount")
    }

    override fun helpCommand() {
        println("Available commands:")
        println("* help - list of commands")
        println("* add enclosure - add new enclosure")
        println("* add <parrot|wolf|lion> <EnclosureID> - add new animal to enclosure")
        println("* add employee <name> <sex:MALE|FEMALE> <job> - add new employee")
        println("* add visitor <name> <sex:MALE|FEMALE> - add new visitor")
        println("* remove <EntityID> - remove enclosure, animal or person")
        println("* edit <AnimalID> <satiety> - edit animal")
        println("* edit <EmployeeID> <name> <job> - edit employee")
        println("* edit <VisitorID> <name> - edit visitor")
        println("* status <zoo|EntityID> - view the status of a zoo, enclosure, animal or person")
        println("* vote <AnimalID> - order an animal voice")
        println("* end - finish the program")
        println("To pause the zoo or start typing a command, press ENTER")
        println("To start the timer again, press ENTER")
    }

    override fun addCommand(args: List<String>) {
        if (args.size < 2) return notEnoughArguments()

        when (val type = args[1]) {
            "enclosure" -> {
                val enclosure = Enclosure()
                entities.add(enclosure)
            }

            "parrot", "wolf", "lion" -> {
                if (args.size < 3) return notEnoughArguments()

                val enclosure = getEntityById<IControlEnclosure>(args[2])
                    ?: return println("Couldn't find an enclosure with this ID")

                val name = type.capitalize()

                if (!enclosure.addingIsAvailable(name)) {
                    return println("You cannot add $name to this enclosure")
                }

                val animal = when (type) {
                    "parrot" -> Parrot()
                    "wolf" -> Wolf()
                    "lion" -> Lion()
                    else -> Animal()
                }
                enclosure.addAnimal(animal)
                entities.add(animal)
            }

            "employee" -> {
                if (args.size < 5) return notEnoughArguments()

                val employee = Employee(args[2], args[3], args[4])
                entities.add(employee)
            }

            "visitor" -> {
                if (args.size < 4) return notEnoughArguments()

                val visitor = Visitor(args[2], args[3])
                entities.add(visitor)
            }
        }
    }

    override fun removeCommand(args: List<String>) {
        if (args.size < 2) return notEnoughArguments()

        val entity = getEntityById<Entity>(args[1])
            ?: return println("Couldn't find an entity with this ID")

        entity.destroy()
        entities.remove(entity)

        when {
            Enclosure::class.isInstance(entity) -> {
                for (animal in (entity as IControlEnclosure).animals) {
                    animal.destroy()
                    entities.remove(animal)
                }
            }

            Animal::class.isInstance(entity) -> {
                enclosures.find {
                    (it as IControlEnclosure).removeAnimal(entity as Animal)
                }
            }
        }
    }

    override fun editCommand(args: List<String>) {
        if (args.size < 3) return notEnoughArguments()

        val entity = getEntityById<Entity>(args[1])
            ?: return println("Couldn't find an entity with this ID")

        when {
            Animal::class.isInstance(entity) -> {
                (entity as Animal).edit(args[2].toInt())
            }

            Employee::class.isInstance(entity) -> {
                if (args.size < 4) return notEnoughArguments()
                (entity as Employee).edit(args[2], args[3])
            }

            Visitor::class.isInstance(entity) -> {
                (entity as Visitor).edit(args[2])
            }

            else -> return println("This entity cannot be edited")
        }

        println("${entity.prefix} successfully edited")
    }

    override fun statusCommand(args: List<String>) {
        if (args.size < 2) return notEnoughArguments()

        when (args[1]) {
            "zoo" -> checkStatus()
            else -> {
                val entity = getEntityById<Entity>(args[1])
                    ?: return println("Couldn't find an entity with this ID")

                entity.checkStatus(1)
            }
        }
    }

    override fun voteCommand(args: List<String>) {
        if (args.size < 2) return notEnoughArguments()

        val animal = getEntityById<Animal>(args[1])
            ?: return println("Couldn't find an animal with this ID")

        animal.vote()
    }

    override fun endCommand() {
        println("Finishing program...")
        command.destroy()
        timer.destroy()
    }

    override fun startCommand() {
        println("The zoo is alive again!")
        timer.start()
    }

    override fun stopCommand() {
        println("The zoo is frozen...")
        timer.stop()
    }

    override fun tick(zoo: IZooStorage) {
        entities.forEach { entity -> entity.tick(zoo) }
    }

    private val command = Command(this)
    private val timer = Timer(this)

    suspend fun launch() = coroutineScope {
        launch { command.launch() }
        launch { timer.launch() }
    }

    init {
        createAnimals()
        helpCommand()
    }
}