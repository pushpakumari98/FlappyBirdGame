import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.Timer;

class Apple extends Rectangle {
    public Apple(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}

class Cloud extends Rectangle {
    public Cloud(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}

class Airplane extends Rectangle {
    public Airplane(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}

public class FlappyBird implements ActionListener, KeyListener {

    public static FlappyBird flappyBird;
    public final int WIDTH = 800, HEIGHT = 600;

    public Renderer renderer;
    public Rectangle bird;
    public ArrayList<Rectangle> columns;
    public ArrayList<Apple> apples;
    public ArrayList<Cloud> clouds;
    public ArrayList<Airplane> airplanes;
    public Random random;

    public int ticks, yMotion, score;
    public boolean gameOver, started, paused;

    // Images
    public BufferedImage birdImage;
    public BufferedImage appleImage;
    public BufferedImage cloudImage;
    public BufferedImage grassImage;
    public BufferedImage airplaneImage;

    // Sounds
    public Clip gameOverSound;
    public Clip backgroundMusic;

    public FlappyBird() {
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20, this); // Use javax.swing.Timer

        renderer = new Renderer();
        jframe.add(renderer);
        jframe.setTitle("Flappy Bird");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addKeyListener(this);
        jframe.setResizable(false);
        jframe.setVisible(true);

        bird = new Rectangle(WIDTH / 2 - 20, HEIGHT / 2 - 15, 70, 60);
        columns = new ArrayList<>();
        apples = new ArrayList<>();
        clouds = new ArrayList<>();
        airplanes = new ArrayList<>();
        random = new Random();

        // Load images
        try {
            birdImage = ImageIO.read(new File("bird.png"));
            appleImage = ImageIO.read(new File("apple.png"));
            cloudImage = ImageIO.read(new File("clouds.png"));
            grassImage = ImageIO.read(new File("grasses.png")); // Load grass image
            airplaneImage = ImageIO.read(new File("airplane.png")); // Load airplane image
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load sounds
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("mixkit-arcade.wav"));
            gameOverSound = AudioSystem.getClip();
            gameOverSound.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            AudioInputStream backgroundAudioStream = AudioSystem.getAudioInputStream(new File("mixkit-game-level.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(backgroundAudioStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the background music
            backgroundMusic.start(); // Start playing the music immediately
        } catch (Exception e) {
            e.printStackTrace();
        }

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        // Add initial clouds and airplanes
        for (int i = 0; i < 3; i++) {
            addCloud();
            addAirplane();
        }

        timer.start();
    }

