package Project1;

import javax.swing.*;

public class Player{
    String name;
    int pieceImageIndex;
    int position, previousPosition;
    boolean isAI;
    int ID;

    public Player(String _name, int _pieceImageIndex, boolean _isAI, int id){
        name = _name;
        pieceImageIndex = _pieceImageIndex;
        position = 0;
        previousPosition = 0;
        isAI = _isAI;
        ID = id;

    }

    public int rollDice(Dice dice[]){
        int tot = 0;
        for(int i = 0; i<dice.length; i++){
            tot += dice[i].rollDice();
        }
        return tot;
    }

    public void updatePosition(int size, int total){
        previousPosition = position;

        if(position + total > size*size - 1){
            position = size*size - ( (position + total) % (size*size - 1) ) - 1;
        }
        else
            position += total;
    }

    public void setPosition(int pos){
        previousPosition = position;
        position = pos;
    }
}
