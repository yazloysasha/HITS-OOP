package interfaces

import animals.Animal

/*
 * Управление вольером
 */

interface IControlEnclosure {
    var prefix: String // Префикс

    // Полный список животных в вольере
    val animals: List<Animal>

    // Запас еды в вольере
    var food: Int

    // Можно ли положить еду
    fun puttingFoodIsAvailable(amount: Int): Boolean

    // Положить еду
    fun putFood(amount: Int)

    // Можно ли добавить новое животное
    fun addingIsAvailable(name: String): Boolean

    // Добавить новое животное в вольер
    fun addAnimal(animal: Animal)

    // Выгнать животное из вольера
    fun removeAnimal(animal: Animal): Boolean

    // Проверить статус вольера
    fun checkStatus(rights: Int)
}