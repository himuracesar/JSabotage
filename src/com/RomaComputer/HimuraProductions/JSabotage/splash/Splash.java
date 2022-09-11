/*******************************************************************************
 *                              Splash.java                                    *
 *                           Cesar Himura Elric                                *
 *              (C)2008  RomaComputer - Himura Productions                     *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.splash;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGame;

import com.golden.gamedev.GameObject;
import com.golden.gamedev.util.ImageUtil;
import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.engine.timer.SystemTimer;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;

import java.net.URL;

public class Splash extends GameObject
{
    /**
     * Constructor del GameObject para las pantallas Splash
     * @param parent
     */
    public Splash(SabotageGame parent)
    {
        super(parent);
    }
    
    /**
     * Inicializa los recursos de la ventana
     */
    public void initResources()
    {
        bsGraphics.setWindowTitle("Sabotage");
    }
    
    /**
     * Actualiza las variables de la ventana
     * @param elapsedTime
     */
    public void update(long elapsedTime)
    {
        try
        {
            bsInput.update(100L);
        }
        catch(Exception ex)
        {
            System.out.println("ERROR UPDATE:: " + ex);
        }
    }
    
    /**
     * Renderiza todos los objetos en pantalla
     * @param g
     */
    public void render(Graphics2D g)
    {
        int ciclos = 0;
        
        String[] files = {"RomaComputer.JPG", "Himura_Productions_presents.JPG", "a_Cesar_Himura_game.png"};
        
        try
        {
            while(ciclos < 3)
            {
                showLogo(files[ciclos]);
                ciclos++;
           }
            //waitForPlay();
            bsGraphics.getBackBuffer();
            parent.nextGameID = SabotageGame.TITLE_SCREEN;
            finish();
        }
        catch(Exception ex)
        {
            System.out.println("EXCEPTION:: " + ex);
        }
    }
    
    /**
     * Muestra una pantalla splash con una imagen en especifico
     * @param file
     */
    public void showLogo(String file) {
       hideCursor();
       SystemTimer dummyTimer = new SystemTimer();
       dummyTimer.setFPS(20);
       bsInput.refresh();
       
       // loading Himura Productions logo and more for splash screen
       URL logoURL = Splash.class.getClass().getResource("/com/RomaComputer/HimuraProductions/JSabotage/images/" + file);
      
       BufferedImage logo = ImageUtil.getImage(logoURL);// the splash screen image

       // time to show Himura Productions splash screen!
       // clear background with black color
       // and wait for a second
       try {
           clearScreen(Color.BLACK);
           Thread.sleep(1000L);
       } catch (InterruptedException e) { }

       // gradually show (alpha blending)
       float alpha = 0.0f;
       dummyTimer.startTimer();
       boolean firstTime = true;
       while (alpha < 1.0f) {
           do {
               Graphics2D g = bsGraphics.getBackBuffer();
               
               g.setColor(Color.BLACK);
               g.fillRect(0, 0, getWidth(), getHeight());
               Composite old = g.getComposite();
               g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
               g.drawImage(logo, 0, 0, null);
               g.setComposite(old);
               bsGraphics.getBackBuffer();
           } while (bsGraphics.flip() == false);
           
           if (firstTime) {
               // workaround for OpenGL mode
               firstTime = false;
               dummyTimer.refresh();
           }

           long elapsedTime = dummyTimer.sleep();
           double increment = 0.001 * elapsedTime;
           if (increment > 0.15) {
               increment = 0.15 + ((increment-0.11)/2);
           }
           alpha += increment;

           if (isSkip(elapsedTime)) {
               clearScreen(Color.BLACK);
               logo.flush(); 
               logo = null;
               return;
           }
           bsInput.update(elapsedTime);
       }
       
       // show the shiny logo for 5000 ms :-)
       do {
           Graphics2D g = bsGraphics.getBackBuffer();

           g.drawImage(logo, 0, 0, null);
           bsGraphics.getBackBuffer();
       } while (bsGraphics.flip() == false);

       int i = 0;
       while (i++ < 100) { // 100 x 50 = 3000
           try {
               Thread.sleep(50L);
           } catch (InterruptedException e) { }

           if (isSkip(50)) {
               clearScreen(Color.BLACK);
               logo.flush(); logo = null;
               return;
           }
       }

       // gradually disappeared
       alpha = 1.0f;
       dummyTimer.refresh();
       while (alpha > 0.0f) {
           do {
               Graphics2D g = bsGraphics.getBackBuffer();

               g.setColor(Color.BLACK);
               g.fillRect(0, 0, getWidth(), getHeight());
               Composite old = g.getComposite();
               g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
               g.drawImage(logo, 0, 0, null);
               g.setComposite(old);
               bsGraphics.getBackBuffer();
           } while (bsGraphics.flip() == false);

           long elapsedTime = dummyTimer.sleep();
           double decrement = 0.00053 * elapsedTime;
           if (decrement > 0.14) {
               decrement = 0.14 + ((decrement-0.04)/2);
           }
           alpha -= decrement;

           if (isSkip(elapsedTime)) {
               clearScreen(Color.BLACK);
               logo.flush(); logo = null;
               return;
           }
       }

       logo.flush();
       logo = null;
       dummyTimer.stopTimer();
       dummyTimer = null;

       // black wait before playing
       try {
           clearScreen(Color.BLACK);
           Thread.sleep(100L);
       } catch (InterruptedException e) { }
   }

    /**
     * Permite saltar la pantalla splash
     * @param elapsedTime
     * @return true en caso de algun boton apretado por teclado o mouse
     */
   private boolean isSkip(long elapsedTime) {
       boolean skip = (bsInput.getKeyPressed() != BaseInput.NO_KEY ||
                       bsInput.getMousePressed() != BaseInput.NO_BUTTON);
       bsInput.update(elapsedTime);

       return skip;
   }

   /**
    * Limpia la pantalla pintandola toda de negro
    * @param col
    */
   private void clearScreen(Color col) {
       Graphics2D g = bsGraphics.getBackBuffer();
       g.setColor(col);
       g.fillRect(0, 0, getWidth(), getHeight());
       bsGraphics.flip();

       g = bsGraphics.getBackBuffer();
       g.setColor(col);
       g.fillRect(0, 0, getWidth(), getHeight());
       bsGraphics.flip();
   }
}