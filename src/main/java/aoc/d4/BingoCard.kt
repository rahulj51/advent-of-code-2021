package aoc.d4

import java.io.File

//Using the logic/code from https://todd.ginsberg.com/post/advent-of-code/2021/day4/

typealias BingoCard = List<List<Int>>

fun readFile(): List<String> {
    return File("src/main/java/aoc/d4/input.dat").readLines()
}

data class Deck(
        val draws: List<Int>,
        val cards: List<BingoCard>
) {

    companion object Factory {

        fun parse(lines: List<String>): Deck {
            val draws = lines.first().split(',').map { it.toInt() }
            val cardLines = lines.drop(2)
                    .filter { it.trim().isNotEmpty() } //remove blank lines
                    .chunked(5) //group into 5s for each card

            val cards = cardLines.map { convertToListList(it) }

            return Deck(draws, cards)
        }

        private fun convertToListList(bingoString: List<String>): BingoCard {
            return bingoString.map { it ->
                it.trim().split("\\s+".toRegex()). //trim and split by space
                map { it.toInt() } } //convert each to a number
        }
    }

}


fun main() {
    val deck = Deck.parse(readFile())

    val drawn = mutableSetOf<Int>()

    val score = part1(deck, drawn)
    println(score)

    drawn.clear()

    val score2 = part2(deck, drawn)
    println(score2)

}

fun part2(deck: Deck, drawn: MutableSet<Int>): Int {

    //keep finding the winner till you run out of them
    var remainingCards = deck.cards
    var lastWinner: BingoCard = mutableListOf()
    var score = 0
    deck.draws.forEach { draw ->
        drawn.add(draw)
        remainingCards.forEach {
            if (it.isWinner(drawn)) {
                lastWinner = it
                score = drawn.last() * lastWinner.sumUnmarked(drawn)
            }
        }
        remainingCards = remainingCards.filter { ! it.isWinner(drawn) }
    }

    return score


}

//very smart use of Kotlin APIs !!!!
// loop through the draws and return the first time the inner function computes not-null
// add each draw to drawn
// loop through the cards and return the first card (or null) that is a winner after each draw
//for this card (when not null), compute the final score
private fun part1(deck: Deck, drawn: MutableSet<Int>): Int {
    val score = deck.draws.firstNotNullOf { draw ->
        drawn.add(draw)
        deck.cards.firstOrNull { it.isWinner(drawn) }
                ?.let { winner -> draw * winner.sumUnmarked(drawn) }

    }
    return score
}

fun BingoCard.sumUnmarked(drawn: MutableSet<Int>): Int =
        //filter out the elements already drawn and then sum them
        //aggregate over all rows (sumOf)
        this.sumOf { row ->
            row.filterNot { it in drawn }.sum()
        }


fun BingoCard.isWinner(drawn: MutableSet<Int>) =
        //any row where all elements are in the drawn list
        this.any { row -> row.all { it in drawn } } ||
                //or for each col, where all elements (i.e. rows)
                //have the column'th element in the drawn list
                (0..4).any { col -> this.all { row -> row[col] in drawn } }
