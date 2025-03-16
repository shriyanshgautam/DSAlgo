# Misc

## A. Intervals


## B. Stack

One contrasting identification is that in nested loop O(n<sup>2</sup>) with i and j, when j = f(i) then stack can be used

To keep greater elements on right
1. Prepare a stack starting from right and, keep popping till top is smaller that current element or stack becomes empty
2. Top element is NGE or -1. Insert the current element for next iteration

#### Question
```ignorelang
Find just next geater element in given array

Example
Input: arr=[1, 3, 2, 6, 5, 7, 9, 5]

Hint: Stack Looks like following
[5], NGE -1
[9], NGE -1
[7, 9] NGE 7
[5, 7, 9] NGE 7
[6, 7, 9] NGE 7
[2, 6, 7, 9] NGE 6
[3, 6, 7, 9] NGE 6
[1, 3, 6, 7, 9] NGE 3
```

#### Pseudocode

```kotlin
fun ngeRight(arr: IntArray): List<Int> {
    var stack: Stack<Int> = Stack()
    var result = mutableListOf<Int>()
    for (i in arr.size - 1 downTo 0) {
        // Keep popping till we find larger than current element
        while (!stack.isEmpty() && stack.peek() <= arr[i]) {
            stack.pop()
        }
        
        // Record top element as NGE or -1
        if (stack.isEmpty()) { result.add(-1) } else { result.add(stack.peek()) }
        
        // Push current element for next iteration
        stack.push(arr[i])
    }
    return result.reversed()
}
```

Note:
1. For NGE on left, change traversal direction to l->r and no reversal required
2. For NSE on right, change to -> keep popping while element is smaller than top i.e. looking for smaller in stack

#### Question
```ignorelang
Stock Span Problem. Given stock prices. Find number of consecutive smaller on left for each day proce

Example
Input: arr=[100, 80, 60, 70, 60, 75, 85]
Hint: i - (NGE index). Store index along with value in stack, Store index in result
i.e no. of element on left till before NGE
```

#### Solution

```kotlin
import java.util.Stack

fun ngeLeft(arr: List<Int>): List<Int> {
    var stack = Stack<Pair<Int, Int>>()
    var result = ArrayList<Int>()
    for (i in 0..arr.size - 1) {
        while (!stack.isEmpty() && stack.peek().first <= arr[i]) {
            stack.pop()
        }
        if (stack.isEmpty()) { result.add(-1) } else { result.add(stack.peek().second) }
        stack.push(Pair(arr[i], i))
    }
    
    var ans = ArrayList<Int>()
    for (i in 0..arr.size - 1) {
        ans[i] = i - result[i]
    }
    return ans
}
```

Note:
1. We put -1 in index to denote index -1 i.e. no element from the array, also element before index 0. We call these pseudo-index

#### Question
```ignorelang
Maximum Area of Histogram. Given heights of histogram of unit width. find the area of maximum single rectangle that can be fit over the entire set

Example
Input: arr=[6, 2, 5, 4, 5, 1, 6]
```

Note:
1. Int above case for each index we look both left and right, we can go uptill NSE - 1 on both side to get rectangle of area (right - left + 1) * arr[i]
2. PseudoIndex: Put -1 in case of no element on left to indicate index -1 and similarly arr.size for right in case of no element. 
3. Remember to reverse NGE/NSE on right array
4. In these problems you prepare NGE/NSE for both sides both

#### Question
```ignorelang
Maximum area reactangle in binary 2D matrix

Example
Input: arr=[
  [0, 1, 1, 0],
  [1, 1, 1, 1],
  [1, 1, 1, 1],
  [1, 1, 0, 0]
]
```
Note:
1. This problem can be broken into MaximumHistogramArea i.e. MAH each for 1x4, 2x4, 3x4 and them 4x4 from top and taking max of them
2. Convert each 2D 1x4, 2x4, 3x4, 4x4 into 1D array of heights. In 4x4 last two will have 0 heights due to breakage
3. Ww can use only 1D array and reutilize one-by-one sequentially 1x4 then 2x4 then 3x4 and then 4x4

