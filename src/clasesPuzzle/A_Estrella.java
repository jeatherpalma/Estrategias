package clasesPuzzle;

import clasesArboles.Node;

import javax.swing.*;
import java.util.Vector;

/**
 * Created by jeather on 14/03/17.
 */
public class A_Estrella {

    public int  getExpandir(Vector<Node> nodosPosiblesAEXpandir, int tamañoGame){
        int valor = manhattanDistancia(nodosPosiblesAEXpandir.get(0).getPuzzle(), tamañoGame);
        int posicion =0;

            for (int i = 1; i < nodosPosiblesAEXpandir.size(); i++) {
                if (valor > manhattanDistancia(nodosPosiblesAEXpandir.get(i).getPuzzle(), tamañoGame)) {
                    valor = manhattanDistancia(nodosPosiblesAEXpandir.get(i).getPuzzle(), tamañoGame);
                    posicion = i;
                } else {
                }
        }
        return posicion;
    }



    //Metodo que emplea una suma de las posiciones que debe de
    public int manhattanDistancia(int [][] puzzle, int tamañoGame){
        int sumaDistanciaManhattan = 0;
        for (int i=0; i<tamañoGame; i++){
            for (int j=0; j<tamañoGame; j++){
                if(puzzle[i][j]!=0){

                    int movimientosX = (puzzle[i][j]-1)/tamañoGame;
                    int movimientosY = (puzzle[i][j]-1)%tamañoGame;
                    int distnaciaX = i - movimientosX;
                    int distanciaY = j - movimientosY;
                    sumaDistanciaManhattan += Math.abs(distnaciaX)+Math.abs(distanciaY);
                }
            }
        }

        return sumaDistanciaManhattan;
    }
}
