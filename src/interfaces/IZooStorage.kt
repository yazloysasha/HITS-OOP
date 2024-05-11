package interfaces

import people.Visitor
import people.Employee
import organization.Shop
import organization.Entity
import organization.Enclosure

/*
 * Интерфейс хранилища зоопарка
 */

interface IZooStorage {
    val entities: MutableList<Entity>
    val enclosures: MutableList<Enclosure>
    val employees: MutableList<Employee>
    val visitors: MutableList<Visitor>
    val shop: Shop
}