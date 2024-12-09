
enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

class Grid() {
    lateinit var grid : List<List<Node>>
    var curX: Int = 0
    var curY: Int = 0
    var curDirection: Direction = Direction.UP
    var antennas: MutableMap<Char, MutableList<coordinates>> = mutableMapOf()

    class Node{
        var visited = false
        var obstacle = false
        var visitedUP = false
        var visitedDown = false
        var visitedLeft = false
        var visitedRight = false
    }

    fun parseInput(gridVal: List<String>) {
        var ret = ArrayList<ArrayList<Node>>()
        gridVal.forEachIndexed() { y, line ->
            ret.add(ArrayList())
            line.forEachIndexed() { x, entry ->
                var currentSpot = Node()
                currentSpot.visited = false
                when (entry) {
                    '.' -> currentSpot.obstacle = false
                    '#' -> currentSpot.obstacle = true
                    '^' -> {
                        currentSpot.obstacle = false
                        setStart(x, y)
                    }
                }
                ret[y].add(currentSpot)
            }
        }
        grid = ret
    }


    fun parseInputAntenna(gridVal: List<String>) {
        var ret = ArrayList<ArrayList<Node>>()
        gridVal.forEachIndexed() { y, line ->
            ret.add(ArrayList())
            line.forEachIndexed() { x, entry ->
                var currentSpot = Node()
                currentSpot.visited = false
                when (entry) {
                    '.' -> currentSpot.obstacle = false
                    else -> {
                        currentSpot.obstacle = false
                        addAntenna(x,y,entry)
                    }
                }
                ret[y].add(currentSpot)
            }
        }
        grid = ret
    }

    private fun addAntenna(x: Int, y: Int, entry: Char) {
        if(!antennas.containsKey(entry)) {
            antennas[entry] = emptyList<coordinates>().toMutableList()
        }
        antennas[entry]?.add(coordinates(x,y))
    }

    fun setStart(x:Int, y:Int){
        curX = x
        curY = y
        curDirection = Direction.UP
    }
    fun setNode(x:Int, y:Int){

    }

    fun getNode(x: Int,y: Int) : Node? {
        if(x<0 || y<0|| (y>=grid.size) || (x>=grid[y].size)){
            return null
        }
        else {
            return grid[y][x]
        }
    }

    class coordinates(var x:Int, var y:Int) {
        override fun toString(): String {
            return "X: $x Y: $y"
        }
    }

    fun visitedDirection(currentNode: Node) : Boolean {
        when(curDirection){
            Direction.UP -> {
                if(currentNode.visitedUP){
                    return true
                } else {
                    currentNode.visitedUP = true
                    return false
                }
            }
            Direction.DOWN -> {
                if(currentNode.visitedDown){
                    return true
                } else {
                    currentNode.visitedDown = true
                    return false
                }
            }
            Direction.LEFT -> {
                if(currentNode.visitedLeft){
                    return true
                } else {
                    currentNode.visitedLeft = true
                    return false
                }
            }
            Direction.RIGHT -> {
                if(currentNode.visitedRight){
                    return true
                } else {
                    currentNode.visitedRight = true
                    return false
                }
            }
        }
    }

    fun clearNodes(startX: Int,startY:Int){
        curX = startX
        curY = startY
        curDirection =Direction.UP
        for(line in grid){
            for(curNode in line){
                curNode.visited = false
                curNode.visitedUP = false
                curNode.visitedDown = false
                curNode.visitedLeft = false
                curNode.visitedRight = false
            }
        }
    }
    fun findCycle() : Int {
        var startX = curX
        var startY = curY
        var cycles = 0
        var startNode = getNode(startX, startY)
        for(line in grid){
            for(curNode in line){
                if(curNode != startNode && !curNode.obstacle){
                    clearNodes(startX,startY)
                    curNode.obstacle = true
                    if(moveGuard()){
                        cycles++
                    }
                    curNode.obstacle = false
                }
            }
        }
        return cycles
    }
    fun moveGuard() : Boolean {
        var currentNode = getNode(curX,curY)
        currentNode?.visited = true
        while(currentNode != null && !visitedDirection(currentNode)) {
            var newNode: Node?
            when(curDirection){
                Direction.UP -> {
                    newNode = getNode(curX,curY -1)
                    if(newNode != null && newNode.obstacle){
                        curDirection = Direction.RIGHT
                    } else {
                        curY -= 1
                        currentNode = newNode
                    }
                }
                Direction.RIGHT -> {
                    newNode = getNode(curX+1,curY)
                    if(newNode != null && newNode.obstacle){
                        curDirection = Direction.DOWN
                    } else {
                        curX += 1
                        currentNode = newNode
                    }
                }
                Direction.DOWN -> {
                    newNode = getNode(curX,curY+1)
                    if(newNode != null && newNode.obstacle){
                        curDirection = Direction.LEFT
                    } else {
                        curY += 1
                        currentNode = newNode
                    }
                }
                Direction.LEFT -> {
                    newNode = getNode(curX-1,curY)
                    if(newNode != null && newNode.obstacle){
                        curDirection = Direction.UP
                    } else {
                        curX -= 1
                        currentNode = newNode
                    }
                }
            }
            if(newNode != null){
                newNode.visited = true
            }
        }
        if(currentNode != null){
            return true
        }
        return false
    }
    fun printGraph(): List<String>{
        var printVal = mutableListOf<String>()
        for(line in grid){
            var curLine : ArrayList<Char> = arrayListOf()
            for(spot in line){
                if(spot.obstacle){
                    curLine.add('#')
                } else {
                    if(spot.visited){
                        curLine.add('X')
                    } else {
                        curLine.add('.')
                    }
                }

            }
            printVal.add(curLine.joinToString(""))
        }
        return printVal
    }

