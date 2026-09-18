package datastructures

import scala.util.Random

class MinHeapSuite extends munit.FunSuite:
  test("pops in ascending order"):
    val heap = MinHeap(5, 1, 8, 3, 9, 2, 2)
    assertEquals(heap.peek, Some(1))
    val popped = List.fill(heap.size)(heap.pop().get)
    assertEquals(popped, List(1, 2, 2, 3, 5, 8, 9))
    assertEquals(heap.pop(), None)

  test("heapsort matches the standard library on random input"):
    val xs = List.fill(500)(Random.nextInt(1000))
    assertEquals(MinHeap.sort(xs), xs.sorted)

  test("reversed ordering makes a max-heap"):
    val heap = MinHeap(3, 7, 1)(using summon, Ordering[Int].reverse)
    assertEquals(heap.pop(), Some(7))
    assertEquals(heap.pop(), Some(3))

  test("interleaved push and pop"):
    val heap = MinHeap[Int]()
    heap.push(4); heap.push(2)
    assertEquals(heap.pop(), Some(2))
    heap.push(1); heap.push(3)
    assertEquals(List.fill(3)(heap.pop().get), List(1, 3, 4))
    assert(heap.isEmpty)
