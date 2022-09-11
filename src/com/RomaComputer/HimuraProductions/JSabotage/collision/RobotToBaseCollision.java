/*******************************************************************************
 *                        RobotToBaseCollision.java                            *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.collision;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGameObject;
import com.RomaComputer.HimuraProductions.JSabotage.Robot;
import com.RomaComputer.HimuraProductions.JSabotage.GenerateEnemies;
import com.RomaComputer.HimuraProductions.JSabotage.Cannon;

import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.sprite.AdvanceSprite;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class RobotToBaseCollision extends BasicCollisionGroup
{
    private SabotageGameObject parent;

    private GenerateEnemies ge;

    public RobotToBaseCollision(SabotageGameObject sgo)
    {
        pixelPerfectCollision = true;

        parent = sgo;

        ge = GenerateEnemies.getGenerateEnemiesInstance();
    }

    public void collided(Sprite robot, Sprite base)
    {
        Robot r = (Robot)robot;

        if(robot.getVerticalSpeed() > 0.04)
        {
            BufferedImage[] images = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion.png", 7, 1);
            VolatileSprite explosion = new VolatileSprite(images, robot.getX(), robot.getY());

            ge.addToScore(2);

            robot.setActive(false);

            parent.EXPLOSION.add(explosion);

            ge.decrementEnemies_Moves();

            parent.playSound("com/RomaComputer/HimuraProductions/JSabotage/sounds/paratrooper_destroy.wav");
        }

        //****** DETECTA COLISION POR LA IZQUIERDA *****
        if(robot.getVerticalSpeed() == 0 && robot.getX() < SabotageGameObject.SCREEN_WIDTH/2)
        {
            r.setSpeed(0, 0);
            r.setX(r.getX()-2);
            r.setWalk(false);
            r.setWalked(true);

            if(!ge.isPositionFormationLeft(0))
            {
                ge.setPositionFormationLeft(0);

                System.out.println("Posicion 0 ocupada --> x:: " + robot.getX() + " y:: " + robot.getY());
            }
        }

        //****** DETECTA COLISION POR LA DERECHA *****
        if(robot.getVerticalSpeed() == 0 && robot.getX() > SabotageGameObject.SCREEN_WIDTH/2)
        {
            r.setSpeed(0, 0);
            r.setX(r.getX()+2);
            r.setWalk(false);
            r.setWalked(true);

            if(!ge.isPositionFormationRight(0))
            {
                ge.setPositionFormationRight(0);

                System.out.println("Posicion 0 ocupada --> x:: " + robot.getX() + " y:: " + robot.getY());
            }
        }

        if(r.getHorizontalSpeed() == 0 && r.getVerticalSpeed() < 0.05 && !r.getWalked())
        {
            robot.setSpeed(0, 0);
            robot.setY(robot.getY()-2);
            //r.setWalk(true);

            //******* EL CAÑON DEBE SER DESTRUIDO *******
            Cannon c = ge.getCannon();
            c.setActive(false);
            BufferedImage[] images = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion2.png", 4, 4);
            VolatileSprite explosion = new VolatileSprite(images, c.getX(), c.getY());

            parent.EXPLOSION.add(explosion);

            parent.playSound("com/RomaComputer/HimuraProductions/JSabotage/sounds/cannon_destroy.wav");

            System.out.println("*** SE DETIENE EN LA BASE ***");
        }

        if(r.getJumps() == 2)
        {
            r.setSpeed(0, 0);
            r.setY(r.getY()-2);
            r.setPriority(100);
            //r.setWalk(false);
            //r.setWalked(true);

            //******* EL CAÑON DEBE SER DESTRUIDO *******
            Cannon c = ge.getCannon();
            c.setActive(false);
            BufferedImage[] images = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion2.png", 4, 4);
            VolatileSprite explosion = new VolatileSprite(images, c.getX(), c.getY());

            parent.EXPLOSION.add(explosion);

            parent.playSound("com/RomaComputer/HimuraProductions/JSabotage/sounds/cannon_destroy.wav");

            System.out.println("*** SE DETIENE EN LA BASE DESPUES DE DOS SALTOS ***");
        }
    }
}