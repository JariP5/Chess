import java.util.Arrays;

public class LinkedList {

	private NodePointer head = null;
	
	public LinkedList() {
	}
	
	public LinkedList(NodePointer node) {
		this.head = node;
	}
	
	public NodePointer getHead() {
		return this.head;
	}
	
	public void setHead(NodePointer node) {
		this.head = node;
	}
	
	
	public void addToEnd(NodePointer node) {
		if (this.head == null) {
			this.head = node;
		} else {
			NodePointer temp = head;
			// traverse to the last item of the list
			while (temp.getNext() != null) {
				temp = temp.getNext();
			}
			temp.setNext(node);
		}

	}
    
	public int countPositions(Figure[][] figures) {
		NodePointer temp = head;
		
		int counter = 1;
    	
    	if (head == null) {
    		return 0;
    	} else {
    		
    		while(temp.getNext() != null){
    			temp = temp.getNext();
    			if (equal(temp.getFigure(), figures)) {
    				counter ++;
    			}
    		}
    	}
    	
    	return counter;
	}
	
	public static boolean equal(final Figure[][] arr1, final Figure[][] arr2) {
		 
		  if (arr1 == null) {
			  return (arr2 == null);
		  }
		  if (arr2 == null) {
			  return false;
		  }
		  if (arr1.length != arr2.length) {
			  return false;
		  }
		  for (int i = 0; i < arr1.length; i++) {

			  if (!Arrays.equals(arr1[i], arr2[i])) {
				  return false;
			  }
		  }
		  
		  return true;
	}
    
    
    
    
    
	
    
    
}
