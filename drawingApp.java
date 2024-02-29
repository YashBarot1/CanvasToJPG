package canvasJPG;
//necessary imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class drawingApp extends JPanel {

	//declaring necessary variables for drawing functionality and UI utilities 
	private int lastX, lastY;
	private BufferedImage image;
	private JButton clearButton, saveButton, exitButton;

	//constructor
	public drawingApp() {

		//setting up a blank canvas
		setPreferredSize(new Dimension(800, 600));
		setBackground(Color.white);
		image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, 800, 600);

		//making buttons for all necessary functions
		clearButton = new JButton("Clear");
		clearButton.addActionListener(e -> clearCanvas());

		saveButton = new JButton("Save");
		saveButton.addActionListener(e -> saveDrawing());

		exitButton = new JButton("Exit");
		exitButton.addActionListener(e -> System.exit(0));

		// Add buttons to a separate panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(exitButton);

		// Add button panel to the main panel
		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.SOUTH);

		//keeping track of the last point for drawLine function
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				lastX = e.getX();
				lastY = e.getY();

			}
		});

		//using a MouseMotionListener to draw
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Graphics g = image.getGraphics();
				g.setColor(Color.black);
				g.drawLine(lastX, lastY, e.getX(), e.getY());
				lastX = e.getX();
				lastY = e.getY();
				repaint();
			}
		});

	}

	//clearCanvas to clearCanvas
	
	private void clearCanvas() {
		// TODO Auto-generated method stub
		Graphics g = image.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, 800, 600);
		repaint();
	}

	//paintComponent function which overloads the default paintComponent
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	//Save Drawing functions which utilises a file chooser 
	public void saveDrawing() {
		try {
			// Prompt the user to select a file location and enter a filename
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showSaveDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File outputFile = fileChooser.getSelectedFile();
				ImageIO.write(image, "jpg", outputFile);
				JOptionPane.showMessageDialog(this, "Drawing saved as " + outputFile.getName(), "Success",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "An error occurred while saving the drawing", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	//main function to finish the setup and test funcitonality 
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Drawing App");
			drawingApp drawingApp = new drawingApp();
			frame.getContentPane().add(drawingApp);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);

		});

	}
}
