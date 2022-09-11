/*******************************************************************************
 *                              Robot.java                                     *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage;

import com.RomaComputer.HimuraProductions.JSabotage.GenerateEnemies;

import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.sprite.AdvanceSprite;

public class Robot extends AdvanceSprite
{
    private boolean bParachute = false;
    private boolean bWalk = false; //indica que esta caminando
    private boolean bWalked = false;
    private boolean falling = false;
    private boolean to_fall = false;
    private boolean walking_left = false;
    private boolean walking_right = false;

    private int id = 0;
    private int priority = -1;
    private int jumps = 0;
    private int point_jump_left = 245;
    private int point_jump_right = 545;

    private double speed_walk = 0.02;
    private double speed_jump = 0.04;
    public static final double SPEED_WITH_PARACHUTE = 0.06;

    private SabotageGameObject parent;

    private Parachute parachute;

    private GenerateEnemies ge;

    private Timer timerParachute = new Timer(1500);
    private Timer timerJump = new Timer(1500);

    /**
     * Contruye un robot y lo pone en pantalla, la posicion en X del robot es modificada.
     * La pantalla se divide en un total de 23 columnas, cada una de 35px de ancho, para obtener
     * la coordenada donde aparecera el robot se debe dividir esta entre el ancho de la columna, redondearlo
     * y finalmente multiplicarlo por el ancho de la columna
     * @param sgo SabotageGameObject
     * @param x Coordenada en X del robot
     * @param y Coordernada en Y del robot
     */
    public Robot(SabotageGameObject sgo, double x, double y)
    {
        super(sgo.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/Robot.png", 8, 2), x, y);
        //System.out.println("w:: " + getWidth() + " h:: " + getHeight());

        ge = GenerateEnemies.getGenerateEnemiesInstance();

        //*** 35 es el tamaño de la columna donde bajaran los robots ***
        setX((Math.round(x/35)) * 35);

        parent = sgo;

        this.setSpeed(0, 0.08);
    }

    /**
     * Actualiza la logica del robot
     * @param elapsedTime Es el tiempo transcurrido
     */
    public void update(long elapsedTime)
    {
        super.update(elapsedTime);

        if(timerParachute.action(elapsedTime) && !bParachute)
        {
            openParachute();
        }

        if(parachute != null)
            if(bParachute && !parachute.isActive() && !parachute.isDestroy())
            {
                this.setSpeed(0, 0.1);
            }

        if(bWalk)
        {
            if(getX() < SabotageGameObject.SCREEN_WIDTH/2)
            {
                setAnimationFrame(8, 15);
                this.setSpeed(speed_walk,0);
                walking_left = true;
                walking_right = false;
            }
            else
            {
                setAnimationFrame(0, 7);
                this.setSpeed(-speed_walk,0);
                walking_left = false;
                walking_right = true;
            }

            setAnimate(true);
            setLoopAnim(true);

            if(walking_left)
            {
                if(ge.isPositionFormationLeft(0) && !ge.isPositionFormationLeft(1))
                {
                    //***** Debe ocupar posicion 2 de la tropa izquierda ******
                    if(point_jump_left <= getX() && !falling)
                    {
                        setSpeed(0.01, -speed_jump);
                        if(timerJump.action(elapsedTime))
                        {
                            //t++;
                            falling = true;
                            setSpeed(speed_walk, 0.001);
                        }
                    }
                    else
                    {
                        if(falling)
                            setSpeed(speed_walk, speed_jump);
                    }
                }
                else
                {
                    if(ge.isPositionFormationLeft(0) && ge.isPositionFormationLeft(1) && priority == 2)
                    {
                        //**** Debe ocupar posicion 3 de la formacion de tropa ****
                        if(ge.getFormationTroopLeft(2).getX() <= getX())
                        {
                            bWalk = false;
                            bWalked = true;
                            setSpeed(0, 0);
                            System.out.println("Posicion 2 ocupada --> x:: " + getX() + " y:: " + getY());
                            ge.setPositionFormationLeft(2);
                        }
                    }
                    else
                    {
                        //***** Debe ocupar posicion 4 de la formacion de tropa *****
                        if(priority == 3)
                        {
                            if(jumps == 0)
                            {
                                if(205 <= getX() && !falling)
                                {
                                    setSpeed(0.01, -speed_jump);
                                    if(timerJump.action(elapsedTime))
                                    {
                                        falling = true;
                                        setSpeed(speed_walk, 0.001);
                                    }
                                }
                                else
                                {
                                    if(falling)
                                        setSpeed(speed_walk, speed_jump);
                                }
                            }//if jumps == 0
                            
                            if(jumps == 1 || jumps == 2)
                            {
                                jump(elapsedTime);
                            }//if jumps == 1
                        }//if priority == 3
                    }//else
                }
            }//if walking_left

            //***************************************
            //**** Va a caminar hacia la derecha ****
            //***************************************
            if(walking_right)
            {
                if(ge.isPositionFormationRight(0) && !ge.isPositionFormationRight(1))
                {
                    //***** Debe ocupar posicion 2 de la tropa derecha ******
                    if(point_jump_right >= getX() && !falling)
                    {
                        setSpeed(-0.01, -speed_jump);
                        if(timerJump.action(elapsedTime))
                        {
                            falling = true;
                            setSpeed(-speed_walk, 0.001);
                        }
                    }
                    else
                    {
                        if(falling)
                            setSpeed(-speed_walk, speed_jump);
                    }
                }
                else
                {
                    if(ge.isPositionFormationRight(0) && ge.isPositionFormationRight(1) && priority == 2)
                    {
                        //**** Debe ocupar posicion 3 de la formacion de tropa ****
                        if(ge.getFormationTroopRight(2).getX() >= getX())
                        {
                            bWalk = false;
                            bWalked = true;
                            setSpeed(0, 0);
                            System.out.println("Posicion 2 ocupada --> x:: " + getX() + " y:: " + getY());
                            ge.setPositionFormationRight(2);
                        }
                    }
                    else
                    {
                        //***** Debe ocupar posicion 4 de la formacion de tropa *****
                        if(priority == 3)
                        {
                            if(jumps == 0)
                            {
                                if(585 >= getX() && !falling)
                                {
                                    setSpeed(-0.01, -speed_jump);
                                    if(timerJump.action(elapsedTime))
                                    {
                                        falling = true;
                                        setSpeed(-speed_walk, 0.001);
                                    }
                                }
                                else
                                {
                                    if(falling)
                                        setSpeed(-speed_walk, speed_jump);
                                }
                            }//if jumps == 0
                            //System.out.println("jumps:: " + jumps);
                            if(jumps == 1 || jumps == 2)
                            {
                                jump(elapsedTime);
                            }//if jumps == 1

                        }//if priority == 3
                    }//else
                }
            }//if walking_right
        }//if bWalk
        else
        {
            setAnimate(false);
            setLoopAnim(false);
        }

        //**** El robot debe bajar de otro robot ****
        if(to_fall)
        {
            if(getX() > SabotageGameObject.SCREEN_WIDTH/2)
                if(point_jump_right < getX())
                    setSpeed(-speed_jump, speed_jump);
                else
                    setSpeed(speed_jump, speed_jump);
            else
                if(point_jump_left > getX())
                    setSpeed(speed_jump, speed_jump);
                else
                    setSpeed(-speed_jump, speed_jump);

            setAnimate(true);
            setLoopAnim(true);
            if(this.getY() >= 550)
            {
                to_fall = false;
                bWalk = true;
            }

        }

        if(ge.isTotalTroopLeft() && getVerticalSpeed() == 0 && getHorizontalSpeed() == 0 && !bWalked)
        {
            //System.out.println("Y:: " + getY());
            if(priority == 0 && !ge.isPositionFormationLeft(0))
            {
                if(Math.round(this.getY()) <= 550)
                    to_fall = true;
                else
                    bWalk = true;
            }
            else
                if(priority == 1 && ge.isPositionFormationLeft(0) && !ge.isPositionFormationLeft(1))
                {
                    if(Math.round(this.getY()) <= 550)
                    to_fall = true;
                else
                    bWalk = true;
                }
                else
                    if(priority == 2 && ge.isPositionFormationLeft(0) && ge.isPositionFormationLeft(1)
                       && !ge.isPositionFormationLeft(2))
                    {
                        if(Math.round(this.getY()) <= 550)
                            to_fall = true;
                        else
                            bWalk = true;
                    }
                    else
                        if(priority == 3 && ge.isPositionFormationLeft(0) && ge.isPositionFormationLeft(1)
                           && ge.isPositionFormationLeft(2))
                        {
                            if(Math.round(this.getY()) <= 550)
                                to_fall = true;
                            else
                                bWalk = true;
                        }
        }

        //***** Verifica prioridades en tropa de la derecha *******
        if(ge.isTotalTroopRight() && getVerticalSpeed() == 0 && getHorizontalSpeed() == 0 && !bWalked)
        {
            //System.out.println("Y:: " + getY());
            if(priority == 0 && !ge.isPositionFormationRight(0))
            {
                if(Math.round(this.getY()) <= 550)
                    to_fall = true;
                else
                    bWalk = true;
            }
            else
                if(priority == 1 && ge.isPositionFormationRight(0) && !ge.isPositionFormationRight(1))
                {
                    if(Math.round(this.getY()) <= 550)
                    to_fall = true;
                else
                    bWalk = true;
                }
                else
                    if(priority == 2 && ge.isPositionFormationRight(0) && ge.isPositionFormationRight(1)
                       && !ge.isPositionFormationRight(2))
                    {
                        if(Math.round(this.getY()) <= 550)
                            to_fall = true;
                        else
                            bWalk = true;
                    }
                    else
                        if(priority == 3 && ge.isPositionFormationRight(0) && ge.isPositionFormationRight(1)
                           && ge.isPositionFormationRight(2))
                        {
                            if(Math.round(this.getY()) <= 550)
                                to_fall = true;
                            else
                                bWalk = true;
                        }
        }
    }//update

    /**
     * Abre el paracaidas
     */
    public void openParachute()
    {
        bParachute = true;

        parachute = new Parachute(parent, this.getX(), this.getY() - 30);

        parent.PARACHUTE.add(parachute);

        this.setSpeed(0, SPEED_WITH_PARACHUTE);
    }

    /**
     * Destruye el paracaidas
     */
    public void destroyParachute()
    {
        if(parachute != null)
        {
            parachute.setActive(false);
        }
        
        bParachute = true;
    }

    /**
     * Asigna si puede caminar o no
     * @param walk true en caso de que camine
     */
    public void setWalk(boolean walk)
    {
        bWalk = walk;
    }

    /**
     * Asigna si el robot ha caminado
     * @param walked
     */
    public void setWalked(boolean walked)
    {
        bWalked = walked;
    }

    /**
     * Obtiene si el robot ha caminado
     * @return true en caso de que ya haya caminado
     */
    public boolean getWalked()
    {
        return bWalked;
    }

    public void jump(long elapsedTime)
    {
        double vx = 0.02;

        if(walking_right)
        {
            vx = -vx;
            setSpeed(-0.01, -speed_jump);
        }
        else
        {
            setSpeed(0.01, -speed_jump);
        }

        
        if(timerJump.action(elapsedTime))
        {
            //t++;
            //has_jump = true;
            falling = true;
            setSpeed(vx, 0.001);
        }
        else
        {
            if(falling)
                setSpeed(vx, speed_jump);
        }
    }

    /**
     * Asigna prioridad al robot (orden al caminar)
     * @param p Prioridad, 0 es el mas alto (en prioridad)
     */
    public void setPriority(int p)
    {
        priority = p;
    }

    /**
     * Regresa la prioridad del robot
     * @return La prioridad del robot
     */
    public int getPriority()
    {
        return priority;
    }

    /**
     * Actualiza el numero de saltos que ha dado
     * @param jumps Saltos
     */
    public void setJumps(int jumps)
    {
        this.jumps = jumps;
    }

    /**
     * Obtiene el numero de saltos que ha realizado
     * @return El numero de saltos que ha realizado
     */
    public int getJumps()
    {
        return jumps;
    }

    /**
     * Asigna si el robot va de bajada
     * @param f
     */
    public void setFalling(boolean f)
    {
        falling = f;
    }

    public void setID(int id)
    {
        this.id = id;
    }

    public int getID()
    {
        return id;
    }
}