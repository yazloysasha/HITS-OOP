package interfaces

/*
 * Интерфейс команд зоопарка
 */

interface IZooCommands {
    // Помощь по командам
    fun helpCommand()

    // Добавить новый объект
    fun addCommand(args: List<String>)

    // Удалить объект
    fun removeCommand(args: List<String>)

    // Отредактировать объект
    fun editCommand(args: List<String>)

    // Проверить статус объекта
    fun statusCommand(args: List<String>)

    // Приказать животному подать голос
    fun voteCommand(args: List<String>)

    // Завершить программу
    fun endCommand()

    // Запустить таймер
    fun startCommand()

    // Остановить таймер
    fun stopCommand()
}