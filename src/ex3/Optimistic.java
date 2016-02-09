package ex3;

import java.util.concurrent.atomic.AtomicInteger;

public class Optimistic implements IntSet {
	
	private final AtomicInteger size;
	Node head;
	public Optimistic()
	{
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		head.next.next = null;
		size = new AtomicInteger(0);
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
						size.incrementAndGet();
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
						size.decrementAndGet();
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
	@Override
	public AtomicInteger size() {
		return size;
	}
	
	public int getSizeByIterating() {
		Node temp = head;
		int counter =0;
		while(temp.next.next!= null)
		{
			temp = temp.next;
			counter++;
		}
		return counter;
	}
}
