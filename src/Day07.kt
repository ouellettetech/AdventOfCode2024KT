fun main() {

    class  Equations(
        val result: Long,
        val parameters: List<Long>,
    ){
        override fun toString(): String{
            return "Equation ${result} = ${parameters.joinToString(",")}"
        }

        fun checkHelper(total: Long, prev: Long,  params: List<Long>, functions: List<(Long,Long) -> Long>): Boolean {
            if(params.isEmpty()) {
                return total == prev
            }
            for (function in functions) {
                if(checkHelper(total, function(prev,params.first()),params.drop(1),functions)) return true
            }
            return false
        }
        fun checkIfValid(): Boolean {
            val add: (Long, Long) -> Long = { x, y -> x + y }
            val multiply: (Long, Long) -> Long = { x, y -> x * y }
            val functions = listOf(add, multiply)

            return checkHelper(result, parameters.first(), parameters.drop(1),functions)
        }
        fun checkIfValidThree(): Boolean {
            val add: (Long, Long) -> Long = { x, y -> x + y }
            val multiply: (Long, Long) -> Long = { x, y -> x * y }
            val concat: (Long, Long) -> Long = { x, y -> (x.toString() + y.toString()).toLong() }
            val functions = listOf(add, multiply,concat)

            return checkHelper(result, parameters.first(), parameters.drop(1),functions)
        }

    }

    fun parseInput(input: List<String>): List<Equations> {
        var retVal = emptyList<Equations>().toMutableList()
        for(line in input){
            val resultSplit = line.split(":")
            val resultVal = resultSplit[0].toLong()
            val rest = resultSplit[1].trim().split(" ")
            val parmVal = rest.map {
                it.toLong()
            }
            retVal.add(Equations(resultVal, parmVal))
        }
        return retVal
    }

    fun part1(input: List<String>): Number {
        println("Start Part 1")
        val inputEquations = parseInput(input)

        var total = 0L
        for(equation in inputEquations){
           if(equation.checkIfValid()){
               println("Current equation = ${equation.result}")
               total+=equation.result
           }

        }
        for(eq in inputEquations){
            println("Equations: ${eq}")
        }
        println("Total: $total")
        return total
    }



    fun part2(input: List<String>): Long {
        println("Start Part 2")
        val inputEquations = parseInput(input)

        var total = 0L
        for(equation in inputEquations){
            if(equation.checkIfValidThree()){
                println("Current equation = ${equation.result}")
                total+=equation.result
            }

        }
        for(eq in inputEquations){
            println("Equations: ${eq}")
        }
        println("Total: $total")
        return total
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
