import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom
import org.scalajs.dom.html.Canvas

@JSExportTopLevel("Main")
object Main {
  def main(args: Array[String]): Unit = {
    val canvas = dom.document.getElementById("canvas").asInstanceOf[Canvas]
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    var w: Int = _
    var h: Int = _
    val maxIter: Int = 1000

    // Linear function scaling 0 to 1000 to 0 to 255 (for pixel color)
    val slope: Double = 255.0 / maxIter

    // Quadratic function curving 0 to 1000 to 0 to 255 (for pixel color)
    // y = ax²+bx+c with c = 0
    // f(50) = 150
    // val a: Double = -0.00284
    // val b: Double = 3.142
    // f(50) = 200
    val a: Double = -0.00284
    val b: Double = 3.142

    dom.fetch("mandelbrot/go.json")
      .toFuture
      .flatMap(_.json().toFuture)
      .map { data =>
        h = data.asInstanceOf[js.Array[js.Array[Double]]].length
        w = data.asInstanceOf[js.Array[js.Array[Double]]](0).length
        ctx.canvas.width = w
        ctx.canvas.height = h
        drawFrame(data.asInstanceOf[js.Array[js.Array[Double]]])
      }
      .recover {
        case throwable: Throwable =>
          println(s"An error occurred: ${throwable.getMessage}")
      }
  }

  def drawFrame(mandelbrot: js.Array[js.Array[Double]]): Unit = {
    val h = mandelbrot.length
    val w = mandelbrot(0).length
    for (y <- 0 until h) {
      for (x <- 0 until w) {
        drawPixel(x, y, 255 - (a * mandelbrot(y)(x) * mandelbrot(y)(x) + b * mandelbrot(y)(x)))
      }
    }
  }

  def drawPixel(x: Int, y: Int, i: Double): Unit = {
    val ctx = dom.document.getElementById("canvas").asInstanceOf[Canvas].getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    ctx.fillStyle = s"rgba($i, $i, $i, 255)"
    ctx.fillRect(x, y, 1, 1)
  }
}