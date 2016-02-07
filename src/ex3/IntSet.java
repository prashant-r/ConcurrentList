package ex3;

public interface IntSet {
	// These methods should need to be thread-safe(i.e. well-synchronized)
	public boolean insert(int x);
	public boolean remove(int x);
	public boolean contain(int x);
}
