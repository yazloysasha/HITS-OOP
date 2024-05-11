package organization

import people.*
import animals.*
import interfaces.*
import kotlinx.coroutines.*
import kotlin.reflect.KClass

/*
 * Зоопарк
 */

class Zoo: IZooStorage, IZooCommands, ITick {
    override val entities = mutableListOf<Entity>()
    override val enclosures = mutableListOf<Enclosure>()
    override val employees = mutableListOf<Employee>()
    override val visitors = mutableListOf<Visitor>()

    // В команде недостаточно аргументов
    private fun notEnoughArguments() {
        println("Not enough arguments in the command...")
    }

    // Получить элемент по ID
    private fun getEntityById(id: Int, parent: KClass<*> = Entity::class): Entity? {
        for (entity in entities) {
            if (id == entity.id && parent.isInstance(entity)) {
                return entity
            }
        }

        return null
    }

    private fun checkStatus() {
        print("[Zoo] Enclosures: ${enclosures.size}")
        print(" | Employees: ${employees.size}")
        println(" | Visitors: ${visitors.size}")
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
                enclosures.add(enclosure)
                entities.add(enclosure)
            }
            "parrot", "wolf", "lion" -> {
                if (args.size < 3) return notEnoughArguments()

                val enclosure = getEntityById(args[2].toInt(), Enclosure::class) as IControlEnclosure?
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
                employees.add(employee)
                entities.add(employee)
            }
            "visitor" -> {
                if (args.size < 4) return notEnoughArguments()

                val visitor = Visitor(args[2], args[3])
                visitors.add(visitor)
                entities.add(visitor)
            }
        }
    }

    override fun removeCommand(args: List<String>) {
        if (args.size < 2) return notEnoughArguments()

        val entity = getEntityById(args[1].toInt())
            ?: return println("Couldn't find an entity with this ID")

        entity.destroy()
        entities.remove(entity)

        when {
            Enclosure::class.isInstance(entity) -> {
                for (animal in (entity as IControlEnclosure).animals) {
                    animal.destroy()
                    entities.remove(animal)
                }
                enclosures.remove(entity)
            }
            Animal::class.isInstance(entity) -> {
                for (enclosure: IControlEnclosure in enclosures) {
                    if (enclosure.removeAnimal(entity as Animal)) break
                }
            }
            Employee::class.isInstance(entity) -> {
                employees.remove(entity)
            }
            Visitor::class.isInstance(entity) -> {
                visitors.remove(entity)
            }
        }
    }

    override fun editCommand(args: List<String>) {
        if (args.size < 3) return notEnoughArguments()

        val entity = getEntityById(args[1].toInt())
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
                val entity = getEntityById(args[1].toInt())
                    ?: return println("Couldn't find an entity with this ID")

                entity.checkStatus(1)
            }
        }
    }

    override fun voteCommand(args: List<String>) {
        if (args.size < 2) return notEnoughArguments()

        val animal = getEntityById(args[1].toInt(), Animal::class) as Animal?
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
        helpCommand()
    }
}