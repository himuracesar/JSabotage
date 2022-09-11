/*******************************************************************************
 *                     BulletToHelcopterCollision.java                         *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.collision;

import java.awt.image.BufferedImage;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGameObject;
import com.RomaComputer.HimuraProductions.JSabotage.GenerateEnemies;
import com.RomaComputer.HimuraProductions.JSabotage.Helcopter;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class BulletToHelcopterCollision extends BasicCollisionGroup
{
    private SabotageGameObject parent;

    private GenerateEnemies ge;

    private Helcopter helcopter;

    public BulletToHelcopterCollision(SabotageGameObject sgo)
    {
        pixelPerfectCollision = true;

        parent = sgo;

        ge = GenerateEnemies.getGenerateEnemiesInstance();
    }

    public void collided(Sprite bullet, Sprite helcopter)
    {
        this.helcopter = (Helcopter)helcopter;

        bullet.setActive(false);
        helcopter.setActive(false);
        //this.helcopter.stopSound();

        ge.setFreeTrack(this.helcopter.getTrack());
        ge.addToScore(5);
        ge.decrementEnemies_Moves();

        BufferedImage[] images = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion2.png", 4, 4);
        VolatileSprite explosion = new VolatileSprite(images, helcopter.getX(), helcopter.getY());

        parent.EXPLOSION_HELCOPTER.add(explosion);
        parent.playSound("com/RomaComputer/HimuraProductions/JSabotage/sounds/helicopter_destroy.wav");
    }
}