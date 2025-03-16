package com.shriyansh.dsalgo.patterns

import java.util.SortedSet

class Node (
    val value: Int
)

class Graph {

}

fun trigger(graph: Map<Node, List<Node>>) {
    val indegree = mutableMapOf<Node, Int>()
    for (entry in graph.entries) {
        val toNodes = entry.value
        for (node in toNodes) {
            var count = indegree.getOrPut(node) {0}
            count++
            indegree[node] = count
        }
    }
}