package datastructures

import scala.annotation.tailrec
import scala.reflect.ClassTag

/** Binary min-heap stored in a [[DynamicArray]] — a priority queue.
  *
  * For node `i`: parent is `(i - 1) / 2`, children are `2i + 1` and `2i + 2`.
  * `push` and `pop` are O(log n); `peek` is O(1).
  * Pass a reversed Ordering (e.g. `Ordering[Int].reverse`) for a max-heap.
  */
class MinHeap[T: ClassTag](using ord: Ordering[T]):
  import ord.mkOrderingOps

  private val data = DynamicArray[T]()

  def size: Int = data.size
  def isEmpty: Boolean = data.isEmpty

  def peek: Option[T] = data.get(0)

  def push(elem: T): Unit =
    data.pushBack(elem)
    siftUp(data.size - 1)

  /** Remove and return the smallest element. */
  def pop(): Option[T] =
    if isEmpty then None
    else
      val top = data(0)
      val last = data.popBack().get
      if !isEmpty then
        data(0) = last
        siftDown(0)
      Some(top)

  @tailrec private def siftUp(i: Int): Unit =
    val parent = (i - 1) / 2
    if i > 0 && data(i) < data(parent) then
      swap(i, parent)
      siftUp(parent)

  @tailrec private def siftDown(i: Int): Unit =
    val left = 2 * i + 1
    val right = left + 1
    var smallest = i
    if left < size && data(left) < data(smallest) then smallest = left
    if right < size && data(right) < data(smallest) then smallest = right
    if smallest != i then
      swap(i, smallest)
      siftDown(smallest)

  private def swap(a: Int, b: Int): Unit =
    val temp = data(a)
    data(a) = data(b)
    data(b) = temp

object MinHeap:
  def apply[T: ClassTag: Ordering](elems: T*): MinHeap[T] =
    val heap = new MinHeap[T]
    elems.foreach(heap.push)
    heap

  /** Heapsort: push everything, then pop in order. O(n log n). */
  def sort[T: ClassTag: Ordering](elems: Seq[T]): List[T] =
    val heap = apply(elems*)
    List.fill(elems.size)(heap.pop().get)
