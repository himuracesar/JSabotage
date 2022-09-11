/*******************************************************************************
 *                 ExplosionRobotToParachuteCollision.java                     *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.collision;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class ExplosionRobotToParachuteCollision extends BasicCollisionGroup
{
    public ExplosionRobotToParachuteCollision()
    {
        pixelPerfectCollision = true;
    }

    public void collided(Sprite explosion, Sprite parachute)
    {
       parachute.setActive(false);
    }
}