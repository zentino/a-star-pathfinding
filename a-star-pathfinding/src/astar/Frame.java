package astar;

import javax.swing.JFrame;

public class Frame {
	private JFrame jframe;
	private AStarLogic aStarLogic;
	private AStarController controller;
	private AStarPanel view;

	public Frame() {
		initGuiAndShow();
	}

	private void initGuiAndShow() {
		jframe = new JFrame("A STAR PATHFINDER");
		aStarLogic = new AStarLogic();
		view = new AStarPanel(aStarLogic);
		// controller = new Controller(model, view);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.add(view);
		jframe.setVisible(true);
		jframe.pack();
	}

	public static void main(String[] args) {
		new Frame();
	}
}
