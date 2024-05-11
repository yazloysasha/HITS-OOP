package people

import animals.Animal
import organization.Shop
import kotlin.random.Random
import interfaces.IZooStorage
import interfaces.IOpenEnclosure

/*
 * Посетитель
 */

class Visitor(
    firstname: String,
    sex: String,
): Person(firstname, sex) {
    private var indexOfEnclosure = -1 // Индекс текущего вольера

    private var money = 0 // Баланс пользователя
    private var food = 0 // Количество еды у пользователя

    // Пройти по зоопарку
    private fun walkThroughZoo(shop: Shop, enclosures: List<IOpenEnclosure>) {
        var indexOfNextEnclosure = indexOfEnclosure

        if (indexOfEnclosure != -1 && indexOfEnclosure >= enclosures.size) {
            indexOfNextEnclosure = -1
        } else if ((indexOfEnclosure != -1 || enclosures.isNotEmpty()) && Random.nextInt(10) < 3) {
            indexOfNextEnclosure = indexOfEnclosure + 1

            if (indexOfNextEnclosure >= enclosures.size) {
                indexOfNextEnclosure = -1
            }
        }

        if (indexOfEnclosure != indexOfNextEnclosure) {
            indexOfEnclosure = indexOfNextEnclosure

            println(
                when (indexOfEnclosure) {
                    -1 -> "[$prefix] I left the zoo - $firstname, $sex"
                    else -> "[$prefix] I went to ${enclosures[indexOfEnclosure].prefix} - $firstname, $sex"
                }
            )
        }

        val value = Random.nextInt(10)

        // Покормить животного в вольере
        if (indexOfEnclosure != -1 && value < 8) {
            val animal = enclosures[indexOfEnclosure].getAnimalFromOpenPart()

            if (animal != null) feedAnimal(animal)
        }

        // Сделать что-то ещё
        if (value < 5) {
            when (indexOfEnclosure) {
                // Пользователь вне зоопарка, можно пополнить баланс
                -1 -> {
                    val amount = Random.nextInt(2000) + 1
                    topUpMoney(amount)
                }

                // Пользователь в зоопарке, можно купить еду
                else -> if (money >= shop.foodPrice) {
                    val moneyAmount = Random.nextInt(money.coerceIn(shop.foodPrice, 900))
                    val foodAmount = shop.buyFood(this, moneyAmount)
                    putFood(foodAmount)
                }
            }
        }
    }

    // Пополнить баланс
    private fun topUpMoney(amount: Int) {
        money += amount

        println("[$prefix] I replenished my balance by $amount\$ - $firstname, $sex")
    }

    // Положить еду в рюкзак
    private fun putFood(amount: Int) {
        food += amount
    }

    // Покормить животного
    private fun feedAnimal(animal: Animal) {
        if (animal.status != "HUNGRY" || food == 0) return

        val minFood = 1
        val maxFood = 4

        val amount = food.coerceIn(minFood, maxFood)
        food -= amount

        println("[$prefix] I fed ${animal.prefix} - $firstname, $sex")

        animal.eat(amount)
    }

    // Забрать деньги у посетителя
    fun reduceMoney(amount: Int) {
        money -= amount
    }

    // Редактировать посетителя
    fun edit(newFirstname: String) {
        firstname = newFirstname
    }

    // Уйти из зоопарка
    override fun destroy() {
        println("[$prefix] I'm leaving the zoo - $firstname, $sex")
    }

    // Проверить статус посетителя
    override fun checkStatus(rights: Int) {
        println("[$prefix] Name: $firstname | Sex: $sex | Balance: \$$money | Food: $food")
    }

    override fun tick(zoo: IZooStorage) {
        walkThroughZoo(zoo.shop, zoo.enclosures)
    }
}