import kotlin.math.max
import kotlin.math.min
import java.util.Stack

fun main() {
    println(dailyTemperatures(intArrayOf(30, 40, 50, 60)).toList())
}

data class TreeNode(var `val`: Int, var left: TreeNode? = null, var right: TreeNode? = null)
class ListNode(var `val`: Int) { var next: ListNode? = null }

fun minDepth(root: TreeNode?): Int = when (root) {
    null -> 0
    else -> {
        val left = minDepth(root.left)
        val right = minDepth(root.right)
        if (left == 0 || right == 0) 1 + left + right else 1 + min(left, right)
    }
}

fun createLinkedList(values: List<Int>): ListNode? =
    values.foldRight(null as ListNode?) { value, acc -> ListNode(value).also { it.next = acc } }

fun printLinkedList(head: ListNode?) {
    var current = head
    while (current != null) {
        print("${current.`val`}${if (current.next != null) " -> " else "\n"}")
        current = current.next
    }
}

fun removeElement(nums: IntArray, value: Int): Int {
    var k = 0
    for (num in nums) {
        if (num != value) nums[k++] = num
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
    val sb = StringBuilder()
    var carry = 0
    var i = a.lastIndex
    var j = b.lastIndex
    while (i >= 0 || j >= 0 || carry > 0) {
        val sum = (a.getOrNull(i)?.digitToInt() ?: 0) +
                (b.getOrNull(j)?.digitToInt() ?: 0) + carry
        sb.append(sum % 2)
        carry = sum / 2
        i--; j--
    }
    return sb.reverse().toString()
}

fun deleteDuplicates(head: ListNode?): ListNode? {
    val dummy = ListNode(0).apply { next = head }
    var prev = dummy
    var curr = head
    while (curr != null) {
        while (curr?.next != null && curr.`val` == curr.next?.`val`) {
            curr = curr.next
        }
        if (prev.next != curr) {
            prev.next = curr?.next
        } else {
            prev = prev.next!!
        }
        curr = curr?.next
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

fun isSymmetric(root: TreeNode?): Boolean {
    fun isMirror(left: TreeNode?, right: TreeNode?): Boolean {
        if (left == null || right == null) return left == right
        return left.`val` == right.`val` &&
                isMirror(left.left, right.right) &&
                isMirror(left.right, right.left)
    }
    return root == null || isMirror(root.left, root.right)
}

fun buildBSTFromSortedArray(arr: IntArray): TreeNode? {
    if (arr.isEmpty()) return null
    val mid = arr.size / 2
    return TreeNode(arr[mid]).apply {
        left = buildBSTFromSortedArray(arr.sliceArray(0 until mid))
        right = buildBSTFromSortedArray(arr.sliceArray(mid + 1 until arr.size))
    }
}

fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
    if (root == null) return false
    if (root.left == null && root.right == null) return targetSum == root.`val`
    return hasPathSum(root.left, targetSum - root.`val`) || hasPathSum(root.right, targetSum - root.`val`)
}

fun lengthOfLongestSubstring(s: String): Int {
    val set = HashSet<Char>()
    var l = 0
    var maxLen = 0
    for (r in s.indices) {
        while (!set.add(s[r])) set.remove(s[l++])
        maxLen = max(maxLen, r - l + 1)
    }
    return maxLen
}

fun findRepeatedDnaSequences(s: String): List<String> {
    val seen = HashSet<String>()
    val repeated = HashSet<String>()
    for (i in 0..s.length - 10) {
        val substr = s.substring(i, i + 10)
        if (!seen.add(substr)) repeated.add(substr)
    }
    return repeated.toList()
}

fun minSubArrayLen(target: Int, nums: IntArray): Int {
    var sum = 0
    var l = 0
    var minLen = Int.MAX_VALUE
    for (r in nums.indices) {
        sum += nums[r]
        while (sum >= target) {
            minLen = min(minLen, r - l + 1)
            sum -= nums[l++]
        }
    }
    return if (minLen == Int.MAX_VALUE) 0 else minLen
}

fun calPoints(ops: Array<String>): Int {
    val stack = Stack<Int>()
    for (op in ops) {
        when (op) {
            "C" -> if (stack.isNotEmpty()) stack.pop()
            "D" -> stack.push(2 * stack.peek())
            "+" -> stack.push(stack.takeLast(2).sum())
            else -> stack.push(op.toInt())
        }
    }
    return stack.sum()
}

fun dailyTemperatures(temperatures: IntArray): IntArray {
    val res = IntArray(temperatures.size)
    val stack = Stack<Int>()
    for (i in temperatures.indices) {
        while (stack.isNotEmpty() && temperatures[i] > temperatures[stack.peek()]) {
            val prevIndex = stack.pop()
            res[prevIndex] = i - prevIndex
        }
        stack.push(i)
    }
    return res
}