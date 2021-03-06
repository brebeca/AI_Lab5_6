package com.company;
import java.util.*;

public class Game {

    public char[][] board;
    public int[] moveX ;
    public int[] moveY ;

    public Game()
    {
        super();
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
        moveX = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};
        moveY = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
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

    public List<Mutare> possibleMovesGenerator(){
        List<Mutare> possibleMoves = new ArrayList<>();
         for(int i=0 ; i< 4;i++)
            for(int j=0; j< 4; j++){
                if(board[i][j]=='c')
                {
                    possibleMoves.add(new Mutare(new Node(i, j), getPossibleMoves(new Node(i,j))));
                }
            }
        return possibleMoves;
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

    public void makeMove(int i, int j, char piesa){
        for(int ii=0; ii<4; ii++)
            for(int jj=0; jj< 4; jj++)
                if(board[ii][jj]==piesa)
                    board[ii][jj]='0';
        board[i][j]=piesa;
    }

    public int evaluate(Node next, Node old){
        int yc=0;
        int yo=0;
        board[next.x][next.y]='c';
        board[old.x][old.y]='0';

        for(int i=0; i<4; i++)
            for(int j=0; j< 4; j++)
                if(board[i][j]=='c')
                    yc+=i+1;
                else
                    if(board[i][j]=='o')
                        yo+=i+1;

        board[next.x][next.y]='0';
        board[old.x][old.y]='c';
        return 12- yc-yo;
    }







    public void makeMoveComputer(Node bestMove, Node oldMove) {
        board[bestMove.x][bestMove.y]='c';
        board[oldMove.x][oldMove.y]='0';
    }
    public Collection<Node> BFS(Node current){
        List<Node> possibleMoves= new ArrayList<>();
        Queue<Node> coada = new LinkedList<>();
        char[][] markedNodes= new char[4][4];
        for(int i=0 ;i< 4; i++){
            for(int j=0 ;j< 4 ; j++)
                markedNodes[i][j]=board[i][j];
        }

        coada.add(current);
        markedNodes[current.x][current.y]='1';

        while(!coada.isEmpty()){
            Node node=coada.remove();//scoatem nodul curent din coada

            markedNodes[node.x][node.y]='1';//marcam nodul ca vizitat

            for (int i = 0; i < 4; i++) {
                Node next= new Node(node.x + moveX[i],node.y+moveY[i]);
                if(validate(next))
                    if(markedNodes[next.x][next.y]!='1') {//verificam daca nodul a mai fost vizitat
                        possibleMoves.add(next);
                        coada.add(next);
                    }
            }
        }
        return possibleMoves;
    }


}
