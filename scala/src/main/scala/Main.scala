import scala.collection.mutable.ListBuffer
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets

val WIDTH: Int = 2000
val HEIGHT: Int = 2000

@main def hello: Unit =
  var start = System.nanoTime()
  val plot = mandelbrot()
  var elapsed = System.nanoTime() - start
  println("Time elapsed - Mandlebrot: " + elapsed)

  start = System.nanoTime()
  saveResults(plot)
  elapsed = System.nanoTime() - start
  println("Time elapsed - Writing file: " + elapsed)


def mandelbrot(): Array[Array[Int]] =
  val MAX_ITER: Int = 1000

  val X_SCALE_MIN: Double = -2.0
  val X_SCALE_MAX: Double = 1.0
  val Y_SCALE_MIN: Double = -1.5
  val Y_SCALE_MAX: Double = 1.5

  val X_SLOPE: Double = (X_SCALE_MAX - X_SCALE_MIN) / WIDTH.toDouble
  val Y_SLOPE: Double = (Y_SCALE_MAX - Y_SCALE_MIN) / HEIGHT.toDouble

  println(X_SLOPE)
  println(Y_SLOPE)

  var plot = Array.ofDim[Int](HEIGHT, WIDTH)

  for(py<-0 to HEIGHT - 1; px<-0 until WIDTH - 1) {
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

def saveResults(plot: Array[Array[Int]]): Unit =
  var str = "["
  for(py<-0 to HEIGHT - 1) {
    str += "["
    for(px<-0 until WIDTH - 1) {
      str += plot(py)(px) + ","
    }
    str.dropRight(1)
    str += "],"
  }
  str.dropRight(2)
  str += "]"
  Files.write(Paths.get("../display/mandelbrot/scala.json"),
  str.getBytes(StandardCharsets.UTF_8))