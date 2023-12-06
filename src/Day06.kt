fun main() {
    val times = part1input[0].toValues()
    val distances = part1input[1].toValues()

    println("Part 1: ${calculateProductOfExceedingDistances(times, distances)}")

    val time = part2input[0].toValue()
    val distance = part2input[1].toValue()
    println("Time: $time, distance: $distance")
    println("Part 2: ${calculateProductOfExceedingDistances(listOf(time), listOf(distance))}")
}

private fun calculateProductOfExceedingDistances(times: List<Long>, distances: List<Long>) =
    times.foldIndexed(initial = 1L) { index, product, time ->
        val maxAllowedDistance = distances[index]
        val exceedingDistanceCount = calculateExceedingDistanceCount(time, maxAllowedDistance)
        product * exceedingDistanceCount
    }

private fun calculateExceedingDistanceCount(time: Long, maxAllowedDistance: Long) =
    (1..time).count { i ->
        val remainingTime = time - i
        val distance = remainingTime * i
        distance > maxAllowedDistance
    }.toLong()

fun String.toValue() =
    this.split(Regex(":\\W+"))[1].split(Regex("\\W+")).joinToString("").toLong()

fun String.toValues() =
    (Regex("(\\d+)").findAll(this)).map { it.value.toLong() }.toList()

//region Test data
@Suppress("unused")
val races = """
    Time:      7  15   30
    Distance:  9  40  200
""".trimIndent().split("\n")

val part1input = """
    Time:        40     82     91     66
    Distance:   277   1338   1349   1063
""".trimIndent().split("\n")

val part2input = """
    Time:        40     82     91     66
    Distance:   277   1338   1349   1063
""".trimIndent().split("\n")

