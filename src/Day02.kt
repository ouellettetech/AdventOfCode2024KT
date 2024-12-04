import java.io.File.separator

fun main() {
    fun checkLine(line: String) : Int{
        var curArray = line.split(" ")
        if(curArray.size == 1) {
            return 1
        }
        var decrease = curArray[0].toInt() > curArray[1].toInt()
        var last = curArray[0].toInt()

        for(n in 1..<curArray.size){
            var diff = curArray[n].toInt() - last
            if(decrease) {
                diff = diff * -1
            }
            if(diff <= 0 || diff > 3){
                return 0
            }
            last = (curArray[n].toInt())
        }
        return 1
    }

    fun part1(input: List<String>): Int {
        var total = 0
        for(line in input) {
            var current = checkLine(line)
            line.println()
            current.println()
            total += current
        }
        println("Total: $total")
        return total
    }

    fun removeIndex(line: List<String>, index: Int): String {
        val curArray = line.toMutableList()
        curArray.println()
        curArray.removeAt(index)
        curArray.println()
        var newString = curArray.joinToString(separator = " ")
        println("New String : $newString")
        return newString

    }

    fun part2(input: List<String>): Int {
        var total = 0
        for(line in input) {
            var current = checkLine(line)
            var curArray = line.split(" ")
            curArray.forEachIndexed() { index, _ ->
                if(current == 0) {
                    current = checkLine(removeIndex(curArray, index))
                }
            }
            line.println()
            current.println()
            total += current
        }
        println("Total: $total")
        return total
    }

    // Test if implementation meets criteria from the description, like:
   //check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
