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


## A. Segment Tree
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