#### Solution
```kotlin
fun hitogram2D(arr: List<List<Int>>): Int {
    var histo = ArrayList<Int>()
    // For first row
    for (j in 0..arr[0].size) {
        histo[j] = arr[0][j]
    }
    
    var mah = MAH(histo)
    
    // for each other row
    for (i in 1..arr.size) {
        for (i in 0..arr[i].size) {
            if (arr[i][j] == 0) {
                histo[j] = 0
            } else {
                histo[j] = histo[j] + arr[i][j]
            }
        }
        mah = Math.max(mah, MAH(histo))
    }
    return mah
}
```

#### Question
```ignorelang
Rain water trapping. Given the heights of buildings in an array of width one unit. Find the total amount of water trapped in these buildings

Example: arr[2, 0, 0, 2, 0, 4]
```

Note
1. This problem is not same as histogram and does not use NGE on left and right, but uses absolute max/min on left/right.
2. This in fact depends largest/maximum element on left and right
3. Find water on each building by min(maxleft, maxright) - arr[i]

#### Preparing Max on left/right
```kotlin
fun maxLeft(arr: List<Int>): List<Int> {
    var result = ArrayList<Int>()
    result[0] = arr[0]
    for (i in 1..arr.size - 1) {
        result[i] = Math.max(result[i - 1], arr[i])
    }
    return result
}

fun maxRight(arr: List<Int>): List<Int> {
    var result = ArrayList<Int>()
    result[arr.size - 1] = arr[arr.size - 1]
    for (i in arr.size - 2..0) {
        result[i] = Math.max(result[i + 1], arr[i])
    }
    return result
}

fun rainWater(arr: List<Int>): Int {
    var water = ArrayList<Int>()
    var maxLeft = maxLeft(arr)
    var maxRight = maxRight(arr)
    for (i in 0..arr.size - 1) {
        water[i] = Math.min(maxLeft[i], maxRight[i]) - arr[i]
    }
    
    var result = 0
    for (i in 0..water.size - 1) {
        result += water[i]
    }
    return result
}
```

#### Question
```ignorelang
Query minimum stack element in stack at each point, apart from existing push and pop operations

Variant: Max stack query
```

Note:
1. This requires auxilary stack to record min in current stack
2. On push, check if top is larger, equal or empty, then push current element, marking it as smallest in current stack
3. On Pop, check if top has that element, if yes then pop from auxilary as well
4. getMin() return top of auxilary stack

#### Solution
````kotlin
fun push(item: Int) {
    stack.push(item)
    // Notice equality
    if (aux.isEmpty() || aux.peek() >= item) {
        aux.push(item)
    }
    
}
fun pop(): Int {
    if (stack.isEmpty) { return -1 }
    if (aux.peek() == stack.peek()) {
        aux.pop()
    }
    return stack.pop()
}
fun min(): Int {
    if (aux.isEmpty) { return -1 }
    return aux.peek()
}
````

#### TLDR on stack min/max element for O(1) space
Note
1. Just use one variable min = first insertion
2. Push: for each insertion, if first or not new min then normal push, if new min then insert 2*element - min, and min = element
3. Pop: If element is greater/equal, then just pop from main stack, if less, then this indicates it is min element modified, to get back the popped element return min, update min to 2*min - stock.peek()
4. Notice that the condition top < min indicates that value is actually stored in min rather than the array. It also indicates that previous min needs to be reinstated.


#### Question
```ignorelang
From given number string remove k digits to make the number smallest possible

Hint: Use stack from left to right, push first, for each largest number push it, while new incoming is smaller than top, pop top and push, reduce k. Do this untill k is zero. Push rest and then pop out all
if k == num.len return 0,
if stack.len == num.len, remove last k
```

#### Question
```ignorelang
Sliding window (fixed) tracking maximum

Similar to Max stack, notice the variations, insertion, removes other elements, pop removes from other end
```