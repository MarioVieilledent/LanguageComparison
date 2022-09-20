# Language Comparison with Mandelbrot set

![GitHub repo file count](https://img.shields.io/github/directory-file-count/MarioVieilledent/LanguageComparison)
![GitHub language count](https://img.shields.io/github/languages/count/MarioVieilledent/LanguageComparison)

- Comparison of languages at computing Mandelbrot set.
- Comparison of languages at writing JSON file.
- Serves a web page to visualize the results.

![Mandelbrot preview](preview.png)

## Need help brada

I would like to write the program in as much programming language as possible.
I know you know python, also you used pascal, and maybe others that I don't.

Your job would be:
- Create a folder with the language name
- Code a program that runs `Mandelbrot algorithm` described below

## Mandelbrot algorithm

This is the pseudo-code in which is based the programs.

Source: [Wikipedia](https://en.wikipedia.org/wiki/Plotting_algorithms_for_the_Mandelbrot_set)

```
for each pixel (Px, Py) on the screen do
    x0 := scaled x coordinate of pixel (scaled to lie in the Mandelbrot X scale (-2.00, 0.47))
    y0 := scaled y coordinate of pixel (scaled to lie in the Mandelbrot Y scale (-1.12, 1.12))
    x := 0.0
    y := 0.0
    iteration := 0
    max_iteration := 1000
    while (x*x + y*y ≤ 2*2 AND iteration < max_iteration) do
        xtemp := x*x - y*y + x0
        y := 2*x*y + y0
        x := xtemp
        iteration := iteration + 1
 
    color := palette[iteration]
    plot(Px, Py, color)
```

## Results

Each language measure the time in nanoseconds to:
1) compute the Mandelbrot set
2) write the `JSON` data

Then I write down performances in a result file.

Incoming.

| Language | min time | max time | average time | size of compiled executable | notes |
| --- | --- | --- | --- | --- | --- |
| Rust | ... | ... | ... | ... | Long time to dev and debug |
| Go | ... | ... | ... | ... | ... |
| Javascript on chromium | ... | ... | ... | - | ... |
| Javascript on node.js | ... | ... | ... | - | ... |
| Python | ... | ... | ... | - | Need help brada |
| Java | ... | ... | ... | ... | ... |
| C | ... | ... | ... | ... | ... |
| C++ | ... | ... | ... | ... | ... |
| C# | ... | ... | ... | ... | ... |

## How does it work

For all language implementation, there's a folder with source code.

For all of them, the program:
1) Run the mandelbrot algorithm measuring the time elapsed in nanoseconds.
2) Write a `json` file in `./display/mandelbrot` and measure the time elapsed to write file.

A `go` server serves static files in `./display`.
The web page is extremely compact, the `js` script fetches the `json` data from `./display/mandelbrot` to display it, so we can ensure the algorithm are doing their job correctly.

## Code

### Rust

I used vectors with rust, which is probably a bad idea to get best performances from the language.

```rust
fn mandlebrot() -> Vec<Vec<usize>> {
    const WIDTH: usize = 2_000;
    const HEIGHT: usize = 2_000;
    const MAX_ITER: usize = 1000;

    const X_SCALE_MIN: f64 = -2.0;
    const X_SCALE_MAX: f64 = 1.0;
    const Y_SCALE_MIN: f64 = -1.5;
    const Y_SCALE_MAX: f64 = 1.5;

    const X_SLOPE: f64 = (X_SCALE_MAX - X_SCALE_MIN) / WIDTH as f64;
    const Y_SLOPE: f64 = (Y_SCALE_MAX - Y_SCALE_MIN) / HEIGHT as f64;

    dbg!(X_SLOPE);
    dbg!(Y_SLOPE);

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
```