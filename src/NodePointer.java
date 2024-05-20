import java.util.Arrays;

public class NodePointer {

	private Figure[][] f;
	private NodePointer next;
	
	public NodePointer() {
		
	}
	
	public NodePointer(Figure[][] f) {
		Figure[][] copy = Arrays.stream(f).map(Figure[]::clone).toArray(Figure[][]::new);
		this.f = copy;
	}
	
	public Figure[][] getFigure() {
		return this.f;
	}
	
	public void setNext(NodePointer node) {
		this.next = node;
	}
	
	public NodePointer getNext() {
		return this.next;
	}
}