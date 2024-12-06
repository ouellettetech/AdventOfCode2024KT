
enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

class Grid(private val gridVal: List<String>) {
    var grid = parseInput(gridVal)
    var curX: Int = 0
    var curY: Int = 0
    var curDirection: Direction = Direction.UP

    class Node{
        var visited = false
        var obstacle = false
        var visitedUP = false
        var visitedDown = false
        var visitedLeft = false
        var visitedRight = false
    }

    private fun parseInput(gridVal: List<String>): List<List<Node>> {
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
        return ret
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

    class coordinates(private var x:Int, private var y:Int) {
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
}