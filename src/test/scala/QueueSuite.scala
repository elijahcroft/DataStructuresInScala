package datastructures

class QueueSuite extends munit.FunSuite:
  test("enqueue/dequeue is FIFO"):
    val q = Queue[Int]()
    for i <- 1 to 50 do q.enqueue(i)
    for i <- 1 to 50 do assertEquals(q.dequeue(), Some(i))
    assertEquals(q.dequeue(), None)

  test("wraps around and grows while wrapped"):
    val q = Queue[Int]()
    for i <- 1 to 6 do q.enqueue(i)
    for _ <- 1 to 4 do q.dequeue()
    for i <- 7 to 20 do q.enqueue(i) // wraps, then grows
    assertEquals(q.toList, (5 to 20).toList)

  test("peek and clear"):
    val q = Queue[String]()
    assertEquals(q.peek, None)
    q.enqueue("a"); q.enqueue("b")
    assertEquals(q.peek, Some("a"))
    assertEquals(q.toString, "Queue(a, b)")
    q.clear()
    assert(q.isEmpty)
