import java.awt.Graphics;
import java.awt.Color;
import java.awt.Canvas;

public class Planet extends Canvas
{
    private double xPos;
    private double yPos;
    private int radius;
    private Color color;
    private boolean target;
    public Planet()
    {
        xPos = 0;
        yPos = 0;
        radius = 0;
        color = new Color(50, 50, 50);
        target = false;
    }

    public Planet(double x, double y, int r) 
    {
        setVisible(true);
        xPos = x;
        yPos = y;
        radius = r;
        color = new Color(50, 50, 50);
        target = false;
    }

    public Planet(double x, double y, int r, Color color, boolean targ) 
    {
        setVisible(true);
        xPos = x;
        yPos = y;
        radius = r;
        this.color = color;
        target = targ;
    }
    
    public Planet(double x, double y, int r, Color color) 
    {
        setVisible(true);
        xPos = x;
        yPos = y;
        radius = r;
        this.color = color;
    }

    public boolean isTarg(){
        return target;
    }
    
    public double getXPos()
    {
        return xPos;
    }

    public void setColor(Color col)
    {
        color = col;
    }

    public double getYPos()
    {
        return yPos;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setRadius(int r)
    {
        radius = r;
    }

    public int type()
    {
        return 0;
    }

    public void setPos(double x, double y)
    {
        xPos = x;
        yPos = y; 
    }

    public void paint(Graphics window)
    {
        window.setColor(color);

        window.fillOval((int)xPos, (int)yPos, radius*2, radius*2);

    }
}