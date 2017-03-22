object LOOP {

  class LOOPER(command: => Unit) {

    def UNTIL(condition: => Boolean): Unit= {
      if (!condition) {
        REPEAT(command) UNTIL (condition)
      }
    }
  }

  def REPEAT(command: => Unit): LOOPER = {
    command
    new LOOPER(command)
  }

  var i = 0

  REPEAT{
    println(i)
    i = i+1
  } UNTIL (i==10)
}