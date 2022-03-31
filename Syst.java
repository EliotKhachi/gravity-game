import java.awt.Graphics;
import java.awt.Color;
import java.awt.Canvas;
import java.lang.Math.*;
import java.util.ArrayList;
public class Syst extends Canvas
{
    private ArrayList<Planet> system;
    public Syst()
    {
        system = new ArrayList<Planet>();
    }

    public ArrayList<Planet> getSyst()
    {
        return system;
    }

    public void clear(){
        system.clear();
    }
    
    public void add(Planet p)
    {
        system.add(p);
    }

    public boolean inBounds(Planet p)
    {
        double r = p.getRadius();
        double centerX = p.getXPos() + r;
        double centerY = p.getYPos() + r;
        for(int i = 0; i < system.size(); i++)
        {
            double r2 = system.get(i).getRadius();
            double x2 = (int)(Math.pow(system.get(i).getXPos() + r2 - centerX, 2));
            double y2 = (int)(Math.pow(system.get(i).getYPos() + r2 - centerY, 2));
            double d = (int)Math.sqrt(x2 + y2);
            if(d - 25 < r + r2) return false;
        }
        return true;
    }
    

    public void paint(Graphics window)
    {
        for(int i = 0; i < system.size(); i++)
        {
            system.get(i).paint(window);
        }
    }
}