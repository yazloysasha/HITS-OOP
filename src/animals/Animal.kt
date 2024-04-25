package animals

/*
 * Основа любого животного
 */

open class Animal {
    var satiety: Int = 0 // Сытость
    open var threshold: Int = 0 // Порог сытости
    open var voice: String = "" // Голос

    val name: String? // Название животного
        get() = this::class.simpleName

    val status: String // Статус животного
        get() = if (isHungry()) "HUNGRY" else "WELL-FED"

    // Голодно ли сейчас животное
    fun isHungry(): Boolean {
        return satiety < threshold
    }

    // Подать голос
    fun vote() {
        println("$name speaks: $voice")
    }
}
