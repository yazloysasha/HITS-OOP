package organization

import people.Visitor
import kotlin.reflect.KClass

/*
 * Магазин
 */

class Shop {
    // Получить цену еды
    private fun getPrice(type: KClass<*>): Int {
        return when (type.simpleName) {
            "Red" -> 500
            "Green" -> 200
            "Blue" -> 100
            else -> 10 // тенге
        }
    }

    // Купить еду
    fun buy(visitor: Visitor, money: Int, type: KClass<*>): Int {
        val price = getPrice(type)

        val amount = money / price

        if (amount != 0) {
            val spent = amount * price

            visitor.reduceMoney(spent)

            println("[Shop] ${visitor.prefix} bought $amount meals of ${type.simpleName} for \$$spent")
        }

        return amount
    }
}