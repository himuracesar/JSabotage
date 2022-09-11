/*******************************************************************************
 *                             Parachute.java                                  *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage;

import com.RomaComputer.HimuraProductions.JSabotage.Robot;

import com.golden.gamedev.object.sprite.AdvanceSprite;

public class Parachute extends AdvanceSprite
{
    private boolean destroy = false;

    public Parachute(SabotageGameObject parent, double x, double y)
    {
        super(parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/paracaidas.GIF", 1, 1), x, y);

        this.setSpeed(0, Robot.SPEED_WITH_PARACHUTE);
    }

    public void update(long elapsedTime)
    {
        super.update(elapsedTime);

        if(!isOnScreen())
        {
            setActive(false);
            destroy = true;
        }
    }

    public boolean isDestroy()
    {
        return destroy;
    }
}