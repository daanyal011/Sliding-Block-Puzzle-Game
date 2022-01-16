package BlockPuzzle;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class MyFrame extends JFrame{
    int m,n;
    JButton [][] bt;
    JPanel p;

    public MyFrame(){
        super("Block Puzzle Game");

        m=4;
        n=4;
        bt = new JButton[4][4];
        p = new JPanel();
        p.setLayout(new GridLayout(4,4));

        Handler h = new Handler();
        puzzleLogic pl = new puzzleLogic();
        pl.shuffle();

        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JButton jb = new JButton();
                bt[i][j] = jb;
                bt[i][j].addActionListener(h);
                bt[i][j].setFont(new Font("NoteWorthy",Font.BOLD,30));
                if(pl.array[count]!=-1) {
                    bt[i][j].setBackground(Color.cyan);
                    bt[i][j].setText(""+pl.array[count]);
                }else {
                    bt[i][j].setBackground(Color.lightGray);
                    bt[i][j].setText("");
                    m=i; n=j;
                }
                count++;
                p.add(bt[i][j]);
            }
        }
        Border br = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,3),"THE FIFTEEN BLOCK PUZZLE", TitledBorder.CENTER,TitledBorder.CENTER);
        p.setBorder(br);
        add(p);
    }

    class Handler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton o = (JButton)e.getSource();

            for (int i = 0; i < 4 ; i++) {
                for (int j = 0; j < 4; j++) {
                    if(bt[i][j]==o) {
                        moves(i,j);
                        if(wins()){
                            WinMsg wm = new WinMsg();
                            wm.setSize(300,150);
                            wm.setVisible(true);
                            wm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            wm.setLocationRelativeTo(null);
                        }
                    }
                }
            }
        }
    }


    public boolean wins(){
        int count = 1;
        do{
            for (int i = 0; i < 4 ; i++) {
                for (int j = 0; j < 4; j++) {
                    if(i==3 && j<3){
                        System.out.println(count);
                        if(Integer.parseInt(bt[i][j].getText())!=count) {
                            return false;
                        }
                        int num=1;
                        for (int k = 0; k < 3; k++) {
                            for (int l = 0; l < 4; l++) {
                                if(Integer.parseInt(bt[k][l].getText())!=num) {
                                    return false;
                                }
                                num++;
                            }
                        }
                    }
                    count++;
                }
            }
        }while (count<16);
        return true;
    }

    public void moves(int i,int j){
        if((i+1==m && j==n) || (i-1==m && j==n) || (i==m && j+1==n) || (i==m && j-1==n)){
            bt[m][n].setText(bt[i][j].getText());
            bt[m][n].setBackground(Color.cyan);
            bt[i][j].setText("");
            bt[i][j].setBackground(Color.lightGray);
            m=i; n=j;
        }
    }
}

class WinMsg extends JFrame {
    JLabel l1;
    JLabel l2;
    public WinMsg(){//Constructor
        l1 = new JLabel("Congratulations!!!");
        l1.setFont(new Font("NoteWorthy",Font.BOLD,20));
        l2 = new JLabel("You have WON!");
        l2.setFont(new Font("NoteWorthy",Font.BOLD,20));
        setLayout(new FlowLayout());

        add(l1);
        add(l2);
    }
}

class puzzleLogic{
    Random randNum = new Random();
    int []array = new int[16];

    public void shuffle(){

        for (int i = 0; i < 16; i++) {
            array[i] = i+1;
        }
        array[15]=-1;

        do{
            for (int i = 0; i < 16; i++) {
                int randomIndex = randNum.nextInt(16);

                int temp = array[i];
                array[i] = array[randomIndex];
                array[randomIndex] = temp;
            }
        }while (!(isSolvable(array)));

        System.out.println("Count = "+getInvCount(array));
        System.out.println("\n");
        System.out.println("Position = "+getPos(array));
        System.out.println("\n");
        System.out.println("Solvable = "+isSolvable(array));
    }

    public int getInvCount(int []arr){
        int invCount=0;
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if(arr[i]==-1)
                    continue;
                if(arr[i]>arr[j] && arr[j]!=-1){
                    invCount++;
                }
            }
        }
        return invCount;
    }

    public boolean getPos(int []arr){

        return (arr[0] == -1 || arr[1] == -1 || arr[2] == -1 || arr[3] == -1 || arr[8] == -1 || arr[9] == -1 || arr[10] == -1 || arr[11] == -1);

    }

    public boolean isSolvable(int []arr){
        return ((getInvCount(arr) % 2 == 0 && !getPos(arr)) || (getInvCount(arr) % 2 != 0 && getPos(arr)));
    }
}

public class Puzzle {
    public static void main(String[] args) {
        MyFrame mf = new MyFrame();
        mf.setSize(500,500);
        mf.setVisible(true);
        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mf.setLocationRelativeTo(null);
        int []myArray={3,9,1,15,14,11,4,6,13,-1,10,12,2,7,8,5};
        puzzleLogic px= new puzzleLogic();
        System.out.println("MyArray Solvable = "+px.isSolvable(myArray));
        System.out.println("MyArray Inversion Count = "+px.getInvCount(myArray));
        System.out.println("MyArray Position = "+px.getPos(myArray));
    }
}
