package byog.Core;

import byog.TileEngine.Tileset;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;



public class Game implements Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 35;
    private static int[] playercoordinates = new int[3];
    private static int[] oldplayercoordinates = new int[3];
    private static long[] playercoordinates2 = new long[3];
    private static long[] oldplayercoordinates2 = new long[3];
    private static String oldname;

    static String moves;

    public void playWithKeyboard() {
        TETile[][] world;
        Menu.activate();
        File file = new File("proj2serializable");
        File file2 = new File("proj2serializablename");
        if (file.exists() && file2.exists()) {
            try {
                FileInputStream fis = new FileInputStream("proj2serializable");
                ObjectInputStream ois = new ObjectInputStream(fis);
                oldplayercoordinates = (int[]) ois.readObject();
                ois.close();
                fis.close();
                FileInputStream fis2 = new FileInputStream("proj2serializablename");
                ObjectInputStream ois2 = new ObjectInputStream(fis2);
                oldname = (String) ois2.readObject();
                ois2.close();
                fis2.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return;
            } catch (ClassNotFoundException c) {
                System.out.println("Class not found");
                c.printStackTrace();
                return;
            }
        }
        if (Menu.load) {
            Menu.message = String.valueOf(oldplayercoordinates[2]);
        }
        String intmessage = Menu.message;
        playercoordinates[2] = Integer.parseInt(intmessage);
        if (Menu.load) {
            Menu.load = false;
            Menu.name = oldname;
            world = saveLink();
        } else {
            world = gameLink();
        }
        playing(world);
    }

    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        File file = new File("proj2inputserializable.txt");
        if (file.exists() && input.charAt(0) == 'l') {
            try {
                FileInputStream fis = new FileInputStream("proj2inputserializable.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                oldplayercoordinates2 = (long[]) ois.readObject();
                ois.close();
                fis.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return finalWorldFrame;
            } catch (ClassNotFoundException c) {
                System.out.println("Class not found");
                c.printStackTrace();
                return finalWorldFrame;
            }
            finalWorldFrame = inputsaveLink(input, finalWorldFrame);
        } else {
            finalWorldFrame = inputgameLink(input);
        }
        GamePlay.inputplay(finalWorldFrame, moves);
//        ter.renderFrame(finalWorldFrame);

        return finalWorldFrame;
    }

    public TETile[][] gameLink() {
        Random rANDOM = new Random(Integer.parseInt(Menu.message));
        int numRooms = rANDOM.nextInt(10) + 15;
        int[][] coordinates = new int[numRooms][2];

        ter.initialize(WIDTH, HEIGHT , 0, -2);

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        WorldCreator.worldFiller(finalWorldFrame);
        Creation c = new Creation();
        for (int i = 0; i < numRooms; i++) {
            int height = rANDOM.nextInt(7) + 3;
            int width = rANDOM.nextInt(7) + 3;
            int startx = rANDOM.nextInt(68) + 4;
            int starty = rANDOM.nextInt(18) + 4;
            c.roomCreator(finalWorldFrame, height,
                    width, startx,
                    starty, coordinates);
        }
        c.hallCreator(finalWorldFrame, coordinates, numRooms);
        c.encapsulation(finalWorldFrame);

        while (true) {
            int xdoor = rANDOM.nextInt(80);
            int ydoor = rANDOM.nextInt(30);
            if (finalWorldFrame[xdoor][ydoor] == Tileset.WATER) {
                finalWorldFrame[xdoor][ydoor] = Tileset.UNLOCKED_DOOR;
                break;
            }
        }

        while (true) {
            int xplayer = rANDOM.nextInt(80);
            int yplayer = rANDOM.nextInt(30);
            if (finalWorldFrame[xplayer][yplayer] == Tileset.WATER) {
                finalWorldFrame[xplayer][yplayer] = Tileset.PLAYER;
                playercoordinates[0] = xplayer;
                playercoordinates[1] = yplayer;
                break;
            }
        }

        StdDraw.text(25, 34, HUD.Mouse(finalWorldFrame));
        ter.renderFrame(finalWorldFrame);

        StdDraw.clear(new Color(0, 0, 0));

        return finalWorldFrame;
    }

    public TETile[][] saveLink() {
        Random rANDOM = new Random(Integer.parseInt(Menu.message));
//        Menu.message = "";
        int numRooms = rANDOM.nextInt(10) + 15;
        int[][] coordinates = new int[numRooms][2];

        ter.initialize(WIDTH, HEIGHT, 0, -2);

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        WorldCreator.worldFiller(finalWorldFrame);
        Creation c = new Creation();
        for (int i = 0; i < numRooms; i++) {
            int height = rANDOM.nextInt(7) + 3;
            int width = rANDOM.nextInt(7) + 3;
            int startx = rANDOM.nextInt(68) + 4;
            int starty = rANDOM.nextInt(18) + 4;
            c.roomCreator(finalWorldFrame, height,
                    width, startx,
                    starty, coordinates);
        }
        c.hallCreator(finalWorldFrame, coordinates, numRooms);
        c.encapsulation(finalWorldFrame);

        while (true) {
            int xdoor = rANDOM.nextInt(80);
            int ydoor = rANDOM.nextInt(30);
            if (finalWorldFrame[xdoor][ydoor] == Tileset.WATER) {
                finalWorldFrame[xdoor][ydoor] = Tileset.UNLOCKED_DOOR;
                break;
            }
        }

        finalWorldFrame[oldplayercoordinates[0]][oldplayercoordinates[1]] = Tileset.PLAYER;

        playercoordinates[0] = oldplayercoordinates[0];
        playercoordinates[1] = oldplayercoordinates[1];


        StdDraw.text(25,34, HUD.Mouse(finalWorldFrame));
        ter.renderFrame(finalWorldFrame);
        StdDraw.clear(new Color(0, 0, 0));

        return finalWorldFrame;
    }

    public TETile[][] inputgameLink(String input) {
        long sEED  = Long.parseLong(input.replaceAll("[^0-9]", ""));
        playercoordinates2[2] = sEED;
        moves = input.substring(String.valueOf(sEED).length() + 1);
        Random rANDOM = new Random(sEED);
        int numRooms = rANDOM.nextInt(10) + 15;
        int[][] coordinates = new int[numRooms][2];

//        ter.initialize(WIDTH, HEIGHT);

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        WorldCreator.worldFiller(finalWorldFrame);
        Creation c = new Creation();
        for (int i = 0; i < numRooms; i++) {
            int height = rANDOM.nextInt(7) + 3;
            int width = rANDOM.nextInt(7) + 3;
            int startx = rANDOM.nextInt(68) + 2;
            int starty = rANDOM.nextInt(18) + 2;
            c.roomCreator(finalWorldFrame, height,
                    width, startx,
                    starty, coordinates);
        }
        c.hallCreator(finalWorldFrame, coordinates, numRooms);
        c.encapsulation(finalWorldFrame);

        while (true) {
            int xdoor = rANDOM.nextInt(80);
            int ydoor = rANDOM.nextInt(30);
            if (finalWorldFrame[xdoor][ydoor] == Tileset.WATER) {
                finalWorldFrame[xdoor][ydoor] = Tileset.UNLOCKED_DOOR;
                break;
            }
        }

        while (true) {
            int xplayer = rANDOM.nextInt(80);
            int yplayer = rANDOM.nextInt(30);
            if (finalWorldFrame[xplayer][yplayer] == Tileset.WATER) {
                finalWorldFrame[xplayer][yplayer] = Tileset.PLAYER;
                playercoordinates2[0] = xplayer;
                playercoordinates2[1] = yplayer;
                break;
            }
        }

        return finalWorldFrame;
    }

    public TETile[][] inputsaveLink(String input, TETile[][] world) {
//        ter.initialize(WIDTH, HEIGHT);
        long sEED  = oldplayercoordinates2[2];
        moves = input.substring(1);
        Random rANDOM = new Random(sEED);
        int numRooms = rANDOM.nextInt(10) + 15;
        int[][] coordinates = new int[numRooms][2];

        WorldCreator.worldFiller(world);
        Creation c = new Creation();
        for (int i = 0; i < numRooms; i++) {
            int height = rANDOM.nextInt(7) + 3;
            int width = rANDOM.nextInt(7) + 3;
            int startx = rANDOM.nextInt(68) + 2;
            int starty = rANDOM.nextInt(18) + 2;
            c.roomCreator(world, height,
                    width, startx,
                    starty, coordinates);
        }
        c.hallCreator(world, coordinates, numRooms);
        c.encapsulation(world);

        while (true) {
            int xdoor = rANDOM.nextInt(80);
            int ydoor = rANDOM.nextInt(30);
            if (world[xdoor][ydoor] == Tileset.WATER) {
                world[xdoor][ydoor] = Tileset.UNLOCKED_DOOR;
                break;
            }
        }

        int boom1 = (int) oldplayercoordinates2[0];
        int boom2 = (int) oldplayercoordinates2[1];

        world[boom1][boom2] = Tileset.PLAYER;

        return world;
    }

    public static int[] getPlayercoordinates() {
        return playercoordinates;
    }

    public static long[] getPlayercoordinates2() {
        return playercoordinates2;
    }

    public void hud(TETile[][] world) {
        ter.renderFrame(world);
        StdDraw.text(3, 34, HUD.Mouse(world));
    }

    public void playing(TETile[][] world) {
        while (true) {
            while (!StdDraw.hasNextKeyTyped()) {
                StdDraw.text(25, 34, HUD.Mouse(world));
                ter.renderFrame(world);
                StdDraw.clear(new Color(0, 0, 0));
            }
            GamePlay.play(world);
            if (GamePlay.win) {
                GamePlay.win = false;
                Menu.message = "";
                Menu.name = "";
                Menu.winScreen();
                while (!StdDraw.hasNextKeyTyped()) { }
                if (StdDraw.nextKeyTyped() == 'x') {
                    playWithKeyboard();
                } else {
                    System.exit(0);
                }
            } else if (GamePlay.restart) {
                GamePlay.restart = false;
                Menu.message = "";
                Menu.name = "";
                playWithKeyboard();
            } else {
                StdDraw.text(25,34, HUD.Mouse(world));
                ter.renderFrame(world);
                StdDraw.clear(new Color(0, 0, 0));
            }
        }
    }
}


