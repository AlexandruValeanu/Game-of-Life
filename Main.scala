import io.threadcso.{PROC, proc}

object Main {
  def main(args: Array[String]): Unit = {
    //run_examples(List.apply[String]("pulsar", "spaceship", "glider", "replicator"))()

    val s = proc{CellularAutomaton.apply("glider", "LifeWithoutDeath").run()}
    s()
  }

  def run_examples(seq: Seq[String]): PROC = {
    var system = proc{}

    for (filename <- seq)
      system = system || proc{CellularAutomaton.apply(filename, "GameOfLife").run()}

    system
  }
}
