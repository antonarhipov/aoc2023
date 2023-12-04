import kotlin.math.pow

fun main() {
    part1()
}

private fun part1() {
    val input = readInput("Day04_A")

    val sum = input.sumOf { card ->
        val split = card.split(": ")
        val (winningNumbers, availableNumbers) = split[1].split(" | ")
        val winningNumbersList = winningNumbers.trim().split(Regex("\\W+")).map { it.toInt() }.toSet()
        val availableNumbersList = availableNumbers.trim().split(Regex("\\W+")).map { it.toInt() }.toSet()

        val intersect = availableNumbersList.intersect(winningNumbersList)
        val points = 2.0.pow(intersect.count().toDouble() - 1).toInt()
        points
    }
    println("Part1: $sum")
}

//region Test data
val cards = """
    Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
    Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
    Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
    Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
    Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
    Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
""".trimIndent().split("\n")
