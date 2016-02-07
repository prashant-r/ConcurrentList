package ex3;

public class HandOverHand implements IntSet {

	Node head;
	public HandOverHand()
	{
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		head.next.next = null;
	}
	
	public boolean insert(int key) {
		Node curr, pred;
		head.lock.lock();
		pred = head;
		try{
			curr = pred.next;
			curr.lock.lock();
			try
			{
				while(curr.key < key  && curr.next!=null)
				{
					pred.lock.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock.lock();
				}
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
			finally{
				curr.lock.unlock();
			}
		}
		finally{
			pred.lock.unlock();
		}
		
	}

	public boolean remove(int key) {
		Node curr, pred;
		head.lock.lock();
		pred = head;
		try{
			curr = pred.next;
			curr.lock.lock();
			try
			{
				while(curr.key < key  && curr.next!=null)
				{
					pred.lock.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock.lock();
				}
				if(curr.key == key)
				{
					pred.next = curr.next;
					return true;
				}
				else
					return false;
			}
			finally{
				curr.lock.unlock();
		}
		}
		finally{
			pred.lock.unlock();
		}
		
	}

	public boolean contain(int key) {
		Node curr, pred;
		head.lock.lock();
		pred = head;
		try{
			curr = pred.next;
			curr.lock.lock();
			try
			{
				while(curr.key < key  && curr.next!=null)
				{
					pred.lock.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock.lock();
				}
				if(curr.key == key)
					return true;
				return false;
			}
			finally{
				curr.lock.unlock();
			}
		}
		finally{
			pred.lock.unlock();
		}
		
	}

}
