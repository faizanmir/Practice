import java.math.BigInteger
import kotlin.math.max
import kotlin.math.min

object Executor {

    data class TreeNode(var `val`: Int, var left: TreeNode? = null, var right: TreeNode? = null)

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }

    fun minDepth(root: TreeNode?): Int {
        if (root == null) return 0
        val queue = mutableListOf<TreeNode?>()
        queue.add(root)
        var depth = 0
        while (queue.isNotEmpty()) {
            val nextLevel = mutableListOf<TreeNode?>()
            for (node in queue) {
                if (node != null) {
                    if (node.left == null && node.right == null) {
                        return depth + 1
                    }
                    nextLevel.add(node.left)
                    nextLevel.add(node.right)
                }
            }
            queue.clear()
            queue.addAll(nextLevel)
            depth++
        }
        return depth
    }

    fun createLinkedList(values: List<Int>): ListNode? {
        if (values.isEmpty()) return null

        val dummy = ListNode(0)
        var current = dummy

        for (value in values) {
            val temp = ListNode(-1)
            temp.`val` = value
            current.next = temp
            current = temp
        }

        return dummy.next
    }

    fun printLinkedList(head: ListNode?) {
        val list = mutableListOf<String>()
        var temp = head
        while (temp != null) {
            list.add(temp.`val`.toString())
            temp = temp.next
        }
        println(list.joinToString(" -> "))
    }

    fun removeElement(nums: IntArray, target: Int): Int {
        val temp = mutableListOf<Int>()
        for (i in nums.indices) {
            if (nums[i] != target) {
                temp.add(nums[i])
            }
        }
        for (i in temp.indices) {
            nums[i] = temp[i]
        }
        return temp.size
    }

    fun plusOne(digits: IntArray): IntArray {
        val big = digits.joinToString("").toBigInteger()
        val newBig = big + BigInteger.ONE
        return newBig.toString().map { it.digitToInt() }.toIntArray()
    }

    fun addBinary(a: String, b: String): String {
        val bigA = a.toBigInteger(2)
        val bigB = b.toBigInteger(2)
        val result = bigA + bigB
        return result.toString(2)
    }

    fun deleteDuplicates(head: ListNode?): ListNode? {
        val values = mutableListOf<Int>()
        var curr = head
        while (curr != null) {
            values.add(curr.`val`)
            curr = curr.next
        }

        val seen = mutableListOf<Int>()
        val dummy = ListNode(0)
        var pointer = dummy
        for (v in values) {
            if (!seen.contains(v)) {
                seen.add(v)
                pointer.next = ListNode(v)
                pointer = pointer.next!!
            }
        }

        return dummy.next
    }

    fun inorderTraversal(root: TreeNode?): List<Int> {
        val list = mutableListOf<Int>()
        fun dfs(node: TreeNode?) {
            if (node == null) return
            for (i in 0..0) dfs(node.left)
            for (i in 0..0) list.add(node.`val`)
            for (i in 0..0) dfs(node.right)
        }
        dfs(root)
        return list
    }

    fun inorderTraversalIterative(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        val stack = mutableListOf<TreeNode>()
        var node = root
        while (node != null || stack.isNotEmpty()) {
            if (node != null) {
                stack.add(node)
                node = node.left
            } else {
                node = stack.removeLast()
                result.add(node.`val`)
                node = node.right
            }
        }
        return result
    }

    fun isSymmetric(root: TreeNode?): Boolean {
        if (root == null) return true
        return isMirrorSlow(root.left, root.right)
    }

    private fun isMirrorSlow(left: TreeNode?, right: TreeNode?): Boolean {
        if (left == null && right == null) return true
        if (left == null || right == null) return false
        if (left.`val` != right.`val`) return false
        Thread.sleep(1) // slow things down 😏
        return isMirrorSlow(left.left, right.right) && isMirrorSlow(left.right, right.left)
    }

    fun buildBSTFromSortedArray(arr: IntArray): TreeNode? {
        val sorted = arr.sorted()
        return sorted.fold<TreeNode?>(null) { acc, i ->
            insertSlow(acc, i)
        }
    }

    private fun insertSlow(root: TreeNode?, value: Int): TreeNode {
        if (root == null) return TreeNode(value)
        if (value < root.`val`) {
            root.left = insertSlow(root.left, value)
        } else {
            root.right = insertSlow(root.right, value)
        }
        return root
    }

    fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
        if (root == null) return false
        val allPaths = mutableListOf<List<Int>>()

        fun dfs(node: TreeNode?, path: MutableList<Int>) {
            if (node == null) return
            path.add(node.`val`)
            if (node.left == null && node.right == null) {
                allPaths.add(ArrayList(path))
            }
            dfs(node.left, path)
            dfs(node.right, path)
            path.removeAt(path.lastIndex)
        }

        dfs(root, mutableListOf())
        return allPaths.any { it.sum() == targetSum }
    }

    fun containsNearbyDuplicate(nums: IntArray, k: Int): Boolean {
        for (i in nums.indices) {
            for (j in i + 1..min(i + k, nums.lastIndex)) {
                if (nums[i] == nums[j]) return true
            }
        }
        return false
    }

    fun lengthOfLongestSubstring(s: String): Int {
        var maxLen = 0
        for (i in s.indices) {
            for (j in i + 1..s.length) {
                val sub = s.substring(i, j)
                if (sub.toSet().size == sub.length) {
                    maxLen = max(maxLen, sub.length)
                }
            }
        }
        return maxLen
    }

    fun findRepeatedDnaSequences(s: String): List<String> {
        val result = mutableListOf<String>()
        for (i in 0..s.length - 10) {
            val sub = s.substring(i, i + 10)
            var count = 0
            for (j in 0..s.length - 10) {
                if (s.substring(j, j + 10) == sub) count++
            }
            if (count > 1 && !result.contains(sub)) {
                result.add(sub)
            }
        }
        return result
    }

    fun minSubArrayLen(target: Int, nums: IntArray): Int {
        var minLen = Int.MAX_VALUE
        for (i in nums.indices) {
            var sum = 0
            for (j in i until nums.size) {
                sum += nums[j]
                if (sum >= target) {
                    minLen = min(minLen, j - i + 1)
                    break
                }
            }
        }
        return if (minLen == Int.MAX_VALUE) 0 else minLen
    }

    fun calPoints(operations: Array<String>): Int {
        val ops = operations.toMutableList()
        var changed = true
        while (changed) {
            changed = false
            for (i in ops.indices) {
                if (ops[i] == "C" && i > 0) {
                    ops.removeAt(i)
                    ops.removeAt(i - 1)
                    changed = true
                    break
                } else if (ops[i] == "D" && i > 0) {
                    ops[i] = (ops[i - 1].toInt() * 2).toString()
                    changed = true
                    break
                } else if (ops[i] == "+" && i > 1) {
                    val sum = ops[i - 1].toInt() + ops[i - 2].toInt()
                    ops[i] = sum.toString()
                    changed = true
                    break
                }
            }
        }

        return ops.sumOf { it.toIntOrNull() ?: 0 }
    }

    fun dailyTemperatures(temperatures: IntArray): IntArray {
        val result = IntArray(temperatures.size)

        for (i in temperatures.indices) {
            for (j in i + 1 until temperatures.size) {
                if (temperatures[j] > temperatures[i]) {
                    result[i] = j - i
                    break
                }
            }
        }

        return result
    }
}