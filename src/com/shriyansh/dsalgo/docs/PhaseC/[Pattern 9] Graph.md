# Graph

Entire graph is mostly limited to BFS/DFS and their applications of levels, components, cyclicity, topology for unweighted graphs. For unweighted graphs shortest paths could be found using BFS

For Weighted ones, Shortest Path Algorithms are of prime importance

If in a graph, given n - 1 edges to connect n nodes, then it is a tree (tree is specific for of graph). If there are more than n - 1 edges then it does not remain a tree. Redundant edges in a graph = edges - (n - 1) to keep it connected
Challenge. Try connecting n nodes with n - 1 edges without creating a tree (Not possible)

### Implementation

#### Matrix
```kotlin
val matrix:List<List<Int>> = ArrayList()
matrix
```

#### Adjacency List : Preferred
```kotlin
data class Node(
    val value: Int
)

data class Neighbour (
    val node: Node,
    val weight: Int
)

val adjacencyList = mutableMapOf<Node, List<Node>>()
adjacencyList[Node(5)] = mutableListOf(Node(6), Node(7))

val adjacencyListWeighted = mutableMapOf<Node, List<Neighbour>>()
adjacencyListWeighted[Node(5)] = mutableListOf(Neighbour(Node(6), 2), Neighbour(Node(7), 4))
```

Note:
1. Adjacency List can be used for any custom node types, whereas matrix will work for nodes represented by indexes
2. Weights can be represented within the list along with each neighbour.
3. In case of undirected (default) add entries to both the keys for each edge (a,b) -> a:[...b] and b:[...a]
2. In questions, input for these can be given as list of edges, use that to build adjacency list

## A. DFS on Graph

#### Implementation

```kotlin
data class Node(
    val value: Int
)

fun triggerMultiComponent(graph: Map<Node, List<Node>>) {
    val visited = HashSet<Node>()
    for (node in graph.keys) {
        if(!node in visited) {
            dfs(node, graph, visited)
        }
    }
}

fun triggerSource(node: Node, graph: Map<Node, List<Node>>) {
    val visited = HashSet<Node>()
    dfs(node, graph, visited)
}

fun dfs(node: Node, graph: Map<Node, List<Node>>, visited: HashSet<Node>) {
    println("$node")
    visited.add(node)

    for (neighbour in graph[node]!!) {
        if (!neighbour in visited) {
            dfs(neighbour, graph, visited)
        }
    }
}
```

Note:
1. For graph with multiple components, parent runs a loop for all nodes, if any node is not visited, it will trigger dfs for that
2. For directed graph, starting from a random node might terminate the iteration, hence this trigger loop is required to finish the traversal

Time complexity: O(V + E)

## B. BFS on Graph

BFS moves step by step in all directions. This can tell the minimum distance between source and destination
in a non weighted graph. All nodes at distance i will only be visited after completing nodes at distance at i - 1. 
If there are multiple paths leading to a destination node, then destination will be always visited first i.e. in minimum distance/smallest path by BFS
This distance can be known by counting levels in BFS

#### Pseudocode
```
function BFS(root: Node<Int>):
    initialize queue as an empty deque
    initialize visited as an empty set
    add root to queue
    add root to visited
    
    while queue is not empty:
        size = size of queue
        print level
        for i from 0 to size - 1:
            element = dequeue first element from queue
            print element.value
            
            for each neighbor in element.neighbours:
                if neighbor is not in visited:
                    add neighbor to queue
                    add neighbor to visited
```

### Basic BFS

```kotlin
data class Node(
    val value: Int
)

fun bfs(root: Node, graph: Map<Node, List<Node>>, visited: HashSet<Node>) {
    val queue = ArrayDeque<Node>()
    // visited definition can be moved inside in case of given source
    queue.add(root)
    visited.add(root)
    while (queue.isNotEmpty()) {
        val size = queue.size
        for (i in 0 until size) {
            val element = queue.pollFirst()
            print("${element.value} ")
            for (neighbor in graph[element]!!) {
                if (!neighbor in visited) {
                    queue.add(neighbor)
                    visited.add(neighbor)
                }
            }
        }
    }
}

fun triggerMultiComponent(graph: Map<Node, List<Node>>) {
    val visited = HashSet<Node>()
    for (node in graph.keys) {
        if (!node in visited) {
            bfs(node, graph, visited)
        }
    }
}

fun triggerSource(node: Node, graph: Map<Node, List<Node>>) {
    val visited = HashSet<Node>()
    bfs(node, graph, visited)
}

```

