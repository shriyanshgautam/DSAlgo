# Two Pointers & Sliding Window

## A. Two Pointers 

----

### I. <ins>Same Direction

#### Question 1
```ignorelang
Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
Note that you must do this in-place without making a copy of the array.

Example:

Input: nums = [0,1,0,3,12]
Output: [1,3,12,0,0]
```

#### Solution
```kotlin
fun moveZerosToFront(list: MutableList<Int>) {
  var slow = 0
  for (i in 0..< list.size) {
    if (list[i] != 0) {
      swap(list, i, slow)
      slow++
    }
  }
}
```
### Other Questions
#### Question 2
```ignorelang
Given a sorted list of numbers, remove duplicates and return the new length. You must do this in-place and without using extra memory.

Example

Input: [0, 0, 1, 1, 1, 2, 2].
Output: 3.
```
#### Question 3
```ignorelang
Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).

Example 1:

Input: s = "abc", t = "ahbgdc"
Output: true
```
#### Solution

```kotlin
fun isSubsequence(input: String, target: String): Boolean {
    var slow = 0
    for (char in target) {
        if (char == input[slow]) {
            slow++
            if (slow == input.length) return true
        }
    }
    return false
}
```

### II. <ins>Opposite Direction : One step at a time

#### Question 4
```ignorelang
You are given an integer array nums and an integer target. Find pair of integers whose sum is equal to target

Example

Input: nums = [2,4,1,7,5,9], target = 6
Output: [[2,4],[1,5]]
```
#### Solution
```kotlin
fun twoSumPair(list: List<Int>, target: Int): List<Pair<Int, Int>> {
    val sortedList = list.sorted()
    var left = 0
    var right = sortedList.size - 1
    val result = ArrayList<Pair<Int, Int>>()
    while (left < right) {
        val sum = sortedList[left] + sortedList[right]
        if (sum == target) {
            result.add(Pair(sortedList[left], sortedList[right]))
            left++
            right--
        }
        if (sum < target) left ++
        if (sum > target) right --
    }
    return result
}

// Variant
fun ifTwoSum(list: List<Int>, target: Int): Boolean {
    val sortedList = list.sorted()
    var left = 0
    var right = sortedList.size - 1
    while (left < right) {
        val sum = sortedList[left] + sortedList[right]
        if (sum == target) {
            return true
        }
        if (sum < target) left ++
        if (sum > target) right --
    }
    return false
}
```
#### Question 5
```ignorelang
You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
Find two lines that together with the x-axis form a container, such that the container contains the most water.
Return the maximum amount of water a container can store.

Example

Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
((indexOf(7) - indexOf(8)) * min(8,7))
```
#### Solution
```kotlin
fun getMaxWater(list: List<Int>): Int {
    var left = 0
    var right = list.size - 1
    var maxVolume = 0
    while (left < right) {
        val volume = (right - left) * Math.min(list[left], list[right])
        if (volume > maxVolume) maxVolume = volume
        if (list[left] <= list[right]) { // If we put two separate if conditions, the second if condition will calculate incorrectly, hence good to have if anf else with exhaustive
            left++
        } else {
            right--
        }
    }
    return maxVolume
}
```

#### Question 6
```ignorelang
You are given an integer array nums and an integer k.
In one operation, you can pick two numbers from the array whose sum equals k and remove them from the array.
Return the maximum number of operations you can perform on the array.

 
Example 1:

Input: nums = [1,2,3,4], k = 5
Output: 2

```
### III. <ins>Opposite Direction : Multiple step at a time
#### Question 7
```ignorelang
Determine whether a string is a palindrome, ignoring non-alphanumeric characters and case. Examples:

Input: Do geese see God? Output: True
```
#### Solution
```kotlin
fun isPalindrome(text: String): Boolean {
    var left = 0
    var right = text.length - 1
    while (left < right) {
        while (left < right && !text[left].isLetterOrDigit()) {
            left++
        }
        while (left < right && !text[right].isLetterOrDigit()) {
            right--
        }
        // Business logic
        if (text[left].lowercaseChar() != text[right].lowercaseChar()) return false
        left++
        right--
    }
    return true
}
```
### Other Question
#### Question 7
```ignorelang
Given a string s, reverse only all the vowels in the string and return it.
The vowels can appear in both lower and upper cases, more than once.

Example 1:
Input: s = "IceCreAm"
Output: "AceCreIm"
```

### IV. <ins> Pointer Based / Fixed Speed

#### Question 9
```ignorelang
Find the middle node of a linked list.

Example

Input: 0 1 2 3 4
Output: 2
```
#### Solution
```kotlin
fun middleOfLinkedList(head: Node<Int>): Node<Int> {
    var fast = head
    var slow = head
    if (fast.next != null && fast.next.next != null) {
        slow = slow.next
        fast = fast.next.next
    }
    return slow
}
```

## B. Sliding Window

---

### I. Fixed Length Window

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
Given a string s and an integer k, return the maximum number of vowel letters in any substring of s with length k.
Vowel letters in English are 'a', 'e', 'i', 'o', and 'u'.

Example 1:
Input: s = "abciiidef", k = 3
Output: 3
```

### II. <ins> Dynamic Length Window

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

## C. Prefix Sum

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
