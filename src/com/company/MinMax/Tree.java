package com.company.MinMax;
import com.company.Node;
import java.util.List;


public class Tree {
    Node  position;
    List<Tree> children;

    Tree(Node node, List<Tree> nodeList) {
        this.position=node;
        this.children=nodeList;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "position=" + position.x +" "+position.y+
                ", children=" + children +
                '}';
    }

}
