package ex3;

import java.util.concurrent.atomic.AtomicInteger;

public class HandOverHand implements IntSet {

	Node head;
	private final AtomicInteger size;
	public HandOverHand()
	{
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		head.next.next = null;
		size = new AtomicInteger(0);
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
					size.incrementAndGet();
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
					size.decrementAndGet();
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
