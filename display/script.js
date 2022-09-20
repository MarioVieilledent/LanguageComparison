let canvas = document.getElementById('canvas');
let ctx = canvas.getContext('2d');

let w;
let h;
let maxIter = 1000;
let slope = 255 / maxIter;


fetch("mandelbrot/rust.json")
    .then(res => res.json())
    .then(data => {
        h = data.length;
        w = data[0].length;
        ctx.canvas.width = w;
        ctx.canvas.height = h;
        drawFrame(data);
    });


function drawFrame(mandelbrot) {
    for (let y = 0; y < h; y++) {
        for (let x = 0; x < w; x++) {
            drawPixel(x, y, 255 - slope * mandelbrot[y][x]);
        }
    }
}

function drawPixel(x, y, i) {
    ctx.fillStyle = `rgba(${i}, ${i}, ${i}, 255)`;
    ctx.fillRect(x, y, 1, 1);
}
