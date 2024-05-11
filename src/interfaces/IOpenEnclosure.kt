package interfaces

import animals.Animal

/*
 * Интерфейс открытой части вольера
 */

interface IOpenEnclosure {
    var prefix: String // Префикс

    // Показать животных, находящихся в открытой части
    fun showAnimalsInOpenPart()

    // Получить животное из открытой части
    fun getAnimalFromOpenPart(): Animal?
}