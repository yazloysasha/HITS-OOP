package animals

import food.Blue
import food.Green

/*
 * Попугай
 */

class Parrot : Animal() {
    override val threshold = 4
    override val voice = "Chirik-chirik"
    override val limit = 16
    override val ration = listOf(
        Green::class,
        Blue::class
    )
}