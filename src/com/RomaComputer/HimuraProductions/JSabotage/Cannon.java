/*******************************************************************************
 *                              Cannon.java                                    *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.sprite.AdvanceSprite;

public class Cannon extends AdvanceSprite
{
    public static final int WIDTH  = 128;
    public static final int HEIGHT =  64;

    protected static final int CANNON_XOFFSET = (WIDTH - 16) /2;
    protected static final int CANNON_YOFFSET = -3*HEIGHT/4;

    //Para especificar el Centro de Rotacion, estos depende de **renderCannon**
    protected static final int CANNON_ROTATIONX = 16/3;
    protected static final int CANNON_ROTATIONY = 50;//70+45;

    protected static final int CANNON_MAX_ANGLE =  73;
    protected static final int CANNON_MIN_ANGLE = -CANNON_MAX_ANGLE;

    private int m_cannonAngle;

    protected BufferedImage m_cannonImg;

    public Cannon(SabotageGameObject parent)
    {
        super(parent.getImages("com/RomaComputer/HimuraProductions/JSabotage/images/cannon.png", 1, 1), 340, 505);

        m_cannonImg = parent.getImage("com/RomaComputer/HimuraProductions/JSabotage/images/cannon.png");
    }

    /**
     * Rotating the cannon image.
     *
     * Cannot use ImageUtile.rotate because the image is not a square
     * and there's no way to specify a center of rotation.  A simple
     * solution would be to make a square image, and combine a translation
     * with the rotation.
     *
     * Another solution would be to create a standard AffineTransformation.
     * That's the implemented solution for now.
     *
     * @todo Fatal error under MacOsX 1.3 if the rotation angle of an
     * AffineTransformation is lower then 0 or greater then PI/2.
     */
    protected void renderCannon(Graphics2D g, int x, int y)
    {
        int cannonX = x + CANNON_XOFFSET;
        int cannonY = y + CANNON_YOFFSET;
        
        AffineTransformOp op = new AffineTransformOp(
            AffineTransform.getRotateInstance( Math.toRadians( m_cannonAngle ),
                                               CANNON_ROTATIONX,
                                               CANNON_ROTATIONY ),
            AffineTransformOp.TYPE_NEAREST_NEIGHBOR );
        g.drawImage( m_cannonImg, op, cannonX, cannonY );
    }

    public void rotateCannon(long elapsedTime, double speed)
    {
        m_cannonAngle += (elapsedTime * speed);
        m_cannonAngle = Math.max(m_cannonAngle, CANNON_MIN_ANGLE);
        m_cannonAngle = Math.min(m_cannonAngle, CANNON_MAX_ANGLE);
    }

    public void render(Graphics2D g, int x, int y)
    {
        renderCannon(g, x, y);
    }

    /**
     * Compute the bullet's starting position and the trajectory
     * @todo There's too much computation for nothing here, this code
     * might need some optimisations...
     */
    public Sprite fire(SabotageGameObject parent)
    {
        double rotCenterX = getX() + CANNON_XOFFSET + CANNON_ROTATIONX;
        double rotCenterY = getY() + CANNON_YOFFSET + CANNON_ROTATIONY;
        int r = CANNON_ROTATIONY;

        double radAngle = Math.toRadians(m_cannonAngle - 90);
        double headX = r * Math.cos(radAngle) + rotCenterX;
        double headY = r * Math.sin(radAngle) + rotCenterY;

        double speedX = (headX - rotCenterX)/r;
        double speedY = (headY - rotCenterY)/r;

        BulletCannon bullet = new BulletCannon(parent, headX, headY, speedX, speedY);

        return bullet;
        //return new Sprite(parent.getImage("HimuraProductions/images/bullet.png"), headX, headY);
    }
}