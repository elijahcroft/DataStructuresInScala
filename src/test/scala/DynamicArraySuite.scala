package datastructures

class DynamicArraySuite extends munit.FunSuite:
  test("pushBack grows past initial capacity"):
    val arr = DynamicArray[Int]()
    for i <- 0 until 100 do arr.pushBack(i)
    assertEquals(arr.size, 100)
    assertEquals(arr(99), 99)
    assert(arr.capacity >= 100)

  test("popBack returns elements in reverse and shrinks"):
    val arr = DynamicArray(1 to 100*)
    for i <- 100 to 1 by -1 do assertEquals(arr.popBack(), Some(i))
    assertEquals(arr.popBack(), None)
    assertEquals(arr.capacity, DynamicArray.MinCapacity)

  test("get and apply bounds"):
    val arr = DynamicArray(1, 2, 3)
    assertEquals(arr.get(3), None)
    assertEquals(arr.get(-1), None)
    intercept[IndexOutOfBoundsException](arr(3))

  test("update"):
    val arr = DynamicArray(1, 2, 3)
    arr(1) = 20
    assertEquals(arr.toList, List(1, 20, 3))

  test("removeAt and remove"):
    val arr = DynamicArray("a", "b", "c", "d")
    assertEquals(arr.removeAt(1), "b")
    assert(arr.remove("d"))
    assert(!arr.remove("zzz"))
    assertEquals(arr.toList, List("a", "c"))

  test("sort"):
    val arr = DynamicArray(5, 3, 9, 1, 1, 7)
    arr.sort()
    assertEquals(arr.toList, List(1, 1, 3, 5, 7, 9))

  test("clear and toString"):
    val arr = DynamicArray(1, 2)
    assertEquals(arr.toString, "[1, 2]")
    arr.clear()
    assert(arr.isEmpty)
    assertEquals(arr.toString, "[]")
