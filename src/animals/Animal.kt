package animals

import organization.Entity
import interfaces.IZooStorage

/*
 * Основа любого животного
 */

open class Animal: Entity() {
    private var satiety = 0 // Сытость
    open var threshold = 0 // Порог сытости
    open var voice = "" // Голос
    open var limit = 0 // Лимит животных в одном вольере

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