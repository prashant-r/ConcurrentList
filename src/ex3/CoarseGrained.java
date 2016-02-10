package ex3;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/*
 * Based on implementation details provided in chapter 9 of The Art of Multiprocessor Programming by Maurice Herlihy  (Author), Nir Shavit  (Author)
 */
public class CoarseGrained implements IntSet{
	private Node head;
	private Lock lock = new ReentrantLock();
	private final AtomicInteger size;
	
	public CoarseGrained()
	{
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		head.next.next = null;
		size = new AtomicInteger(0);
	}
	public boolean insert(int key) {
		Node pred, curr;
		lock.lock();
		try
		{
			pred = head;
			curr = pred.next;
			while(curr.key < key && curr.next!=null)
			{
				pred = curr;
				curr = curr.next;
			}
			if(key == curr.key)
				return false;
			else
			{
				Node nodeToAdd = new Node(key);
				pred.next = nodeToAdd;
				nodeToAdd.next = curr;
				size.incrementAndGet();
				return true;
			}
		}
		finally
		{
			lock.unlock();
		}
	}

	public boolean remove(int x) {
		Node pred, curr;
		int key =x;
		lock.lock();
		try
		{
			pred = head;
			curr = pred.next;
			while(curr.key < key && curr.next!=null)
			{
				pred = curr;
				curr = curr.next;
			}
			if(key == curr.key)
			{
				pred.next = curr.next;
				size.decrementAndGet();
				return true;
			}
			else
				return false;
		}
		finally
		{
			lock.unlock();
		}
	}

	public boolean contain(int x) {
		Node pred, curr;
		int key =x;
		lock.lock();
		try
		{
			pred = head;
			curr = pred.next;
			while(curr.key < key && curr.next!=null)
			{
				pred = curr;
				curr = curr.next;
			}
			return key == curr.key;
		}
		finally
		{
			lock.unlock();
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
