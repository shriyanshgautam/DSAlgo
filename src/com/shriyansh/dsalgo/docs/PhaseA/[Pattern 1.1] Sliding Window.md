# Sliding Window

---

## A. Fixed Length Window

In fixed window problems, the window that we slide has fixed size, it slides one step at a time from both ends i.e. j++ and i++

#### Template
```kotlin
fun fixedSlidingWindow(arr: IntArray, k: Int): Int {
    var start = 0
    var accumulate = 0
    var ans = -1
    fot (end in 0..< arr.size) {
        val size = end - start + 1
        if (size < k) {
            accumulate += arr[end]
            // accumulation logic
        } else {
            // accumulation
            accumulate += arr[end]
            // finding if this window is optimal
            ans // update required optimal ans
            // slide window
            accumulate -= arr[start]
            start++
            accumulate += arr[end]
        }
    }
    return ans
}
```

#### Question 10
```ignorelang
Given an array (list) nums consisted of only non-negative integers, find the largest sum among all subarrays of length k in nums.

Example
Input: nums = [1, 2, 3, 7, 4, 1], k = 3
Output: 14
```
#### Solution
```kotlin
fun maxOfSlidingWindow(list: List<Int>, window: Int): Int {
  // First Window
  var windowSum = 0
  for (i in 0..<window) {
    windowSum += list[i]
  }

  var largestSum = windowSum
  for (i in window until list.size) {
    windowSum += list[i] // Add Next
    windowSum -= list[i - window] // Remove Last
    largestSum = Math.max(largestSum, windowSum)
  }
  return largestSum
}
```

#### Question 11
```ignorelang
Given a string original and a string check, find the starting index of all substrings of original that is an anagram of check. The output must be sorted in ascending order.

Example
Input: original = "cbaebabacd", check = "abc"
```
#### Solution

```kotlin
fun findAnagrams(input: String, check: String): List<Int> {
    if (input.length < check.length) return emptyList()
    var result = ArrayList<Int>()
    var checkSet = HashMap<Char, Int>()
    for (i in check.indices) {
        checkSet[check[i]] = checkSet.getOrDefault(check[i], 0) + 1
    }
    var windowSet = HashMap<Char, Int>()
    for (i in check.indices) {
        windowSet[input[i]] = windowSet.getOrDefault(input[i], 0) + 1
    }
    if (checkSet == windowSet) result.add(0)
    for (i in check.length..<input.length) {
        windowSet[input[i - check.length]] = windowSet[input[i - check.length]] - 1
        if (windowSet[input[i - check.length]] == 0) {
            windowSet.remove(input[i - check.length])
        }
        windowSet[input[i]] = windowSet.getOrDefault(input[i], 0) + 1
        if (checkSet == windowSet) result.add(i - check.length + 1)
    }
    return result
}
```

#### Question 12
```ignorelang
Find the maximum from every window of size k

Input: nums = [1, 2, 3, 7, 4, 1], k = 3
Output: [3, 7, 7, 7]

Hint: Use stack to keep max at each time, and check end while removing from window
```

#### Question 12
```ignorelang
Given a string s and an integer k, return the maximum number of vowel letters in any substring of s with length k.
Vowel letters in English are 'a', 'e', 'i', 'o', and 'u'.

Example 1:
Input: s = "abciiidef", k = 3
Output: 3
```

## B. <ins> Dynamic Length Window : Maximum Window

In dynamic window problems, the size of window can increase decrease to maintain the given condition (min sum, max sum, mix characters etc)
We traverse the array finding all such sub arrays. There could be scenarios where conditions could be negative or positive. We need to report either all such window positions or min or max

Notice:
1. In Fixed window problems, window size is fixed and we need to find windows that match the required condition (maximize/minimize).
2. In Dynamic window problem, we collect all windows matching given condition first and then return required window size (maximize, minimize)

Works for only positive items


