package Project1;

import javax.swing.*;
import java.util.Random;

public class Dice {
    public int faceValue;
    private Random rand = new Random();

    public Dice() {
        rollDice();
    }

    public int rollDice() {
        faceValue = rand.nextInt(6) + 1;
        return faceValue;
    }
}
