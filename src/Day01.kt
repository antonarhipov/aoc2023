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
        val firstValue = firstDigit(string)
        val secondValue = secondDigit(string)
        "$firstValue$secondValue".toInt()
    }
    println("Answer for part 2: $sumB")
}

private fun firstDigit(string: String): String {
    val word = string.findAnyOf(w2d.keys) ?: (Int.MAX_VALUE to "")
    val digit = string.findAnyOf(w2d.values) ?: (Int.MAX_VALUE to "")
    return if (word.first > digit.first) {
        digit.second
    } else {
        w2d[word.second]!!
    }
}

private fun secondDigit(string: String): String {
    val word = string.findLastAnyOf(w2d.keys) ?: (-1 to "")
    val digit = string.findLastAnyOf(w2d.values) ?: (-1 to "")
    return if (word.first < digit.first) {
        digit.second
    } else {
        w2d[word.second]!!
    }
}

