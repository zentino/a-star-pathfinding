package astar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class AStarPanel extends JPanel implements ActionListener {

	public static final int PANEL_WIDTH = 1000;
	public static final int PANEL_HEIGHT = 1000;
	private AStarLogic aStarLogic;
	private int myTimerDelay;
	private Timer myTimer;
	private boolean pathFinderStart = false;

	private JButton startButton;
	private JPanel buttons;

	public AStarPanel(AStarLogic aStarLogic) {
		super();
		this.aStarLogic = aStarLogic;
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		this.setVisible(true);

		// Create UI elements
		buttons = new JPanel();
		startButton = new JButton("Start");
		buttons.add(startButton);
		// Transparent background
		buttons.setOpaque(false);
		buttons.setBounds(0, PANEL_HEIGHT - 50, PANEL_WIDTH, PANEL_HEIGHT);
		this.add(buttons);

		myTimerDelay = 500;
		myTimer = new Timer(myTimerDelay, this);
		myTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(pathFinderStart) {
			if(!aStarLogic.isPathFound()) {
				aStarLogic.findPath();
			} else {
				aStarLogic.reconstructPath();
			}
		}
		reDraw();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawNodes(g);
		g.setColor(Color.GRAY);
		drawGrid(g);
	}

	private void drawGrid(Graphics g) {
		int columns = aStarLogic.getColumns();
		int rows = aStarLogic.getRows();
		int rectWidth = (PANEL_WIDTH / columns);
		int rectHeight = (PANEL_HEIGHT / rows);

		for (int x = 0; x < PANEL_WIDTH; x += rectWidth) {
			for (int y = 0; y < PANEL_HEIGHT; y += rectHeight) {
				g.drawRect(x, y, rectWidth, rectHeight);
			}
		}
	}

	private void drawNodes(Graphics g) {
		int columns = aStarLogic.getColumns();
		int rows = aStarLogic.getRows();
		// Width and height of a rectangle that represents the node
		int rectWidth = (PANEL_WIDTH / columns);
		int rectHeight = (PANEL_HEIGHT / rows);

		// Draw all nodes that were visited
		for (Node node : aStarLogic.getVisited()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(node.getX() * rectWidth, node.getY() * rectHeight, rectWidth, rectHeight);
			// TODO REFACTOR
			drawCosts(node,g);
		}
		// Draw the expanding frontier
		for (Node node : aStarLogic.getFrontier()) {
			g.setColor(Color.CYAN);
			g.fillRect(node.getX() * rectWidth, node.getY() * rectHeight, rectWidth, rectHeight);
			// TODO REFACTOR
			drawCosts(node,g);
		}
		// Draw the path
		for (Node node : aStarLogic.getPath()) {
			g.setColor(Color.YELLOW);
			g.fillRect(node.getX() * rectWidth, node.getY() * rectHeight, rectWidth, rectHeight);
			// TODO REFACTOR
			drawCosts(node,g);
		}

		// Draw the start node
		g.setColor(Color.RED);
		g.fillRect(aStarLogic.getStartNode().getX() * rectWidth, aStarLogic.getStartNode().getY() * rectHeight,
				rectWidth, rectHeight);

		// Draw the end node
		g.setColor(Color.BLUE);
		g.fillRect(aStarLogic.getEndNode().getX() * rectWidth, aStarLogic.getEndNode().getY() * rectHeight, rectWidth,rectHeight);
	}

	/**
	 * Draws all costs
	 * @param current The current node
	 * @param g
	 */
	public void drawCosts(Node current, Graphics g) {
		// TODO REFACTOR
		int columns = aStarLogic.getColumns();
		int rows = aStarLogic.getRows();
		// Width and height of a rectangle that represents the node
		int rectWidth = (PANEL_WIDTH / columns);
		int rectHeight = (PANEL_HEIGHT / rows);
		Font small = new Font("arial", Font.PLAIN, 10);
		g.setColor(Color.black);
		g.setFont(small);
		g.drawString("F" +Integer.toString(current.getFcost()), current.getX() * rectWidth + 1, current.getY() * rectHeight + 10);
		g.drawString("G" +Integer.toString(current.getGcost()), current.getX() * rectWidth + 1, current.getY() * rectHeight + rectHeight);
		g.drawString("H" +Integer.toString(current.getHcost()), current.getX() * rectWidth + 1, current.getY() * rectHeight + rectHeight/2);
	}


	public void reDraw() {
		this.repaint();
	}

	// ---------------------- GETTERS AND SETTERS ----------------------

	public JButton getStartButton() { return this.startButton; }

	public boolean isPathFinderStart() {return pathFinderStart;}

	public void setPathFinderStart(boolean pathFinderStart) { this.pathFinderStart = pathFinderStart; }

}
