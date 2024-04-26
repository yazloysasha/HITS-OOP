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

    // Проверить статус животного
    fun checkStatus() {
        println("[$name] Status: $status | Satiety: $satiety")
    }

    // Уменьшить сытость животного
    fun reduceSatiety(number: Int) {
        if (satiety > 0) {
            if (satiety == threshold) {
                println("The lion #$number is hungry!")
            }
            satiety--
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
}
