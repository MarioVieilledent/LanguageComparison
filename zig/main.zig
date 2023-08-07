const std = @import("std");

var WIDTH: usize = undefined;
var HEIGHT: usize = undefined;

pub fn main() !void {

    const stdin = std.io.getStdIn().reader();
    const stdout = std.io.getStdOut().writer();

    var buf: [10]u8 = undefined;
    try stdout.print("A number please: ", .{});

    if (try stdin.readUntilDelimiterOrEof(buf[0..], '\n')) |user_input| {
        const line = std.mem.trimRight(u8, user_input[0..user_input.len - 1], "\r");
        WIDTH = try std.fmt.parseInt(usize, line, 10);
        HEIGHT = WIDTH;
    }

    var timer = std.time.Timer.start();

    if (timer) |*clock| {

        const MAX_ITER: usize = 1000;

        const X_SCALE_MIN: f64 = -2.0;
        const X_SCALE_MAX: f64 = 1.0;
        const Y_SCALE_MIN: f64 = -1.5;
        const Y_SCALE_MAX: f64 = 1.5;

        const result = mandlebrot(MAX_ITER, X_SCALE_MIN, X_SCALE_MAX, Y_SCALE_MIN, Y_SCALE_MAX);
        _ = result;

        const elapsed = clock.read();

        std.debug.print("Time elapsed: {} ns\n", .{elapsed});
    } else |_| {

    }
}

fn mandlebrot(MAX_ITER: usize, X_SCALE_MIN: f64, X_SCALE_MAX: f64, Y_SCALE_MIN: f64, Y_SCALE_MAX: f64) [5000][5000]usize {
    const widthFloat: f64 = @floatFromInt(WIDTH);
    const heightFloat: f64 = @floatFromInt(HEIGHT);

    const X_SLOPE: f64 = (X_SCALE_MAX - X_SCALE_MIN) / widthFloat;
    const Y_SLOPE: f64 = (Y_SCALE_MAX - Y_SCALE_MIN) / heightFloat;

    var plot: [5000][5000]usize = undefined;

    for (0..HEIGHT) |py| {
        for (0..WIDTH) |px| {
            const pxf: f64 = @floatFromInt(px);
            const pyf: f64 = @floatFromInt(py);
            const x0: f64 = X_SLOPE * pxf + X_SCALE_MIN;
            const y0: f64 = Y_SLOPE * pyf + Y_SCALE_MIN;
            var x: f64 = 0.0;
            var y: f64 = 0.0;
            var iteration: usize = 0;

            while (x * x + y * y <= 4.0 and iteration < MAX_ITER) {
                var x_temp: f64 = x * x - y * y + x0;
                y = 2.0 * x * y + y0;
                x = x_temp;
                iteration += 1;
            }

            plot[py][px] = iteration;
        }
    }

    return plot;
}