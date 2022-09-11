/*******************************************************************************
 *                  ExplosionAirplaneToBombCollision.java                      *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.collision;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGameObject;
import com.RomaComputer.HimuraProductions.JSabotage.GenerateEnemies;

import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class ExplosionAirplaneToBombCollision extends BasicCollisionGroup
{
    private SabotageGameObject parent;

    private GenerateEnemies ge;

    public ExplosionAirplaneToBombCollision(SabotageGameObject sgo)
    {
        pixelPerfectCollision = true;

        parent = sgo;

        ge = GenerateEnemies.getGenerateEnemiesInstance();
    }

    public void collided(Sprite explosion, Sprite bomb)
    {
        BufferedImage[] images = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion3.png", 4, 4);
        VolatileSprite exp = new VolatileSprite(images, bomb.getX(), bomb.getY());

        ge.addToScore(25);

        bomb.setActive(false);

        parent.EXPLOSION.add(exp);
    }
}