package com.shriyansh.dsalgo.patterns.dfs


fun main() {

}

/**
 * Top Down approach for recursion where no value is returned from Nth calculation
 * Rather while traversing down each node precesses on its own
 */
fun anyOrder(root: TreeNode?) {
    // Base condition
    if (root == null) return

            // Processing Nth for preOrder
            println(root.value)

    // Left and Right subtree processing
    anyOrder(root.left)

            // Processing Nth for inOrder
            println(root.value)

    anyOrder(root.right)

            // Processing Nth for postOrder
            println(root.value)
}