fun main() {


    fun part1(input: List<String>): Number {
        println("Start Part 1")
        var total = 0
        var wordSearch = Grid(input)
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
        var wordSearch = Grid(input)
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


class Grid constructor(private val gridVal: List<String>) {

    fun getChar(x: Int,y: Int) : Char? {
        if(x<0 || y<0|| (y>=gridVal.size) || (x>=gridVal[y].length)){
            return null
        }
        else {
            return gridVal[y][x]
        }
    }

    class coordinates constructor(private var x:Int, private var y:Int) {
        override fun toString(): String {
            return "X: $x Y: $y"
        }
    }

    fun findXMas(x:Int, y:Int) : Int {
        if(getChar(x,y)!='A'){
            return 0
        }
        var UL = getChar(x-1,y-1)
        var UR = getChar(x+1,y-1)
        var LL = getChar(x-1,y+1)
        var LR = getChar(x+1,y+1)
        println("Checking $x,$y UL: $UL LR: $LR UR: $UR LL: $LL")
        var diag1 = (UL== 'M' && LR == 'S') || (UL== 'S' && LR == 'M')
        var diag2 = (UR== 'M' && LL == 'S') || (UR== 'S' && LL == 'M')
        if(diag1 && diag2){
            return 1
        } else {
            return 0
        }
    }

    fun checkForWord(wordToFind: String, x: Int, y: Int) : Int{
        var found = 0
        found+=checkForWordDirection(wordToFind,x,y,-1,-1)
        found+=checkForWordDirection(wordToFind,x,y, 0,-1)
        found+=checkForWordDirection(wordToFind,x,y, 1,-1)
        found+=checkForWordDirection(wordToFind,x,y,-1,0)
        found+=checkForWordDirection(wordToFind,x,y,1,0)
        found+=checkForWordDirection(wordToFind,x,y,-1,1)
        found+=checkForWordDirection(wordToFind,x,y,0,1)
        found+=checkForWordDirection(wordToFind,x,y,1,1)
        return found
    }

    fun checkForWordDirection(wordToFind: String, x: Int, y: Int, dirX: Int, dirY: Int, path: MutableList<coordinates> = emptyList<coordinates>().toMutableList()) : Int{

        var firstChar = wordToFind.first()
        var restWord = wordToFind.drop(1)
        if(getChar(x,y) != firstChar){
            //println("Not Found $wordToFind, at x $x, y $y")
            return 0
        }
        if(restWord.length==0){

            var StrPath = ""
            for(elm in path){
                StrPath += "[$elm] "
            }
            println("Found $wordToFind, at x $x, y $y path: $StrPath")
            return 1
        }
        path.add(coordinates(x,y))
        return checkForWordDirection(restWord,x+dirX,y+dirY, dirX,dirY, path)
    }

    fun checkForWordAnywhere(wordToFind: String, x: Int, y: Int, path: MutableList<coordinates> = emptyList<coordinates>().toMutableList()) : Int{

        var firstChar = wordToFind.first()
        var restWord = wordToFind.drop(1)
        if(getChar(x,y) != firstChar){
            //println("Not Found $wordToFind, at x $x, y $y")
            return 0
        }
        if(restWord.length==0){

            var StrPath = ""
            for(elm in path){
                StrPath += "[$elm] "
            }
            println("Found $wordToFind, at x $x, y $y path: $StrPath")
            return 1
        }
        var found = 0;
        path.add(coordinates(x,y))
        found+=checkForWordAnywhere(restWord,x-1,y-1, path)
        found+=checkForWordAnywhere(restWord,x,y-1, path)
        found+=checkForWordAnywhere(restWord,x+1,y-1, path)
        found+=checkForWordAnywhere(restWord,x-1,y, path)
        found+=checkForWordAnywhere(restWord,x+1,y, path)
        found+=checkForWordAnywhere(restWord,x-1,y+1, path)
        found+=checkForWordAnywhere(restWord,x,y+1, path)
        found+=checkForWordAnywhere(restWord,x+1,y+ 1, path)
        println("Found Sub $wordToFind, at x $x, y $y found $found")
        return found
    }
}
