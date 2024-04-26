package people

/*
 * Основа любого человека
 */

open class Person(initName: String, initSex: String) {
    var name = initName // Имя
    val sex = if (initSex == "MALE") "MALE" else "FEMALE" // Пол
}
