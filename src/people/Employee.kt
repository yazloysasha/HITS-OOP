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
    var animal = initAnimal // Животное, закреплённое за сотрудником

    // Редактировать сотрудника
    fun edit(newName: String, newJob: String, newAnimal: String) {
        name = newName
        job = newJob
        animal = newAnimal
    }

    // Проверить статус сотрудника
    fun checkStatus() {
        println("[Employee] Name: $name | Sex: $sex | Job: $job")
    }
}
