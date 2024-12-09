fun main() {

    fun part1(input: List<String>): Number {
        val zeroCode = '0'.code
        var compactDisk = input[0]
        println("Start Part 1")
        var total = 0L
        var front = 0
        var frontFile = 0
        var end = compactDisk.length-1
        var endFile = (compactDisk.length-1)/2
        var empty = false
        var endLeft = compactDisk[end].code - zeroCode
        var newArrayIdx = 0
        while(front < end){
            var current = compactDisk[front].code - zeroCode
            println("Current code: $current")
            for(i in 0..<current) {
                if (!empty) {
                    total += (newArrayIdx * frontFile)
                    println("Adding    $newArrayIdx * $frontFile ${newArrayIdx * frontFile} Total: $total")
                    newArrayIdx++
                } else {
                    while (endLeft == 0) {
                        end--//skip blank one.
                        end--
                        endLeft = compactDisk[end].code - zeroCode
                        endFile--
                    }

                    total += newArrayIdx * endFile
                    println("AddingNeg $newArrayIdx * $endFile ${newArrayIdx * endFile} Total: $total")
                    endLeft--
                    newArrayIdx++
                }
            }
            if(!empty) {
                frontFile++
            }
            println("Incrementing front....")
            front++
            empty = !empty
        }
        while(endLeft>0){
            total += newArrayIdx * endFile
            println("AddingNeg $newArrayIdx * $endFile ${newArrayIdx * endFile} Total: $total")
            endLeft--
            newArrayIdx++
        }

        println("Total: $total")
        return total
    }

    fun part2(input: List<String>): Number {
        println("Start Part 2")
        var disk = DiskFile()
        disk.parseFile(input[0])
        disk.compress()
        var total = disk.checkSum()
        println("Total: $total")
        return total
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}

class File(var size: Int,val fileName: Long, val empty: Boolean) {

}
class DiskFile{
    private val zeroCode = '0'.code
    var files: MutableList<File> = mutableListOf()
    fun parseFile(input: String){
        var empty = false
        var fileNum = 0L
        for(currentChar in input){
            var current = currentChar.code - zeroCode
            files.add(File(current,fileNum,empty))
            if(!empty) {
                fileNum++
            }
            empty = !empty
        }
    }
    fun compress(){
        var fileNum = files.size-1
        while(fileNum>0){
            if(files[fileNum].empty){
                fileNum--
                continue
            }
            println(" Checking file $fileNum Value: ${files[fileNum].fileName}")
            var found = false
            var i =0
            while(!found && i <fileNum){
                if(files[i].empty && files[i].size>=files[fileNum].size){
                    files[i].size -= files[fileNum].size
                    files.add(i,files[fileNum])
                    fileNum++
                    val emptyFile = File(files[fileNum].size,-1,true)
                    files.removeAt(fileNum)
                    files.add(fileNum,emptyFile)
                    found = true
                }
                i++
            }
            fileNum--
        }
    }
    fun checkSum(): Long {
        var sum = 0L
        var newArrayIdx = 0L
        for(file in files){
            for (i in 1..file.size) {
                if (!file.empty) {
                    println("Adding    $newArrayIdx * ${file.fileName} ${newArrayIdx * file.fileName} Total: $sum")
                    sum += (newArrayIdx * file.fileName)
                }
                newArrayIdx++
            }
        }
        return sum
    }
}