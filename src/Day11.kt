fun main() {

    fun part1(input: List<String>): Number {
        println("Start Part 1")
        var stones = Stones(input[0])
        for(i in 1..25) {
            stones.runRules()
            //println("New Stones: ${stones.stones}")
            println("stones; ${stones.countStones()}")
        }
        var total = stones.countStones()
        println("Total: $total")
        return total
    }

    fun part2(input: List<String>): Number {
        println("Start Part 2")
        var stones = hashStones(input[0])
        for(i in 1..75) {
            stones.runRules()
            //println("New Stones: ${stones.stones}")
            println("$i stones; ${stones.countStones()}")
        }
        var total = stones.countStones()
        println("Total: $total")
        return total
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 55312)
    //check(part2(testInput) == 55312L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}

class hashStones(input: String) { //75 runs is way more memory than I have, lol about 112TB of data...
    var stonesCount = importStones(input)
    var stonesMap = HashMap<Long, List<Long>>()

    fun importStones(input: String) : HashMap<Long, Long> {
        var myStones = hashMapOf<Long, Long>()
        for (stoneVal in input.split(" ")){
            myStones[stoneVal.toLong()] = 1L
        }
        return myStones
    }

    fun calcNewStones(stone: Long) : List<Long>{
        val rule1: (Long) -> List<Long>? = { x ->
            if (x == 0L) {
                listOf(1L)
            } else {
                null
            }
        }
        val rule2: (Long) -> List<Long>? = { x ->
            var temp = x.toString()
            if (temp.length % 2 == 0) {
                var middle = temp.length / 2
                listOf(temp.substring(0, middle).toLong(), temp.substring(middle).toLong())
            } else {
                null
            }
        }
        val rule3: (Long) -> List<Long>? = { x ->
            listOf(x * 2024L)
        }
        val rules = listOf(rule1, rule2, rule3)
        var result: List<Long>? = null
        for (rule in rules) {
            if (result == null) {
                result = rule(stone)
            }
        }
        if(result == null) {
            return emptyList()
        }
        return result
    }

    fun addStone(stone: Long){
        if(stonesCount[stone] == null){
            stonesCount[stone] = 1
            stonesMap[stone] = calcNewStones(stone)
        }
        else {
            stonesCount[stone] = stonesCount[stone]!! + 1 // can't be null I just checked above.
        }
    }
    fun runRules(){
        var newCount = HashMap<Long, Long>()

        for(stone in stonesCount.keys){
            if(stonesMap[stone] == null){
                stonesMap[stone] = calcNewStones(stone)
            }

            for(newStone in stonesMap[stone]?: emptyList()){
                var newValue = (newCount[newStone] ?: 0L) + (stonesCount[stone] ?: 0L)
                newCount[newStone] = newValue
            }
        }
        stonesCount = newCount
    }

    fun countStones() : Long{
        var sum = 0L
        for(stoneVal in stonesCount.values){
            sum += stoneVal
        }
        return sum
    }
}
class Stones(input: String) {
    var stones = input.split(" ").map { it.toLong() }

    fun runRules() {
        val rule1: (Long) -> List<Long>? = { x ->
            if (x == 0L) {
                listOf(1L)
            } else {
                null
            }
        }
        val rule2: (Long) -> List<Long>? = { x ->
            var temp = x.toString()
            if (temp.length % 2 == 0) {
                var middle = temp.length / 2
                listOf(temp.substring(0, middle).toLong(), temp.substring(middle).toLong())
            } else {
                null
            }
        }
        val rule3: (Long) -> List<Long>? = { x ->
            listOf(x * 2024L)
        }
        var newStones = emptyList<Long>().toMutableList()
        val rules = listOf(rule1, rule2, rule3)
        for (stone in stones) {
            var result: List<Long>? = null
            for (rule in rules) {
                if (result == null) {
                    result = rule(stone)
                }
            }
            if (result != null) {
                newStones.addAll(result)
            }
        }
        stones = newStones
    }

    fun countStones(): Int {
        return stones.size
    }
}