package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is correctly implemented") {
    assert(contains(x => true, 100))
    assert(!contains(x => false, 100))
    assert(contains(x => x > 0, 200))
    assert(contains(x => x <= 0, 0))
    assert(contains(x => x <= 0, -1))
    assert(!contains(x => x <= 0, 1))
    assert(contains(x => 7 <= x && x <=7 , 7))
    assert(!contains(x => 7 <= x && x <=7 , 8))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect is correctly implemented") {
    new TestSets {
      val sA = union(s1, s2);
      val sB = union(s2, s3);
      assert(contains(intersect(sA, sB), 2))
      assert(!contains(intersect(sA, sB), 1))
      assert(contains(intersect(x => x>10, x=> x<30), 20))
      assert(!contains(intersect(x => x>10, x=> x<30), 30))
    }
  }

  test("diff is correctly implemented") {
    new TestSets {
      val sA = union(union(s1, s2), s3)
      assert(contains(diff(sA, s3), 2))
      assert(contains(diff(sA, s3), 1))
      assert(!contains(diff(sA, s3), 0))
      assert(contains(diff(x => x>10, x => x>15), 11))
      assert(!contains(diff(x => x>10, x => x>15), 18))
    }
  }

  test("filter is correctly implemented") {
    new TestSets {
      val sA = union(s1, s2);
      val sB = union(s2, s3);
      assert(contains(filter(sA, sB), 2))
      assert(!contains(filter(sA, sB), 1))
      assert(contains(filter(x => x>10, x=> x<30), 20))
      assert(!contains(filter(x => x>10, x=> x<30), 30))
    }
  }

  trait LargeTestSets {
    val biggerThanZero: Set = x => 0 < x
    val smallerThanZero: Set = x => 0 > x
    val a1: Set = x => 0 <= x && x < 10
    val a2: Set = x => 0 <= x && x < 20
    val a3: Set = x => x < 0 && x > -10
  }

  test("forall is correctly implemented") {
    new LargeTestSets {
      assert(forall(a1, a2))
      assert(!forall(a2, a1))
      assert(!forall(a3, a1))
    }
  }

  test("exists is correctly implemented") {
    new LargeTestSets {
      assert(exists(a1, a2))
      assert(exists(a2, a1))
      assert(!exists(a3, a2))
      assert(!exists(a3, a1))
    }
  }

  test("map is correctly implmented") {
    new LargeTestSets {
      assert(contains(map(a1, x => x+1), 1))
      assert(contains(map(a1, x => x+1), 10))
      assert(!contains(map(a1, x => x+1), 0))
      assert(!contains(map(a1, x => x+1), 11))
      assert(contains(map(a1, x => 2*x), 0))
      assert(contains(map(a1, x => 2*x), 18))
      assert(!contains(map(a1, x => 2*x), -1))
      assert(!contains(map(a1, x => 2*x), 20))
    }
  }
}
