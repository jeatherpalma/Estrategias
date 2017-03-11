package clasesPuzzle;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GameNpuzzle {
	
	//Vector que guardara cada posición del juego
	private Vector piezasJuego = new Vector();

	//Variables de la interfaz
	private JFrame jFramePrincipal;
	private JLabel jLabelTitutlo, jLabelIndicadorCantidad;
	private JButton jButtonGenerarJuego;
	private JTextField jTextFieldCantidadElementos;
	private JTable jTableGame;
	private JScrollPane jScrollPaneGame;
	////////////////////////////////////

	////////Fuentes para los labels/////
	Font fontTitutlos = new Font("Arial", Font.BOLD,25);
	Font fontReferencias = new Font("Arial", Font.BOLD, 15);
	////////////////////////////////////

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
        jLabelIndicadorCantidad.setBounds(10,105,100, 30);
        jLabelIndicadorCantidad.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelIndicadorCantidad.setFont(fontReferencias);
		//Caja de entrada de catidad de elementos
		jTextFieldCantidadElementos = new JTextField();
		jTextFieldCantidadElementos.setBounds(160,105,30,30);
        jTextFieldCantidadElementos.setFont(fontReferencias);

        //Genración de la tabla
        String []columns = {"Direccion","Descripcion","Tipo","Ram","Apuntador"};


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
                    int tamañoGame = Integer.parseInt(jTextFieldCantidadElementos.getText());

                    DefaultTableModel defaultTableModel = new DefaultTableModel(0, tamañoGame);
                    jTableGame = new JTable(defaultTableModel);
                    jTableGame.setFont(fontReferencias);
                    jScrollPaneGame = new JScrollPane(jTableGame);
                    jScrollPaneGame.setBounds(200,70,450,200);
                    jFramePrincipal.add(jScrollPaneGame);

                    Object [][] matrizGame = generaJuego(tamañoGame);
                    for (int i=0; i<tamañoGame; i++){
                        Vector rowAddTable = new Vector();
                        for (int j=0; j<tamañoGame; j++){
                            rowAddTable.add(matrizGame[i][j]);
                        }
                        defaultTableModel.addRow(rowAddTable);
                    }

                }catch (NumberFormatException ed){

                }
			}
		});


		/**********************************************************************/


		/*******Carga de los elementos al contenedor*/
		jFramePrincipal.add(jLabelTitutlo);
		jFramePrincipal.add(jLabelIndicadorCantidad);
		jFramePrincipal.add(jTextFieldCantidadElementos);
		jFramePrincipal.add(jButtonGenerarJuego);
		/*****************************************************************/


		jFramePrincipal.setLocation(100,100);
		jFramePrincipal.setVisible(true);
	}
	
	
	public Object[][] generaJuego(int tamaño){

		//Variable que guarda el tamaño del juego
		int tamañoGame = tamaño * tamaño;

		//Genera las piezas de 1 al tamaño del puzzle
		for (int i = 0; i < tamañoGame; i++) {
			piezasJuego.addElement(String.valueOf(i));
		}
		//Genera un juego aleatoriamente
		Object matriz [][] = new Object[tamaño][tamaño];
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
