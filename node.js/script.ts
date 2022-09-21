
const WIDTH: number = 2000;
const HEIGHT: number = 2000;

let start: Date = new Date();
let plot: Plot = mandelbrot();
let end: Date = new Date();
let elapsed: number = end.getTime() - start.getTime();
console.log("Time elapsed - Mandlebrot: " + elapsed);

function mandelbrot(): Plot {
    const MAX_ITER: number = 1000;

    const X_SCALE_MIN: number = -2.0;
    const X_SCALE_MAX: number = 1.0;
    const Y_SCALE_MIN: number = -1.5;
    const Y_SCALE_MAX: number = 1.5;

    const X_SLOPE: number = (X_SCALE_MAX - X_SCALE_MIN) / (WIDTH);
    const Y_SLOPE: number = (Y_SCALE_MAX - Y_SCALE_MIN) / (HEIGHT);

    console.log(X_SLOPE);
    console.log(Y_SLOPE);

    let plot: any = [];

    for (let py = 0; py < HEIGHT; py++) {
        plot.push([]);
        for (let px = 0; px < WIDTH; px++) {
            let x0: number = X_SLOPE * px + X_SCALE_MIN;
            let y0: number = Y_SLOPE * py + Y_SCALE_MIN;
            let x: number = 0.0;
            let y: number = 0.0;
            let iteration: number = 0;

            while (x * x + y * y <= 4.0 && iteration < MAX_ITER) {
                let x_temp: number = x * x - y * y + x0;
                y = 2.0 * x * y + y0;
                x = x_temp;
                iteration++;
            }

            plot[py].push(iteration);
        }
    }
    return plot;
}

type Plot = number[][];