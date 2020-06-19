package brickBreaker;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Brick Bracker");
        //是否可由使用者調整視窗大小
        obj.setResizable(false);
        //顯示視窗可見, 沒有該語句視窗將不可見, 就沒有GUI的意義了
        obj.setVisible(true);
        //定義使用者點擊視窗的關閉按鈕時要甚麼甚麼動作
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        obj.add(gameplay);

    }
}
