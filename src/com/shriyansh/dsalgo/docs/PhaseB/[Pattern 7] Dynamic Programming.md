# Dynamic Programming

## A. Defining Dynamic Programming
> Dynamic Programming  = Enhanced Recursion

> Dynamic Programming  = Recursion + Memoization


Similar to Recursion DP problems also ask either total possible combination / ways or min / max / sum

Lets solve Fibonacci Calculation and derive understanding of DP

### O. Steps to Identify & Approach DP
#### Identifiers
1. Recursion, i.e. choice at each step from multiple available options
2. If recursion involves calling two sub problems in recursive call, there is a possibility to cache, memoize, tabulate & have DP
3. DP cannot be applied to linear recursion chain, since results cannot be reused. If branched recursion is there with re-occuring function call with same input

Note: Similar to recursion DP would also deal with aggregation like min/max, possible or not apart from simple counting.
Use similar approaches to handle aggregation as done in recursion
Ex. 
    
    1. Maximum/Minimum value possible: Math.max(a, b) / Math.min(a, b)
    2. Solution Possible or not: a || b
    3. Count 1 + a

#### Steps
1. Build the recursion diagram first
2. Add Memoization layer into recursive solution #1. Adding suitable keys and memoized values
3. [Optional] Build tabulation/matrix approach based recursive solution #1

Both Memoization layer and tabulation are DP solutions, you can choose either of them for DP
One benefit of tabulation over recursive memoization is call stacks. Since tabulation is iterative, it does not involve call stack

### I. Pure Recursion (Top-Down)
In this case, the tree nodes and subtrees repeat and are calculated repeatedly
leading to factorial complexity
```kotlin
fun fibonacci(n: Int): Int {
    // base case
    if (n == 0) return 0
    if (n == 1) return 1
    
    // recurse
    return fibonacci(n-1) + fibonacci(n-2)
}
```

### II. Recursion with Memoization (Top-Down)
In this approach we use the same recursion, but put a caching layer to avoid recalculation for repeated subtrees
```kotlin
fun fibonacci(n: Int, memo: MutableMap<Int, Int>): Int {
    // base case
    if (n == 0) return 0
    if (n == 1) return 1
    
    memo[n]?.let { return it }
    
    memo[n] = fibonacci(n - 1, memo) + fibonacci(n - 2, memo)
    return memo[n]!!
}
```

### III. Tabulation (Bottom-Up)
In this approach we build up smaller sub-problems (dependencies) first and the use them to solve bigger problem

```kotlin
fun fibonacci(n: Int): Int {
    val table = IntArray(n + 1)
    table[0] = 0
    table[1] = 1
    
    for (i in 2..n) {
        table[i] = table[i - 1] + table[i - 2]
    }
    return table[n]
}
fun fibonacci(): Int {
    var first = 0
    var second = 1
    for (i in 2..n) {
        val temp = second
        second += first
        first = temp
    }
    return second
}
```

## B. Patterns in DP

