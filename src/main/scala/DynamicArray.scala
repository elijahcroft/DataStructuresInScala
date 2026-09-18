package datastructures

import scala.annotation.tailrec
import scala.reflect.ClassTag

/** A growable array (like C++ `std::vector` / Java `ArrayList`).
  *
  * Doubles its capacity when full and halves it when it drops below a quarter
  * full, so `pushBack` / `popBack` are amortized O(1).
  */
class DynamicArray[T: ClassTag](initialCapacity: Int = DynamicArray.MinCapacity):
  private var size_ = 0
  private var data_ = new Array[T](math.max(initialCapacity, DynamicArray.MinCapacity))

  def size: Int = size_
  def capacity: Int = data_.length
  def isEmpty: Boolean = size_ == 0

  /** Element at `i`; throws if out of bounds. */
  def apply(i: Int): T =
    checkIndex(i)
    data_(i)

  /** Element at `i`, or `None` if out of bounds. */
  def get(i: Int): Option[T] =
    if i >= 0 && i < size_ then Some(data_(i)) else None

  /** Replace the element at `i` (enables `arr(i) = x`). */
  def update(i: Int, elem: T): Unit =
    checkIndex(i)
    data_(i) = elem

  def pushBack(elem: T): Unit =
    if size_ == capacity then resize(capacity * 2)
    data_(size_) = elem
    size_ += 1

  def pushAll(elems: IterableOnce[T]): Unit =
    elems.iterator.foreach(pushBack)

  def popBack(): Option[T] =
    if isEmpty then None
    else
      size_ -= 1
      val elem = data_(size_)
      shrinkIfSparse()
      Some(elem)

  def last: Option[T] = get(size_ - 1)

  /** Remove the element at `i`, shifting later elements left. */
  def removeAt(i: Int): T =
    checkIndex(i)
    val elem = data_(i)
    shiftLeft(i)
    size_ -= 1
    shrinkIfSparse()
    elem

  /** Remove the first occurrence of `elem`. Returns whether it was found. */
  def remove(elem: T): Boolean =
    val i = indexOf(elem)
    if i >= 0 then removeAt(i)
    i >= 0

  def indexOf(target: T): Int =
    @tailrec def loop(i: Int): Int =
      if i >= size_ then -1
      else if data_(i) == target then i
      else loop(i + 1)
    loop(0)

  def contains(elem: T): Boolean = indexOf(elem) >= 0

  def clear(): Unit =
    size_ = 0
    data_ = new Array[T](DynamicArray.MinCapacity)

  def foreach(f: T => Unit): Unit =
    for i <- 0 until size_ do f(data_(i))

  /** In-place quicksort. */
  def sort()(using Ordering[T]): Unit =
    Quicksort.sort(data_, 0, size_ - 1)

  def toList: List[T] = List.tabulate(size_)(data_(_))

  override def toString: String = toList.mkString("[", ", ", "]")

  private def checkIndex(i: Int): Unit =
    if i < 0 || i >= size_ then
      throw IndexOutOfBoundsException(s"index $i out of bounds for size $size_")

  @tailrec private def shiftLeft(i: Int): Unit =
    if i < size_ - 1 then
      data_(i) = data_(i + 1)
      shiftLeft(i + 1)

  private def shrinkIfSparse(): Unit =
    if size_ < capacity / 4 && capacity > DynamicArray.MinCapacity then
      resize(math.max(capacity / 2, DynamicArray.MinCapacity))

  private def resize(newCapacity: Int): Unit =
    val temp = new Array[T](newCapacity)
    Array.copy(data_, 0, temp, 0, size_)
    data_ = temp

object DynamicArray:
  val MinCapacity = 8

  def apply[T: ClassTag](elems: T*): DynamicArray[T] =
    val arr = new DynamicArray[T](elems.size)
    arr.pushAll(elems)
    arr

/** Lomuto-partition quicksort on a slice of an array. */
object Quicksort:
  def sort[T](arr: Array[T], low: Int, high: Int)(using ord: Ordering[T]): Unit =
    if low < high then
      val p = partition(arr, low, high)
      sort(arr, low, p - 1)
      sort(arr, p + 1, high)

  private def partition[T](arr: Array[T], low: Int, high: Int)(using ord: Ordering[T]): Int =
    import ord.mkOrderingOps
    val pivot = arr(high)
    var i = low - 1
    for j <- low until high do
      if arr(j) < pivot then
        i += 1
        swap(arr, i, j)
    swap(arr, i + 1, high)
    i + 1

  private def swap[T](arr: Array[T], x: Int, y: Int): Unit =
    val temp = arr(x)
    arr(x) = arr(y)
    arr(y) = temp
