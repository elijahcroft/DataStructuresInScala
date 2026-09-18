package datastructures

class DoublyLinkedListSuite extends munit.FunSuite:
  test("insertFront and insertBack"):
    val list = DoublyLinkedList[Int]()
    list.insertFront(2)
    list.insertBack(3)
    list.insertFront(1)
    assertEquals(list.toList, List(1, 2, 3))
    assertEquals(list.headOption, Some(1))
    assertEquals(list.lastOption, Some(3))

  test("delete the only node"):
    val list = DoublyLinkedList(1)
    assertEquals(list.deleteFront(), Some(1))
    assert(list.isEmpty)
    assertEquals(list.deleteBack(), None)
    list.insertBack(5)
    assertEquals(list.toList, List(5))

  test("delete by value at head, middle, and tail"):
    val list = DoublyLinkedList(1, 2, 3, 4)
    assert(list.delete(1))
    assert(list.delete(3))
    assert(list.delete(4))
    assert(!list.delete(99))
    assertEquals(list.toList, List(2))
    assertEquals(list.size, 1)

  test("deleteAt"):
    val list = DoublyLinkedList("a", "b", "c")
    assertEquals(list.deleteAt(3), None)
    assertEquals(list.deleteAt(2), Some("c"))
    assertEquals(list.deleteAt(0), Some("a"))
    assertEquals(list.toList, List("b"))

  test("insertAfter and insertBefore"):
    val list = DoublyLinkedList(1, 3)
    val three = list.find(3).get
    list.insertBefore(three, 2)
    list.insertAfter(three, 4)
    list.insertBefore(list.find(1).get, 0)
    assertEquals(list.toList, List(0, 1, 2, 3, 4))
    assertEquals(list.lastOption, Some(4))

  test("reverse"):
    val list = DoublyLinkedList(1, 2, 3, 4)
    list.reverse()
    assertEquals(list.toList, List(4, 3, 2, 1))
    var backward = List.empty[Int]
    list.foreachReverse(v => backward = backward :+ v)
    assertEquals(backward, List(1, 2, 3, 4))

  test("clear and toString"):
    val list = DoublyLinkedList(1, 2)
    assertEquals(list.toString, "[1 <-> 2]")
    list.clear()
    assert(list.isEmpty)
    assertEquals(list.toList, Nil)