[Youtube: Aditya](https://www.youtube.com/watch?v=nqowUJzG-iM&list=PL_z_8CaSLPWekqhdCPmFohncHwz8TY2Go&index=1&t=0s)

```ignorelang
What is knapsack?

Given multiple items with different weights and values. Also give a bag with alimited weight capacity.
You need to select items to put into the bag such that it does not excced the bag by weight capacity but achieves maximum value of total items

Knapsack has following variations
1. Fractional knapsack: Items can be put fractionally as well. Can be solved by greedy approach.
2. 0/1 knapsack: Only once instance of one item can be put in bag. Can be solved by DP
3. Unbounded knapsack: Multiple instances of each item can be put in the bag. Can be solved by DP
```

### I. 0/1 Knapsack
Although 0/1 knapsack involves two attributes weight and value, but it could as well be one attribute for example weight, still Recursion/DP applies.
There is a overall capacity/sum to be achieved. Each item can only be picked at most once or not at all

#### Solution : Recursive
```kotlin
fun knapsack(weights:IntArray, values: IntArray, n: Int, capacity: Int): Int {
    // base condition, think about the minima; possible values of all inputs
    if (n == 0 || capacity == 0) {
        return 0 // in both of the above cases there can be no value added to bag
    }
    
    if (weights[n - 1] < capacity) {
        return Math.max(
            values[n - 1] + knapsack(weights, values, n - 1, capacity - weights[n - 1]),
            knapsack(weights, values, n - 1, capacity)
        )
    } else {
        return knapsack(weights, values, n - 1, capacity)
    }
}
```

#### Solution : Memoization
```kotlin
// For Memoization, keys are the inputs that vary across recursion
// Store in cache for all branches of return in recursive call
// retrieve from cache before any further recursive calls
val table = Array(n) { IntArray(capacity) { -1 } }
fun knapsack(weights:IntArray, values: IntArray, n: Int, capacity: Int): Int {
    // base condition, think about the minima; possible values of all inputs
    if (n == 0 || capacity == 0) {
        return 0 // in both of the above cases there can be no value added to bag
    }

    // cache retreival after base, before recursive calls
    if (table[n][capacity] != -1) { return table[n][capacity] }

    if (weights[n - 1] < capacity) {
        val res =  Math.max(
            values[n - 1] + knapsack(weights, values, n - 1, capacity - weights[n - 1]),
            knapsack(weights, values, n - 1, capacity)
        )
        // storing in cache
        table[n][capacity] = res
        return res
    } else {
        val res = knapsack(weights, values, n - 1, capacity)
        // storing in cache
        table[n][capacity] = res
        return res
    }
}
```

#### Solution : Tabulation
```kotlin
// For tabulation, similar to memoization select the keys and dimensions of the table
// Initialize the first row/column as per the base condition of recursive approach
// Rest are filled based upon the recursive call in recursive approach. Make sure the lookup is always from smaller values of i,j which is already calculated for subproblem
fun knapsack(weights:IntArray, values: IntArray, n: Int, capacity: Int): Int {
    val table = Array(n + 1) { IntArray(capacity + 1) { -1 } }
    
    for (i in 0..n) {
        for (j in 0..capacity) {
            if (i == 0 || j == 0) { table[i][j] == 0 }
        }
    }
    // Separating base initialization for rest of tabulation build up
    for (i in 1..n) {
        for (j in 1..capacity) {
            // Convert recursive calls to table lookup
            if (weights[i - 1] < j) {
                table[i][j] =  Math.max(values[i - 1] + table[i - 1][j - weights[i - 1]], table[i - 1][j])
            } else {
                table[i][j] = table[i - 1][j]
            }
        }
    }
    return table[n][w]
}
// Notice that table stores the value that needs to be return by the function, the ask
```

#### Variants of 0/1 Knapsack
1. Subset Sum Possible | Equal sum partition in two subset : Find if there is subset of given elements which sum up to a given target
3. Count of subset sum
4. Target sum

### II. Unbounded Knapsack

#### Variants of Unbounded Knapsack
1. Coin Change
2. Coin Change II
3. Rod cutting
4. Ribbon cutting

### III. Fibonacci


### IV. Longest common subsequence
Given two string, find the length of longest common subsequence between both of them

#### Solution : Recursive
```kotlin
fun lcs(first: String, second: String, m: Int, n: Int): Int {
    // base condition, think about the minima; possible values of all inputs
    if (m == 0 || n == 0) {
        return 0
    }
    
    if (first[m -1] == second[n - 1]) {
        return 1 + lcs(first, second, m - 1, n - 1)
    } else {
        return Math.max(
            lcs(first, second, m, n -1),
            lcs(first, second, m - 1, n)
        )
    }
}
```

#### Solution : Memoized
```kotlin
val table = Array(m + 1) { IntArray(n + 1) { -1 } }
fun lcs(first: String, second: String, m: Int, n: Int): Int {
    // base condition, think about the minima; possible values of all inputs
    if (m == 0 || n == 0) {
        return 0
    }
    
    if (table[m][n] != -1) { return table[m][n] }
    
    if (first[n -1] == second[m - 1]) {
        val res =  1 + lcs(first, second, m - 1, n - 1)
        table[m][n] = res
        return  res
    } else {
        val res = Math.max(
            lcs(first, second, m, n -1),
            lcs(first, second, m - 1, n)
        )
        table[m][n] = res
        return res
    }
}
```

#### Solution : Tabulation
```kotlin
fun lcs(first: String, second: String, m: Int, n: Int): Int {
    val table = Array(m + 1) { IntArray(n + 1) { -1 } }

    for (i in 0..m + 1) {
        for (j in 0..n + 1) {
            if (i == 0 || j == 0) { table[i][j] == 0 }
        }
    }
    // Separating initialization for recursion build up
    for (i in 1..m + 1) {
        for (j in 1..n + 1) {
            if (first[i - 1] == second[j - 1]) {
                table[i][j] = 1 + table[i -1][j - 1]
            } else {
                table[i][j] = Math.max(table[i][j - 1], table[i - 1][j])
            }
        }
    }
    return table[m][n]
}
```
#### Printing LCS
```kotlin
fun printLcs(first: String, second: String, m: Int, n: Int, table: Array<Array<Int>>): String {
    var i = m
    var j = n
    var result = ""
    while (i > 0 && j > 0) {
        if(first[i - 1] == second[j - 1]) {
            result = first[i - 1] + result
            i--
            j--
        } else {
            if (table[i - 1][j] > table[i][j - 1]) {
                j--
            } else {
                i--
            }
        }
    }
    return result
}
```

#### Variants of Longest Common Subsequence
1. Longest Common Substring
```kotlin
fun lcs(first: String, second: String, m: Int, n: Int): Int {
    val table = Array(m + 1) { IntArray(n + 1) { -1 } }

    for (i in 0..m + 1) {
        for (j in 0..n + 1) {
            if (i == 0 || j == 0) { table[i][j] == 0 }
        }
    }
    // Separating initialization for recursion build up
    for (i in 1..m + 1) {
        for (j in 1..n + 1) {
            if (first[i - 1] == second[j - 1]) {
                table[i][j] = 1 + table[i -1][j - 1]
            } else {
                table[i][j] = 0
            }
        }
    }
    return table[m][n]
}
```
2. Shortest Common Supersequence
  * Hint: LCS is AUB and is double counted, subtract from sum of length of both
3. Minimum number of insertion or deletion to convert one string to other (Notice that replace is not give, otherwise DP cannot be applied)
  * Hint: Insert: len(b) - LCS, Delete: len(a) - LCS
4. Longest Palindromic Subsequence | Minimum number of deletion to make palindrome | Minimum number of deletion to make palindrome
  * Hint: LCS between string and reverse of string | length of string - LPS | Repeat the characters rather than deleting them in previous problem, since they are only one side
5. Print Shortest Common Supersequence
  * Hint: Refer Print LCS and extend it where character are not equal add it. Also Add while loop to consume remaining of i or j
6. Longest Repeating Subsequence
  * Hint: Reuse LCS of string with itself, with modification i != j for matching character
7. Sequence Pattern Matching: If a is subsequence of b
  * Hint: If a is subsequence of b, use length of subsequence to validate
8. Minimum insertion to make it palindrome

### V. Longest increasing subsequence
### VI. Kadane's Algorithm
### VII. Matrix chain multiplication
It involves breaking the array and combining elements in various ways keeping the sequence to see 
the optimal way of combining the elements one by one


#### Solution : Recursive
```kotlin
fun mcm(items: IntArray, i: Int, j: Int): Int {
    if (i >= j) return 0
    
    var minCost = Int.MAX_VALUE
    for (k in i..<j) {
        val cost = mcm(items, i, k) + mcm (items, k + 1, j) +
           items[i - 1] + items[k] + items[j]
        temp = Math.min(minCost, cost)
    }
    return minCost
}
// i starts with 1; j starts with arr.len - 1
// k lies between from i to j - 1
// items[i - 1] + items[k] + items[j] gives cost of multiplying matrices returned by two recursive mcm calls
// The for loop tries all partitions between i to j to get min cost way of partition
```

#### Solution : Memoization
```kotlin
// changing parameter for keys are i and j
val table = Array(m + 1) { IntArray(n + 1) { -1 } }
fun mcm(items: IntArray, i: Int, j: Int): Int {
    if (i >= j) return 0
    
    if (table[i][j] != -1) { return table[i][j] }

    var minCost = Int.MAX_VALUE
    for (k in i..<j) {
        val cost = mcm(items, i, k) + mcm (items, k + 1, j) +
                items[i - 1] + items[k] + items[j]
        temp = Math.min(minCost, cost)
    }
    table[i][j] = minCost
    return minCost
}
```

#### Solution: Tabulation
We do not use tabulation for MCM based problems

#### Variants of Matrix Chain Multiplication
1. Palindrome Partitioning ie. minimum number of partition to get palindrome
```kotlin
// Here i can start at 0 and j at len - 1
// k can go from i to j - 1
val table = Array(m + 1) { IntArray(n + 1) { -1 } }
fun partition(string: String, i: int, j: Int): Int {
    if (i >= i) {
        return 0 // no partition required
    }
    // Additionally if string from i to j is already palindrome, we need no partition
    if (isPalindrome(string, i, j)) { return 0 }
    
    // Memoization layer
    if (table[i][j] != -1) { return table[i][j] }
    
    var minPartitions = Int.MAX_VALUE
    for (k in i..<j) {
        val partitions = partition(string, i, k) + partition(string, k + 1, j) + 
            1 // denoting one more partition at k
        minPartitions = Math.min(minPartitions, partitions)
    }
    
    // Memoization
    table[i][j] = minPartitions
    return minPartitions
}
// See video on how to further optimize this [Video](https://www.youtube.com/watch?v=9h10fqkI7Nk&list=PL_z_8CaSLPWekqhdCPmFohncHwz8TY2Go&index=37)
fun isPalindrome(string: String, i: Int, j: Int): Boolean {
    if (i >= j) { return true}
    var start = i
    var end = j
    while (start < end) {
        if (string[start] != string[end]) { return false}
        start++
        end--
    }
    return true
}
```


### VIII. DP on tress
### IX. DP on grid
### X. Others


### Greedy vs DP

Here’s a detailed comparison between **Greedy Algorithms** and **Dynamic Programming (DP)**:

| **Aspect**                | **Greedy Algorithms**                                  | **Dynamic Programming (DP)**                                  |
|---------------------------|---------------------------------------------------------|---------------------------------------------------------------|
| **Approach**               | Makes the **locally optimal** choice at each step with the hope of finding a global optimum. | Solves problems by solving **overlapping subproblems** and combining their solutions to find the global optimum. |
| **Optimality Guarantee**   | Does not guarantee an optimal solution for all problems. It only works when a **greedy choice property** and **optimal substructure** hold. | Guarantees an optimal solution for problems that have both **optimal substructure** and **overlapping subproblems**. |
| **Problem Type**           | Suitable for problems where making local choices leads to a globally optimal solution. | Used for problems with optimal substructure and overlapping subproblems, such as optimization problems. |
| **Decision Process**       | Makes a **single decision** at each step and does not reconsider previous decisions. | **Breaks the problem** into subproblems, stores the solutions to subproblems, and combines them to build up the final solution. |
| **Complexity**             | Usually **faster** because it makes decisions in a greedy manner without considering all possible choices. | **Slower** than greedy, as it stores and computes solutions to overlapping subproblems (e.g., O(n^2) or O(n^3) time complexity). |
| **Memory Usage**           | Typically uses **constant space** (not considering the input size). | Requires **additional space** to store solutions to subproblems (e.g., arrays, matrices). |
| **Example Problems**       | **Huffman Coding**, **Greedy Knapsack**, **Prim’s Algorithm**, **Dijkstra’s Algorithm (for shortest path in a graph)**. | **Fibonacci sequence**, **0/1 Knapsack problem**, **Longest Common Subsequence (LCS)**, **Matrix Chain Multiplication**. |
| **Solution Building**      | **Builds the solution incrementally**, based on the greedy choices made at each step. | **Builds the solution** by solving and combining solutions to smaller subproblems (either through memoization or tabulation). |
| **Backtracking**           | No backtracking—once a decision is made, it is not revisited. | Solutions are built by considering subproblems and may revisit or adjust decisions (via memoization). |
| **Time Complexity**        | Typically **faster**, **O(n)** or **O(n log n)**, as each decision is made once without revisiting. | Typically **slower**, **O(n^2)** or higher, as it solves and stores multiple subproblems. |
| **Correctness**            | **Not guaranteed** to be correct for all problems; only works if the problem has the "greedy choice property" and "optimal substructure." | **Guaranteed** to be correct if the problem has overlapping subproblems and optimal substructure. |
| **Example Comparison**     | **Greedy Knapsack Problem**: Choose items that maximize value per unit weight without revisiting choices. | **0/1 Knapsack Problem**: Consider all possible combinations of items and choose the combination that maximizes value while staying within the weight limit. |

### Key Differences:
1. **Greedy Algorithms** make a local optimal choice at each step, aiming for a globally optimal solution, but they do not always guarantee the best solution.
2. **Dynamic Programming** solves complex problems by breaking them into smaller subproblems, storing the results of subproblems, and combining them, ensuring an optimal solution.

### In Summary:
- **Greedy Algorithms** are faster and simpler but only work for problems where local optimal decisions lead to a global optimum.
- **Dynamic Programming** is more robust and guarantees an optimal solution for problems with overlapping subproblems but often comes at the cost of more time and space complexity.

Would you like a deeper dive into any of these algorithms?

Example. Swap digits to make largest number. If duplicates are there then greedy does not work and requires DP


