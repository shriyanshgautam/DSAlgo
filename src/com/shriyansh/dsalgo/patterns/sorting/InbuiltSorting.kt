package com.shriyansh.dsalgo.patterns.sorting

import java.util.*

data class Item(
    val value: Int
)

fun main() {
    val array = arrayOf(1, 2, 3, 8, 5, 2, 5, 7, 5, 3, 32)
    val list = mutableListOf(1, 2, 3, 8, 5, 2, 5, 7, 5, 3, 32)
    val itemList = mutableListOf(Item(1), Item(5), Item(10), Item(3), Item(4))
    println(inbuiltArraySorting(array))
    println(inBuiltCollectionSorting(list))
    println(inBuiltCollectionReverseSorting(list))
    println(inBuiltCollectionSortingByAttribute(itemList))
}

fun inbuiltArraySorting (array: Array<Int>): Array<Int> {
    Arrays.sort(array)
    return array
}

fun inBuiltCollectionSorting(list: MutableList<Int>): List<Int> {
    Collections.sort(list)
    return list
}

fun inBuiltCollectionReverseSorting(list: MutableList<Int>): List<Int> {
    Collections.sort(list, Collections.reverseOrder())
    return list
}

fun inBuiltCollectionSortingByAttribute(list: MutableList<Item>): List<Item> {
    Collections.sort(list, { o1, o2 -> o1.value - o2.value })
    return list
}