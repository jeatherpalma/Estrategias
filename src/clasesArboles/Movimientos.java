package clasesArboles;

import java.util.Vector;

/**
 * Created by jeather on 8/03/17.
 */

public class Movimientos {

	Vector<int[][]> matExpandor = new Vector<>();
	public Vector<String> historial = new Vector<>();
	public int contador = 0;

	public boolean banderaGeneral=false;

    public Vector<int[][]> regresaVector(int game[][], int tamañoGame, int [][]gameResuelto){
		boolean banderaDerecha = true, banderaIzquierda = true, banderaAbajo = true, banderaArriba = true;

        contador ++;
		if(convierteMatrizString(game,tamañoGame).equals(convierteMatrizString(gameResuelto, tamañoGame))){
			banderaGeneral = true;

		}else{
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

                    i=j=tamañoGame;
                }

            }
        }

		//Copia de matrices dependiendo del giro
		int [][] matDerecha = nuevaMatriz(game, tamañoGame);
		int [][] matAbajo = nuevaMatriz(game, tamañoGame);
		int [][] matArriba = nuevaMatriz(game, tamañoGame);
		int [][] matIzquierda = nuevaMatriz(game, tamañoGame);

		//Matrices voletadas dependiendo del gito
		String matGiradaDerecha;
		String matGiradaAbajo;
		String matGiradaArriba;
		String matGiradaIzquierda;


        ////////////////////Verifica en que parte se encuentra el cero////////////////////////////////////

        //////Esquina superior Izquierda
        if(posicionY==0 && posicionX==0){
        	//Mueve derecha, mueve abajo
			//[y0,x0] = [y,x+1],[y+1,x]
			matGiradaDerecha = convierteMatrizString(mueveDerecha(matDerecha, tamañoGame), tamañoGame);
			matGiradaAbajo = convierteMatrizString(mueveAbajo(matAbajo, tamañoGame), tamañoGame);

			for (int i = 0; i <historial.size(); i++) {

				if(historial.get(i).equals(matGiradaDerecha)){
					banderaDerecha = false;
					break;
				}
			}

        	for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaAbajo)){
					banderaAbajo = false;
					break;
				}
			}

        	if(banderaDerecha){
        		matExpandor.add(matDerecha);
				historial.add(matGiradaDerecha);

        	}

        	if(banderaAbajo){
        		matExpandor.add(matAbajo);
				historial.add(matGiradaAbajo);
			}

        }else

        //////Esquina superior derecha
        if(posicionY==0 && posicionX==tamañoGame-1){
           //Mueve izquierda, mueve abajo
        	//[y0,xn-1] = [y,x-1],[y+1,x]
			matGiradaAbajo = convierteMatrizString(mueveAbajo(matAbajo, tamañoGame), tamañoGame);
     		matGiradaIzquierda = convierteMatrizString(mueveIzquierda(matIzquierda, tamañoGame), tamañoGame);
			for (int i = 0; i <historial.size(); i++) {

				if(historial.get(i).equals(matGiradaIzquierda)){
					banderaIzquierda = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaAbajo)){
					banderaAbajo = false;
					break;
				}
			}


			if(banderaIzquierda){
				matExpandor.add(matIzquierda);
				historial.add(matGiradaIzquierda);
			}

			if(banderaAbajo){
				matExpandor.add(matAbajo);
				historial.add(matGiradaAbajo);
			}
        }else

        //Esquina inferior derecha
        if(posicionY==tamañoGame-1 && posicionX==tamañoGame-1){
            //Mueve arriba, izquierda
        	//[yn-1,xn-1] = [y-1,x],[y,x-1]
			matGiradaArriba = convierteMatrizString(mueveArriva(matArriba, tamañoGame), tamañoGame);
			matGiradaIzquierda = convierteMatrizString(mueveIzquierda(matIzquierda, tamañoGame), tamañoGame);
			for (int i = 0; i <historial.size(); i++) {

				if(historial.get(i).equals(matGiradaIzquierda)){
					banderaIzquierda = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaArriba)){
					banderaArriba = false;
					break;
				}
			}

			if(banderaIzquierda){
				matExpandor.add(matIzquierda);
				historial.add(matGiradaIzquierda);
			}

			if(banderaArriba){
				matExpandor.add(matArriba);
				historial.add(matGiradaArriba);
			}

        }else

        //////Esquina inferior izquierda
        if(posicionY==tamañoGame-1 && posicionX==0){
        	//Mueve arriba, mueve derecha
            //[yn-1,x0] = [y-1,x],[y,x+1]
			matGiradaArriba = convierteMatrizString(mueveArriva(matArriba, tamañoGame), tamañoGame);
			matGiradaDerecha = convierteMatrizString(mueveDerecha(matDerecha, tamañoGame), tamañoGame);
			for (int i = 0; i <historial.size(); i++) {

				if(historial.get(i).equals(matGiradaDerecha)){
					banderaDerecha = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaArriba)){
					banderaArriba = false;
					break;
				}
			}

			if(banderaDerecha){
				matExpandor.add(matDerecha);
				historial.add(matGiradaDerecha);
			}

			if(banderaArriba){
				matExpandor.add(matArriba);
				historial.add(matGiradaArriba);
			}
        }else

        //Horilla superior
        if(posicionY==0 && posicionX !=tamañoGame-1){
        	//Mueve derecha, mueve izquierda, mueve abajo
            //[y, x]=[y, x+1],[y, x-1],[y+1, x]

			matGiradaDerecha = convierteMatrizString(mueveDerecha(matDerecha,tamañoGame),tamañoGame);
			matGiradaIzquierda = convierteMatrizString(mueveIzquierda(matIzquierda, tamañoGame), tamañoGame);
			matGiradaAbajo = convierteMatrizString(mueveAbajo(matAbajo,tamañoGame), tamañoGame);

			for (int i = 0; i <historial.size(); i++) {

				if(historial.get(i).equals(matGiradaDerecha)){
					banderaDerecha = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaAbajo)){
					banderaAbajo = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaIzquierda)){
					banderaIzquierda = false;
					break;
				}
			}

			if(banderaAbajo){
				matExpandor.add(matAbajo);
				historial.add(matGiradaAbajo);
			}
			if(banderaDerecha){
				matExpandor.add(matDerecha);
				historial.add(matGiradaDerecha);
			}

			if(banderaIzquierda){
				matExpandor.add(matIzquierda);
				historial.add(matGiradaIzquierda);
			}





        }else
        //horilla izquierda
        if(posicionX==0 && posicionY!=0 && posicionY!=tamañoGame-1){
            //[y, x]=[y, x+1],[y+1, x],[y-1, x]
        	//Mueve derecha, mueve abajo, mueve arriba

			matGiradaDerecha = convierteMatrizString(mueveDerecha(matDerecha,tamañoGame),tamañoGame);
			matGiradaArriba = convierteMatrizString(mueveArriva(matArriba, tamañoGame), tamañoGame);
			matGiradaAbajo = convierteMatrizString(mueveAbajo(matAbajo,tamañoGame), tamañoGame);

			for (int i = 0; i <historial.size(); i++) {

				if(historial.get(i).equals(matGiradaDerecha)){
					banderaDerecha = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaAbajo)){
					banderaAbajo = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaArriba)){
					banderaArriba = false;
					break;
				}
			}

			if(banderaAbajo){
				matExpandor.add(matAbajo);
				historial.add(matGiradaAbajo);
			}
			if(banderaDerecha){
				matExpandor.add(matDerecha);
				historial.add(matGiradaDerecha);
			}

			if(banderaArriba){
				matExpandor.add(matArriba);
				historial.add(matGiradaArriba);
			}

        }else
        //horilla inferior
        if(posicionY==tamañoGame-1 && posicionX!=0 && posicionX!=tamañoGame-1){
            //[y, x]=[y, x+1],[y, x-1],[y-1, x]
        	//Mueve derecha, mueve izquierda, mueve arriba

			matGiradaDerecha = convierteMatrizString(mueveDerecha(matDerecha,tamañoGame),tamañoGame);
			matGiradaArriba = convierteMatrizString(mueveArriva(matArriba, tamañoGame), tamañoGame);
			matGiradaIzquierda = convierteMatrizString(mueveIzquierda(matIzquierda,tamañoGame), tamañoGame);

			for (int i = 0; i <historial.size(); i++) {

				if(historial.get(i).equals(matGiradaDerecha)){
					banderaDerecha = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaIzquierda)){
					banderaIzquierda = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaArriba)){
					banderaArriba = false;
					break;
				}
			}


			if(banderaDerecha){
				matExpandor.add(matDerecha);
				historial.add(matGiradaDerecha);
			}

            if(banderaIzquierda){
                matExpandor.add(matIzquierda);
                historial.add(matGiradaIzquierda);
            }

			if(banderaArriba){
				matExpandor.add(matArriba);
				historial.add(matGiradaArriba);
			}

        }else
        //horilla derecha
        if (posicionY!=0 && posicionY!=tamañoGame-1 && posicionX==tamañoGame-1){
            //[y, x]=[y, x-1],[y-1, x],[y+1, x]
        	//Mueve izquierda, mueve arriba, mueve abajo

			matGiradaAbajo = convierteMatrizString(mueveAbajo(matAbajo,tamañoGame),tamañoGame);
			matGiradaArriba = convierteMatrizString(mueveArriva(matArriba, tamañoGame), tamañoGame);
			matGiradaIzquierda = convierteMatrizString(mueveIzquierda(matIzquierda,tamañoGame), tamañoGame);

			for (int i = 0; i <historial.size(); i++) {

				if(historial.get(i).equals(matGiradaAbajo)){
					banderaAbajo = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaIzquierda)){
					banderaIzquierda = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaArriba)){
					banderaArriba = false;
					break;
				}
			}

			if(banderaIzquierda){
				matExpandor.add(matIzquierda);
				historial.add(matGiradaIzquierda);
			}
			if(banderaAbajo){
				matExpandor.add(matDerecha);
				historial.add(matGiradaAbajo);
			}

			if(banderaArriba){
				matExpandor.add(matArriba);
				historial.add(matGiradaArriba);
			}
        }

        else{
            //Hacer movimientos del centro
            //[y!=0, x!=0] = [y, x-1],[y, x+1],[y-1, x],[y+1, x]
        	//Mueve izquierda, mueve derecha, mueve abajo. mueve arriba
			matGiradaAbajo = convierteMatrizString(mueveAbajo(matAbajo,tamañoGame),tamañoGame);
			matGiradaArriba = convierteMatrizString(mueveArriva(matArriba, tamañoGame), tamañoGame);
			matGiradaIzquierda = convierteMatrizString(mueveIzquierda(matIzquierda,tamañoGame), tamañoGame);
			matGiradaDerecha = convierteMatrizString(mueveDerecha(matDerecha,tamañoGame),tamañoGame);

			for (int i = 0; i <historial.size(); i++) {

				if(historial.get(i).equals(matGiradaAbajo)){
					banderaAbajo = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaIzquierda)){
					banderaIzquierda = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {
				if(historial.get(i).equals(matGiradaArriba)){
					banderaArriba = false;
					break;
				}
			}

			for (int i = 0; i <historial.size(); i++) {

				if(historial.get(i).equals(matGiradaDerecha)){
					banderaDerecha = false;
					break;
				}
			}

			if(banderaIzquierda){
				matExpandor.add(matIzquierda);
				historial.add(matGiradaIzquierda);
			}
			if(banderaAbajo){
				matExpandor.add(matAbajo);
				historial.add(matGiradaAbajo);
			}

			if(banderaArriba){
				matExpandor.add(matArriba);
				historial.add(matGiradaArriba);
			}
			if(banderaDerecha){
				matExpandor.add(matDerecha);
				historial.add(matGiradaDerecha);
			}

        }

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
