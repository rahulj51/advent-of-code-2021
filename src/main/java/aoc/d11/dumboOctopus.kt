package aoc.d11

import java.io.File

fun readFile(f: String): List<String> {
    return File(f).readLines()
}
fun readInputFile(): List<String> {
    return readFile("src/main/java/aoc/d11/input.dat")
}

fun readTestFile(): List<String> {
    return readFile("src/main/java/aoc/d11/input_test.dat")
}

val readings = aoc.d11.readInputFile()
    .map { it ->
        it.map { d -> d.digitToInt() }.toIntArray().toList() } //convert each row to an int array
    .toList() //add to a second array

fun main() {

    part1(readings)

    part2(readings)

}

fun part1(readings: List<List<Int>>) {
    var energyMap = readings
    var totalFlashes = 0

    (1..100).forEach {
        energyMap = flash(energyMap)
        totalFlashes += countFlashes(energyMap)
    }
    println(totalFlashes)


}

fun part2(readings: List<List<Int>>) {
    var energyMap = readings
    val numberOfOctopii = energyMap.count() * energyMap.first().count()
    var flashes = 0
    var step = 0
    while (flashes < numberOfOctopii) {
        step += 1
        energyMap = flash(energyMap)
        flashes = countFlashes(energyMap)
    }

    println(step)



}


fun countFlashes(energyMap: List<List<Int>>): Int =
    energyMap.flatMap { row -> row.filter { col -> col==0 } }.count()



fun flash(energyMap: List<List<Int>>): List<List<Int>> {
    val allPoints = Point.parse(energyMap)

    var energyMap = increaseByOne(energyMap, allPoints)

    var flashablePoints = flashable(energyMap)
    var alreadyFlashed  = mutableListOf<Point>()

    while (flashablePoints.isNotEmpty() && ! alreadyFlashed.containsAll(flashablePoints)) {
        flashablePoints.forEach { point ->
            if (! alreadyFlashed.contains(point)) {
                val neighbors = point.adjacent()
                energyMap = increaseByOne(energyMap, neighbors)
            }
        }

        alreadyFlashed.addAll(flashablePoints)
        flashablePoints = flashable(energyMap)

    }

    energyMap = energyMap.map { row -> row.map { col -> if (col > 9) 0 else col } }
    return energyMap
}

private fun flashable(energyMap: List<List<Int>>) =
    energyMap.foldIndexed(mutableListOf<Point>()) { y, points, row ->
        val pointies = row.foldIndexed(mutableListOf<Point>()) { x, pointies, col ->
            if (col >= 10) pointies.add(Point(x, y))
            pointies
        }
        points.addAll(pointies)
        points

    }

private fun increaseByOne(energyMap: List<List<Int>>, points: List<Point>) =
    energyMap.mapIndexed { y, row ->
        row.mapIndexed { x, col ->
            if (points.contains(Point(x,y))) {
                col + 1
            } else {
                col
            }
        }
    }


private fun printGrid(energyMap: List<List<Int>>) {
    println()
    energyMap.forEach { row ->
        row.forEach{col -> if (col>=10) print("*") else print(col)}
        println()
    }

}


