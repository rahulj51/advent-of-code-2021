package aoc.d6

import java.io.File

fun readFile(): List<String> {
    return File("src/main/java/aoc/d6/input.dat").readLines()
}

data class LanternFish(
        val daysRemainingToMakeBaby: Int

){
    fun tickTock() =
        when (daysRemainingToMakeBaby) {
            0 -> listOf(copy(daysRemainingToMakeBaby=6),LanternFish(8))
            else -> {
                listOf(copy(daysRemainingToMakeBaby.dec()))
            }
        }



    companion object Factory {
        fun parse(str: String): LanternFish {
            return LanternFish(str.toInt())
        }
    }
}

fun main() {

    val allFish = readFile().first().split(',').map { LanternFish.parse(it) }

    val school = spawn(allFish, 1)

    println (school.size)

}

tailrec fun spawn(allFish: List<LanternFish>, generation: Int): List<LanternFish> {
    if (generation > 80) return allFish

    return spawn(allFish.flatMap { fish -> fish.tickTock() }, generation.inc())
}


