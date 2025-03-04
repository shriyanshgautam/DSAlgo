# Binary Search

Binary Search intends to search target position in an array/domain in around O(logn) time which is less that linear search time of O(n)

## A. Basic Implementation

#### Question
```ignorelang
Given Sorted Array and a target to find, find the index of the target if present in the
array otherwise return -1
```
#### Basic Implementation
```kotlin
fun binarySearch(items: List<Int>, target: Int): Int {
    var left = 0
    var right = items.size - 1
    
    while (left <= right) {
        val mid = left + (right - left)/2
        
        // 1. Checking if seek element is satisfying or not
        if (items[mid] == target) {
            return mid
        }
        
        // 2. Choosing direction
        if (items[mid] > target) {
            right = mid - 1
        } else {
            left = mid + 1
        }
    }
    return -1
} 
```

Notes:
1. Note Mid can also be calculated as (left + right) / 2, but if they are large, summing them up might overflow, hence calculating incrementally using left + (right - left)/2 avoid that
2. Logic involves:
     1. looking at mid and checking if it satisfies the criteria
     2. Choose direction to move on and ignore other
3. equality check in <= check in while loop ensure edge case of one element in array. Only in vanilla implementation mid can be discarded in further iterations, but not in modified implementations

#### Question
```ignorelang
Given Reverse Sorted Array and a target to find, find the index of the target if present in the
array otherwise return -1

Example
Input arr = [15, 11, 9, 7, 4, 3, 1], target = 3

Reverse the direction choosing logic
```

## B. Repeated & Sorted

#### Question 1
```ignorelang
Given a sorted array of integers and a target integer, find the first occurrence of the target and return its index. Return -1 if the target is not in the array.

Input: arr = [1, 3, 3, 3, 3, 6, 10, 10, 10, 100], target = 3
Output: 1
```

#### Solution
```kotlin
fun binarySearchRepeated(items: List<Int>, target: Int): Int {
    var left = 0
    var right = items.size - 1
    var lastMatching = -1
    while (left <= right) {
        val mid = left + (right - left) / 2
        if (items[mid] == target) { // continue to find boundary
            lastMatching = mid
            right = mid - 1
        } else if(items[mid] > target) {
            right = mid - 1
        } else {
            left = mid + 1
        }
    }
    return lastMatching
}
```

Note
1. If we found matching element at middle, we still need to continue to see if it is boundary, remember the last matched one in separate variable

#### Question
```ignorelang
Given a sorted array of integers and a target integer, find the first  and last occurrence of the target. Return -1 if the target is not in the array.

Input: arr = [1, 3, 3, 3, 3, 6, 10, 10, 10, 100], target = 3
Output: 1

Sol: Apply Binary Search two times, one for first occurance, anoher for last occurance. Last occurance version moves to right in case of match

Variant: Find number of occurance of given target in a sorted array
```

#### Question
```ignorelang
Find given target in a nearly sorted array. Nearly sorted means and item can be deviated from its position by maxiumum of one position to either left or right

Example
Input: arr=[5, 10, 30 ,20 ,40], target = 20

Sol: In this case we just not only check mid in first logic block, but also check mid - 1 and mid + 1, then more to either start to mid - 2 or mid + 2 to end. Also before checking mid - 1 and mid + 1 does not go out of bounds 
```

#### Question 2
```ignorelang
An array of boolean values is divided into two sections: The left section consists of all false, and the right section consists of all true. Find the First True in a Sorted Boolean Array of the right section, i.e., the index of the first true element. If there is no true element, return -1.

Example 1
Input: arr = [false, false, true, true, true]
Output: 2
```
#### Solution
```kotlin
// Degenerate of previous one, since we know that only two values are possible
fun binarySearchMonotonic(items: List<Boolean>): Int {
    var left = 0
    var right = items.size - 1
    var lastMatching = -1
    while (left <= right) {
        val mid = left + (right - left)/2
        if (items[mid] == true) { // feasible(), continue to find boundary
            lastMatching = mid
            right = mid - 1
        } else {
            left = mid + 1
        }
    }
    return lastMatching
}
```

## C. Finding Previous / Floor or Next / Ceil of target
In this case element might be present multiple times, or absent. I f present them return its index, otherwise provide previous or next

