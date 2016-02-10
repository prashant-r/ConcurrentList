package ex3;

import java.util.concurrent.atomic.AtomicInteger;

public interface IntSet {
	// These methods are thread-safe(well-synchronized)
	public boolean insert(int x);
	public boolean remove(int x);
	public boolean contain(int x);
	public AtomicInteger size();
}
