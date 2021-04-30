package me.wilux.square1;

import me.wilux.helper_classes.LLNode;
import me.wilux.helper_classes.Pos;
import me.wilux.helper_classes.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class FMap implements Comparable {
    private int nSquares;
    private LLNode<Square> squares;
    private final int nFilled;

    private boolean[][] grid;
    private int height;
    private int width;

    private HashSet<Pos> unfilledPosSet;
    private float fillPerSquare;

    public FMap withSquare(Square square){
        boolean[][] gridCopy = Arrays.stream(grid).map(boolean[]::clone).toArray(boolean[][]::new);

        FMap fmap;
        if(square == null){
            fmap = new FMap(
                    this.nSquares,
                    this.squares,
                    this.nFilled,
                    gridCopy
            );
        } else {
            for (int dy = 0; dy <= square.extension; dy++) {
                for (int dx = 0; dx <= square.extension; dx++) {
                    int rx = square.pos.x+dx;
                    int ry = square.pos.y+dy;

                    gridCopy[ry][rx] = true;

                    Pos p = new Pos(rx,ry);
                }
            }

            fmap = new FMap(
                    this.nSquares+1,
                    this.squares.append(square),
                    this.nFilled + (square.extension+1)*(square.extension+1),
                    gridCopy
            );
        }

        return fmap;
    }

    FMap(int nSquares, LLNode<Square> squares, int nFilled, boolean[][] grid){
        this.nSquares = nSquares;
        this.squares = squares;
        this.nFilled = nFilled;
        this.grid = grid;
        this.fillPerSquare = ((float)nFilled)/((float)nSquares);

        this.height = grid.length;
        this.width = grid[0].length;

        this.unfilledPosSet = new HashSet<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(!grid[y][x]){
                    //System.out.println("x:"+x+" y:"+y);
                    this.unfilledPosSet.add(new Pos(x,y));
                }
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        FMap left = (FMap)o;
        FMap right = this;

        return Float.compare(left.fillPerSquare, right.fillPerSquare);
    }

    public boolean[][] getGrid() { return grid;}

    public HashSet<Pos> getUnfilledPosSet() { return unfilledPosSet; }

    public int getHeight() { return height; }

    public int getWidth() { return width; }

    public int getnFilled() { return nFilled; }

    public List<Square> getSquares() {
        List<Square> squareList = new ArrayList<>();
        LLNode<Square> curr = this.squares;

        while (curr != null && curr.getValue() != null){
            squareList.add(curr.getValue());
            curr = curr.getTail();
        }

        return squareList;
    }
}



