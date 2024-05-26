package people

import food.*
import kotlin.random.Random
import kotlin.reflect.KClass
import interfaces.IZooStorage
import interfaces.IControlEnclosure

/*
 * Сотрудник
 */

class Employee(
    firstname: String,
    sex: String,
    private var job: String // Должность
) : Person(firstname, sex) {
    // Пройтись по вольерам
    private fun walkThroughEnclosures(enclosures: List<IControlEnclosure>) {
        // Случайный тип еды
        val type = when (Random.nextInt(3)) {
            0 -> Red::class
            1 -> Green::class
            else -> Blue::class
        }

        // Случайный вольер
        val enclosure = enclosures[Random.nextInt(enclosures.size)]

        // Случайное количество еды
        val amount = Random.nextInt(16) + 4

        if (enclosure.puttingFoodIsAvailable(amount, type)) {
            putFoodInEnclosure(enclosure, amount, type)
        }
    }

    // Положить еду в вольер
    private fun putFoodInEnclosure(enclosure: IControlEnclosure, amount: Int, type: KClass<*>) {
        enclosure.putFood(amount, type)

        println("[$prefix] I put $amount food in the ${enclosure.prefix} - $firstname, $sex ($job)")
    }

    // Редактировать сотрудника
    fun edit(newFirstname: String, newJob: String) {
        firstname = newFirstname
        job = newJob
    }

    // Уйти из зоопарка
    override fun destroy() {
        println("[$prefix] I'm leaving the zoo - $firstname, $sex ($job)")
    }

    // Проверить статус сотрудника
    override fun checkStatus(rights: Int) {
        print("[$prefix] Name: $firstname | Sex: $sex | Job: $job")
    }

    override fun tick(zoo: IZooStorage) {
        walkThroughEnclosures(zoo.enclosures)
    }
}