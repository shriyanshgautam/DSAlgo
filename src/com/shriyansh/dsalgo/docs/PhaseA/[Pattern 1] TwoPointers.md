# Two Pointers 

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

