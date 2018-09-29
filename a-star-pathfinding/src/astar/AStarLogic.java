package astar;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class AStarLogic {

	private int columns = 25;
	private int rows = 25;
	public static int VERTICAL_HORIZONTAL_MOVEMENT_COST = 10;
	public static int DIAGONAL_MOVEMENT_COST = 14;
	// Grid
	private Node[][] nodes;
	private Node startNode;
	private Node endNode;
	private boolean pathFound = false;

	// Keep track of an expanding ring called the frontier(Open list)
	private PriorityQueue<Node> frontier;
	// Keep track of all nodes that were visited (Closed list)
	private List<Node> visited = new ArrayList<>();
	// Used to reconstruct the path
	private List<Node> path = new ArrayList<>();
	// Keep track of the total movement cost from the start location
	private HashMap<Node, Integer> costSoFar = new HashMap<>();

	private List<Node> blockedNodes = new ArrayList<>();

	public AStarLogic() {
		nodes = new Node[columns][rows];
		init();
		findNeighbors();
		// Set start and end node coordinates
		startNode = nodes[3][12];
		startNode.setStart(true);
		endNode = nodes[21][12];
		endNode.setEnd(true);

		// Define the comparator for the priority queue
		frontier = new PriorityQueue<Node>((node1, node2) -> {
			if (node1.getFcost() < node2.getFcost()) {
				return -1;
			} else if (node1.getFcost() > node2.getFcost()) {
				return 1;
			}
			return 0;
		});
		frontier.offer(startNode);
		visited.add(startNode);
		costSoFar.put(startNode, 0);
	}

	/**
	 * Pick and remove a node (location) from the frontier. Expand the frontier
	 * by looking at the neighbors of the current node. If a neighbor wasn't
	 * visited, add it to the visited list and frontier queue.
	 */
	public void findPath() {
		if (!frontier.isEmpty() && !pathFound) {
			Node current = frontier.poll();

			// If current == goal (early exit)
			if (current.getX() == endNode.getX() && current.getY() == endNode.getY()) {
				pathFound = true;
				return;
			}
			for (Node nextNode : current.getNeighbors()) {
				int newTotalMovementCost = costSoFar.get(current) + getMovementCost(current, nextNode);
				if(!nextNode.isBlocked()) {
					if (!costSoFar.containsKey(nextNode) || newTotalMovementCost < costSoFar.get(nextNode)) {
						costSoFar.put(nextNode, newTotalMovementCost);
						nextNode.setHcost(calculateHeuristicCost(endNode, nextNode));
						nextNode.setGcost(newTotalMovementCost);
						nextNode.setFcost(nextNode.getHcost() + newTotalMovementCost);
						nextNode.setParent(current);
						frontier.offer(nextNode);
						visited.add(nextNode);
					}
				}
			}
		}
	}

	/**
	 * Get Movement costs from a node A to a node B
	 *
	 * @param a
	 * @param b
	 * @return Diagonal neighbor -> diagonal movement cost, Vertical/Horizontal neighbor -> vertical/horizontal movement cost
	 */
	private int getMovementCost(Node a, Node b) {
		if (a.getX() == b.getX() || a.getY() == b.getY()) {
			return VERTICAL_HORIZONTAL_MOVEMENT_COST;
		}
		return DIAGONAL_MOVEMENT_COST;
	}

	/**
	 * Calculates the Manhattan distance on a square grid
	 *
	 * @param goal
	 * @param start
	 * @return heuristic cost
	 */
	private int calculateHeuristicCost(Node goal, Node start) {
		int heuristicCost = (Math.abs(goal.getX() - start.getX())
				+ Math.abs(goal.getY() - start.getY())) * VERTICAL_HORIZONTAL_MOVEMENT_COST;
		return heuristicCost;
	}

	/**
	 * Reconstruct the path from goal to start.
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
		Node current;
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				current = nodes[x][y];

				if (x + 1 < columns && y - 1 >= 0) {
					current.addNeighbor(nodes[x + 1][y - 1]);
				}
				if (x + 1 < columns) {
					current.addNeighbor(nodes[x + 1][y]);
				}
				if (x + 1 < columns && y + 1 < rows) {
					current.addNeighbor(nodes[x + 1][y + 1]);
				}
				if (y + 1 < rows) {
					current.addNeighbor(nodes[x][y + 1]);
				}
				if (x - 1 >= 0 && y + 1 < rows) {
					current.addNeighbor(nodes[x - 1][y + 1]);
				}
				if (x - 1 >= 0) {
					current.addNeighbor(nodes[x - 1][y]);
				}
				if (y - 1 >= 0 && x - 1 >= 0) {
					current.addNeighbor(nodes[x - 1][y - 1]);
				}

				if (y - 1 >= 0) {
					current.addNeighbor(nodes[x][y - 1]);
				}

			}
		}
	}

	public void addBlockedNode(int x, int y) {
		Node node = nodes[x][y];
		node.setBlocked(true);
		blockedNodes.add(node);
	}

	public void removeBlockedNode(int x, int y) {
		Node node = nodes[x][y];
		node.setBlocked(false);
		blockedNodes.remove(node);
	}

	// ---------------------- GETTERS AND SETTERS ----------------------

	public int getColumns() { return columns; }

	public int getRows() { return rows; }

	public PriorityQueue<Node> getFrontier() { return frontier; }

	public List<Node> getVisited() { return visited; }

	public List<Node> getPath() { return path; }

	public Node getStartNode() { return startNode; }

	public Node getEndNode() { return endNode;}

	public boolean isPathFound() { return pathFound; }

	public List<Node> getBlockedNodes() { return blockedNodes; }
}
