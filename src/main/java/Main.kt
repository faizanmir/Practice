import java.util.ArrayDeque
import kotlin.math.max
import kotlin.math.min

object Executor {

    // Definition for a binary tree node
    data class TreeNode(var `val`: Int, var left: TreeNode? = null, var right: TreeNode? = null)

    // Definition for a singly-linked list node
    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }

    fun minDepth(root: TreeNode?): Int {
        fun dfs(node: TreeNode?): Int {
            if (node == null) return 0

            val leftDepth = dfs(node.left)
            val rightDepth = dfs(node.right)

            return if (leftDepth == 0 || rightDepth == 0) {
                1 + leftDepth + rightDepth
            } else {
                1 + min(leftDepth, rightDepth)
            }
        }

        return dfs(root)
    }

    fun createLinkedList(values: List<Int>): ListNode? {
        if (values.isEmpty()) return null

        val head = ListNode(values[0])
        var current = head

        for (i in 1 until values.size) {
            val newNode = ListNode(values[i])
            current.next = newNode
            current = newNode
        }

        return head
    }

    fun printLinkedList(head: ListNode?) {
        var current = head
        while (current != null) {
            print(current.`val`)
            if (current.next != null) {
                print(" -> ")
            } else {
                println()
            }
            current = current.next
        }
    }

    fun removeElement(nums: IntArray, target: Int): Int {
        var index = 0

        for (i in nums.indices) {
            if (nums[i] != target) {
                nums[index] = nums[i]
                index++
            }
        }

        return index
    }

    fun plusOne(digits: IntArray): IntArray {
        for (i in digits.size - 1 downTo 0) {
            if (digits[i] < 9) {
                digits[i]++
                return digits
            }

            digits[i] = 0
        }

        return intArrayOf(1) + digits
    }

    fun addBinary(a: String, b: String): String {
        var i = a.length - 1
        var j = b.length - 1
        var carry = 0
        val result = StringBuilder()

        while (i >= 0 || j >= 0 || carry > 0) {
            val bitA = if (i >= 0) a[i--].digitToInt() else 0
            val bitB = if (j >= 0) b[j--].digitToInt() else 0

            val sum = bitA + bitB + carry
            result.append(sum % 2)
            carry = sum / 2
        }

        return result.reverse().toString()
    }

    fun deleteDuplicates(head: ListNode?): ListNode? {
        val dummy = ListNode(Int.MIN_VALUE)
        dummy.next = head
        var current = dummy

        while (current.next != null) {
            if (current.`val` == current.next?.`val`) {
                current.next = current.next?.next
            } else {
                current = current.next!!
            }
        }

        return dummy.next
    }

    fun inorderTraversal(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()

        fun dfs(node: TreeNode?) {
            if (node == null) return
            dfs(node.left)
            result.add(node.`val`)
            dfs(node.right)
        }

        dfs(root)
        return result
    }

    fun inorderTraversalIterative(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        val stack = ArrayDeque<TreeNode>()
        var current = root

        while (current != null || stack.isNotEmpty()) {
            while (current != null) {
                stack.addLast(current)
                current = current.left
            }

            current = stack.removeLast()
            result.add(current.`val`)
            current = current.right
        }

        return result
    }

    fun isSymmetric(root: TreeNode?): Boolean {
        if (root == null) return true

        val queue = ArrayDeque<TreeNode?>()
        queue.add(root.left)
        queue.add(root.right)

        while (queue.isNotEmpty()) {
            val left = queue.removeFirst()
            val right = queue.removeFirst()

            if (left == null && right == null) continue
            if (left == null || right == null || left.`val` != right.`val`) return false

            queue.add(left.left)
            queue.add(right.right)
            queue.add(left.right)
            queue.add(right.left)
        }

        return true
    }

    fun isSymmetricDfs(root: TreeNode?): Boolean {
        fun isMirror(a: TreeNode?, b: TreeNode?): Boolean {
            if (a == null && b == null) return true
            if (a == null || b == null) return false
            return a.`val` == b.`val` && isMirror(a.left, b.right) && isMirror(a.right, b.left)
        }

        return isMirror(root?.left, root?.right)
    }

    fun buildBSTFromSortedArray(arr: IntArray): TreeNode? {
        fun build(start: Int, end: Int): TreeNode? {
            if (start > end) return null

            val mid = (start + end) / 2
            val node = TreeNode(arr[mid])

            node.left = build(start, mid - 1)
            node.right = build(mid + 1, end)

            return node
        }

        return build(0, arr.lastIndex)
    }

    fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
        if (root == null) return false
        if (root.left == null && root.right == null) {
            return root.`val` == targetSum
        }

        val remaining = targetSum - root.`val`
        return hasPathSum(root.left, remaining) || hasPathSum(root.right, remaining)
    }

    fun containsNearbyDuplicate(nums: IntArray, k: Int): Boolean {
        val seenMap = mutableMapOf<Int, Int>()

        for (i in nums.indices) {
            val num = nums[i]
            if (num in seenMap && i - seenMap[num]!! <= k) {
                return true
            }
            seenMap[num] = i
        }

        return false
    }

    fun lengthOfLongestSubstring(s: String): Int {
        val charSet = mutableSetOf<Char>()
        var left = 0
        var maxLen = 0

        for (right in s.indices) {
            while (charSet.contains(s[right])) {
                charSet.remove(s[left])
                left++
            }

            charSet.add(s[right])
            maxLen = max(maxLen, right - left + 1)
        }

        return maxLen
    }

    fun findRepeatedDnaSequences(s: String): List<String> {
        val seen = mutableSetOf<String>()
        val repeated = mutableSetOf<String>()

        for (i in 0..s.length - 10) {
            val sub = s.substring(i, i + 10)

            if (!seen.add(sub)) {
                repeated.add(sub)
            }
        }

        return repeated.toList()
    }

    fun minSubArrayLen(target: Int, nums: IntArray): Int {
        var left = 0
        var currentSum = 0
        var minLength = Int.MAX_VALUE

        for (right in nums.indices) {
            currentSum += nums[right]

            while (currentSum >= target) {
                minLength = min(minLength, right - left + 1)
                currentSum -= nums[left]
                left++
            }
        }

        return if (minLength == Int.MAX_VALUE) 0 else minLength
    }

    fun calPoints(operations: Array<String>): Int {
        val stack = ArrayDeque<Int>()

        for (op in operations) {
            when {
                op.toIntOrNull() != null -> {
                    stack.addLast(op.toInt())
                }
                op == "C" -> {
                    if (stack.isNotEmpty()) stack.removeLast()
                }
                op == "D" -> {
                    if (stack.isNotEmpty()) stack.addLast(stack.last() * 2)
                }
                op == "+" -> {
                    if (stack.size >= 2) {
                        val last = stack.removeLast()
                        val secondLast = stack.last()
                        stack.addLast(last)
                        stack.addLast(last + secondLast)
                    }
                }
            }
        }

        return stack.sum()
    }

    fun dailyTemperatures(temperatures: IntArray): IntArray {
        val result = IntArray(temperatures.size)
        val stack = ArrayDeque<Int>()

        for (i in temperatures.indices) {
            while (stack.isNotEmpty() && temperatures[i] > temperatures[stack.last()]) {
                val prevIndex = stack.removeLast()
                result[prevIndex] = i - prevIndex
            }

            stack.addLast(i)
        }

        return result
    }
}