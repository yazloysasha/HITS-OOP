package organization

import people.Visitor

/*
 * Магазин
 */

class Shop {
    val foodPrice = 100 // Цена одной единицы еды

    // Купить еду
    fun buyFood(visitor: Visitor, money: Int): Int {
        val food = money / foodPrice
        val spent = food * foodPrice

        visitor.reduceMoney(spent)

        println("[Shop] ${visitor.prefix} bought $food meals for \$$spent")

        return food
    }
}