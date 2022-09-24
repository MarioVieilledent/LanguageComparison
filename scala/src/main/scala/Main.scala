import scala.collection.mutable.ListBuffer

val WIDTH: Int = 2000
val HEIGHT: Int = 2000

@main def hello: Unit =
  val start = System.nanoTime()
  val plot = mandelbrot()
  val elapsed = System.nanoTime() - start
  println("Time elapsed - Mandlebrot: " + elapsed)

def mandelbrot(): Unit =
  val MAX_ITER: Int = 1000

  val X_SCALE_MIN: Double = -2.0
  val X_SCALE_MAX: Double = 1.0
  val Y_SCALE_MIN: Double = -1.5
  val Y_SCALE_MAX: Double = 1.5

  val X_SLOPE: Double = (X_SCALE_MAX - X_SCALE_MIN) / WIDTH.toDouble
  val Y_SLOPE: Double = (Y_SCALE_MAX - Y_SCALE_MIN) / HEIGHT.toDouble

  var plot = Array.ofDim[Int](HEIGHT, WIDTH)

  for ((subTab, py) <- plot.view.zipWithIndex) {
    for (px <- subTab) {
      val x0: Double = X_SLOPE * px.toDouble + X_SCALE_MIN
      val y0: Double = Y_SLOPE * py.toDouble + Y_SCALE_MIN
      var x: Double = 0.0
      var y: Double = 0.0
      var iteration: Int = 0

      while (x * x + y * y <= 4.0 && iteration < MAX_ITER) {
        val x_temp: Double = x * x - y * y + x0
        y = 2.0 * x * y + y0
        x = x_temp
        iteration += 1
      }

      plot(py)(px) = iteration
    }

    plot
  }