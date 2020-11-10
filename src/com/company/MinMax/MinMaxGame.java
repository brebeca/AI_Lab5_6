package com.company.MinMax;
import com.company.Game;
import com.company.Move;
import com.company.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MinMaxGame extends Game {
    public void startMinMAx(){
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

    public void startAlfaBeta(){
        printBoard();
        Scanner myObj = new Scanner(System.in);
        while(!isFinal()){
            String nrPiesa = myObj.nextLine();
            String i = myObj.nextLine();
            String j = myObj.nextLine();
            makeMove(Integer.parseInt(i),Integer.parseInt(j),nrPiesa.charAt(0));

            Move movePC= getBestAlfaBeta();
            makeMoveComputer(movePC.next, movePC.piesa);

            printBoard();
        }
    }
    public Move getBestAlfaBeta(){
        List<Tree> trees= constructMinMaxTrees(2);
        int max = 0;
        Move bestMove=null;
        for (Tree tree: trees) {
            Move currentMove=alfaBetaAlg(tree.position,tree,2,-9999,9999,"max");
            if(currentMove.scor>=max)
            {
                max=currentMove.scor;
                bestMove=currentMove;
            }
        }
        return bestMove;
    }

    public Move getBest(){
        List<Tree> trees= constructMinMaxTrees(2);
        int max = 0;
        Move bestMove=null;
        for (Tree tree: trees) {
            Move currentMove=minMaxAlg(tree.position,tree,2,-9999,9999,"max");
            if(currentMove.scor>=max)
            {
                max=currentMove.scor;
                bestMove=currentMove;
            }
        }
        return bestMove;
    }
    public  Move alfaBetaAlg(Node root, Tree current, int depth,int alpha,int beta, String lookFor)
    {
        if(depth==0)
            return new Move( evaluate(current.position,root), current.position,null);

        Node best=null;
        if(lookFor.contains("max"))
        {
            int max=-1;
            for (Tree child : current.children) {
                int eval= minMaxAlg(root, child, depth-1,alpha, beta,"min").scor;
                if(max<eval){
                    max=eval;
                    best=child.position;
                }
                alpha=Math.max(max,alpha);
                if(beta<=alpha)
                    break;
            }
            return  new Move(max, current.position, best);
        }
        else {
            int min=9999;
            for (Tree child: current.children) {
                int eval= minMaxAlg(root, child, depth-1,alpha, beta,"max").scor;
                min=Math.min(eval, min);
                if(min>eval){
                    min=eval;
                    best=child.position;
                }
                beta=Math.min(beta,min);
                if(beta<=alpha)
                    break;
            }
            return new Move(min, current.position, best);
        }
    }


    public  Move minMaxAlg(Node root, Tree current, int depth,int alpha,int beta, String lookFor)
    {
        if(depth==0)
            return new Move( evaluate(current.position,root), current.position,null);

        Node best=null;
        if(lookFor.contains("max"))
        {
            int max=-1;
            for (Tree child : current.children) {
                  int eval= minMaxAlg(root, child, depth-1,alpha, beta,"min").scor;
                  if(max<eval){
                      max=eval;
                      best=child.position;
                  }
            }
            return  new Move(max, current.position, best);
        }
        else {
            int min=9999;
            for (Tree child: current.children) {
                int eval= minMaxAlg(root, child, depth-1,alpha, beta,"max").scor;
                min=Math.min(eval, min);
                if(min>eval){
                    min=eval;
                    best=child.position;
                }
            }
            return new Move(min, current.position, best);
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
    public int evaluate2(){
        int yc=0;
        int yo=0;

        for(int i=0; i<4; i++)
            for(int j=0; j< 4; j++)
                if(board[i][j]=='c')
                    yc+=i+1;
                else
                if(board[i][j]=='o')
                    yo+=i+1;

        return 12- yc-yo;
    }


}
