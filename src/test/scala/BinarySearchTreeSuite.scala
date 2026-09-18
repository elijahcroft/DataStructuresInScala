package datastructures

class BinarySearchTreeSuite extends munit.FunSuite:
  //        5
  //      /   \
  //     3     8
  //    / \   / \
  //   1   4 7   9
  def sample = BinarySearchTree(5, 3, 8, 1, 4, 7, 9)

  test("insert ignores duplicates"):
    val t = sample
    assert(!t.insert(4))
    assertEquals(t.size, 7)

  test("contains, min, max, height"):
    val t = sample
    assert(t.contains(7))
    assert(!t.contains(6))
    assertEquals(t.min, Some(1))
    assertEquals(t.max, Some(9))
    assertEquals(t.height, 2)

  test("empty tree"):
    val t = BinarySearchTree[Int]()
    assertEquals(t.min, None)
    assertEquals(t.height, -1)
    assertEquals(t.levelOrder, Nil)
    assert(!t.remove(1))

  test("traversals"):
    val t = sample
    assertEquals(t.inOrder, List(1, 3, 4, 5, 7, 8, 9))
    assertEquals(t.preOrder, List(5, 3, 1, 4, 8, 7, 9))
    assertEquals(t.postOrder, List(1, 4, 3, 7, 9, 8, 5))
    assertEquals(t.levelOrder, List(List(5), List(3, 8), List(1, 4, 7, 9)))

  test("remove leaf, one-child node, two-child node, and root"):
    val t = sample
    assert(t.remove(1)) // leaf
    assert(t.remove(3)) // now has one child (4)
    assert(t.remove(8)) // two children
    assert(t.remove(5)) // root
    assert(!t.remove(5))
    assertEquals(t.inOrder, List(4, 7, 9))
    assertEquals(t.size, 3)

  test("works with any Ordering"):
    val t = BinarySearchTree("pear", "apple", "fig")
    assertEquals(t.inOrder, List("apple", "fig", "pear"))
