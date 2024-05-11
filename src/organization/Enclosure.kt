package organization

import animals.Animal
import kotlin.random.Random
import interfaces.IZooStorage
import interfaces.IOpenEnclosure
import interfaces.IControlEnclosure

/*
 * Вольер
 */

class Enclosure: IOpenEnclosure, IControlEnclosure, Entity() {
    private val animalsInOpenPart = mutableListOf<Animal>() // Животные в открытой части
    private val animalsInClosedPart = mutableListOf<Animal>() // Животные в закрытой части

    override val animals: List<Animal>
        get() = animalsInOpenPart + animalsInClosedPart

    private val limit = 128 // Максимальный запас еды
    override var food = 0 // Запас еды в вольере

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
            if (food < minFood) return

            val amount = food.coerceIn(minFood, maxFood)
            food -= amount
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

    override fun puttingFoodIsAvailable(amount: Int): Boolean {
        return food + amount < limit
    }

    override fun putFood(amount: Int) {
        food += amount
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
        println("[$prefix] Animals: ${animals.size} | Food: $food")

        animals.forEach { animal -> animal.checkStatus(1) }
    }

    override fun tick(zoo: IZooStorage) {
        movementOfAnimals()
        animalsToEat()
    }
}