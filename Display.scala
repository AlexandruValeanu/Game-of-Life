import java.awt._

class Display(N: Int, a: Utility.Board) extends Frame {
  // Define some constants
  private val blockSize = 30
  private val padding   = 5
  private val gridSize  = blockSize+2*padding
  
  // Set up the display
  private val pane = new ScrollPane() 
  pane.setSize(N * gridSize, N * gridSize)
  private val board = new Board()
  pane.add(board)
  this.add(pane, "Center")
  this.pack()
  this.setVisible(true)
  this.setTitle("Life")
  this.setSize((N + 1) * gridSize, (N + 1) * gridSize)
  
  // Fill in all the squares
  def draw(): Unit = {
    for (i <- 0 until N){
        for (j<-0 until N){
            if (a(i)(j)) board.drawAt(j, i) else board.blankAt(j, i)
        }
    }
  }

  override def paint(g: Graphics): Unit = draw()

  class Board extends Component{
    // Define colours
    val backgroundColor: Color = Color.gray.brighter
    val blockColor: Color = Color.black

    // Paint the square at (x,y) in colour c
    private def paintAt(x: Int, y: Int, c: Color) = {    
      val g = getGraphics
      g.setColor(c)
      g.fillRect(x * gridSize + padding, y * gridSize + padding, blockSize, blockSize)
    }

    // Draw a piece
    def drawAt(x: Int, y: Int): Unit = paintAt(x, y, blockColor)

    // Draw a blank square
    def blankAt(x: Int, y: Int): Unit = paintAt(x, y, backgroundColor)
  }

}


