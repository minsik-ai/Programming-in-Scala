package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = oneOf(
    const(empty),
    for {
      elem <- arbitrary[A]
      genRes <- oneOf(const(empty), genHeap)
    } yield insert(elem, genRes)
  )

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("insertEmpty") = forAll { (x: A, y: A) =>
    val minXY = if (x < y) x else y
    val xHeap = insert(x, empty)
    val xyHeap = insert(y, xHeap)
    findMin(xyHeap) == minXY
  }

  property("delMin") = forAll { (x: A) =>
    isEmpty(deleteMin(insert(x, empty)))
  }

  property("checkOrdered") = forAll { (h: H) =>

    def checkOrdered(heap: H): A = {

      val delHeap = deleteMin(heap)
      val curMin = findMin(heap)

      if (isEmpty(delHeap)) {
        curMin
      } else {
        val prevMin = checkOrdered(delHeap)

        if (ord.lteq(curMin, prevMin))
          curMin
        else
          throw new RuntimeException
      }
    }

    if (isEmpty(h)) true
    else {
      try {
        checkOrdered(h)
        true
      } catch {
        case e: RuntimeException => false
      }
    }
  }

  property("mergeMin") = forAll { (m1: H, m2: H) =>
    val m1Min = if (isEmpty(m1)) 0 else findMin(m1)
    val m2Min = if (isEmpty(m2)) 0 else findMin(m2)
    val merged = meld(m1, m2)
    val mergeMin = if (isEmpty(merged)) 0 else findMin(merged)

    m1Min == mergeMin || m2Min == mergeMin
  }

  def checkEq(m1: H, m2: H): Boolean = {
    if (isEmpty(m1) && isEmpty(m2))
      true
    else if (isEmpty(m1) != isEmpty(m2))
      false
    else if (findMin(m1) == findMin(m2))
      checkEq(deleteMin(m1), deleteMin(m2))
    else
      false
  }

  property("DeleteAllAddAll") = forAll { (m: H) =>
    def cloneHeap(x: H): H = {
      if (isEmpty(x))
        empty
      else {
        val minElem = findMin(x)

        insert(minElem, cloneHeap(deleteMin(x)))
      }
    }

    val cloneM = cloneHeap(m)

    checkEq(m, cloneM)
  }

  property("deleteMinAddMin") = forAll { (m: H) =>
    if (isEmpty(m))
      true
    else {
      val minVal = findMin(m)
      val delAndAdd = insert(minVal, deleteMin(m))

      findMin(delAndAdd) == minVal
    }
  }


  property("Add5ElemsAndCheck") = forAll { (a0: A, a1: A, a2: A, a3: A, a4: A) =>
    val heap = insert(a0, insert(a1, insert(a2, insert(a3, insert(a4, empty)))))
    val sortedList = List(a0, a1, a2, a3, a4).sorted(ord)

    def checkEqToList(heap: H, sortedList: List[Int]): Boolean = {
      if (isEmpty(heap) && sortedList.isEmpty)
        true
      else if (isEmpty(heap) != sortedList.isEmpty)
        false
      else if (findMin(heap) == sortedList.head)
        checkEqToList(deleteMin(heap), sortedList.tail)
      else 
        false
    }

    checkEqToList(heap, sortedList)
  }
}
