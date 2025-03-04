# DFS

## A. DFS on Tree

### Templates
```kotlin
fun dfs() {
    // leaf condition
    // other termination condition
    
    // recursive call to children
    // use of values returned by children
    
    // return values to parent
}
```

### I. Bottom up Result Only

#### Question 1
```ignorelang
Find Count of nodes in a tree
```
#### Solution
```kotlin
fun countOfNodes(root: Node<Int>?): Int {
  if (root == null) return 0
  
  val leftCount = countOfNodes(root.left)
  val rightCount = countOfNodes(root.right)
  
  val count = left + right + 1

  return count
}
```

#### Question 2
```ignorelang
Find Count of Leaf Nodes
```
#### Solution
```kotlin
fun countOfLeafNodes(root: Node<Int>?): Int {
  if (root == null) return 0

  // Addtional Terminating Condition
  if (root.left == null && root.right == null) return 1
  
  val leftCount = countOfNodes(root.left)
  val rightCount = countOfNodes(root.right)
  
  val count = leftCount + rightCount
  return count
}
```

#### Question 3
```ignorelang
Find Max Depth of Tree
```
#### Solution
```kotlin
fun maxDepth(root: Node<Int>?): Int {
  if (root == null) return 0
  
  val leftDepth = maxDepth(root.left)
  val rightDepth = maxDepth(root.right)
  
  val maxDepth = Math.max(left, right) + 1

  return maxDepth
}
```

### II. NonPath with State
#### Question
```ignorelang
Given a binary tree root, a node X in the tree is named good if in the path from root to X there are no nodes with a value greater than X.
Return the number of good nodes in the binary tree.

Example
Input: root = [3,1,4,3,null,1,5]
Output: 4
```

```kotlin
// This looks Similar to state with return value
fun visibleNode(root: Node<Int>?, max: Int): Int {
    if (root == null) return 0
    
    val currentNodes = if (root.value > max) 1 else 0
    
    val leftCount = visibleNode(root.left, Math.max(max, root.value))
    val rightCount = visibleNode(root.right, Math.max(max, root.value))
    return leftCount + rightCount + currentNodes
}
```

### II. NonPath with Collection

#### Template
```kotlin
fun nonPathWithCollection(root: Node<Int>?, result: List<Int>) {
    // leaf termination, return null
    
    // Other Terminating condition
    // Update Collection
    
    // recurse children
    // return null
}
```

```ignorelang
Get all leaf nodes
```
```kotlin
fun collectLeafNodes(root: Node<Int>?, result: List<Int>) {
    if (root == null) return
    
    if (root.left == null && root.right == null) {
        result.add(root.value)
    }

    collectLeafNodes(root.left, result)
    collectLeafNodes(root.right, result)
    return
}
```

### III. Path with Collection
* Any return statement before child recursion call is terminating condition for recursion build up phase.
* Any return statement after child recursion call is return upwards to parent in recursion destruction phase
* Any primitive state pass to children is copy over so not conflict among siblings.
* Any non primitive like list, array is pass by reference, hence involves addition and removal of elements after processing before it goes to sibling to avoid conflict among them w.r.t. modification and use of path or any state.

#### Template
```kotlin
fun pathCollection() {
    // leaf termination, return null
    
    //path.add(root.value)
    
    // Other terminating condition
    // Updating result
    
    //child recursion
    
    //path.remove()
}
```

#### Question
```ignorelang
Find all root to leaf paths in Tree
```
#### Solution
```kotlin
fun fundAllPaths(root: Nodes<Int>?, path: MutableList<Int>, paths: MutableList<MutableList<Int>>) {
  if (root == null) return
  
  path.add(root.value)
  if (root.left == null && root.right == null) {
    paths.add(path.toMutableList())
  }

  findAllPaths(root.left, path, paths)
  findAllPaths(root.right, path, paths)
  
  path.removeLast()
}
```

### IV. Path with Collection and Dynamic/Static State
#### Question
```ignorelang
Find all tree paths with sum equal to targt
```
#### Solution
```kotlin
fun fundAllPathsWithSum(root: Nodes<Int>?, sum: Int, path: MutableList<Int>, paths: MutableList<MutableList<Int>>) {
  if (root == null) return
  
  path.add(root.value)
  val remaining = sum - root.value
  if (remaining == 0 && root.left == null && root.right == null) {
    paths.add(path.toMutableList())
  }

  fundAllPathsWithSum(root.left, remainig, path, paths)
  fundAllPathsWithSum(root.right, remaining, path, paths)
  
  path.removeLast()
}
```
### Other Questions
#### Question
```ignorelang
Find all nodes greater than target
```
#### Question
```ignorelang
Find all paths with all elements grater than target
```

#### Question
```ignorelang
Find the path with maximum sum from root to leaf
```

### V. Path with Aggregation

#### Template
```kotlin
fun pathWithAggregation() {
    // leaf termination with default values false, MIN_VALUE,0,
    
    // Other termination
    
    // child recursion
    
    // use of child values and return, +-, ||, Max/min
    
    // Note: No path addition and removal
}
```

```ignorelang
Find if there is a path with target sum till leaf
```
```kotlin
fun ifPathSumTillLeafNodes(root: Node<Int>?, sum: Int): Boolean {
  if (root == null) return false
  
  val remaining = sum - root.value
  // Addtional Terminating Condition
  if (remaining == 0 
      && root.left == null && root.right == null) return true
  
  val leftCount = ifPathSumTillLeafNodes(root.left, remaining)
  val rightCount = ifPathSumTillLeafNodes(root.right, remaining)
  
  val hasPath = left ||right

  return hasPath
}
```

### VI. Binary Search Tree

#### Implementation
```kotlin
// Insert
fun insert(root: Node<Int>?, value: Int) {
    if (root == null) return Node(value)
    
    if (value < root.value) {
        insert(root.left, value)
    } else {
        insert(root.right, value)
    }
}

// Search
fun search(root: Node<Int>, value: Int): Boolean {
    if (root == null) return false
    if (root.value == value) return true
    
    if (value < root.value) {
        return search(root.left, value)
    } else {
        return search(root.right, value)
    }
}
```

#### Question
```ignorelang
Validate if given tree is binary tree
```
#### Solution
```kotlin
fun validate(root: Node<Int>, lower: Int, upper: Int): Boolean {
    if (root == null) return true
    if (root.value < lower) return false
    if (root.value > upper) return false
    return validate(root.left, lower, root.value) && validate(root.right, root.value, higher)
}
```

#### Question
```ignorelang
Find Lowest Common Ancestor for two nodes
```

#### Question
```ignorelang
Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.
Basically, the deletion can be divided into two stages:

Search for a node to remove.
If the node is found, delete the node.

```
