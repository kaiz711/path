package map;

import java.util.LinkedList;

public class Dot{
	int x, y;
	LinkedList<Dot> path = new LinkedList<Dot>();
	public Dot(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Dot(int x, int y, LinkedList<Dot> path) {
		this.x=x;
		this.y=y;
		this.path.addAll(path);
		this.path.add(new Dot(x, y));
	}
}