package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


public class Menu {
    private int width;
    private int height;
    static boolean gameStart = false;
    static String message = "";
    static boolean newGame = false;
    public static boolean load = false;
    public static String name = "";
    public static Color color = new Color(100,149,237);

    public Menu() {
        this.width = 50;
        this.height = 50;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Helvetica", Font.BOLD, 27);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.enableDoubleBuffering();
    }

    private static void theMenu() {
        StdDraw.clear(color);
        StdDraw.text(25, 40, "CS61B: THE GAME");
        StdDraw.text(25, 28, "New Game (n)");
        StdDraw.text(25, 25, "Start Game (s)");
        StdDraw.text(25, 22, "Load Game (l)");
        StdDraw.text(25, 19, "Quit (:q)");
        StdDraw.text(25, 16, "Restart (x)");
        StdDraw.show();
    }

    private static void newer() {
        while (!StdDraw.hasNextKeyTyped()) {
        }
        char key = StdDraw.nextKeyTyped();
        if (key == 'n') {
            newGame = true;
            StdDraw.clear(color);
            StdDraw.text(25, 27, "Enter name: ");
            StdDraw.text(25, 19, "Press (0) to continue");
            StdDraw.show();
            Menu.name();
        } else if (key == 'l') {
            load = true;
        }
    }

    public static void name() {
        while (true) {
            while (!StdDraw.hasNextKeyTyped()) {
            }
            char key = StdDraw.nextKeyTyped();
            if (key == '0') {
                StdDraw.clear(color);
                StdDraw.text(25, 27, "Enter seed: ");
                StdDraw.text(25, 19, "Press (s) to start game");
                StdDraw.show();
                Menu.seed();
                break;
            } else {
                name = name + key;
                StdDraw.clear(color);
                StdDraw.text(25, 27, "Enter name:");
                StdDraw.text(25, 23, name);
                StdDraw.text(25, 19, "Press (0) to continue");
                StdDraw.show();
            }
        }
    }

    public static void seed() {
        while (true) {
            while (!StdDraw.hasNextKeyTyped()) {
            }
            char key = StdDraw.nextKeyTyped();
            if (key == 's') {
                gameStart = true;
                break;
            } else {
                message = message + key;
                StdDraw.clear(color);
                StdDraw.text(25, 27, "Enter seed:");
                StdDraw.text(25, 23, message);
                StdDraw.text(25, 19, "Press (s) to start game");
                StdDraw.show();
            }
        }
    }

    public static void activate() {
        Menu boom = new Menu();
        boom.theMenu();
//        boom.namer();
//        boom.name();
        boom.newer();

//        StdDraw.clear(color);
//        StdDraw.text(25, 28, "Escape Seahaven");
//        StdDraw.text(25, 25, "");
//        StdDraw.text(25, 22, "based on the American film 'The Truman Show'");
//
//        StdDraw.show();
//        StdDraw.pause(1000);
//
//        StdDraw.clear(color);
//        StdDraw.text(25, 34, "Truman Burbank is the unknowing subject of");
//        StdDraw.text(25, 31, "a reality television program 'The Truman Show.'");
//        StdDraw.text(25, 28, "He has lived his whole life in the fabricated");
//        StdDraw.text(25, 25, "town of Seahaven Island, contained in a massive");
//        StdDraw.text(25, 22, "arcological dome. All other inhabitants of");
//        StdDraw.text(25, 19, "Seahaven are actors, with Truman being the lone");
//        StdDraw.text(25, 16, "character unaware of his existence as a");
//        StdDraw.text(25, 13, "psychological experiment.");
//
//        StdDraw.show();
//        StdDraw.pause(10000);
//
//        StdDraw.clear(color);
//        StdDraw.text(25, 28, "Help Truman sail through the waters to find the");
//        StdDraw.text(25, 25, "exit door of the dome, so he can escape");
//        StdDraw.text(25, 22, "Seahaven and live his life in the outside world.");
//
//        StdDraw.show();
//        StdDraw.pause(7000);
    }

    public static void winScreen() {
        StdDraw.clear(Color.WHITE);
        StdDraw.text(25, 25, "Truman escaped! You win.");
        StdDraw.show();
    }
}
