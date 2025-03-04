package com.shriyansh.dsalgo.patterns.dfs

import javax.print.attribute.standard.MediaSize.NA

data class NArryTreeNode(
    val value: Int,
    val children: List<NArryTreeNode>?
)

fun tracingAllPaths(node: NArryTreeNode, path: MutableList<Int>) {
    if (node.children.isNullOrEmpty()) {
        path.add(node.value)
        println(path)
        path.removeLast()
        return
    }

    for (child: NArryTreeNode in node.children) {
        path.add(child.value)
        tracingAllPaths(child, path)
        path.removeLast()
    }

    return
}