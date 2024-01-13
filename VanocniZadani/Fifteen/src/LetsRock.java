import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * This Class handles the game
 */
public class LetsRock implements ActionListener {
    JFrame jf;
    JPanel basePlate;
    JPanel title = new JPanel();
    JLabel [][] picOnB;
    JLabel txt = new JLabel();
    JButton [][] button;
    int [][] numbers;
    private int pictures;
    private String file;

    /**
     * Constructor
     */
    public LetsRock(){
        numbers = new int[4][4];
        fileGen();
        startUi();
    }

    /**
     * This method generates and mixes all the squares
     */
    public void mixNumbers(){
        Random rd = new Random();
        int [] number = new int[16];
        for(int i=0;i<16;i++){
            number[i] = i+1;
        }
        number[15] = 1618;
        for(int i=0;i<16;i++){
            int accN =  rd.nextInt(16);
            int tempN = number[i];
            number[i] = number[accN];
            number[accN] = tempN;
        }
        int temp = 0;
        for(int i=0;i<4;i++){
            for(int a=0;a<4;a++){
                numbers[i][a] = number[temp];
                temp += 1;
            }
        }
    }

    /**
     * This method takes care of all the visuals
     */
    public void startUi(){
        jf = new JFrame("Puzzle Game Fifteen");
        basePlate = new JPanel();
        basePlate.setBackground(Color.BLACK);
        basePlate.setLayout(new GridLayout(4,4));
        button = new JButton[4][4];
        picOnB  = new JLabel[4][4];
        jf.add(basePlate);
        jf.setSize(830,950);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mixNumbers();
        switch (pictures) {
            case 0 -> {
                txt.setBackground(new Color(16, 24, 32));
                txt.setForeground(new Color(254, 231, 21));
            }
            case 1 -> {
                txt.setBackground(new Color(33, 40, 69));
                txt.setForeground(new Color(104, 18, 24));
            }
            case 2 -> {
                txt.setBackground(new Color(1, 81, 55));
                txt.setForeground(new Color(255, 250, 160));
            }
        }
        for(int i=0;i<4;i++){
            for(int a=0;a<4;a++){
                String identifier = i+","+a;
                button[i][a] = new JButton();
                button[i][a].setText(identifier);
                button[i][a].setFont(new Font("TimesRoman",Font.PLAIN,0));
                button[i][a].addActionListener(this);
                int numb = numbers[i][a];
                if(numb!=1618){
                    picOnB[i][a] = new JLabel(new ImageIcon(file + numb + ".png"));
                }else{
                    picOnB[i][a] = new JLabel(new ImageIcon(file + 16 + ".png"));
                }
                button[i][a].add(picOnB[i][a]);
                button[i][a].setBorder(BorderFactory.createLineBorder(Color.black,2));
                button[i][a].setBackground(Color.BLACK);
                basePlate.add(button[i][a]);
            }
        }
        txt.setFont(new Font("Man...",Font.BOLD,75));
        txt.setHorizontalAlignment(JLabel.CENTER);
        txt.setText("Puzzle game fifteen");
        txt.setOpaque(true);
        title.setLayout(new BorderLayout());
        title.setBounds(0,0,830,100);
        title.add(txt);
        jf.add(title,BorderLayout.NORTH);
        jf.setVisible(true);
    }

    /**
     * This method lets the player know if he won
     */
    public void winWindowPopUp(){
        JLabel WPic = new JLabel(new ImageIcon(file+"ws.png"));
        JFrame WFrame = new JFrame("Puzzle Solved!");
        WFrame.add(WPic);
        WFrame.setSize(571,429);
        WFrame.setLocationRelativeTo(null);
        WFrame.setLayout(new GridLayout(1,1));
        WFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WFrame.setVisible(true);
    }

    /**
     * This method randomises the games picture
     */
    public void fileGen(){
        Random rn = new Random();
        pictures = rn.nextInt(3);
        if (pictures==0){
            file = "pictures1/";
        }else if (pictures==1){
            file = "pictures2/";
        }else{
            file = "pictures3/";
        }
    }

    /**
     * This method checks if the player won
     */
    Boolean won(){
        int temp = 1;
        for(int i=0;i<4;i++){
            for(int a=0;a<4;a++){
                if(numbers[i][a]!=temp && numbers[i][a]!=1618){
                    return false;
                }
                temp += 1;
            }
        }
        return true;
    }



    /**
     * This method moves with the squares
     * @param e gets the squares cords
     */
    @Override
    public void actionPerformed(ActionEvent e){
        Boolean victory = won();
        if(!victory){
            String cords = e.getActionCommand();
            int p = Integer.parseInt(cords.split(",")[0]);
            int o = Integer.parseInt(cords.split(",")[1]);
            if(numbers[p][o]!=-1){
                if(p-1>=0 && numbers[p-1][o]==1618){
                    picOnB[p][o].setIcon(new ImageIcon(file + 16 + ".png"));
                    picOnB[p-1][o].setIcon(new ImageIcon(file + numbers[p][o] + ".png"));
                    int tempor = numbers[p][o];
                    numbers[p][o] = numbers[p-1][o];
                    numbers[p-1][o] = tempor;
                }else if(o-1>=0 && numbers[p][o-1]==1618){
                    picOnB[p][o].setIcon(new ImageIcon(file + 16 + ".png"));
                    picOnB[p][o-1].setIcon(new ImageIcon(file + numbers[p][o] + ".png"));
                    int tempor = numbers[p][o];
                    numbers[p][o] = numbers[p][o-1];
                    numbers[p][o-1] = tempor;
                }else if(p+1<4 && numbers[p+1][o]==1618){
                        picOnB[p][o].setIcon(new ImageIcon(file + 16 + ".png"));
                        picOnB[p+1][o].setIcon(new ImageIcon(file + numbers[p][o] + ".png"));
                        int tempor = numbers[p][o];
                        numbers[p][o] = numbers[p+1][o];
                        numbers[p+1][o] = tempor;
                }else if(o+1<4 && numbers[p][o+1]==1618){
                        picOnB[p][o].setIcon(new ImageIcon(file + 16 + ".png"));
                        picOnB[p][o+1].setIcon(new ImageIcon(file + numbers[p][o] + ".png"));
                        int tempor = numbers[p][o];
                        numbers[p][o] = numbers[p][o+1];
                        numbers[p][o+1] = tempor;
                }
            }
            victory = won();
            if(victory){
                winWindowPopUp();
            }
        }
    }
}