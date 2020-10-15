package Project1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSettings {
    public GameSettings(){
        final int width = 325;
        final int height = 550;

        final int ButtonWidth = 100;
        final int ButtonHeight = 50;

        final int LableWidth = 70;
        final int LableHeight = 25;

        final int numberOfPlayer = 4;


        String[] playerType = { "---", "Player", "AI"};
        String[] playerImg = { "---", "Red", "Yellow", "Blue", "Pink", "White", "Black"};

        JTextField names[] = new JTextField[numberOfPlayer];
        JComboBox types[] = new JComboBox[numberOfPlayer];
        JComboBox imgs[] = new JComboBox[numberOfPlayer];


        JFrame setframe = new JFrame("New game");
        setframe.setSize(width, height);
        setframe.setLayout(null);
        //setframe.setVisible(true);


        //player
        JLabel lab_player = new JLabel("Players");
        lab_player.setBounds(10, 10, LableWidth, LableHeight);
        setframe.add(lab_player);

        final int xStart = 30;
        final int yStart = 50;
        final int xSpace = LableWidth/4;
        final int ySpace = LableHeight+10;

        for(int i = 0; i < numberOfPlayer; i++){
            int j = 0;
            types[i] = new JComboBox(playerType);

            if(i<2)
                types[i].setSelectedIndex(1);

            types[i].setBounds(xSpace * (j++) + xStart, ySpace*i + yStart, LableWidth, LableHeight);
            setframe.add(types[i]);

            names[i] = new JTextField("Player" + (i + 1));
            names[i].setBounds(xSpace * (j++) + xStart + 80, ySpace*i + yStart, LableWidth, LableHeight);
            setframe.add(names[i]);

            imgs[i] = new JComboBox(playerImg);

            if(i<2)
                imgs[i].setSelectedIndex(1);

            imgs[i].setBounds(xSpace * (j++) + xStart + 160, ySpace*i + yStart, LableWidth, LableHeight);
            setframe.add(imgs[i]);
        }

        //Board
        JLabel lab_Board = new JLabel("Board:");
        lab_Board.setBounds(10, 200, LableWidth, LableHeight);
        setframe.add(lab_Board);


        String[] boardText = {"Size:", "Snakes:", "Ladders:"};
        String[] cbNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] cdSize = {"5x5", "6x6", "7x7", "8x8", "9x9", "10x10"};
        JLabel boardLable[] = new JLabel[3];
        JComboBox boardCB[] = new JComboBox[3];


        for(int i = 0; i < 3; i++){
            int j = 0;
            boardLable[i] = new JLabel(boardText[i]);
            boardLable[i].setBounds((xSpace * (j++)) + xStart, ySpace*i + yStart + 180, LableWidth, LableHeight);
            setframe.add(boardLable[i]);


            if(i == 0)
                boardCB[i] = new JComboBox(cdSize);
            else
                boardCB[i] = new JComboBox(cbNumbers);

            boardCB[i].setBounds( 95 + xStart, ySpace*i + yStart + 180, LableWidth, LableHeight);
            setframe.add(boardCB[i]);
        }


        //Dice
        JLabel lab_Dice = new JLabel("Dice:");
        lab_Dice.setBounds(10, 350, LableWidth, LableHeight);
        setframe.add(lab_Dice);

        JLabel lab_NumberOfDice = new JLabel("Number of Dice:");
        lab_NumberOfDice.setBounds(xStart+xSpace, ySpace + 350, LableWidth+50, LableHeight);
        setframe.add(lab_NumberOfDice);

        JComboBox CB_numDice = new JComboBox(cbNumbers);
        CB_numDice.setSelectedIndex(1);
        CB_numDice.setBounds(xStart+145, ySpace + 350, LableWidth+50, LableHeight);
        setframe.add(CB_numDice);



        //start
        JButton start = new JButton("Start");
        start.setBounds(xStart+145, 450, ButtonWidth, ButtonHeight);
        setframe.add(start);

        //updates frame
        setframe.setVisible(true);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int activPlayers= 0;

                //checkes if there are enough valid players (all inputs are valid)
                for(int i = 0; i<numberOfPlayer; i++){
                    if(types[i].getSelectedIndex() != 0 && names[i].getText().length() != 0 && imgs[i].getSelectedIndex() != 0)
                        activPlayers++;
                }
                if(activPlayers > 1){
                    Player players[] = new Player[activPlayers];

                    int playerIndex = 0;
                    //creates players
                    for(int i = 0; i<numberOfPlayer; i++){
                        //checks if all the inputs are correct, if they are the player is created
                        if(types[i].getSelectedIndex() != 0 && names[i].getText().length() != 0 && imgs[i].getSelectedIndex() != 0){

                            if(types[i].getSelectedIndex() == 1)
                                players[playerIndex++] = new Player(names[i].getText(), imgs[i].getSelectedIndex()-1, false, i);
                            else
                                players[playerIndex++] = new Player(names[i].getText(), imgs[i].getSelectedIndex()-1, true, i);
                        }
                    }

                    //gets all the info from the input
                    int numberD = CB_numDice.getSelectedIndex() + 1;
                    int boardSize = boardCB[0].getSelectedIndex() + 5;
                    int numberSnakes = boardCB[1].getSelectedIndex() + 1;
                    int numberLadders = boardCB[2].getSelectedIndex() + 1;

                    //create game that creats GUI
                    Game game = new Game(numberD, players, boardSize,numberSnakes, numberLadders);
                    setframe.setVisible(false);
                }
            };
        });
    }
}
