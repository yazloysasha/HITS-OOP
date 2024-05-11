package interfaces

/*
 * Функционал, привязанный к таймеру
 */

interface ITick {
    fun tick(zoo: IZooStorage) // Новый тик таймера
}