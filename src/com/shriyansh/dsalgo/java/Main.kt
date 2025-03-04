package com.shriyansh.dsalgo.java

fun main() {
    println(findAnagrams("cbaebabacd", "abc"))
}

fun findAnagrams(input: String, check: String): List<Int> {
    if (input.length < check.length) return emptyList()
    var result = ArrayList<Int>()
    var checkSet = HashMap<Char, Int>()
    for (i in check.indices) {
        checkSet[check[i]] = checkSet.getOrDefault(check[i], 0) + 1
    }
    var windowSet = HashMap<Char, Int>()
    for (i in check.indices) {
        windowSet[input[i]] = windowSet.getOrDefault(input[i], 0) + 1
    }
    if (checkSet == windowSet) result.add(0)
    for (i in check.length..<input.length) {
        windowSet[input[i - check.length]] = windowSet[input[i - check.length]]?.minus(1) ?: 0
        if (windowSet[input[i - check.length]] == 0) {
            windowSet.remove(input[i - check.length])
        }
        windowSet[input[i]] = windowSet.getOrDefault(input[i], 0) + 1
        if (checkSet == windowSet) result.add(i - check.length + 1)
    }
    return result
}

fun maxOfSlidingWindow(list: List<Int>, window: Int): Int {
    // First Window
    var windowSum = 0
    for (i in 0..<window) {
        windowSum += list[i]
    }

    var largestSum = windowSum
    for (i in window until list.size) {
        windowSum += list[i] // Add Next
        windowSum -= list[i - window] // Rremove Last
        largestSum = Math.max(largestSum, windowSum)
    }
    return largestSum
}

fun isSubsequence(input: String, target: String): Boolean {
    var slow = 0
    for (char in target) {
        if (char == input[slow]) {
            slow++
            if (slow == input.length) return true
        }

    }
    return false
}


fun twoSum(list: List<Int>, target: Int): List<Pair<Int, Int>> {
    val sortedList = list.sorted()
    var left = 0
    var right = list.size - 1
    val result = ArrayList<Pair<Int, Int>>()
    while (left < right) {
        val sum = sortedList[left] + sortedList[right]
        if (sum == target) {
            result.add(Pair(sortedList[left], sortedList[right]))
            left++
            right--
        }
        if (sum < target) left ++
        if (sum > target) right --
    }
    return result
}

fun getMaxWater(list: List<Int>): Int {
    var left = 0
    var right = list.size - 1
    var maxVolume = 0
    while (left < right) {
        val volume = (right - left) * Math.min(list[left], list[right])
        if (volume > maxVolume) maxVolume = volume
        if (list[left] <= list[right]) {
            left++
        } else {
            right--
        }
    }
    return maxVolume
}

fun isPalindrome(text: String): Boolean {
    var left = 0
    var right = text.length - 1
    while (left < right) {
        while (left < right && !text[left].isLetterOrDigit()) {
            left++
        }
        while (left < right && !text[right].isLetterOrDigit()) {
            right--
        }
        // Business logic
        if (text[left].lowercaseChar() != text[right].lowercaseChar()) return false
        left++
        right--
    }
    return true
}