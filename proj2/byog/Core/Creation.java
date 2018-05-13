package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Creation {
    int index;

    private static long SEED;
    private static Random RANDOM;

    public Creation() {
        index = 0;
        SEED = 2;
        RANDOM = new Random(SEED);
    }

    public void roomCreator(TETile[][] tiles, int height, int width, int startx, int starty,
                                   int[][] coordinates) {
        if (tiles[startx][starty] != Tileset.NOTHING) {
            tiles[startx][starty] = tiles[startx][starty];
        } else {
            for (int i = startx + 1; i <= startx + width - 1; i++) {
                for (int j = starty + 1; j <= starty + height - 1; j++) {
                    tiles[i][j] = Tileset.WATER;
                }
            }
            int randomx = RANDOM.nextInt(width) + startx;
            int randomy = RANDOM.nextInt(height) + starty;
            coordinates[index] =  new int[]{randomx, randomy};
            index++;
        }
    }

    public void hallCreator(TETile[][] tiles, int[][] coordinates, int numRooms) {
        for (int i = 0; i < numRooms - 1; i++) {
            if (coordinates[i][0] < coordinates[i + 1][0]) {
                hallHelperRight(tiles, coordinates, i);
            } else {
                hallHelperLeft(tiles, coordinates, i);
            }

            if (coordinates[i][1] < coordinates[i + 1][1]) {
                hallHelperUp(tiles, coordinates, i);
            } else {
                hallHelperDown(tiles, coordinates, i);
            }
        }
    }

    public void hallHelperRight(TETile[][] tiles, int[][] coordinates, int indexer) {
        for (int i = coordinates[indexer][0]; i <= coordinates[indexer + 1][0]; i++) {
            tiles[i][coordinates[indexer][1]] = Tileset.WATER;
        }
    }

    public void hallHelperLeft(TETile[][] tiles, int[][] coordinates, int indexer) {
        for (int i = coordinates[indexer][0]; i >= coordinates[indexer + 1][0]; i--) {
            tiles[i][coordinates[indexer][1]] = Tileset.WATER;
        }
    }

    public void hallHelperUp(TETile[][] tiles, int[][] coordinates, int indexer) {
        for (int i = coordinates[indexer][1]; i <= coordinates[indexer + 1][1]; i++) {
            tiles[coordinates[indexer + 1][0]][i] = Tileset.WATER;
        }
    }

    public void hallHelperDown(TETile[][] tiles, int[][] coordinates, int indexer) {
        for (int i = coordinates[indexer][1]; i >= coordinates[indexer + 1][1]; i--) {
            tiles[coordinates[indexer + 1][0]][i] = Tileset.WATER;
        }
    }

    public void encapsulation(TETile[][] tiles) {
        for (int i = 0; i < WorldCreator.WIDTH; i++) {
            for (int j = 0; j < WorldCreator.HEIGHT - 3; j++) {
                if (floorCheck(tiles, i, j)) {
                    tiles[i][j] = Tileset.WALL;
                }
            }
        }

        for (int x = 0; x < WorldCreator.WIDTH; x++) {
            for (int y = 0; y < 2; y ++) {
                tiles[x][y] = Tileset.WALL;
            }
        }
    }

    public boolean floorCheck(TETile[][] tiles, int i, int j) {
        return (tiles[i][j] == Tileset.NOTHING);
    }
}
