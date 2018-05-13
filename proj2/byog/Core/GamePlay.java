package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GamePlay implements Serializable {
    static boolean win = false;
    public static boolean restart = false;

    public static void play(TETile[][] world) {
        while (true) {
            char key = StdDraw.nextKeyTyped();
            switch (key) {
                case ':':
                    while (!StdDraw.hasNextKeyTyped()) { }
                    if (StdDraw.nextKeyTyped() == 'q') {
                        try {
                            FileOutputStream fileOut = new FileOutputStream("proj2serializable");
                            ObjectOutputStream out = new ObjectOutputStream(fileOut);
                            out.writeObject(Game.getPlayercoordinates());
                            out.close();
                            fileOut.close();
                            FileOutputStream fileOut2 = new FileOutputStream("proj2serializablename");
                            ObjectOutputStream out2 = new ObjectOutputStream(fileOut2);
                            out2.writeObject(Menu.name);
                            out2.close();
                            fileOut2.close();
                        } catch (IOException i) {
                            i.printStackTrace();
                        }
                        System.exit(0);
                    }
                    break;
                case 'w': moveUp(world);
                          break;
                case 'a': moveLeft(world);
                          break;
                case 's': moveDown(world);
                          break;
                case 'd': moveRight(world);
                          break;
                case 'x': restart = true;
                          break;
                default: break;
            }
            break;
        }
    }

    public static void inputplay(TETile[][] world, String moves) {
        for (int i = 0; i < moves.length(); i++) {
            switch (moves.charAt(i)) {
                case 'w': moveUp2(world);
                          break;
                case 'a': moveLeft2(world);
                          break;
                case 's': moveDown2(world);
                          break;
                case 'd': moveRight2(world);
                          break;
                case ':':
                    if (moves.charAt(i + 1) == ('q')) {
                        try {
                            FileOutputStream fileOut = new
                                    FileOutputStream("proj2inputserializable.txt");
                            ObjectOutputStream out = new ObjectOutputStream(fileOut);
                            out.writeObject(Game.getPlayercoordinates2());
                            out.close();
                            fileOut.close();
                        } catch (IOException k) {
                            k.printStackTrace();
                        }
                    }
                    break;
                default: break;
            }
        }
    }

    public static void moveUp(TETile[][] world) {
        int x = Game.getPlayercoordinates()[0];
        int y = Game.getPlayercoordinates()[1];
        if (y + 1 > 29) {
            world[x][y] = Tileset.PLAYER;
        } else if (world[x][y + 1] == Tileset.UNLOCKED_DOOR) {
            win = true;
        } else if (world[x][y + 1] == Tileset.WATER) {
            world[x][y + 1] = Tileset.PLAYER;
            world[x][y] = Tileset.WATER;
            Game.getPlayercoordinates()[1] += 1;
        }
    }

    public static void moveRight(TETile[][] world) {
        int x = Game.getPlayercoordinates()[0];
        int y = Game.getPlayercoordinates()[1];
        if (x + 1 > 79) {
            world[x][y] = Tileset.PLAYER;
        } else if (world[x + 1][y] == Tileset.UNLOCKED_DOOR) {
            win = true;
        } else if (world[x + 1][y] == Tileset.WATER) {
            world[x + 1][y] = Tileset.PLAYER;
            world[x][y] = Tileset.WATER;
            Game.getPlayercoordinates()[0] += 1;
        }
    }

    public static void moveLeft(TETile[][] world) {
        int x = Game.getPlayercoordinates()[0];
        int y = Game.getPlayercoordinates()[1];
        if (x - 1 < 0) {
            world[x][y] = Tileset.PLAYER;
        } else if (world[x - 1][y] == Tileset.UNLOCKED_DOOR) {
            win = true;
        } else if (world[x - 1][y] == Tileset.WATER) {
            world[x - 1][y] = Tileset.PLAYER;
            world[x][y] = Tileset.WATER;
            Game.getPlayercoordinates()[0] -= 1;
        }
    }

    public static void moveDown(TETile[][] world) {
        int x = Game.getPlayercoordinates()[0];
        int y = Game.getPlayercoordinates()[1];
        if (y - 1 < 0) {
            world[x][y] = Tileset.PLAYER;
        } else if (world[x][y - 1] == Tileset.UNLOCKED_DOOR) {
            win = true;
        } else if (world[x][y - 1] == Tileset.WATER) {
            world[x][y - 1] = Tileset.PLAYER;
            world[x][y] = Tileset.WATER;
            Game.getPlayercoordinates()[1] -= 1;
        }
    }

    public static void moveUp2(TETile[][] world) {
        int x = (int) Game.getPlayercoordinates2()[0];
        int y = (int) Game.getPlayercoordinates2()[1];
        if (y + 1 > 29) {
            world[x][y] = Tileset.PLAYER;
        } else if (world[x][y + 1] == Tileset.UNLOCKED_DOOR) {
            win = true;
        } else if (world[x][y + 1] == Tileset.WATER) {
            world[x][y + 1] = Tileset.PLAYER;
            world[x][y] = Tileset.WATER;
            Game.getPlayercoordinates2()[1] += 1;
        }
    }

    public static void moveRight2(TETile[][] world) {
        int x = (int) Game.getPlayercoordinates2()[0];
        int y = (int) Game.getPlayercoordinates2()[1];
        if (x + 1 > 79) {
            world[x][y] = Tileset.PLAYER;
        } else if (world[x + 1][y] == Tileset.UNLOCKED_DOOR) {
            win = true;
        } else if (world[x + 1][y] == Tileset.WATER) {
            world[x + 1][y] = Tileset.PLAYER;
            world[x][y] = Tileset.WATER;
            Game.getPlayercoordinates2()[0] += 1;
        }
    }

    public static void moveLeft2(TETile[][] world) {
        int x = (int) Game.getPlayercoordinates2()[0];
        int y = (int) Game.getPlayercoordinates2()[1];
        if (x - 1 < 0) {
            world[x][y] = Tileset.PLAYER;
        } else if (world[x - 1][y] == Tileset.UNLOCKED_DOOR) {
            win = true;
        } else if (world[x - 1][y] == Tileset.WATER) {
            world[x - 1][y] = Tileset.PLAYER;
            world[x][y] = Tileset.WATER;
            Game.getPlayercoordinates2()[0] -= 1;
        }
    }

    public static void moveDown2(TETile[][] world) {
        int x = (int) Game.getPlayercoordinates2()[0];
        int y = (int) Game.getPlayercoordinates2()[1];
        if (y - 1 < 0) {
            world[x][y] = Tileset.PLAYER;
        } else if (world[x][y - 1] == Tileset.UNLOCKED_DOOR) {
            win = true;
        } else if (world[x][y - 1] == Tileset.WATER) {
            world[x][y - 1] = Tileset.PLAYER;
            world[x][y] = Tileset.WATER;
            Game.getPlayercoordinates2()[1] -= 1;
        }
    }
}
