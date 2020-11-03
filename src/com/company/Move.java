package com.company;

public class Move {
    public int scor;
    public Node piesa;
    public Node next;
    Move( int scor, Node node, Node next){
        this.scor=scor;
        this.piesa=node;
        this.next=next;
    }
}
