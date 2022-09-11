/*******************************************************************************
 *                        SabotageGameObject.java                              *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage;

import com.RomaComputer.HimuraProductions.JSabotage.collision.BulletToHelcopterCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.RobotToFloorCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.RobotToBaseCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.BulletToRobotCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.BulletToParachuteCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.ExplosionRobotToParachuteCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.RobotToRobotCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.ExplosionHelcopterToRobotCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.RobotToCannonCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.BulletToAirplaneCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.BombToBaseCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.BombToFloorCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.BulletToBombCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.BombToCannonCollision;
import com.RomaComputer.HimuraProductions.JSabotage.collision.ExplosionAirplaneToBombCollision;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import com.golden.gamedev.GameObject;
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.util.FontUtil;

public class SabotageGameObject extends GameObject
{
    private boolean out_helcopter = true;
    private boolean out_airplane = false;
    private boolean reactiva = false;

    private final int TIME_OUT_HELCOPTER = 150000;
    private final int TIME_OUT_AIRPLANE = 100000;
    private final int TIME_WAIT_ENEMIES = 10000;
    private final int TIME_GENERATE_ENEMIES = 3000;
    private final int PLAYING = 0;
    private final int GAME_OVER = 1;
    private int gameState = 0;
    
    public static int SCREEN_WIDTH = 800;
    public static int SCREEN_HEIGHT = 600;

    private Background background;

    private SpriteGroup FLOOR;
    private SpriteGroup BASE_CANNON;
    public SpriteGroup ROBOT;
    private SpriteGroup CANNON;
    private SpriteGroup BULLET_CANNON;
    public SpriteGroup EXPLOSION_HELCOPTER;
    public SpriteGroup EXPLOSION;
    private SpriteGroup HELCOPTERS;
    public SpriteGroup PARACHUTE;
    private SpriteGroup AIRPLANES;
    public SpriteGroup BOMB;

    private PlayField playfield;

    private GenerateEnemies generator;

    private Timer timer_generate_enemies = new Timer(TIME_GENERATE_ENEMIES);
    private Timer timer_helcopter = new Timer(TIME_OUT_HELCOPTER);
    private Timer timer_airplane = new Timer(TIME_OUT_AIRPLANE);
    private Timer timer_wait_enemies = new Timer(TIME_WAIT_ENEMIES);
    private Timer timer_game_over = new Timer(5000);

    private GameFont font;
    private GameFont fontGameOver;
    private GameFont fontInstruccion;

    protected static final double KEYBOARD_ROTATION_SPEED = 0.1;

    /***********************************************************
     * Personajes del Juego                                    *
     ***********************************************************/
    //private Robot robot;
    private Cannon cannon;
    private Helcopter helcopter;
    private Airplane airplane;

    /***********************************************************
     * Tipo de Colisiones                                      *
     ***********************************************************/
    private BulletToHelcopterCollision collisionBulletHelcopter;
    private RobotToFloorCollision collisionRobotFloor;
    private RobotToBaseCollision collisionRobotBase;
    private BulletToRobotCollision collisionBulletRobot;
    private BulletToParachuteCollision collisionBulletParachute;
    private ExplosionRobotToParachuteCollision collisionExplosionParachute;
    private RobotToRobotCollision collisionRobotRobot;
    private ExplosionHelcopterToRobotCollision collisionExplosionRobot;
    private RobotToCannonCollision collisionRobotCannon;
    private BulletToAirplaneCollision collisionBulletAirplane;
    private BombToBaseCollision collsionBombBase;
    private BombToFloorCollision collsionBombFloor;
    private BulletToBombCollision collisionBulletBomb;
    private BombToCannonCollision collisionBombCannon;
    private ExplosionAirplaneToBombCollision collsionAxplosionBomb;

    /***********************************************************
     * Constructor                                             *
     ***********************************************************/
    public SabotageGameObject(GameEngine parent)
    {
        super(parent);
    }

    /**
     * Inicia los recursos del juego
     */
    public void initResources()
    {
        generator = GenerateEnemies.getGenerateEnemiesInstance();

        bsGraphics.setWindowTitle("Sabotage");
        bsGraphics.setWindowIcon(new ImageIcon("com/RomaComputer/HimuraProductions/JSabotage/images/duke.gif").getImage());

        background = new ImageBackground(getImage("com/RomaComputer/HimuraProductions/JSabotage/images/background.JPG"));

        FLOOR = new SpriteGroup("floor");
        BASE_CANNON = new SpriteGroup("Base_Cannon");
        CANNON = new SpriteGroup("Cannon");
        BULLET_CANNON = new SpriteGroup("Bullet_Cannon");
        ROBOT = new SpriteGroup("robot");
        EXPLOSION_HELCOPTER = new SpriteGroup("Explosion_Helcopter");
        EXPLOSION = new SpriteGroup("Explosion");
        HELCOPTERS = new SpriteGroup("Helcopter");
        PARACHUTE = new SpriteGroup("Parachute");
        AIRPLANES = new SpriteGroup("Airplanes");
        BOMB = new SpriteGroup("Bomb");

        playfield = new PlayField(background);
        playfield.addGroup(FLOOR);
        playfield.addGroup(CANNON);
        playfield.addGroup(BASE_CANNON);
        playfield.addGroup(BULLET_CANNON);
        playfield.addGroup(HELCOPTERS);
        playfield.addGroup(EXPLOSION);
        playfield.addGroup(ROBOT);
        playfield.addGroup(PARACHUTE);
        playfield.addGroup(EXPLOSION_HELCOPTER);
        playfield.addGroup(AIRPLANES);
        playfield.addGroup(BOMB);

        collisionBulletHelcopter = new BulletToHelcopterCollision(this);
        collisionRobotFloor = new RobotToFloorCollision(this);
        collisionRobotBase = new RobotToBaseCollision(this);
        collisionBulletRobot = new BulletToRobotCollision(this);
        collisionBulletParachute = new BulletToParachuteCollision(this);
        collisionExplosionParachute = new ExplosionRobotToParachuteCollision();
        collisionRobotRobot = new RobotToRobotCollision(this);
        collisionExplosionRobot = new ExplosionHelcopterToRobotCollision(this);
        collisionRobotCannon = new RobotToCannonCollision(this);
        collisionBulletAirplane = new BulletToAirplaneCollision(this);
        collsionBombBase = new BombToBaseCollision(this);
        collsionBombFloor = new BombToFloorCollision(this);
        collisionBulletBomb = new BulletToBombCollision(this);
        collisionBombCannon = new BombToCannonCollision(this);
        collsionAxplosionBomb = new ExplosionAirplaneToBombCollision(this);

        playfield.addCollisionGroup(BULLET_CANNON, HELCOPTERS, collisionBulletHelcopter);
        playfield.addCollisionGroup(ROBOT, FLOOR, collisionRobotFloor);
        playfield.addCollisionGroup(ROBOT, BASE_CANNON, collisionRobotBase);
        playfield.addCollisionGroup(BULLET_CANNON, ROBOT, collisionBulletRobot);
        playfield.addCollisionGroup(BULLET_CANNON, PARACHUTE, collisionBulletParachute);
        playfield.addCollisionGroup(EXPLOSION, PARACHUTE, collisionExplosionParachute);
        playfield.addCollisionGroup(ROBOT, ROBOT, collisionRobotRobot);
        playfield.addCollisionGroup(EXPLOSION_HELCOPTER, ROBOT, collisionExplosionRobot);
        //playfield.addCollisionGroup(ROBOT, CANNON, collisionRobotCannon);
        playfield.addCollisionGroup(BULLET_CANNON, AIRPLANES, collisionBulletAirplane);
        playfield.addCollisionGroup(BOMB, BASE_CANNON, collsionBombBase);
        playfield.addCollisionGroup(BOMB, FLOOR, collsionBombFloor);
        playfield.addCollisionGroup(BULLET_CANNON, BOMB, collisionBulletBomb);
        playfield.addCollisionGroup(CANNON, BOMB, collisionBombCannon);
        playfield.addCollisionGroup(EXPLOSION_HELCOPTER, BOMB, collsionAxplosionBomb);

        //Sprite base_cannon = new Sprite(getImage("com/RomaComputer/HimuraProductions/JSabotage/images/base_cannon_01.png"), 330, 495);
        //Sprite base_cannon = new Sprite(getImage("com/RomaComputer/HimuraProductions/JSabotage/images/base_cannon_02.png"), 338, 495);

        Sprite base_cannon = new Sprite(getImage("com/RomaComputer/HimuraProductions/JSabotage/images/baseCannon.gif"), 300, 500);
        Sprite floor = new Sprite(getImage("com/RomaComputer/HimuraProductions/JSabotage/images/floor.JPG"),0,580);
        
        cannon = new Cannon(this);

        BASE_CANNON.add(base_cannon);
        CANNON.add(cannon);
        FLOOR.add(floor);

        timer_airplane.setActive(false);
        timer_wait_enemies.setActive(false);

        Font awtFont = FontUtil.createTrueTypeFont(bsIO.getURL("/com/RomaComputer/HimuraProductions/JSabotage/fonts/ARCADECLASSIC.TTF"), Font.BOLD, 60);

        font = fontManager.getFont(awtFont);

        awtFont = awtFont.deriveFont(Font.PLAIN, 100f);
        fontGameOver = fontManager.getFont(awtFont);

        awtFont = awtFont.deriveFont(Font.PLAIN, 40f);
        fontInstruccion = fontManager.getFont(awtFont);

        gameState = 0;
        generator.resetAll();
    }

    /**
     * Actualiza la logica del juego
     * @param elapsedTime tiempo transcurrido
     */
    public void update(long elapsedTime)
    {
        switch(gameState)
        {
            case PLAYING: updatePlaying(elapsedTime);
            break;

            case GAME_OVER: updateGameOver(elapsedTime);
            break;
        }
    }//update

    /**
     * Actualiza el control sobre el cañon
     * @param elapsedTime
     */
    private void updateControlCannon(long elapsedTime)
    {
        if(cannon.isActive())
        {
            if(bsInput.isKeyPressed(KeyEvent.VK_SPACE))
            {
                Sprite bullet_cannon = cannon.fire(this);

                if(generator.getScore() > 0)
                    generator.addToScore(-1);

                BULLET_CANNON.add(bullet_cannon);

                playSound("com/RomaComputer/HimuraProductions/JSabotage/sounds/disparo.wav");
            }

            if(bsInput.isKeyDown(KeyEvent.VK_LEFT))
            {
                cannon.rotateCannon(-elapsedTime, KEYBOARD_ROTATION_SPEED);
            }
            else
                if(bsInput.isKeyDown(KeyEvent.VK_RIGHT))
                {
                    cannon.rotateCannon(elapsedTime, KEYBOARD_ROTATION_SPEED);
                }
        }
    }

    private void updatePlaying(long elapsedTime)
    {
        playfield.update(elapsedTime);

        if(generator.isTotalTroopLeft() || generator.isTotalTroopRight())
        {
            timer_helcopter.setActive(false);
            timer_airplane.setActive(false);
            timer_wait_enemies.setActive(false);

            reactiva = true;

            //System.out.println("*** ENEMIES:: " + generator.getEnemies_Moves());
            if(generator.getEnemies_Moves() == 0)
            {
                if(generator.isTotalTroopLeft() && !generator.isPriorityAssignLeft())
                {
                    generator.assingPrioritiesTroopLeft();
                    collisionBulletRobot.setActive(false);
                }

                if(generator.isTotalTroopRight() && !generator.isPriorityAssignRight())
                {
                    generator.assingPrioritiesTroopRight();
                    collisionBulletRobot.setActive(false);
                }
            }
        }
        else
        {
            if(reactiva)
            {
                timer_helcopter.setActive(true);
                timer_wait_enemies.setActive(true);
                reactiva = false;
            }

            if(timer_helcopter.isActive())
            {
                if(timer_generate_enemies.action(elapsedTime))
                {
                    helcopter = (Helcopter)generator.generateHelcopter(this);

                    if(helcopter != null)
                        HELCOPTERS.add(helcopter);
                }//if out_helcopter

                if(timer_helcopter.action(elapsedTime))
                {
                    out_helcopter = false;
                    out_airplane = true;

                    timer_helcopter.setActive(false);
                    timer_airplane.setActive(false);
                    timer_wait_enemies.setActive(true);
                }
            }
            else
            {
                if(timer_airplane.isActive())
                {
                    if(timer_generate_enemies.action(elapsedTime))
                    {
                        airplane = (Airplane)generator.generateAirplane(this);

                        if(airplane != null)
                            AIRPLANES.add(airplane);
                    }

                    if(timer_airplane.action(elapsedTime))
                    {
                        out_helcopter = true;
                        out_airplane = false;

                        timer_airplane.setActive(false);
                        timer_helcopter.setActive(false);
                        timer_wait_enemies.setActive(true);
                    }
                }//if timer_airplane.isActive()
            }

            if(timer_wait_enemies.isActive())
            {
                if(timer_wait_enemies.action(elapsedTime))
                {
                    if(out_helcopter)
                        timer_helcopter.setActive(true);
                    else
                        timer_airplane.setActive(true);

                    timer_wait_enemies.setActive(false);
                }
            }
        }

        updateControlCannon(elapsedTime);

        generator.setCannon(cannon);

        if(timer_game_over.action(elapsedTime) && !cannon.isActive())
        {
            gameState = GAME_OVER;
        }
    }

    private void updateGameOver(long elapsedTime)
    {
        if(bsInput.getKeyPressed() != bsInput.NO_KEY)
        {
            parent.nextGameID = SabotageGame.MENU_SCREEN;
            finish();
        }

        bsInput.update(elapsedTime);
    }

    /**
     * Renderiza los graficos en pantalla
     * @param g
     */
    public void render(Graphics2D g)
    {
        playfield.render(g);
        paintScore(g);

        if(gameState == GAME_OVER)
        {
            renderGameOver(g);
        }
    }

    /**
     * Pinta la puntuacion del gamer
     * @param g
     */
    private void paintScore(Graphics2D g)
    {
        g.setPaint(Color.WHITE);
        font.drawString(g, String.valueOf(generator.getScore()), GameFont.CENTER, 0, 515, getWidth());

        //g.setPaint(Color.RED);
        //font.drawString(g, String.valueOf(generator.getEnemies_Moves()), GameFont.CENTER, 0, 550, getWidth());
    }

    private void renderGameOver(Graphics2D g)
    {
        g.setPaint(Color.WHITE);
        fontGameOver.drawString(g, "GAME OVER", GameFont.CENTER, 0, 200, getWidth());
        fontInstruccion.drawString(g, "Press any key to continue", GameFont.CENTER, 0, 280, getWidth());
    }
}