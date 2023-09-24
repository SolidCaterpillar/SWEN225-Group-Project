import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameSetup {

    //Timer or clock
    int tickRate = 100;

    Timer timer = new Timer(tickRate, e -> { //actionlistener
        //updateGame();
        //repaint();
    });

    public GameSetup() {

    }

    public void startTime(){
        timer.start();
    }

    public void stopTime(){
        timer.stop();
    }

}
