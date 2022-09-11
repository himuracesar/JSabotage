/*******************************************************************************
 *                          GenerateEnemies.java                               *
 *                           Cesar Himura Elric                                *
 *              (C)2006-2009  RomaComputer - Himura Productions                *
 *******************************************************************************/

package com.RomaComputer.HimuraProductions.JSabotage;

import java.awt.Point;

import java.util.Vector;
import java.util.List;

import com.golden.gamedev.object.Sprite;

public class GenerateEnemies
{
    private static GenerateEnemies ge;

    private int troop_right = 0;
    private int troop_left = 0;
    private int score = 0;
    private int lastTrack = -1;
    private int id_robot = -1;
    private int high_score = 0;
    private int enemies_moves = 0;
    private final int total_troop_right = 4;
    private final int total_troop_left = 4;

    private boolean priorityAssignTroopLeft = false;
    private boolean priorityAssignTroopRight = false;

    /**
     * Son las pistas por donde pasaran los helicopteros y aviones, 8 pistas --> 0, 1, 2 y 3
     * del lado izquierdo y 4, 5, 6 y 7 del lado derecho
     */
    private boolean[] tracks = {true, true, true, true, true, true, true, true};
    private boolean[] position_formation_left = {false, false, false};
    private boolean[] position_formation_right = {false, false, false};

    private int[] helcopters = {0, 0, 0, 0, 0, 0, 0, 0};


    private final Point[] formation_troop_left = {new Point(273, 551),
                                             new Point(273, 522),
                                             new Point(240, 551)};

    private final Point[] formation_troop_right = {new Point(506, 551),
                                             new Point(506, 519),
                                             new Point(545, 551)};

    private Vector<Robot> vTroopLeft = new Vector<Robot>();
    private Vector<Robot> vTroopRight = new Vector<Robot>();

    private Cannon cannon = null;

    /**
     * Constructor privado, esta clase es un Singleton
     */
    private GenerateEnemies()
    {

    }

    /**
     * Obtiene la unica instancia de GenerateEnemies
     * @return instancia de GenerateEnemies
     */
    public static GenerateEnemies getGenerateEnemiesInstance()
    {
        if(ge == null)
            ge = new GenerateEnemies();

        return ge;
    }

    /**
     * Genera un helicoptero
     * @param parent Es el GameObject del juego
     * @return El helicoptero como sprite
     */
    public Sprite generateHelcopter(SabotageGameObject parent)
    {
        Helcopter helcopter = null;

        boolean puedeGenerar = false;

        int x = 0;
        int y = 0;

        int track = (int)(Math.random() * 8);

        //*** Sale del lado izquierdo ***
        if(track <= 3)
        {
            if(tracks[track + 4])
            {
                tracks[track] = false;
                puedeGenerar = true;

                if(track == lastTrack)
                    x = -105;
                else
                    x = -5;
                y = track * 70;
            }
        }
        //*** Sale del lado derecho ***
        else
        {
            if(tracks[track - 4])
            {
                tracks[track] = false;
                puedeGenerar = true;

                if(track == lastTrack)
                    x = 900;
                else
                    x = 799;
                y = (track-4) * 70;
            }
        }

        lastTrack = track;

        if(puedeGenerar)
        {
            helcopter = new Helcopter(parent, x, y, track);
            helcopters[track]++;

            enemies_moves++;
        }

        return helcopter;
    }

    /**
     * Genera un avion
     * @param parent SabotageGameObject
     * @return Regresa el Sprite del avion
     */
    public Sprite generateAirplane(SabotageGameObject parent)
    {
        Airplane airplane = null;

        boolean puedeGenerar = false;

        int x = 0;
        int y = 0;

        int track = (int)(Math.random() * 3);

        //*** Sale en la pista 0 ***
        if(track <= 1)
        {
            track = 0;
            if(tracks[track + 4])
            {
                tracks[track] = false;
                puedeGenerar = true;

                if(track == lastTrack)
                    x = -105;
                else
                    x = -5;
                y = track * 70;
            }
        }
        //*** Sale en pista 4 ***
        else
        {
            track = 4;
            if(tracks[track - 4])
            {
                tracks[track] = false;
                puedeGenerar = true;

                if(track == lastTrack)
                    x = 900;
                else
                    x = 799;
                y = (track-4) * 70;
            }
        }

        lastTrack = track;

        if(puedeGenerar)
        {
            airplane = new Airplane(parent, x, y, track);
            helcopters[track]++;
            enemies_moves++;
        }

        return airplane;
    }

