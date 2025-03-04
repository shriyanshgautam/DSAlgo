package com.shriyansh.dsalgo.patterns

data class Interval(
    var start: Int,
    var end: Int
)

fun main() {
    val intervals = listOf(Interval(1,3), Interval(4,7), Interval(5,8), Interval(9, 10), Interval(10,13), Interval(11, 14))
    println(mergeIntervals(intervals))
}

fun mergeIntervals(intervals: List<Interval>): List<Interval> {
    // Base condition
    if (intervals.isEmpty() || intervals.size == 1) {
        return intervals
    }
    // Sort intervals by start time
    val sortedIntervals = intervals.sortedBy { it.start }

    val result = mutableListOf<Interval>()

    // Start with first interval as in memory probing interval
    var probingInterval = sortedIntervals[0]

    for (interval in sortedIntervals.subList(1, sortedIntervals.size - 1)) {
        if (interval.start < probingInterval.end) {
            // Keep extending the probing interval till overlapping intervals are coming in
            probingInterval.end = Math.max(probingInterval.end, interval.end)
        } else {
            // if non overlapping interval is found, save the probing interval
            result.add(probingInterval)
            // Refresh probing interval from the current one
            probingInterval = interval
        }
    }
    // Add Last probing interval to result
    result.add(probingInterval)
    return result
}