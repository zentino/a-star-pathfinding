package astar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class AStarPanel extends JPanel implements ActionListener {

	public static final int PANEL_WIDTH = 1000;
	public static final int PANEL_HEIGHT = 1000;
	private AStarLogic aStarLogic;
	private int myTimerDelay;
	private Timer myTimer;

	public AStarPanel(AStarLogic aStarLogic) {
		super();
		this.aStarLogic = aStarLogic;
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		this.setVisible(true);

		myTimerDelay = 10;
		myTimer = new Timer(myTimerDelay, this);
		myTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

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
		}
		// Draw the expanding frontier
		for (Node node : aStarLogic.getFrontier()) {
			g.setColor(Color.CYAN);
			g.fillRect(node.getX() * rectWidth, node.getY() * rectHeight, rectWidth, rectHeight);
		}
		// Draw the path
		for (Node node : aStarLogic.getPath()) {
			g.setColor(Color.YELLOW);
			g.fillRect(node.getX() * rectWidth, node.getY() * rectHeight, rectWidth, rectHeight);
		}

		// Draw the start node
		g.setColor(Color.RED);
		g.fillRect(aStarLogic.getStartNode().getX() * rectWidth, aStarLogic.getStartNode().getY() * rectHeight,
				rectWidth, rectHeight);

		// Draw the end node
		g.setColor(Color.BLUE);
		g.fillRect(aStarLogic.getEndNode().getX() * rectWidth, aStarLogic.getEndNode().getY() * rectHeight, rectWidth,
				rectHeight);
	}

	public void reDraw() {
		this.repaint();
	}

}
