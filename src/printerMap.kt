class printerMap constructor(private val providedrules: List<String>) {
    var rules = parseProvidedRules()

    fun parseProvidedRules(): HashMap<Int, MutableList<Int>> {
        var parsed = HashMap<Int,MutableList<Int>>()
        for(rule in providedrules){
            val cur = rule.split("|")
            val first = cur[0].toInt()
            val second = cur[1].toInt()
            if(!parsed.containsKey(first)){
                parsed[first] = emptyList<Int>().toMutableList()
            }
            parsed[first]?.add(second)
        }
        return parsed
    }
    fun convertLineToInt(line: String): List<Int> {
        var intArray = emptyList<Int>().toMutableList()
        for(cur in line.split(",")){
            intArray.add(cur.toInt())
        }
        return intArray
    }
    fun checkIfLineFollowsRule(printerPages: List<Int>): Boolean{
        printerPages.forEachIndexed() { index, page ->
            var curRules= rules[page]
            if(curRules != null){
//                for(rule in curRules){
//                    println("Page: $page Rule: $rule")
//                }
                for(rule in curRules) {
                    val foundIndex = printerPages.indexOf(rule)
                   // println("Rule: $rule Page: $page found $foundIndex Index $index")
                    if ((foundIndex >= 0) && (foundIndex < index))
                    {
                        return false
                    }
                }
            } else {
                println("Page not in RULES!!!!!! $page")
            }
        }
        return true
    }

    fun correctList(intList: List<Int>) : List<Int>{
        var newList = intList.sortedWith <Int>(object : Comparator<Int> {
            override fun compare(o1: Int, o2: Int): Int {
                if(rules[o1]?.contains(o2) == true) {
                    return -1
                }
                if(rules[o2]?.contains(o1) == true) {
                    return 1
                }
                return 0
            }
        })
        //println("Sorted List... ${newList.map { it.toString()}}")
        return newList
    }
}