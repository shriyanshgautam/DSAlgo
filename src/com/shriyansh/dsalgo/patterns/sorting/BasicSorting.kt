package com.shriyansh.dsalgo.patterns.sorting

fun main() {
    val list = mutableListOf(7, 3, 0, 2, 10 ,5 ,4)
    println(selectionSort(list))
    println(bubbleSort(list))
    println(insertionSort(list))
}


/**
 *
 * for each item i in list
 *  iterate remaining on right side to find any value less than i
 *  swap it with i
 */
fun selectionSort(list: MutableList<Int>): List<Int> {
    for (i in 0..<(list.size)) {
        var minIndex = i
        for (j in i + 1..<list.size) {
            if (list[j] < list[minIndex]) {
                minIndex = j
            }
        }
        // Swap
        val temp = list[i]
        list[i] = list[minIndex]
        list[minIndex] = temp
    }
    return list
}

/**
 * track swapped flag, mark it true to start
 * while swapped flag is true
 *  mark swapped flag as false
 *  for each element i from 0 to size - 1
 *      check if i > i + 1 and swap if it is and enable swapped flag
 */
fun bubbleSort(list: MutableList<Int>): List<Int> {
    var swapped = true
    while (swapped) {
        swapped = false
        for (i in 0..<list.size - 1) {
            if (list[i] > list[i + 1]) {
                //Swap
                val temp = list[i]
                list[i] = list[i + 1]
                list[i + 1] = temp
                swapped = true
            }
        }
    }
    return list
}

/**
 * for i in 1 to size
 *  starting with i as index
 *  while i > 0 and i - 1 > 1
 *      swap and decrement i
 */
fun insertionSort(list: MutableList<Int>): List<Int> {
    for (i in 1..<list.size) {
        var index = i
        while (index > 0 && list[i - 1] > list[i]) {
            // Swap
            val temp  = list[index - 1]
            list[index - 1] = list[index]
            list[index] = temp
            index--
        }
    }
    return list
}

fun heapSort(list: MutableList<Int>) {

}