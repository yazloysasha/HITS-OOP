package animals

/*
 * Основа любого животного
 */

open class Animal {
    private var satiety = 32 // Сытость
    open var threshold = 0 // Порог сытости
    open var voice = "" // Голос

    val name: String? // Название животного
        get() = this::class.simpleName

    val status: String // Статус животного
        get() = if (satiety < threshold) "HUNGRY" else "WELL-FED"

    // Уменьшить сытость животного
    fun reduceSatiety() {
        if (satiety > 0) satiety--
    }

    // Подать голос
    fun vote() {
        println("$name speaks: $voice")
    }
}
