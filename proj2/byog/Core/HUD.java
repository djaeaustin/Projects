package byog.Core;

import byog.TileEngine.Tileset;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

public class HUD {
    private static int x;
    private static int y;
    private static String location = "";

    public static String Mouse(TETile[][] world) {
        x = (int) StdDraw.mouseX();
        y = (int) StdDraw.mouseY();
        if (x >= 80 || y >= 32 || x < 0 || y < 0) {
            location = "";
        } else if (world[x][y + 2] == Tileset.WALL) {
            location = "wall";
        } else if (world[x][y + 2] == Tileset.WATER) {
            location = "water";
        } else if (world[x][y + 2] == Tileset.PLAYER) {
            location = "player";
        } else if (world[x][y + 2] == Tileset.UNLOCKED_DOOR) {
            location = "unlocked door";
        } else {
            location = "";
        }
        return location;
    }
}
