import java.util.Stack
import kotlin.collections.ArrayDeque
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.arrayListOf
import kotlin.collections.filterNotNull
import kotlin.collections.forEachIndexed
import kotlin.collections.hashSetOf
import kotlin.collections.isEmpty
import kotlin.collections.isNotEmpty
import kotlin.collections.sliceArray
import kotlin.collections.toIntArray
import kotlin.collections.toList
import kotlin.collections.toMutableList
import kotlin.math.max
import kotlin.math.min

fun main() {
//   calPoints(operations = arrayOf("5","-2","4","C","D","9","+","+"))
//   calPoints(operations = arrayOf("5","2","C","D","+"))
    dailyTemperatures(intArrayOf(30, 40, 50, 60))

}

fun minDepth(root: TreeNode?): Int {
    fun dfs(node: TreeNode?): Int {
        if (node == null) return 0
        val left = dfs(node.left)
        val right = dfs(node.right)

        return if (left == 0 || right == 0) 1 + left + right
        else 1 + minOf(left, right)
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

// Function to print linked list
fun printLinkedList(head: ListNode?) {
    var current = head
    while (current != null) {
        print("${current.`val`}${if (current.next != null) " -> " else "\n"}")
        current = current.next
    }
}


fun removeElement(nums: IntArray, `val`: Int): Int {
    var i = 0
    var k = 0
    val n = nums.size
    while (i < n) {
        var j = i
        while (j < n && nums[j] == `val`) {
            j++
        }
        if (j == n) break
        nums[k] = nums[j]
        k++
        i = j + 1
    }
    return k
}

fun plusOne(digits: IntArray): IntArray {
    var carry = 0
    val n = digits.size - 1
    for (i in n downTo 0) {
        if (i == n) digits[i] = digits[i] + 1
        val curr = digits[i] + carry
        val digit = curr % 10
        carry = curr / 10
        digits[i] = digit
    }

    return if (carry != 0) digits.toMutableList().apply {
        add(0, carry)
    }.toIntArray() else digits
}

fun addBinary(a: String, b: String): String {
    var aPtr = a.length - 1
    var bPtr = b.length - 1
    val builder = StringBuilder()
    var carry = 0
    while (aPtr >= 0 || bPtr >= 0 || carry > 0) {
        println()
        var aVal = '0'
        var bVal = '0'
        if (aPtr >= 0) {
            aVal = a[aPtr]
            aPtr--
        }
        if (bPtr >= 0) {
            bVal = b[bPtr]
            bPtr--
        }
        println("carry = $carry")
        val sum = carry + aVal.digitToInt() + bVal.digitToInt()
        println("sum = $sum")
        carry = sum / 2
        println("carry post assign = $carry")
        builder.append(sum % 2)
    }
    return if (carry != 0) "$carry$builder".reversed()
    else builder.toString().reversed()
}


class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun deleteDuplicates(head: ListNode?): ListNode? {
    val dummy = ListNode(Int.MIN_VALUE)
    dummy.next = head
    var curr: ListNode? = dummy

    fun drop(curr: ListNode?, next: ListNode?) {
        curr?.next = next?.next
    }

    while (curr != null) {
        while (curr.`val` == curr.next?.`val`) {
            drop(curr, curr.next)
        }
        curr = curr.next
    }

    return dummy.next
}

data class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}


fun inorderTraversal(root: TreeNode?): List<Int> {
    val res = arrayListOf<Int>()
    fun dfs(node: TreeNode?) {
        if (node == null) return
        dfs(node.left)
        res.add(node.`val`)
        dfs(node.right)
    }
    dfs(root)
    return res.toList()
}

fun inorderTraversalIterative(root: TreeNode?): List<Int> {
    val res = arrayListOf<Int?>()
    var node = root
    while (node != null) {
        while (node?.left != null) {
            res.add(node.`val`)
            node = node.left
        }

        while (node?.right != null) node = node.right
    }
    return res.filterNotNull()
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
        if (left == null || right == null) return false
        if (left.`val` != right.`val`) return false
        queue.add(left.left)
        queue.add(right.right)

        queue.add(left.right)
        queue.add(right.left)
    }

    return true
}

fun isSymmetricDfs(root: TreeNode): Boolean {
    return isMirror(root.left, root.right)
}

