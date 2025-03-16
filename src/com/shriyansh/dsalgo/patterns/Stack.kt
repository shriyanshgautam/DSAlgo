package com.shriyansh.dsalgo.patterns

import java.util.Stack


fun main() {
    println(ngeRight(listOf(1, 3, 2, 6, 5, 7, 9, 5)))
}

fun ngeRight(arr: List<Int>): List<Int> {
    var stack: Stack<Int> = Stack()
    var result = mutableListOf<Int>()
    for (i in arr.size - 1 downTo 0) {
        while (!stack.isEmpty() && stack.peek() <= arr[i]) {
            stack.pop()
        }
        if (stack.isEmpty()) { result.add(-1) } else { result.add(stack.peek()) }
        stack.push(arr[i])
    }
    return result.reversed()
}