1. When looking for a maximum substring that satisfies a given condition (e.g., the longest substring containing certain characters), the sliding window typically expands to include more characters, and only contracts when necessary (e.g., to meet a condition or optimize the window size).
2. When looking for a minimum substring that satisfies a given condition (e.g., the smallest window that contains all characters of another string), the sliding window needs to shrink as soon as a valid window is found, in order to minimize the size.


#### PsuedoCode for Maximum Window
```kotlin
fun slidingWindowMaximum(arr: IntArray, k: Int): Int {
    var start = 0
    var ans = Int.MIN_VALUE
    var accumulate = 0
    for (end in 0..<arr.size) {
        // expand by one
        accumulate += arr[end]
        
        // If condition voilating contract from start
        while (accumulate > k) {
            accumulate -= arr[start]
            start++
        }
        
        // Remember the max window
        ans = Math.max(ans, end - start + 1)
    }
    return ans
}
```

#### Question 13
```ignorelang
Find the length of the longest subarray with sum smaller than or equal to a target

Example
Input: nums = [1, 6, 3, 1, 2, 4, 5], target = 10
Output: 4
```
#### Solution
```kotlin
fun findLargestSubArrayWithSumLessThan(list: MutableList<Int>, target: Int): Int {
    var left = 0
    var windowSum = 0

    var largestLength = 0
    for (i in 0 until list.size) {
      windowSum += list[i]
      // Here window is contracted to make window valid while being longest
      while (windowSum > target) { // Contract from left till window is back to valid
        windowSum -= list[left]
        left++
      }
      largestLength = Math.max(largetLength, i - right + 1)
    }
    return largestLength
}
```

#### Question 14
```ignorelang
Find the length of the longest substring of a given string without repeating characters.

Example
Input: abccabcabcc
Output: 3

Hint: Is it same as k distinct character, use size of map == size of window, TODO: Try using set
```

#### Question 15
```ignorelang
Find the length of the longest substring of a given string with no more than k distict characters.

Example
Input: intput = araaci, k = 2
Output: 4
```

#### Question 16
```ignorelang
Given an array of characters where each character represents a fruit tree, you are given two baskets, and your goal is to put the maximum number of fruits in each basket. The only restriction is that each basket can have only one type of fruit and all trees used should be contiguous.

Example
Input: [3,3,3,1,2,1,1,2,3,3,4]
Output: 5

Variant: Pick Toys
Hint: k unique character
```

## C. Dynamic Sliding Window: Minimum Window

#### PsuedoCode for Min Window Length
```kotlin
fun slidingWindowMin(arr: IntArray, k:Int): Int {
    var start = 0
    var accumulate = 0
    var ans = Int.MAX_VALUE
    for (end in 0..<arr.size) {
        // expand to one step from end
        accumulate += arr[end]
        
        // if condition is satisfied, keep contacting and remembering
        while (accumulate == k) {
            ans = Math.min(ans, end - start + 1)
            accumulate -= arr[start]
            start++
        }
    }
    return ans
}
```

#### Question 17
```ignorelang
Find the length of the shortest subarray such that the subarray sum is at least target
Input: nums = [1, 4, 1, 7, 3, 0, 2, 5], target = 10
Output: 2
```
#### Solution
```kotlin
fun findSmallestSubArrayWithSumAtLest(list: MutableList<Int>, target: Int): Int {
  var left = 0
  var windowSum = 0

  var smallestLength = list.size
  for (i in 0 untill list.size) {
    windowSum += list[i]
    // Here window is contracted to keep length shorter while being valid
    while (windowSum >= target) {
      // Why inside
      // To track smallest before it became invalid
      smallestLength = Math.min(smallestLength, i - left + 1)
      windowSum -= list[left]
      left++
    }
  }
  return smallestLength
}
```

#### Question 18
```ignorelang
Given a string and a pattern, find the smallest substring in the given string which has all the characters of the given pattern.

Example
Input: input = "aabdec", pattern = "abc"
Output:
```

