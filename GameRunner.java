import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.lang.Math;
import java.awt.Font;
//import java.awt.event.*;
//import static java.lang.Character.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
public class GameRunner extends JPanel implements KeyListener
{
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    Syst planets;
    public static ArrayList<Meteor> meteors;
    Mouse mouse = new Mouse();
    
    private int keyCode, numplanets;
    private boolean win, playAgain;
    static boolean nextLevel;
    static int meteorCount;
    
    public GameRunner()
    {
        planets = new Syst();
        meteors = new ArrayList<Meteor>();

        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        
       
        numplanets = 1;

        loadPlanets();

        win = false;
        nextLevel = false;

        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public void loadPlanets(){
        //add target
        int j = 0;
        while(j < 1){
            Planet target = new Planet((int)(Math.random()*WIDTH*1/5 + 20),
                    (int)(Math.random()*HEIGHT*5/6), 20,
                    new Color(150, 150, 0), true);
            if(planets.inBounds(target)) {planets.add(target); j++;}
        }
        //add Planets
        int i = 0;
        while(i < numplanets){
            Planet obj = new Planet((int)(Math.random()*WIDTH*3/5 + 20),
                    (int)(Math.random()*HEIGHT*2/3),
                    (int)(Math.random()*WIDTH/10 + 10));
            if(planets.inBounds(obj)){
                planets.add(obj);
                i++;
            }
        }
    }

    public static int getWidthh(){return WIDTH;}

    public static int getHeightt(){return HEIGHT;}

    public void paint(Graphics g)
    {
        super.paint(g);
        //background
        setBackground(new Color(10, 10, 40));

        if(nextLevel){
            planets.clear();
            meteors.clear();
            if(numplanets < 10)
            {
                numplanets++;
                loadPlanets();
            }
            else
                win = true;
            nextLevel = false;
        }

        if(win)
        {
            g.setColor(Color.RED);
            g.setFont(new Font("TAHOMA",Font.BOLD,90));
            g.drawString("YOU MADE IT!!!",300,300);
            g.setFont(new Font("TAHOMA",Font.BOLD,20));
            g.drawString("It only took you " + meteorCount + " meteors!",500,400);
            g.drawString("Press \"P\" to play again",530,500);
            if(playAgain){
                planets.clear();
                meteors.clear();
                numplanets = 1;
                loadPlanets();
                playAgain = false;
                win = false;
                meteorCount = 0;
            }
        }
        else 
        {
            ///update meteors
            if(meteors.size() != 0)
            {
                Meteor latest = meteors.get(meteors.size()-1);

                if(mouse.getButton() ==1 || latest.getXPos() <= WIDTH*4/5)latest.update(planets);
                else if(mouse.getButton()!=1)latest.drawPath(planets, g);
                latest.paint(g);
                for(int i = meteors.size()-2; i >= 0; i--)
                {
                    meteors.get(i).paint(g);
                    meteors.get(i).update(planets);
                }
            }

            g.setColor(Color.WHITE);
            g.drawLine(WIDTH*4/5,0,WIDTH*4/5,HEIGHT);
            //static planets
            planets.paint(g);
        }
    }
    
    
    public void keyTyped(KeyEvent e){
        if(e.getKeyChar() == 'p' || e.getKeyChar() == 'P'){
            playAgain = true;
        }
    }

    public void keyPressed(KeyEvent e){}

    public void keyReleased(KeyEvent e){
        keyCode = 0;
    }
    

    public static void main(String args[])
    {
        JFrame frame = new JFrame("GRAVITY");
        GameRunner game = new GameRunner();
        frame.add(game);
        frame.setVisible(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        while (true)
        {
            game.repaint();
        }
    }
}