# Backtracking

## Introduction

Backtracking is a fundamental algorithmic technique that builds upon recursion. It is particularly useful for solving problems that involve sequences, permutations, and combinatorial optimization. Unlike basic recursion, which explores all possibilities, backtracking prunes paths that do not satisfy given constraints, making it more efficient in many cases.

Backtracking is commonly used in problems like:
- Generating permutations and combinations
- Solving puzzles such as Sudoku and N-Queens
- Constructing valid sequences, such as balanced parentheses
- Searching for solutions in constraint-based problems

This chapter explores the principles, implementation, and optimizations of backtracking, including comparisons with other recursive techniques like Dynamic Programming (DP).

## Understanding Recursion and Backtracking

### Single-Branch vs. Multi-Branch Recursion
Recursion can be categorized into single-branch and multi-branch recursion:
- **Single-Branch Recursion**: Involves a single recursive call per function invocation. An example is calculating the factorial of a number, where the function only calls itself once per step.
- **Multi-Branch Recursion**: Involves multiple recursive calls per invocation, creating a tree-like structure of execution. Examples include Fibonacci sequence computation and tree traversal.

Example:
```kotlin
fun factorial(n: Int): Int {
    if (n == 0 || n == 1) return 1
    return n * factorial(n - 1)
}
```
This is single-branch recursion because each call only makes one recursive call.

For multi-branch recursion, consider the Fibonacci sequence:
```kotlin
fun fibonacci(n: Int): Int {
    if (n <= 1) return n
    return fibonacci(n - 1) + fibonacci(n - 2)
}
```
This function has two recursive calls at each step, leading to an exponential growth in recursive calls.

### Explicit vs. Implicit Recursive Structures
Recursion can also be categorized based on the structure it follows:
- **Explicit Structure Recursion**: This occurs when recursion is naturally embedded in the problem’s definition, such as in tree traversals.
- **Implicit Decision Tree Recursion**: The recursive structure is not immediately visible but emerges from the choices made at each step, as seen in combinatorial problems like permutations or subset generation.

Example of explicit recursion in tree traversal:
```kotlin
fun inorderTraversal(root: TreeNode?) {
    if (root == null) return
    inorderTraversal(root.left)
    println(root.value)
    inorderTraversal(root.right)
}
```
Here, recursion follows the explicit structure of the binary tree.

Example of implicit recursion in decision trees:
```kotlin
fun generateSubsets(nums: List<Int>, index: Int, path: MutableList<Int>, result: MutableList<List<Int>>) {
    if (index == nums.size) {
        result.add(ArrayList(path))
        return
    }
    
    // Include current element
    path.add(nums[index])
    generateSubsets(nums, index + 1, path, result)
    
    // Exclude current element (Backtrack)
    path.removeLast()
    generateSubsets(nums, index + 1, path, result)
}
```
Here, the recursion does not follow a predefined structure like a tree but instead explores different choices dynamically.

### Recursion as a Foundation
Recursion is a method where a function calls itself to solve a smaller version of a larger problem. It follows a well-defined structure:
1. **Base Condition** – Defines when recursion should stop.
2. **Recursive Hypothesis** – Assumes the function correctly solves the smaller problem.
3. **Inductive Step** – Uses the smaller solution to construct the final answer.

Example of a recursive function computing the sum of numbers from 1 to `n`:

```kotlin
fun sumPreRecursion(n: Int, res: Int): Int {
    if (n == 0) return res
    return sumPreRecursion(n - 1, res + n)
}
```

### What is Backtracking?

Backtracking is a type of recursion with **pruning** to avoid exploring paths that cannot lead to valid results. Unlike brute-force recursion, which checks all possibilities, backtracking strategically eliminates paths early based on constraints.

Backtracking is primarily interested in valid **paths** from the root to leaf nodes in a decision tree. These paths become apparent once a base condition is met, making it similar to base-case recursion where the focus is on reaching the final state.