private fun isMirror(n1: TreeNode?, n2: TreeNode?): Boolean {
    if (n1 == null && n2 == null) {
        return true
    }

    if (n1 == null || n2 == null) {
        return false
    }

    return n1.`val` == n2.`val` && isMirror(n1.left, n2.right) && isMirror(n1.right, n2.left)
}

fun buildBSTFromSortedArray(arr: IntArray): TreeNode? {
    if (arr.isEmpty()) return null
    val mid = arr.size / 2
    val root = TreeNode(arr[mid])
    root.left = buildBSTFromSortedArray(arr.sliceArray(0..mid))
    root.right = buildBSTFromSortedArray(arr.sliceArray(mid + 1..arr.size))
    return root
}

fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
    var res = false
    fun dfs(node: TreeNode?, sum: Int) {
        if (node == null) return
        val newSum = sum + node.`val`
        if (node.left == null && node.right == null && newSum == targetSum) {
            res = true
            return
        }
        dfs(node.left, newSum)
        dfs(node.right, newSum)
    }
    dfs(root, 0)
    return res
}

fun containsNearbyDuplicate(nums: IntArray, k: Int): Boolean {
    var r = 0
    var l = 0
    val n = nums.size - 1
    while (r <= n) {
        val curr = nums[r]

        //windowTooLarge l++
        r++
    }
    return false
}

fun lengthOfLongestSubstring(s: String): Int {
    var r = 0
    var l = 0
    val windowSet = hashSetOf<Char>()
    var maxL = Int.MIN_VALUE
    while (r < s.length) {
        val entering = s[r]
        println("entering : $entering")
        while (entering in windowSet) {
            println("removing ${s[l]}")
            println("set before removing $windowSet l: $l, r: $r")
            windowSet.remove(s[l])
            l++
            println("set after removing $windowSet l: $l, r: $r")
        }
        println("adding $entering")
        windowSet.add(entering)
        maxL = max(maxL, (r - l) + 1)
        r++
    }
    return maxL

}

fun findRepeatedDnaSequences(s: String): List<String> {
    var r = 0
    val seen = hashSetOf<String>()
    val res = hashSetOf<String>()
    var currWindow = ""
    while (r < s.length) {
        val curr = s[r]
        currWindow = currWindow + curr
        if (currWindow.length > 10) {
            currWindow = currWindow.substring(1)
        }
        if (currWindow.length == 10) {
            if (!seen.add(currWindow)) {
                res.add(currWindow)
            }
        }
        r++
    }
    return res.toList()
}

fun minSubArrayLen(target: Int, nums: IntArray): Int {
    var r = 0
    var l = 0
    var windowSum = 0
    var minL = Int.MAX_VALUE
    while (r < nums.size) {
        val curr = nums[r]
        windowSum = windowSum + curr
        while (windowSum >= target) {
            minL = min(minL, ((r - l) + 1))
            val toRemove = nums[l]
            windowSum -= toRemove
            l++
        }
        r++
    }
    return if (minL == Int.MIN_VALUE) 0 else minL
}


//arrayOf("5","-2","4","C","D","9","+","+")
fun calPoints(operations: Array<String>): Int {
    val stack = Stack<Int>()
    var res = 0
    for (operation in operations) {
        if (operation.toIntOrNull() != null) {
            println("pushing $operation")
            stack.push(operation.toInt())
            println("stack = ${stack.toList()}")
        } else if (operation == "C") {
            println("popped ${stack.pop()}")
            println("stack = ${stack.toList()}")
        } else if (operation == "D") {
            val value = 2 * stack.peek()
            println("pushing doubled value $value")
            stack.push(value)
            println("stack = ${stack.toList()}")
        } else if (operation == "+") {
            println("adding...")
            var sum: Int
            val num1 = stack.pop()
            val num2 = stack.peek()
            sum = num1 + num2
            stack.push(num1)
            println("sum = $sum")
            stack.push(sum)
            println("stack = ${stack.toList()}")
        }
    }
    while (stack.isNotEmpty()) {
        res += stack.pop()
    }

    println("res = $res")
    return res
}


fun dailyTemperatures(temperatures: IntArray): IntArray {
    val stack = Stack<Pair<Int, Int>>()
    val res = MutableList(temperatures.size, { 0 })
    temperatures.forEachIndexed { index, value ->
        if (stack.isNotEmpty()) {
            while (stack.isNotEmpty() && stack.peek().second < value) {
                val top = stack.pop()
                res[top.first] = index - top.first
            }
        }
        stack.push(index to value)
    }
    return res.toIntArray()
}
