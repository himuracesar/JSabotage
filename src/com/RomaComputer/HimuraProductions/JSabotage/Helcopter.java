/*******************************************************************************
 *                             Helcopter.java                                  *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage;

import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.sprite.AdvanceSprite;

public class Helcopter extends AdvanceSprite
{
    private SabotageGameObject parent;

    private GenerateEnemies ge;

    private double speedX = 0.11;

    private int track = 0;

    private Timer timerRobot =  new Timer(1300);

    public Helcopter(SabotageGameObject sgo, int x, int y, int track)
    {
        super(sgo.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/helicoptero.png", 2, 2), x, y);
        //System.out.println("x:: " + x + " y:: " + y + " track:: " + track);

        parent = sgo;

        //parent.bsSound.setLoop(true);
        //parent.playSound("com/RomaComputer/HimuraProductions/JSabotage/sounds/helicopter.wav");

        do{
            speedX = Math.random() * 19 / 100;
        }while(speedX < 0.11 || speedX > 0.18);
        
        this.track = track;

        if(track <= 3)
        {
            setAnimationFrame(2, 3);
        }
        else
        {
            speedX = -speedX;
            setAnimationFrame(0, 1);
        }

        ge = GenerateEnemies.getGenerateEnemiesInstance();

        setAnimate(true);
        setLoopAnim(true);

        this.setSpeed(speedX, 0);
    }

    /**
     * Actualiza la logica del helicoptero
     * @param elapsedTime Tiempo transcurrido
     */
    public void update(long elapsedTime)
    {
        super.update(elapsedTime);

        if(timerRobot.action(elapsedTime))
        {
            launchRobot();
            
            timerRobot = new Timer((int)(Math.random()*5000));
        }

        if(!isOnScreen())
        {
            setActive(false);
            ge.setFreeTrack(track);
            ge.decrementEnemies_Moves();
        }
    }

    /**
     * Obtiene la pista por donde cirula el helicoptero
     * @return
     */
    public int getTrack()
    {
        return track;
    }

    /**
     * Lanza un robot
     */
    private void launchRobot()
    {
        if(getX()+getWidth() < SabotageGameObject.SCREEN_WIDTH && getX() > 20)
        {
            Robot robot = new Robot(parent, this.getX() + 50, this.getY() + 20);
            //System.out.println("R:: " + robot.getX() + ", " + robot.getY());
            ge.incrementEnemies_Moves();

            parent.ROBOT.add(robot);
        }
    }

    public void stopSound()
    {
        parent.bsSound.setLoop(false);
    }
}
