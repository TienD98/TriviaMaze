import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import static java.awt.event.KeyEvent.*;

public class Panel extends JPanel {

    Room buttons[];
    ArrayList<Question> questions;
    int currentPos;
    int endPosition;
    Panel(){
        currentPos=0;
        endPosition=99;
        questions=new ArrayList<>();
        setLayout(new GridLayout(10,1));
        buttons=new Room[100];

        Random r=new Random();
        for(int i=0;i<100;i++){

            buttons[i]=new Room(r.nextBoolean(),r.nextBoolean(),r.nextBoolean(),r.nextBoolean());
            buttons[i].setOpaque(false);
            add(buttons[i]);

        }
        buttons[0].east=true;
        buttons[0].south=true;
        buttons[currentPos].setText("P");
        buttons[endPosition].setText("E");

        setOpaque(false);
        connectToDB();
    }

    public void connectToDB(){
        Connection conn = null;
        try {
            // db parameters
         //   String url = "jdbc:sqlite:D:/Documents/Idea Projects/MAZE/ques.db";
           
         //	String url =  "jdbc:sqlite:C:/Users/bitda/OneDrive/Desktop/TCSS360/MAZE/ques.db";
         	String url = "jdbc:sqlite:ques.db";
            // create a connection to the database
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

            String query="Select*from Question";
            PreparedStatement pst=conn.prepareStatement(query);
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                String ques=rs.getString("Statement");
                String ans=rs.getString("Answer");
                int type=rs.getInt("Type");
                Question q=new Question(ques,ans,type);
                questions.add(q);
            }
            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    public void actionListener(KeyEvent e){
        Random rand=new Random();
        if(e.getKeyCode()==VK_UP && buttons[currentPos].north){
            if(currentPos-10>=0) {
                int r=rand.nextInt(13);
                String answer=JOptionPane.showInputDialog(questions.get(r).question);
                if(answer.equalsIgnoreCase(questions.get(r).answer)) {
                    buttons[currentPos].setText("");
                    currentPos -= 10;
                    buttons[currentPos].setText("P");
                }
                else{
                    JOptionPane.showMessageDialog(new JFrame(),"Incorrect answer!");
                    buttons[currentPos].north=false;
                }
            }
        }
        else if(e.getKeyCode()==VK_DOWN && buttons[currentPos].south){
            int r=rand.nextInt(13);
            String answer=JOptionPane.showInputDialog(questions.get(r).question);
            if(answer.equalsIgnoreCase(questions.get(r).answer)) {
                buttons[currentPos].setText("");
                currentPos += 10;
                buttons[currentPos].setText("P");
            }
            else{
                JOptionPane.showMessageDialog(new JFrame(),"Incorrect answer!");
                buttons[currentPos].south=false;
            }
        }
        else if(e.getKeyCode()==VK_LEFT && buttons[currentPos].west){
            int r=rand.nextInt(13);
            String answer=JOptionPane.showInputDialog(questions.get(r).question);
            if(answer.equalsIgnoreCase(questions.get(r).answer)) {
                buttons[currentPos].setText("");
                currentPos -= 1;
                buttons[currentPos].setText("P");
            }
            else{
                JOptionPane.showMessageDialog(new JFrame(),"Incorrect answer!");
                buttons[currentPos].west=false;
            }
        }
        else if(e.getKeyCode()==VK_RIGHT && buttons[currentPos].east){
            int r=rand.nextInt(13);
            String answer=JOptionPane.showInputDialog(questions.get(r).question);
            if(answer.equalsIgnoreCase(questions.get(r).answer)) {
                buttons[currentPos].setText("");
                currentPos += 1;
                buttons[currentPos].setText("P");
            }
            else{
                JOptionPane.showMessageDialog(new JFrame(),"Incorrect answer!");
                buttons[currentPos].east=false;
            }
        }
        if(currentPos==endPosition){
            JOptionPane.showMessageDialog(new JFrame(),"You Won!");
        }
        if(!buttons[currentPos].north && !buttons[currentPos].south && !buttons[currentPos].east && !buttons[currentPos].west){
            JOptionPane.showMessageDialog(new JFrame(),"You Lost!");
            System.exit(1);
        }
    }
}
