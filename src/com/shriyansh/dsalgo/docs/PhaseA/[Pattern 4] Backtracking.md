# Backtracking

Type of recursive problems
1. Asks to solve by recursion
2. Recursively defined. Ex. kth grammar, Josephus problem | solved using hypothesis method
3. Tree
4. Choices and Decisions based problems | Focus on this | Solved using input/output i.e. candidate, choice and pruning method

## A. Recursion
Recursion involves first drawing the decision tree, either an explicit tree it given or you derive a decision tree based upon given conditions
Converting that decision tree to code is simpler.

Recursion forms the basis of Backtracking, DP and Divide and Conquer.

In recursion we pass input by value, hence we need to create a fresh instance at every node, we can't have a shared instance of result passed throughout. If it is required then it should use backtracking pass by reference

```kotlin
// For precalculation, we need to change signature so that state can be passed to children
// Also the termination not will return response and each call in stack will keep returning it, since no calculation is involved post recursive call
fun sumPreRecursion(n: Int, res: Int): Int {
    if (n == 0) return res
    // Calculation for this Node
    val newRes = res + n
    println(newRes)
    // Recurse to children passing them the calculation till this node
    // Note that this executes from top to bottom
    return sumPreRecursion(n - 1, newRes)
}
```

```kotlin
// Since calculation is done post recursion, the final ans will be only available after we reach back to root node, which will return the final ans
// Note that no state is passed, children just care about solving their sub problem and return result
fun sumPostRecursion(n: Int): Int {
    if (n == 0) return 0
    // Recursion to children
    val childrenAns = sumPostRecursion(n - 1)
    // Calculation for this node
    // User response from children to calculate ans for this node
    // Note that this executes while returning back from bottom to top
    val ans = childrenAns + n
    println(ans)
    return ans
}
```

// Note that n - 1 passed to children in both the variations is not considered as state, since it is the primary input over which the answer is calculated

// TODO: Think of problem that has static state passed to all children, and calculation post recursion
// TODO: Think of problem that has dynamic state passed to all children and calculation post recursion

// Opinion: Backtracking generally involves building ans from top to bottom, where leaf node finally knows the ans, updates it to result


// Types of problems
// 1. Listing where all nodes are visited to either find ones that are satisfying some given conditions
// 2. Branched Aggregation where all branches are evaluated to check which one is Max/Min, Exists, not exists
// 3. Overall aggregation where sum, max, min across all nodes irrespective of branches are required

// Types to approaches
// 1. Hypothesis of recursion. In simple problems where recursive relation is evident like f(n) = f(n - 1) + f(n - 2) or f(n) = n + f(n - 1)
// 2. Thinking in terms of Base Condition, Procession calculation, State, Recursive call (determining children) and return values
// 3. Decision tree

// Processing step: It is the responsibility/contribution of the current node in solving the overall problem. It involves thinking about:
// 1. Deciding if and what state to pass, is it static of dynamic
// 2. How to use recursive return value to build up the solution, both in pre and post variants
// 3. Either return value from child to parent with modification (post) or just pass on child's response to parent without modification (pre)


Hypothesis Design
1. First design the expectation from the root function call, both input and output
2. Now shorten the input for child, mostly the terminal value in array or one less in case of number
3. Now set up the expectations from the child recursion call with this input
4. Now determine what work needs to be done by current node to get from result of child to result of current node.
5. This will give the induction step of the node
6. Now prepare for base condition on basis of smallest input where solution needs no work on the input and relate that by running above hypothesis step with its parent call node


Note that it might be difficult to see where the actual problem is solved. You realise that it is solved by the incremental processing done by the node i.e. updating  state and input, using the result passed by child calls to prepare returnable result

Problem based on array as input
Since recursive problem will be solving a smaller problem, array needs to be shortened down. 
In this case we can use a pointer array, which points to the last element applicable for the child recursive call.
If each call needs to modify the input itself, then passing pointer would not work, we will have to pop out last element before recursion and push it back after recursion.
Note that array here is input and not state.

Pointer in case of array being passed as input.