    /**
     * Libera una pista o carril si esta ya no esta ocupada
     * @param track
     */
    public void setFreeTrack(int track)
    {
        helcopters[track]--;
        
        if(helcopters[track] == 0)
        {
            tracks[track] = true;
        }
    }

    /**
     * Agrega un elemento a la tropa de la izquierda
     */
    public void addElementToTroopLeft(Robot r)
    {
        troop_left++;
        vTroopLeft.add(r);
    }

    /**
     * Borra un elemento de la tropa de la izquierda
     * @param id Id del elemento de que debe borrar
     */
    public void deleteElementToTroopLeft(int id)
    {
        for(int index = 0; index < vTroopLeft.size(); index++)
        {
            if(vTroopLeft.get(index).getID() == id)
            {
                vTroopLeft.remove(index);
                troop_left--;
                break;
            }
        }
    }

    /**
     * Asigna prioridades a los robots de la tropa izquierda para saber quien
     * camina primero y asi sucesivamente
     */
    public void assingPrioritiesTroopLeft()
    {
        int[][] robots = new int[troop_left][3];
        int[][] robotsAssign = new int[troop_left][2];;
        int[][] robotsAux = null;

        int[] rb = null;

        int numberRobot = 0;
        int inx = 0;
        int xr = 0;
        int cont = 0;

        Vector<int[]> vAux = new Vector();

        for(Robot r: vTroopLeft)
        {
            robots[numberRobot][0] = numberRobot; //id del robot
            robots[numberRobot][1] = (int)r.getY(); //coordenada en Y del robot
            robots[numberRobot][2] = (int)r.getX(); //coordenada en X del robot

            numberRobot++;
        }

        //Se ordenan los robots en base a la coordenada X
        robots = ordenaArrayDistancias(robots, 3);
        //System.out.println("***** PASA EL PRIMER ORDENAMIENTO ******");
        while(inx < robots.length)
        {
            vAux.clear();

            //cont = 0;

            xr = robots[inx][2];
            //System.out.println("xr:: " + xr);
            rb = new int[2];
            rb[0] = robots[inx][0];
            rb[1] = robots[inx][1];
            //System.out.println("rb_1:: " + rb[1]);
            vAux.add(rb);
            //System.out.println("Agrega:: " + inx);
            //cont++;

            for(int i = inx+1; i < robots.length; i++)
            {
                if(xr == robots[i][2])
                {
                    inx++;

                    rb = new int[2];
                    rb[0] = robots[i][0];
                    rb[1] = robots[i][1];

                    vAux.add(rb);
                    //System.out.println("_Agrega:: " + inx);
                    //cont++;
                }
                else
                {
                    break;
                }
            }//for

            //Pasar vAux a un array bidimensional y ordenarlo
            robotsAux = toArray(vAux);
            
            if(robotsAux.length > 1)
                robotsAux = ordenaArrayMenorAMayor(robotsAux, 2);

            //System.out.println("**LENGTH:: " + robotsAux.length);
            for(int j = 0; j < robotsAux.length; j++)
            {
                //System.out.println("j+cont:: " + (j+cont));
                robotsAssign[j+cont][0] = robotsAux[j][0];//id del robot
                robotsAssign[j+cont][1] = robotsAux[j][1];//coordenada y
            }

            cont += robotsAux.length;
            //System.out.println("cont:: " + cont);

            inx++;
        }//while

        for(int index = 0; index < robotsAssign.length; index++)
        {
            Robot r = vTroopLeft.get(robotsAssign[index][0]);
            System.out.println("ROBOT:: " + r.getX() + ", " + r.getY() + ", priority :: " + index);
            r.setPriority(index);
        }

        priorityAssignTroopLeft = true;
    }

    private int[][] toArray(List<int[]> list)
    {
        int[][] array = new int[list.size()][2];
        //System.out.println("** list.size():: " + list.size());

        for(int index = 0; index < list.size(); index++)
        {
            array[index][0] = list.get(index)[0];
            array[index][1] = list.get(index)[1];
        }
        //System.out.println("*** array:: " + array.length);
        return array;
    }