Note:
1. Time complexity wise both approach will take O(V + E)
2. Remember that node is marked visited when it is inserted into the queue and not while popping out
3. For graph with multiple components, pass visited from parent, parent runs a loop for all nodes, if any node is not visited, it will trigger bfs for that
TODO: See if updating levels after the size consumption loop makes more sense, along with initialising it as -1.

Multisource BFS: In this we start with multiple source at ones, and traverse level by level for each source. This was we find nearest common node both all source nodes.
This traverse level 1 for all sources, then moves to level 2 for all source groups and so on.
TODO: Draw diagram to represent this.

#### Question
```ignorelang
Find if path exist between given source and destination nodes in a graph.

Hint: They might be in different component in a forest. You can use BFS/DFS for this start from source, if before completion of traversal if destination is found then return true otherwise false.
```

#### Question
```ignorelang
Reorient Reroutes to make all path lead to city 0

Hint:
1. This problem though provides directional edge definitions, we can store them as undirected (both a->b and b->a)
We can use any BFS/DFS and from city 0, maintain a count if there is a directed edge to move forward. This path will actually give number of edges to be reversed
```

## C. Matrix as graph

In Matrix problems, the paths to reachable cells can be derived using the conditions. Try to find the paths in all directions using that condition.
This path finding algorithm is applicable to any cell. Keep in mid the edge cell conditions
Example 
1. Knight : (i-2, j+1), (i-2, j-1), (i+2, j+1), (i+2, j-1), (i-1, j-2), (i+1, j-2), (i-1, j+2), (i+1, j+2)
2. All Adjacent: (i-1,j-1), (i-1, j) (i -1, j+1), (i, j+1), (i+1, j+1), (i+1, j), (i+1, j-1), (i, j-1)
3. Four Adjacent: (i, j-1), (i-1, j), (i, j+1), (i+1, j)

Try drawing graph diagram with nodes and edges to simplify visualization

Matrix problem needs visited map with a 2D key or a 2D boolean array initialized with
```kotlin
val visited = Array(n) { BooleanArray(n) { false } }
```

Time Complexity
1. for matrix based graph traversal is O(V + E), where V is nxm and E can be ignored, Hence it is O(n x m)

#### Helper for generating neighbour coordinates in Matrix problems

```kotlin
fun getNeighbours(source: Pair<Int, Int>, xLimit: Int, yLimit: Int): List<Pair<Int, Int>> {
    // Predefined Moves (Knights)
    val moves = listOf(Pair(-2, 1), Pair(-2, -1), Pair(2, 1), Pair(2, -1), Pair(-1, -2), Pair(1, -2), Pair(-1, 2), Pair(1, 2))
    val neighbours = mutableListOf<Pair<Int, Int>>()
    for (move in moves) {
        // validate coordinate bounds
        val coords = Pair(source.first + move.first, source.second + move.second)
        if (coords.first in 0..<xLimit && coords.second in 0..<xLimit) {
            neighbours.add(coords)
        }
    }
    return neighbours
}
```

TODO: Write DFS abd BFS code for matrix as graph

Note: This will replace the adjacency list, this can be derived at run time

#### Question
```ignorelang
Steps by knight in a chess board. Given source and destination cells. Find the minimum number of steps by knight

Hint: 
1. Paths (i-2, j+1), (i-2, j-1), (i+2, j+1), (i+2, j-1)
2. Trivia: any destination can be reached from any source by knight
3. Distance will be denoted by level 
```

#### Question
```ignorelang
Flood fill a matrix, cells with guven number to a different given number.Start pixel coordinates are given

Hint: Neighbours will be adjacent by should also have same given color. This updates the neighbour logic
Also visited can be omitted as we are updating the original array, that can be used as visited itself
Can be solved via both BFS/DFS, BFS preferred
Remember edge case where source color is same as required color, return
```

#### Question
```ignorelang
Number of islands in a grid of 1 and 0

Hint: Variant of flood fill
This is also an example of multiple components, hence needs uber level loop of unvisited in trigger that loops over all cells of grid
```

