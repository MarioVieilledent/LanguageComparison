import time


WIDTH = 2000
HEIGHT = 2000


def main():
    start = time.time()
    plot = mandelbrot()
    elapsed = time.time() - start
    print('Time elapsed - Mandlebrot:' + elapsed)


def mandelbrot():
    const MAX_ITER uint64 = 1000

    const X_SCALE_MIN float64 = -2.0
    const X_SCALE_MAX float64 = 1.0
    const Y_SCALE_MIN float64 = -1.5
    const Y_SCALE_MAX float64 = 1.5

    const X_SLOPE float64 = (X_SCALE_MAX - X_SCALE_MIN) / float64(WIDTH)
    const Y_SLOPE float64 = (Y_SCALE_MAX - Y_SCALE_MIN) / float64(HEIGHT)

    // fmt.Println(X_SLOPE)
    // fmt.Println(Y_SLOPE)

    plot = [HEIGHT][WIDTH]uint64{}

    for py := 0; py < int(HEIGHT); py++ {
        for px := 0; px < int(WIDTH); px++ {
            var x0 float64 = X_SLOPE*float64(px) + X_SCALE_MIN
            var y0 float64 = Y_SLOPE*float64(py) + Y_SCALE_MIN
            var x float64 = 0.0
            var y float64 = 0.0
            var iteration uint64 = 0

            for x*x+y*y <= 4.0 & & iteration < MAX_ITER {
                var x_temp float64 = x*x - y*y + x0
                y = 2.0*x*y + y0
                x = x_temp
                iteration++
            }

            plot[py][px] = iteration
        }
    }
    return
