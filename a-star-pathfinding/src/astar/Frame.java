package astar;

import javax.swing.JFrame;

public class Frame {
	private JFrame jframe;
	private AStarLogic astarLogic;
	private AStarController astarController;
	private AStarPanel view;

	public Frame() {
		initGuiAndShow();
	}

	private void initGuiAndShow() {
		jframe = new JFrame("A* Pathfinder");
		astarLogic = new AStarLogic();
		view = new AStarPanel(astarLogic);
		astarController = new AStarController(astarLogic, view);
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
