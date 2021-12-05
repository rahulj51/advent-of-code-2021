package aoc.d3

fun main() {
    val readings = readFile()
    /**
     * from 0-11,
     *
     */
    val range = readings.first().indices
    println(range)


    fun oxygenGeneratorRatingCriteria (l: List<String>, index: Int) : List<String> {
        if (l.size == 1) return l

        val (ones, zeros) = l.partition { it -> it[index] == '1' }

        if (ones.size >= zeros.size)
            return ones
        else
            return zeros
    }

    fun co2ScrubberRatingCriteria (l: List<String>, index: Int) : List<String> {
        if (l.size == 1) return l

        val (ones, zeros) = l.partition { it -> it[index] == '1' }

        if (zeros.size <= ones.size)
            return zeros
        else
            return ones
    }


    val oxygenGeneratorRating = range.fold(readings,::oxygenGeneratorRatingCriteria)
    println(oxygenGeneratorRating)

    val co2ScrubberRating = range.fold(readings,::co2ScrubberRatingCriteria)
    println(co2ScrubberRating)

    val lifeSupportRating = oxygenGeneratorRating.joinToString("").toInt(2) *
            co2ScrubberRating.joinToString("").toInt(2)

    println(lifeSupportRating)

}