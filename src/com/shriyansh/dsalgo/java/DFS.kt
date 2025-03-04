package com.shriyansh.dsalgo.java

fun findTreeLeaf(root: Node<Int>?, result: MutableList<Int>) {
    if (root == null) return

    if (root.left == null && root.right == null) {
        result.add(root.value)
    }
    findTreeLeaf(root.left, result)
    findTreeLeaf(root.right, result)
    return
}


fun main() {
    val root = Node(1)
    val left = Node(2)
    val right = Node(3)
    root.left = left
    root.right = right
    left.right = Node(5)
    left.left = Node(4)
    right.left = Node(8)
    right.right = Node(7)

    val result = ArrayList<Int>()
    findTreeLeaf(root, result)
    println(result)
}