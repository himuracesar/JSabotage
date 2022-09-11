/*******************************************************************************
 *                       ParserXMLInstruccions.java                            *
 *                           Cesar Himura Elric                                *
 *             (C)2006-2009  RomaComputer - Himura Productions                 *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.menu;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;

import org.xml.sax.SAXException;

import java.io.InputStream;
import java.io.IOException;

import java.util.Vector;

public class ParserXMLInstruccions
{
    private int TOTAL_ROWS;
    private int SIZE_ROW;

    private String font = "";
    private String sInstruccions = "";

    private Vector<String> vInstruccions = new Vector<String>();

    public ParserXMLInstruccions()
    {
        try
        {
            InputStream input = ParserXMLInstruccions.class.getClass().getResourceAsStream("/com/RomaComputer/HimuraProductions" +
                                                                                      "/JSabotage/menu/instruccions.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder generador = factory.newDocumentBuilder();

            Document doc = generador.parse(input);

            XPathFactory xpathFactory = XPathFactory.newInstance();

            XPath ruta = xpathFactory.newXPath();

            font = ruta.evaluate("INSTRUCCIONS/FONT", doc);
            TOTAL_ROWS = Integer.parseInt(ruta.evaluate("INSTRUCCIONS/TOTAL_ROWS", doc));
            SIZE_ROW = Integer.parseInt(ruta.evaluate("INSTRUCCIONS/SIZE_ROW", doc));

            for(int indexRow = 1; indexRow <= TOTAL_ROWS; indexRow++)
            {
                sInstruccions = ruta.evaluate("INSTRUCCIONS/ROW" + indexRow, doc);

                vInstruccions.add(sInstruccions);
            }//for indexGroup
        }
        catch(ParserConfigurationException ex)
        {
            System.out.println("!!ERROR!! ParserConfigurationException:: " + ex);
        }
        catch(SAXException ex)
        {
            System.out.println("!!ERROR!! SAXException:: " + ex);
        }
        catch(IOException ex)
        {
            System.out.println("¡¡ERROR!! IOException:: " + ex);
        }
        catch(XPathExpressionException ex)
        {
            System.out.println("¡¡ERROR!! XPathExpressionException:: " + ex);
        }
    }

    public int getSizeTitleGroup()
    {
        return SIZE_ROW;
    }

    public Vector<String> getInstruccions()
    {
        return vInstruccions;
    }

    public String getFont()
    {
        return font;
    }
}