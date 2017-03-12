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
	////////////////////////////////////

	////////Fuentes para los labels/////
	Font fontTitutlos = new Font("Arial", Font.BOLD,25);
	Font fontReferencias = new Font("Arial", Font.BOLD, 15);
	////////////////////////////////////

	//Pila de nodos a expandir
	Vector<Node> pilaDeNodosExpandir = new Vector<>();
	Node nodoGenerado = null;
	Arbol arb;

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


        //Boton que genera el juego
		jButtonGenerarJuego = new JButton("Generar puzzle");
		jButtonGenerarJuego.setBounds(10,70,180,30);
		jButtonGenerarJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                    if (jTextFieldCantidadElementos.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Error introduzca una cantidad",
                                "Error de entrada", JOptionPane.ERROR_MESSAGE);
                    }
                    tamañoGame = Integer.parseInt(jTextFieldCantidadElementos.getText());

                    DefaultTableModel defaultTableModel = new DefaultTableModel(0, tamañoGame);
                    jTableGame = new JTable(defaultTableModel);
                    jTableGame.setFont(fontReferencias);
                    jScrollPaneGame = new JScrollPane(jTableGame);
                    jScrollPaneGame.setBounds(200,70,450,200);
                    jFramePrincipal.add(jScrollPaneGame);

                    matrizGame = generaJuego(tamañoGame);
                    for (int i=0; i<tamañoGame; i++){
                        Vector rowAddTable = new Vector();
                        for (int j=0; j<tamañoGame; j++){
                            rowAddTable.add(matrizGame[i][j]);
                        }
                        defaultTableModel.addRow(rowAddTable);
                    }
					int [][] game = {{1,11,2,9},{5,6,13,4},{8,10,3,12},{7,14,15,0}};
					nodoGenerado = Arbol.nuevoArbol(null,game);
					mvObjeto.historial.add(mvObjeto.convierteMatrizString(game, tamañoGame));
					pilaDeNodosExpandir.addElement(nodoGenerado);

                }catch (NumberFormatException ed){

                }


			}
		});

		/****************************Boton primero en anchura************************/
		jButtonprimeroEnAnchura = new JButton("Anchura");
		jButtonprimeroEnAnchura.setBounds(10,140,180,30);
		jButtonprimeroEnAnchura.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int [][] game = {{1,11,2,9},{5,6,13,4},{8,10,3,12},{7,14,15,0}};
				int [][]sol = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};

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


				Node n=pilaDeNodosExpandir.get(0);
				nodoGenerado = pilaDeNodosExpandir.get(0);

				for (int j=0; j<tamañoGame; j++){
					for(int k=0; k<tamañoGame; k++){
						System.out.print(nodoGenerado.getPuzzle()[j][k]+" ");
					}
					System.out.println();
				}

				System.out.println();


				for (int i=0; i<=n.getProfundidad(n); i++){
					n = n.getPadre();
					for (int j=0; j<tamañoGame; j++){
						for(int k=0; k<tamañoGame; k++){
							System.out.print(n.getPuzzle()[j][k]+" ");
						}
						System.out.println();
					}

					System.out.println();
				}



			}
		});


		/**********************************************************************/


		/*******Carga de los elementos al contenedor*/
		jFramePrincipal.add(jLabelTitutlo);
		jFramePrincipal.add(jLabelIndicadorCantidad);
		jFramePrincipal.add(jTextFieldCantidadElementos);
		jFramePrincipal.add(jButtonGenerarJuego);
		jFramePrincipal.add(jButtonprimeroEnAnchura);
		/*****************************************************************/


		jFramePrincipal.setLocation(100,100);
		jFramePrincipal.setVisible(true);
	}


	public int[][] generaJuego(int tamaño){

		//Variable que guarda el tamaño del juego
		int tamañoGame = tamaño * tamaño;

		//Genera las piezas de 1 al tamaño del puzzle
		for (int i = 0; i < tamañoGame; i++) {
			piezasJuego.addElement(i);
		}
		//Genera un juego aleatoriamente
		int matriz [][] = new int[tamaño][tamaño];
		for (int i=0; i<tamaño; i++){
            for (int j=0; j<tamaño; j++){
                int numeroAleatorio = (int) (Math.random() * piezasJuego.size());
                matriz[i][j] = piezasJuego.get(numeroAleatorio);
                piezasJuego.removeElementAt(numeroAleatorio);
            }
        }

        return matriz;
	}
}