```kotlin
fun trigger(grid: List<List<Int>>): Int {
    var islands = 0
    for (i in 0..<grid.size) {
        for (j in 0..<grid[0].size) {
            if (grid[i][j] == 1) {
                dfs(i, j, grid) // this will mark all cells of island to 2, so that further iterations here will not consider them again
                islands++
            }
        }
    }
    return islands
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

Hint: BFS, counting levels for each new rotten source (island), taking maximum of levels for each island. Here levels is same as minutes
You can also think of this question as maximum level island in a grid

Multisource BFS: In case a single island has multiple oranges, we need to process them paralleley. Called multisource BFS
This way you process level for all source first and then move for level two of them and so on
This just requires pushing all sources in the queue initially and then bfs implementation will take care of it.
Multisource also takes case of islands

Edge case: If there is a island of fresh oranges only return -1

This question disguises BFS as minutes, also uses multisource BFS and hence a good question. Graph is already disguised as matrix
```

#### Question
```ignorelang
GOOD Question: Analyse why

House, Wells, Ground and Prohibited Areas. Find minimum distance to travel for each house to well without using prohibited area and can go in 4 adjacent direction.
This requires running BFS for each house and store minimum levels to get first Well. Since each house required running BFS afresh, this will be O(n x m X n x m).

Think well as source, since single well can provide water for multiple nearest houses. You can start multi source bfs and then race condition between wells will also be resolved.
Since this will work parallely from all sources and calculation is reused by multiple houses, time complexity is O(n x m)
Original grid can be used to mark houses as visited, but you need additional ans grid to store ans for each house.
BFS continues even if houses are found at any levels so find other houses which are still not visited.
Since Multisource BFS is used, no need to handle islands
If any H remains after entire traversal, then for that put -1, since any well cannot satisfy them.

Alternative: DFS with Memoization will also work
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

## D. Cycle Detection

Cycle or loop can occur in both directed and undirected graphs. 
For directed graph all loop looking structure need to be a cycle, they should lead to a loop while traversing

### I. Cycle detection in undirected graph
For Undirected graphs, cycle detection can be done using BFS and DFS both

Using a non-cyclical graph would only have incoming node as visited, other nodes should not be a visited node since DFS traverses branch by branch.
Hence if in DFS if you find any neighbour as visited apart from the previous incoming node then there is a cycle
This means that a node which was visited earlier has been encountered again

```kotlin
fun trigger(root: Node, graph: Map<Node, List<Node>>): Boolean {
    val visited = HashSet<Node>()
    for (node in graph.keys) {
        if (!node in visited) {
            val ans = dfs(root, graph, visited, null)
            if (ans) return true
        }
    }
    return false
}

fun dfs(node: Node, graph: Map<Node, List<Node>>, visited: HashSet<Node>, parent: Node): Boolean {
    println("$node")
    visited.add(node)
    for (neighbour in graph[node]!!) {
        if (!neighbour in visited) {
            val ans = dfs(neighbour, graph, visited, node)
            if (ans) return true
        } else if (neighbour in visited && neighbour!= parent) {
            return true
        }
    }
    return false
}

fun bfs(node: Node, graph: Map<Node, List<Node>>, visited: HashSet<Node>, parent: HashMap<Node, Node>): Boolean {
    val queue = ArrayDeque<Node>()
    queue.add(node)
    visited.add(node)
    while (!queue.isEmpty()) {
        val size = queue.size
        for (i in 0..<size) {
            val element = queue.pollFirst()
            for (neighbour in graph[element]!!) {
                if (!neighbour in visited) {
                    queue.add(neighbour)
                    visited.add(neighbour)
                    parent[neighbour] = element
                } else if (neighbour in visited && parent[element] != neighbour) {
                    return true
                } 
            }
        }
    }
    return false
}

