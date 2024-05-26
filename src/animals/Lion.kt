package animals

import food.Red
import food.Green

/*
 * Лев
 */

class Lion : Animal() {
    override val threshold = 16
    override val voice = "R-r-r"
    override val limit = 4
    override val ration = listOf(
        Red::class,
        Green::class
    )
}