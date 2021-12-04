package aoc.d2

import java.io.File

enum class DIRECTION {
    forward, up, down
}

data class Command (
        val type: DIRECTION,
        val amount: Int
){
    companion object Factory {
        /**
         * parses a line of input into a Command object
         * For ex. "forward 8" or "up 9" etc.
         */
        fun parse(str: String) =
            str.split(' ').let {
                Command(DIRECTION.valueOf(it.first()), it.last().toInt())
            }

    }

}

private data class Submarine(
        val position: Int = 0,
        val aim: Int = 0,
        val depth:Int = 0
)
{
    fun move(command: Command) =

        when(command.type) {
            DIRECTION.down -> copy( aim = aim + command.amount)
            DIRECTION.up -> copy(aim = aim - command.amount)
            DIRECTION.forward -> copy( position = position + command.amount,
                    depth = depth + (aim * command.amount))
        }

    fun myRelativePosition(): Int = position * depth

}

fun main() {
    val inp = readFile()
    val commands = inp.map { Command.parse(it) }
    val submarine = commands.fold(Submarine()) { sub, comm -> sub.move(comm) }
    println(submarine)
    println(submarine.myRelativePosition())
}
