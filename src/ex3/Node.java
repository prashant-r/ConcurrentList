package ex3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node {
	
	public final Lock lock;
	public int key;
	public Node next;
	public Node(int key) {
		super();
		this.key = key;
		this.next = null;
		lock = new ReentrantLock(true);
	}
}

