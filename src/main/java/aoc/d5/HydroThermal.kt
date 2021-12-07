package aoc.d5

import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min
import java.lang.Math.abs

typealias Point = Pair<Int,Int>

fun readFile(): List<String> {
    return File("src/main/java/aoc/d5/input.dat").readLines()
}

data class Line(
        val begin: Point,
        val end: Point
) {
    fun isHorizontal() =
            begin.first == end.first

    fun isVertical() =
            begin.second == end.second

    fun isDiagonal() =
            !isHorizontal() && !isVertical()

    fun maxX() =
            max(begin.first, end.first)

    fun minX() =
            min(begin.first, end.first)

    fun maxY() =
            max(begin.second, end.second)

    fun minY() =
            min(begin.second, end.second)

    fun plotHorizontalLine() =
        (minY()..maxY()).map { Point(begin.first, it) }

    fun plotVerticalLine() =
            (minX()..maxX()).map { Point(it, begin.second) }

    fun plotDiagonalLine() =
        (minX()..maxX()).map { x ->
            (minY()..maxY()).map { y ->
                Point(x,y)
            }
        }.flatten().filter { p -> abs(begin.first - p.first) == abs(begin.second - p.second) }


    companion object Factory {
        fun parse(input: String):Line {
            val points = input.split("->").
            map { it ->
                it.split(",").
                let { l -> Pair(l.first().trim().toInt(), l.last().trim().toInt()) }
            }
            return Line(points.first(), points.last())
        }

    }
}

fun main() {

    val readings = readFile()
    val allLines = readings.map { Line.parse(it) }
    println("we are here")
    val hPoints = allLines.filter { it.isHorizontal() }.map { it.plotHorizontalLine() }.flatten()
    val vPoints = allLines.filter { it.isVertical() }.map { it.plotVerticalLine() }.flatten()
    val dPoints = allLines.filter { it.isDiagonal() }.map { it.plotDiagonalLine() }.flatten()


    println((hPoints + vPoints).groupBy { it }.filterValues { it.size > 1 }.size)

    println((hPoints + vPoints + dPoints).groupBy { it }.filterValues { it.size > 1 }.size)
}