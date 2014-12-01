package se.skoggy.utils;

public abstract class Pool<T> {

	public static final int DEFAULT_CAPACITY = 1024;
	protected int capacity;
	protected int count;
	protected Object[] items;

	public Pool(){
		this(DEFAULT_CAPACITY);
	}

	public Pool(int capacity){
		this.capacity = capacity;
		count = 0;
		items = new Object[capacity];
		for (int i = 0; i < capacity; i++) {
			items[i] = createEmptyObject();
		}
	}

	public void clear(){
		count = 0;
	}

	public int count(){
		return count;
	}

	@SuppressWarnings("unchecked")
	public T get(int index){
		return (T)items[index];
	}

	@SuppressWarnings("unchecked")
	public T pop(){
		return (T)items[count++];
	}

	public void push(int index){
		Object temp = items[index];
		items[index] = items[count - 1];
		items[count - 1] = temp;
		count--;
	}
	public abstract T createEmptyObject();
}
