@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson7.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0) throw IllegalArgumentException()
    return MatrixImpl(height, width, e)
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    private val map = mutableMapOf<Cell, E>()
    init {
        for (i in 0 until height) {
            for (j in 0 until width) {
                map[Cell(i, j)] = e
            }
        }
    }

    override fun get(row: Int, column: Int): E = get(Cell(row, column))

    override fun get(cell: Cell): E {
        if (map[cell] == null) throw IndexOutOfBoundsException("map[${cell.row},${cell.column}] is not exist")
        return map[cell]!!
    }

    override fun set(row: Int, column: Int, value: E) {
        set(Cell(row, column), value)
    }

    override fun set(cell: Cell, value: E) {
        map[cell] = value
    }

    override fun equals(other: Any?): Boolean {
        if (other is Matrix<*> && height == other.height && width == other.width) {
            for (i in 0 until height) {
                for (j in 0 until width) {
                    if (this[i, j] != other[i, j]) return false
                }
            }
            return true
        }
        return false
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("[")
        for (row in 0 until height) {
            sb.append("[")
            for (column in 0 until width - 1) {
                sb.append(map[Cell(row, column)])
                sb.append(", ")
            }
            sb.append(map[Cell(row, width - 1)])
            sb.append("]")
        }
        sb.append("]")
        return "$sb"
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + map.hashCode()
        return result
    }

}