#### Question
```ignorelang
Find Floor of target in a sorted array. If element is present then return its index, otherwise provide just smaller value's index

Example
Input: arr=[1, 2, 4, 5, 7, 8, 10], target = 6

Variant: Find Ceil of target in a sorted array
Variant: Find Next Letter in a sorted alphabetical array for a given target. Here even if element is present, give next character. Characters might be repeated
 * Hint: Continue to right in case of match
```

#### Solution
```kotlin
fun binarySearch(arr: IntArray, target: Int): Int { 
    var start = 0 
    var end = arr.size - 1
    var lastIndex = -1
    while (start <= end) { 
        val mid = start + (end - start)/2
        if (arr[mid] == target) { return mid }
        if (arr[mid] < target) {
            lastIndex = mid
            start = mid + 1
        } else {
            end = mid - 1
        }
    }
    return lastIndex
}
```

Note
1. We cannot just find element (in case it is present) and give its previous/next index, since element might be repeated


#### Question
```ignorelang
Find element that is closest to target in terms of value

Example
Input: arr=[1, 3, 6, 7 ,8], target = 4

Sol: Combine Floor and Ceil in case of repeated element OR normal binary serach at while terminating condition will have start and end as neighbouring elements of target with start > end
```
## D. Dynamic determination of satisfying criteria and direction choice
In these problems we define both the conditions, 1. if mid satisfies the condition, 2. choose the direction to continue otherwise


#### Question
```ignorelang
Find the index of the peak element. Assume there is only one peak element.

Input: arr=[0 1 2 3 2 1 0]
Output: 3

Variant: Find max in  bitonic array. Remember in peak finding previous problem, there can be multiple peaks, but answer rquires any one
```

#### Solution
```kotlin
fun peak(arr: IntArray): Int {
    var start = 0
    var end = arr.size - 1
    while (start <= end) {
        val mid = start + (end - start)/2
        if (mid > 0 && mid < arr.size - 1) {
            if (arr[mid] > arr[mid -1] && arr[mid] > arr[mid + 1]) { return mid }
            if (arr[mid - 1] > arr[mid + 1]) { end = mid -1} else { start = mid + 1 }
        } else if (mid == 0) {
            if(arr[mid] > arr[mid + 1]) { 
                return 0 
            } else {
                return 1
            }
        } else if (mid == arr.size - 1) {
           if (arr[mid] > arr[mid - 1]) { 
               return arr.size - 1
           } else {
               return arr.size - 2
           }
        }
    }
}
```
Note:
1. Satisfying condition that arr[mid] is greater that both left and right element. Handle mid=0 and mid=arr.length edge cases
2. Direction can be determined by value of arr[mid - 1] > arr[mid + 1]

#### Question
```ignorelang
Find target in bitonic array

Example
Input: arr=[0 1 2 3 2 1 0]
Output: 2
```

#### Solution
```kotlin
fun binarySearchBitonicArray(arr: IntArray, target: Int): Int {
    val peakIndex = findPeak(arr)
    val left = binarySearch(arr, start, peakIndex, target)
    val right = binarySearchReverseSorted(arr, peakIndex + 1, arr.size - 1, target) // Reverse Sorted
    if (left != -1) { return left }
    if (right != -1) { return right }
    return -1
}
```

#### Question
```ignorelang
A sorted array of unique integers was rotated at an unknown pivot. For example, [10, 20, 30, 40, 50] becomes [30, 40, 50, 10, 20]. Find the index of the minimum element in this array.

Input: [30, 40, 50, 10, 20]
Output: 3

Variant: Find number of rotations
* Hint: Number of rotations == index of minimum element
```

#### Solution 
```kotlin
fun findMinIndexRotated(arr: IntArray): Int {
    var start = 0
    var end = arr.size - 1
    while (start <= end) {
        val mid = start + (end - start)/2
        val prev = (mid - 1 + arr.size) % arr.size
        val next = (mid + 1) % arr.size
       
        if (arr[mid] <= arr[prev] && arr[mid] <= arr[next]) { return mid }
        if (arr[start] <= arr[mid]) { 
            start = mid + 1 
        } else if (arr[mid] <= arr[end]) { 
            end = mid - 1 
        }
    }
    return -1
}
```

