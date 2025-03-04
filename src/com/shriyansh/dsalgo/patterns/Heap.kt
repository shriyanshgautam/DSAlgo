package com.shriyansh.dsalgo.patterns

import java.util.PriorityQueue

// https://medium.com/@stephen.joel/two-heaps-median-f28ebc1569d7

fun main() {
    val input = arrayOf(2, 4, 7, 3, 9 ,5 ,1)
    println(findTopK(input, 3))

    val receivers = arrayOf(Receiver(3.0, Coordinates(1.0,2.0)))
    println(findTopKByAttribute(receivers, 1))
}

data class Coordinates(
    var x: Double,
    var y: Double
)

data class Receiver(
    var distance: Double,
    var coordinates: Coordinates
)

fun findTopK(arr: Array<Int>, k: Int): List<Int> {
    val heap: PriorityQueue<Int> = PriorityQueue { a, b -> b - a } // Condition for max heap
    for (item in arr) {
        if (heap.size >= k) {
            val top = heap.peek()
            if (item < top) {
                heap.poll()
                heap.add(item)
            }
        } else {
            heap.add(item)
        }
    }

    val result = mutableListOf<Int>()
    while (!heap.isEmpty()) {
        result.add(heap.poll())
    }
    return result
}


/*
* Can also be used for top k frequent items
*/
fun findTopKByAttribute(arr: Array<Receiver>, k: Int): Set<Coordinates> {
    val heap: PriorityQueue<Receiver> = PriorityQueue { a, b -> (b.distance - a.distance).toInt() }
    for (item in arr) {
        if (heap.size >= k) {
            val top = heap.poll()
            if (item.distance < top.distance) {
                heap.poll()
                heap.add(item)
            }
        } else {
            heap.add(item)
        }
    }

    val result = mutableSetOf<Coordinates>()
    while (!heap.isEmpty()) {
        result.add(heap.poll().coordinates)
    }
    return result
}


/**
 *
 */
fun kWayMerge() {

}