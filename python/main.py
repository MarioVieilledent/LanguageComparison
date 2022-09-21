import time

WIDTH = 2000
HEIGHT = 2000


def main():
    start = time.time()
    plot = mandelbrot()
    elapsed = time.time() - start
    print('Time elapsed - Mandlebrot:')
    print(elapsed)


def mandelbrot():
    MAX_ITER = 1000

    X_SCALE_MIN = -2.0
    X_SCALE_MAX = 1.0
    Y_SCALE_MIN = -1.5
    Y_SCALE_MAX = 1.5

    X_SLOPE = (X_SCALE_MAX - X_SCALE_MIN) / WIDTH
    Y_SLOPE = (Y_SCALE_MAX - Y_SCALE_MIN) / HEIGHT

    # print(X_SLOPE)
    # print(Y_SLOPE)

    plot = [[0 for x in range(WIDTH)] for y in range(HEIGHT)]

    for py in range(HEIGHT):
        for px in range(WIDTH):
            x0 = X_SLOPE*px + X_SCALE_MIN
            y0 = Y_SLOPE*py + Y_SCALE_MIN
            x = 0.0
            y = 0.0
            iteration = 0

            while x*x+y*y <= 4.0 and iteration < MAX_ITER:
                x_temp = x*x - y*y + x0
                y = 2.0*x*y + y0
                x = x_temp
                iteration += 1

            plot[py][px] = iteration

    return plot


main()
