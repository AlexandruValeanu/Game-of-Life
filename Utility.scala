object Utility {
  type Row = Array[Boolean]
  type Board = Array[Row]

  val ALIVE = true
  val DEAD = false

  private val directions = 8
  private val dx: Array[Int] = Array.apply(-1, -1, -1, 0, 0, +1, +1, +1)
  private val dy: Array[Int] = Array.apply(-1, 0, +1, -1, +1, -1, 0, +1)

  def wrap(n: Int)(a: Int): Int = {
    if (a >= n) a - n
    else if (a < 0) a + n
    else a
  }

  /*
    We will consider a variant with a finite N by N grid, treated as a toroid, i.e.
    where the top and bottom edges of the grid are treated as being adjacent, as
    are the left and right edges.
  */
  def neighbours(n: Int)(x: Int, y: Int): Seq[(Int, Int)] = {
    var list = List.empty[(Int, Int)]

    for (k <- 0 until directions){
      list = (wrap(n)(x + dx(k)), wrap(n)(y + dy(k))) :: list
    }

    list
  }

  def localRegionImage(startRow: Int, n: Int)(board: Utility.Board): Utility.Board = {
    val newBoard = Array.ofDim[Utility.Row](n)

    for (i <- 0 until n){
      newBoard(i) = Array.ofDim[Boolean](board(startRow + i).length)

      for (j <- board(startRow + i).indices)
        newBoard(i)(j) = board(startRow + i)(j)
    }

    newBoard
  }

  def updateBoard(startRow: Int, n: Int, N: Int)(localBoard: Utility.Board)(board: Utility.Board): Unit = {
    for (i <- 0 until n; j <- 0 until N)
      board(startRow + i)(j) = localBoard(i)(j)
  }
}
