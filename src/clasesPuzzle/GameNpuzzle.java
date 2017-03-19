package clasesPuzzle;


import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import clasesArboles.Arbol;
import clasesArboles.Movimientos;
import clasesArboles.Node;

public class GameNpuzzle {

	//Vector que guardara cada posición del juego
	private Vector<Integer> piezasJuego = new Vector<>();

	//Variables de la interfaz
	private JFrame jFramePrincipal;
	private JLabel jLabelTitutlo, jLabelIndicadorCantidad;
	private JButton jButtonGenerarJuego;
	private JTextField jTextFieldCantidadElementos;
	private JTable jTableGame;
	private DefaultTableModel defaultTableModel;
	private JScrollPane jScrollPaneGame;
	private JButton jButtonprimeroEnAnchura;
	private JButton jButtonEnProfundidad;
    private JButton jButtonAEstrella;
	private JButton jButtonClean;
	////////////////////////////////////

	//Objetos para el area de impresión
	private JTextArea jTextAreaResultado;
	private JScrollPane jScrollPaneResultado;
	private JTextArea jTextAreaEspacio, jTextAreaTiempo, jTextAreaNodos;

	////////Fuentes para los labels/////
	Font fontTitutlos = new Font("Arial", Font.BOLD,25);
	Font fontReferencias = new Font("Arial", Font.BOLD, 15);
	Font fontResultados = new Font("Arial", Font.BOLD, 12);
	////////////////////////////////////
	//Indicador del método
	JLabel jLabelMetodo;
	//Pila de nodos a expandir
	Vector<Node> pilaDeNodosExpandir = new Vector<>();
    //Onjetos de la clase ARbol y nodo
	Node nodoGenerado = null;
	Arbol arb;

    //Objeto de la heuristica
    A_Estrella aEstrella = new A_Estrella();

	//Vector de matrices a expandir
	Vector<int [][]> trayectoria = new Vector<>();

	//Objeto de movimientos
	Movimientos mvObjeto = new Movimientos();

    //Variables en donde se guarda el puzzle y el tamaño
	int [][] matrizGame;
    int [][] solucion;
	int tamañoGame;

    //Memoria utilizada
    long memory;
	//Para calcular el tiempo
	long tiempoInicio,tiempoFinal;
	DecimalFormat decimalesFormato = new DecimalFormat("0.000");


