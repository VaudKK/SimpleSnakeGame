import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame(){
        GamePanel gamePanel = new GamePanel();
        this.setSize(800,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Simple Snake Game");
        this.setLocationRelativeTo(null);
        this.add(gamePanel);
        this.pack();
        this.setVisible(true);
    }

}
