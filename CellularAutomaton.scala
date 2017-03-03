import io.threadcso.semaphore.{Barrier, BooleanSemaphore}
import io.threadcso.{PROC, proc, sleepms}

import scala.io.Source

abstract class CellularAutomaton(val N: Int, val board: Utility.Board, val WORKERS: Int) {
  def newState(i: Int, j: Int): Boolean

  private [this] val display = new Display(N, board)
  private [this] val mutex = new BooleanSemaphore(available = true)

  private [this] val barrier = new Barrier(WORKERS)

  def worker(startRow: Int, n: Int): PROC = proc{
    val local = Utility.localRegionImage(startRow, n)(board)

    while (true){
      mutex.down()
        display.draw()
        sleepms(200)
      mutex.up()

      for (i <- 0 until n; j <- 0 until N){
        local(i)(j) = newState(startRow + i, j)
      }

      barrier.sync()
      Utility.updateBoard(startRow, n, N)(local)(board)
      barrier.sync()
    }
  }

  def run(): Unit = {
    val slice = N / WORKERS
    var system: PROC = worker(slice * (WORKERS - 1), N - slice * (WORKERS - 1))
    0.until(WORKERS - 1).foreach(i => system = system || worker(slice * i, slice))
    system()
  }

  def countAliveAndDead(x: Int, y: Int): (Int, Int) = {
    var (alive, dead) = (0, 0)

    for ((a, b) <- Utility.neighbours(N)(x, y))
      if (board(a)(b) == Utility.ALIVE)
        alive += 1
      else
        dead += 1

    (alive, dead)
  }
}

object CellularAutomaton {
  def apply(filename: String, typeOfCA: String): CellularAutomaton = {
    try {
      val lines = Source.fromFile(filename).getLines().toList
      val N = lines.head.toInt
      val board = Array.ofDim[Boolean](N, N)

      for ((line, i) <- lines.tail.zipWithIndex) {
        val xs = line.split(" ")

        for ((x, j) <- xs.zipWithIndex)
          board(i)(j) = x.toInt > 0
      }

      typeOfCA match {
        case "GameOfLife" => new GameOfLife(N, board, WORKERS = 4)
        case "Highlife" => new Highlife(N, board, WORKERS = 4)
        case "LifeWithoutDeath" => new LifeWithoutDeath(N, board, WORKERS = 4)
        case _ => throw new IllegalArgumentException("game unknown")
      }
    }
    catch {
      case _: Exception => null
    }
  }
}
