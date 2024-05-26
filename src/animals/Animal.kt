package animals

import organization.Entity
import kotlin.reflect.KClass
import interfaces.IZooStorage

/*
 * Основа любого животного
 */

abstract class Animal : Entity() {
    private var satiety = 0 // Сытость

    abstract val threshold: Int // Порог сытости
    abstract val voice: String // Голос
    abstract val limit: Int // Лимит животных в одном вольере
    abstract val ration: List<KClass<*>> // Рацион питания

    val status: String // Статус животного
        get() = if (satiety < threshold) "HUNGRY" else "WELL-FED"

    // Поесть
    fun eat(amount: Int) {
        satiety += amount

        println("[$prefix] I have eaten!")
    }

    // Проголодаться
    private fun reduceSatiety() {
        if (satiety > 0) {
            satiety--
        }

        if (satiety < threshold) {
            println("[$prefix] I am hungry!")
        }
    }

    // Подать голос
    fun vote() {
        println("[$prefix] I speak: $voice")
    }

    // Редактировать животного
    fun edit(newSatiety: Int) {
        satiety = newSatiety
    }

    // Уйти из зоопарка
    override fun destroy() {
        println("[$prefix] I'm leaving the zoo")
    }

    // Проверить статус животного
    override fun checkStatus(rights: Int) {
        println(
            when (rights) {
                0 -> "[$prefix] I'm here!"
                else -> "[$prefix] Status: $status | Satiety: $satiety"
            }
        )
    }

    override fun tick(zoo: IZooStorage) {
        reduceSatiety()
    }
}