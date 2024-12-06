import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)




class GridString(private val gridVal: List<String>) {

    fun getChar(x: Int,y: Int) : Char? {
        if(x<0 || y<0|| (y>=gridVal.size) || (x>=gridVal[y].length)){
            return null
        }
        else {
            return gridVal[y][x]
        }
    }

    class coordinates(private var x:Int, private var y:Int) {
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