#### Question
```ignorelang
Kth symbol in grammer, where k and n are given.
n = 0 means string "0"
n = 1 mean "01", that is every "0" in n - 1 will become "01" and every "1" in n - 1 will become "10"
n = 2 -> "0110"

k denotes the ith bit in the string of given k
Return the bit given n and k
```
Note:
1. Solve in terms of hypothesis design
2. TODO: Draw diagram for Induction and Hypothesis design for this
3. Notice that n+ 1 string can be generated by appending the reverse of n string. Although not useful in general recursion learning
3. Can k be moved out of recursion, as it is not helping in the incremental process. But basis the above observation, we can also reduce k to work for n - 1 problem

#### Question
```ignorelang
Tower of hanio with 3 poles and 3 disks
```
Note:
1. Solve in terms of hypothesis design



### Problems on Decision and Choices | Alternative to Hypothesis based problems
Notes
1. Look for choices as each step
2. Traversing the given array
3. Identifying condition for leaf node | base condition


#### Question
```ignorelang
Balanced paraenthesis of size n
```

Non array tracker problem

Choices 
1. same candidates available at all nodes

Pruning
1. If open count > closed
2. open and close < n

Termination
1. open and closed equal to 0


TODO: How can I use the input output method diagram? In my recursive definition, either result is build top to bottom an returned all the way up or
it is build from bottom to top, where the state is broken down from top to bottom phase.

In decision based problems, it is mostly top to bottom hence input/output method diagram works well for visualisation as you only draw the top to bottom phase and dont case about bottom to top return sequence
I also need to find a way to represent bottom to top diagrammatic representation


## Backtracking

It is type of recursion with pruning involved to avoid result that cannot lead to results
Also Backtracking is interested in valid PATHs from root to leaf satisfying the given conditions, paths are known once you reach at leaf nodes, similar to base recursion which is interested in leaf node results (top to bottom)
Back tracking is used in combinatorial/permutation problems
Hence Backtracking is pruning + path tracking for result from root to leaf

Most of the texts mention that backtracking is going back to parent if current solution does not work, but this is incorrect. Backtracking is mostly pruning at nodes where you decide not to take a path due to constraints
This path building is done by reference, hence if one branch you append a character, once you complete that branch and move to next you need to remove the character from path just to make way for other branch. This does not mean we are going back (backtracking) due to result not found in that branch, but it is part to trying out all possible branches. Each of the paths found shall still be considered later for global optimal out of them.
This removal of character is only required because path array is passed by reference (single instance). This removal is not due to backtracking algorithm. It can still be solve using pass by value and without removal of character step, but it will take lot of space
https://www.youtube.com/watch?v=9kl-VbTTwYo
Since there is shared instance of result path, along with removal of character on branch change, we also need to store the result once we reach the leaf node for later reference. Later we decide from tall the stored path whether we need to return all paths or optimal one out of it.
Since choices in backtracking problem are high in number or variable, we run a loop to run through each of them. Within this loop we will have pruning logic to move ahead only for valid choices
Notice there is so much complexity only due to path adding, removal, copy in result. This given indication that backtracking problems are mostly interested in path
Also notice that pruning is not only limited to backtracking , it is used in general recursion problems as well if required.
Hence difference between recursion and backtracking is very thin

DP is also type of recursion but uses caching and has some overlapping in sub-problems so that they can be reused from cache. It can use either memoization (top to bottom) or tabulation (bottom up)
DP is used to find the optimal solution
Greedy vs DP

Number of choices in DP and backtracking at each node is generally very large as compared to normal recursion

