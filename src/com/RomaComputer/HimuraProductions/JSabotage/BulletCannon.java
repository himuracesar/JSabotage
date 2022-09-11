/*******************************************************************************
 *                           BulletCannon.java                              *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage;

import com.golden.gamedev.object.sprite.AdvanceSprite;

public class BulletCannon extends AdvanceSprite
{

    public BulletCannon(SabotageGameObject parent, double x, double y, double sx, double sy)
    {
        super(parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/bullet.png", 1, 1), x, y);

        this.setSpeed(sx, sy);
    }

    public void update(long elapsedTime)
    {
        super.update(elapsedTime);
        if(!isOnScreen())
        {
            setActive(false);
        }
    }
}