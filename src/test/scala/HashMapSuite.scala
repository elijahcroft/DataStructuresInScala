package datastructures

class HashMapSuite extends munit.FunSuite:
  test("put and get"):
    val m = HashMap[String, Int]()
    assertEquals(m.put("a", 1), None)
    assertEquals(m.put("b", 2), None)
    assertEquals(m.get("a"), Some(1))
    assertEquals(m.get("zzz"), None)
    assertEquals(m.getOrElse("zzz", 0), 0)

  test("put overwrites and returns the old value"):
    val m = HashMap[String, Int]()
    m.put("a", 1)
    assertEquals(m.put("a", 10), Some(1))
    assertEquals(m.get("a"), Some(10))
    assertEquals(m.size, 1)

  test("remove"):
    val m = HashMap[Int, String]()
    m.put(1, "one"); m.put(2, "two")
    assertEquals(m.remove(1), Some("one"))
    assertEquals(m.remove(1), None)
    assert(!m.contains(1))
    assertEquals(m.size, 1)

  test("survives many rehashes"):
    val m = HashMap[Int, Int]()
    for i <- 0 until 10_000 do m.put(i, i * i)
    assertEquals(m.size, 10_000)
    for i <- 0 until 10_000 do assertEquals(m.get(i), Some(i * i))
    assertEquals(m.keys.sorted, (0 until 10_000).toList)

  test("handles negative hash codes and colliding keys"):
    // "Aa" and "BB" have the same hashCode in Java.
    val m = HashMap[Any, String]()
    m.put("Aa", "x"); m.put("BB", "y"); m.put(-42, "neg")
    assertEquals(m.get("Aa"), Some("x"))
    assertEquals(m.get("BB"), Some("y"))
    assertEquals(m.get(-42), Some("neg"))
