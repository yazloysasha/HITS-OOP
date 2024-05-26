package food

/*
 * Основа марки еды
 */

open class Food {
    var weight = 0 // Вес
    open val maxWeight = 0 // Максимальный вес

    // Положить еду в контейнер
    fun put(amount: Int) {
        weight = (weight + amount).coerceIn(0, maxWeight)
    }
}