fun triggerCycleBFS(graph: Map<Node, List<Node>>): Boolean {
    val visited = HashSet<Node>()
    val parent = HashMap<Node, Node>()
    for (node in graph.keys) {
        if (!node in visited) {
            val ans = bfs(node, graph, visited, parent)
            if (ans) return true
        }
    }
    return false
}
```

### II. Cycle detection in directed graph 
For Directed graphs, cycle detection can be done using BFS and DFS both.
For directed graph, approach used in undirected graph cannot be reused.
For directed graph DFS is suitable, where nodes within current call stack is referred to find a cycle
To store the nodes in current call stack of dfs use Set, add as soon as its children are added and remove as soon an children are complete, similar to backtracking

```kotlin
fun trigger(root: Node, graph: Map<Node, List<Node>>): Boolean {
    val visited = HashSet<Node>()
    val path = HashSet<Node>()
    for (node in graph.keys) {
        if (!node in visited) {
            val ans = dfs(root, graph, visited, path)
            if (ans) return true
        }
    }
    return false
}
fun dfs(node: Node, graph: Map<Node, List<Node>>, visited: HashSet<Node>, path: HashSet<Node>): Boolean {
    println("$node")
    visited.add(node)
    path.add(node)
    for (neighbour in graph[node]!!) {
        if (!neighbour in visited) {
            val ans = dfs(neighbour, graph, visited, path)
            if (ans) return true
        } else if (neighbour in visited && node in path) {
            return true
        }
    }
    path.remove(node)
    return false
}
```

Note:
1. Cycle detection in directed graph can also be done using BFS, that will be detailed in topological sort
2. When the cycle is detected, the last nodes till repeating neighbour are part of cycle

#### Question
```ignorelang
A node is a terminal node if there are no outgoing edges from it. A node is called safe node is every path possible from it leads to terminal node. Find all the safe nodes

Hint: This is a variant of cycle detection, for each node find it there is a cycle or not starting from  it
Remember that any path that it not leading to terminal node it means it is forming a cycle and looping and hence not ending to terminal node
Note that every path in a non cyclic graph will always end in a node in general

Sol: We can use path to get all nodes that are part of cycle as well as other nodes which are leading to this cycle. Notice that all nodes in path will be unsafe nodes
Since we are returning from stack when cycle is found, backtracking for that is not complete and path will still contain all those nodes. So you can just print the path in the trigger after each dfs return true.
```

#### Question
```ignorelang
Find the nodes in logest cycle of directed graph

TODO: TBD
```

## E. Topological Sort
Topological Sort is arranging nodes in directed graph in order so that dependent nodes occur first. This is applicable to acyclic graph only.
Nodes with no dependencies are put first. Topological sort will sequence in hierarchical manner with bottom ones first.
A topological sort can have multiple valid sequences
Topological sort is not possible for a cyclic graph

Topological sort can be solved using
1. BFS using Kahn's Algorithm
2. DFS

### Kahn's Algorithm / BFS (Preferred to Topological Sort)
InDegree: Number of edges incoming towards the node
OutDegree: Number of nodes outgoing from the node

It starts with nodes having 0 inDegree, i.e. node with 0 dependencies.
After visiting a node we remove it decrement their dependents inDegree. With this we can keep choosing nodes with inDegree 0 after each iteration

Notice that if topological sort in cyclic graph, then queue will become empty but inDegree for remaining nodes will still be not zero. This means the only starting point will be somewhere on the loop. There is not absolute starting point.

Algorithm
1. Prepare inDegree count for each node, using edge list
2. Push all nodes with 0 inDegree in the BFS queue
3. While processing neighbours for these, reduce their inDegree by 1
4. If inDegree becomes 0, then only insert that neighbour in queue

### Using Kahn's algorithm for cycle detection
Apply Kahn's algorithm and if at the end (queue is empty) is the inDegree array still has some nodes with non-zero inDegree
or the output does not contain all nodes then there is a cycle

```kotlin
import java.util.ArrayDeque

fun bfs(node: Node, graph: Map<Node, List<Node>>, inDegree: MutableMap<Node, Int>): List<Node> {
    val queue = ArrayDeque<Node>()
    val topologicalSort = mutableListOf<Node>()
    for (item in inDegree.entries) {
        if (item.value == 0) { queue.add(item) }
    }
    while (!queue.isEmpty()) {
        val size = queue.size()
        for (i in 0..<size) {
            val element = queue.pollFirst()
            topologicalSort.add(element)
            for (neighbour in graph[node]!!) {
                var count = indegree[neighbour]?.minus(1)!!
                inDegree[neighbour] = count
                if (count == 0) {
                    queue.add(neighbour)
                }
            }
        }
    }
    return topologicalSort
}

