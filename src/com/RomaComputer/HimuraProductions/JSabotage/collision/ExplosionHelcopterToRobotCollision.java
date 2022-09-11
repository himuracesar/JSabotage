/*******************************************************************************
 *                ExplosionHelcopterToRobotCollision.java                      *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.collision;

import java.awt.image.BufferedImage;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGameObject;
import com.RomaComputer.HimuraProductions.JSabotage.GenerateEnemies;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class ExplosionHelcopterToRobotCollision extends BasicCollisionGroup
{
    private SabotageGameObject parent;

    private GenerateEnemies ge;

    public ExplosionHelcopterToRobotCollision(SabotageGameObject sgo)
    {
        pixelPerfectCollision = true;

        parent = sgo;

        ge = GenerateEnemies.getGenerateEnemiesInstance();
    }

    public void collided(Sprite explosionHellcopter, Sprite robot)
    {
        robot.setActive(false);

        BufferedImage[] images = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion.png", 7, 1);
        VolatileSprite explosion = new VolatileSprite(images, robot.getX(), robot.getY());

        ge.addToScore(2);
        ge.decrementEnemies_Moves();//robot

        parent.EXPLOSION.add(explosion);
    }
}