package datastructures

/** Hash map using separate chaining: each bucket is an immutable list of entries.
  *
  * Doubles the bucket count when the load factor passes 0.75, so
  * put/get/remove are O(1) on average.
  */
class HashMap[K, V]:
  private var buckets = Array.fill[List[(K, V)]](16)(Nil)
  private var size_ = 0

  def size: Int = size_
  def isEmpty: Boolean = size_ == 0

  /** Insert or overwrite. Returns the previous value, if any. */
  def put(key: K, value: V): Option[V] =
    val i = indexFor(key)
    val old = buckets(i).collectFirst { case (`key`, v) => v }
    buckets(i) = (key, value) :: buckets(i).filterNot(_._1 == key)
    if old.isEmpty then
      size_ += 1
      if size_ > buckets.length * 3 / 4 then rehash()
    old

  def get(key: K): Option[V] =
    buckets(indexFor(key)).collectFirst { case (`key`, v) => v }

  def getOrElse(key: K, default: => V): V = get(key).getOrElse(default)

  def contains(key: K): Boolean = get(key).isDefined

  /** Remove `key`. Returns the removed value, if any. */
  def remove(key: K): Option[V] =
    val i = indexFor(key)
    val old = buckets(i).collectFirst { case (`key`, v) => v }
    if old.isDefined then
      buckets(i) = buckets(i).filterNot(_._1 == key)
      size_ -= 1
    old

  def keys: List[K] = entries.map(_._1)
  def values: List[V] = entries.map(_._2)
  def entries: List[(K, V)] = buckets.toList.flatten

  override def toString: String =
    entries.map((k, v) => s"$k -> $v").mkString("HashMap(", ", ", ")")

  private def indexFor(key: K): Int = Math.floorMod(key.##, buckets.length)

  private def rehash(): Unit =
    val old = entries
    buckets = Array.fill(buckets.length * 2)(Nil)
    for (k, v) <- old do
      val i = indexFor(k)
      buckets(i) = (k, v) :: buckets(i)