Note:
1. Satisfying condition that arr[mid] is less that both left and right element. Here take (mid + 1)%arr.length for next or (mid - 1 + arr.length)%arr.length for previous
2. Direction can be determined by value arr[start] > arr[mid]

#### Question
```ignorelang
Find target in rotated sorted array

Example
Input: arr=[30, 40, 50, 10, 20], target=40
```

#### Solution
```kotlin
fun rotatedBinarySearch(arr: IntArray, target: Int): Int {
    val minIndex = findMinIndexRotated(arr)
    val left = binarySearch(arr, 0, minIndex - 1, target)
    val right = binarySearch(arr, minIndex, arr.size - 1, target)
    if (left != -1) { return left }
    if (right != -1) { return right }
    return -1
}
```

## E. Monotonicity
Binary Search apart from being applicable in sorted array, can also be used in any monotonic function as well

There is a `feasible() ` method that checks whether the current (mid) satisfies the search. Any element after that will always satisfy as per monotonicity
This also involves determining whether to go left or right
#### Question 3
```ignorelang
Given an array of integers sorted in increasing order and a target, find the index of the first element in the array that is larger than or equal to the target. Assume that it is guaranteed to find a satisfying number.

Example
Input: arr = [1, 3, 3, 5, 8, 8, 10], target = 2
Output: 1
```
#### Solution
```kotlin
// Notice how we assume that target element might not be exactly present hence merging case equality and less than in one
fun searchFeasibleInMonotonic(items: List<Int>, target: Int): Int {
    var left = 0
    var right = items.size - 1
    var lastMatching = -1
    while (left <= right) {
        val mid = left + (right - left) / 2
        if (feasible(items[mid], target)) { // continue to find boundary
            lastMatching = mid
            right = mid - 1
        } else {
            left = mid + 1
        }
    }
    return lastMatching
}

fun feasible(found: Int, target: Int): Boolean {
    return found >= target
}
```

## F. Find in infinite array / number line

#### Question
```ignorelang
Find position of element in a sorted but infinitely long array

Example: arr = [1, 3 ,4 , 8, 10 ......], target = 25
```

#### Solution
```kotlin
fun binarySearchInfinite(arr: IntArray, target: Int): Int {
   var start = 0
   var end = 1
   // Finding suitable end
   while (arr[end] < target) { 
       start = end
       end *= 2
   }
   return binarySearch(arr, start, end, target)
}

fun binarySearch(arr: IntArray, start: Int, end: Int, target: Int): Int {
   var left = start
   var right = end - 1
   while (left <= right) {
      val mid = start + (end - start) / 2
      if (arr[mid] == target) {
         return mid
      }
      if (arr[mid] < target) {
         left = mid + 1
      } else {
         right = mid - 1
      }
   }
   return -1
}
```

Note:
1. Determining `end` is a challenge. Start with end = 1 and every time we need to move to right, multiply it by 2 to expand to right

#### Question
```ignorelang
Find first 1 in a Binary Sorted array consisting of 0 and 1

Input: arr=[0, 0, 0, 0, 0, 0, ....]

Sol: Similar to infinite + 0,1 or repeating elemtn, i.e. first occurance solution
```


#### Question 6
```ignorelang
Given an integer, find its square root without using the built-in square root function. Only return the integer part (truncate the decimals).

Example
Input: 16
Output: 4

Note that here if we do not find exactly matching one, we need to report the previous one, hence, index of first feasible - 1
```


## G. 2D Binary Search

#### Question
```ignorelang
Binary Search in 2D sorted both row and column wise

Sol: Start with first element of last column as mid, while it is greater than target jump to first element of previous column (j--). Once that we find smaller element we do i--. Repeat it
If we are goind to boundary of arr and cannot go beyond, then element is not present
```
#### Solution
```kotlin
fun twoDimensionBinary(arr: List<List<Int>>, target: Int): Pair<Int, Int> {
    var i = 0
    var j = arr.size - 1
    while (i < arr.size && j >= 0) {
        if (arr[i][j] == target) { return Pair(i, j) }
        if (arr[i][j] > target) {
            j--
        } else {
            i++
        }
    }
   return Pair(-1, -1)
}
```
Note:
1. Complexity O(n + m)

