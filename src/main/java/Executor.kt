import java.util.ArrayDeque
import kotlin.math.max
import kotlin.math.min

object Executor {

    fun minDepth(root: TreeNode?): Int {
        fun dfs(node: TreeNode?): Int {
            if (node == null) return 0
            val left = dfs(node.left)
            val right = dfs(node.right)
            return if (left == 0 || right == 0) 1 + left + right else 1 + min(left, right)
        }
        return dfs(root)
    }

    fun createLinkedList(values: List<Int>): ListNode? {
        if (values.isEmpty()) return null
        val head = ListNode(values[0])
        var current = head
        for (i in 1 until values.size) {
            current.next = ListNode(values[i])
            current = current.next!!
        }
        return head
    }

    fun printLinkedList(head: ListNode?) {
        var current = head
        while (current != null) {
            print("${current.`val`}${if (current.next != null) " -> " else "\n"}")
            current = current.next
        }
    }

    fun removeElement(nums: IntArray, target: Int): Int {
        var k = 0
        for (i in nums.indices) {
            if (nums[i] != target) {
                nums[k++] = nums[i]
            }
        }
        return k
    }

    fun plusOne(digits: IntArray): IntArray {
        for (i in digits.indices.reversed()) {
            if (digits[i] < 9) {
                digits[i]++
                return digits
            }
            digits[i] = 0
        }
        return intArrayOf(1) + digits
    }

    fun addBinary(a: String, b: String): String {
        var aPtr = a.length - 1
        var bPtr = b.length - 1
        var carry = 0
        val builder = StringBuilder()

        while (aPtr >= 0 || bPtr >= 0 || carry > 0) {
            val aVal = if (aPtr >= 0) a[aPtr--].digitToInt() else 0
            val bVal = if (bPtr >= 0) b[bPtr--].digitToInt() else 0
            val sum = aVal + bVal + carry
            builder.append(sum % 2)
            carry = sum / 2
        }
        return builder.reverse().toString()
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }

    fun deleteDuplicates(head: ListNode?): ListNode? {
        val dummy = ListNode(Int.MIN_VALUE)
        dummy.next = head
        var curr = dummy

        while (curr.next != null) {
            if (curr.`val` == curr.next?.`val`) {
                curr.next = curr.next?.next
            } else {
                curr = curr.next!!
            }
        }
        return dummy.next
    }

    data class TreeNode(var `val`: Int, var left: TreeNode? = null, var right: TreeNode? = null)

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
        var curr = root

        while (curr != null || stack.isNotEmpty()) {
            while (curr != null) {
                stack.push(curr)
                curr = curr.left
            }
            curr = stack.pop()
            result.add(curr.`val`)
            curr = curr.right
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
        fun isMirror(t1: TreeNode?, t2: TreeNode?): Boolean {
            if (t1 == null && t2 == null) return true
            if (t1 == null || t2 == null) return false
            return t1.`val` == t2.`val` &&
                    isMirror(t1.left, t2.right) &&
                    isMirror(t1.right, t2.left)
        }
        return isMirror(root?.left, root?.right)
    }

    fun buildBSTFromSortedArray(arr: IntArray): TreeNode? {
        fun build(start: Int, end: Int): TreeNode? {
            if (start > end) return null
            val mid = (start + end) / 2
            return TreeNode(arr[mid]).apply {
                left = build(start, mid - 1)
                right = build(mid + 1, end)
            }
        }
        return build(0, arr.lastIndex)
    }

    fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
        if (root == null) return false
        if (root.left == null && root.right == null) return root.`val` == targetSum
        val newTarget = targetSum - root.`val`
        return hasPathSum(root.left, newTarget) || hasPathSum(root.right, newTarget)
    }

    fun containsNearbyDuplicate(nums: IntArray, k: Int): Boolean {
        val seen = HashMap<Int, Int>()
        for (i in nums.indices) {
            val value = nums[i]
            if (value in seen && i - seen[value]!! <= k) return true
            seen[value] = i
        }
        return false
    }

    fun lengthOfLongestSubstring(s: String): Int {
        val set = mutableSetOf<Char>()
        var l = 0
        var maxLength = 0

        for (r in s.indices) {
            while (s[r] in set) {
                set.remove(s[l++])
            }
            set.add(s[r])
            maxLength = max(maxLength, r - l + 1)
        }
        return maxLength
    }

    fun findRepeatedDnaSequences(s: String): List<String> {
        val seen = hashSetOf<String>()
        val output = hashSetOf<String>()
        for (i in 0..s.length - 10) {
            val sub = s.substring(i, i + 10)
            if (!seen.add(sub)) {
                output.add(sub)
            }
        }
        return output.toList()
    }

    fun minSubArrayLen(target: Int, nums: IntArray): Int {
        var l = 0
        var sum = 0
        var minLength = Int.MAX_VALUE

        for (r in nums.indices) {
            sum += nums[r]
            while (sum >= target) {
                minLength = min(minLength, r - l + 1)
                sum -= nums[l++]
            }
        }
        return if (minLength == Int.MAX_VALUE) 0 else minLength
    }

    fun calPoints(operations: Array<String>): Int {
        val stack = ArrayDeque<Int>()
        for (op in operations) {
            when {
                op.toIntOrNull() != null -> stack.addLast(op.toInt())
                op == "C" -> stack.removeLast()
                op == "D" -> stack.lastOrNull()?.let { stack.addLast(2 * it) }
                op == "+" -> {
                    val size = stack.size
                    if (size >= 2) {
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
        val stack = ArrayDeque<Int>()
        val result = IntArray(temperatures.size)

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