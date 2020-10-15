package Project1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class SnakesAndLadders extends Thread{
    private final int WindowHeight = 700;
    private final int WindowWidth = 900;

    private JFrame frame = null;
    public JButton button_rollDice = null;

    private JButton[] board;

    private JLabel[] lable_diceList;
    private JLabel label_gameStatus;
    private JButton btn_restart;
    private JButton btn_newGame;
    private int size;
    private final int diceSize = 100;

    private ImageIcon diceImages[];
    private ImageIcon tileImages[];
    private ImageIcon playerImages[];

    private JLabel playerIcon[];

    private Game go;

    public SnakesAndLadders(int _size, int dice, Game _go, Player players[], int boardPos[]) {
        size = _size;
        getImages(_size, dice, (WindowHeight - 35) / _size);

        go = _go;
        //creates the GUI
        makeMainWindow(size, dice, go, players, boardPos);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    //Creates the GUI
    private void makeMainWindow(int numberOfSquares,int numberOfDice, Game go, Player players[], int boardPos[]){
        frame = new JFrame("Snakes and Ladders");
        frame.setSize(WindowWidth, WindowHeight);
        frame.setBackground(Color.RED);
        //frame.setBackground(new Color(189, 252, 252, 255));
        frame.setLayout(null);

        //creates and places the players
        Random rand = new Random();

        playerIcon = new JLabel[players.length];
        for(int i = 0; i<players.length; i++){
            playerIcon[i] = new JLabel();

            int x = 235 + Math.abs( rand.nextInt() % ((WindowHeight / size)/2) );
            int y = WindowHeight - WindowHeight / size + rand.nextInt() % (WindowHeight / (size*3)- 15);

            playerIcon[i].setIcon(playerImages[players[i].pieceImageIndex]);
            playerIcon[i].setBounds(x, y, playerImages[players[i].pieceImageIndex].getIconWidth(), playerImages[players[i].pieceImageIndex].getIconHeight());
            playerIcon[i].setVisible(true);
            frame.add(playerIcon[i]);
        }

        createBoard2(boardPos);


        //Roll Dice button
        final int ButtonWidth = WindowWidth / (numberOfSquares);
        final int ButtonHeight = ButtonWidth / 3;

        button_rollDice = new JButton("Roll Dice");
        button_rollDice.setBounds((int) ((WindowWidth - WindowHeight - ButtonWidth)/2 ),
                (int) (WindowHeight * 0.2), ButtonWidth, ButtonHeight);
        frame.add(button_rollDice);

        lable_diceList = new JLabel[numberOfDice];
        for(int i = 0; i < numberOfDice; i++) {
            lable_diceList[i] = new JLabel();
            lable_diceList[i].setIcon(new ImageIcon(diceImages[i].getImage().getScaledInstance(diceSize, diceSize, Image.SCALE_DEFAULT)));
            lable_diceList[i].setBounds(50, (200 + 110 * i), diceSize, diceSize);
            frame.add(lable_diceList[i]);


        }

        label_gameStatus = new JLabel(players[0].name + " turn");
        label_gameStatus.setBounds(((WindowWidth - WindowHeight - ButtonWidth)/2 ),
                (int) (WindowHeight * 0.2-50), ButtonWidth, ButtonHeight);
        label_gameStatus.setVisible(true);
        frame.add(label_gameStatus);

        btn_restart = new JButton("Restart");
        btn_restart.setBounds(((WindowWidth - WindowHeight - ButtonWidth)/2 ),
                (int) (WindowHeight-ButtonHeight*2), ButtonWidth, ButtonHeight);
        btn_restart.setVisible(false);
        frame.add(btn_restart);

        btn_newGame = new JButton("New Game");
        btn_newGame.setBounds(((WindowWidth - WindowHeight - ButtonWidth)/2 ),
                (int) (WindowHeight-ButtonHeight*3), ButtonWidth, ButtonHeight);
        btn_newGame.setVisible(true);
        frame.add(btn_newGame);


        frame.setVisible(true);

        //play a turn
        button_rollDice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button_rollDice.setEnabled(false);
                go.Roll();
            }
        });

        //restart current game
        btn_restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go.restart();
                button_rollDice.setEnabled(true);
                btn_restart.setVisible(false);

            }
        });

        //new game
        btn_newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameSettings gs = new GameSettings();
                frame.dispose();
            }
        });
    }

    private void createBoard2(int boardPos[]){

        int buttonSize = (WindowHeight - 35) / size;
        board = new JButton[size*size];
        int squareNumber;

        //finds the button number, starting at 0
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++) {

                if (size % 2 == 0) {
                    if (j % 2 == 0)
                        squareNumber = (size - j) * size - i - 1;
                    else
                        squareNumber = (size - j - 1) * size + i;
                } else {
                    if (j % 2 == 0)
                        squareNumber = (size - j - 1) * size + i;
                    else
                        squareNumber = (size - j) * size - i - 1;
                }

                board[squareNumber] = new JButton();
                board[squareNumber].setBounds(i * buttonSize + 230, j * buttonSize, buttonSize, buttonSize);
                board[squareNumber].setBorderPainted(false);
                board[squareNumber].setEnabled(false);

                //ladder on position squareNumber
                if(boardPos[squareNumber] > 1 ){
                    board[squareNumber].setDisabledIcon(tileImages[(3 + ((i + j) % 2))]);
                    board[squareNumber].setIcon(tileImages[3 + ((i + j) % 2)]);
                }
                //snake om position squareNumber
                else if(boardPos[squareNumber]<0){
                    board[squareNumber].setDisabledIcon(tileImages[(5 + ((i + j) % 2))]);
                    board[squareNumber].setIcon(tileImages[5 + ((i + j) % 2)]);
                }
                else if(squareNumber == size*size-1){
                    board[squareNumber].setDisabledIcon(tileImages[(7 + ((i + j) % 2))]);
                    board[squareNumber].setIcon(tileImages[7 + ((i + j) % 2)]);
                }

                //normal tile on position squareNumber
                else {
                    board[squareNumber].setDisabledIcon(tileImages[(i + j) % 2]);
                    board[squareNumber].setIcon(tileImages[2]);

                    //adds functionality to the squares
                    board[squareNumber].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            boardClick();
                        }
                    });
                }


                frame.add(board[squareNumber]);
            }
        }
    }
    public void updateBoard(Dice[] dice, Player player){
        updateDice(dice);

        movePiece(player);
    }

    // updates the dice images
    private void updateDice(Dice dice[]){

        new Thread(){
            public void run(){
                Random rand = new Random();
                for(int loops = 0; loops < 5; loops++) {
                    for (int i = 0; i < dice.length; i++) {
                        lable_diceList[i].setIcon(diceImages[rand.nextInt(6)]);
                    }
                    try {  sleep(100);
                    }catch(Exception e){  }
                }

                for(int i = 0; i<dice.length; i++){
                    lable_diceList[i].setIcon(diceImages[dice[i].faceValue-1]);
                }
            }
        }.start();

    }

    //moves a player to the players position
    public void movePiece(Player player){
        Random rand = new Random();

        int x = board[player.position].getBounds().x + Math.abs(rand.nextInt() % board[player.position].getWidth())/2 + 5;
        int y = board[player.position].getBounds().y + Math.abs(rand.nextInt() % board[player.position].getHeight())/3 + 5;

        playerIcon[player.ID].setBounds(x, y, playerIcon[player.ID].getWidth(), playerIcon[player.ID].getHeight());
    }

    //player lands on a snake or a ladder
    public void enableBoardButton(int btn){
        button_rollDice.setEnabled(false);

        board[btn].setEnabled(true);
    }

    //when a square on the board is clicked
    private void boardClick(){
        for(int i = 0; i< board.length; i++)
            board[i].setEnabled(false);
        button_rollDice.setEnabled(true);
        go.movePlayer();

    }

    //a player has won
    public void playerWon(Player player){
        label_gameStatus.setText(player.name + " won!");
        button_rollDice.setEnabled(false);
        btn_restart.setVisible(true);
    }

    //make the roll button clickable
    public void enableRoll(){
        button_rollDice.setEnabled(true);
    }

    //updates the turn lable
    public void setLabel_gameStatus(Player player){
        label_gameStatus.setText(player.name + "s turn");
    }

    private void getImages(int _size, int dice, int tileSize){
        //size = _size;

        //get dice images from file
        diceImages = new ImageIcon[6];
        for(int i = 0; i<6; i++ ){
            try {
                diceImages[i] = new ImageIcon("C:\\Users\\alexa\\Documents\\skole\\5semester\\Utvikling\\imt_3281\\Task_3\\src\\Project1\\Images\\dice"+ (i+1) +".jpg");
                //cant get it to work with relative path
                //images[i] = new ImageIcon(".\\Images\\dice"+ (i+1) +".jpg");
            }
            catch (Exception e){
                System.out.println("Error with file ./Images/dice" + i + ".jpg");
            }
        }


        //get tile images from file
        tileImages = new ImageIcon[9];
        for(int i = 0; i<9; i++) {
            tileImages[i] = new ImageIcon("C:\\Users\\alexa\\Documents\\skole\\5semester\\Utvikling\\imt_3281\\Task_3\\src\\Project1\\Images\\TileImage"+ i +".jpg"); // load the image to a imageIcon
            Image image = tileImages[i].getImage(); // transform it
            Image newimg = image.getScaledInstance(tileSize, tileSize, java.awt.Image.SCALE_FAST); // scale it the smooth way
            tileImages[i] = new ImageIcon(newimg);  // transform it back
        }

        //get player images from file
        playerImages = new ImageIcon[6];
        for(int i = 0; i<6; i++) {
            playerImages[i] = new ImageIcon("C:\\Users\\alexa\\Documents\\skole\\5semester\\Utvikling\\imt_3281\\Task_3\\src\\Project1\\Images\\PlayerImage"+ i +".png"); // load the image to a imageIcon
            Image image = playerImages[i].getImage(); // transform it
            Image newimg = image.getScaledInstance((tileSize/8)*2, tileSize/3, java.awt.Image.SCALE_FAST); // scale it the smooth way
            playerImages[i] = new ImageIcon(newimg);  // transform it back
        }
    }
}
