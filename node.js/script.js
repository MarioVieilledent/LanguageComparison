"use strict";

var WIDTH = 2000;
var HEIGHT = 2000;

var start = new Date();
var plot = mandelbrot();
var end = new Date();
var elapsed = end.getTime() - start.getTime();

console.log("Time elapsed - Mandlebrot: " + elapsed);

function mandelbrot() {
    var MAX_ITER = 1000;

    var X_SCALE_MIN = -2.0;
    var X_SCALE_MAX = 1.0;
    var Y_SCALE_MIN = -1.5;
    var Y_SCALE_MAX = 1.5;

    var X_SLOPE = (X_SCALE_MAX - X_SCALE_MIN) / (WIDTH);
    var Y_SLOPE = (Y_SCALE_MAX - Y_SCALE_MIN) / (HEIGHT);

    console.log(X_SLOPE);
    console.log(Y_SLOPE);

    var plot = [];

    for (var py = 0; py < HEIGHT; py++) {
        plot.push([]);

        for (var px = 0; px < WIDTH; px++) {
            var x0 = X_SLOPE * px + X_SCALE_MIN;
            var y0 = Y_SLOPE * py + Y_SCALE_MIN;
            var x = 0.0;
            var y = 0.0;
            var iteration = 0;

            while (x * x + y * y <= 4.0 && iteration < MAX_ITER) {
                var x_temp = x * x - y * y + x0;
                y = 2.0 * x * y + y0;
                x = x_temp;
                iteration++;
            }

            plot[py].push(iteration);
        }
    }

    return plot;
}
