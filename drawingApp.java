package canvasJPG;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class drawingApp extends JPanel {

	private int lastX,lastY;
	private BufferedImage image;
	
	public drawingApp() {
		setPreferredSize(new Dimension(800,600));
		setBackground(Color.cyan);
		image = new BufferedImage(800,600, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(Color.yellow);
		g.fillRect(0,0,800,600);
		
		addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent e) {
		        lastX = e.getX();
		        lastY = e.getY();
		    
		    }
		});
		
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
	
	 protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, null);
	    }
	 
	public void saveDrawing() {
		try {
			File savedImage = new File("drawing.jpg");
			  ImageIO.write(image, "drawing.jpg",savedImage);
		}
		catch(Exception e) {
			System.out.println("Couldn't save!");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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

