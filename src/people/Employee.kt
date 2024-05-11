package people

import kotlin.random.Random
import interfaces.IZooStorage
import interfaces.IControlEnclosure

/*
 * Сотрудник
 */

class Employee(
    firstname: String,
    sex: String,
    private var job: String // Должность
): Person(firstname, sex) {
    // Пройтись по вольерам
    private fun walkThroughEnclosures(enclosures: List<IControlEnclosure>) {
        // Вольер с минимальным количеством еды
        var badEnclosure: IControlEnclosure? = null

        // Количество еды
        val amount = Random.nextInt(8) + 1

        for (enclosure in enclosures) {
            if (!enclosure.puttingFoodIsAvailable(amount)) continue

            if (badEnclosure == null || enclosure.food < badEnclosure.food) {
                badEnclosure = enclosure
            }
        }

        if (badEnclosure != null) {
            putFoodInEnclosure(badEnclosure, amount)
        }
    }

    // Положить еду в вольер
    private fun putFoodInEnclosure(enclosure: IControlEnclosure, amount: Int) {
        enclosure.putFood(amount)

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