package com.shriyansh.dsalgo.java

import java.util.ArrayDeque

class Node<T>(val value: T) {
    var left: Node<T>? = null
    var right: Node<T>? = null
}


fun bfs(root: Node<Int>) {
    val queue = ArrayDeque<Node<Int>>()
    queue.add(root)
    while (queue.isNotEmpty()) {
        val size  = queue.size
        for (i in 0 until size) {
            val element = queue.pollFirst()
            print("${element.value} ")
            element.left?.let { queue.add(it) }
            element.right?.let { queue.add(it) }
        }
        println()
    }
}



fun main() {
    val root = Node(2)
    val left = Node(3)
    val right = Node(4)
    root.left = left
    root.right = right
    left.right = Node(6)
    right.left = Node(5)
    bfs(root)
}