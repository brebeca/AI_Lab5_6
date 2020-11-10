package com.company.MinMax;
import com.company.Game;
import com.company.Move;
import com.company.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MinMaxGame extends Game {
    public void start(){
        printBoard();
        Scanner myObj = new Scanner(System.in);
        while(!isFinal()){
            String nrPiesa = myObj.nextLine();
            String i = myObj.nextLine();
            String j = myObj.nextLine();
            makeMove(Integer.parseInt(i),Integer.parseInt(j),nrPiesa.charAt(0));

            Move movePC= getBest();
            makeMoveComputer(movePC.next, movePC.piesa);

            printBoard();
        }
    }

    public Move getBest(){
        List<Tree> trees= constructMinMaxTrees(2);
        int max = 0;
        Node pizitiePiesaScorMAx=null;
        for (Tree tree: trees) {
            int scor=minMaxAlg(tree.position,tree,2,-9999,9999,"max");
            if(scor>=max)
            {
                max=scor;
                pizitiePiesaScorMAx=tree.position;
            }
        }
        return new Move(max,pizitiePiesaScorMAx,best);
    }
    public Node best;

    public  int minMaxAlg(Node root, Tree current, int depth,int alpha,int beta, String lookFor)
    {
        if(depth==0)
            return  evaluate(current.position,root);

        if(lookFor.contains("max"))
        {
            int max=-1;
            for (Tree child : current.children) {
                int eval= minMaxAlg(root, child, depth-1,alpha, beta,"min");
              if(max<eval){
                  max=eval;
                  best=child.position;
              }
              alpha=Math.max(max,alpha);
              if(beta<=alpha)
                  break;
            }
            return  max;
        }
        else {
            int min=9999;
            for (Tree child: current.children) {
                int eval= minMaxAlg(root, child, depth-1,alpha, beta,"max");
                min=Math.min(eval, min);
                beta=Math.min(beta,min);
                if(beta<=alpha)
                    break;
            }
            return min;
        }
    }

    public List<Tree> constructMinMaxTrees(int depth)
    {
        List<Tree> possibleMoves = new ArrayList<>();
        for(int i=0 ; i< 4;i++)
            for(int j=0; j< 4; j++){
                if(board[i][j]=='c')
                {
                    possibleMoves.add(new Tree(new Node(i, j), getSubtrees(new Node(i,j) , depth)));
                }
            }
        return possibleMoves;
    }

    public List<Tree> getSubtrees(Node node,int depth) {
        if(depth==0)
            return null;
        List<Tree> trees= new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Node next= new Node(node.x + moveX[i],node.y+moveY[i]);
            if(validate(next))
            {
                Tree tree=new Tree(next,getSubtrees(next,depth-1));
                trees.add(tree);
            }
        }
        return trees;
    }

    public void testTrees()
    {
        List<Tree> list= getSubtrees(new Node(0,0), 2);
        for (Tree t:list) {
            System.out.println(t.toString());
            System.out.println();
        }
        printBoard();
    }


}
