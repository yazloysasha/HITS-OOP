package people

import organization.Entity

/*
 * Основа любого человека
 */

abstract class Person(
    var firstname: String, // Имя
    initSex: String
): Entity() {
    val sex = if (initSex == "MALE") "MALE" else "FEMALE" // Пол
}