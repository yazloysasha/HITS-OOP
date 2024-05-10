package interfaces

/*
 * Интерфейс утилиты зоопарка
 */

interface IUtil {
    // Разрушить утилиту
    fun destroy()

    // Запустить утилиту
    suspend fun launch()
}