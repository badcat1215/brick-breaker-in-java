package brickBreaker;

import java.awt.*;

public class MapGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;
    public MapGenerator(int row, int col){
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                // 用1表示存在 0表示消失
                map[i][j] = 1;
            }
        }

        brickWidth = 540/col;
        brickHeight = 150/row;
    }
    public void draw(Graphics2D g){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j] == 1) {
                    g.setColor(Color.CYAN);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    // 設定筆畫粗細
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }

    }
    public void setBrickValue(int value, int row, int col){
        map[row][col] = value;
    }
}