fun trigger(graph: Map<Node, List<Node>>): List<Node> {
    val inDegree = mutableMapOf<Node, Int>()
    // InDegree calculation
    for (entry in graph.entries) {
        val toNodes = entry.value
        for (node in toNodes) {
            var count = indegree.getOrPut(node) {0}
            count++
            indegree[node] = count
        }
    }
    // to not miss any node with indegree 0
    for (node in graph.keys) {
        inDegree.putIfAbsent(node, 0)
    }
    
    // Here inDegree will take care of multiple components since it will hve nodes from all the components
    val ans = bfs(node, graph, indegree)
    
    if (ans.size != graph.size) {
        // Graph has a cycle
        return emptyList()
    }
    return emptyList()
}
```
Note
1. Visited array is not needed, as inDegree array is helping on that
2. Notice similarity to vanilla BFS

### Using DFS
Unlike Kahn's algorithm, we need to run a loop from trigger to ensure all the nodes are visited

TODO


#### Question
```ignorelang
Counrse Scheduling: There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array.

Example 1:

Input: numCourses = 2, prerequisites = [[1,0]]
Output: [0,1]

Hint: Topological Sort
```

#### Question
```ignorelang
Largest Color Value Path in directed graph.
You are given a string colors where colors[i] is a lowercase English letter representing the color of the ith node in this graph (0-indexed). You are also given a 2D array edges where edges[j] = [aj, bj] indicates that there is a directed edge from node aj to node bj.

A valid path in the graph is a sequence of nodes x1 to x2 to x3 to ... to xk such that there is a directed edge from xi to xi+1 for every 1 less than or = i less than k. The color value of the path is the number of nodes that are colored the most frequently occurring color along that path.

Return the largest color value of any valid path in the given graph, or -1 if the graph contains a cycle.


Example 1:
Input: colors = "abaca", edges = [[0,1],[0,2],[2,3],[3,4]]
Output: 3

Hint: Using topological sort, keep collecting the max (not sum, since two separate path cannot be summed up for a color) value for each color till now, add current node color to frequency. This way the terminal node can know what is the max color path till here
While inserting a node, also insert the frequency map of color till that node (including that node) in the queue
See solution in Adutya Verma Graph Video 27
```

## G. Connected Components (Concept part of spanning tree)
This involves counting the number of components in a graph. Component refers to unconnected group of nodes in a graph (forest).

To count number of components in a graph, it is equal to number of calls to dfs/bfs from trigger function. This trigger loops over all the nodes to see if any unvisited nodes are still there and starts dfs/bfs again.
```kotlin
fun trigger(graph: Map<Node, List<Node>>): Int {
    var components = 0
    val visited = HashSet<Node>()
    for (node in graph.keys) {
        if (!node in visited) {
            dfs(node, graph, visited) // or bfs
            components++
        }
    }
    return components
}
```

Note:
1. This can also be solved using DSU to calculate the number of components, rather than dfs/bfs

#### Question
```ignorelang
Given nodes and connections between them. Since there can be multiple components. You can remove any existing connection and use it to connect the components so that we achieve one component finally.
Find the minimum number of connections rearranged to achieve this.


Hint:
1. For each additional component we need one connection. additionalConnections = components - 1
2. Finding redundant connection in each component. for n nodes we need atleast n - 1 connections. This way we can calculate number of redundant connections available

Alternatively[Mathematical]: For all n nodes in a graph, we need n - 1 connections. If there are n - 1 connections in this graph, then all components can be connected otherwise return -1. The answer is (components - 1)
```

## H. Shortest Path in weighted graph
In unweighted graphs the sorted path problems could be solved using BFS.
For unweighted graphs Shortest path problem need specific algorithms
There are two variants here as well
1. Single source shortest path
2. All source shortest path (Grid of all possible source and destinations and their shortest possible path)

In case of only positive weights, Dijkstra O(E* logV) is applicable
If negative weights are involved, then it can cause negative weight cycle, Dijkstra will not work, Belmann Ford O(V*E) is applicable
For All source shortest path problems, Floyd Warshall O(V^3...) is applicable



### I. Dijkstra
This algorithm works successively at each set it calculates shortest path till intermediate nodes and incrementally build over it.
Hence the solution not only just find optimal path for destination, but also for nodes that it has traversed while solving the problem
It relies on Greedy approach

Dijkstra works similar to BFS, it uses PQ on weights rather than normal queue

1. Keep a heap indexed on distance till that node. Keep an array to store distance of all from source
2. Initialize distances of all as infinity
3. Insert source with distance 0, and update distance of source as 0
4. While PQ is not empty, pop the top, keep distance till it, for each neighbouring see if current distance of them is more than new calculate distance and update in distance array and insert/update heap as well

Note:
1. Intuition: Any node only comes out/popped out of queue when all the distances till that node have already been considered (final). Because if there was a path less than this distance, then it would have appeared at the top (with some previous node), but this is not the case. Notice that this would not hold in case of negative weights
2. Since for heap we want to pop min distance, but we also want to update the weight of given node, we need this additional API in heap. SortedSet (TreeSet) could be useful for this

```kotlin
import java.util.PriorityQueue
import java.util.TreeSet

