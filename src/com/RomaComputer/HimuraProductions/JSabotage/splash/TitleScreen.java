/*******************************************************************************
 *                              TitleScreen.java                               *
 *                           Cesar Himura Elric                                *
 *             (C)2006-2009  RomaComputer - Himura Productions                 *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.splash;

import com.RomaComputer.HimuraProductions.JSabotage.SabotageGame;

import com.golden.gamedev.GameObject;
import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.util.FontUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class TitleScreen extends GameObject
{
    private GameFont font;
    private GameFont fontSmall;
    private GameFont fontSign;

    /**
     * Constructor del GameObject para la pantalla de titulo
     * @param parent
     */
    public TitleScreen(SabotageGame parent)
    {
        super(parent);
    }

    /**
     * Inicializa los recursos
     */
    public void initResources()
    {
        Font awtFont = FontUtil.createTrueTypeFont(bsIO.getURL("/com/RomaComputer/HimuraProductions/JSabotage/fonts/ARCADECLASSIC.TTF"), Font.BOLD, 150);
        Font awtFontSign = FontUtil.createTrueTypeFont(bsIO.getURL("/com/RomaComputer/HimuraProductions/JSabotage/fonts/256BYTES.TTF"), Font.BOLD, 25);

        font = fontManager.getFont(awtFont);
        fontSign = fontManager.getFont(awtFontSign);

        awtFont = awtFont.deriveFont(Font.PLAIN, 30f);

        fontSmall = fontManager.getFont(awtFont);

        //awtFont = awtFont.deriveFont(Font.PLAIN, 15f);

        //fontSign = fontManager.getFont(awtFont);
    }

    /**
     * Actualiza la logica de la pantalla de titulo
     * @param elapsedTime Tiempo transcurrido
     */
    public void update(long elapsedTime)
    {
        if(bsInput.getKeyPressed() != bsInput.NO_KEY)
        {
            parent.nextGameID = SabotageGame.MENU_SCREEN;
            finish();
        }

        bsInput.update(elapsedTime);
    }

    /**
     * Renderiza todos los objetos en pantalla
     * @param g
     */
    public void render(Graphics2D g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setPaint(Color.WHITE);
        font.drawString(g, "SABOTAGE", GameFont.CENTER, 10, 120, getWidth());

        fontSmall.drawString(g, "Press any key to continue", GameFont.CENTER, 5, 250, getWidth());

        fontSign.drawString(g, "©2006-2009 HimuraProductions", GameFont.CENTER, 15, 570, getWidth());

        //g.setFont(new Font("Arial", Font.PLAIN, 15));

        //g.drawString("©2006-2009 HimuraProductions", 300, 590);
    }
}