/*******************************************************************************
 *                              Credits.java                                   *
 *                           Cesar Himura Elric                                *
 *            (C)2006-2009  RomaComputer - Himura Productions                  *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.credits;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGame;

import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.util.FontUtil;
import com.golden.gamedev.util.ImageUtil;
import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.engine.timer.SystemTimer;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;

import java.util.Vector;

import java.net.URL;

/**
 * Mustra los creditos del juego
 * @author Cesar Himura Elric
 */
public class Credits extends GameObject
{
    private GameFont fontTG;
    private GameFont fontMG;
    
    private ParserXMLCredits xml;
    
    private Vector<String[]> vCreditos;
    
    private int sizeTG;
    private int sizeMG;
    private int y;
    private int yAux = 0;
    private int xImg;
    private int yImg;
    private static final int WIDTH_SCREEN = 800;
    private static final int HEIGHT_SCREEN = 600;
    
    /**
     * Constructor del Menu principal de Hellcopter
     * @param parent
     */
    public Credits(SabotageGame parent)
    {
        super(parent);
    }
    
    /**
     * Inicializa los recursos para mostrar los creditos del juego
     */
    public void initResources()
    {
        xml = new ParserXMLCredits();
        
        sizeTG = xml.getSizeTitleGroup();
        sizeMG = xml.getSizeMemberGroup();
        
        Font awtFont = FontUtil.createTrueTypeFont(bsIO.getURL(xml.getFont()), Font.BOLD, sizeTG);
        
        fontTG = fontManager.getFont(awtFont);
        fontMG = fontManager.getFont(awtFont.deriveFont((float)sizeMG));
        
        vCreditos = xml.getCredits();
        
        y = 605;
    }
    
    /**
     * Actualiza las variables del juego dependiendo del estado del juego 
     * @param elapsedTime Tiempo recorrido
     */
    public void update(long elapsedTime)
    {
        y--;
    }
    
    /**
     * Pinta la pantalla con todos sus objetos  
     * @param g
     */
    public void render(Graphics2D g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.WHITE);
        
        yAux = y;
        
        for(String[] s: vCreditos)
        {
            //System.out.println("yAux:: " + yAux + " .:"+s[0]+":.");
            if(s[0].equals("TG"))
            {
                yAux += 73;
                fontTG.drawString(g, s[1], GameFont.CENTER, 0, yAux, getWidth());
            }
            else
            {
                yAux += 27;
                fontMG.drawString(g, s[1], GameFont.CENTER, 0, yAux, getWidth());
            }
        }//for
        
        if(yAux < -80)
        {
            showLogo("Copyright_Himura_Productions.png");
            bsGraphics.getBackBuffer();
            parent.nextGameID = SabotageGame.MENU_SCREEN;
            //bsMusic.stopAll();
            finish();
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
       
       // loading GTGE logo for splash screen
       URL logoURL = Credits.class.getClass().getResource("/com/RomaComputer/HimuraProductions/JSabotage/images/" + file);
      
       BufferedImage logo = ImageUtil.getImage(logoURL);// the splash screen image

       xImg = WIDTH_SCREEN/2 - logo.getWidth()/2;
       yImg = HEIGHT_SCREEN/2 -logo.getHeight()/2;
       
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
               g.drawImage(logo, xImg, yImg, null);
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
       
       // show the shiny logo for 3000 ms :-)
       do {
           Graphics2D g = bsGraphics.getBackBuffer();

           g.drawImage(logo, xImg, yImg, null);
           bsGraphics.getBackBuffer();
       } while (bsGraphics.flip() == false);

       int i = 0;
       while (i++ < 200) { // 200 x 50 = 10000
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
               g.drawImage(logo, xImg, yImg, null);
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