Backtracking is widely used in combinatorial and permutation problems, such as:
- Finding all permutations of a given set
- Solving constraint-based puzzles like Sudoku and N-Queens
- Constructing sequences like valid parenthesis expressions

### Understanding Path Building in Backtracking

A common misconception about backtracking is that it involves returning to a parent node if a solution is not found. However, backtracking is primarily about pruning—deciding **not** to explore certain paths due to constraints.

#### Path Tracking and Pruning

- When constructing a solution (e.g., generating permutations), the **path** is modified by appending a character or decision.
- Once a branch is fully explored, the decision is **removed** to allow space for other possibilities.
- This is necessary because the path is shared across recursive calls due to **pass-by-reference** in many programming languages.
- If paths were passed by value (creating a new copy each time), the removal step would not be required but would result in higher memory usage.

Example: When generating all permutations of a string, the character is added to the path, and after exploring that choice, it is removed before trying the next character.

#### Looping Through Choices

Backtracking problems typically involve multiple choices at each step. These choices are explored using a loop, and within each iteration:
1. A choice is made and added to the current path.
2. A **pruning condition** ensures only valid choices are explored.
3. The function recursively explores the remaining possibilities.
4. The choice is **removed** (backtracking) to make way for the next iteration.

This structured approach makes backtracking an efficient solution for problems that require **generating all possible solutions and filtering valid ones**.
Backtracking refines recursion by incorporating a decision-making component. The key characteristics of backtracking are:
- Exploring all possible choices at each step.
- Eliminating paths that do not satisfy constraints (pruning).
- Tracking valid solutions that meet the given conditions.

A **decision tree** helps visualize backtracking by representing each choice as a node and each branch as a decision leading to further choices.

## Approaches to Backtracking

### 1. Hypothesis Design Approach
In this method, we break down the problem recursively and define expectations from each function call. It follows these steps:
1. **Define Expectation** – What should the function return?
2. **Reduce the Input** – Call the function with a smaller input.
3. **Process the Current Node** – Use the recursive result to compute the answer for the current node.
4. **Base Case** – Define when to stop the recursion.

Example: Solving the Tower of Hanoi problem using hypothesis design.

```kotlin
fun towerOfHanoi(n: Int, from: Char, to: Char, aux: Char) {
    if (n == 1) {
        println("Move disk 1 from $from to $to")
        return
    }
    towerOfHanoi(n - 1, from, aux, to)
    println("Move disk $n from $from to $to")
    towerOfHanoi(n - 1, aux, to, from)
}
```

### 2. Candidate, Termination, and Pruning Approach
This method explicitly tracks the available **candidates**, defines a **termination condition**, and applies **pruning** to eliminate unnecessary paths. It follows:
1. **Candidate Selection** – Choose potential moves at each step.
2. **Termination Condition** – Check if a valid solution has been reached.
3. **Pruning** – Cut off paths that cannot lead to valid solutions.

Example: Finding all subsets of a given set.

```kotlin
fun findSubsets(nums: List<Int>, index: Int, path: MutableList<Int>, result: MutableList<List<Int>>) {
    if (index == nums.size) {
        result.add(ArrayList(path))
        return
    }

    // Include current element
    path.add(nums[index])
    findSubsets(nums, index + 1, path, result)

    // Exclude current element (Backtrack)
    path.removeLast()
    findSubsets(nums, index + 1, path, result)
}
```

This method is particularly useful for problems like permutations, combinations, and the N-Queens problem.

## Backtracking Template

A standard backtracking algorithm follows this structure:

```kotlin
fun solve(candidates: List<Int>, path: MutableList<Int>, result: MutableList<Int>) {
    if (isSolved(path)) {
        result.addAll(path)
        return
    }

    for (i in candidates) {
        path.add(i)
        if (isValid(path)) {
            solve(candidates, path, result)
        }
        path.removeLast() // Backtrack
    }
}
```

