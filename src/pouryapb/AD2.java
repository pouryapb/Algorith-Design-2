package pouryapb;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AD2 extends JFrame{

	private static final long serialVersionUID = 3090441365551554649L;
	private static final int CANVAS_WIDTH  = 640;
	private static final int CANVAS_HEIGHT = 480;
	private Integer[] heights;
	private String answer;
	private static Logger logger = Logger.getLogger(AD2.class.getName());

	private class DrawCanvas extends JPanel {

		private static final long serialVersionUID = -5152092633990740582L;

		@Override
		public void paintComponent(Graphics g) {
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			super.paintComponent(g);
			setBackground(Color.DARK_GRAY);
			
			int startX = CANVAS_WIDTH / 2 - heights.length * 8;
			int startY = 8 * CANVAS_HEIGHT / 10;
			
			g.setColor(Color.white);
			
			String[] answerArray = answer.split(" ");
			var diffArray = new Integer[heights.length + 1];
			
			for (var i = 0; i < diffArray.length; i++) {
				diffArray[i] = Math.abs(Integer.parseInt(answerArray[Math.abs(answerArray.length - 1 + i) % answerArray.length]) - Integer.parseInt(answerArray[i % answerArray.length]));
			}
			
			var maxDiff = String.valueOf(Collections.max(Arrays.asList(diffArray)));
			g.drawString("Answer: " + answer.replace(" ", "  ") + "                       maximum difference: " + maxDiff, 20, 20);
			g.drawString("Visualized version:", 20, 50);
			
			g.setColor(Color.white);
			
			// differences
			
			for (var i = 0; i < heights.length + 1; i++) {
				
				int max = Collections.max(Arrays.asList(heights));
				int x = startX + i * 18 - 12;
				int y = startY - max - 10;
				
				g.drawString(String.valueOf(diffArray[i]), x - (String.valueOf(diffArray[i]).length() - 1) * 3, y - 10);
				
				((Graphics2D)g).rotate(Math.PI / 2, x, y);
				
				g.drawString("{", x, y);
				
				((Graphics2D)g).rotate(-Math.PI / 2, x, y);
				
			}
			
			// differences

			((Graphics2D)g).setStroke(new BasicStroke(10));

			var space = 18;
			
			for (var i = 0; i < heights.length; i++) {
				((Graphics2D)g).drawLine(startX + i * space - (heights.length * space), startY, startX + i * space - (heights.length * space), startY - heights[i]);
			}
			
			g.setColor(Color.decode("#24ad05"));
			for (var i = 0; i < heights.length; i++) {
				((Graphics2D)g).drawLine(startX + i * space, startY, startX + i * space, startY - heights[i]);
			}
			
			g.setColor(Color.white);
			for (var i = 0; i < heights.length; i++) {
				((Graphics2D)g).drawLine(startX + i * space + (heights.length * space), startY, startX + i * space + (heights.length * space), startY - heights[i]);
			}
		}
	}
	
	public void visualize(String str) {
		var canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		
		setContentPane(canvas);
		
		setTitle("Title!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		answer = str;
		
		String[] arr = str.split(" ");
		
		heights = new Integer[arr.length];
		
		for (var i = 0; i < heights.length; i++) {
			heights[i] = Integer.parseInt(arr[i]);
		}
	}
	
	public String solve(int[] arr) {

		sort(arr, 0, arr.length - 1);

		var temp0 = new StringBuilder();
		var temp1 = "";
		int first = (arr.length - 1) % 2;

		for (var i = arr.length - 1; i >= 0; i--) {

			if (i % 2 == first)
				temp0.append((temp0.length() == 0) ? arr[i] : " " + arr[i]);
			else 
				temp1 = (temp1.length() == 0) ? arr[i] + temp1 : arr[i] + " " + temp1;
		}

		return temp0 + " " + temp1;
	}

	private int partition(int[] arr, int low, int high) {

		int pivot = arr[high];
		int i = (low - 1);

		for (int j = low; j < high; j++) {

			if (arr[j] <= pivot) {
				i++;

				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}

		arr[high] = arr [i + 1];
		arr[i + 1] = pivot;

		return i + 1;
	}

	public void sort(int[] arr, int low, int high) {

		if (low < high) {
			int pi = partition(arr, low, high);

			sort(arr, low, pi - 1);
			sort(arr, pi + 1, high);
		}
	}

	public static void main(String[] args) {
		var ob = new AD2();
		
		var sc  = new Scanner(System.in);
		
		logger.log(Level.INFO, "Length of array:");
		var n = sc.nextInt();
		
		var arr = new int[n];
		
		logger.log(Level.INFO, "Array elements:");
		for (var i = 0; i < n; i++) {
			arr[i] = sc.nextInt();
		}
		
		sc.close();
		
		String str = ob.solve(arr);
		
		SwingUtilities.invokeLater(() -> ob.visualize(str));
	}
}
