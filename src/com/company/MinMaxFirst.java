package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MinMaxFirst {
    public char[][] board;
    private final int[] moveX = {-1, -1,  0, 1, 1,  1,   0, -1};
    private final int[] moveY = {0,   1,  1, 1, 0, -1,  -1, -1};

    MinMaxFirst()
    {
        board= new char[4][4];
        for(int i=1 ; i< 3;i++)
            for(int j=0; j< 4; j++)
            {
                board[i][j]='0';
            }
        for(int i=0;i< 4; i++){
            board[0][i]='c';
            board[3][i]=(char)(i+48+1);
        }

    }

    public boolean isFinal(){
        boolean okCalculator= true;
        boolean okOm=true;
        for(int i=0;i< 4; i++){
            {
                if (board[0][i] != '1'&&board[0][i] != '2'&&board[0][i] != '3'&&board[0][i] != '4') //daca pe linia 0 a ajuns omul cu toate piesele lui
                    okOm = false;
                if (board[3][i] != 'c') // daca pe linia 4 a ajuns calculatorul cu toate piesele lui
                    okCalculator = false;

            }
        }
        return  okCalculator || okCalculator;
    }

    public void MinMaxstart(){
        printBoard();
        Scanner myObj = new Scanner(System.in);
        while(!isFinal()){
            String nrPiesa = myObj.nextLine();
            String i = myObj.nextLine();
            String j = myObj.nextLine();
            makeMove(Integer.parseInt(i),Integer.parseInt(j),nrPiesa.charAt(0));

            List<Move> mutariPosibile=MinMaxMovesGenerator();

             Move movePC= MinMaxGetBest(mutariPosibile);
             makeMoveComputer(movePC.next, movePC.piesa);

            printBoard();
        }

    }
    public List<Move> MinMaxMovesGenerator(){
        List<Move> possibleMoves = new ArrayList<>();
        for(int i=0 ; i< 4;i++)
            for(int j=0; j< 4; j++){
                if(board[i][j]=='c')
                {
                    possibleMoves.add(MinMax_2(new Node(i,j)));
                }
            }
        return possibleMoves;
    }

    public Move MinMax_2(Node curent){
        List<Node> posibileDinCurent= getPossibleMoves(curent);
        int max=0;
        Node maxNode=null;

        for (Node node:posibileDinCurent) {
            if(max<getMinForLevel2(node,curent)){
                max=getMinForLevel2(node,curent);
                maxNode=node;
            }
        }
        return  new Move(max, curent, maxNode);
    }
    public Move MinMaxGetBest(List<Move> possibleMoves){
        possibleMoves.sort(Move.bestScore);
        return possibleMoves.get(0);
    }

    private int getMinForLevel2(Node node, Node parent) {
        List<Node> possibleMoves=getPossibleMoves(node);
        int min=100;
        Node nodeMin=null;
        for (Node possNode: possibleMoves) {

            board[possNode.x][possNode.y]='c';
            board[parent.x][parent.y]='0';

            int scor=evaluate();

            board[possNode.x][possNode.y]='0';
            board[parent.x][parent.y]='c';

            if(scor<min)
            {
                min=scor;
                nodeMin=node;
            }
        }

        return min;
    }
    public List<Node> getPossibleMoves(Node node){
        List<Node> possible= new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Node next= new Node(node.x + moveX[i],node.y+moveY[i]);
            if(validate(next))
                possible.add(next);
        }
        return possible;
    }
    public boolean validate(Node node){
        return node.x<4 && node.x>=0 && node.y<4 && node.y>=0 && board[node.x][node.y]=='0';
    }

    public void printBoard(){
        for(int i=0 ; i< 4;i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

    }

    private void makeMoveComputer(Node bestMove, Node oldMove) {
        board[bestMove.x][bestMove.y]='c';
        board[oldMove.x][oldMove.y]='0';
    }
    public int evaluate(){
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
    public void makeMove(int i, int j, char piesa){
        for(int ii=0; ii<4; ii++)
            for(int jj=0; jj< 4; jj++)
                if(board[ii][jj]==piesa)
                    board[ii][jj]='0';
        board[i][j]=piesa;
    }

}
