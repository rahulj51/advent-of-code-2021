package aoc.d11

data class Point(
    val x : Int,
    val y : Int
) {

    fun adjacent(): List<Point> =
        listOf(
            Point(this.x + 1, this.y),
            Point(this.x - 1, this.y),
            Point(this.x, this.y + 1),
            Point(this.x, this.y - 1),
            //4 diagonals
            Point(this.x - 1 , this.y - 1),
            Point(this.x - 1 , this.y + 1),
            Point(this.x + 1 , this.y - 1),
            Point(this.x + 1 , this.y + 1)
        )

    companion object Factory {
        fun parse(arr: List<List<Int>>) =
            arr.flatMapIndexed { y, row -> row.mapIndexed { x, col -> Point(x, y) } }
    }
}
