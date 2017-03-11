package clasesArboles;


public class Arbol {
	Node raiz;
	//Crea un arbol nuevo con una raiz nula
	public Arbol(){
		raiz = null;
	}

	//Crea un nuevo arbol con nodos
	public Arbol(Node nodo){
		raiz = nodo;
	}
	
	//Agrega un nuevo arbol
	public static Node nuevoArbol(Node nodoPadre, int[][] dato){
			return new Node(nodoPadre, dato);
	}

	//Optine el padre de cualquier nodo
	public Node getRaiz(){
		return raiz;
	}
	
	

}
