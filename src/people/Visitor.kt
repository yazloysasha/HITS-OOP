package people

/*
 * Посетитель
 */

class Visitor(
    initName: String,
    initSex: String
): Person(initName, initSex) {
    // Проверить статус посетителя
    fun checkStatus() {
        println("[Visitor] Name: $name | Sex: $sex")
    }
}
