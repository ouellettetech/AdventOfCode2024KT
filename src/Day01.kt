fun main() {
    fun part1(input: List<String>): Int {
        println("Starting Part 1")
        var listA = mutableListOf<Number>()
        var listB = mutableListOf<Number>()

        for (line in input) {
            val curLine = line.split(" ")
            listA.add(line.split(" ")[0].toInt())
            listB.add(line.split(" ")[3].toInt())
            println("Line: $line ${curLine[0]} ${curLine[3]}")
        }
        listA.sortBy { it.toInt() }
        listB.sortBy { it.toInt() }
        var total = 0
        for (n in 0..listA.size - 1) {
            total += Math.abs(listA[n].toInt() - listB[n].toInt())
        }
        println("Total: $total")
        return total
    }

    fun part2(input: List<String>): Int {
        println("Starting Part 1")
        var listA = mutableListOf<Number>()
        var listB = mutableListOf<Number>()

        for (line in input) {
            val curLine = line.split(" ")
            listA.add(line.split(" ")[0].toInt())
            listB.add(line.split(" ")[3].toInt())
            println("Line: $line ${curLine[0]} ${curLine[3]}")
        }
        var total = 0
        for (lineAItem in listA) {
            var current = 0
            for(lineBItem in listB) {
                if(lineAItem.toInt() == lineBItem.toInt()) {
                    current += lineAItem.toInt()
                }
            }
            total += current
        }
        println("Total: $total")
        return total
    }

    // Test if implementation meets criteria from the description, like:
   //check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
