/*******************************************************************************
 *                       RobotToRobotCollision.java                            *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.collision;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGameObject;
import com.RomaComputer.HimuraProductions.JSabotage.Robot;
import com.RomaComputer.HimuraProductions.JSabotage.GenerateEnemies;

import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

public class RobotToRobotCollision extends BasicCollisionGroup
{
    private SabotageGameObject parent;

    private GenerateEnemies ge;

    public RobotToRobotCollision(SabotageGameObject sgo)
    {
        pixelPerfectCollision = true;

        parent = sgo;

        ge = GenerateEnemies.getGenerateEnemiesInstance();
    }

    public void collided(Sprite robotArriba, Sprite robotAbajo)
    {
        Robot robotUp = (Robot)robotArriba;
        Robot robotDown = (Robot)robotAbajo;
        
       //***** COLISION PARA DETENER AL ROBOT QUE CAE ENCIMA DE OTRO *****
       if(robotAbajo.getVerticalSpeed() == 0 && robotAbajo.getHorizontalSpeed() == 0 &&
          robotArriba.getVerticalSpeed() == Robot.SPEED_WITH_PARACHUTE &&
          robotArriba.getHorizontalSpeed() == 0)
       {
           robotUp.setSpeed(0 ,0);
           robotUp.setY(robotArriba.getY()-2);

           ge.decrementEnemies_Moves();

           Robot r = (Robot)robotArriba;

           if(r.getX() < SabotageGameObject.SCREEN_WIDTH/2)
               ge.addElementToTroopLeft(r);
           else
               ge.addElementToTroopRight(r);

           r.setID(ge.getIDRobot());

           /*if(ge.isTotalTroopLeft())
           {
               ge.assingPrioritiesTroopLeft();
           }

           if(ge.isTotalTroopRight())
           {
               ge.assingPrioritiesTroopRight();
           }*/

           System.out.println("*** ENCIMA DE OTRO ***");
       }

       //***** EL ROBOT COLISIONA CON OTRO DESPUES DE UN SALTO *****
       if(robotAbajo.getVerticalSpeed() == 0 && robotAbajo.getHorizontalSpeed() == 0 &&
          robotArriba.getVerticalSpeed() == 0.04 && (robotArriba.getHorizontalSpeed() == 0.02 ||
          robotArriba.getHorizontalSpeed() == -0.02))
       {
           robotUp.setSpeed(0 ,0);
           robotUp.setY(robotArriba.getY()-2);

           if(robotUp.getPriority() != 3)
           {
               robotUp.setWalk(false);
               robotUp.setWalked(true);

               if(robotUp.getX() < SabotageGameObject.SCREEN_WIDTH/2)
               {
                   if(ge.isPositionFormationLeft(0) && !ge.isPositionFormationLeft(1))
                   {
                       ge.setPositionFormationLeft(1);
                       System.out.println("Posicion 1 ocupada --> x:: " + robotUp.getX() + " y:: " + robotUp.getY());
                   }
               }
               else
               {
                   if(ge.isPositionFormationRight(0) && !ge.isPositionFormationRight(1))
                   {
                       ge.setPositionFormationRight(1);
                       System.out.println("Posicion 1 ocupada --> x:: " + robotUp.getX() + " y:: " + robotUp.getY());
                   }
               }
           }
           else
           {
               robotUp.setJumps(robotUp.getJumps() + 1);
               robotUp.setFalling(false);
           }

           System.out.println("*** ENCIMA DE OTRO DESPUES DEL SALTO ***");
        }

       //***** COLISION DE ROBOTS CON EXPLOSIONES *****
       if(robotAbajo.getVerticalSpeed() == 0 && robotAbajo.getHorizontalSpeed() == 0 &&
          robotArriba.getVerticalSpeed() > Robot.SPEED_WITH_PARACHUTE)
       {
            ge.addToScore(4);

            if(robotArriba.getX() < SabotageGameObject.SCREEN_WIDTH/2)
            {
                ge.deleteElementToTroopLeft(robotDown.getID());
            }
            else
            {
                ge.deleteElementToTroopRight(robotDown.getID());
            }

            BufferedImage[] images = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion.png", 7, 1);
            VolatileSprite explosion = new VolatileSprite(images, robotArriba.getX(), robotArriba.getY());

            robotArriba.setActive(false);

            parent.EXPLOSION.add(explosion);

            BufferedImage[] images2 = parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/explosion.png", 7, 1);
            VolatileSprite explosion2 = new VolatileSprite(images2, robotAbajo.getX(), robotAbajo.getY());

            robotAbajo.setActive(false);

            parent.EXPLOSION.add(explosion2);

            ge.decrementEnemies_Moves();//robotArriba

            parent.playSound("com/RomaComputer/HimuraProductions/JSabotage/sounds/paratrooper_destroy.wav");
       }

       //**** ROBOT COLISIONA CON OTRO EN EL AIRE ****
       if(robotAbajo.getVerticalSpeed() > 0 && robotArriba.getVerticalSpeed() > 0)
       {
            robotUp.destroyParachute();
            robotDown.destroyParachute();
       }
    }
}