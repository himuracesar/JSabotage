/*******************************************************************************
 *                        BombToBaseCollision.java                             *
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

public class BombToBaseCollision extends BasicCollisionGroup
{
    private SabotageGameObject parent;

    private GenerateEnemies ge;

    public BombToBaseCollision(SabotageGameObject sgo)
    {
        pixelPerfectCollision = true;

        parent = sgo;

        ge = GenerateEnemies.getGenerateEnemiesInstance();
    }

    public void collided(Sprite bomb, Sprite base)
    {
        BufferedImage[] images = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion3.png", 4, 4);
        VolatileSprite explosion = new VolatileSprite(images, bomb.getX(), bomb.getY());

        //System.out.println("BOMB:: " + bomb.getX() + ", "  + bomb.getY());

        if(bomb.getX() >= 370 && bomb.getX() <= 430)
        {
            BufferedImage[] images_cannon = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion2.png", 4, 4);
            VolatileSprite explosion_cannon = new VolatileSprite(images_cannon, ge.getCannon().getX(), ge.getCannon().getY());

            parent.EXPLOSION.add(explosion_cannon);

            ge.getCannon().setActive(false);
        }

        bomb.setActive(false);

        parent.EXPLOSION.add(explosion);
    }
}