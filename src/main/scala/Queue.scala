package datastructures

import scala.reflect.ClassTag

/** FIFO queue backed by a circular buffer. `enqueue`/`dequeue` are amortized O(1). */
class Queue[T: ClassTag]:
  private var data = new Array[T](8)
  private var front = 0
  private var size_ = 0

  def size: Int = size_
  def isEmpty: Boolean = size_ == 0

  def enqueue(elem: T): Unit =
    if size_ == data.length then grow()
    data((front + size_) % data.length) = elem
    size_ += 1

  def dequeue(): Option[T] =
    if isEmpty then None
    else
      val elem = data(front)
      front = (front + 1) % data.length
      size_ -= 1
      Some(elem)

  def peek: Option[T] = if isEmpty then None else Some(data(front))

  def clear(): Unit =
    front = 0
    size_ = 0

  /** Elements from front to back. */
  def toList: List[T] = List.tabulate(size_)(i => data((front + i) % data.length))

  override def toString: String = toList.mkString("Queue(", ", ", ")")

  /** Double the buffer and unwrap it so the front is at index 0 again. */
  private def grow(): Unit =
    val bigger = new Array[T](data.length * 2)
    for i <- 0 until size_ do bigger(i) = data((front + i) % data.length)
    data = bigger
    front = 0
