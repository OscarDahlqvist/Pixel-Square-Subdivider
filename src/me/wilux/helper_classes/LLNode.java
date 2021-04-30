package me.wilux.helper_classes;

public class LLNode<T> {
    T value;
    LLNode<T> tail;

    public LLNode<T> append(T value){
        return new LLNode<T>(value,this);
    }

    public LLNode(T value, LLNode<T> tail){
        this.value = value;
        this.tail = tail;
    }

    public T getValue() { return value; }

    public LLNode<T> getTail() { return tail; }
}