    fun countVisited(): Int {
        var count = 0
        for(line in grid){
            for(cur in line){
                if(!cur.obstacle && cur.visited){
                    count++
                }
            }
        }
        return count
    }

    fun findAntinodes() {
        for(currentFreq in antennas.keys){
            println("Looking at Freq: $currentFreq")
            val currentFreqValues = antennas[currentFreq]
            if (currentFreqValues != null) { //since this is in keys should never be null....
                for(first in 0..<currentFreqValues.size){
                    for(second in first+1..<currentFreqValues.size) {
                        findAntinode(currentFreqValues[first],currentFreqValues[second])
                    }
                }
            }
        }
    }

    private fun setAntinode(curX:Int,curY: Int){
        var node = getNode(curX,curY)
        if(node != null){
            node.obstacle = true
        }
    }
    private fun findAntinode(first: Grid.coordinates, second: Grid.coordinates) {
        var xDiff = first.x - second.x
        var yDiff = first.y - second.y
        var minFirstX = first.x - xDiff
        var minFirstY = first.y - yDiff
        var minSecondX = second.x - xDiff
        var minSecondY = second.y - yDiff
        var minX: Int
        var minY: Int
        var maxX: Int
        var maxY: Int
        if(minFirstX == second.x){
            minY = minSecondY
            minX = minSecondX
        }
        else {
            minY = minFirstY
            minX = minFirstX
        }
        var maxFirstX = first.x + xDiff
        var maxFirstY = first.y + yDiff
        var maxSecondX = second.x + xDiff
        var maxSecondY = second.y + yDiff
        if(maxFirstX == second.x){
            maxX = maxSecondX
            maxY = maxSecondY
        }
        else {
            maxX = maxFirstX
            maxY = maxFirstY
        }

        println("Finding Antinode for ${first.x},${first.y} and ${second.x},${second.y} Setting to min: $minX,$minY and Max: $maxX, $maxY")
        setAntinode(minX,minY)
        setAntinode(maxX,maxY)
    }

    fun countAntinodes(): Number {
        var total = 0
        for(line in grid){
            for(curNode in line){
                if(curNode.obstacle){
                    total++
                }
            }
        }
        return total
    }

    private fun findHarmonicAntinode(first: Grid.coordinates, second: Grid.coordinates) {
        var xDiff = first.x - second.x
        var yDiff = first.y - second.y

        println("Finding Antinode for ${first.x},${first.y} and ${second.x},${second.y}")
        var setNextNode = setNextAntnode(coordinates(first.x,first.y),0,0)
        while(setNextNode!=null)
        {
            println("Finding Antinode for ${first.x},${first.y} and ${second.x},${second.y} Setting to: ${setNextNode?.x},${setNextNode?.y}")
            setNextNode = setNextAntnode(setNextNode,xDiff,yDiff)
        }
        setNextNode = setNextAntnode(coordinates(first.x,first.y),0,0)
        while(setNextNode!=null)
        {
            println("Finding Antinode for ${first.x},${first.y} and ${second.x},${second.y} Setting to: ${setNextNode?.x},${setNextNode?.y}")
            setNextNode = setNextAntnode(setNextNode,xDiff*-1,yDiff*-1)
        }

    }

    fun setNextAntnode(currentNode: Grid.coordinates, xDiff: Int, yDiff: Int): Grid.coordinates? {
        var newX = currentNode.x+xDiff
        var newY = currentNode.y+yDiff
        var nextNode = getNode(newX,newY)
        if(nextNode != null){
            nextNode.obstacle = true
            return coordinates(newX,newY)
        }
        return null
    }

    fun findHarmonicAntinodes() {
        for (currentFreq in antennas.keys) {
            println("Looking at Freq: $currentFreq")
            val currentFreqValues = antennas[currentFreq]
            if (currentFreqValues != null) { //since this is in keys should never be null....
                for (first in 0..<currentFreqValues.size) {
                    for (second in first + 1..<currentFreqValues.size) {
                        findHarmonicAntinode(currentFreqValues[first], currentFreqValues[second])
                    }
                }
            }
        }
    }
}