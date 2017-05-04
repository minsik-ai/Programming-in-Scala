package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 && r == 0)
      1
    else if (c < 0 || r < 0 || c > r)
      0
    else
      pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {

    // Need to count number of '(' and ')'s.
    // this is a (isRightOrderOnce, parenthesis count) pair.
    def unmatchedParanthesis(chars: List[Char]): (Boolean, Int)= {
      if(chars.isEmpty)
        (true, 0)
      else {
        val addCount =
          if (chars.head == '(')
            1
          else if (chars.head == ')')
            -1
          else
            0

        val tailPair = unmatchedParanthesis(chars.tail)
        val returnInt = addCount + tailPair._2

        if(returnInt > 0)       //wrong order appears once. Return false for all isRightOrderOnce variable.
          (false, returnInt)
        else
          (tailPair._1, returnInt)
      }
    }

    unmatchedParanthesis(chars) == (true, 0)
  }

  /**
    * Exercise 3
    */
  def countChange(money: Int, coins: List[Int]): Int = {
    val sortedList = coins.sortWith(_ > _)

    def auxCountChange(money: Int, coins: List[Int]): Int = {
      if(money == 0)
        1
      else if(money < 0 || coins.isEmpty)
        0
      else {
        //첫번째 코인을 포함하는 경우 + 첫번째 코인을 포함하지 않는 경우
        auxCountChange(money - coins.head, coins) + auxCountChange(money, coins.tail)
      }
    }

    auxCountChange(money, sortedList)
  }
}
