class GameOfLife(N: Int, board: Utility.Board, WORKERS: Int) extends CellularAutomaton(N, board, WORKERS){
  /*
  1. Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
  2. Any live cell with more than three live neighbours dies, as if by overcrowding.
  3. Any live cell with two or three live neighbours lives, unchanged, to the next generation.
  4. Any tile with exactly three live neighbours cells will be populated with a living cell.
  */

  // RULE: B3/S23

  override def newState(i: Int, j: Int): Boolean =  {
    val (alive, _) = countAliveAndDead(i, j)

    if (board(i)(j) == Utility.DEAD){
      alive == 3 // case 4
    }
    else{ // current cell is alive
      if (alive < 2 || alive > 3) // case 1 or 2
        Utility.DEAD
      else
        board(i)(j) // case 3
    }
  }
}