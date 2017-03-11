package clasesArboles;

import java.util.Vector;

/**
 * Created by jeather on 8/03/17.
 */
public class Movimientos {

	Vector<int[][]> matExpandor = new Vector<>();
    boolean bandera1 = true, bandera2 = true, bandera3 = true, bandera4 = true;
	//Constructror
    public Vector<int[][]> regresaVector(int game[][], int tamañoGame){
        matExpandor.removeAllElements();
    	
        int posicionX = 0;
        int posicionY = 0;
        
        //For que me ecnuentra posicion cero 
        for(int i=0; i<tamañoGame; i++){
            for(int j=0; j<tamañoGame; j++){
                //Localiza la posición nula del juego
                if(game[i][j]==0){
                    posicionX = j;
                    posicionY = i;
                }
            }
        }


        ////////////////////Verifica en que parte se encuentra el cero////////////////////////////////////

        //////Esquina superior Izquierda
        if(posicionY==0 && posicionX==0){
        	//Mueve derecha, mueve abajo
        	int [][] matDerecha = nuevaMatriz(game, tamañoGame);
        	int [][] matAbajo = nuevaMatriz(game, tamañoGame);
        	
        	String matGiradaDerecha = convierteMatrizString(mueveDerecha(game, tamañoGame), tamañoGame);
        	String matGiradaAbajo = convierteMatrizString(mueveAbajo(game, tamañoGame), tamañoGame);
        	
        	for (int i = 0; i <historial.size(); i++) {
				if(historial.getElementAt(i)==matGiradaDerecha){
					//bandera 1 false
				}
			}
        	
        	for (int i = 0; i <historial.size(); i++) {
				if(historial.getElementAt(i)==matGiradaAbajo){
					//bandera 2 false
				}
			}
        	
        	if(bandera1){
        		matExpandor.add(mueveDerecha(game, tamañoGame));
        	}
        	
        	if(bandera2){
        		matExpandor.add(mueveAbajo(game, tamañoGame));
        	}
            //[y0,x0] = [y,x+1],[y+1,x]
        }else
        //////Esquina superior derecha
        if(posicionY==0 && posicionX==tamañoGame-1){
           //Mueve izquierda, mueve abajo
        	//[y0,xn-1] = [y,x-1],[y+1,x]
        }else
        //Esquina inferior derecha
        if(posicionY==tamañoGame-1 && posicionX==tamañoGame-1){
            //Mueve arriba, izquierda
        	//[yn-1,xn-1] = [y-1,x],[y,x-1]
        }else
        //////Esquina inferior izquierda
        if(posicionY==tamañoGame-1 && posicionX==0){
        	//Mueve arriba, mueve derecha
            //[yn-1,x0] = [y-1,x],[y,x+1]
        }else

        //Horilla superior
        if(posicionY==0 && posicionX !=tamañoGame-1){
        	//Mueve derecha, mueve izquierda, mueve abajo
            //[y, x]=[y, x+1],[y, x-1],[y+1, x]
        }else
        //horilla izquierda
        if(posicionX==0 && posicionY!=0 && posicionY!=tamañoGame-1){
            //[y, x]=[y, x+1],[y+1, x],[y-1, x]
        	//Mueve derecha, mueve abajo, mueve arriba
        }else
        //horilla inferior
        if(posicionY==tamañoGame-1 && posicionX!=0 && posicionX!=tamañoGame-1){
            //[y, x]=[y, x+1],[y, x-1],[y-1, x]
        	//Mueve derecha, mueve izquierda, mueve arriba
        }else
        //horilla derecha
        if (posicionY!=0 && posicionY!=tamañoGame-1 && posicionX==tamañoGame-1){
            //[y, x]=[y, x-1],[y-1, x],[y+1, x]
        	//Mueve izquierda, mueve arriba, mueve abajo
        }

        else{
            //Hacer movimientos del centro
            //[y!=0, x!=0] = [y, x-1],[y, x+1],[y-1, x],[y+1, x]
        	//Mueve izquierda, mueve derecha, mueve abajo. mueve arriba

        }
        return matExpandor;

    }
    
    //Convierte la matriz a string
    public String convierteMatrizString(int mat[][], int size){
        String puzzleConcertido ="";
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                puzzleConcertido +=mat[i][j];
            }
        }

        return puzzleConcertido;
     }

    //Mueve hacia la Izquierda
    public int [][] mueveIzquierda(int mat[][], int size){
    	for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(mat[i][j]==0){
					mat[i][j] = mat[i][j-1];
					mat[i][j-1]=0;
					i=size;
					break;
				}
			}
		}
    	return mat;
    }
    
    //Mueve hacia la derecha
    public int [][] mueveDerecha(int mat[][], int size){
    	for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(mat[i][j]==0){
					mat[i][j] = mat[i][j+1];
					mat[i][j+1]=0;
					i=size;
					break;
				}
			}
		}
    	return mat;
    }
    
    //Mueve hacia abajo
    public int [][] mueveAbajo(int mat[][], int size){
    	for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(mat[i][j]==0){
					mat[i][j] = mat[i+1][j];
					mat[i+1][j]=0;
					i=size;
					break;
				}
			}
		}
    	return mat;
    }
    
    //Mueve hacia Arriba
    public int [][] mueveArriva(int mat[][], int size){
    	for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(mat[i][j]==0){
					mat[i][j] = mat[i-1][j];
					mat[i-1][j]=0;
					i=size;
					break;
				}
			}
		}
    	return mat;
    }

    //Regresa una copia de la matriz orignial
    public int [][] nuevaMatriz(int mat[][], int size){
    	
    	int [][] matNew = new int[size][size];
    	
    	for (int i = 0; i <size; i++) {
			for (int j = 0; j < size; j++) {
				matNew[i][j] = mat[i][j];
						
			}
		}
    	
    	return matNew;
    }
}
