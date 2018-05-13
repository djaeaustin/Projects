package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class WorldCreator {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 35;

    public static void worldFiller(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }
}
