import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    final int SCREEN_WIDTH = 800;
    final int SCREEN_HEIGHT = 800;
    final int UNIT_SIZE = 25;
    final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
    final int DELAY = 80;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    int snakeLength = 6;
    int orangesEaten;
    int orangeX;
    int orangeY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;


    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new GameKeyAdapter());
        startGame();
    }
    public void startGame(){
        createOrange();
        this.running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        if(running){
            //Grid
            for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++){
                g.drawLine(i * UNIT_SIZE,0,i * UNIT_SIZE,SCREEN_HEIGHT);
                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
            }

            //Orange
            g.setColor(Color.ORANGE);
            g.fillOval(orangeX,orangeY,UNIT_SIZE,UNIT_SIZE);

            //Snake
            for (int i = 0; i < snakeLength; i++) {
                if(i == 0){
                    g.setColor(Color.GREEN);
                }else{
                    g.setColor(new Color(40,130,0));
                }
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
            }

            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics fontMetrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + orangesEaten,(SCREEN_WIDTH - fontMetrics.stringWidth("Score: " + orangesEaten))/2,fontMetrics.getHeight());

        }else{
            gameOver(g);
        }


    }

    public void gameOver(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free",Font.BOLD,70));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Snake Dead",(SCREEN_WIDTH - fontMetrics.stringWidth("Snake Dead"))/2,SCREEN_HEIGHT/2);
    }

    public void createOrange(){
        orangeX = random.nextInt(SCREEN_WIDTH /UNIT_SIZE) * UNIT_SIZE;
        orangeY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE) * UNIT_SIZE;
    }

    public void move(){

        //Shift
        for (int i = snakeLength; i > 0 ; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

            switch (direction){
                case 'U':
                    y[0] = y[0] - UNIT_SIZE;
                    break;
                case 'D':
                    y[0] = y[0] + UNIT_SIZE;
                    break;
                case 'L':
                    x[0] = x[0] - UNIT_SIZE;
                    break;
                case 'R':
                    x[0] = x[0] + UNIT_SIZE;
                    break;
            }
    }

    public void checkOrange(){
        if((x[0] == orangeX) && (y[0] == orangeY)){
            snakeLength ++;
            orangesEaten ++;
            createOrange();
        }
    }

    public void checkCollisions(){
        //Check for collision with the body
        for (int i = snakeLength; i > 0; i --){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }

        //Check for collision with left or right of game frame
        if(x[0] < 0){
            running = false;
        }

        if(x[0] >  SCREEN_WIDTH){
            running = false;
        }

        //Check for collision with top or bottom game frame
        if(y[0] < 0){
            running = false;
        }

        if(y[0] >  SCREEN_HEIGHT){
            running = false;
        }

        if(!running){
            timer.stop();
        }

    }

    public class GameKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkOrange();
            checkCollisions();
        }
        repaint();
    }
}
