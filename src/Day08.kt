fun main() {

    fun part1(input: List<String>): Number {
        println("Start Part 1")
        var grid = Grid()
        grid.parseInputAntenna(input)
        grid.findAntinodes()
        var total = grid.countAntinodes()
        var printVal = grid.printGraph()
        for(line in printVal){
            println(line)
        }
        println("Total: $total")
        return total
    }



    fun part2(input: List<String>): Number {
        println("Start Part 2")
        var grid = Grid()
        grid.parseInputAntenna(input)
        grid.findHarmonicAntinodes()
        var total = grid.countAntinodes()
        var printVal = grid.printGraph()
        for(line in printVal){
            println(line)
        }
        println("Total: $total")
        return total
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
