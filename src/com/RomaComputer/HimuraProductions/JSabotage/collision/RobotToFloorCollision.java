/*******************************************************************************
 *                       RobotToFloorCollision.java                            *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.collision;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGameObject;
import com.RomaComputer.HimuraProductions.JSabotage.GenerateEnemies;
import com.RomaComputer.HimuraProductions.JSabotage.Robot;

import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class RobotToFloorCollision extends BasicCollisionGroup
{
    private SabotageGameObject parent;

    private GenerateEnemies ge;

    public RobotToFloorCollision(SabotageGameObject sgo)
    {
        pixelPerfectCollision = true;

        parent = sgo;

        ge = GenerateEnemies.getGenerateEnemiesInstance();
    }

    public void collided(Sprite robot, Sprite floor)
    {
        //**** El robot va a alta velocidad, debe explotar *****
        if(robot.getVerticalSpeed() > Robot.SPEED_WITH_PARACHUTE)
        {
            BufferedImage[] images = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion.png", 7, 1);
            VolatileSprite explosion = new VolatileSprite(images, robot.getX(), robot.getY());

            ge.addToScore(2);
            ge.decrementEnemies_Moves();

            robot.setActive(false);

            parent.EXPLOSION.add(explosion);

            parent.playSound("com/RomaComputer/HimuraProductions/JSabotage/sounds/paratrooper_destroy.wav");
        }

        if(robot.getVerticalSpeed() > 0 && robot.getVerticalSpeed() == Robot.SPEED_WITH_PARACHUTE)
        {
             robot.setSpeed(0, 0);
             robot.setY(robot.getY()-2);

             ge.decrementEnemies_Moves();

             if(robot.getX() < SabotageGameObject.SCREEN_WIDTH/2)
             {
                Robot r = (Robot)robot;
                ge.addElementToTroopLeft(r);

                r.setID(ge.getIDRobot());

                /*if(ge.isTotalTroopLeft())
                {
                    ge.assingPrioritiesTroopLeft();
                }*/
             }
             else
             {
                 //**** Van para la tropa de la derecha ****
                 Robot r = (Robot)robot;
                 ge.addElementToTroopRight(r);

                 r.setID(ge.getIDRobot());

                 /*if(ge.isTotalTroopRight())
                 {
                     ge.assingPrioritiesTroopRight();
                 }*/
             }

             parent.playSound("com/RomaComputer/HimuraProductions/JSabotage/sounds/paratrooper.wav");

            System.out.println("*** STOP ***");
        }
    }
}