### Explanation:
1. **Base Case** – If a valid solution is found, store it.
2. **Choices** – Iterate over available options.
3. **Pruning** – Check if the choice is valid before proceeding.
4. **Recursive Call** – Explore further solutions.
5. **Backtrack** – Undo the last choice before moving to the next option.

## Common Backtracking Problems

### 1. Generating Permutations

#### Example Problem:
**Problem Statement:** Given a string `s`, return all possible permutations of the characters in `s`.

**Example:**
```
Input: "ABC"
Output: ["ABC", "ACB", "BAC", "BCA", "CAB", "CBA"]
```
Finding all permutations of a given string.

```kotlin
fun permutations(input: String, output: String, result: MutableList<String>) {
    if (input.isEmpty()) {
        result.add(output)
        return
    }

    for (i in input.indices) {
        val newInput = input.substring(0, i) + input.substring(i + 1)
        val newOutput = output + input[i]
        permutations(newInput, newOutput, result)
    }
}
```

### 2. Solving the N-Queens Problem

#### Example Problem:
**Problem Statement:** Place `N` queens on an `N × N` chessboard such that no two queens attack each other.

**Example:**
```
Input: N = 4
Output: [
    [".Q..",  "...Q",  "Q...",  "..Q."],
    ["..Q.",  "Q...",  "...Q",  ".Q.."]
]
```
The N-Queens problem places `N` queens on an `N × N` chessboard such that no two queens attack each other.

```kotlin
fun solveNQueens(n: Int, board: MutableList<String>, row: Int, result: MutableList<List<String>>) {
    if (row == n) {
        result.add(board.toList())
        return
    }

    for (col in 0 until n) {
        if (isSafe(board, row, col)) {
            board[row] = board[row].substring(0, col) + "Q" + board[row].substring(col + 1)
            solveNQueens(n, board, row + 1, result)
            board[row] = board[row].substring(0, col) + "." + board[row].substring(col + 1) // Backtrack
        }
    }
}
```

### 3. Generating Balanced Parentheses

#### Example Problem:
**Problem Statement:** Given `n` pairs of parentheses, generate all valid combinations.

**Example:**
```
Input: n = 3
Output: ["((()))", "(()())", "(())()", "()(())", "()()()"]
```
Generate all valid combinations of `n` pairs of parentheses.

```kotlin
fun generateParentheses(n: Int, open: Int, close: Int, path: String, result: MutableList<String>) {
    if (open == n && close == n) {
        result.add(path)
        return
    }

    if (open < n) generateParentheses(n, open + 1, close, path + "(", result)
    if (close < open) generateParentheses(n, open, close + 1, path + ")", result)
}
```

## Time Complexity Analysis
The time complexity of a backtracking algorithm depends on the **branching factor** (number of choices per step) and the **depth** (number of decisions made). In many cases, this results in exponential complexity.

- **Permutations**: `O(n!)` due to factorial growth of choices.
- **N-Queens**: `O(N!)` because each row allows `N` choices initially, reducing at each step.
- **Balanced Parentheses**: `O(2^n)` since every position has two choices (open or close).

## Backtracking vs Dynamic Programming
| Feature | Backtracking | Dynamic Programming |
|---------|-------------|----------------------|
| **Purpose** | Finds all possible solutions | Finds the optimal solution |
| **Exploration** | Searches all valid paths | Uses overlapping subproblems |
| **Efficiency** | Exponential in worst case | Polynomial with memoization |
| **Example** | N-Queens, Permutations | Knapsack, Fibonacci |

## Conclusion
Backtracking is a versatile and powerful approach for solving problems with constraints and multiple choices. By combining recursion, decision trees, and pruning, backtracking efficiently explores all possible solutions while avoiding redundant computations. Understanding its core principles equips you with a fundamental toolset for tackling complex combinatorial problems efficiently.

Next, we will explore **Dynamic Programming**, a technique that optimizes recursion by storing intermediate results.
