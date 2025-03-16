# Breadth First Search

## A. BFS on Tree

#### Basic BFS
```kotlin
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
    }
}

```

#### Question 1
```ignorelang
Get Level Order Traversal for Binary Tree
```
#### Solution
```kotlin
fun bfs(root: TreeNode<Int>): ArrayList<List<Int>> {
    val response = ArrayList<List<Int>>()
    val queue = ArrayDeque<TreeNode<Int>>()
    queue.add(root)
    while (!queue.isEmpty()) {
        val count = queue.size
        val level = mutableListOf<Int>()
        for (i in 1..count) {
            val item = queue.pop()
            level.add(item.value)
            item.left?.let { queue.add(it) }
            item.right?.let { queue.add(it) }
        }
        response.add(level)
    }
    return response
}
```