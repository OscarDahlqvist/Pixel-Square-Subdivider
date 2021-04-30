package me.wilux.helper_classes;

public class Square {
    public Pos pos;
    public int extension;
    public Square(Pos pos, int extension){
        this.pos = pos;
        this.extension = extension;
    }

    @Override
    public int hashCode() { return pos.hashCode()+extension; }
}
