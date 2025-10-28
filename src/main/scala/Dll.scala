// insertFront / insertBack — add a new node at the front or back.
// deleteFront / deleteBack — remove from the front or back.
// insertAfter / insertBefore — insert relative to a specific node.
// deleteNode(value) — find and remove a node with a specific value.
// traverseForward — go from head → tail.
// traverseBackward — go from tail → head.
// find(value) — return the node containing a value.
// isEmpty — check if the list has no nodes.
// clear — remove all nodes.
// size / length — count the number of nodes.
// printList — show all values in order (for debugging).

// Optional / Advanced:
// reverse() — flip all the links.
// contains(value) — true/false if element exists.
// toArray / toVector — convert to another structure.


class DLL[T]{
    protected var size_ = 0

    class Node(var value_ : T, var prev_ : Node | Null, var next_ : Node | Null )


    var head_ : Node | Null = null
    var tail_ : Node | Null = null
    def empty: Boolean = head_ == null || tail_ == null
    
    def findVal(start: Node, target: T): Option[T] = {
        if(start == null ) return None
        else if(start.value_ == target) return Some(start.value_)
        else findVal(start.next_, target)
        
    }
    def find(start: Node, target: T): Option[Node] = {
        var temp = start
        if(temp == null ) return None
        else if(temp.value_ == target) return Some(temp)
        else find(temp.next_, target )
        
    }
    

    def insertFront(value: T): Unit = {
        if(this.empty){
            head_ = new Node(value, null, null)
        }else{

        
        val temp = head_
        head_ = new Node(value, null, temp)
        temp.prev_ = head_
        }
        size_ += 1
    }
    def insertBack(value: T): Unit = {
        if (head_ == null){
            head_ = new Node(value, null, null)
            tail_ = head_
        }else if(tail_ == null ){
            tail_ = head_
            val temp = new Node(value, tail_, null)
            tail_.next_ = temp
            tail_ = temp
        }else{
            
            val temp = new Node(value, tail_, null)
            tail_.next_ = temp
            tail_ = temp
        }
        size_ += 1
    }
    def deleteFront(): Unit ={
        if(!this.empty){
        val temp = head_.next_
        head_.next_ = null
        head_ = temp
        head_.prev_ = null
        size_ -= 1
        }
        
    }
    def deleteBack(): Unit = {
        
        if(this.empty) return 

        val temp = tail_.prev_
        tail_.prev_ = null
        tail_ = temp
        tail_.next_ = null
        size_ -= 1
    }
    
    def deleteNodeVal(value: T): Boolean = {
        if(this.empty) return false
        find(head_, value ) match {
            case Some(node) =>
                val prevNode = node.prev_
                val nextNode = node.next_
                node.prev_ = null
                node.next_ = null
                prevNode.next_ = nextNode
                nextNode.prev_ = prevNode
            case None => return false                
        }
        
        size_ -= 1
        true
    }
    def deleteNode(index: Int): Boolean ={
        if(index > size_) return false
        var i = 0
        var temp = head_
        while(i != index){
            temp = temp.next_
            i += 1
        }
        val prevNode = temp.prev_
        val nextNode = temp.next_
        temp.next_ = null; temp.prev_ = null
        prevNode.next_ = nextNode
        nextNode.prev_ = prevNode
        size_ -= 1
        return true
        
    }
    def insertAfter(node: Node, value: T): Unit ={
        var temp = node.next_
        
        val newNode = new Node(value, node, temp)
        node.next_ = newNode
        if(temp != null){
        temp.prev_ = newNode
        }
        else{
            tail_ = newNode
        }
        size_ += 1
    }

    

    def getSize: Int = size_

    def clear():Unit = {
        clearHelper(head_)
        head_ = null
        tail_ = null
        size_ = 0
    }
    private def clearHelper(node: Node): Unit ={
        if(node == null)return
        else{
            val next = node.next_
            node.prev_ = null
            node.next_ = null
            clearHelper(next)
        }
    }
    def traverseForward(node: Node, f: Node => Unit): Unit = {
        if(node == null)return
        else{
            f(node)
            traverseForward(node.next_, f)
        }
    }
    def traverseBackward(node: Node, f: Node => Unit): Unit = {
        if(node == null)return
        else{
            f(node)
            traverseBackward(node.prev_, f)
        }
    }
    def printList(): Unit = {
        traverseForward(head_, n => print(s"${n.value_} , "))
    }
    def printListNext(): Unit = {
        traverseForward(head_, n => println(s"${n.value_} ${n.next_}" ) )
    }

    def reverse(): Unit = {
        traverseBackward(tail_, n =>{
            val cur = n.prev_ 
            n.prev_ = n.next_
            n.next_ = cur
        }
            )

        val temp = tail_
        tail_ = head_
        head_ = temp
    }
}
