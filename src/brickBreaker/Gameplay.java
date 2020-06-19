package brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

// KeyListener 介面 (interface) 進行鍵盤事件的處理，
// 需要實作 keyTyped() 、 keyPressed() 、 keyReleased() 方法 (method)
// 其參數 (parameter) e 為 KeyEvent 物件 (object)

// ActionListener 介面 (interface) 進行按鈕事件的處理，需要實作 actionPerformed() 方法 (method) ，
// 其參數 (parameter) e 為 ActionEvent 物件 (object)
public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalRow = 3;
    private int totalCol = 7;
    private int totalBricks = totalRow * totalCol;
    private Timer timer;
    // delay就是速度
    private int delay = 3;
    private int playerX = 310;
    private int ballposX = 400;
    private int ballposY = 300;
    private int ballXdir = 1;
    private int ballYdir = -1;

    private MapGenerator map;


    public Gameplay(){
        map = new MapGenerator(totalRow, totalCol);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }
    // fillRect(int x, int y, int width, int height)
    // 注意起點是在左上角
    public void paint(Graphics g){
        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // drawing map
        map.draw((Graphics2D) g);

        // score 計分板
        g.setColor(SystemColor.ORANGE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: "+score, 580, 30);

        // borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(681, 0, 3, 592);

        // the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // the ball
        g.setColor(Color.red);
        g.fillOval(ballposX, ballposY, 20, 20);

        // WIN!
        if (totalBricks == 0){
            play = false;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 45));
            g.drawString("You Win!!! You're Scroe is :"+score, 40, 300);

            g.setFont(new Font("serif", Font.BOLD, 22));
            g.drawString("Press Enter to Restart", 220, 350);


        }

        // LOSE!
        if (ballposY > 570){
            play = false;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 45));
            g.drawString("Game Over! You're Scroe is :"+score, 40, 300);

            g.setFont(new Font("serif", Font.BOLD, 22));
            g.drawString("Press Enter to Restart", 220, 350);
        }


        // dispose() 方法 (method) 釋放系統資源，並且停止繪圖。
        // dispose() 沒有回傳值 (return value) ，也不需要參數 (paramenter) 。
        g.dispose();

    }
// actionPerformed() 設定按下按鈕後的動作
    // 設定每次按下按鈕之後 repaint()
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        // Rectangle.intersects(Rectangle r) 是 boolean
        // 檢查移動的板跟球是否有交集
        // Determines whether or not this Rectangle and the specified Rectangle intersect.
        // 加入ballYdir > 0, 避免在板塊中困住
        if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)) && ballYdir > 0){
            ballYdir = -ballYdir;
        }

        // 看是否有碰到方塊
        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[0].length; j++) {
                if (map.map[i][j] == 1) {
                    int brickX = j * map.brickWidth + 80;
                    int brickY = i * map.brickHeight + 50;
                    int brickWidth = map.brickWidth;
                    int brickHeight = map.brickHeight;

                    Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                    if (rect.intersects(ballRect)){
                        map.setBrickValue(0,i, j);
                        totalBricks--;
                        score += 5;
                        // 讓球碰到方塊會彈開
                        // 方法一 我覺得這樣可以
                        // 實際執行看起來也沒太大BUG 不過教學是用方法二
                        // ballXdir = -ballXdir;
                        // ballYdir = -ballYdir;

                        // 方法二
                        if (ballposX + 19 <= rect.x || ballposX +1 >= rect.x +rect.width){
                            ballXdir = -ballXdir;
                        } else {
                            ballYdir = -ballYdir;
                        }

                    }


                }

            }

        }

        // 讓球移動並且檢查球是否碰到上面左邊右邊
        if (play) {
            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0){
                ballXdir = -ballXdir;
            }
            if (ballposY < 0){
                ballYdir = -ballYdir;
            }
            if (ballposX > 678){
                ballXdir = -ballXdir;
            }

        }
        repaint();

    }
// keyTyped() 鍵盤按鍵按住與釋放間的事件處理
    @Override
    public void keyTyped(KeyEvent e) {

    }
// keyPressed() 鍵盤按鍵按住的事件處理
    // 設定按住左右鍵的時候更改playerX的位置(配合moveRrght()跟moveLeft())
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 575) {
                playerX = 575;
            } else {
                moveRrght();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if (playerX <= 10){
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if (!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(totalRow, totalCol);

                repaint();
            }
        }

    }
// keyReleased() 鍵盤按鍵釋放的事件處理
    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void moveRrght(){
        play = true;
        playerX += 20;
    }

    public void moveLeft(){
        play = true;
        playerX -= 20;
    }
}
