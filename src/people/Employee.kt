package people

/*
 * Сотрудник
 */

class Employee(
    initName: String,
    initSex: String,
    initJob: String
): Person(initName, initSex) {
    var job = initJob // Должность
}
