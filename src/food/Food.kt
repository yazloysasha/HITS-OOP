package food

/*
 * Основа марки еды
 */

open class Food {
    val name = this::class.simpleName // Название класса
    var weight = 0 // Вес
    open val limit = 0 // Максимальный вес

    // Положить еду в контейнер
    fun put(amount: Int) {
        weight = (weight + amount).coerceIn(0, limit)
    }

    // Забрать еду из контейнера
    fun take(amount: Int) {
        weight -= amount
    }
}