## H. Application of Binary Search: TBD
These questions involve multiple objects all growing/consumed starting t=0 simultaneously. At any time after t=0 as time is spent items are complete/available/finished. at t=max(arr), all items are available/finished/complete.
You need to apply binary search to find minimum t such that given condition is satisfied

### I. Equal allocation: Min of Max / Max of Min
#### Question
```ignorelang
Given array of number of pages of different books and number of readers available. One book cannot be shared between readers. 
Atleast one book should be given to one reader. Books should be given continuos. Find Minimize maximum no of pages read by any reader.

Example
Input: arr=[10, 20, 30, 40], k=2

Variant: Painters Problem | Split array with largest sum
Variant: Min Capacity to ship given packages with given weights in k days
Hint: min starts with max(arr) and max starts with sum(arr)
```

#### Solution
```kotlin
fun allocateBooks(arr: IntArray, k: Int): Int {
    var start = arr.min() // Min possible is just the smallest element allocated
    var end = arr.sum() // Max possible is entire book set allocated to one
    while (start <= end) {
        val mid = start + (end - start)/2
        if (evaluate(arr, mid, k)) {
            // move to right to get max possible
            start = mid + 1
        } else {
            end = mid - 1
        }
    }
    return start
}

fun evaluate(arr: IntArray, mid: Int, k: Int): Boolean {
    var students = 1
    var pagesRead = 0
    for (item in arr) {
        if (pagesRead + item < mid) {
            pagesRead += item
        } else {
            students++
           pagesRead = item
        }
    }
   return students > k
}
```

#### Question
```ignorelang
Bloom days required to prepare m buquet of n flowers each from different flowers in consequtive positions

Example
Input: arr:[1, 3, 5, 6, 9, 11], m=2 n=3
```

#### Solution
```kotlin
fun flowerBloomingDays(arr: IntArray, m: Int, n: Int): Int {
    var start = arr.min() // Min days for one bloom 
    var end = arr.max() // Max days for all bloom
    while (start <= end) {
        val mid = start + (end - start)/2
        if (evaluate(arr, mid, m, n)) {
            // move to left to find least possible
            end = mid - 1
        } else {
            start = mid + 1
        }
    }
   return start
}

fun evaluate(arr: IntArray, mid: Int, m: Int, n: Int): Boolean {
    var availableFlowers = 0
    var boquetCount = 0
    for (item in arr) {
        if (item < mid) {
            availableFlowers++
        } else {
            // For break in consecutiveness
            boquetCount += availableFlowers/n
            availableFlowers = 0
        }
    }
    // For no break in consecutiveness
    boquetCount += availableFlowers/n
   return boquetCount > m
}
```

#### Question
```ignorelang
Koko eating bananas. Find min number of bananas to eat per hours rate to complete in k hours, Each bucket in array should be eaten sequentially only

Example
Input: arr=[3, 6, 7, 11], k=8

Sol: Start with min as 1 and max at max(arr)
Taking mid as number of bananas eating per hour
```

#### Solution: Partial
```kotlin
fun evaluate(arr: IntArray, mid: Int, k: Int): Boolean {
    var hoursConsumed = 0
    for (item in arr) {
        hoursConsumed += ceil(item/mid)
    }
    return hoursConsumed < k
}
```

#### Question
```ignorelang
Aggressive Cows. Given position for each avaiable counter for cows. Find the min distance possible to place k cows

Example:
Input: arr=[0, 3, 4, 7, 9, 10], k=4

Sol: Start with min as 1 and max as (arr[arr.size - 1] - arr[0])
Taking mid as required distance between consecutive counters

Variant: Has Station positining
Hint: Start low as 0, max as (arr[arr.size - 1] - arr[0])
```
#### Solution: Partial
```kotlin
fun evaluate(arr: IntArray, mid: Int, k: Int): Boolean {
    var cows = 1
    var last = arr[0]
    for (item in arr.slice(1..arr.size)) {
        // if distance between current stall and last stall is atleast the reuired
        if (item - last >= mid) {
            cows++
            last = item
        }
    }
    return cows >= k
}
```
