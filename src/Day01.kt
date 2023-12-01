val w2d = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9"
)

fun main() {

    val strings = readInput("Day01_A")
    val sum = strings.sumOf { line ->
        val first = line.first { it.isDigit() }
        val last = line.last { it.isDigit() }
        "$first$last".toInt()
    }
    println("Answer for part 1: $sum")

    val stringsB = readInput("Day01_B")
    val sumB = stringsB.sumOf { string ->
        val word = string.findAnyOf(w2d.keys)
        val digit = string.findAnyOf(w2d.values)
        val firstValue = firstDigit(word, digit)
        val lastWord = string.findLastAnyOf(w2d.keys)
        val lastDigit = string.findLastAnyOf(w2d.values)
        val secondValue = secondDigit(lastWord, lastDigit)
        "$firstValue$secondValue".toInt()
    }
    println("Answer for part 2: $sumB")
}

private fun firstDigit(word: Pair<Int, String>?, digit: Pair<Int, String>?) = word?.let {
    if ((digit?.first ?: Int.MAX_VALUE) > word.first) {
        w2d[word.second]
    } else {
        digit!!.second
    }
} ?: digit!!.second

private fun secondDigit(word: Pair<Int, String>?, digit: Pair<Int, String>?) = word?.let {
    if ((digit?.first ?: -1) > word.first) {
        digit!!.second
    } else {
        w2d[word.second]
    }
} ?: digit!!.second
