import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Frame extends JFrame {
    ArrayList<String> cheatList =new ArrayList<>();
    Panel panel=new Panel();

    Frame(){

        ImageIcon background=new ImageIcon("backImage.jpeg");
        Image img=background.getImage();
        Image temp=img.getScaledInstance(800,500,Image.SCALE_SMOOTH);

        JLabel back=new JLabel(background);
        JLabel bottom=new JLabel();
        JLabel title= new JLabel("WELCOME TO THE DUNGEON");

        JMenuBar mb=new JMenuBar();
        JMenu file, help;
        file=new JMenu("File");
        help=new JMenu("Help");

        JMenuItem save=new JMenuItem("Save Game");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter myWriter = new FileWriter("saveGame.txt");
                    myWriter.write(String.valueOf(panel.currentPos));
                    myWriter.close();
                    JOptionPane.showMessageDialog(new JFrame(),"Game saved successfully");
                } catch (IOException ex) {
                    System.out.println("An error occurred.");
                    ex.printStackTrace();
                }
            }
        });
        JMenuItem load=new JMenuItem("Load Game");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File myObj = new File("saveGame.txt");
                    Scanner myReader = new Scanner(myObj);
                    String data=null;
                    while (myReader.hasNextLine()) {
                        data = myReader.nextLine();
                    }
                    if(!data.isEmpty()){
                        String input=JOptionPane.showInputDialog("Are you sure you want to load the save game? \nCurrent position: "+panel.currentPos+"\nPosition in save game: "+data+"\n[Y/N]");
                        if(input.equalsIgnoreCase("y")){
                            panel.buttons[panel.currentPos].setText("");
                            panel.currentPos=Integer.parseInt(data);
                            panel.buttons[panel.currentPos].setText("P");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(new JFrame(),"No save file found!");
                    }
                    myReader.close();
                } catch (FileNotFoundException ex) {
                    System.out.println("An error occurred.");
                    ex.printStackTrace();
                }
            }
        });
        JMenuItem exit=new JMenuItem("Exit Game");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        file.add(save);
        file.add(load);
        file.add(exit);

        JMenuItem about=new JMenuItem("About");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new JFrame(),"Dungeon Game v1.0");
            }
        });
        JMenuItem instructions=new JMenuItem("Instructions");
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new JFrame(),"Use arrow keys to move. \nEach room has some doors.\nTo pass through a door, you have to answer the question asked. \nCorrect answer will move you to the next room but the wrong answer will lock the door forever. \nReach the end point E to win the game. \nYou lose if there is no way out of a room.");
            }
        });
        JMenuItem cheats=new JMenuItem("Cheats");
        cheats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateCheats();
                Random r=new Random();
                JOptionPane.showMessageDialog(new JFrame(),cheatList.get(r.nextInt(13)));
            }
        });
        help.add(about);
        help.add(instructions);
        help.add(cheats);

        mb.add(file);
        mb.add(help);
        setJMenuBar(mb);
        setSize(800,500);

        background=new ImageIcon(temp);


        back.setLayout(new BorderLayout());
        back.setBounds(0, 0, 800, 500);
        back.add(panel,BorderLayout.CENTER);
        back.add(new JLabel("                                "),BorderLayout.EAST);
        back.add(new JLabel("                                "),BorderLayout.WEST);
        back.add(bottom,BorderLayout.SOUTH);
        back.add(title,BorderLayout.NORTH);

        title.setBorder(new LineBorder(Color.green));


        add(back);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        bottom.setText(panel.buttons[panel.currentPos].toString());
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                panel.actionListener(e);
                bottom.setText(panel.buttons[panel.currentPos].toString());
            }

        });
    }

    public void generateCheats(){
        ArrayList<Question> questions= panel.questions;
        for(Question q:questions){
            cheatList.add(q.toString());
        }
    }

    public static void main(String[]args){
        Frame frame=new Frame();
    }
}
