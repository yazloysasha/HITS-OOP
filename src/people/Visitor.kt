package people

/*
 * Посетитель
 */

class Visitor(
    initName: String,
    initSex: String
): Person(initName, initSex) {
    // Редактировать посетителя
    fun edit(newName: String) {
        name = newName
    }

    // Проверить статус посетителя
    fun checkStatus() {
        println("[Visitor] Name: $name | Sex: $sex")
    }
}
