package me.wilux.square2;

import me.wilux.helper_classes.Square;

import java.util.ArrayList;
import java.util.List;

public class Plating {
    public static void main(){
        /*
        *   foreach tile in grid
        *       find all squares which can overlap it.
        *
        * */
        String[] smap = {
                "########....",
                "...##.......",
                "...##.......",
                "...##.......",
                ".........###",
                ".........###",
                "#..#########",
        };
        int height= smap.length;
        int width = smap[0].length();

        boolean[][] grid = new boolean[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(smap[y].charAt(x) == '#')
                    grid[y][x] = true;
            }
        }

        ArrayList<Square>[][] sqrSet = new ArrayList[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sqrExt = 0;

            }
        }
    }
}
