package organization

import java.util.UUID
import interfaces.ITick

/*
 * Любой объект (животное / человек)
 */

abstract class Entity : ITick {
    val id = UUID.randomUUID().toString() // ID
    val name = this::class.simpleName // Название класса
    val prefix = "$name $id" // Префикс

    // Разрушить объект
    abstract fun destroy()

    // Проверить статус объекта
    abstract fun checkStatus(rights: Int)

    init {
        println("[$prefix] I was added to the zoo")
    }
}