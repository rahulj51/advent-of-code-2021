package aoc.d14


class Element(
    val e: Char,
    var next: Element?
) {
    fun toStr(): String = next?.let { charArrayOf(e, it.e).joinToString("") } ?: charArrayOf(e).joinToString("")

    fun steps(): Sequence<Char?> = sequence {
        var n:Element? = this@Element
        do  {
            yield (n?.e)
            n = n?.next
        } while (n != null)

    }

    companion object Factory {
        fun parse(template:String): Element {
            val templateChars = template.toCharArray()
            val ll = Element(e = templateChars.last(), next=null)
            val linkedList = templateChars.take(templateChars.size - 1).foldRight(ll) { e, acc ->
                Element(e = e, next = acc)
            }
            return linkedList
        }

    }
}
