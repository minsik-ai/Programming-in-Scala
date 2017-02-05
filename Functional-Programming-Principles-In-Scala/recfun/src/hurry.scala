val MinutesLeftToSolve = 240 - args(1).toInt

def solveProb(i: Int): Int = {

  val timeSpent = i * (i + 1) / 2 * 5

  if (i <= args(0).toInt && timeSpent < MinutesLeftToSolve) {
    1 + solveProb(i+1)
  } else {
    0
  }
}

solveProb(0)