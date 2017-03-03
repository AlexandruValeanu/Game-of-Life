
class LifeWithoutDeath(N: Int, board: Utility.Board, WORKERS: Int) extends CellularAutomaton(N, board, WORKERS){
  // RULE: B3/S012345678

  override def newState(i: Int, j: Int): Boolean = {
    val (alive, _) = countAliveAndDead(i, j)

    if (board(i)(j) == Utility.DEAD){
      alive == 3
    }
    else{ // current cell is alive
      board(i)(j)
    }
  }
}
