package astar;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class AStarController {

	private AStarLogic astarLogic;
	private AStarPanel astarPanel;

	public AStarController(AStarLogic astarLogic, AStarPanel astarPanel) {
		this.astarLogic = astarLogic;
		this.astarPanel = astarPanel;
		astarPanel.addMouseListener(getMouseAdapter());
		initController();
	}

	private void initController() {
		astarPanel.getStartButton().addActionListener((e) -> startPathfinder(e));
	}

	private void startPathfinder(ActionEvent e) {
		if (!astarPanel.isPathFinderStart()) {
			astarPanel.setPathFinderStart(true);
			((JButton) e.getSource()).setText("Stop");
		} else {
			astarPanel.setPathFinderStart(false);
			((JButton) e.getSource()).setText("Start");
		}
	}

	private MouseAdapter getMouseAdapter() {
		return new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				int x = me.getX() / (AStarPanel.PANEL_WIDTH / astarLogic.getColumns());
				int y = me.getY() / (AStarPanel.PANEL_HEIGHT / astarLogic.getRows());
				// If the left mouse button was pressed then block the node
				if (me.getButton() == MouseEvent.BUTTON1) {
					astarLogic.addBlockedNode(x, y);
					astarPanel.reDraw();

				// If the right mouse button was pressed then unblock the node
				} else if (me.getButton() == MouseEvent.BUTTON3) {
					astarLogic.removeBlockedNode(x,y);
					astarPanel.reDraw();
				}
			}
		};
	}
}
