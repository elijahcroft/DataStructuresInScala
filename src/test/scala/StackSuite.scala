package datastructures

class StackSuite extends munit.FunSuite:
  test("push/pop is LIFO"):
    val s = Stack[Int]()
    for i <- 1 to 20 do s.push(i)
    assertEquals(s.size, 20)
    for i <- 20 to 1 by -1 do assertEquals(s.pop(), Some(i))
    assert(s.isEmpty)

  test("pop and peek on empty"):
    val s = Stack[String]()
    assertEquals(s.pop(), None)
    assertEquals(s.peek, None)

  test("peek does not remove"):
    val s = Stack[String]()
    s.push("a"); s.push("b")
    assertEquals(s.peek, Some("b"))
    assertEquals(s.size, 2)

  test("contains, clear, toString"):
    val s = Stack[Int]()
    s.push(1); s.push(2); s.push(3)
    assert(s.contains(2))
    assertEquals(s.toString, "Stack(3, 2, 1)")
    s.clear()
    assert(s.isEmpty)
