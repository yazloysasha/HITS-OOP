package organization

import food.*
import animals.Animal
import kotlin.random.Random
import kotlin.reflect.KClass
import interfaces.IZooStorage
import interfaces.IOpenEnclosure
import interfaces.IControlEnclosure

/*
 * Вольер
 */

class Enclosure : IOpenEnclosure, IControlEnclosure, Entity() {
    private val animalsInOpenPart = mutableListOf<Animal>() // Животные в открытой части
    private val animalsInClosedPart = mutableListOf<Animal>() // Животные в закрытой части

    override val animals: List<Animal>
        get() = animalsInOpenPart + animalsInClosedPart

    override val fodder = mutableListOf<Food>() // Хранилище еды

    // Перемещения животных по вольеру
    private fun movementOfAnimals() {
        val goToOpenPart = mutableListOf<Animal>()
        val goToClosedPart = mutableListOf<Animal>()

        val chance = 3 // Шанс 30%

        for (animal in animalsInOpenPart) {
            if (Random.nextInt(10) < chance) {
                goToClosedPart.add(animal)
            }
        }
        for (animal in animalsInClosedPart) {
            if (Random.nextInt(10) < chance) {
                goToOpenPart.add(animal)
            }
        }

        for (animal in goToOpenPart) {
            animalsInClosedPart.remove(animal)
            animalsInOpenPart.add(animal)

            println("[$prefix] ${animal.prefix} moved to the open part")
        }
        for (animal in goToClosedPart) {
            animalsInOpenPart.remove(animal)
            animalsInClosedPart.add(animal)

            println("[$prefix] ${animal.prefix} moved to the closed part")
        }
    }

    // Животные едят в вольере
    private fun animalsToEat() {
        val minFood = 1
        val maxFood = 4

        for (animal in animals) {
            if (animal.status != "HUNGRY") continue

            // Животное ищет свою еду
            val food = fodder.find { firstT ->
                animal.ration.find { secondT ->
                    secondT.isInstance(firstT)
                } != null
            } ?: continue

            if (food.weight < minFood) return

            val amount = food.weight.coerceIn(minFood, maxFood)

            food.take(amount)
            animal.eat(amount)
        }
    }

    override fun showAnimalsInOpenPart() {
        animalsInOpenPart.forEach { animal -> animal.checkStatus(0) }
    }

    override fun getAnimalFromOpenPart(): Animal? {
        if (animalsInOpenPart.isEmpty()) return null

        return animalsInOpenPart[Random.nextInt(animalsInOpenPart.size)]
    }

    override fun puttingFoodIsAvailable(amount: Int, type: KClass<*>): Boolean {
        val food = fodder.find { type::class.isInstance(it) }
            ?: return true

        return food.weight < food.limit
    }

    override fun putFood(amount: Int, type: KClass<*>) {
        var food = fodder.find { type.isInstance(it) }

        if (food == null) {
            food = when (type.simpleName) {
                "Red" -> Red()
                "Green" -> Green()
                "Blue" -> Blue()
                else -> null
            }
            if (food != null) {
                fodder.add(food)
            }
        }

        food?.put(amount)
    }

    override fun addingIsAvailable(name: String): Boolean {
        return animals.isEmpty() || name == animals[0].name && animals.size < animals[0].limit
    }

    override fun addAnimal(animal: Animal) {
        animalsInClosedPart.add(animal)

        println("[$prefix] ${animal.prefix} added to closed part")
    }

    override fun removeAnimal(animal: Animal): Boolean {
        if (animalsInOpenPart.remove(animal)) return true
        return animalsInClosedPart.remove(animal)
    }

    // Выбросить вольер из зоопарка
    override fun destroy() {
        println("[$prefix] I was thrown out of the zoo")
    }

    // Проверить статус вольера
    override fun checkStatus(rights: Int) {
        print("[$prefix] Animals: ${animals.size}")
        for (food in fodder) {
            print(" | ${food.name} Food: ${food.weight}")
        }
        println()

        animals.forEach { animal -> animal.checkStatus(1) }
    }

    override fun tick(zoo: IZooStorage) {
        movementOfAnimals()
        animalsToEat()
    }
}