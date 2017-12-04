@file:Suppress("UNUSED_PARAMETER")

package lesson6.task1

import lesson1.task1.sqr
import java.lang.Math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val dis = this.center.distance(other.center) - (this.radius + other.radius)
        return if (dis > 0) dis else 0.0
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()

    fun center() = Point((begin.x + end.x) / 2, (begin.y + end.y) / 2)

}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.size < 2) throw IllegalArgumentException()
    var max = 0.0
    var maxSegment = Segment(Point(0.0, 0.0), Point(0.0, 0.0))
    for (i in 0 until points.size) {
        for (j in i + 1 until points.size) {
            val dist = points[i].distance(points[j])
            if (dist > max) {
                max = dist
                maxSegment = Segment(points[i], points[j])
            }
        }
    }
    return maxSegment
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val radius = (diameter.begin.distance(diameter.end) / 2)
    val center = diameter.center()
    return Circle(center, radius)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        assert(angle >= 0 && angle < Math.PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * Math.cos(angle) - point.x * Math.sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        if (this.angle == Math.PI / 2) return Point(-this.b, (-this.b) *
                tan(other.angle) + other.b / cos(other.angle))
        if (other.angle == Math.PI / 2) return Point(-other.b, (-other.b) *
                tan(this.angle) + this.b / cos(this.angle))
        val x = -(this.b / cos(this.angle) - other.b / cos(other.angle)) /
                (tan(this.angle) - tan(other.angle))
        val y = x * tan(this.angle) + this.b / cos(this.angle)
        return Point(x, y)

    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${Math.cos(angle)} * y = ${Math.sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    if (s.end.x == s.begin.x) return Line(s.begin, PI / 2)
    var angle = Math.atan((s.end.y - s.begin.y) / (s.end.x - s.begin.x))
    if (angle < 0) angle += PI
    return Line(s.begin, angle)
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point) = lineBySegment(Segment(a, b))


/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val point = Segment(a, b).center()
    val line = lineByPoints(a, b)
    val angle = if (line.angle < Math.PI / 2) Math.PI / 2 + line.angle
    else line.angle - Math.PI / 2
    return Line(point, angle)
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) throw IllegalArgumentException()
    var min = Double.MAX_VALUE
    var minPair = Pair(Circle(Point(0.0, 0.0), 1.0),
            Circle(Point(Double.MAX_VALUE - 1, Double.MAX_VALUE - 1), 1.0))
    for (i in 0 until circles.size) {
        for (j in i + 1 until circles.size) {
            val dist = circles[i].distance(circles[j])
            if (dist < min) {
                min = dist
                minPair = Pair(circles[i], circles[j])
            }
        }
    }
    return minPair
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val bis1 = bisectorByPoints(a, b)
    val bis2 = bisectorByPoints(b, c)
    val center = bis1.crossPoint(bis2)
    val radius = center.distance(b)
    return Circle(center, radius)

}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    if (points.isEmpty()) throw IllegalArgumentException()
    if (points.size == 1) return Circle(points[0], 0.0)
    var case = true
    val diameter = diameter(*points)
    for (i in 0 until points.size) {
        if ((diameter.center().distance(points[i]) -
                diameter.begin.distance(diameter.end) / 2 > 1e-7)) {
            case = false
            break
        }
    }
    if (case) return Circle(circleByDiameter(diameter).center,
            circleByDiameter(diameter).radius + 1e-12)
    else {
        var minRad = Double.MAX_VALUE
        var minCircle = Circle(Point(0.0, 0.0), Double.MAX_VALUE)
        for (i in 0 until points.size - 2) {
            for (j in i + 1 until points.size - 1) {
                for (k in j + 1 until points.size) {
                    var check = 0
                    val currCircle = Circle(circleByThreePoints(points[i],
                            points[j], points[k]).center, circleByThreePoints(points[i],
                            points[j], points[k]).radius + 1e-12)
                    for (f in 0 until points.size) {
                        if (!currCircle.contains(points[f])) {
                            check = -1
                            break
                        }
                    }
                    if (check == 0 && currCircle.radius < minRad) {
                        minRad = currCircle.radius
                        minCircle = currCircle
                    }
                }
            }
        }
        return minCircle
    }
}