Refer size of problem i.e. limits of n, this will give you indication of whether to use recursion, backtracking, dp [Video](https://www.youtube.com/watch?v=3etzME4DNXg)


#### Backtracking Template
```kotlin
fun solve(candidates: List<Int>, path: MutableList<Int>, result: MutableList<Int>) {
    // notice candidates is input and path is output
    // Base condition
    if (isSolved(path)) {
        result.addAll(path.stream().toList())
        return
    }
    
    // Choices
    for (i in candidates) {
        path.add(i)
        // Pruning
        if (isValid(path)) {
            solve(candidates, path, result)
        }
        path.removeLast() // In basic recursion we would not have removed, since path would not be shared and we would create a new instance for children 
    }
}
```

#### Question
```ignorelang
Find all permutations of given string
```

```kotlin
// Recursive Solution
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

```kotlin
// Recursive with handling duplicate character in pruning
fun permutations(input: String, output: String, result: MutableList<String>) {
    if (input.isEmpty()) {
        result.add(output)
        return
    }

    val set: MutableSet<Char> = HashSet()
    for (i in input.indices) {
        // Pruning
        if (!set.contains(input[i])) {
            set.add(input[i])
            val newInput = input.substring(0, i) + input.substring(i + 1)
            val newOutput = output + input[i]
            permutations(newInput, newOutput, result)
        }
    }
}
```

```kotlin
// Backtracking 
fun permutations(input: String, output: MutableList<Char>, result: MutableList<MutableList<Char>>) {
    if (output.size == input.length) {
        result.add(output.stream().toList()) // Cloning to result
        return
    }

    val set: MutableSet<Char> = HashSet()
    for (i in input.indices) {
        // Pruning
        if (!set.contains(input[i])) {
            set.add(input[i])
            val newInput = input.substring(0, i) + input.substring(i + 1)
            output.add(input[i])
            permutations(newInput, output, result)
            output.removeLast()
        }
    }
}
```
```kotlin
// Backtracking with tracker
// With tracker we don't need to update input again and again
// Notice that both input and output 
fun permutations(tracker: Int, input: MutableList<Char>, result: MutableList<String>) {
    if (output.size == tracker) {
        result.add(input.toString()) // Cloning to result
        return
    }

    val set: MutableSet<Char> = HashSet()
    for (i in tracker..<input.length) {
        // Pruning
        if (!set.contains(input[i])) {
            set.add(input[i])
            swap(input[tracker], input[i])
            permutations(tracker + 1, input, result)
            swap(input[tracker], input[i])
        }
    }
}
```

Note:
1. Backtracking is natural extension of recursion.

#### Question
```ignorelang
Swap k digits in a number to make it largest, if digits can repeat

Example
Input: input: 4577
```
Hint:
1. Backtracking only required when largest digits are repeated, otherwise greedy can be applied
2. Notice terminating condition if (k == 1 || noSwapsPossible ie. digits already sorted)

#### Question
```ignorelang
Return all n digit numbers with strictly increasing digits
```

### Time complexity derivation for recursion/backtracking
This can be derived easily using the recursive tree rather than code
This is equal to product of complexity of work done in each node and number of nodes
Number of nodes = $c^1 + c^2 + c^3... c^n$ where c is branching factor = $O(c^n)$  // See it is the largest of all adding numbers as done in time complexity calculations
If the number of choices decrease as we go down the tree then Number of nodes = $n + n*(n - 1) + n*(n - 1)*(n - 2)...n!$ = $O(n!)$ // See it is the largest of all adding numbers as done in time complexity calculations
Work done in each node can be derived by removing recursive call, In most cases since there is loop for choices it becomes O(len(inputString))




**Backtracking** is a specialized type of recursion that involves **pruning**—discarding paths or solutions that cannot lead to valid results. The main goal of backtracking is to explore **valid paths** from the root to the leaf node, ensuring that each step satisfies the problem's constraints. Unlike basic recursion, which typically focuses on reaching a solution at the leaf node (top-down approach), backtracking explores potential solutions incrementally, and if a path doesn't lead to a valid solution, it backtracks to a previous decision point and tries a different path. **Backtracking** is commonly used in **combinatorial problems**, such as the N-Queens problem, Sudoku solving, or generating permutations.

---

**Dynamic Programming (DP)** is another form of recursion, but with a key difference: it optimizes recursive solutions by storing the results of subproblems to avoid redundant calculations. This technique leverages **caching** to reuse previously computed solutions, which significantly reduces the time complexity. DP is particularly effective when the problem has **overlapping subproblems**—i.e., the same subproblem is solved multiple times throughout the recursion. DP can be implemented using two approaches:
- **Memoization (Top-Down)**: Store results as you compute them recursively, and reuse them when the same subproblem appears again.
- **Tabulation (Bottom-Up)**: Solve smaller subproblems first, starting from the base case and building up to the final solution.

DP is typically used to find **optimal solutions** to problems that involve decision-making, such as the Knapsack problem, shortest path algorithms, and Fibonacci sequence calculations.

---

### Key Differences:
- **Backtracking** is focused on exploring paths to find a valid solution and backtracking when necessary.
- **Dynamic Programming** is designed for optimization, solving problems efficiently by caching subproblem results and reusing them.


| **Aspect**                  | **Recursion**                                   | **Backtracking**                                  | **Dynamic Programming**                             |
|-----------------------------|-------------------------------------------------|--------------------------------------------------|-----------------------------------------------------|
| **Definition**               | A method where the solution to a problem depends on solutions to smaller instances of the same problem. | A special case of recursion that explores all possibilities to find a solution by building incrementally and "backtracking" when a solution is not viable. | A method for solving problems by breaking them down into simpler subproblems and storing the results to avoid redundant work. |
| **Key Idea**                 | A function calls itself to solve a smaller instance of the same problem. | Recursive search through decision spaces, eliminating paths that don’t lead to a solution. | Breaking a problem into subproblems, solving them once, and using the results for later computations (memoization or tabulation). |
| **Problem Type**             | Can be applied to any problem that can be broken into smaller instances of itself. | Typically used for combinatorial problems (e.g., puzzles, paths). | Best suited for optimization problems with overlapping subproblems (e.g., shortest path, knapsack). |
| **Efficiency**               | Can be inefficient, especially without memoization or optimization. | Generally inefficient due to exploring all possible paths (brute force approach). | Highly efficient as it avoids redundant calculations by storing intermediate results. |
| **Memory Usage**             | Can consume a lot of memory if the depth of recursion is large. | Uses additional memory in terms of recursion stack and possible path exploration. | Typically uses additional memory for storing solutions to subproblems (in tables or arrays). |
| **Example Problems**         | Factorial calculation, Fibonacci numbers. | N-Queens problem, Sudoku solver, maze solving. | Fibonacci numbers (using memoization), Knapsack problem, Shortest path in graphs (e.g., Dijkstra’s algorithm). |
| **Termination Condition**    | Must have a base case to stop the recursion. | Must have base cases and also includes pruning of invalid paths. | Usually terminates after solving all subproblems and combining their solutions. |
| **Subproblem Overlap**       | No inherent reuse of solutions; each recursive call is independent. | No reuse of solutions; each branch of exploration is tried separately. | Significant reuse of subproblem solutions, especially in overlapping subproblems. |
| **Solution Construction**    | The solution is built progressively through recursive calls. | The solution is built incrementally and backtracked when a decision doesn’t lead to a solution. | The solution is constructed by combining solutions to subproblems, often in a bottom-up manner. |
| **Complexity**               | Time complexity can be high due to repeated work. | Time complexity is often exponential due to exploration of all paths. | Time complexity is reduced compared to recursion due to avoiding recomputation (polynomial or better). |
| **Memoization**              | Recursion itself doesn’t use memoization, but it can be optimized with it. | Not inherently using memoization, although it can be applied. | Memoization is a key aspect (top-down approach) or tabulation (bottom-up approach) is used. |

### Hierarchy and Relationships:
- **Recursion** is the general concept and can be applied to both **Backtracking** and **Dynamic Programming**.
- **Backtracking** is a specific form of recursion where you explore potential solutions, discard invalid ones, and backtrack to earlier choices when needed.
- **Dynamic Programming** is a technique that optimizes recursion by avoiding redundant calculations (using memoization or tabulation). It can be seen as a refined approach that builds on recursion to efficiently solve optimization problems.

In summary:
- **Recursion** is the base technique.
- **Backtracking** is a form of recursion aimed at finding solutions by exploring all options and "undoing" decisions when necessary.
- **Dynamic Programming** is a more efficient approach to recursion that solves problems by storing previously computed results, reducing time complexity.