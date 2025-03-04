package com.shriyansh.dsalgo.patterns


fun main() {
    val array = arrayOf(1, 3, 6, 8, 10, 11)
    println(binarySearchBasic(array, 3))
    println(binarySearchBasic(array, 2))

    val duplicateArray = arrayOf(1, 2, 2, 3, 4, 4, 4, 5, 7, 7)
    println(binarySearchWithDuplicate(duplicateArray, 6))
    println(binarySearchWithBoundaryFeasibility(duplicateArray, 6))
}

fun binarySearchBasic(arr: Array<Int>, target: Int): Int {
    var left = 0
    var right = arr.size - 1
    while (left <= right) {
        // We can use mid = (left + right) / 2, but that might cause integer overflow
        val mid = left + (right - left) / 2
        if (arr[mid] == target) return mid
        if (arr[mid] > target) {
            right = mid - 1
        } else {
            left = mid + 1
        }
    }
    return -1
}

/**
 * NOT_NEEDED : If need match exactly
 */
fun binarySearchWithDuplicate(arr: Array<Int>, target: Int): Int {
    var left = 0
    var right = arr.size - 1
    while (left < right) {
        val mid = left + (right - left) / 2
        if (arr[mid] >= target) {
            // Keep
            right = mid
        } else {
            left = mid + 1
        }
    }
    return if(arr[left] == target) left else -1
}

/**
 * Feasible Condition ==
 *  Ex. 1, 2, 2, 3, 4, 4, 4, 6, 8, 8
 *  target = 4
 *
 * Feasible Condition >=
 *   Ex. 1, 2, 2, 3, 4, 4, 4, 6, 8, 8
 *   target = 4
 *   Ex. 1, 2, 3, 3, 4, 4, 4 ,6, 8, 8
 *   target = 5
 *
 * Feasible condition >, if element greater than
 *   Ex. 1, 2, 2, 3, 4, 4, 4, 6, 8, 8
 *   target = 5
 */
fun binarySearchWithBoundaryFeasibility(arr: Array<Int>, target: Int): Int {
    var left = 0
    var right = arr.size - 1
    // Storing the last feasible boundary element
    var lastFeasible = -1
    while (left <= right) {
        val mid = left + (right - left) / 2
        if (arr[mid] >= target) { // Feasibilty condition
            // remember the last boundary we found so that if in next iteration if we dont fine any
            lastFeasible = mid
            right = mid - 1
        } else { // Non Feability condition
            left = mid + 1
        }
    }
    return lastFeasible
}