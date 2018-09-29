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

		initController();
	}

	private void initController() {
		astarPanel.getStartButton().addActionListener((e)->startPathfinder(e));
	}

	private void startPathfinder(ActionEvent e) {
		if(!astarPanel.isPathFinderStart()) {
			astarPanel.setPathFinderStart(true);
			((JButton)e.getSource()).setText("Stop");
		} else {
			astarPanel.setPathFinderStart(false);
			((JButton)e.getSource()).setText("Start");
		}
	}

	private MouseAdapter getMouseAdapter() {
		// TODO implement this method
		return null;
	}
}
