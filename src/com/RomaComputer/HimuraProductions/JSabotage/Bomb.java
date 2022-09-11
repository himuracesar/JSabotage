/*******************************************************************************
 *                               Bomb.java                                     *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage;

import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.sprite.AdvanceSprite;

public class Bomb extends AdvanceSprite
{
    private double g = 0.3;
    private double Vx = 0.135;
    private double Vy = 0.015;

    private int t = 0;

    private Timer timer = new Timer(1000);

    /**
     * Constructor para crear una bomba
     * @param sgo SabotageGameObject
     * @param x Coordenada en x donde aparecera la bomba
     * @param y Coordenada en y donde aparecera la bomba
     * @param signVx Indica si el avion sale por la derecha o izquiera y altera solo con el signo
     * la velocidad en X de la bomba
     */
    public Bomb(SabotageGameObject sgo, double x, double y, int signVx)
    {
        super(sgo.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/bomba.png", 5, 2), x, y);

        Vx = Vx * signVx;

        this.setSpeed(Vx, Vy);
    }

    /**
     * Actualiza la logica de la bomba
     * @param elapsedTime Tiempo transcurrido
     */
    public void update(long elapsedTime)
    {
        super.update(elapsedTime);

        setAnimationFrame(2, 9);
        setAnimate(true);
        setLoopAnim(true);

        if(timer.action(elapsedTime))
            t++;

        if(t > 0)
        {
            Vy = g * t;

            setSpeed(Vx, Vy);
        }

        if(!isOnScreen())
        {
            setActive(false);
        }
    }
}