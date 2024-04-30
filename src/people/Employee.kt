package people

import animals.Animal

/*
 * Сотрудник
 */

class Employee(
    initName: String,
    initSex: String,
    initJob: String,
    initAnimal: Animal?
): Person(initName, initSex) {
    var job = initJob // Должность
    var animal = initAnimal // Животное, закреплённое за сотрудником

    // Редактировать сотрудника
    fun edit(newName: String, newJob: String, newAnimal: Animal?) {
        name = newName
        job = newJob
        animal = newAnimal
    }

    // Проверить статус сотрудника
    fun checkStatus() {
        print("[Employee] Name: $name | Sex: $sex | Job: $job | Animal: ")

        if (animal == null) {
            println("NONE")
        } else {
            println(animal?.name)
        }
    }

    // Выгнать сотрудника
    fun kick() {
        animal?.employee = null
    }
}
