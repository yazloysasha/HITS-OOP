package people

/*
 * Сотрудник
 */

class Employee(
    initName: String,
    initSex: String,
    initJob: String,
    initAnimal: String
): Person(initName, initSex) {
    var job = initJob // Должность
    var animal = parseAnimal(initAnimal) // Животное, закреплённое за сотрудником
        set(value) {
            field = parseAnimal(value)
        }

    // Получить животного по его типу
    private fun parseAnimal(type: String): String {
        return if (type == "parrot" || type == "wolf" || type == "lion") type else "NONE"
    }

    // Редактировать сотрудника
    fun edit(newName: String, newJob: String, newAnimal: String) {
        name = newName
        job = newJob
        animal = newAnimal
    }

    // Проверить статус сотрудника
    fun checkStatus() {
        println("[Employee] Name: $name | Sex: $sex | Job: $job | Animal: $animal")
    }
}
