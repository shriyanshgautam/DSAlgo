package com.shriyansh.dsalgo.patterns.dfs

data class TreeNode(
    val value: Int,
    var right: TreeNode?,
    var left: TreeNode?
)


fun main() {
    println(factorial(5))
}

/**
 * [Liner/One Child Model] Recursion on linear model, where finding current value depends only upon one child value, which recursively depends upon its child values
 */
fun factorial(n: Int): Int {
    // Base termination condition
    if (n <= 1) return 1

    // Nth Calculation and recursive calls
    // Here Nth calculation involves child values to be known
    // There can be scenarios where Nth calculation is independent of child values, in such case also recursive calls should be made for child
    val value = n * factorial(n - 1)

    return value
}

/**
 * [Branched Child Model] Recursion on branched model, where finding current value depends on multiple child values (left and right)
 * While processing Nth condition, think as is both left and right child values and already known and derive nth value based upon them
 *
 * getNode(root, target):
 *  if root == null
 *     return null
*   if root == target
 *     return root
 *  return getNode(root.left, target) or getNode(root.right, target)
 *
 *  Nth value calculation sometimes can be done without depending on values of child, but child recursive calls are still made and returned
 *
 */
fun getNodePresent(root: TreeNode?, target: Int): TreeNode? {
    // Base termination condition
    if (root == null) {
        return null
    }

    // Nth calculation
    if (root.value == target) {
        return root
    }
    // Recursive call for Children
    val left = getNodePresent(root.left, target)
    // Additional circuit breaker could be place here, if left subtree has already found the value then we can return true from here itself
    val right = getNodePresent(root.right, target)

    // Returning for Nth
    if (left != null) return left
    if (right != null) return right
    return null
}