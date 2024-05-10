package interfaces

import animals.Animal

/*
 * Интерфейс зоопарка
 */

interface IZoo {
    // Проверить статус зоопарка
    fun checkStatus()

    // Помощь по командам
    fun helpCommand()

    // Добавить новый объект
    fun addCommand(args: List<String>)

    // Удалить объект
    fun removeCommand(args: List<String>)

    // Отредактировать объект
    fun editCommand(args: List<String>)

    // Проверить статус объекта
    fun statusCommand(args: List<String>)

    // Приказать животному подать голос
    fun voteCommand(args: List<String>)

    // Завершить программу
    fun endCommand()

    // Получить свободное животное
    fun getFreeAnimal(type: String): Animal?

    // Пройти по виду животного
    fun passAnimals(animals: List<Animal>)
}