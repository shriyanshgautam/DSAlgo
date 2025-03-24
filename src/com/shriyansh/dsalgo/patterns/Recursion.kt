package com.shriyansh.dsalgo.patterns

fun main() {
//    calculatePreRecursion(6)
    sumPreRecursion(6, 0) // Result 6, 11, 15, 18, 20, 21
    sumPostRecursion(6) // Result 1, 3, 6, 10, 15, 21
}

fun calculatePreRecursion(n: Int) {
    if (n == 0) return
    println(n)
    calculatePreRecursion(n - 1)
}

fun calculatePostRecursion(n: Int) {
    if (n == 0) return
    calculatePostRecursion(n - 1)
    println(n)
}

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