val availableCubes = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14,
)

fun main() {
    solvePart1()
    solvePart2()
    solvePart2B()
}

//region Part 1
fun solvePart1() {
    val strings = readInput("Day02_A")
    val sum = strings.sumOf { line ->
        val game = line.split(": ")
        val gameid = game[0].split(" ")[1].toInt()
        val gamesets = game[1].splitToGameSets()

        if (gamesets.any {
                val cube = parseCubeSet(it)
                val total = availableCubes[cube.color] ?: 0
                total < cube.count
            }) 0 else gameid
    }
    println("Answer for part 1: $sum")
}

private fun String.splitToGameSets() = this.split(Regex("[,;]"))

fun parseCubeSet(cubeSet: String): Cube {
    val cubes = cubeSet.trim().split(" ")
    val count = cubes[0].toInt()
    val color = cubes[1]
    return Cube(count, color)
}

data class Cube(val count: Int, val color: String)
//endregion


//region Part 2
private fun solvePart2() {
    val strings = readInput("Day02_B")
    val sum = strings.sumOf { line ->
        val map = parseLine(line)
        calculatePower(map)
    }
    println("Answer for part 2: $sum")
}

private fun calculatePower(map: Map<String, List<Int>>) =
    map.values.fold(1) { acc, ints -> acc * ints.max() }

private fun parseLine(line: String): Map<String, List<Int>> {
    val game = line.split(": ")
    val gamesets = game[1].splitToGameSets()

    return gamesets.map {
        val (score, color) = it.trim().split(" ")
        color to score.toInt()
    }.groupBy(
        { it.first },
        { it.second }
    )
}
//endregion


//region Part 2B
private fun solvePart2B() {
    val strings = readInput("Day02_B")
    val sum = strings.sumOf { line ->
        val map = computeMaxScores(line)
        calculateProduct(map)
    }
    println("Answer for part 2B: $sum")
}

private fun computeMaxScores(resultsLine: String): Map<String, Int> {
    val gamesData = resultsLine.split(": ")[1]
    return gamesData
        .splitToGameSets()
        .associateByMaxScore()
}

private fun List<String>.associateByMaxScore(): Map<String, Int> {
    return this.map { draw ->
        val (score, color) = draw.trim().split(" ")
        color to score.toInt()
    }.groupBy({ it.first }, { it.second })
        .mapValues { (_, scores) -> scores.max() }
}

private fun calculateProduct(map: Map<String, Int>) = map.values.fold(1) { acc, ints -> acc * ints }
//endregion


//region Test data
val testInput = """
    Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
""".trimIndent().split("\n")
//endregion
