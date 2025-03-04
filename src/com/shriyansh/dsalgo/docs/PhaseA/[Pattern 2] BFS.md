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

## B. BFS on Graph

### Basic BFS
```kotlin
fun bfs(root: Node<Int>) {
    val queue = ArrayDeque<Node<Int>>()
    val visited = HashSet<Node<Int>>()
    queue.add(root)
    visited.add(root)
    while (queue.isNotEmpty()) {
        val size  = queue.size
        for (i in 0 until size) {
            val element = queue.pollFirst()
            print("${element.value} ")
            for (neighbor in element.neighbours) {
                if (!neighbor in visited) {
                    queue.add(neighbor)
                    visited.add(neighbor)
                }
            }
        }
    }
}

```

#### Question
```ignorelang
You are given an m x n grid where each cell can have one of three values:
0 representing an empty cell,
1 representing a fresh orange, or
2 representing a rotten orange.
Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.

Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.

Example

Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
Output: 4
```
#### Question
```ignorelang
You are given an m x n matrix maze (0-indexed) with empty cells (represented as '.') and walls (represented as '+'). You are also given the entrance of the maze, where entrance = [entrancerow, entrancecol] denotes the row and column of the cell you are initially standing at.
In one step, you can move one cell up, down, left, or right. You cannot step into a cell with a wall, and you cannot step outside the maze. Your goal is to find the nearest exit from the entrance. An exit is defined as an empty cell that is at the border of the maze. The entrance does not count as an exit.
Return the number of steps in the shortest path from the entrance to the nearest exit, or -1 if no such path exists.

Example
Input: maze = [["+","+",".","+"],[".",".",".","+"],["+","+","+","."]], entrance = [1,2]
Output: 1

```