import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;

public class Mouse implements MouseListener, MouseMotionListener
{
    private static int mouseX = -1;
    private static int mouseY = -1;
    private static int mouseB = -1;

    private int xPrev = -1;
    private int yPrev = -1;
    private static double mouseSensitivity = 0.005;
    private static Meteor m;
    private boolean inZone;
    public static int getX()
    {
        return mouseX;
    }

    public static int getY()
    {
        return mouseY;
    }

    public static int getButton()
    {
        return mouseB;
    }

    public void mouseReleased(MouseEvent e)
    {
        mouseB = e.getButton();
        
        inZone = false;
    }

    public void mousePressed(MouseEvent e)
    {
        mouseB = -1;
        xPrev = e.getX();
        yPrev = e.getY();
        if(e.getX() > GameRunner.getWidthh()*4/5){
            m = new Meteor(xPrev-6 ,yPrev-6);
            GameRunner.meteors.add(m);
            inZone = true;
            GameRunner.meteorCount++;
        }
    }

    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if(inZone){
            double xDist = xPrev - mouseX;
            double yDist = yPrev - mouseY;
            double d = m.distance(xDist, yDist);
            double theta = 0;
            theta = Math.atan(yDist/xDist);
            double xS = mouseSensitivity *d* Math.cos(theta);
            double yS = mouseSensitivity *d* Math.sin(theta);
            if(xDist > 0)m.setSpeed(xS, yS);
            else if(xDist < 0)m.setSpeed(-xS, -yS);
            else m.setSpeed(0, yS);
        }

    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseClicked(MouseEvent e)
    {
        
    }

    public void mouseEntered(MouseEvent e)
    {

    }

    public void mouseExited(MouseEvent e)
    {
    }

}
