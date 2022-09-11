/*******************************************************************************
 *                     BulletToParachuteCollision.java                         *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.collision;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGameObject;

import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class BulletToParachuteCollision extends BasicCollisionGroup
{
    SabotageGameObject parent;

    public BulletToParachuteCollision(SabotageGameObject sgo)
    {
        pixelPerfectCollision = true;
        parent = sgo;
    }

    public void collided(Sprite bullet, Sprite parachute)
    {
        bullet.setActive(false);
        parachute.setActive(false);

        BufferedImage[] images = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/Explosion1.png", 5, 3);
        VolatileSprite explosion = new VolatileSprite(images, parachute.getX(), parachute.getY());

        parent.EXPLOSION.add(explosion);
    }
}