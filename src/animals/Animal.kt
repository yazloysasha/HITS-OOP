package animals

import people.Employee

/*
 * Основа любого животного
 */

open class Animal {
    private var satiety = 32 // Сытость
    open var threshold = 0 // Порог сытости
    open var voice = "" // Голос

    // Сотрудник, закреплённый за животным
    var employee: Employee? = null

    val name: String? // Название животного
        get() = this::class.simpleName

    val status: String // Статус животного
        get() = if (satiety < threshold) "HUNGRY" else "WELL-FED"

    // Редактировать животного
    fun edit(newSatiety: Int) {
        satiety = newSatiety
    }

    // Проверить статус животного
    fun checkStatus() {
        println("[$name] Status: $status | Satiety: $satiety")
    }

    // Уменьшить сытость животного
    fun reduceSatiety(number: Int) {
        if (satiety > 0) {
            satiety--
        }
        if (satiety < threshold) {
            println("The lion #$number is hungry!")
        }
    }

    // Подать голос
    fun vote() {
        println("$name speaks: $voice")
    }

    // Покормить животного
    fun feed() {
        satiety += 4
    }

    // Убить животного
    fun kill() {
        employee?.animal = null
    }
}
