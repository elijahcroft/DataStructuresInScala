package datastructures

import scala.reflect.ClassTag

/** LIFO stack backed by a [[DynamicArray]]. All operations are amortized O(1). */
class Stack[T: ClassTag]:
  private val data = DynamicArray[T]()

  def push(elem: T): Unit = data.pushBack(elem)

  /** Remove and return the top element, or `None` if empty. */
  def pop(): Option[T] = data.popBack()

  /** The top element without removing it, or `None` if empty. */
  def peek: Option[T] = data.last

  def isEmpty: Boolean = data.isEmpty
  def size: Int = data.size
  def contains(elem: T): Boolean = data.contains(elem)
  def clear(): Unit = data.clear()

  /** Elements from top to bottom. */
  def toList: List[T] = data.toList.reverse

  override def toString: String = toList.mkString("Stack(", ", ", ")")
