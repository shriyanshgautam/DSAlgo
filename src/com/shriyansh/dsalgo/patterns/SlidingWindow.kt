package com.shriyansh.dsalgo.patterns


fun main() {
    // println(dynamicUniqueCharacter(arrayOf('a', 'a', 'b', 'a', 'c', 'b', 'e', 'b', 'e', 'b', 'e'), 3))

    for (i in 10 downTo 1) {
        println(i)
    }
}


fun dynamicUniqueCharacter(arr: Array<Char>, k: Int): Int {
    var start = 0
    var map = mutableMapOf<Char, Int>()
    var ans = 0
    for (end in 0..<arr.size ) {
        var counter = map.getOrPut(arr[end]) { 0 }
        counter++
        map[arr[end]] = counter

        while (map.size > k) {
            if (map.containsKey(arr[start])) {
                var count = map[arr[start]]!!
                count--
                if (count <= 0) { map.remove(arr[start])} else { map[arr[start]] = count }
                start++
            }
        }
        ans = Math.max(ans, end - start + 1)
        println("${start}-${end}-${ans}")
    }
    return ans
}

