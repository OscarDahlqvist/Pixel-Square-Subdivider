package me.wilux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class LLNode<T> {
    T value;
    LLNode<T> tail;

    LLNode append(T value){
        return new LLNode<T>(value,this);
    }

    public LLNode(T value, LLNode<T> tail){
        this.value = value;
        this.tail = tail;
    }
}

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

    public boolean[][] getGrid() {
        return grid;
    }

    public HashSet<Pos> getUnfilledPosSet() {
        return unfilledPosSet;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getnFilled() {
        return nFilled;
    }

    public List<Square> getSquares() {
        List<Square> squareList = new ArrayList<>();
        LLNode<Square> curr = this.squares;

        while (curr != null && curr.value != null){
            squareList.add(curr.value);
            curr = curr.tail;
        }

        return squareList;
    }
}

class Square {
    Pos pos;
    int extension;
    Square(Pos pos, int extension){
        this.pos = pos;
        this.extension = extension;
    }

    @Override
    public int hashCode() { return pos.hashCode()+extension; }
}

class Pos {
    int x;
    int y;
    Pos(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() { return x+y; }
}
