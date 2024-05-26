package interfaces

import food.Food
import animals.Animal
import kotlin.reflect.KClass

/*
 * Управление вольером
 */

interface IControlEnclosure {
    var prefix: String // Префикс

    // Полный список животных в вольере
    val animals: List<Animal>

    // Запас еды в вольере
    val fodder: MutableList<Food> // Хранилище еды

    // Можно ли положить еду
    fun puttingFoodIsAvailable(amount: Int, type: KClass<*>): Boolean

    // Положить еду
    fun putFood(amount: Int, type: KClass<*>)

    // Можно ли добавить новое животное
    fun addingIsAvailable(name: String): Boolean

    // Добавить новое животное в вольер
    fun addAnimal(animal: Animal)

    // Выгнать животное из вольера
    fun removeAnimal(animal: Animal): Boolean

    // Проверить статус вольера
    fun checkStatus(rights: Int)
}