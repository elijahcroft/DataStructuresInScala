package datastructures

import scala.annotation.tailrec

/** Unbalanced binary search tree (a set: duplicates are ignored).
  *
  * insert / contains / remove are O(h), where h is the tree height:
  * O(log n) on random input, O(n) worst case on sorted input.
  */
class BinarySearchTree[T](using ord: Ordering[T]):
  import ord.mkOrderingOps

  private class Node(var value: T, var left: Node | Null = null, var right: Node | Null = null)

  private var root: Node | Null = null
  private var size_ = 0

  def size: Int = size_
  def isEmpty: Boolean = size_ == 0

  /** Insert `value`. Returns false if it was already present. */
  def insert(value: T): Boolean =
    @tailrec def loop(n: Node): Boolean =
      if value < n.value then
        if n.left == null then { n.left = Node(value); true } else loop(n.left.nn)
      else if value > n.value then
        if n.right == null then { n.right = Node(value); true } else loop(n.right.nn)
      else false

    val added = if root == null then { root = Node(value); true } else loop(root.nn)
    if added then size_ += 1
    added

  def contains(value: T): Boolean =
    @tailrec def loop(n: Node | Null): Boolean =
      if n == null then false
      else if value < n.value then loop(n.left)
      else if value > n.value then loop(n.right)
      else true
    loop(root)

  /** Remove `value`. Returns false if it wasn't present. */
  def remove(value: T): Boolean =
    val before = size_
    root = removeFrom(root, value)
    size_ < before

  def min: Option[T] = Option(root).map(n => leftmost(n).value)

  def max: Option[T] =
    @tailrec def loop(n: Node): T = if n.right == null then n.value else loop(n.right.nn)
    Option(root).map(loop)

  /** Number of edges on the longest root-to-leaf path (-1 for an empty tree). */
  def height: Int =
    def loop(n: Node | Null): Int = if n == null then -1 else 1 + math.max(loop(n.left), loop(n.right))
    loop(root)

  def inOrder: List[T] =
    def loop(n: Node | Null, acc: List[T]): List[T] =
      if n == null then acc else loop(n.left, n.value :: loop(n.right, acc))
    loop(root, Nil)

  def preOrder: List[T] =
    def loop(n: Node | Null): List[T] =
      if n == null then Nil else n.value :: loop(n.left) ++ loop(n.right)
    loop(root)

  def postOrder: List[T] =
    def loop(n: Node | Null): List[T] =
      if n == null then Nil else loop(n.left) ++ loop(n.right) :+ n.value
    loop(root)

  /** Breadth-first, one list per depth. */
  def levelOrder: List[List[T]] =
    @tailrec def loop(level: List[Node], acc: List[List[T]]): List[List[T]] =
      if level.isEmpty then acc.reverse
      else
        val next = level.flatMap(n => List(n.left, n.right).collect { case c: Node => c })
        loop(next, level.map(_.value) :: acc)
    loop(Option(root).toList.map(_.nn), Nil)

  override def toString: String = inOrder.mkString("BST(", ", ", ")")

  private def removeFrom(n: Node | Null, value: T): Node | Null =
    if n == null then null
    else if value < n.value then { n.left = removeFrom(n.left, value); n }
    else if value > n.value then { n.right = removeFrom(n.right, value); n }
    else if n.left == null then { size_ -= 1; n.right }
    else if n.right == null then { size_ -= 1; n.left }
    else
      // Two children: replace with the in-order successor, then remove that.
      val successor = leftmost(n.right.nn).value
      n.value = successor
      n.right = removeFrom(n.right, successor)
      n

  @tailrec private def leftmost(n: Node): Node =
    if n.left == null then n else leftmost(n.left.nn)

object BinarySearchTree:
  def apply[T: Ordering](elems: T*): BinarySearchTree[T] =
    val tree = new BinarySearchTree[T]
    elems.foreach(tree.insert)
    tree
