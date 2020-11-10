package com.company;

import java.util.Comparator;

public class Move {
    public int scor;
    public Node piesa;
    public Node next;
    public Move(int scor, Node node, Node next){
        this.scor=scor;
        this.piesa=node;
        this.next=next;
    }
    public static Comparator<Move> bestScore = (s1, s2) -> s2.scor - s1.scor;
}
