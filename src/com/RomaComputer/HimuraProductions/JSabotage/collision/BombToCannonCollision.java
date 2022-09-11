/*******************************************************************************
 *                       BombToCannonCollision.java                            *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.collision;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGameObject;

import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class BombToCannonCollision extends BasicCollisionGroup
{
    private SabotageGameObject parent;

    public BombToCannonCollision(SabotageGameObject sgo)
    {
        pixelPerfectCollision = false;

        parent = sgo;
    }

    public void collided(Sprite bomb, Sprite cannon)
    {
        BufferedImage[] images = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion2.png", 4, 4);
        VolatileSprite explosion = new VolatileSprite(images, cannon.getX(), cannon.getY());

        bomb.setActive(false);
        cannon.setActive(false);

        parent.EXPLOSION.add(explosion);
    }
}