// ⚙️ Core Stack Methods

// push(element) — add an element to the top of the stack.
// pop() — remove and return the top element.
// peek() — return (but don’t remove) the top element.
// isEmpty() — true if stack has no elements.
// size() — return the number of elements.
// clear() — remove all elements.

// 🧠 Optional / Advanced
// toArray() — return all elements as an Array or List.
// contains(element) — check if an element exists.
// printStack() — show all elements top → bottom (for debugging).
// top() — alias for peek().
// bottom() — view the oldest element directly.

package datastructures

import scala.reflect.ClassTag

class Stack[T : ClassTag]{
    var data = new DynamicArray[T]

    def push(elem: T): Unit = {
        data.pushBack(elem)
    }
    def pop(): T = {
        val popped = data(data.size - 1)
        data.popBack()
        popped
    }
    def peek(): T = {
        val top = data(data.size - 1)
        top
    }
    def isEmpty(): Boolean = {
        return data.isEmpty 
    }
    def size(): Int = {
        val stackSize = data.size
        stackSize
    }
    def clear(): Unit = {
        if(this.size() == 0){
            return
        }else{
            pop()
            clear()
        }

    }


}