    public void addColumn(boolean start) {
        int space = 300;
        int width = 100;
        int height = 50 + random.nextInt(300);

        if (start) {
            columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height, width, height)); // Adjusted y to touch land
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
        } else {
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height, width, height)); // Adjusted y to touch land
            columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
        }
    }

    public void addApple() {
        int width = 50;
        int height = 50;
        int x = WIDTH + width + apples.size() * 500;
        int y = random.nextInt(HEIGHT - height - 120);

        apples.add(new Apple(x, y, width, height));
    }

    public void addCloud() {
        int width = 200;
        int height = 100;
        int x = WIDTH + width + clouds.size() * 400;
        int y = random.nextInt(200); // Clouds float in the top 200 pixels

        clouds.add(new Cloud(x, y, width, height));
    }

    public void addAirplane() {
        int width = 100;
        int height = 100;
        int x = WIDTH + width + airplanes.size() * 600;
        int y = random.nextInt(200); // Airplanes float in the top 200 pixels

        airplanes.add(new Airplane(x, y, width, height));
    }

    public void paintColumn(Graphics g, Rectangle column) {
        g.setColor(Color.blue.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    public void paintApple(Graphics g, Apple apple) {
        g.drawImage(appleImage, apple.x, apple.y, apple.width, apple.height, null);
    }

    public void paintCloud(Graphics g, Cloud cloud) {
        g.drawImage(cloudImage, cloud.x, cloud.y, cloud.width, cloud.height, null);
    }

    public void paintAirplane(Graphics g, Airplane airplane) {
        g.drawImage(airplaneImage, airplane.x, airplane.y, airplane.width, airplane.height, null);
    }

    public void paintGrass(Graphics g) {
        int grassWidth = grassImage.getWidth();
        int grassHeight = grassImage.getHeight();
        int y = HEIGHT - grassHeight;

        for (int x = 0; x < WIDTH; x += grassWidth) {
            g.drawImage(grassImage, x, y, null);
        }
    }

    public void jump() {
        if (gameOver) {
            bird = new Rectangle(WIDTH / 2 - 20, HEIGHT / 2 - 15, 70, 60);
            columns.clear();
            apples.clear();
            clouds.clear();
            airplanes.clear();
            yMotion = 0;
            score = 0;

            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);

            // Add initial clouds and airplanes again
            for (int i = 0; i < 3; i++) {
                addCloud();
                addAirplane();
            }

            gameOver = false;
            startBackgroundMusic();  // Restart background music
        }

        if (!started) {
            started = true;
            startBackgroundMusic();  // Start background music when the game starts
        } else if (!gameOver) {
            if (yMotion > 0) {
                yMotion = 0;
            }
            yMotion -= 10;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!paused) {
            int speed = 10;

            ticks++;

            if (started) {
                for (int i = 0; i < columns.size(); i++) {
                    Rectangle column = columns.get(i);
                    column.x -= speed;
                }

                for (int i = 0; i < apples.size(); i++) {
                    Apple apple = apples.get(i);
                    apple.x -= speed;
                }

                for (int i = 0; i < clouds.size(); i++) {
                    Cloud cloud = clouds.get(i);
                    cloud.x -= speed / 2; // Clouds move slower than other elements
                }

                for (int i = 0; i < airplanes.size(); i++) {
                    Airplane airplane = airplanes.get(i);
                    airplane.x -= speed; // Airplanes move at the same speed as columns
                }

                if (ticks % 2 == 0 && yMotion < 15) {
                    yMotion += 2;
                }

                for (int i = 0; i < columns.size(); i++) {
                    Rectangle column = columns.get(i);

                    if (column.x + column.width < 0) {
                        columns.remove(column);

                        if (column.y == 0) {
                            addColumn(false);
                        }
                    }
                }

                for (int i = 0; i < apples.size(); i++) {
                    Apple apple = apples.get(i);

                    if (apple.x + apple.width < 0) {
                        apples.remove(apple);
                    }
                }

                for (int i = 0; i < clouds.size(); i++) {
                    Cloud cloud = clouds.get(i);

                    if (cloud.x + cloud.width < 0) {
                        clouds.remove(cloud);
                        addCloud();
                    }
                }

                for (int i = 0; i < airplanes.size(); i++) {
                    Airplane airplane = airplanes.get(i);

                    if (airplane.x + airplane.width < 0) {
                        airplanes.remove(airplane);
                        addAirplane();
                    }
                }

                if (ticks % 100 == 0) {
                    addApple();
                }

                bird.y += yMotion;

                for (Rectangle column : columns) {
                    if (column.intersects(bird)) {
                        gameOver = true;
                        stopBackgroundMusic();  // Stop background music
                        playGameOverSound();    // Play game over sound
                        bird.x = column.x - bird.width;
                    }
                }

                for (Apple apple : apples) {
                    if (apple.intersects(bird)) {
                        score++;
                        apples.remove(apple);
                        break;
                    }
                }

                for (Rectangle column : columns) {
                    if (column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 10 && bird.x + bird.width / 2 < column.x + column.width / 2 + 10) {
                        score++;
                    }
                }

                if (bird.y > HEIGHT - 120 || bird.y < 0) {
                    gameOver = true;
                    stopBackgroundMusic();  // Stop background music
                    playGameOverSound();    // Play game over sound
                }

                if (bird.y + yMotion >= HEIGHT - 120) {
                    bird.y = HEIGHT - 120 - bird.height;
                }
            }

            renderer.repaint();
        }
    }

    public void repaint(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw clouds
        for (Cloud cloud : clouds) {
            paintCloud(g, cloud);
        }

        // Draw airplanes
        for (Airplane airplane : airplanes) {
            paintAirplane(g, airplane);
        }

        // Draw grass
        paintGrass(g);

        g.drawImage(birdImage, bird.x, bird.y, bird.width, bird.height, null);

        for (Rectangle column : columns) {
            paintColumn(g, column);
        }

        for (Apple apple : apples) {
            paintApple(g, apple);
        }

        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 60));

        if (!started) {
            g.drawString("Press Space to Start", 75, HEIGHT / 2 - 50);
        }

        if (gameOver) {
            g.drawString("Game Over", 100, HEIGHT / 2 - 50);
        }

        if (!gameOver && started) {
            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
        }

        // Display pause message
        if (paused) {
            g.drawString("Paused", WIDTH / 2 - 100, HEIGHT / 2 - 50);
        }

        // Display pause instruction in the middle of the green rectangle
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Press P to Pause", WIDTH / 2 - 80, HEIGHT - 80); // Adjusted Y position
    }

    // Play game over sound
    public void playGameOverSound() {
        if (gameOverSound != null && !gameOverSound.isRunning()) {
            gameOverSound.setFramePosition(0);
            gameOverSound.start();
        }
    }

    // Start background music
    public void startBackgroundMusic() {
        if (backgroundMusic != null && !backgroundMusic.isRunning()) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();
        }
    }

    // Stop background music
    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        } else if (e.getKeyCode() == KeyEvent.VK_P) { // Toggle pause with 'P' key
            if (paused) {
                paused = false;
                startBackgroundMusic();  // Resume background music
            } else {
                paused = true;
                stopBackgroundMusic();   // Stop background music
            }
        }
    }

    public static void main(String[] args) {
        flappyBird = new FlappyBird();
    }
}

class Renderer extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        FlappyBird.flappyBird.repaint(g);
    }
}