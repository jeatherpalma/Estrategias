package clasesPuzzle;

import clasesArboles.Node;

import javax.swing.*;
import java.util.Vector;

/**
 * Created by jeather on 14/03/17.
 */
public class A_Estrella {

    public int  getExpandir(Vector<Node> nodosPosiblesAEXpandir, int tamañoGame){
        int valor = manhattanDistancia(nodosPosiblesAEXpandir.get(0).getPuzzle(), tamañoGame,nodosPosiblesAEXpandir.get(0));
        int posicion =0;

        for (int i = 1; i < nodosPosiblesAEXpandir.size(); i++) {
            if (valor > manhattanDistancia(nodosPosiblesAEXpandir.get(i).getPuzzle(), tamañoGame,nodosPosiblesAEXpandir.get(i))) {
                valor = manhattanDistancia(nodosPosiblesAEXpandir.get(i).getPuzzle(), tamañoGame,nodosPosiblesAEXpandir.get(i));
                posicion = i;
            } else {
            }
        }
        return posicion;
    }



    //Metodo que emplea una suma de movimientos para estar en la posición indicada (Manhattan)
    public int manhattanDistancia(int [][] puzzle, int tamañoGame, Node aux){
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

        //Suma del costo para llegar al nodo a manhattan
        while(aux!=null){
            sumaDistanciaManhattan += 1;
            aux=aux.getProfundidad2(aux);

        }
        return sumaDistanciaManhattan;
    }
}

