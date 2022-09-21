
const WIDTH = 2000;
const HEIGHT = 2000;

let start = new Date();
let plot = mandelbrot();
let elapsed = new Date() - start;
console.log("Time elapsed - Mandlebrot: " + elapsed);

function mandelbrot() {
    const MAX_ITER = 1000;

    const X_SCALE_MIN = -2.0;
    const X_SCALE_MAX = 1.0;
    const Y_SCALE_MIN = -1.5;
    const Y_SCALE_MAX = 1.5;

    const X_SLOPE = (X_SCALE_MAX - X_SCALE_MIN) / (WIDTH);
    const Y_SLOPE = (Y_SCALE_MAX - Y_SCALE_MIN) / (HEIGHT);

    console.log(X_SLOPE);
    console.log(Y_SLOPE);

    let plot = [];

    for (let py = 0; py < HEIGHT; py++) {
        plot.push([]);
        for (let px = 0; px < WIDTH; px++) {
            let x0 = X_SLOPE * px + X_SCALE_MIN;
            let y0 = Y_SLOPE * py + Y_SCALE_MIN;
            let x = 0.0;
            let y = 0.0;
            let iteration = 0;

            while (x * x + y * y <= 4.0 && iteration < MAX_ITER) {
                let x_temp = x * x - y * y + x0;
                y = 2.0 * x * y + y0;
                x = x_temp;
                iteration++;
            }

            plot[py].push(iteration);
        }
    }
    return plot;
}