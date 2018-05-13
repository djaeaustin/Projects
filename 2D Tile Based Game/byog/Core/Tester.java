package byog.Core;

import byog.TileEngine.TETile;
import org.junit.Test;
import static org.junit.Assert.*;

public class Tester {

    @Test
    public void someTest() {
        Game game1 = new Game();
        TETile[][] t1 = game1.playWithInputString("n5197880843569031643s");
        TETile[][] t2 = game1.playWithInputString("n5197880843569031643s");
        boolean boom = true;
        for (int i = 0; i < t1.length; i++) {
            for (int j = 0; j < t1[0].length; j++) {
                if (t1[i][j].character() != t2[i][j].character()) {
                    boom = false;
                }
            }
        }
        assertEquals(boom, true);
    }
}
