/*******************************************************************************
 *                           SabotageGame.java                                 *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage;

import com.RomaComputer.HimuraProductions.JSabotage.credits.Credits;
import com.RomaComputer.HimuraProductions.JSabotage.menu.Menu;
import com.RomaComputer.HimuraProductions.JSabotage.splash.Splash;
import com.RomaComputer.HimuraProductions.JSabotage.splash.TitleScreen;

import java.awt.Dimension;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;

public class SabotageGame extends GameEngine
{
    public static final int SPLASH = 0;
    public static final int TITLE_SCREEN = 1;
    public static final int MENU_SCREEN = 2;
    public static final int GAME_PLAY = 3;
    public static final int CREDITS = 4;

    {distribute = true;}//LIBERADO!! 19/08/2009

    /**
     * Obtiene el ID del modo de juego, para poder pintar el menu, splash o el juego
     * @param GameID ID del modo
     * @return El ID del modo
     */
    public GameObject getGame(int GameID)
    {
        System.out.println("GAMEID:: " + GameID);
        switch (GameID)
        {
            case SPLASH: return new Splash(this);
            case TITLE_SCREEN: return new TitleScreen(this);
            case MENU_SCREEN: return new Menu(this);
            case GAME_PLAY: return new SabotageGameObject(this);
            case CREDITS: return new Credits(this);
        }

        return null;
    }

    /**
     * Pasa al Siguiente modo de juego
     */
    private void nextGameMode()
    {
        nextGameID = SPLASH;
    }

    public static void main(String[] args)
    {
        GameLoader game = new GameLoader();
        game.setup(new SabotageGame(), new Dimension(800, 600), false);
        game.start();
    }
}