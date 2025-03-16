# Advanced

## A. Trie

### Basic Implementation

```kotlin
class TrieNode {
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    var isEndOfWord: Boolean = false
}

fun createEmptyTrie(): TrieNode {
    return TrieNode() // This is same as $ i.e. empty node
}

fun insert(word: String, root: TrieNode) {
    var current = root
    for (char in word) {
        if (!current.children.containsKey(char)) {
            current.children[char] = TrieNode()
        }
        current = current.children[char]!!
        // Short version
        // current = current.children.getOrPut(char) { TrieNode() }
        current.isEndOfWord = true
    }
}

fun search(word: String, root: TrieNode): Boolean {
    var current = root
    for (char in word) {
        if (!current.children.containsKey(char)) { 
            return false 
        }
        current = current.children[char]!!
        //current = current.children[char] ?: return false
    }
    // Check if the current word is marked in Trie after traversing the path
    return current.isEndOfWord
}

fun isPrefix(prefix: String, root: TrieNode): Boolean {
    var current = root
    for (char in prefix) {
        if (!current.children.containsKey(char)) { 
            return false 
        }
        current = current.children[char]!!
        // current = current.children[char] ?: return false
    }
    // If we have traversed a path with given prefix, then return true
    return true
}
```
### Advanced Implementation
```kotlin
class TrieAdvancedNode {
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    var wordEndCount = 0
    var prefixCount = 0
} 

fun insert(word: String, root: TrieAdvancedNode) {
    var current = root
    for (char in word) {
        if (!current.children.containsKey(char)) {
            current.children[char] = TrieAdvancedNode()
        }
        current.prefixCount++
        current = current.children[char]!!
    }
    current.wordEndCount++
}

fun erase(word: String, root: TrieAdvancedNode) {
   TBD
}

// Rest remains same
```
Note:
1. End of word is enabled only on characters where words end in the path.
2. Word end is marked on the node pointed character in parant and not in the marked node

#### Question
```ignorelang
Given list of words, find the maximum length word such that all its prefixes are present as word in the given list

Example
Input: words=[n, ninja, ninj, nin, ni, ninga]

Sol: Insert all words. Then for each word check if isEnd is marked true for each of its charatcer. Find maximum such word. 
```

## B. Disjoint Set Union
This is a graph data structure that can have multiple components. Each component is stored as spanning tree and each node has some parent representative
DSU is a data structure that provides following set of query interfaces apart from adding a node
1. find if two nodes are in same component i.e. same parent
2. Join two components or add a node to component

The parent point to itself for parent, if component has single node, then that node is parent of itself

Since nodes in a component follow hierarchy till the representative parent, finding representative parent can be like traversing long chain.
Path compression shortens this by making this representative as direct parent of all nodes in that component, hence reducing parent finding query time

### I. Union by rank with path compression

```kotlin
class Item(
    val data: Node,
    var parent: Item?,
    var rank: Int
)
class DSU(nodes: List<Node>) {
    var map: MutableMap<Node, Item> = HashMap()

    init {
        for (node in nodes) {
            val item = Item(node, null, 0)
            item.parent = item
            map[node] = item
        }
    }
    
    fun add(node: Node) {
        val item = Item(node, null, 0)
        item.parent = item
        map[node] = item
    }
    
    fun findParent(node: Node): Item {
        val setItem = map[node]!!
        if (setItem == setItem.parent) { return setItem }
        // Path compression
        setItem.parent = findSet(setItem.parent) // This compresses path for each node in the hierarchy
        return setItem.parent!!
    }
    
    fun union(first: Node, second: Node) {
        val firstParent = findParent(first)
        val secondParent = findParent(second)
        // They are already part of same component
        if (firstParent == secondParent) { return }
        
        // Smaller component takes larger one as its parent
        if (firstParent.rank >= secondParent.rank) {
            secondParent.parent = firstParent
        } else {
            firstParent.parent = secondParent
        }
        // increase rank only if equal
        if (firstParent.rank == secondParent.rank) { firstParent.rank++ }
    }
}
```


## C. Segment Tree
This is used to support queries like sum/min/max between two given positions in an array.

The tree representation is same as in Heap, i.e. array based binary tree with following references
Left Child = 2 * index + 1
Right Child = 2 * index + 2
Parent = (index - 1)/2

We can also solve this by building 2D matrix to remember min values across each combination of i and j
But that would require O(n^2) time to build and similar space, but would provide O(1) query time
With Segment Tree, we take O(n) [O(4n)] time to build and similar space, query takes O(logn) time

### I. Implementation
```kotlin
fun buildSegmentTreeMin(arr: IntArray, segTree: IntArray, low: Int, high: Int, segTreePos: Int) {
    if (low == high) {
        segTree[segTreePos] = arr[low]
        return
    }
    val mid = low + (high - low)/2
    buildSegmentTreeMin(arr, segTree, low, mid, 2*segTreePos + 1)
    buildSegmentTreeMin(arr, segTree, mid + 1, high, 2*segTreePos + 2)
    segTree[segTreePos] = Math.min(segTree[2*segTreePos + 1], segTree[2*segTreePos + 2])
}

fun query(segTree: IntArray, qLow: Int, qHigh: Int, low: Int, high: Int, pos: Int): Int {
    if (qLow <= low && high <= qHigh) { // Total Overlap
        return segTree[pos]
    }
    if (qLow > high || qHigh < low) { // No overlap
        return Int.MAX_VALUE
    } 
    val mid = low + (high - low)/2
    return Math.min(
        query(segTree, qLow, qHigh, low, mid, 2*pos + 1),
        query(segTree, qLow, qHigh, mid + 1, high, 2*pos + 2)
    )
}

fun update() {
    TBD
}
```

