import scala.annotation.tailrec

// fields  
// constructor  
// add  
// get  
// set  
// remove  
// clear  
// size  
// isEmpty  
// resize  
// toString  
import scala.reflect.ClassTag
class myVector[T: ClassTag](capacity: Int){
    protected val minCapacity: Int = 8
    protected var size_ : Int = 0
    protected var capacity_ : Int = if(capacity > minCapacity) capacity else minCapacity
    protected var data_ : Array[T] = new Array[T](capacity_)



    def apply(x: Int): T = data_(x)

    def isEmpty: Boolean = size_ == 0
    def get(i: Int): Option[T] = {
        if (i < size_ )return Some(data_(i))
        else{
            return None
        }
    }

    def set(i: Int, elem: T): Unit = {
        if (i < size_){
             data_(i) = elem
        }else{
            println("Out of bounds")
        }
    }
    // if it takes no prameters ommit the parenthesis
    def resize() :Unit = {
        

        capacity_ = 
            if(size_ >= capacity_ / 2){
              capacity_ * 2  
            } 
            else if(size_ < capacity_ /4) {
                math.max(capacity_ / 2, minCapacity)
                }
            else {capacity_}
        val temp = new Array[T](capacity_)
        for(i <- 0 until size_){
            temp(i) = data_(i)
        }
        data_ = temp
    }
    def resize(factor: Int): Int = {
        capacity_ = 
            if(size_ >= capacity_ / 2) capacity_ * factor
            else if(size_ < capacity_ / factor) if(capacity_ /factor > minCapacity) capacity_ /factor else minCapacity
            else capacity_
        val temp = new Array[T](capacity_)
        Array.copy(data_, 0, temp, 0, size_)
        data_ = temp
        capacity_
    }
    def pushBack(elem: T): Unit = {
        if(size_ >= capacity_){
            this.resize()
        }
        
        data_(size_) = elem
        size_ += 1
        
    }
    def pushBack(elems: Iterable[T]):Unit = {
        
        for(e <- elems){
            this.pushBack(e)
        }
    }
    def popBack(): Option[T] = {
        if(!this.isEmpty){
            size_ -= 1
            return Some(data_(size_))
        }else{
            return None;
        }
        
    }
    def getSize: Int = size_
    def getCapacity: Int = capacity_

    def printVector: Unit = println((0 until size_).map(data_(_)).mkString("[", ", ", "]")) 
          


    def clear(): Unit = {
        if (size_ == 0 ){

        }
        else{
            this.popBack()
            clear()
        }
    }
    def removeIndex(pos: Int):Unit = {
        if(pos < 0 || pos >= size_) return
        moveLeft(pos)
        size_ -=1
    }
    def removeVal(elem: T): Unit = {
        val index = this.find(0, elem)
        moveLeft(index)

        size_ -= 1
    }
    @tailrec private def moveLeft(i: Int): Unit ={
        if(i < size_ -1){
            data_(i) = data_(i+1)
            moveLeft(i+1)
        }
    }

    @tailrec private def find(i: Int, target: T): Int = {
        if(i >= size_) -1
        else if(data_(i) == target) i
        else find(i + 1, target)
    }

    def forEach(f: T => Unit): Unit = {
        
        for( i <- 0 until size_){
            f(data_(i))
        }
    }

    def sort()(implicit ord: Ordering[T]): Unit = {
    val qs = new Quicksort[T]
    qs.sort(data_, 0, size_ - 1)
}
    
    
    


}


class Quicksort[T:Ordering]{
    def swap(arr: Array[T], x : Int, y: Int): Unit = {
        val temp = arr(x)
        arr(x) = arr(y)
        arr(y) = temp
    }
    def partition(arr: Array[T], low: Int, high: Int)(implicit ord: Ordering[T]): Int = {
        import ord._
        val pivot = arr(high) 
        var i = low - 1

        for(j <- low until high){
            if(arr(j) < pivot){
                //swap arr i and j
                i += 1
                swap(arr, i, j)

            }
        }

        swap(arr, i + 1, high)
        return i + 1
    }
    def sort(arr: Array[T], low: Int, high: Int)(implicit ord: Ordering[T]): Unit = {
        if(low < high){
            val pi = partition(arr, low, high)

            sort(arr, low, pi-1)
            sort(arr, pi+1, high)
    }
            
    }
}