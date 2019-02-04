package parkinggarage.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Canvas;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.wb.swt.SWTResourceManager;
import org.swtchart.*;
import org.swtchart.ISeries.SeriesType;

import parkinggarage.model.CarType;
import parkinggarage.model.Spot;
import parkinggarage.util.Settings;

import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.layout.FillLayout;

public class View extends Composite {
	private static final int int2DoubleMultiplier = 1000;
	private int floors;
	private int rows;
	private int places;
	private Settings settings;
	parkinggarage.model.Spot[] spots;
	Image img;
	
	private Label waitingCars;
	private Label freeSpotsText;
	private Label freeSpots;
	private Label dayTime;
	
	private Chart chart;
	
	Canvas carsCanvas;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public View(Composite parent, int style, int floors, int rows, int places, Settings settings) {
		super(parent, style);
		this.floors = floors;
		this.rows = rows;
		this.places = places;
		this.settings = settings;
		
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
		
		TabItem tbtmGraphs = new TabItem(tabFolder, SWT.NONE);
		tbtmGraphs.setText("Graphs");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmGraphs.setControl(composite_1);
		
		chart = new Chart(composite_1, SWT.NONE);
		chart.setBounds(10, 10, 772, 552);
		
		TabItem tbtmSettings = new TabItem(tabFolder, SWT.NONE);
		tbtmSettings.setText("Settings");
		
		Composite settingsTab = new Composite(tabFolder, SWT.NONE);
		tbtmSettings.setControl(settingsTab);
		settingsTab.setLayout(new FillLayout(SWT.VERTICAL));
		
		// Settings
		
		Composite horizontalLayout = new Composite(settingsTab, SWT.NONE);
		horizontalLayout.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		//<----------------------------------------------------------->
		Composite weekdayUnplannedArivalsComp = new Composite(horizontalLayout, SWT.NONE);
		
		Label label = new Label(weekdayUnplannedArivalsComp, SWT.NONE);
		label.setBounds(186, 43, 100, 15);
		
		Scale scale = new Scale(weekdayUnplannedArivalsComp, SWT.NONE);
		scale.setBounds(10, 30, 170, 42);
		scale.setMaximum(500);
		scale.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Scale sc = (Scale) arg0.widget;
				label.setText(sc.getSelection() + " (0 - " + sc.getMaximum() + ")");
				settings.setWeekDayUnplannedArrivals(sc.getSelection());
			}
		});
		scale.setSelection(100);
		label.setText(scale.getSelection() + " (0 - " + scale.getMaximum() + ")");
		
		Label lblTimeBetweenTicksmiliseconds = new Label(weekdayUnplannedArivalsComp, SWT.NONE);
		lblTimeBetweenTicksmiliseconds.setBounds(10, 10, 250, 15);
		lblTimeBetweenTicksmiliseconds.setText("weekday Unplanned Arivals");

		//<----------------------------------------------------------->
		Composite weekendUnplannedArrivalsComp = new Composite(horizontalLayout, SWT.NONE);
		
		Label label_1 = new Label(weekendUnplannedArrivalsComp, SWT.NONE);
		label_1.setBounds(186, 43, 100, 15);
		
		Scale scale_1 = new Scale(weekendUnplannedArrivalsComp, SWT.NONE);
		scale_1.setBounds(10, 30, 170, 42);
		scale_1.setMaximum(500);
		scale_1.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Scale sc = (Scale) arg0.widget;
				label_1.setText(sc.getSelection() + " (0 - " + sc.getMaximum() + ")");
				settings.setWeekendUnplannedArrivals(sc.getSelection());
			}
		});
		scale_1.setSelection(200);
		label_1.setText(scale_1.getSelection() + " (0 - " + scale_1.getMaximum() + ")");
		
		Label label_2 = new Label(weekendUnplannedArrivalsComp, SWT.NONE);
		label_2.setText("weekend Unplanned Arrivals");
		label_2.setBounds(10, 10, 250, 15);

		//<----------------------------------------------------------->
		Composite composite_4 = new Composite(settingsTab, SWT.NONE);
		composite_4.setLayout(new FillLayout(SWT.HORIZONTAL));

		//<----------------------------------------------------------->
		Composite weekDaySubscriberArrivalsComp = new Composite(composite_4, SWT.NONE);
		
		Label label_3 = new Label(weekDaySubscriberArrivalsComp, SWT.NONE);
		label_3.setBounds(186, 43, 100, 15);
		
		Scale scale_2 = new Scale(weekDaySubscriberArrivalsComp, SWT.NONE);
		scale_2.setBounds(10, 30, 170, 42);
		scale_2.setMaximum(500);
		scale_2.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Scale sc = (Scale) arg0.widget;
				label_3.setText(sc.getSelection() + " (0 - " + sc.getMaximum() + ")");
				settings.setWeekDaySubscriberArrivals(sc.getSelection());
			}
		});
		scale_2.setSelection(50);
		label_3.setText(scale_2.getSelection() + " (0 - " + scale_2.getMaximum() + ")");
		
		Label label_4 = new Label(weekDaySubscriberArrivalsComp, SWT.NONE);
		label_4.setText("weekDay Subscriber Arrivals");
		label_4.setBounds(10, 10, 250, 15);

		//<----------------------------------------------------------->
		Composite weekendDaySubscriberArrivalsComp = new Composite(composite_4, SWT.NONE);
		
		Label label_5 = new Label(weekendDaySubscriberArrivalsComp, SWT.NONE);
		label_5.setBounds(186, 43, 100, 15);
		
		Scale scale_3 = new Scale(weekendDaySubscriberArrivalsComp, SWT.NONE);
		scale_3.setBounds(10, 30, 170, 42);
		scale_3.setMaximum(500);
		scale_3.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Scale sc = (Scale) arg0.widget;
				label_5.setText(sc.getSelection() + " (0 - " + sc.getMaximum() + ")");
				settings.setWeekendSubscriberArrivals(sc.getSelection());
			}
		});
		scale_3.setSelection(5);
		label_5.setText(scale_3.getSelection() + " (0 - " + scale_3.getMaximum() + ")");
		
		Label label_6 = new Label(weekendDaySubscriberArrivalsComp, SWT.NONE);
		label_6.setText("weekend Day Subscriber Arrivals");
		label_6.setBounds(10, 10, 250, 15);

		//<----------------------------------------------------------->
		Composite composite_7 = new Composite(settingsTab, SWT.NONE);
		composite_7.setLayout(new FillLayout(SWT.HORIZONTAL));

		//<----------------------------------------------------------->
		Composite subscriberEnterSpeedComp = new Composite(composite_7, SWT.NONE);
		
		Label label_7 = new Label(subscriberEnterSpeedComp, SWT.NONE);
		label_7.setBounds(186, 43, 100, 15);
		
		Scale scale_4 = new Scale(subscriberEnterSpeedComp, SWT.NONE);
		scale_4.setBounds(10, 30, 170, 42);
		scale_4.setMaximum(20);
		scale_4.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Scale sc = (Scale) arg0.widget;
				label_7.setText(sc.getSelection() + " (0 - " + sc.getMaximum() + ")");
				settings.setSubscriberEnterSpeed(sc.getSelection());
			}
		});
		scale_4.setSelection(3);
		label_7.setText(scale_4.getSelection() + " (0 - " + scale_4.getMaximum() + ")");
		
		Label label_8 = new Label(subscriberEnterSpeedComp, SWT.NONE);
		label_8.setText("subscriber Enter Speed");
		label_8.setBounds(10, 10, 250, 15);

		//<----------------------------------------------------------->
		Composite unplannedEnterSpeedComp = new Composite(composite_7, SWT.NONE);
		
		Label label_9 = new Label(unplannedEnterSpeedComp, SWT.NONE);
		label_9.setBounds(186, 43, 100, 15);
		
		Scale scale_5 = new Scale(unplannedEnterSpeedComp, SWT.NONE);
		scale_5.setBounds(10, 30, 170, 42);
		scale_5.setMaximum(20);
		scale_5.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Scale sc = (Scale) arg0.widget;
				label_9.setText(sc.getSelection() + " (0 - " + sc.getMaximum() + ")");
				settings.setUnplannedEnterSpeed(sc.getSelection());
			}
		});
		scale_5.setSelection(3);
		label_9.setText(scale_5.getSelection() + " (0 - " + scale_5.getMaximum() + ")");
		
		Label label_10 = new Label(unplannedEnterSpeedComp, SWT.NONE);
		label_10.setText("unplanned Enter Speed");
		label_10.setBounds(10, 10, 250, 15);

		//<----------------------------------------------------------->
		Composite composite_10 = new Composite(settingsTab, SWT.NONE);
		composite_10.setLayout(new FillLayout(SWT.HORIZONTAL));

		//<----------------------------------------------------------->
		Composite paymentSpeedComp = new Composite(composite_10, SWT.NONE);
		
		Label label_11 = new Label(paymentSpeedComp, SWT.NONE);
		label_11.setBounds(186, 43, 100, 15);
		
		Scale scale_6 = new Scale(paymentSpeedComp, SWT.NONE);
		scale_6.setBounds(10, 30, 170, 42);
		scale_6.setMaximum(20);
		scale_6.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Scale sc = (Scale) arg0.widget;
				label_11.setText(sc.getSelection() + " (0 - " + sc.getMaximum() + ")");
				settings.setPaymentSpeed(sc.getSelection());
			}
		});
		scale_6.setSelection(7);
		label_11.setText(scale_6.getSelection() + " (0 - " + scale_6.getMaximum() + ")");
		
		Label label_12 = new Label(paymentSpeedComp, SWT.NONE);
		label_12.setText("payment Speed");
		label_12.setBounds(10, 10, 250, 15);

		//<----------------------------------------------------------->
		Composite exitSpeedComp = new Composite(composite_10, SWT.NONE);
		
		Label label_13 = new Label(exitSpeedComp, SWT.NONE);
		label_13.setBounds(186, 43, 100, 15);
		
		Scale scale_7 = new Scale(exitSpeedComp, SWT.NONE);
		scale_7.setBounds(10, 30, 170, 42);
		scale_7.setMaximum(20);
		scale_7.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Scale sc = (Scale) arg0.widget;
				label_13.setText(sc.getSelection() + " (0 - " + sc.getMaximum() + ")");
				settings.setExitSpeed(sc.getSelection());
			}
		});
		scale_7.setSelection(5);
		label_13.setText(scale_7.getSelection() + " (0 - " + scale_7.getMaximum() + ")");
		
		Label label_14 = new Label(exitSpeedComp, SWT.NONE);
		label_14.setText("exit Speed");
		label_14.setBounds(10, 10, 250, 15);

		//<----------------------------------------------------------->
		Composite composite_13 = new Composite(settingsTab, SWT.NONE);
		composite_13.setLayout(new FillLayout(SWT.HORIZONTAL));

		//<----------------------------------------------------------->
		Composite tickPauseComp = new Composite(composite_13, SWT.NONE);
		
		Label label_15 = new Label(tickPauseComp, SWT.NONE);
		label_15.setBounds(186, 43, 100, 15);
		
		Scale scale_8 = new Scale(tickPauseComp, SWT.NONE);
		scale_8.setBounds(10, 30, 170, 42);
		scale_8.setMaximum(1000);
		scale_8.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Scale sc = (Scale) arg0.widget;
				label_15.setText(sc.getSelection() + " (0 - " + sc.getMaximum() + ")");
				settings.setTickPause(sc.getSelection());
			}
		});
		scale_8.setSelection(50);
		label_15.setText(scale_8.getSelection() + " (0 - " + scale_8.getMaximum() + ")");
		
		Label label_16 = new Label(tickPauseComp, SWT.NONE);
		label_16.setText("tick Pause");
		label_16.setBounds(10, 10, 250, 15);

		//<----------------------------------------------------------->
		Composite reservationShowChanceComp = new Composite(composite_13, SWT.NONE);
		
		Label label_17 = new Label(reservationShowChanceComp, SWT.NONE);
		label_17.setBounds(186, 43, 100, 15);
		
		Scale scale_9 = new Scale(reservationShowChanceComp, SWT.NONE);
		scale_9.setBounds(10, 30, 170, 42);
		scale_9.setMaximum(10 * int2DoubleMultiplier);
		scale_9.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Scale sc = (Scale) arg0.widget;
				label_17.setText(sc.getSelection() / (double)int2DoubleMultiplier + " (0 - " + sc.getMaximum() / (double)int2DoubleMultiplier + ")");
				settings.setReservationShowChance(sc.getSelection() / (double)int2DoubleMultiplier);
			}
		});
		scale_9.setSelection(3 * 1000);
		label_17.setText(scale_9.getSelection() / (double)int2DoubleMultiplier + " (0 - " + scale_9.getMaximum() / (double)int2DoubleMultiplier + ")");
		
		Label label_18 = new Label(reservationShowChanceComp, SWT.NONE);
		label_18.setText("reservation Show Chance");
		label_18.setBounds(10, 10, 250, 15);

		//<----------------------------------------------------------->
		Composite composite_16 = new Composite(settingsTab, SWT.NONE);
		composite_16.setLayout(new FillLayout(SWT.HORIZONTAL));

		//<----------------------------------------------------------->
		Composite queueLeaveThresholdComp = new Composite(composite_16, SWT.NONE);
		
		Label label_19 = new Label(queueLeaveThresholdComp, SWT.NONE);
		label_19.setBounds(186, 43, 100, 15);
		
		Scale scale_10 = new Scale(queueLeaveThresholdComp, SWT.NONE);
		scale_10.setBounds(10, 30, 170, 42);
		scale_10.setMaximum(10 * int2DoubleMultiplier);
		scale_10.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Scale sc = (Scale) arg0.widget;
				label_19.setText(sc.getSelection() / (double)int2DoubleMultiplier + " (0 - " + sc.getMaximum() / (double)int2DoubleMultiplier + ")");
				settings.setQueueLeaveThreshold(sc.getSelection() / (double)int2DoubleMultiplier);
			}
		});
		scale_10.setSelection(5 * 1000);
		label_19.setText(scale_10.getSelection() / (double)int2DoubleMultiplier + " (0 - " + scale_10.getMaximum() / (double)int2DoubleMultiplier + ")");
		
		Label label_20 = new Label(queueLeaveThresholdComp, SWT.NONE);
		label_20.setText("queue Leave Threshold");
		label_20.setBounds(10, 10, 250, 15);

		//<----------------------------------------------------------->
		Composite queueLeaveScalingComp = new Composite(composite_16, SWT.NONE);
		
		Label label_21 = new Label(queueLeaveScalingComp, SWT.NONE);
		label_21.setBounds(186, 43, 100, 15);
		
		Scale scale_11 = new Scale(queueLeaveScalingComp, SWT.NONE);
		scale_11.setBounds(10, 30, 170, 42);
		scale_11.setMaximum(10 * int2DoubleMultiplier);
		scale_11.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Scale sc = (Scale) arg0.widget;
				label_21.setText(sc.getSelection() / (double)int2DoubleMultiplier + " (0 - " + sc.getMaximum() / (double)int2DoubleMultiplier + ")");
				settings.setQueueLeaveScaling(sc.getSelection() / (double)int2DoubleMultiplier);
			}
		});
		scale_11.setSelection(8 * 1000);
		label_21.setText(scale_11.getSelection() / (double)int2DoubleMultiplier + " (0 - " + scale_11.getMaximum() / (double)int2DoubleMultiplier + ")");
		
		Label label_22 = new Label(queueLeaveScalingComp, SWT.NONE);
		label_22.setText("queue Leave Scaling");
		label_22.setBounds(10, 10, 250, 15);
		//<----------------------------------------------------------->
		
		chart.getTitle().setText("Line Chart");
		chart.getAxisSet().getXAxis(0).getTitle().setText("Data Points");
		chart.getAxisSet().getYAxis(0).getTitle().setText("Amplitude");
	}
	
	public void updateView(parkinggarage.model.Spot[] spots) {
		this.spots = spots;
		if(isDisposed())
			return;
		carsCanvas.redraw();
	}
	
	public void updateParkingInfo(int freeSpots, int carsWaiting) {
		if(isDisposed())
			return;
		this.freeSpots.setText(freeSpots + " spots");
		this.freeSpots.redraw();
		this.waitingCars.setText(carsWaiting + " cars");
		this.waitingCars.redraw();
	}
	
	public void updateDayTimeInfo(String dayTimeInfo) {
		
	}
	
	public void updateTestGraph(double[] data, double max) {
		chart.getAxisSet().getXAxis(0).setRange(new Range(0, data.length));
		chart.getAxisSet().getYAxis(0).setRange(new Range(0, max));
		ILineSeries lineSeries = (ILineSeries)chart.getSeriesSet().createSeries(SeriesType.LINE, "line series");
		lineSeries.setYSeries(data);
		chart.getAxisSet().adjustRange();
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
        int height = rect.height / places - 5;
		for(int floor = 0; floor < floors; floor++) {
            for(int row = 0; row < rows; row++)  {
                for(int place = 0; place < places; place++) {
                    int i = (floor * rows * places) + (row * places) + place;
                    
                    Spot spot = null;
                    if(spots != null && i < spots.length)
                    	spot = spots[i];
                    
                    parkinggarage.model.Car car = spot == null ? null : spot.getCar();
                    int color = car == null ? 0x32cd32 : car.getType() == CarType.UNPLANNED ? 0xff0000 : 0x0000ff;
                    
                    int x = (int) Math.floor((floor * rows * (25 + rows / 2 * 3) + row * (20 + rows / 2 * 3) + ((row + 1) % 2) * 3 + floor * 3) * scalex) + 7;
                    int y = place *  height + place;
                    
                    for (int loopx = 0; loopx < width; loopx++){
                    	for(int loopy = 0; loopy < height; loopy++){
                    		imageData.setPixel(x + loopx, y + loopy + 9, color);
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
