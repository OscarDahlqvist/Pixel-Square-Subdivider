package me.wilux;

import java.io.File;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        String[] smap = {
            "#########...",
            "...######...",
            "...######...",
            "............",
            "...###......",
            "...#########",
        };
        String[] smap2 = {
                "#######....",
                "......#....",
                "......#....",
                "...#....###",
                "#..####.###",
        };
        int smapHeight= smap.length;
        int smapWidth = smap[0].length();

        boolean[][] grid = new boolean[smapHeight][smapWidth];
        for (int y = 0; y < smapHeight; y++) {
            for (int x = 0; x < smapWidth; x++) {
                if(smap[y].charAt(x) == '#')
                    grid[y][x] = true;
            }
        }

        //TODO: divide avanced maps
        renderMap(grid);
    }

    public static void renderMap(boolean[][] grid){
        FMap baseMap = new FMap(
                0,
                new LLNode<>(null,null),
                0,
                grid
        );

        int height= grid.length;
        int width = grid[0].length;
        int nToFill = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(!grid[y][x])
                    nToFill++;
            }
        }
        PriorityQueue<FMap> pq = new PriorityQueue<>();
        pq.add(baseMap);

        int i = 0;
        while (pq.peek() != null){
            FMap fmap = pq.remove();

            if(fmap.getnFilled()==nToFill){
                System.out.println("FINISHED after "+i+" iterations.");
                prnt(fmap);
                return;
            }

            if(i%100000==0){
                System.out.println(i+" iterations.");
                prnt(fmap);
            }
            i++;


            for (Pos pos: fmap.getUnfilledPosSet()) {
                expandFrom(pq, fmap, pos.x, pos.y);
            }
        }
    }

    public static void expandFrom(PriorityQueue<FMap> pq, FMap fmap, int x, int y){
        int sqrex = 0;

        while(true){

            if (unfillable(fmap, x+sqrex, y+sqrex)) return;
            for (int i = 0; i < sqrex; i++) {
                if(unfillable(fmap,x+sqrex,y+i)) return;
                if(unfillable(fmap,x+i,y+sqrex)) return;
            }

            boolean[][] grid = fmap.getGrid();
            grid[y][x] = true;
            for (int i = 0; i < sqrex; i++) {
                grid[y][x] = true;
                grid[y][x] = true;
            }

            Square sq = new Square(new Pos(x,y), sqrex);
            pq.add(fmap.withSquare(sq));


            sqrex++;
        }
    }

    public static boolean unfillable(FMap fmap, int x, int y){
        boolean[][] grid = fmap.getGrid();
        if(y>=fmap.getHeight() || x>=fmap.getWidth()){
            return true;
        }
        return grid[y][x];
    }

    public static void prnt(FMap fmap){
        int height = fmap.getHeight();
        int width = fmap.getWidth();

        char[][] chars = new char[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(fmap.getGrid()[y][x])
                    chars[y][x] = '.';
                else
                    chars[y][x] = ' ';
            }
        }

        char squareDisp = 'A';

        for(Square square: fmap.getSquares()){
            for (int dy = 0; dy <= square.extension; dy++) {
                for (int dx = 0; dx <= square.extension; dx++) {
                    int rx = square.pos.x+dx;
                    int ry = square.pos.y+dy;

                    chars[ry][rx] = squareDisp;
                }
            }
            squareDisp = (char) ((squareDisp+1)%('Z'+1));
        }


        StringBuilder sb = new StringBuilder();
        for (char[] line: chars) {
            for (char c: line) {
                sb.append(c);
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }
}
