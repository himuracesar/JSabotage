/*******************************************************************************
 *                              Airplane.java                                  *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage;

import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.sprite.AdvanceSprite;

public class Airplane extends AdvanceSprite
{
    private double speedX = 0.15;

    private int track = 0;

    private GenerateEnemies ge;

    private SabotageGameObject parent;

    private Timer timerBomb = new Timer(500);

    /**
     * Constructor para crear un avion
     * @param sgo SabotageGameObject
     * @param x Coordenada en x donde aparecera el avion
     * @param y Coordenada en y donde aparecera el avion
     * @param track La pista por donde volara el avion
     */
    public Airplane(SabotageGameObject sgo, int x, int y, int track)
    {
        super(sgo.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/plane.png", 2, 2), x, y);
        //System.out.println("airplane x:: " + x + " y:: " + y + " track:: " + track);

        this.track = track;

        if(track == 0)
        {
            setAnimationFrame(0, 1);
        }
        else
        {
            speedX = -speedX;
            setAnimationFrame(2, 3);
        }

        setAnimate(true);
        setLoopAnim(true);
        this.setSpeed(speedX, 0);

        parent = sgo;

        ge = GenerateEnemies.getGenerateEnemiesInstance();
    }

    /**
     * Actualiza la logica del avion
     * @param elapsedTime El tiempo transcurrido
     */
    public void update(long elapsedTime)
    {
        super.update(elapsedTime);
        
        if(timerBomb.action(elapsedTime))
        {
            if(speedX > 0 && SabotageGameObject.SCREEN_WIDTH/2 > getX())
                launchBomb();
            if(speedX < 0 && SabotageGameObject.SCREEN_WIDTH/2 < getX())
                launchBomb();
        }

        if(!isOnScreen())
        {
            setActive(false);
            ge.setFreeTrack(track);
            ge.decrementEnemies_Moves();
        }
    }

    /**
     * Obtiene la pista por donde cirula el avion
     * @return
     */
    public int getTrack()
    {
        return track;
    }

    /**
     * Lanza un bomba
     */
    public void launchBomb()
    {
        Bomb bomb;

        if(track == 0)
            bomb = new Bomb(parent, this.getX() + 20, this.getY() + 20, 1);
        else
            bomb = new Bomb(parent, this.getX() + 50, this.getY() + 20, -1);

        parent.BOMB.add(bomb);
    }
}