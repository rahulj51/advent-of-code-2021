package aoc.d3

import java.io.File

/*
12 bits
split into 12 lists
for each list, find most common and least common
Bit can be a class with position (defining order) - too much???

00110011001
split into separate bits
fold to a list of 12 lists
what is the right function to use


 */

fun readFile(): List<String> {
    return File("src/main/java/aoc/d3/input.dat").readLines()
}


fun main() {

    val readings = readFile()
    //hardocding this from 0-11 but it can also be inferred from the file
    val gammaBits  = (0..11).map { i ->
        val ones = readings.count { j -> j[i] == '1' }
        if (ones > readings.size / 2) '1' else '0'
    }

    println(gammaBits)

    val epsilonBits = gammaBits.map { b -> if (b == '1') '0' else '1' }

    println(epsilonBits)

    val powerConsumption = gammaBits.convertToDecimal() * epsilonBits.convertToDecimal()

    println(powerConsumption)

}

private fun <String> List<String>.convertToDecimal(): Int =
    this.joinToString("").toInt(2)