    /**
     * Ordena arrays multidimensionales de mayor a menor sin importar el numero de columnas, en la ultima
     * columna va el dato por el cual hara la ordenacion
     * @param jd Array que ordenara
     * @param cols Numero de columnas
     * @return El array ordenado de mayor a menor
     */
    private int[][] ordenaArrayDistancias(int[][] jd, int cols)
    {
        int[] c = new int[cols];

        for(int i = 0; i < cols; i++)
            c[i] = 0;

        for(int i = 0; i < jd.length; i++)
            for(int j = 0; j < jd.length - 1; j++)
                if(jd[j][cols-1] < jd[j + 1][cols-1])
                {
                    for(int col = 0; col < cols; col++)
                    {
                        c[col] = jd[j][col];
                        jd[j][col] = jd[j + 1][col];
                        jd[j + 1][col] = c[col];
                    }
                }

        return jd;
    }

    private int[][] ordenaArrayMenorAMayor(int[][] jd, int cols)
    {
        int[] c = new int[cols];

        for(int i = 0; i < cols; i++)
            c[i] = 0;

        for(int i = 0; i < jd.length; i++)
            for(int j = 0; j < jd.length - 1; j++)
                if(jd[j][cols-1] > jd[j + 1][cols-1])
                {
                    for(int col = 0; col < cols; col++)
                    {
                        c[col] = jd[j][col];
                        jd[j][col] = jd[j + 1][col];
                        jd[j + 1][col] = c[col];
                    }
                }

        return jd;
    }

    /**
     * Verifica si la tropa de la izquierda ya esta completa
     * @return true si la tropa esta completa
     */
    public boolean isTotalTroopLeft()
    {
        if(total_troop_left <= troop_left)
            return true;

        return false;
    }

    /**
     *
     * @param index
     */
    public void setPositionFormationLeft(int index)
    {
        position_formation_left[index] = true;
    }

    public boolean isPositionFormationLeft(int index)
    {
        return position_formation_left[index];
    }

    public Point getFormationTroopLeft(int index)
    {
        return formation_troop_left[index];
    }

    /**
     * Agrega un elemento a la tropa de la derecha
     */
    public void addElementToTroopRight(Robot r)
    {
        troop_right++;
        vTroopRight.add(r);
    }

    /**
     * Borra un elemento de la tropa de la izquierda
     * @param id Id del elemento de que debe borrar
     */
    public void deleteElementToTroopRight(int id)
    {
        for(int index = 0; index < vTroopRight.size(); index++)
        {
            if(vTroopRight.get(index).getID() == id)
            {
                vTroopRight.remove(index);
                troop_right--;
                break;
            }
        }
    }

    /**
     * Asigna prioridades a los robots de la tropa derecha para saber quien
     * camina primero y asi sucesivamente
     */
    public void assingPrioritiesTroopRight()
    {
        int[][] robots = new int[troop_right][3];
        int[][] robotsAssign = new int[troop_right][2];
        int[][] robotsAux = null;

        int[] rb = null;

        int numberRobot = 0;
        int inx = 0;
        int xr = 0;
        int cont = 0;

        Vector<int[]> vAux = new Vector();

        for(Robot r: vTroopRight)
        {
            robots[numberRobot][0] = numberRobot; //id del robot
            robots[numberRobot][1] = (int)r.getY(); //coordenada en Y del robot
            robots[numberRobot][2] = (int)r.getX(); //coordenada en X del robot

            numberRobot++;
        }

        //Se ordenan los robots en base a la coordenada X
        robots = ordenaArrayMenorAMayor(robots, 3);
        //System.out.println("***** PASA EL PRIMER ORDENAMIENTO ******");
        while(inx < robots.length)
        {
            vAux.clear();

            //cont = 0;

            xr = robots[inx][2];
            //System.out.println("xr:: " + xr);
            rb = new int[2];
            rb[0] = robots[inx][0];
            rb[1] = robots[inx][1];
            //System.out.println("rb_1:: " + rb[1]);
            vAux.add(rb);
            //System.out.println("Agrega:: " + inx);
            //cont++;

            for(int i = inx+1; i < robots.length; i++)
            {
                if(xr == robots[i][2])
                {
                    inx++;

                    rb = new int[2];
                    rb[0] = robots[i][0];
                    rb[1] = robots[i][1];

                    vAux.add(rb);
                    //System.out.println("_Agrega:: " + inx);
                    //cont++;
                }
                else
                {
                    break;
                }
            }//for

            //Pasar vAux a un array bidimensional y ordenarlo
            robotsAux = toArray(vAux);

            if(robotsAux.length > 1)
                robotsAux = ordenaArrayMenorAMayor(robotsAux, 2);

            //System.out.println("**LENGTH:: " + robotsAux.length);
            for(int j = 0; j < robotsAux.length; j++)
            {
                //System.out.println("j+cont:: " + (j+cont));
                robotsAssign[j+cont][0] = robotsAux[j][0];//id del robot
                robotsAssign[j+cont][1] = robotsAux[j][1];//coordenada y
            }

            cont += robotsAux.length;
            //System.out.println("cont:: " + cont);

            inx++;
        }//while

        for(int index = 0; index < robotsAssign.length; index++)
        {
            Robot r = vTroopRight.get(robotsAssign[index][0]);
            System.out.println("ROBOT:: " + r.getX() + ", " + r.getY() + ", priority :: " + index);
            r.setPriority(index);
        }

        priorityAssignTroopRight = true;
    }

