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

    val enclosures: List<Enclosure>
    val employees: List<Employee>
    val visitors: List<Visitor>

    val shop: Shop
}