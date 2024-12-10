fun main() {

    fun part1(input: List<String>): Number {
        println("Start Part 1")
        var grid = Grid()
        grid.parseInputTopoGraph(input)
        var total = grid.findAllTrails()
        println("Total: $total")
        return total
    }

    fun part2(input: List<String>): Number {
        println("Start Part 1")
        var grid = Grid()
        grid.parseInputTopoGraph(input)
        var total = grid.findAllDistinctTrails()
        println("Total: $total")
        return total
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36L)
    check(part2(testInput) == 81L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}