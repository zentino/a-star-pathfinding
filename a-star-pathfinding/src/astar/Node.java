package astar;

import java.util.ArrayList;
import java.util.List;

public class Node {
	// Coordinates
	private int x, y;
	// Parent node to reconstruct the path
	private Node parent;
	private boolean start;
	private boolean end;
	private List<Node> neighbors = new ArrayList<>();

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {return x;}

	public void setX(int x) {this.x = x;}

	public int getY() {return y;}

	public void setY(int y) {this.y = y;}

	public void addNeighbor(Node neighbor) {neighbors.add(neighbor);}

	public List<Node> getNeighbors() {return this.neighbors;}

	public boolean isEnd() {return end;}

	public void setEnd(boolean end) {this.end = end;}

	public boolean isStart() {return start;}

	public void setStart(boolean start) {this.start = start;}

	public Node getParent() {return parent;}

	public void setParent(Node parent) { this.parent = parent; }
}