fun dijkstra(source: Node, graph: Map<Node, List<Pair<Node, Int>>>): Map<Node, Int> {
    val distances: HashMap<Node, Int> = HashMap()
    for (node in graph.keys) {
        distances[node] = Int.MAX_VALUE
    }
    val queue: PriorityQueue<Pair<Node, Int>> = PriorityQueue { a: Pair<Node, Int>, b: Pair<Node, Int> -> a.second - b.second }
    distances[source] = 0
    queue.add(Pair(source, 0))
    while (queue.isEmpty()) {
        val element = queue.poll()
        for (neighbour in graph[element]!!) {
            if (element.second + neighbour.second < distances[neighbour]!!) {
                distances[neighbour] = element.second + neighbour.second
                queue.add(Pair(neighbour.first, distances[neighbour]!!))
            }
        }
    }
    return distances
}
```

## I. Minimum Spanning Tree
This is applicable to undirected weighted graphs.
Spanning Tree: Graph with n - 1 edges for n nodes and all nodes are reachable. There can be multiple spanning tree for a given graph. Since n - 1 path are minimum edges possible, it is possible via tree only.
TODO: Discuss why minimum spanning tree and not minimum spanning graph.
Minimum Spanning Tree: Spanning Tree containing all nodes with minimum overall weights.

### I. Prim's Algorithm (Intuitive BFS Variant)
1. Create Visited Array and PriorityQueue (indexed on weight)
2. insert one of the node as source node with weight as 0
3. While PQ is not empty, pop top element
4. check if it is not visited then mark it visited, add weight to sum
5. for each neighbours of this if it is not visited add it along with weight to PQ

```kotlin
import java.util.PriorityQueue

data class PrimsItem (
    val node: Node,
    val source: Node,
    val weight: Int
)

fun mstPrims(source: Node, graph: Map<Node, List<Pair<Node, Int>>>): Pair<Int, List<Pair<Node, Node>>> {
    val visited = HashSet<Node>()
    val heap:PriorityQueue<PrimsItem> = PriorityQueue {a,b -> a.weight - b.weight}
    var weight = 0
    val mstEdges = ArrayList<Pair<Node, Node>>()
    heap.add(PrimsItem(source, null, 0))
    while (heap.isNotEmpty()) {
        val element = heap.poll()
        if (!element.node in visited) {
            visited.add(element)
            mstEdges.add(Pair(element.node, element.source))
            weight += element.second
            
            for (neighbour in graph[element]!!) {
                if (!neighbour.first in visited) {
                    heap.add(PrimsItem(neighbour.first, element, neighbour.second))
                }
            }
        }
    }
    return Pair(weight, mstEdges)
}
```

Note:
1. Difference from vanilla BFS is the node is marked visited only after popping out

### II. Kruskals (Simple but Uses Concepts from DSU)
Refer: Advanced / B for DSU

1. Store all edges in list and sort them by weight
2. Create a disjoint set and weight tracker
3. For each edges in edge list, pop and check if both ends are in same union, if not then (add them to union, store weight) else ignore

```kotlin
data class Item (
    val first: Node,
    val second: Node,
    val weight: Int
)
fun mstKruskal(graph: Map<Node, List<Pair<Node, Int>>>): Int {
    val edges = ArrayList<Item>()
    for (entry in graph.entries) {
        for (node in entry.value) {
            edges.add(Item(entry.key, node.first, node.second))
        }
    }
    edges.sortedBy { it.weight }
    val disjointSet = DisjointSet(graph.keys)
    var mstWeight = 0
    for (item in edges) {
        if (disjointSet.findSet(item.first) != disjointSet.findSet(item.second)) {
            disjointSet.union(item.first, item.second)
            mstWeight += item.weight
        }
    }
    return mstWeight
}
```



## J. Other

1. Hamilton Path
2. Graph Coloring
3. Strongly Connected Components
4. Network Flow