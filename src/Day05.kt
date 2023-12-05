fun main() {
    part1()
    part2()
}


//region Part 1:
private fun part1() {
    val input = readInput("Day05_A").splitByEmptyLines()
    val seeds = input.first().first().replace("seeds: ", "").split(" ").map { it.trim().toLong() }
    val mappings = createMappings(input)
    val result = seeds.minOf { seed ->
        findLocationForSeed(seed, mappings)
    }
    println("Part 1: $result")
}
//endregion

//region Part 2
private fun part2() {
    val input = readInput("Day05_B").splitByEmptyLines()

    val chunked = input.first().first().replace("seeds: ", "").split(" ").chunked(2)
    val seedRanges = chunked.map {
        val firstValue = it.first().trim().toLong()
        val span = it.last().trim().toLong()
        firstValue..<firstValue + span
    }

    val mappings = createMappings(input)

    var min = Long.MAX_VALUE
    for (range in seedRanges) {
        for (seed in range) {
            val location = findLocationForSeed(seed, mappings)
            if (min > location) {
                min = location
            }
        }
    }

    println("Part 2: $min")
}
//endregion


class Mapping(val name: String, val rules: List<Rule>)
class Rule(val destination: Long, val source: Long, val span: Long)

private fun createMappings(input: MutableList<MutableList<String>>): MutableMap<String, Mapping> {
    val mappings = mutableMapOf<String, Mapping>()
    for (rawMapping in input.drop(1)) {
        val name = rawMapping.first().replace(" map:", "")
        val rules: List<Rule> = rawMapping.drop(1).map { ruleString ->
            val (destination, source, span) = ruleString.split(" ").map { it.trim().toLong() }
            Rule(destination, source, span)
        }
        mappings[name] = Mapping(name, rules)
    }
    return mappings
}

private fun List<String>.splitByEmptyLines(): MutableList<MutableList<String>> =
    fold(mutableListOf(mutableListOf<String>())) { acc, s ->
        if (s.isEmpty()) acc.add(mutableListOf())
        else acc.last().add(s)
        acc
    }

fun List<Rule>.find(number: Long): Long {
    return this
        .firstOrNull { number in it.source..it.source + it.span }
        ?.let { it.destination + number - it.source }
        ?: number
}

// This is ugly. Would love to make this work for any combination of pipelines
fun findLocationForSeed(seed: Long, mappings: Map<String, Mapping>): Long {
    val soil = mappings["seed-to-soil"]?.rules?.find(seed)!!
    val fertilizer = mappings["soil-to-fertilizer"]?.rules?.find(soil)!!
    val water = mappings["fertilizer-to-water"]?.rules?.find(fertilizer)!!
    val light = mappings["water-to-light"]?.rules?.find(water)!!
    val temperature = mappings["light-to-temperature"]?.rules?.find(light)!!
    val humidity = mappings["temperature-to-humidity"]?.rules?.find(temperature)!!
    val location = mappings["humidity-to-location"]?.rules?.find(humidity)!!
    return location
}

//region Test data
val seedMap = """
    seeds: 79 14 55 13

    seed-to-soil map:
    50 98 2
    52 50 48

    soil-to-fertilizer map:
    0 15 37
    37 52 2
    39 0 15

    fertilizer-to-water map:
    49 53 8
    0 11 42
    42 0 7
    57 7 4

    water-to-light map:
    88 18 7
    18 25 70

    light-to-temperature map:
    45 77 23
    81 45 19
    68 64 13

    temperature-to-humidity map:
    0 69 1
    1 0 69

    humidity-to-location map:
    60 56 37
    56 93 4
""".trimIndent().split("\n")
