package class_GUI;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Start_GUI extends JFrame {

	private JPanel contentPane;
	private static JProgressBar progressBar;
	
	private static void init() throws InterruptedException {
		progressBar.setValue(25);
		Thread.sleep(500);
		progressBar.setValue(50);
		Thread.sleep(500);
		progressBar.setValue(75);
		Thread.sleep(500);
		progressBar.setValue(100);
		DangNhap_GUI dn = new DangNhap_GUI();
		dn.setVisible(true);
	}
	
	public static void run() {
		Start_GUI start = new Start_GUI();
		start.setVisible(true);
		try {
			init();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		start.setVisible(false);
	}

	/**
	 * Create the frame.
	 */
	public Start_GUI() {
		setBounds(100, 100, 604, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setBounds(0, 364, 604, 30);
		progressBar.setForeground(Color.GREEN);
		progressBar.setValue(0);
		contentPane.add(progressBar);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("data\\icons\\logo-start.png"));
		lblLogo.setBounds(0, 0, 604, 364);
		contentPane.add(lblLogo);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}
