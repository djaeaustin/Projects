package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round = 1;
    private Random rand;
    private boolean gameOver = false;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(43, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.WHITE);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        String message = "";
        for (int i = 0; i < n; i++) {
            message = message + CHARACTERS[rand.nextInt(CHARACTERS.length)];
        }
        return message;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        //TODO: If the game is over, make sure to end the game.
        StdDraw.clear(Color.WHITE);
        if (gameOver) {
            StdDraw.text(20, 20, "Game Over! You made it to round: " + Integer.toString(round));
            StdDraw.show();
        } else {
            StdDraw.text(5, 35, "Round: " + Integer.toString(round));
            StdDraw.text(20, 35, "Watch!");
            StdDraw.text(20, 5, ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
            StdDraw.text(20, 20, s);
            StdDraw.show();
        }
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        //TODO: Make sure all my code has proper comments.
        //TODO: Make sure to always go for falling up airs as Falcon.
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

        public String solicitNCharsInput(int n) {
        //TODO: Use this function in other functions.
        //TODO: Read n letters of player input
        String message = "";
        for (int i = 0; i < n; i++) {
            while (!StdDraw.hasNextKeyTyped()) {
            }
            message = message + StdDraw.nextKeyTyped();
        }
        return message;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        //TODO: Clear board and ping face.
        //TODO: Establish Game loop
        drawFrame("Round: " + Integer.toString(round));
        StdDraw.pause(2000);
        String test = generateRandomString(round);
        flashSequence(test);
        String message = solicitNCharsInput(round);
        if (message.equals(test)) {
            round++;
            startGame();
        } else {
            gameOver = true;
            drawFrame("wow");
        }
    }

}
