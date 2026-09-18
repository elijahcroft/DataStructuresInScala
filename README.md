# Data Structures in Scala

Classic data structures implemented from scratch in Scala 3, each with a munit test suite.

| Structure | File | Highlights |
|---|---|---|
| Dynamic Array | [`DynamicArray.scala`](src/main/scala/DynamicArray.scala) | Amortized O(1) push/pop, grows ×2 / shrinks ×½, in-place quicksort |
| Stack | [`Stack.scala`](src/main/scala/Stack.scala) | LIFO, built on `DynamicArray` |
| Queue | [`Queue.scala`](src/main/scala/Queue.scala) | FIFO circular buffer, unwraps when it grows |
| Doubly Linked List | [`DoublyLinkedList.scala`](src/main/scala/DoublyLinkedList.scala) | O(1) insert/delete at both ends and next to a node, in-place reverse |
| Binary Search Tree | [`BinarySearchTree.scala`](src/main/scala/BinarySearchTree.scala) | Insert/remove (incl. two-child case), in/pre/post/level-order traversals |
| Min Heap | [`MinHeap.scala`](src/main/scala/MinHeap.scala) | Array-backed priority queue, sift up/down, heapsort |
| Hash Map | [`HashMap.scala`](src/main/scala/HashMap.scala) | Separate chaining, rehashes at 0.75 load factor |

## Complexity

| Structure | Access | Search | Insert | Delete |
|---|---|---|---|---|
| Dynamic Array | O(1) | O(n) | O(1)* at end | O(1)* at end, O(n) elsewhere |
| Stack / Queue | — | O(n) | O(1)* | O(1) |
| Doubly Linked List | O(n) | O(n) | O(1) at a node | O(1) at a node |
| Binary Search Tree | — | O(h) | O(h) | O(h) |
| Min Heap | O(1) min | — | O(log n) | O(log n) min |
| Hash Map | — | O(1) avg | O(1) avg | O(1) avg |

\* amortized. `h` is tree height: O(log n) on random input, O(n) if you insert sorted input.

## Running

With [scala-cli](https://scala-cli.virtuslab.org/):

```sh
scala-cli test .
```

## Example

```scala
import datastructures.*

val tree = BinarySearchTree(5, 3, 8, 1, 4)
tree.inOrder     // List(1, 3, 4, 5, 8)
tree.levelOrder  // List(List(5), List(3, 8), List(1, 4))

val heap = MinHeap(9, 2, 7)
heap.pop()       // Some(2)

val list = DoublyLinkedList(1, 2, 3)
list.reverse()
list.toString    // [3 <-> 2 <-> 1]
```
