fun main() {


    fun part1(input: List<String>): Number {
        println("Start Part 1")
        var total = 0
        var wordSearch = GridString(input)
        for(y in 0..input.size-1){
            for(x in 0..input[y].length-1){
                total += wordSearch.checkForWord("XMAS", x, y)
            }
        }
        println("Total: $total")
        return total
    }



    fun part2(input: List<String>): Int {
        println("Start Part 2")
        var total = 0
        var wordSearch = GridString(input)
        for(y in 0..input.size-1){
            for(x in 0..input[y].length-1){
                var result = wordSearch.findXMas(x, y)
                if(result == 1){
                    println("Found X: $x Y: $y")
                }
                total += wordSearch.findXMas(x, y)
            }
        }
        println("Total: $total")
        return total
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}