	//Metodo constructor
	public GameNpuzzle(){
		//Inicio y creacion del contenedor principal///////////////////
		jFramePrincipal = new JFrame("N-puzzle");
		jFramePrincipal.setSize(700,700);
		jFramePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFramePrincipal.setLayout(null);

		/////////////////////////////////////////////////////////////

		/*Generaciónde de contenido de la ventana******************************/
		/////Titulo
		jLabelTitutlo = new JLabel("N-puzzle");
		jLabelTitutlo.setBounds(300, 10,180,30);
		jLabelTitutlo.setFont(fontTitutlos);

		//Label de la caja de cantidad
        jLabelIndicadorCantidad = new JLabel("N-Elementos");
        jLabelIndicadorCantidad.setBounds(10,105,180, 30);
        jLabelIndicadorCantidad.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelIndicadorCantidad.setFont(fontReferencias);
		//Caja de entrada de catidad de elementos
		jTextFieldCantidadElementos = new JTextField();
		jTextFieldCantidadElementos.setBounds(160,105,30,30);
        jTextFieldCantidadElementos.setFont(fontReferencias);

		/****************************Boton primero en anchura************************/
		jButtonprimeroEnAnchura = new JButton("Breadth-First");
		jButtonprimeroEnAnchura.setBounds(10,140,180,30);
		jButtonprimeroEnAnchura.setEnabled(false);
		jButtonprimeroEnAnchura.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jLabelMetodo.setText("Breadth-First");
				//Borrar los campos
				jTextAreaEspacio.setText("");
				jTextAreaNodos.setText("");
				jTextAreaTiempo.setText("");
				jTextAreaResultado.setText("");

				//Se carga la memoria en uso antes de empezar con el algoritmo en Profunidad
				memory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
				//Tiempo de inicio
				tiempoInicio = System.currentTimeMillis();
				//Cuenta de nodos expandidos
                mvObjeto.banderaGeneral=false;
                mvObjeto.contador =0;

                int [][]sol = getSolucion(tamañoGame);
                int [][] game =cargarJuego(tamañoGame);

                //Limpieza de los vectores y el hash
                pilaDeNodosExpandir.clear();
				mvObjeto.historial2.clear();
				trayectoria.clear();

				//Strin de puzzle
				String puzzle = mvObjeto.convierteMatrizString(game, tamañoGame);
                //Se agrega el juego al nodo y al historial
                nodoGenerado = Arbol.nuevoArbol(null,game);
				mvObjeto.historial2.put(puzzle,puzzle);
                pilaDeNodosExpandir.addElement(nodoGenerado);

				while(mvObjeto.banderaGeneral==false){
					Vector<int[][]> matricesExpandir = mvObjeto.regresaVector(pilaDeNodosExpandir.get(0).getPuzzle(),tamañoGame,sol);
					if(mvObjeto.banderaGeneral==true){
						break;
					}
					for (int i=0; i<matricesExpandir.size(); i++){
						nodoGenerado = Arbol.nuevoArbol(pilaDeNodosExpandir.get(0), matricesExpandir.get(i));
						String puzzleExpandido = mvObjeto.convierteMatrizString(matricesExpandir.get(i), tamañoGame);
						mvObjeto.historial2.put(puzzleExpandido,puzzleExpandido);
						pilaDeNodosExpandir.addElement(nodoGenerado);
						arb = new Arbol(nodoGenerado);

					}

					pilaDeNodosExpandir.remove(0);

				}
				nodoGenerado = pilaDeNodosExpandir.get(0);
				int contador=0;
				while(nodoGenerado!=null){
					contador++;
					trayectoria.add(nodoGenerado.getPuzzle());
					nodoGenerado=nodoGenerado.getProfundidad2(nodoGenerado);
				}
				//tiempo fin
				tiempoFinal = (System.currentTimeMillis() -tiempoInicio);
				//Se hace el calculo con la memoria utilizada después del algoritmo
				memory = Math.abs(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()-memory)/(1024L*1024L);

				//Impresión de los resultados
				jTextAreaTiempo.append(decimalesFormato.format(tiempoFinal*1.6667e-5)+ "m " +decimalesFormato.format(tiempoFinal*0.001)
				+"s");
				jTextAreaNodos.append("\n");
				jTextAreaNodos.append("Profundidad objetivo: " +contador);
				jTextAreaNodos.append("\n");
				jTextAreaEspacio.append("\n");
				jTextAreaEspacio.append("Memoria utilizada: " +memory+ " Mb");
				jTextAreaNodos.append("Nodos expandidos: " +mvObjeto.contador);

				for (int i=trayectoria.size()-1; i>=0; i--)
				{
					for(int j=0; j<tamañoGame; j++)
					{
						for(int k=0; k<tamañoGame; k++){
							jTextAreaResultado.append(trayectoria.get(i)[j][k]+" ");
						}
						jTextAreaResultado.append("\n");
					}
					jTextAreaResultado.append("\n");
				}
			}
		});

		/************************En profundidad********************************/
		jButtonEnProfundidad = new JButton("Depth-first");
		jButtonEnProfundidad.setBounds(10,180,180,30);
		jButtonEnProfundidad.setEnabled(false);
		jButtonEnProfundidad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jLabelMetodo.setText("Depth-first");
				//Borrar los campos
				jTextAreaEspacio.setText("");
				jTextAreaNodos.setText("");
				jTextAreaTiempo.setText("");
				jTextAreaResultado.setText("");
				//SE carga la memoria en uso antes de empezar con el algoritmo en Profunidad
				memory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
				//Tiempo de inicio
				tiempoInicio = System.currentTimeMillis();
				//Cuenta de nodos expandidos
                mvObjeto.banderaGeneral=false;
                mvObjeto.contador =0;

                int [][]sol = getSolucion(tamañoGame);
                int [][] game =cargarJuego(tamañoGame);

                //Limpieza de los vectores
                pilaDeNodosExpandir.clear();
				mvObjeto.historial2.clear();
                trayectoria.clear();

                //Se agrega el juego
                nodoGenerado = Arbol.nuevoArbol(null,game);
				mvObjeto.historial2.put(mvObjeto.convierteMatrizString(game, tamañoGame),mvObjeto.convierteMatrizString(game, tamañoGame));
                pilaDeNodosExpandir.addElement(nodoGenerado);


				//Forma de pila el vector de nodos a expandir
				Node aux = null;
				while(mvObjeto.banderaGeneral==false){
					aux = pilaDeNodosExpandir.remove(pilaDeNodosExpandir.size()-1);
					Vector<int[][]> matricesExpandir = mvObjeto.regresaVector(aux.getPuzzle(),tamañoGame,sol);
					if(mvObjeto.banderaGeneral==true){
						break;
					}
					for (int i=0; i<matricesExpandir.size(); i++){
						nodoGenerado = Arbol.nuevoArbol(aux, matricesExpandir.get(i));
						String puzzleExpandido = mvObjeto.convierteMatrizString(matricesExpandir.get(i), tamañoGame);
						mvObjeto.historial2.put(puzzleExpandido,puzzleExpandido);
						pilaDeNodosExpandir.addElement(nodoGenerado);
						arb = new Arbol(nodoGenerado);

					}
				}

				int contador=0;
				while(aux!=null){
					contador++;
					trayectoria.add(aux.getPuzzle());
					aux=aux.getProfundidad2(aux);

				}
				//tiempo fin
				tiempoFinal = (System.currentTimeMillis() -tiempoInicio);
				//Se hace el calculo con la memoria utilizada después del algoritmo
				memory = Math.abs(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()-memory)/(1024L*1024L);

				//Impresión de los resultados
				jTextAreaTiempo.append(decimalesFormato.format(tiempoFinal*1.6667e-5)+ "m " +decimalesFormato.format(tiempoFinal*0.001)
						+"s");
				jTextAreaNodos.append("\n");
				jTextAreaNodos.append("Profundidad objetivo: " +contador);
				jTextAreaNodos.append("\n");
				jTextAreaEspacio.append("\n");
				jTextAreaEspacio.append("Memoria utilizada: " +memory+ " Mb");
				jTextAreaNodos.append("Nodos expandidos: " +mvObjeto.contador);
				for (int i=trayectoria.size()-1; i>=0; i--)
				{
					for(int j=0; j<tamañoGame; j++)
					{
						for(int k=0; k<tamañoGame; k++){
							jTextAreaResultado.append(trayectoria.get(i)[j][k]+" ");
						}
						jTextAreaResultado.append("\n");
					}
					jTextAreaResultado.append("\n");
				}

			}
		});

        /*********************A_estrella*********************************************/
        jButtonAEstrella = new JButton("A_Estrella");
        jButtonAEstrella.setBounds(10,220,180,30);
        jButtonAEstrella.setEnabled(false);
        jButtonAEstrella.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				jLabelMetodo.setText("A*");
				//Borrar los campos
				jTextAreaEspacio.setText("");
				jTextAreaNodos.setText("");
				jTextAreaTiempo.setText("");
				jTextAreaResultado.setText("");
                //SE carga la memoria en uso antes de empezar con el algoritmo en A*
				memory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
				//Tiempo de inicio
				tiempoInicio = System.currentTimeMillis();
				//Cuenta de nodos expandidos
                mvObjeto.banderaGeneral=false;
                mvObjeto.contador =0;

                int [][]sol = getSolucion(tamañoGame);
                int [][] game =cargarJuego(tamañoGame);

                //Limpieza de los vectores
                pilaDeNodosExpandir.clear();
				mvObjeto.historial2.clear();
				trayectoria.clear();

                //Se agrega el juego
                nodoGenerado = Arbol.nuevoArbol(null,game);
				mvObjeto.historial2.put(mvObjeto.convierteMatrizString(game, tamañoGame),mvObjeto.convierteMatrizString(game, tamañoGame));
				pilaDeNodosExpandir.addElement(nodoGenerado);



                Node aux = null;

				//Algoritmo a estrella
                while (mvObjeto.banderaGeneral==false){
                    aux = pilaDeNodosExpandir.remove(aEstrella.getExpandir(pilaDeNodosExpandir,tamañoGame));
                    Vector<int[][]> matricesExpandir = mvObjeto.regresaVector(aux.getPuzzle(),tamañoGame,sol);
                    if(mvObjeto.banderaGeneral==true){
                        break;
                    }
                    for (int i=0; i<matricesExpandir.size(); i++){
						nodoGenerado = Arbol.nuevoArbol(aux, matricesExpandir.get(i));
						String puzzleExpandido = mvObjeto.convierteMatrizString(matricesExpandir.get(i), tamañoGame);
						mvObjeto.historial2.put(puzzleExpandido,puzzleExpandido);
                        pilaDeNodosExpandir.addElement(nodoGenerado);
                        arb = new Arbol(nodoGenerado);

                    }


                }

                int contador=0;
                while(aux!=null){
                    contador++;
                    trayectoria.add(aux.getPuzzle());
                    aux=aux.getProfundidad2(aux);

                }

				//tiempo fin
				tiempoFinal = (System.currentTimeMillis() -tiempoInicio);
				//Se hace el calculo con la memoria utilizada después del algoritmo
				memory = Math.abs(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()-memory)/(1024L*1024L);

				//Impresión de los resultados
				jTextAreaTiempo.append(decimalesFormato.format(tiempoFinal*1.6667e-5)+ "m " +decimalesFormato.format(tiempoFinal*0.001)
						+"s");
				jTextAreaNodos.append("\n");
				jTextAreaNodos.append("Profundidad objetivo: " +contador);
				jTextAreaNodos.append("\n");
				jTextAreaEspacio.append("\n");
				jTextAreaEspacio.append("Memoria utilizada: " +memory+ " Mb");
				jTextAreaNodos.append("Nodos expandidos: " +mvObjeto.contador);

				//Impresión de los movimientos
                for (int i=trayectoria.size()-1; i>=0; i--)
                {
                    for(int j=0; j<tamañoGame; j++)
                    {
                        for(int k=0; k<tamañoGame; k++){
                            jTextAreaResultado.append(trayectoria.get(i)[j][k]+" ");
                        }
                        jTextAreaResultado.append("\n");
                    }
                    jTextAreaResultado.append("\n");
                }

            }
        });

        /***************************************************************************/
		//Boton que genera el juego
		jButtonGenerarJuego = new JButton("Generar puzzle");
		jButtonGenerarJuego.setBounds(10,70,180,30);
		jButtonGenerarJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
					jLabelMetodo.setText("");
					//Borrar los campos
					jTextAreaEspacio.setText("");
					jTextAreaNodos.setText("");
					jTextAreaTiempo.setText("");
					jTextAreaResultado.setText("");
					jButtonEnProfundidad.setEnabled(true);
					jButtonprimeroEnAnchura.setEnabled(true);
                    jButtonAEstrella.setEnabled(true);
					jTextAreaResultado.append("");
                    if (jTextFieldCantidadElementos.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Error introduzca una cantidad",
                                "Error de entrada", JOptionPane.ERROR_MESSAGE);
                    }
                    tamañoGame = Integer.parseInt(jTextFieldCantidadElementos.getText());

                    defaultTableModel = new DefaultTableModel(0, tamañoGame);
                    jTableGame = new JTable(defaultTableModel);
                    jTableGame.setFont(new Font("Arial",Font.BOLD,18));
					jTableGame.getCellRenderer(0,0);
                    jScrollPaneGame = new JScrollPane(jTableGame);
                    jScrollPaneGame.setBounds(200,70,200,200);
                    jFramePrincipal.add(jScrollPaneGame);

                    matrizGame = generaJuego(tamañoGame);
                    for (int i=0; i<tamañoGame; i++){
                        Vector rowAddTable = new Vector();
                        for (int j=0; j<tamañoGame; j++){
                            rowAddTable.add(matrizGame[i][j]);
                        }
                        defaultTableModel.addRow(rowAddTable);
                    }

					nodoGenerado = Arbol.nuevoArbol(null,matrizGame);
					String puzzleExpandido = mvObjeto.convierteMatrizString(matrizGame, tamañoGame);
					mvObjeto.historial2.put(puzzleExpandido,puzzleExpandido);
					pilaDeNodosExpandir.addElement(nodoGenerado);

                }catch (NumberFormatException ed){

                }


			}
		});
		//Area de movimientos
		JLabel jLabelMovimientos = new JLabel("Movimientos:");
		jLabelMovimientos.setFont(fontReferencias);
		jLabelMovimientos.setBounds(290,290,180,30);
		jTextAreaResultado = new JTextArea();
		jScrollPaneResultado = new JScrollPane(jTextAreaResultado);
		jScrollPaneResultado.setBounds(290,320,400,300);

		//Area de espacio
		JLabel jLabelEspacio = new JLabel("Espacio en memoria:");
		jLabelEspacio.setFont(fontReferencias);
		jLabelEspacio.setBounds(10,290,240,30);
		jTextAreaEspacio = new JTextArea();
		jTextAreaEspacio.setFont(fontResultados);
		jTextAreaEspacio.setBounds(10,320,240,50);


		//Area de Nodos
		JLabel jLabelNodos = new JLabel("Nodos expandidos y Profundida:");
		jLabelNodos.setFont(fontReferencias);
		jLabelNodos.setBounds(10,390,265,30);
		jTextAreaNodos = new JTextArea();
		jTextAreaNodos.setFont(fontResultados);
		jTextAreaNodos.setBounds(10,420,240,50);


		//Area de Tiempo
		JLabel jLabelTiempo = new JLabel("Tiempo necesario:");
		jLabelTiempo.setFont(fontReferencias);
		jLabelTiempo.setBounds(10,480,240,30);
		jTextAreaTiempo = new JTextArea();
		jTextAreaTiempo.setFont(fontResultados);
		jTextAreaTiempo.setBounds(10,510,240,50);

		//Boton de limpiar
		jButtonClean = new JButton("Limpiar");
		jButtonClean.setBounds(10,570,180,30);
		jButtonClean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					for (int i=0; i<tamañoGame; i++){
						defaultTableModel.removeRow(0);
					}
					jTextFieldCantidadElementos.setText("");
					jLabelMetodo.setText("");
					jTextAreaEspacio.setText("");
					jTextAreaNodos.setText("");
					jTextAreaTiempo.setText("");
					jTextAreaResultado.setText("");
				}catch (Exception ex){

				}


			}
		});

		///Indicador del método
		jLabelMetodo = new JLabel();
		jLabelMetodo.setFont(new Font("Arial",Font.BOLD, 30));
		jLabelMetodo.setBounds(440,100,250,60);
		/**********************************************************************/


		/*******Carga de los elementos al contenedor*/
		jFramePrincipal.add(jLabelTitutlo);
		jFramePrincipal.add(jLabelIndicadorCantidad);
		jFramePrincipal.add(jTextFieldCantidadElementos);
		jFramePrincipal.add(jButtonGenerarJuego);
		jFramePrincipal.add(jButtonprimeroEnAnchura);
		jFramePrincipal.add(jButtonEnProfundidad);
		jFramePrincipal.add(jScrollPaneResultado);
        jFramePrincipal.add(jButtonAEstrella);
		jFramePrincipal.add(jLabelMovimientos);
		jFramePrincipal.add(jTextAreaEspacio);
		jFramePrincipal.add(jLabelEspacio);
		jFramePrincipal.add(jTextAreaNodos);
		jFramePrincipal.add(jLabelNodos);
		jFramePrincipal.add(jTextAreaTiempo);
		jFramePrincipal.add(jLabelTiempo);
		jFramePrincipal.add(jLabelMetodo);
		jFramePrincipal.add(jButtonClean);
		/*****************************************************************/


		jFramePrincipal.setLocationRelativeTo(null);
		jFramePrincipal.setVisible(true);

	}


	public int[][] generaJuego(int tamaño){

		//Matriz en la que se genera el juego
		int matriz[][] = new int[tamaño][tamaño];
		//Bandera que controla el ciclo para la determinación de si es resolvible
		boolean ban = true;
		int inversiones = 0;
		while(ban) {

			inversiones = 0;
			//Genera las piezas de 1 al tamaño del puzzle
			for (int i = 0; i < (tamañoGame * tamañoGame); i++) {
				piezasJuego.addElement(i);
			}
			//Genera un juego aleatoriamente
			Vector<Integer> vector = new Vector<>();
			for (int i = 0; i < tamaño; i++) {
				for (int j = 0; j < tamaño; j++) {
					int numeroAleatorio = (int) (Math.random() * piezasJuego.size());
					matriz[i][j] = piezasJuego.get(numeroAleatorio);
					int numero = Integer.parseInt(piezasJuego.remove(numeroAleatorio).toString());
					if (numero != 0) {
						vector.add(numero);
					}
				}
			}

			//Gurada todos los elementos a excepcion del cero para comprobar si el juego es resolvible
			for (int i = 0; i < vector.size(); i++) {
				for (int j = i + 1; j < vector.size(); j++) {
					if (vector.get(i) > vector.get(j)) {
						//AUmenta el número de inversiones si se encontro alguna
						inversiones++;
					}
				}
			}
			if (inversiones % 2 == 0) {
				ban = false;
			}


		}
        return matriz;
	}
	public int [][] cargarJuego(int tamañoGame){
		int matriz [][] = new int[tamañoGame][tamañoGame];

		for (int i = 0; i<tamañoGame; i++){
			for (int j=0; j<tamañoGame; j++){
				matriz[i][j] = Integer.parseInt(jTableGame.getValueAt(i,j).toString());
			}

		}

		return matriz;
	}
    public int [][] getSolucion(int tamañoGame){

        int [][] solucion = new int[tamañoGame][tamañoGame];
        int posiciones =1;
        for (int i = 0; i<tamañoGame; i++){
            for (int j=0; j<tamañoGame; j++){
                solucion[i][j]= posiciones;
                posiciones++;
            }

        }
        solucion[tamañoGame-1][tamañoGame-1]=0;
        return solucion;
    }


}
