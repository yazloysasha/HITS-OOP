package people

import food.*
import animals.Animal
import organization.Shop
import kotlin.random.Random
import kotlin.reflect.KClass
import interfaces.IZooStorage
import interfaces.IOpenEnclosure

/*
 * Посетитель
 */

class Visitor(
    firstname: String,
    sex: String,
) : Person(firstname, sex) {
    private var indexOfEnclosure = -1 // Индекс текущего вольера

    private var money = 0 // Баланс пользователя
    private val backpack = mutableListOf<Food>() // Рюкзак с едой

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
                else -> if (money > 0) {
                    // Случайный тип еды
                    val type = when (Random.nextInt(3)) {
                        0 -> Red::class
                        1 -> Green::class
                        else -> Blue::class
                    }

                    val given = Random.nextInt(money.coerceIn(1, 2000))
                    val amount = shop.buy(this, given, type)
                    if (amount != 0) {
                        putFood(amount, type)
                    }
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
    private fun putFood(amount: Int, type: KClass<*>) {
        var pocket = backpack.find { type.isInstance(it) }

        if (pocket == null) {
            pocket = when (type.simpleName) {
                "Red" -> Red()
                "Green" -> Green()
                "Blue" -> Blue()
                else -> null
            }
            if (pocket != null) {
                backpack.add(pocket)
            }
        }

        pocket?.put(amount)
    }

    // Покормить животного
    private fun feedAnimal(animal: Animal) {
        if (animal.status != "HUNGRY") return

        val minFood = 1
        val maxFood = 8

        val pocket = backpack.find { firstT ->
            animal.ration.find { secondT ->
                firstT.weight > 0 && secondT.isInstance(firstT)
            } != null
        } ?: return

        val amount = pocket.weight.coerceIn(minFood, maxFood)
        pocket.take(amount)

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
        print("[$prefix] Name: $firstname | Sex: $sex | Balance: \$$money")
        for (pocket in backpack) {
            print(" | [F] ${pocket.name}: ${pocket.weight}")
        }
        println()
    }

    override fun tick(zoo: IZooStorage) {
        walkThroughZoo(zoo.shop, zoo.enclosures)
    }
}