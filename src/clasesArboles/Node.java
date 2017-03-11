package clasesArboles;


public class Node {
	Node nodo;
	int [][] puzzle;
	public int profundidad=0;
	
	/////////Crea un nuevo nodo raiz
	public Node(int [][]puzzle){
		this.puzzle = puzzle;
		this.nodo = null;
	}	
	/////////Crea un nuevo nodo con referencia al padre
	public Node(Node nodo,int[][] puzzle){
		this.nodo = nodo;
		this.puzzle = puzzle;
		
	}
	//Regresa el padre de cualquier nodo
	public Node getPadre(){
		return nodo;
	}
	//Obtiene la profundidad del nodo
	public int getProfundidad(Node n){
		
		if(n!=null){
			profundidad++;
			getProfundidad(n.getPadre());
		}
			return profundidad;
	}
	
	
	//Regresa el puzzle del nodo
	public int [][] getPuzzle(){
		return puzzle;
	}
	
	
}
