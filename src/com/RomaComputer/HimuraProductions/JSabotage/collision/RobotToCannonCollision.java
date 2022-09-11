/*******************************************************************************
 *                      RobotToCannonCollision.java                            *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.collision;

import java.awt.image.BufferedImage;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGameObject;
import com.RomaComputer.HimuraProductions.JSabotage.Robot;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class RobotToCannonCollision extends BasicCollisionGroup
{
    private SabotageGameObject parent;

    public RobotToCannonCollision(SabotageGameObject sgo)
    {
        pixelPerfectCollision = false;

        parent = sgo;
    }

    public void collided(Sprite robot, Sprite cannon)
    {
        Robot r = (Robot)robot;
        r.setSpeed(0, 0);
        r.setAnimate(false);

        cannon.setActive(false);
        
        BufferedImage[] images = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion2.png", 4, 4);
        VolatileSprite explosion = new VolatileSprite(images, cannon.getX(), cannon.getY());

        parent.EXPLOSION.add(explosion);
    }
}
