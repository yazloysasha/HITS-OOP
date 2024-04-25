import organization.Zoo
import kotlinx.coroutines.*

suspend fun main() = coroutineScope<Unit> {
    val zoo = Zoo()

    zoo.launch()
}
