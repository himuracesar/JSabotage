/*******************************************************************************
 *                               Menu.java                                     *
 *                           Cesar Himura Elric                                *
 *           (C)2006-2009  RomaComputer - Himura Productions                   *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.menu;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGame;
import com.RomaComputer.HimuraProductions.JSabotage.GenerateEnemies;

import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.util.FontUtil;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import java.util.Vector;

public class Menu extends GameObject
{
    private GameFont font;
    private GameFont fontText;
    
    private int selectedIndex;
    private int TOTAL_OPTION = 2;
    private int sizeFont = 0;
    private int y = 0;

    private ParserXMLInstruccions xml;

    private Vector<String> vInstruccions;

    private GenerateEnemies ge;
    
    public Menu(SabotageGame parent)
    {
        super(parent);
    }
    
    public void initResources()
    {
        //title = getImage("com/RomaComputer/HimuraProductions/Hellcopter/images/Hellcopter_Menu.png", false);
        
        //font = fontManager.getFont(getImages("com/RomaComputer/HimuraProductions/Hellcopter/images/bitmapFontCopacetix.png", 74, 1));

        ge = GenerateEnemies.getGenerateEnemiesInstance();

        xml = new ParserXMLInstruccions();

        sizeFont = xml.getSizeTitleGroup();

        Font awtFontText = FontUtil.createTrueTypeFont(bsIO.getURL(xml.getFont()), Font.BOLD, sizeFont);

        fontText = fontManager.getFont(awtFontText);

        vInstruccions = xml.getInstruccions();

        Font awtFont = FontUtil.createTrueTypeFont(bsIO.getURL("/com/RomaComputer/HimuraProductions/JSabotage/fonts/256BYTES.TTF"), Font.BOLD, 30);
        
        font = fontManager.getFont(awtFont);

        /*awtFont = awtFont.deriveFont(Font.PLAIN, 30f);

        fontText = fontManager.getFont(awtFont);*/

        y = 0;
        selectedIndex = 0;
    }
    
    public void update(long elapsedTime)
    {
        switch (bsInput.getKeyPressed()) 
        {
            case KeyEvent.VK_UP:
                    //playSound("sounds/MenuMove.wav");
                    selectedIndex--;
                    if (selectedIndex < 0) 
                        selectedIndex = TOTAL_OPTION;
            break;

            case KeyEvent.VK_DOWN:
                    //playSound("sounds/MenuMove.wav");
                    selectedIndex++;
                    if (selectedIndex > TOTAL_OPTION) 
                        selectedIndex = 0;
            break;
            
            case KeyEvent.VK_ENTER:
                switch(selectedIndex)
                {
                    case 0: 
                        parent.nextGameID = SabotageGame.GAME_PLAY;
                        bsMusic.stopAll();
                        finish();
                    break;

                    case 1:
                        parent.nextGameID = SabotageGame.CREDITS;
                        bsMusic.stopAll();
                        finish();
                    break;
                    
                    case 2:
                        finish();
                    break;
                }
            break;

            case KeyEvent.VK_ESCAPE:
                    //playSound("sounds/MenuSelect.wav");
                    finish();
            break;
        }
    }
    
    public void render(Graphics2D g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setPaint(Color.WHITE);
        /*fontText.drawString(g, "******** SABOTAGE ********", GameFont.CENTER, 0, 0, getWidth());
        //fontText.drawString(g, "©2006-2009 HimuraProductions", GameFont.CENTER, 0, 15, getWidth());
        fontText.drawString(g, "The object of Sabotage is to score as many points as posible", GameFont.CENTER, 0, 30, getWidth());
        */

        for(int index = 0; index < vInstruccions.size(); index++)
        {
            fontText.drawString(g, vInstruccions.get(index), GameFont.CENTER, 0, y, getWidth());

            y += 30;
        }

        y = 0;

        g.setPaint(Color.RED);

        if(ge.getScore() > ge.getHighScore())
            ge.setHighScore(ge.getScore());

        fontText.drawString(g, "HIGH-SCORE: " + ge.getHighScore(), GameFont.RIGHT, 0, 568, getWidth());

        drawText(g, "PLAY", 24, (selectedIndex == 0));
        drawText(g, "CREDITS", 26, (selectedIndex == 1));
        drawText(g, "EXIT", 28, (selectedIndex == 2));
        
        //font.drawString(g, "Jugar", GameFont.CENTER, 0, 150, getWidth());
        //font.drawString(g, "Salir", GameFont.CENTER, 0, 190, getWidth());
    }
    
    private void drawText(Graphics2D g, String text, int line, boolean selected) 
    {
        if (selected) {
                // draw selected rectangle
                g.setColor(Color.YELLOW);
                g.fillRect((getWidth()/2) - (font.getWidth(text)/2) - 1,
                                   (line*20) + 2,
                                   font.getWidth(text) + 2,
                                   30);
        }

        g.setColor(Color.CYAN);
        font.drawString(g, text, GameFont.CENTER, 0, line * 20, getWidth());
    }
}