use std::{fs, time::Instant};

const WIDTH: usize = 2_000;
const HEIGHT: usize = 2_000;

fn main() {
    let start: Instant = Instant::now();
    let plot: Vec<Vec<usize>> = mandlebrot();
    let duration = start.elapsed();
    println!("Time elapsed - Mandlebrot: {:?}", duration.as_nanos());

    let start: Instant = Instant::now();
    save_result(&plot);
    let duration = start.elapsed();
    println!("Time elapsed - Writing file: {:?}", duration.as_nanos());
}

fn mandlebrot() -> Vec<Vec<usize>> {
    const MAX_ITER: usize = 1000;

    const X_SCALE_MIN: f64 = -2.0;
    const X_SCALE_MAX: f64 = 1.0;
    const Y_SCALE_MIN: f64 = -1.5;
    const Y_SCALE_MAX: f64 = 1.5;

    const X_SLOPE: f64 = (X_SCALE_MAX - X_SCALE_MIN) / WIDTH as f64;
    const Y_SLOPE: f64 = (Y_SCALE_MAX - Y_SCALE_MIN) / HEIGHT as f64;

    // dbg!(X_SLOPE);
    // dbg!(Y_SLOPE);

    let mut plot: Vec<Vec<usize>> = vec![vec![0; WIDTH]; HEIGHT];

    for py in 0..HEIGHT {
        for px in 0..WIDTH {
            let x0: f64 = X_SLOPE * px as f64 + X_SCALE_MIN;
            let y0: f64 = Y_SLOPE * py as f64 + Y_SCALE_MIN;
            let mut x: f64 = 0.0;
            let mut y: f64 = 0.0;
            let mut iteration: usize = 0;

            while x * x + y * y <= 4.0 && iteration < MAX_ITER {
                let x_temp: f64 = x * x - y * y + x0;
                y = 2.0 * x * y + y0;
                x = x_temp;
                iteration += 1;
            }

            plot[py][px] = iteration;
        }
    }
    plot
}

fn save_result(plot: &Vec<Vec<usize>>) {
    let mut result: String = String::from("[");
    for y in plot {
        result.push_str("[");

        result.push_str(
            &y.iter()
                .map(|&v| v.to_string())
                .collect::<Vec<String>>()
                .join(", ")[..],
        );

        result.push_str("],");
    }
    result.pop();
    result.push_str("]");
    // println!("{}", result);
    fs::write("../display/mandelbrot/rust.json", result).expect("Unable to write file");
}
