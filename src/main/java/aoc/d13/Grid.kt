package aoc.d13

import aoc.common.Point

data class Grid (
    val maxX : Int,
    val maxY : Int
) {

//    fun allPoints(): Set<Point> {
//        val allPoints = (0..maxY).flatMap { y ->
//            (0..maxX).map { x ->
//                Point(x, y)
//            }
//        }.toSet()
//        return allPoints
//    }
//
//    fun uncoveredPoints(points: Set<Point>) =
//        allPoints().filterNot { it in points }

    fun plot(points: Set<Point>) {
        println()
        (0..maxY).forEach { y ->
            (0..maxX).forEach{x ->
                if (points.contains(Point(x,y))) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    fun resize(fold: Pair<AXIS, Int>): Grid =
        if (fold.first == AXIS.y) copy(maxY = fold.second -1) else copy(maxX = fold.second -1)


    companion object Factory {

        fun parse(points: Set<Point>) : Grid {
            val maxX = points.map { p -> p.x }.maxOf { it }
            val maxY = points.map { p -> p.y }.maxOf { it }
            return Grid(maxX, maxY)
        }

    }


}