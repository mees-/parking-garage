package parkinggarage.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Canvas;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import parkinggarage.model.CarType;
import parkinggarage.model.Spot;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;

public class View extends Composite {
	private int floors;
	private int rows;
	private int places;
	parkinggarage.model.Spot[] spots;
	Image img;
	
	private Label waitingCars;
	private Label freeSpotsText;
	private Label freeSpots;
	private Label dayTime;
	
	Canvas carsCanvas;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public View(Composite parent, int style, int floors, int rows, int places) {
		super(parent, style);
		this.floors = floors;
		this.rows = rows;
		this.places = places;
		
		setLayout(null);
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setBounds(0, 0, 800, 600);
		
		TabItem tbtmGarage_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmGarage_1.setText("Garage");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tbtmGarage_1.setControl(composite);
		composite.setLayout(null);
		
		waitingCars = new Label(composite, SWT.BORDER);
		waitingCars.setText("13");
		waitingCars.setBounds(120, 10, 76, 15);
		
		carsCanvas = new Canvas(composite, SWT.BORDER | SWT.DOUBLE_BUFFERED);
		carsCanvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				redrawGarage(arg0);
			}
		});
		carsCanvas.setBounds(10, 31, 772, 501);
		
		freeSpotsText = new Label(composite, SWT.NONE);
		freeSpotsText.setText("Aantal vrije plekken:");
		freeSpotsText.setBounds(233, 10, 112, 15);
		
		freeSpots = new Label(composite, SWT.BORDER);
		freeSpots.setText("587");
		freeSpots.setBounds(351, 10, 76, 15);
		
		Label waitingCarsText = new Label(composite, SWT.NONE);
		waitingCarsText.setBounds(10, 10, 104, 15);
		waitingCarsText.setText("Aantal wachtende:");
		
		Label lblUnplanned = new Label(composite, SWT.CENTER);
		lblUnplanned.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		lblUnplanned.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblUnplanned.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblUnplanned.setBounds(10, 538, 104, 24);
		lblUnplanned.setText("Unplanned");
		
		Label lblSubscribers = new Label(composite, SWT.CENTER);
		lblSubscribers.setText("Subscribers");
		lblSubscribers.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblSubscribers.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblSubscribers.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblSubscribers.setBounds(120, 538, 104, 24);
		
		Label lblFreeSpots = new Label(composite, SWT.CENTER);
		lblFreeSpots.setText("Free spots");
		lblFreeSpots.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFreeSpots.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblFreeSpots.setBackground(SWTResourceManager.getColor(50, 205, 50));
		lblFreeSpots.setBounds(230, 538, 104, 24);
		
		dayTime = new Label(composite, SWT.NONE);
		dayTime.setBounds(637, 10, 145, 15);
		dayTime.setAlignment(SWT.RIGHT);
		dayTime.setText("Monday 7:35");
		
		TabItem tbtmAgenda = new TabItem(tabFolder, SWT.NONE);
		tbtmAgenda.setText("Agenda");
		
		TabItem tbtmGraphs = new TabItem(tabFolder, SWT.NONE);
		tbtmGraphs.setText("Graphs");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void updateView(parkinggarage.model.Spot[] spots) {
		this.spots = spots;
		if(isDisposed())
			return;
		carsCanvas.redraw();
	}
	
	public void UpdateParkingInfo(int freeSpots, int carsWaiting) {
		if(isDisposed())
			return;
		this.freeSpots.setText(freeSpots + " spots");
		this.freeSpots.redraw();
		this.waitingCars.setText(carsWaiting + " cars");
		this.waitingCars.redraw();
	}
	
	public void UpdateDayTimeInfo(String dayTimeInfo) {
		
	}
	
	private void redrawGarage(PaintEvent arg) {
		Device device = Display.getCurrent ();
		
		if(isDisposed())
			return;
		Rectangle rect = getBounds();
		float scalex = ((float)rect.width) / (( floors - 1) * rows * (25 + rows / 2 * 3) + rows * (20 + rows / 2 * 3) + ((rows + 1) % 2) * 3 + 10 + floors * 3);
		float scaley = 1;//((float)rect.height) / (places * 10 + 10);
		PaletteData palette = new PaletteData(0xFF , 0xFF00 , 0xFF0000);
		ImageData imageData = new ImageData(rect.width, rect.height, 24, palette);
		int width = (int) Math.floor(19 * scalex);
        int height = rect.height / places - 1;
		for(int floor = 0; floor < floors; floor++) {
            for(int row = 0; row < rows; row++)  {
                for(int place = 0; place < places; place++) {
                    int i = (floor * rows * places) + (row * places) + place;
                    
                    Spot spot = null;
                    if(spots != null && i < spots.length)
                    	spot = spots[i];
                    
                    parkinggarage.model.Car car = spot == null ? null : spot.getCar();
                    int color = car == null ? 0x32cd32 : car.getType() == CarType.UNPLANNED ? 0xff0000 : 0x0000ff;
                    
                    int x = (int) Math.floor((floor * rows * (25 + rows / 2 * 3) + row * (20 + rows / 2 * 3) + ((row + 1) % 2) * 3 + floor * 3) * scalex) + 10;
                    int y = place *  height + place;
                    
                    for (int loopx = 0; loopx < width; loopx++){
                    	for(int loopy = 0; loopy < height; loopy++){
                    		imageData.setPixel(x + loopx, y + loopy, color);
                    	}
                   	}
                 }
            }
        }
		if(img != null)
		img.dispose();
		img = new Image(device, imageData);
		
		arg.gc.drawImage(img, 0, 0);
    }
}
