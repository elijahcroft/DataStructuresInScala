package datastructures

import scala.annotation.tailrec

/** Doubly linked list with O(1) insert/delete at both ends and next to a known node. */
class DoublyLinkedList[T]:
  class Node private[DoublyLinkedList] (
      val value: T,
      private[DoublyLinkedList] var prev: Node | Null,
      private[DoublyLinkedList] var next: Node | Null
  )

  private var head: Node | Null = null
  private var tail: Node | Null = null
  private var size_ = 0

  def size: Int = size_
  def isEmpty: Boolean = size_ == 0

  def headOption: Option[T] = Option(head).map(_.value)
  def lastOption: Option[T] = Option(tail).map(_.value)

  def insertFront(value: T): Node =
    val node = Node(value, null, head)
    if head == null then tail = node else head.prev = node
    head = node
    size_ += 1
    node

  def insertBack(value: T): Node =
    val node = Node(value, tail, null)
    if tail == null then head = node else tail.next = node
    tail = node
    size_ += 1
    node

  def insertAfter(node: Node, value: T): Node =
    if node eq tail then insertBack(value)
    else
      val newNode = Node(value, node, node.next)
      node.next.nn.prev = newNode
      node.next = newNode
      size_ += 1
      newNode

  def insertBefore(node: Node, value: T): Node =
    if node eq head then insertFront(value)
    else insertAfter(node.prev.nn, value)

  def deleteFront(): Option[T] = Option(head).map(unlink)
  def deleteBack(): Option[T] = Option(tail).map(unlink)

  /** Remove the first node holding `value`. Returns whether one was found. */
  def delete(value: T): Boolean = find(value).map(unlink).isDefined

  /** Remove the node at position `index`. */
  def deleteAt(index: Int): Option[T] =
    if index < 0 || index >= size_ then None
    else
      @tailrec def walk(n: Node, i: Int): Node = if i == 0 then n else walk(n.next.nn, i - 1)
      Some(unlink(walk(head.nn, index)))

  def find(value: T): Option[Node] =
    @tailrec def loop(n: Node | Null): Option[Node] =
      if n == null then None
      else if n.value == value then Some(n)
      else loop(n.next)
    loop(head)

  def contains(value: T): Boolean = find(value).isDefined

  def clear(): Unit =
    head = null
    tail = null
    size_ = 0

  /** Reverse in place by swapping every node's prev/next pointers. */
  def reverse(): Unit =
    @tailrec def loop(n: Node | Null): Unit =
      if n != null then
        val next = n.next
        n.next = n.prev
        n.prev = next
        loop(next)
    loop(head)
    val oldHead = head
    head = tail
    tail = oldHead

  def foreach(f: T => Unit): Unit =
    @tailrec def loop(n: Node | Null): Unit =
      if n != null then
        f(n.value)
        loop(n.next)
    loop(head)

  def foreachReverse(f: T => Unit): Unit =
    @tailrec def loop(n: Node | Null): Unit =
      if n != null then
        f(n.value)
        loop(n.prev)
    loop(tail)

  def toList: List[T] =
    var acc = List.empty[T]
    foreachReverse(v => acc = v :: acc)
    acc

  override def toString: String = toList.mkString("[", " <-> ", "]")

  private def unlink(node: Node): T =
    if node.prev == null then head = node.next else node.prev.nn.next = node.next
    if node.next == null then tail = node.prev else node.next.nn.prev = node.prev
    node.prev = null
    node.next = null
    size_ -= 1
    node.value

object DoublyLinkedList:
  def apply[T](elems: T*): DoublyLinkedList[T] =
    val list = new DoublyLinkedList[T]
    elems.foreach(list.insertBack)
    list
