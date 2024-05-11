package people

import interfaces.IZooStorage

/*
 * Посетитель
 */

class Visitor(
    firstname: String,
    sex: String,
): Person(firstname, sex) {
    // Редактировать посетителя
    fun edit(newFirstname: String) {
        firstname = newFirstname
    }

    // Уйти из зоопарка
    override fun destroy() {
        println("[$prefix] I'm leaving the zoo - $firstname, $sex")
    }

    // Проверить статус посетителя
    override fun checkStatus(rights: Int) {
        println("[$prefix] Name: $name | Sex: $sex")
    }

    override fun tick(zoo: IZooStorage) {
        //
    }
}