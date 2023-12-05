fun main() {
    part1()
}

class Mapping(val name: String, val rules: List<Rule>)
class Rule(val destination: Long, val source: Long, val span: Long)

private fun part1() {
    val input = readInput("Day05_A")
    val splits = input.fold(mutableListOf(mutableListOf<String>())) { acc, s ->
        if (s.isEmpty()) acc.add(mutableListOf())
        else acc.last().add(s)
        acc
    }

    val seeds = splits.first().first().replace("seeds: ", "").split(" ").map { it.trim().toLong() }

    val mappings = mutableMapOf<String, Mapping>()
    for (rawMapping in splits.drop(1)) {
        val name = rawMapping.first().replace(" map:", "")
        val rules: List<Rule> = rawMapping.drop(1).map { ruleString ->
            val (destination, source, span) = ruleString.split(" ").map { it.trim().toLong() }
            Rule(destination, source, span)
        }
        mappings[name] = Mapping(name, rules)
    }

    val result = seeds.minOf { seed ->
        findLocationForSeed(seed, mappings)
    }
    println("Part 1: $result")

}

fun List<Rule>.find(number: Long): Long {
    for (rule in this) {
        if (number in rule.source..rule.source + rule.span) {
            return rule.destination + number - rule.source
        }
    }
    return number
}

fun findLocationForSeed(seed: Long, mappings: Map<String, Mapping>) : Long {
    val soil = mappings["seed-to-soil"]?.rules?.find(seed)!!
    val fertilizer = mappings["soil-to-fertilizer"]?.rules?.find(soil)!!
    val water = mappings["fertilizer-to-water"]?.rules?.find(fertilizer)!!
    val light = mappings["water-to-light"]?.rules?.find(water)!!
    val temperature = mappings["light-to-temperature"]?.rules?.find(light)!!
    val humidity = mappings["temperature-to-humidity"]?.rules?.find(temperature)!!
    val location = mappings["humidity-to-location"]?.rules?.find(humidity)!!

    println("Seed $seed, soil $soil, fertilizer $fertilizer, water $water, light $light, temperature $temperature, humidity $humidity, location $location.")
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
