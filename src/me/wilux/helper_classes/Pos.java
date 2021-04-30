package me.wilux.helper_classes;

public class Pos {
    public int x;
    public int y;
    public Pos(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() { return x+y; }
}

