/*******************************************************************************
 *                       BombToFloorCollision.java                             *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.collision;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGameObject;

import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class BombToFloorCollision extends BasicCollisionGroup
{
    private SabotageGameObject parent;

    public BombToFloorCollision(SabotageGameObject sgo)
    {
        pixelPerfectCollision = true;

        parent = sgo;
    }

    public void collided(Sprite bomb, Sprite floor)
    {
        BufferedImage[] images = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion3.png", 4, 4);
        VolatileSprite explosion = new VolatileSprite(images, bomb.getX(), bomb.getY());

        bomb.setActive(false);

        parent.EXPLOSION.add(explosion);
    }
}