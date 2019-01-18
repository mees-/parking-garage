package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;
import parkinggarage.model.*;
import simulation.Car;
import simulation.Location;

public class SimulatorView extends JPanel {
	
	private Image carParkImage;   
	private Dimension size;
	
	private int floors;
    private int rows;
    private int places;
    
    private Spot[] spots;
    
	public SimulatorView(int floors, int rows, int places) {
		size = new Dimension(0, 0);

		this.floors = floors;
		this.rows = rows;
		this.places = places;
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }
	
	public void paintComponent(Graphics g) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }
	
	public void updateView(parkinggarage.model.Spot[] spots) {
		// Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(size.width, size.height);
        }
        
        Graphics graphics = carParkImage.getGraphics();
        for(int floor = 0; floor < floors; floor++) {
            for(int row = 0; row < rows; row++)  {
                for(int place = 0; place < places; place++) {
                    int i = (floor * rows * places) + (row * places) + place;
                    
                    Spot spot = null;
                    if(i < spots.length)
                    	spot = spots[i];
                    
                    parkinggarage.model.Car car = spot == null ? null : spot.getCar();
                    Color color = car == null ? Color.white : car.getType() == CarType.UNPLANNED ? Color.RED : Color.BLUE;
                    Location location = new Location(floor, row, place);
                    drawPlace(graphics, location, color);
                }
            }
        }
        repaint();
    }
	
	private void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1); // TODO use dynamic size or constants
    }
}
