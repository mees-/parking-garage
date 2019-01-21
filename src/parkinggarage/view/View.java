package parkinggarage.view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class View extends JFrame{
	public SimulatorView simView;
	
	public View(int floors, int rows, int places) {
		simView = new SimulatorView(floors, rows, places);
		
		Container contentPane = getContentPane();
		contentPane.add(simView, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
	
	public void updateView(parkinggarage.model.Spot[] spots) {
		simView.updateView(spots);
    }
}
