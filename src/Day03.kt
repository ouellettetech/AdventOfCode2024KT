fun main() {


    fun part1(input: List<String>): Number {
        println("Start Part 1")
        val regex = Regex("mul\\(([0-9]+),([0-9]+)\\)")
        var total = 0
        for(line in input) {
            println("checking line out of : ${input.size} $line")
            val match = regex.findAll(line)
            val results = match.map { it.groupValues[1].toInt()* it.groupValues[2].toInt()}
            results.count().println()
            for(result in results) {
                total += result
            }
            total.println()
        }
        println("Total: $total")
        return total
    }

    fun removeDoDont(input: List<String>): List<String> {
        println("input length: ${input.size}")
        var newList = mutableListOf<String>()

        for(line in input){
            var dostring = line.split("do()").toMutableList()
            println("do count: ${dostring.size}")
            if(newList.size>0 && dostring.size > 0){
                var last = newList.last()
                newList.removeLast()
                dostring.set(0, last + dostring[0])
            }
            newList.addAll(dostring)
        }
        println("newList: ${newList.size}")
        return newList.map {
            var dontValues = it.split("don't()")
            println("don't count: ${dontValues.size}")
            dontValues[0]
        }
    }

    fun part2(input: List<String>): Int {
        println("Start Part 2")
        var newInput = removeDoDont(input)
        val regex = Regex("mul\\(([0-9]+),([0-9]+)\\)")
        var total = 0
        for(line in newInput) {
            println("checking line out of : ${newInput.size} $line")
            val match = regex.findAll(line)
            val results = match.map { it.groupValues[1].toInt()* it.groupValues[2].toInt()}
            results.count().println()
            for(result in results) {
                total += result
            }
            total.println()
        }
        println("Total: $total")
        return total
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    //part1(input).println()
    part2(input).println()
}
