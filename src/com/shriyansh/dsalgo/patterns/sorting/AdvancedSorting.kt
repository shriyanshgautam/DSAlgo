package com.shriyansh.dsalgo.patterns.sorting

import java.util.LinkedList


fun mergeSort(arr: List<Int>, startIndex: Int, endIndex: Int): List<Int> {
    // Base condition if list size is 0 or 1 result list with that range
    if (endIndex - startIndex <= 1) {
        return arr.subList(startIndex, endIndex)
    }

    // Processing left and right recursion call
    val midIndex = (startIndex + endIndex) / 2
    val leftList = mergeSort(arr, startIndex, midIndex)
    val rightList = mergeSort(arr, midIndex, endIndex)

    // Conciling Right and Left recursive call
    val result = LinkedList<Int>()
    var leftPointer = 0
    var rightPointer = 0

    while (leftPointer < leftList.size || rightPointer < rightList.size) {
        // If any one of the pointers has reached end of the list
        if (leftPointer == leftList.size) {
            result.add(rightList[rightPointer])
            rightPointer++
        } else if (rightPointer == rightList.size) {
            result.add(leftList[leftPointer])
            leftPointer++
        // Add the least of the left and tight list item to result
        } else if (leftList[leftPointer] < rightList[rightPointer]) {
            result.add(leftList[leftPointer])
            leftPointer++
        } else {
            result.add(rightList[rightPointer])
            rightPointer++
        }
    }
    return result
}

fun quickSort() {

}