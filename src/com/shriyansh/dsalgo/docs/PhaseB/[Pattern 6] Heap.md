# Heap

Every Heap based problem can be solved by sorting in O(nlogn). Heap can provide a way to optimize it if k is involved in the question to O(nlogk)
* For finding k largest, use Min Heap of size k, so that you maintain largest k, with smallest of them at the top. This top will help us decide if upcoming element can be swapped/inserted of not
* For finding k smallest, use Max Heap of size k, so as to maintain smallest k, with largest of them at the top. ...
* After all processing, the top element is the kth largest/smallest element. Pop out one by one to get in sorted order, do not dump out the heap.
* After all processing, all the elements in heap are k largest/smallest ones

### Heap Implementation

## A. Top K

#### Question
```ignorelang
Given an integer array nums and an integer k, return the kth largest element in the array.
Note that it is the kth largest element in the sorted order, not the kth distinct element.

Example:

Input: nums = [3,2,1,5,6,4], k = 2
Output: 5

```

#### Question
```ignorelang
Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k, return the k closest points to the origin (0, 0).
The distance between two points on the X-Y plane is the Euclidean distance

Example:

Input: points = [[3,3],[5,-1],[-2,4]], k = 2
Output: [[3,3],[-2,4]]
```

#### Question
```ignorelang
Find k closest numbers to a given number

Sol: Heap on distance from target number, while keeping original number in heap for reporting

Variant: Top k frequent elements
Hint: Using map and then heap
```

Note:
1. In such problems where original data set is a data structure and heap priority is derived. Use custom class for heap elements
2. This helps in maintaining the original input while dealing with derived priority for heap purpose


#### Question
```ignorelang
Rope joining problem. There are n ropes of given length. At any point of time you can join two string. for that you pay a price equal to sum of lengths of two.
Find the total cost which is minimal to make single string out of all.

Example: arr=[2, 3, 4, 5, 6, 7, 8]
```

#### Question
```ignorelang
Sort a k sorted array

Example: 
Input: arr=[6, 5, 3, 2, 8, 10, 9], k = 3

Sol: Heap of size k + 1
```

```kotlin
fun findTopK(arr: Array<Int>, k: Int): List<Int> {
    val heap: PriorityQueue<Int> = PriorityQueue { a:Int, b:Int -> b - a } // Condition for max heap
    for (item in arr) {
        if (heap.size >= k) {
            val top = heap.peek()
            if (item < top) {
                heap.poll()
                heap.add(item)
            }
        } else {
            heap.add(item)
        }
    }

    val result = mutableListOf<Int>()
    while (!heap.isEmpty()) {
        result.add(heap.poll())
    }
    return result
}
```


## B. Merge K sorted

#### Question
```ignorelang
You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
Merge all the linked-lists into one sorted linked-list and return it.
Example:

Input: lists = [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]
```
```kotlin
fun mergeSorted(lists: List<Node?>): Node {
    if (lists.isEmpty()) return null
    val heap: PriorityQueue<Int> = PriorityQueue { a:Node, b:Node -> b.value - a.value }
    
    val holder = Node(0)
    var iterator = holder
    for (item in lists) {
        if (item != null) {
            heap.add(item)
        }
    }
    
    while (!heap.isEmpty()) {
        iterator.next = heap.poll()
        iterator = iterator.next
        
        if (iterator.next != null) {
            heap.add(iterator.next)
        }
    }
    
    return holder.next
}
```

#### Question
```ignorelang
You are given two integer arrays nums1 and nums2 sorted in non-decreasing order and an integer k.
Define a pair (u, v) which consists of one element from the first array and one element from the second array.
Return the k pairs (u1, v1), (u2, v2), ..., (uk, vk) with the smallest sums.

Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
Output: [[1,2],[1,4],[1,6]]
```

#### Question
```ignorelang
Given an n x n matrix where each of the rows and columns is sorted in ascending order, return the kth smallest element in the matrix.
Example: 

Input: matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
Output: 13
```

## C. Two Heaps
```ignorelang
The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value, and the median is the mean of the two middle values.

For example, for arr = [2,3,4], the median is 3.
For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
Implement the MedianFinder class:

MedianFinder() initializes the MedianFinder object.
void addNum(int num) adds the integer num from the data stream to the data structure.
double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer will be accepted.
```

```kotlin
val small = PriorityQueue<Int>(Collections.reverseOrder())
val large = PriorityQueue<Int>()
var isEven = true

fun findMedian(): Double {
    return if (isEven)
        (small.peek() + large.peek()) / 2.0
    else
        small.peek()
}

fun addNum(num: Int) {
    if (isEven) {
        large.offer(num)
        small.offer(large.poll())
    } else {
        small.offer(num)
        large.offer(small.poll())
    }
    isEven = !isEven
}
```

## C. Minimum Number Pattern