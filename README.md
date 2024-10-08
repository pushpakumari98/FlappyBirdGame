<h2><b>Flappy Bird Game</b></h2>
<hr>
<p>This project is a simple clone of the popular "Flappy Bird" game, built using Java's Swing and AWT libraries. The game includes custom graphics for the bird, apples, clouds, grass, and airplanes. It also features background music and a sound effect for game over.</p>

<h3><b>Table of Contents</b></h3>
  <ul>
    <li><strong>Requirements</strong></li>
    <li><strong>Installation</strong></li>
    <li><strong>How to Play</strong></li>
    <li><strong>Game Features</strong></li>
    <li><strong>Controls</strong></li>
    <li><strong>Customization</strong></li>
    <li>Contribution</li>
</ul>
<h3><b>Requirements</b></h3>
<ul>
    <li><strong>Java:</strong> Ensure that you have the latest version of Java installed on your system. You can download Java from <a href="https://www.java.com">here</a>.</li>
    <li><strong>IDE (Optional):</strong> You can use any IDE like IntelliJ IDEA, Eclipse, or NetBeans to run the project. Alternatively, it can be run directly from the command line.</li>
    <li><strong>Audio and Image Files:</strong> The following files are needed in the same directory as the code:
        <ul>
            <li><strong>bird.png:</strong> The image for the bird character.</li>
            <li><strong>apple.png:</strong> The image for apples in the game.</li>
            <li><strong>clouds.png:</strong> The image for clouds.</li>
            <li><strong>grasses.png:</strong> The image for grass.</li>
            <li><strong>airplane.png:</strong> The image for airplanes.</li>
            <li><strong>mixkit-arcade.wav:</strong> The sound file for the game-over sound.</li>
            <li><strong>mixkit-game-level.wav:</strong> The background music sound file.</li>
        </ul>
    </li>
</ul>
<h3><b>Installation</b></h3>
<ul>
    <li><strong>Clone the Repository:</strong>
        <ul>
            <li>Use Git or download the project manually.</li>
            <li><code>git clone https://github.com/your-username/flappy-bird-java-game.git</code></li>
        </ul>
    </li>
    <li><strong>Add Image and Sound Files:</strong>
        <ul>
            <li>Download the necessary <code>.png</code> image files and <code>.wav</code> sound files and place them in the root directory of the project.</li>
        </ul>
    </li>
    <li><strong>Compile the Game:</strong>
        <ul>
            <li>If you're using an IDE, import the project into your workspace and compile it.</li>
            <li>If you're using the terminal, navigate to the project directory and compile the Java files using the following command:</li>
            <li><code>javac FlappyBird.java</code></li>
        </ul>
    </li>
    <li><strong>Run the Game:</strong>
        <ul>
            <li>After compiling, run the game with the following command:</li>
            <li><code>java FlappyBird</code></li>
        </ul>
    </li>
</ul>

<h3><b>How to play</b></h3>
<ul>
    <li>When the game starts, you'll see a static bird in the middle of the screen.</li>
    <li>To begin, press the spacebar to make the bird jump.</li>
    <li>The bird continuously falls due to gravity, so keep pressing the spacebar to avoid obstacles and stay in the air.</li>
    <li>The goal is to pass between the columns while avoiding hitting them.</li>
</ul>
<h3><b>Game Features</b></h3>
<ul>
    <li><strong>Obstacles:</strong>
        <ul>
            <li>Columns appear from the right and move toward the bird. The player must guide the bird through the gaps between the columns.</li>
        </ul>
    </li>
    <li><strong>Collectibles:</strong>
        <ul>
            <li>Apples appear as collectibles. Collect them to increase your score.</li>
        </ul>
    </li>
    <li><strong>Background Objects:</strong>
        <ul>
            <li>Clouds and airplanes move in the background to create a more dynamic environment.</li>
        </ul>
    </li>
    <li><strong>Sounds:</strong>
        <ul>
            <li>Background music plays continuously while the game is running.</li>
            <li>A game-over sound is played when the bird crashes.</li>
        </ul>
    </li>
    <li><strong>Pause Feature:</strong>
        <ul>
            <li>Press P to pause and resume the game.</li>
        </ul>
    </li>
</ul>
<h3><b>Controls</b></h3>
<ul>
    <li><strong>Spacebar:</strong> Press to make the bird jump.</li>
    <li><strong>P:</strong> Press to pause and resume the game.</li>
    <li><strong>Esc:</strong> Close the game.</li>
</ul>
<h3><b>Customization</b></h3> 
<ul>
    <li><strong>Changing Images:</strong> Replace the <code>bird.png</code>, <code>apple.png</code>, <code>clouds.png</code>, <code>grasses.png</code>, and <code>airplane.png</code> files in the root directory to use different graphics for the bird, apples, clouds, grass, and airplanes.</li>
    <li><strong>Modifying Difficulty:</strong> You can modify the difficulty by adjusting the speed of the columns or the frequency at which they spawn. This can be done in the <code>addColumn</code> method of the <code>FlappyBird</code> class.</li>
    <li><strong>Sound Customization:</strong> Replace the sound files (<code>mixkit-arcade.wav</code> and <code>mixkit-game-level.wav</code>) with any <code>.wav</code> files of your choice to customize background music and the game-over sound.</li>
</ul>
<h3><b></b></h3>
Contributions are welcome! If you'd like to contribute to the game, please fork the repository and submit a pull request with your changes.


https://github.com/user-attachments/assets/055f2a60-0237-4589-9bb8-d4a55b767ad9





