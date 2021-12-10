package aoc.d6


/*
According to hints from the internet (https://todd.ginsberg.com/post/advent-of-code/2021/day6/),
this needs to be solved in constant space.
So we basically maintain a ledger (count of how many fish are due) for each day
Each element represents the count of fish with a clock=index of the element
 */
data class SmartLanternFishSchool(
        val ledger: LongArray
){
    fun tickTock(): SmartLanternFishSchool {
        //rotate
        val newLedger = LongArray(9) { i -> 0}
        val aboutToDeliver = ledger.first()
        ledger.copyInto(newLedger, startIndex = 1)
        newLedger[6] += aboutToDeliver //add parents
        newLedger[8] = aboutToDeliver //add new fish (equals birthing since each fish produces one fish each)
        return copy(ledger=newLedger)
    }

    companion object Factory {
        fun init(fishes: List<Int>): SmartLanternFishSchool {
            val ledger =  LongArray(9) { i -> 0}
            fishes.forEach { fish ->
                ledger[fish] = ledger[fish].inc()
            }
            return SmartLanternFishSchool(ledger)
        }
    }
}

fun main() {

    val allFish = readFile().first().split(',').map { it.toInt() }

    var school = SmartLanternFishSchool.init(allFish)

    (1..256).forEach {
        school = school.tickTock()
    }

    println(school.ledger.sum())




}