    /**
     * Verifica si la tropa de la izquierda ya esta completa
     * @return true si la tropa esta completa
     */
    public boolean isTotalTroopRight()
    {
        if(total_troop_right <= troop_right)
            return true;

        return false;
    }

    /**
     *
     * @param index
     */
    public void setPositionFormationRight(int index)
    {
        position_formation_right[index] = true;
    }

    public boolean isPositionFormationRight(int index)
    {
        return position_formation_right[index];
    }

    public Point getFormationTroopRight(int index)
    {
        return formation_troop_right[index];
    }

    /**
     * Resetea el record de puntos a cero
     */
    public void resetScore()
    {
        score = 0;
    }

    public void resetAll()
    {
        for(int index = 0; index < formation_troop_left.length; index++)
        {
            position_formation_left[index] = false;
        }

        for(int index = 0; index < formation_troop_right.length; index++)
        {
            position_formation_right[index] = false;
        }

        for(int index = 0; index < helcopters.length; index++)
        {
            helcopters[index] = 0;
        }

        for(int index = 0; index < tracks.length; index++)
        {
            tracks[index] = true;
        }

        id_robot = -1;
        lastTrack = -1;
        troop_left = 0;
        troop_right = 0;
        enemies_moves = 0;

        priorityAssignTroopLeft = false;
        priorityAssignTroopRight = false;

        vTroopLeft.clear();
        vTroopRight.clear();

        resetScore();
    }

    /**
     * Agrega puntos al record
     * @param points
     */
    public void addToScore(int points)
    {
        score += points;
    }

    /**
     * Obtiene el record de puntos
     * @return El record de punto
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Asigna un ID al robot
     * @param id Id que asignara
     */
    public void setIDRobot(int id)
    {
        id_robot = id;
    }

    /**
     * Obtiene el ID del robot asignado
     * @return El ID del robot asignado
     */
    public int getIDRobot()
    {
        id_robot++;

        return id_robot;
    }

    public void setCannon(Cannon c)
    {
        cannon = c;
    }

    /**
     * Obtiene el Cañon del juego (objeto)
     * @return El Cañon del juego (objeto)
     */
    public Cannon getCannon()
    {
        return cannon;
    }

    /**
     * Obtien el puntaje mas alto de la partida
     * @return El puntaje mas alto
     */
    public int getHighScore()
    {
        return high_score;
    }

    /**
     * Asigna el puntaje mas alto de la partida
     * @param score El puntaje ha asignar
     */
    public void setHighScore(int score)
    {
        high_score = score;
    }

    /**
     * Incremente los enemigos que estan en movimiento
     */
    public void incrementEnemies_Moves()
    {
        enemies_moves++;
    }

    /**
     * Obtiene cuentos enemigos estan en movimiento (en el aire)
     * @return
     */
    public int getEnemies_Moves()
    {
        return enemies_moves;
    }

    /**
     * Decrementa los enemigos en movimiento
     */
    public void decrementEnemies_Moves()
    {
        enemies_moves--;
    }

    /**
     * Regresa true si a la tropa de la izquierda ya fue priorizada
     * @return true si a la tropa de la izquierda ya fue priorizada
     */
    public boolean isPriorityAssignLeft()
    {
        return priorityAssignTroopLeft;
    }

    /**
     * Regresa true si a la tropa de la izquierda ya fue priorizada
     * @return true si a la tropa de la izquierda ya fue priorizada
     */
    public boolean isPriorityAssignRight()
    {
        return priorityAssignTroopRight;
    }
}