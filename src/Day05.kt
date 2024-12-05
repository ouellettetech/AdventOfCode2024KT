fun main() {


    fun part1(input: List<String>): Number {
        println("Start Part 1")
        var total = 0
        val breakpoint = input.indexOfFirst {it.isEmpty()}
        var printer = printerMap(input.subList(0,breakpoint))
        var pages = input.subList(breakpoint+1,input.size)
//        for(line in input.subList(0,breakpoint))
//        {
//            println("Line1 $line")
//        }
//        for(line in input.subList(breakpoint+1,input.size))
//        {
//            println("Line2 $line")
//        }
        for(page in pages){
            val intList = printer.convertLineToInt(page)
            if(printer.checkIfLineFollowsRule(intList)){
                println("Checking $page valid")
                total+=intList[(intList.size/2)]
            } else {
                println("Checking $page INVALID")
            }
        }
        return total
    }



    fun part2(input: List<String>): Int {
        println("Start Part 2")
        var total = 0
        val breakpoint = input.indexOfFirst {it.isEmpty()}
        val printer = printerMap(input.subList(0,breakpoint))
        val pages = input.subList(breakpoint+1,input.size)
        for(page in pages){
            val intList = printer.convertLineToInt(page)
            if(printer.checkIfLineFollowsRule(intList)){
                println("Checking $page valid")
            } else {
                val newList = printer.correctList(intList)
                total+=newList[(newList.size/2)]
                println("Checking $page INVALID")
            }
        }
        return total
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

