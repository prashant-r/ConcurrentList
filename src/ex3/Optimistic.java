package ex3;

public class Optimistic implements IntSet {

	Node head;
	public Optimistic()
	{
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		head.next.next = null;
	}
	public boolean insert(int key) {
		Node pred, curr;
		while(true){
			pred = head;
			curr = pred.next;
			while(curr.key < key && curr.next != null)
			{
				pred = curr;
				curr = curr.next;
			}
			pred.lock.lock();
			curr.lock.lock();
			try{
				if(validate(pred, curr))
				{
					if(curr.key == key)
						return false;
					else
					{
						Node node = new Node(key);
						pred.next = node;
						node.next = curr;
						return true;
					}
				}
			
			}
			finally
			{
				pred.lock.unlock();
				curr.lock.unlock();
			}
		}
	}
	
	public boolean validate(Node pred, Node curr)
	{
		Node node = head;
		while(node.key <= pred.key && node.next!=null)
		{
			if(node == pred)
				return (pred.next == curr);
			node = node.next;
			
		}
		return false;
	}
	
	public boolean remove(int key) {
		Node pred, curr;
		while(true){
			pred = head;
			curr = pred.next;
			while(curr.key < key && curr.next != null)
			{
				pred = curr;
				curr = curr.next;
			}
			pred.lock.lock();
			curr.lock.lock();
			try{
				if(validate(pred, curr))
				{
					if(curr.key == key)
					{
						pred.next = curr.next;
						return true;
					}
					else
					{
						return false;
					}
				}
			
			}
			finally
			{
				pred.lock.unlock();
				curr.lock.unlock();
			}
		}
	}

	public boolean contain(int key) {
		Node pred, curr;
		while(true){
			pred = head;
			curr = pred.next;
			while(curr.key < key && curr.next != null)
			{
				pred = curr;
				curr = curr.next;
			}
			pred.lock.lock();
			curr.lock.lock();
			try{
				if(validate(pred, curr))
				{
					if(curr.key == key)
						return true;
					else
					{
						return false;
					}
				}
			
			}
			finally
			{
				pred.lock.unlock();
				curr.lock.unlock();
			}
		}
	}

}
