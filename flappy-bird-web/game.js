const canvas = document.getElementById("gameCanvas");
const ctx = canvas.getContext("2d");

// Load images
const birdImg = new Image();
birdImg.src = "assets/bird.png";

const appleImg = new Image();
appleImg.src = "assets/apple.png";

// Sounds
const bgMusic = new Audio("assets/bg-music.wav");
const gameOverSound = new Audio("assets/game-over.wav");
bgMusic.loop = true;

// Game variables
let birdY = 250;
let velocity = 0;
const gravity = 0.5;
const jump = -8;
let score = 0;
let gameOver = false;

// Pipes
let pipes = [];
pipes.push({ x: 400, gap: 150, top: 200 });

// Start music
document.addEventListener("keydown", () => {
  if (bgMusic.paused) bgMusic.play();
}, { once: true });

// Controls
document.addEventListener("keydown", e => {
  if (e.code === "Space" && !gameOver) {
    velocity = jump;
  }
});

// Game loop
function update() {
  if (gameOver) return;

  velocity += gravity;
  birdY += velocity;

  // Pipes movement
  pipes.forEach(pipe => pipe.x -= 2);

  // Collision
  pipes.forEach(pipe => {
    if (
      50 > pipe.x &&
      50 < pipe.x + 50 &&
      (birdY < pipe.top || birdY > pipe.top + pipe.gap)
    ) {
      endGame();
    }
  });

  // Add pipes
  if (pipes[pipes.length - 1].x < 200) {
    pipes.push({
      x: 400,
      gap: 150,
      top: Math.random() * 200 + 50
    });
    score++;
  }

  // Ground collision
  if (birdY > canvas.height) {
    endGame();
  }
}

function draw() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);

  // Bird
  ctx.drawImage(birdImg, 50, birdY, 40, 40);

  // Pipes
  ctx.fillStyle = "green";
  pipes.forEach(pipe => {
    ctx.fillRect(pipe.x, 0, 50, pipe.top);
    ctx.fillRect(pipe.x, pipe.top + pipe.gap, 50, canvas.height);
  });

  // Score
  ctx.fillStyle = "black";
  ctx.font = "20px Arial";
  ctx.fillText("Score: " + score, 10, 30);
}

function endGame() {
  gameOver = true;
  bgMusic.pause();
  gameOverSound.play();
  alert("Game Over! Score: " + score);
  location.reload();
}

function loop() {
  update();
  draw();
  requestAnimationFrame(loop);
}

loop();

canvas.addEventListener("touchstart", () => {
  velocity = jump;
});

