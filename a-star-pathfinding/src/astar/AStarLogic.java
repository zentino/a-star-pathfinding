package astar;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class AStarLogic {

	private int columns = 25;
	private int rows = 25;
	// Grid
	private Node[][] nodes;
	private Node startNode;
	private Node endNode;
	private boolean pathFound = false;

	// Keep track of an expanding ring called the frontier
	private Queue<Node> frontier = new ArrayDeque<Node>();
	// Keep track of all nodes that were visited
	private List<Node> visited = new ArrayList<>();
	// Used to reconstruct the path
	private List<Node> path = new ArrayList<>();

	public AStarLogic() {
		nodes = new Node[columns][rows];
		init();
		findNeighbors();
		startNode = nodes[2][9];
		startNode.setStart(true);
		endNode = nodes[14][7];
		endNode.setEnd(true);

		frontier.offer(startNode);
		visited.add(startNode);
	}

	/**
	 * Pick and remove a node (location) from the frontier. Expand the frontier
	 * by looking at the neighbors of the current node. If a neighbor wasn't
	 * visited, add it to the visited list and frontier queue.
	 */
	public void findPath() {
		if (!frontier.isEmpty() && !pathFound) {
			Node current = frontier.poll();
			// If current == goal
			if (current.getX() == endNode.getX() && current.getY() == endNode.getY()) {
				pathFound = true;
				return;
			}
			for (Node nextNode : current.getNeighbors()) {
				if (!visited.contains(nextNode)) {
					nextNode.setParent(current);
					frontier.offer(nextNode);
					visited.add(nextNode);
				}
			}
		}
	}

	/**
	 * Reconstruct the path from goal to start
	 */
	public void reconstructPath() {
		Node current = endNode;
		while (!current.isStart()) {
			path.add(current);
			current = current.getParent();
		}
	}

	public void init() {
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				nodes[x][y] = new Node(x, y);
			}
		}
	}

	/**
	 * Find all horizontal/vertical and diagonal neighbors of every node. All
	 * nodes on the edge of the grid have either 3 or 5 neighbors. All other
	 * nodes have 8.
	 */
	public void findNeighbors() {
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				Node current = nodes[x][y];
				if (y - 1 >= 0) {
					current.addNeighbor(nodes[x][y - 1]);
				}
				// if(y - 1 >= 0 && x - 1 >= 0) {
				// current.addNeighbor(nodes[x - 1][y - 1]);
				// }
				if (x - 1 >= 0) {
					current.addNeighbor(nodes[x - 1][y]);
				}
				// if(x - 1 >= 0 && y + 1 < rows) {
				// current.addNeighbor(nodes[x - 1][y + 1]);
				// }
				if (y + 1 < rows) {
					current.addNeighbor(nodes[x][y + 1]);
				}
				// if(x + 1 < columns && y + 1 < rows) {
				// current.addNeighbor(nodes[x + 1][y + 1]);
				// }
				if (x + 1 < columns) {
					current.addNeighbor(nodes[x + 1][y]);
				}
				// if(x + 1 < columns && y - 1 >= 0) {
				// current.addNeighbor(nodes[x + 1][y - 1]);
				// }
			}
		}
	}

	// ---------------------- GETTERS AND SETTERS ----------------------

	public int getColumns() { return columns; }

	public int getRows() { return rows; }

	public Queue<Node> getFrontier() { return frontier; }

	public List<Node> getVisited() { return visited; }

	public List<Node> getPath() { return path; }

	public Node getStartNode() { return startNode; }

	public Node getEndNode() { return endNode;}

	public boolean isPathFound() { return pathFound; }
}
