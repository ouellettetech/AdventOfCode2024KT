fun main() {


    fun part1(input: List<String>): Number {
        println("Start Part 1")
        var grid = Grid(input)
        grid.moveGuard()
        var total = grid.countVisited()
        var printVal = grid.printGraph()
        for(line in printVal){
            println(line)
        }
        println("Total: $total")
        return total
    }



    fun part2(input: List<String>): Int {
        println("Start Part 2")
        var grid = Grid(input)
        var total = grid.findCycle()
        //var printVal = grid.printGraph()
        //for(line in printVal){
        //    println(line)
        //}
        println("Total: $total")
        return total
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}