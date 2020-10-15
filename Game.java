package Project1;

import java.util.Random;

public class Game{
    private SnakesAndLadders snakesAndLadders;
    private static Dice dice[];
    private Player players[];

    private int playerTurn;
    private int board[];
    private int size;
    final int END = 1;


    public Game(int numberOfDice, Player _players[], int boardSize, int numberSnakes, int numberLadders){

        size = boardSize;
        playerTurn = 0;
        dice = new Dice[numberOfDice];
        players = new Player[_players.length];

        //creates Dice
        for (int i = 0; i<numberOfDice; i++){
            dice[i] = new Dice();
        }
        //creates players
        for (int i = 0; i< _players.length; i++) {
            players[i] = _players[i];
        }

        //places the snakes and ladders
        board = new int[boardSize * boardSize];
        placeSnakesAndLadders(numberSnakes, numberLadders);

        //creates GUI
        snakesAndLadders = new SnakesAndLadders(boardSize, numberOfDice, this, players, board);
    }

    private void placeSnakesAndLadders(int numSnakes, int numLadders){
        //places snakes
        for(int i = 0; i<numSnakes; i++){
            placeSnake();
        }
        for(int i = 0; i<numLadders; i++){
            placeLadder();
        }
    }

    //places a snake on the board -x = head, 1 = tail
    private void placeSnake(){
        int head, tail;
        head = findEmpty();
        tail = findEmpty();

        //if head is placed before the end, swap them
        if(head < tail) {
            int temp = head;
            head = tail;
            tail = temp;
        }

        //"Places" the snake om the board
        board[head] = -tail;
        board[tail] = END;
    }

    //places a ladder on the board x = start, 1 = end
    private void placeLadder(){
        int start, end;
        start = findEmpty();
        end = findEmpty();

        //if head is placed before the end, swap them
        if(start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        //"Places" the ladder om the board
        board[start] = end;
        board[end] = END;
    }

    //find a random empty spot on the board
    private int findEmpty(){
        Random rand = new Random();
        int tempIndex = rand.nextInt(board.length - 2) + 1;

        for(; board[tempIndex] != 0; tempIndex++)
            if(tempIndex >= board.length-1)
                tempIndex = 1;

        return tempIndex;
    }

    //updates the nextplayer variable
    private void nextPlayer(){
        playerTurn++;
        playerTurn = playerTurn % players.length;
    }

    //return true if all dice have the same value (more than 1 dice)
    private boolean sameDiceValue(){
        if(dice.length == 1) return false;

        for(int i = 1; i<dice.length; i++) {
            if (dice[i-1].faceValue != dice[i].faceValue) {
                return false;
            }
        }
        return true;
    }

    private boolean playerClickButton(){
        snakesAndLadders.button_rollDice.setEnabled(true);

        return true;
    }

    //main game loop
    public void Roll() {
            do {
                if (!sameDiceValue()) {
                    nextPlayer();
                }

                int value = players[playerTurn].rollDice(dice);
                players[playerTurn].updatePosition(size, value);
                snakesAndLadders.updateBoard(dice, players[playerTurn]);

                //checks if the player won
                if (players[playerTurn].position == size * size -1){
                    break;
                }

                    //if a player lands on a snake or a ladder
                    if (board[players[playerTurn].position] != 0 && board[players[playerTurn].position] != 1) {
                        //if the AI landed on something
                        if (players[playerTurn].isAI) {
                            movePlayer();
                            //if the player landed on something
                        } else {
                            snakesAndLadders.enableBoardButton(Math.abs( board[players[playerTurn].position] ));
                            players[playerTurn].setPosition(players[playerTurn].position);
                            break;
                        }
                    }
                    //if nothing special happens
                    else
                        snakesAndLadders.enableRoll();

                //if the AI plays next turn
            } while ((players[playerTurn].isAI && sameDiceValue()) || players[(playerTurn + 1) % players.length].isAI);

            //if someone won
            if (players[playerTurn].position == size * size - 1){
                snakesAndLadders.playerWon(players[playerTurn]);

            }else if(!sameDiceValue()) //same players turn
                snakesAndLadders.setLabel_gameStatus(players[(playerTurn + 1) % players.length]);
    }

    //reset the variables
    public void restart(){
        for(int i = 0; i<players.length; i++){
            players[i].setPosition(0);
            snakesAndLadders.movePiece(players[i]);
        }
        playerTurn = 0;
    }

    public void movePlayer(){
        players[playerTurn].setPosition(Math.abs( board[players[playerTurn].position]) );
        snakesAndLadders.movePiece(players[playerTurn]);
    }
}