#### Question 19
```ignorelang
Given a binary array nums and an integer k, return the maximum number of consecutive 1's in the array if you can flip at most k 0's.

Example
Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
Output: 6
```
#### Solution
```kotlin
fun getLongestContiguousExceptK(nums: List<Int>, k: Int): Int {
    var left = 0
    var onesCount = 0
    var longestLength = 0
    for (i in 0..nums.size) {
        if (nums[i] == 1) {
            onesCount++
        }
        // If non 1 has started breaching k, increment left
        if ((i - left + 1) - onesCount > k) {
            if (nums[left] == 1) {
                onesCount--
            }
            left++
        }
        longestLength = Math.max(longestLength, i - left + 1)
    }
    return longestLength
}
```

#### Question 20
```ignorelang
Given a string with lowercase letters only, if you are allowed to replace no more than K letters with any letter, find the length of the longest substring having the same letters after replacement.

Example
Input: input = "aabccbb", k = 2
Output: 5
```

## D. Prefix Sum

#### Calculating Prefix Sum
```kotlin
fun getPrefixSum(list: List<Int>): List<Int> {
  val prefixSum = ArrayList<Int>()
  prefixSum.add(0) // Sum till 0 index is zero. Notice: end index is exluded
  var sum = 0
  for (i in 0 until list.size) {
    sum += list[i] // Or prefixSum[i] + list[i]
    prefixSum.add(sum)
  }
  return prefixSum
}

fun querySum(prefixSum: List<Int>,left: Int, right: Int): Int {
   return prefixSum[right + 1] - prefixSum[left]
}
```

#### Question 21
```ignorelang
There is a biker going on a road trip. The road trip consists of n + 1 points at different altitudes. The biker starts his trip on point 0 with altitude equal 0.
You are given an integer array gain of length n where gain[i] is the net gain in altitude between points i and i + 1 for all (0 <= i < n). Return the highest altitude of a point.

Input: gain = [-5,1,5,0,-7]
Output: 1
```
#### Solution
```kotlin
fun getHighest(list: List<Int>): Int {
    val prefixSum: List<Int> = getPrefixSum(list)
    var max = Integer.MIN_VALUE
    for (i in prefixSum) {
        max = Math.max(max, i)
    }
    return max
}
```

#### Question 22
```ignorelang
Given an integer array nums, find pair of indices which has sum equals to given target

```
#### Solution
```kotlin
fun findSubArrayTargetSum(list: List<Int>): Pair<Int, Int> {
  val prefixSumMap = HashMap<Int, Int>()
  prefixSumMap[0] = 0
  var sum = 0
  for (i in 0 until lis.size) {
    sum += list[i]
    // BusinessLogic
    if (prefixSumMap.contains(sum - target)) {
      return Pair(sum - target, i+1)
    }
    
    prefixSumMap[sum] = i+1
  }
  return null
}
```

#### Question 23
```ignorelang
The pivot index is the index where the sum of all the numbers strictly to the left of the index is equal to the sum of all the numbers strictly to the index's right.
If the index is on the left edge of the array, then the left sum is 0 because there are no elements to the left. This also applies to the right edge of the array.
Return the leftmost pivot index. If no such index exists, return -1.

Example
Input: nums = [1,7,3,6,5,6]
Output: 3

[0, 1, 8, 11, 17, 22, 28]
[0, 6, 11, 17, 20, 27, 28]
```
#### Solution
```kotlin
fun getPivot(list: List<Int>): Int {
    val prefixSum = getPrefixSum(list)
    val suffixSum = getSuffixSum(list)
    var max = Integer.MIN_VALUE
    for (i in 0 until list.size) {
        val sum = prefixSum[i] + suffixSum[list.size - i - 1]
        max = Math.max(max, sum)
    }
    return max
}
```

#### Question 24
```ignorelang
Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].

Example
Input: [1, 2, 3, 4].
Output: [24, 12, 8, 6].
```
