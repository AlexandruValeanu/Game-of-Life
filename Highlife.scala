class Highlife(N: Int, board: Utility.Board, WORKERS: Int) extends CellularAutomaton(N, board, WORKERS){
  //  A cell is born if it has 3 or 6 neighbors and survives if it has 2 or 3 neighbors

  // RULE: B36/S23

  override def newState(i: Int, j: Int): Boolean = {
    val (alive, _) = countAliveAndDead(i, j)

    if (board(i)(j) == Utility.DEAD){
      alive == 3 || alive == 6
    }
    else{ // current cell is alive
      if (alive < 2 || alive > 3)
        Utility.DEAD
      else
        board(i)(j)
    }
  }
}
