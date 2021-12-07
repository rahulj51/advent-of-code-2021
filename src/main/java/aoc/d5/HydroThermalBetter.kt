package aoc.d5

import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min
import java.lang.Math.abs
import kotlin.math.absoluteValue
import kotlin.math.sign

//https://todd.ginsberg.com/post/advent-of-code/2021/day5/
data class Line2(
        val begin: Point,
        val end: Point
) {
    fun shareAxis() =
        begin.first == end.first || begin.second == end.second

    fun plot(): List<Point> {
        val xDelta = (end.first - begin.first).sign
        val yDelta = (end.second - begin.second).sign

        val steps = maxOf((begin.first - end.first).absoluteValue, (begin.second - end.second).absoluteValue)

        return (1 .. steps).scan(begin) { last, _ -> Point(last.first + xDelta, last.second + yDelta) }
    }


    companion object Factory {
        fun parse(input: String):Line2 {
            val points = input.split("->").
            map { it ->
                it.split(",").
                let { l -> Pair(l.first().trim().toInt(), l.last().trim().toInt()) }
            }
            return Line2(points.first(), points.last())
        }

    }
}

fun main() {

    println(solve(Line2::shareAxis))
    println(solve({true}))
}

fun solve(lineFilter : (Line2) -> Boolean): Int {
    val filter = readFile()
            .map { Line2.parse(it) }
            .filter(lineFilter)
    val flatMap = filter
            .flatMap { it.plot() }
    return flatMap
            .groupingBy { it }
            .eachCount()
            .count{ it.value > 1}
}
