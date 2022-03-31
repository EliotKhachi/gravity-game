import java.awt.Graphics;
import java.awt.Color;
import java.awt.Canvas;
import java.lang.Math.*;
public class Meteor extends Planet
{
    private double xSpeed;
    private double ySpeed;
    private double xAcc;
    private double yAcc;
    private boolean ghost;
    private static int howbig = 6;
    private static final double G = 0.005;
    public Meteor(double x, double y, int r)
    {
        super(x, y, r);
        xSpeed = 0;
        ySpeed = 0;
        xAcc = 0;
        yAcc = 0;
        ghost = false;
    }

    public Meteor(double x, double y)
    {
        setPos(x,y);
        setRadius(howbig);
        xSpeed = 0;
        ySpeed = 0;
        xAcc = 0;
        yAcc = 0;
        ghost = false;
    }

    public Meteor(double x, double y, double xs, double ys)
    {
        this(x, y);
        this.setRadius(howbig);
        xSpeed = xs;
        ySpeed = ys;
        xAcc = 0;
        yAcc = 0;
        ghost = false;
    }
    
    public Meteor(double x, double y, double xs, double ys, boolean g)
    {
        this(x, y);
        this.setRadius(howbig);
        xSpeed = xs;
        ySpeed = ys;
        xAcc = 0;
        yAcc = 0;
        ghost = g;
    }

    public void setSpeed(double x, double y)
    {
        xSpeed = x;
        ySpeed = y;
    }

    public void setAcc(double x, double y)
    {
        xAcc = x;
        yAcc = y;
    }

    public double distance(double x, double y)
    {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y,2));
    }
    
    public void CalcForce(Planet p)
    {
        double force, tempXAcc = 0, tempYAcc = 0;
        double xDist = (p.getXPos() + p.getRadius()) - this.getXPos() - this.getRadius();
        double yDist = (p.getYPos() + p.getRadius()) - this.getYPos() - this.getRadius();    
        force = G * Math.pow(p.getRadius(),2) / Math.pow(distance(xDist, yDist), 2);
        double theta = Math.atan(yDist/xDist);
        if(xDist > 0)
        {
                tempXAcc += force * Math.cos(theta);
                tempYAcc += force * Math.sin(theta);
        }
        else{
                tempXAcc -= force * Math.cos(theta);
                tempYAcc -= force * Math.sin(theta);
        }
        setAcc(xAcc + tempXAcc, yAcc + tempYAcc);
    }

    public void CalcForces(Syst p)
    {
        for(int i = 0; i < p.getSyst().size(); i++)
        {
            CalcForce(p.getSyst().get(i));
        }
    }

    public boolean isGhost(){
        return ghost;
    }
    
    public boolean hitWall(){
        return getXPos() <=0 ||getXPos() + howbig*2 + 5 >= GameRunner.getWidthh();
    }

    public boolean hitCeilingOrFloor(){
        return getYPos() <= 0 || getYPos() + howbig*2 + 26>= GameRunner.getHeightt(); 
    }

    public boolean inPlanet(Planet p)
    {
        double r = getRadius();
        double centerX = getXPos() + r;
        double centerY = getYPos() + r;
        double r2 = p.getRadius();
        double x2 = (int)(Math.pow(p.getXPos() + r2 - centerX, 2));
        double y2 = (int)(Math.pow(p.getYPos() + r2 - centerY, 2));
        double d = (int)Math.sqrt(x2 + y2);
        
        if(!ghost && p.isTarg() && d + 3 < r + r2){GameRunner.nextLevel = true;}
        if(d + 1< r + r2) setSpeed(0,0);
        if(d + getRadius()*2 < r + r2){ 
            return true;
        }

        return false;
    }

    public boolean inSyst(Syst p)
    {
        for(int i = 0 ; i < p.getSyst().size(); i++)
        {
            if(inPlanet(p.getSyst().get(i)))return true;
        }
        return false;
    }

    public void drawPath(Syst p,Graphics g)
    {
        //draw line from current pos to next pos
        g.setColor(Color.WHITE);
        Meteor ghost = new Meteor(getXPos(), getYPos(), xSpeed, ySpeed, true);
        for(int i = 0; i <250 ; i++){
            if(i%10 ==0)g.fillOval((int)(ghost.getXPos()+2), (int)(ghost.getYPos()+2), 3, 3);
            ghost.update(p);
        }
    }

    public void update(Syst p)
    {
        //introduce forces
        CalcForces(p);
        setSpeed(xSpeed + xAcc, ySpeed + yAcc);
        setPos((getXPos() + xSpeed), (getYPos() + ySpeed));
        if(inSyst(p)) 
            GameRunner.meteors.remove(this);
        if(hitWall())setSpeed(-xSpeed, ySpeed);
        if(hitCeilingOrFloor())setSpeed(xSpeed, -ySpeed);
        setAcc(0, 0);
    }

    public void paint(Graphics window)
    {
        window.setColor(Color.RED);

        window.fillOval((int)getXPos(), (int)getYPos(), (int)getRadius()*2, (int)getRadius()*2);
    }
}