/*******************************************************************************
 *                         ParserXMLCredits.java                               *
 *                           Cesar Himura Elric                                *
 *             (C)2006-2009  RomaComputer - Himura Productions                 *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage.credits;

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

public class ParserXMLCredits
{   
    private int TOTAL_GROUPS;
    private int SIZE_TITLE_GROUP;
    private int SIZE_MEMBER_GROUP;
    private int members;
    
    private String font = "";
    
    private String[] sCredits = null;
    
    private Vector<String[]> vCredits = new Vector();
    
    public ParserXMLCredits()
    {
        try
        {
            InputStream input = ParserXMLCredits.class.getClass().getResourceAsStream("/com/RomaComputer/HimuraProductions" +
                                                                                      "/JSabotage/credits/credits.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            DocumentBuilder generador = factory.newDocumentBuilder();
            
            Document doc = generador.parse(input);
            
            XPathFactory xpathFactory = XPathFactory.newInstance();
            
            XPath ruta = xpathFactory.newXPath();
            
            font = ruta.evaluate("CREDITS/FONT", doc);
            TOTAL_GROUPS = Integer.parseInt(ruta.evaluate("CREDITS/TOTAL_GROUPS", doc));
            SIZE_TITLE_GROUP = Integer.parseInt(ruta.evaluate("CREDITS/SIZE_TITLE_GROUP", doc));
            SIZE_MEMBER_GROUP = Integer.parseInt(ruta.evaluate("CREDITS/SIZE_MEMBER_GROUP", doc));
            
            for(int indexGroup = 1; indexGroup <= TOTAL_GROUPS; indexGroup++)
            {
                sCredits = new String[2];
                
                sCredits[0] = "TG";
                sCredits[1] = ruta.evaluate("CREDITS/GROUP" + indexGroup + "/TITLE_GROUP", doc);
                
                vCredits.add(sCredits);
                
                members = Integer.parseInt(ruta.evaluate("CREDITS/GROUP" + indexGroup + "/MEMBERS", doc));
                
                for(int indexMember = 1; indexMember <= members; indexMember++)
                {
                    sCredits = new String[2];
                    
                    sCredits[0] = "MG";
                    sCredits[1] = ruta.evaluate("CREDITS/GROUP" + indexGroup + "/MEMBER_GROUP" + indexMember, doc);
                    
                    vCredits.add(sCredits);
                }//indexMember
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
            System.out.println("��ERROR!! IOException:: " + ex);
        }
        catch(XPathExpressionException ex)
        {
            System.out.println("��ERROR!! XPathExpressionException:: " + ex);
        }
    }
    
    public int getSizeTitleGroup()
    {
        return SIZE_TITLE_GROUP;
    }
    
    public int getSizeMemberGroup()
    {
        return SIZE_MEMBER_GROUP;
    }
    
    public Vector<String[]> getCredits()
    {
        return vCredits;
    }
    
    public String getFont()
    {
        return font;
    }
}