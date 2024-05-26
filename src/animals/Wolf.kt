package animals

import food.Red
import food.Blue

/*
 * Волк
 */

class Wolf : Animal() {
    override val threshold = 8
    override val voice = "Gav-gav"
    override val limit = 8
    override val ration = listOf(
        Red::class,
        Blue::class
    )
}