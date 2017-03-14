package clasesPuzzle;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

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
	private JScrollPane jScrollPaneGame;
	private JButton jButtonprimeroEnAnchura;
	private JButton jButtonEnProfundidad;
    private JButton jButtonAEstrella;
	////////////////////////////////////

	//Objetos para el area de impresión
	private JTextArea jTextAreaResultado;
	private JScrollPane jScrollPaneResultado;

	////////Fuentes para los labels/////
	Font fontTitutlos = new Font("Arial", Font.BOLD,25);
	Font fontReferencias = new Font("Arial", Font.BOLD, 15);
	////////////////////////////////////

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

	int [][] matrizGame;
	int tamañoGame;


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
		jButtonprimeroEnAnchura = new JButton("Anchura");
		jButtonprimeroEnAnchura.setBounds(10,140,180,30);
		jButtonprimeroEnAnchura.setEnabled(false);
		jButtonprimeroEnAnchura.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int [][]sol = {{0,1,2},{3,4,5},{6,7,8}};
				trayectoria = new Vector<>();
				while(mvObjeto.banderaGeneral==false){
					Vector<int[][]> matricesExpandir = mvObjeto.regresaVector(pilaDeNodosExpandir.get(0).getPuzzle(),tamañoGame,sol);
					if(mvObjeto.banderaGeneral==true){
						break;
					}
					for (int i=0; i<matricesExpandir.size(); i++){
						nodoGenerado = Arbol.nuevoArbol(pilaDeNodosExpandir.get(0), matricesExpandir.get(i));
						mvObjeto.historial.add(mvObjeto.convierteMatrizString(matricesExpandir.get(i), tamañoGame));
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

				jTextAreaResultado.append("Profundidad del nodo objetivo: " +contador);
				jTextAreaResultado.append("\n");
				jTextAreaResultado.append("Cantidad de nodos expandidos: " +mvObjeto.contador);
				jTextAreaResultado.append("\n\n");
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
		jButtonEnProfundidad = new JButton("Profundidad");
		jButtonEnProfundidad.setBounds(10,180,180,30);
		jButtonEnProfundidad.setEnabled(false);
		jButtonEnProfundidad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int [][]sol = {{0,1,2},{3,4,5},{6,7,8}};
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
						mvObjeto.historial.add(mvObjeto.convierteMatrizString(matricesExpandir.get(i), tamañoGame));
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

				jTextAreaResultado.append("Profundidad del nodo objetivo: " +contador);
				jTextAreaResultado.append("\n");
				jTextAreaResultado.append("Cantidad de nodos expandidos: " +mvObjeto.contador);
				jTextAreaResultado.append("\n\n");
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
                Node aux = null;
                int [][]sol = {{1,2,3},{4,5,6},{7,8,0}};
                while (mvObjeto.banderaGeneral==false){
                    aux = pilaDeNodosExpandir.remove(aEstrella.getExpandir(pilaDeNodosExpandir,tamañoGame));
                    Vector<int[][]> matricesExpandir = mvObjeto.regresaVector(aux.getPuzzle(),tamañoGame,sol);
                    if(mvObjeto.banderaGeneral==true){
                        break;
                    }
                    for (int i=0; i<matricesExpandir.size(); i++){
						nodoGenerado = Arbol.nuevoArbol(aux, matricesExpandir.get(i));
                        mvObjeto.historial.add(mvObjeto.convierteMatrizString(matricesExpandir.get(i), tamañoGame));
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

                jTextAreaResultado.append("Profundidad del nodo objetivo: " +contador);
                jTextAreaResultado.append("\n");
                jTextAreaResultado.append("Cantidad de nodos expandidos: " +mvObjeto.contador);
                jTextAreaResultado.append("\n\n");
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
					jButtonEnProfundidad.setEnabled(true);
					jButtonprimeroEnAnchura.setEnabled(true);
                    jButtonAEstrella.setEnabled(true);
					jTextAreaResultado.append("");
                    if (jTextFieldCantidadElementos.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Error introduzca una cantidad",
                                "Error de entrada", JOptionPane.ERROR_MESSAGE);
                    }
                    tamañoGame = Integer.parseInt(jTextFieldCantidadElementos.getText());

                    DefaultTableModel defaultTableModel = new DefaultTableModel(0, tamañoGame);
                    jTableGame = new JTable(defaultTableModel);
                    jTableGame.setFont(new Font("Arial",Font.BOLD,20));
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
					int [][] game = {{5,2,8},{4,1,7},{0,3,6}};
					//int [][] game = {{3,2,6},{5,7,4},{8,0,1}};
                    //int [][]game = {{5,2,8},{4,1,7},{0,3,6}};
                    //int [][] game = {{1,2,3},{4,5,6},{7,0,8}};
					//874320651
					nodoGenerado = Arbol.nuevoArbol(null,game);
					mvObjeto.historial.add(mvObjeto.convierteMatrizString(game, tamañoGame));
					pilaDeNodosExpandir.addElement(nodoGenerado);

                }catch (NumberFormatException ed){

                }


			}
		});

		jTextAreaResultado = new JTextArea();
		jScrollPaneResultado = new JScrollPane(jTextAreaResultado);
		jScrollPaneResultado.setBounds(120,290,400,300);


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
}
