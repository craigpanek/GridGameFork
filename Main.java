/**
 * Copyright 2014 Peter "Felix" Nguyen, Craig Panek
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) throws IOException {
		JFrame frame = new Map("Game2D");
		frame.setSize(1200, 660);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setVisible(true);
	}		
}

		// SEE NOTES BELOW

		// REEVAULATE WHETHER 4 LINES BELOW ARE NECESSARY
		
		//scrollPane.ssetVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//scrollPane.setPreferredSize(new Dimension(1200, 660)); // default size
		//frame.setLayout(new GridBagLayout());


		// INSTEAD OF ITEM BELOW ACTION MENU IS MUTED WHEN MOUSE OUTSIDE GAME AREA
		
		// listener to reposition the action menu whenever the JFrame is moved
		// by user
		/*frame.addComponentListener(new ComponentAdapter() {

			public void componentMoved(ComponentEvent e) {
				Point point = new Point((int) frame.getLocationOnScreen()
						.getX() + 50,
						(int) frame.getLocationOnScreen().getY() + 50);
				Component c = e.getComponent();
				System.out.println(c.getLocation());
				map.setActionMenu(point);
			}
		});*/

		// NOTE: THESE KEYBOARD CONTROLS WERE MOVED INTO MAP CLASS
		// 		AND IMPLEMENTED USING KEY LISTENERS
		
		// keyboard controls to move the map vertically
/*		JScrollBar vScrollBar = scrollPane.getVerticalScrollBar();
		vScrollBar.setUnitIncrement(32);
		InputMap vInput = vScrollBar
				.getInputMap(JComponen
				t.WHEN_IN_FOCUSED_WINDOW);
		vInput.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");
		vInput.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");

		// keyboard controls to move the map horizontally
		JScrollBar hScrollBar = scrollPane.getHorizontalScrollBar();
		hScrollBar.setUnitIncrement(32);
		InputMap hInput = hScrollBar
				.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		hInput.put(KeyStroke.getKeyStroke("LEFT"), "negativeUnitIncrement");
		hInput.put(KeyStroke.getKeyStroke("RIGHT"), "positiveUnitIncrement");
*/
		// WHY DO WE WANT TO MOVE THE MAP?
		
		// mouse controls to drag the map around (not working)
		/*MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JViewport viewPort = scrollPane.getViewport();
				Point vpp = viewPort.getViewPosition();
				vpp.translate(10, 10);
				map.scrollRectToVisible(new Rectangle(vpp, viewPort.getSize()));
				System.out.println("hello");
			}
		};*/

		//scrollPane.getViewport().addMouseListener(mouseAdapter);
		//scrollPane.getViewport().addMouseMotionListener(mouseAdapter);
