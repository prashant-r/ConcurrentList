package ex3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CoarseGrained implements IntSet{
	private Node head;
	private Lock lock = new ReentrantLock();
	
	public CoarseGrained()
	{
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		head.next.next = null;
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

}
