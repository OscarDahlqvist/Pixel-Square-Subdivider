package me.wilux.square1;

import me.wilux.helper_classes.LLNode;
import me.wilux.helper_classes.Pos;
import me.wilux.helper_classes.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        String[] smap = {
            "########....",
            "...##.......",
            "...##.......",
            "...##.......",
            ".........###",
            ".........###",
            "#..#########",
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

        FMap baseMap = new FMap(
                0,
                new LLNode<>(null,null),
                0,
                grid
        );

        prnt(baseMap,'#');
        FMap uncor = subdivideFmap(baseMap);
        renderMap(uncor);
    }

    //TODO: this function overrides the input, (make it not)
    public static FMap subdivideFmap(FMap srcMap){
        List<Pos> corridors = findCorridors(srcMap);
        boolean[][] grid = srcMap.getGrid();
        for (Pos p: corridors) {
            grid[p.y][p.x] = true;
        }
        prnt(srcMap,'#');
        return srcMap;
    }

    public static List<Pos> findCorridors(FMap srcMap){
        boolean[][] grid = srcMap.getGrid();
        int height= grid.length;
        int width = grid[0].length;

        List<List<Pos>> filters = get1Filters();

        //Find 1 wide corridors
        List<Pos> corridors = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (List<Pos> filter: filters) {
                    boolean matchesFilter = true;
                    for (Pos pos: filter) {
                        int dx = pos.x;
                        int dy = pos.y;
                        if(!unfillable(srcMap,x+dx,y+dy)){
                            matchesFilter = false;
                            break;
                        }
                    }
                    if(matchesFilter){
                        corridors.add(new Pos(x,y));
                    }
                }
            }
        }
        return corridors;
    }

    public static List<List<Pos>> get1Filters(){

        List<List<Pos>> filters = new ArrayList<>();
        for (String[] strFilt: strFilter) {
            ArrayList<Pos> filt = new ArrayList<>();
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    if(strFilt[y].charAt(x) == '#')
                        filt.add(new Pos(x-1,y-1));
                }
            }
            filters.add(filt);
        }
        return filters;
    }

    public static void renderMap(FMap baseMap){
        boolean[][] grid = baseMap.getGrid();

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
        if(y>=fmap.getHeight() || x>=fmap.getWidth() || x<0 || y<0){
            return true;
        }
        return grid[y][x];
    }

    public static void prnt(FMap fmap){
        prnt(fmap, '.');
    }
    public static void prnt(FMap fmap, char wallChar){
        int height = fmap.getHeight();
        int width = fmap.getWidth();

        char[][] chars = new char[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(fmap.getGrid()[y][x])
                    chars[y][x] = wallChar;
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

    public static final String[][] strFilter = new String[][]{
            {"#  ",
             "  #",
             " # ",},
            {" # ",
             "  #",
             "#  ",},
            {" # ",
             "#  ",
             "  #",},
            {"  #",
             "#  ",
             " # ",},
            {"   ",
             "# #",
             "   ",},
            {" # ",
             "   ",
             " # ",},
            {"# #",
             "   ",
             "# #",},
    };
}
