package class_GUI;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.InputEvent;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import java.awt.FlowLayout;
import javax.swing.border.MatteBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import class_DAO.KhachHang_DAO;
import class_Entities.NhanVien;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class ThongKeSoLuongKhachHang_GUI extends JFrame implements WindowListener {
	private NhanVien emp;
	private JComboBox<String> cbLoaiBieuDo;
	private JComboBox<String> cbNam;
	private static JPanel panel_2;

	private String[] layDanhSachCacNam() {
		KhachHang_DAO khachhang_dao = new KhachHang_DAO();
		ArrayList<String> danhSachCacNam = khachhang_dao.layCacNamDangKyCuaKhachHang();
		int soNamHienCo = danhSachCacNam.size();
		String[] option = new String[soNamHienCo];
		for (int i = 0; i < soNamHienCo; i++) {
			option[i] = danhSachCacNam.get(i);
		}
		return option;
	}

	private static JFreeChart createBarChart(CategoryDataset dataset, int nam) {
		JFreeChart barChart = ChartFactory.createBarChart3D(
				"BIỂU ĐỒ THỐNG KÊ SỐ LƯỢNG KHÁCH HÀNG QUA CÁC THÁNG TRONG NĂM " + nam, "Tháng", "Số lượng", dataset,
				PlotOrientation.VERTICAL, true, true, true);
		// Plot plot = barChart.getPlot();
		barChart.getPlot().setBackgroundPaint(Color.WHITE);

		CategoryPlot plot = barChart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		return barChart;
	}

	private static JFreeChart createLineChart(CategoryDataset dataset, int nam) {
		JFreeChart lineChart = ChartFactory.createLineChart(
				"BIỂU ĐỒ THỐNG KÊ SỐ LƯỢNG KHÁCH HÀNG QUA CÁC THÁNG TRONG NĂM " + nam, "Tháng", "Số lượng", dataset,
				PlotOrientation.VERTICAL, true, true, true);
		// Plot plot = barChart.getPlot();
		lineChart.getPlot().setBackgroundPaint(Color.WHITE);

		CategoryPlot plot = lineChart.getCategoryPlot();
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		return lineChart;
	}

	private static CategoryDataset taoDuLieuBieuDoTheoNam(int nam) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		KhachHang_DAO khachhang_dao = new KhachHang_DAO();
		for (int i = 0; i < 12; i++) {
			int thang = i+1;
			int soLuongKhachHang = khachhang_dao.laySoLuongKhachHangTheoThang(thang, nam);
			dataset.addValue(soLuongKhachHang, "Tháng", "Tháng " + thang);
		}
		return dataset;
	}

	private static void taoBieuDoThongKeDangCot(CategoryDataset duLieuChoBieuDo, int nam) {
		panel_2.removeAll();
		panel_2.validate();
		panel_2.repaint();

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("data\\icons\\logo3.png"));
		lblLogo.setBounds(21, 30, 300, 60);
		panel_2.add(lblLogo);

		JLabel lblTitle = new JLabel("");
		lblTitle.setIcon(new ImageIcon("data\\icons\\statistics-customer.png"));
		lblTitle.setBounds(393, 23, 878, 82);
		panel_2.add(lblTitle);

		JFreeChart barChart = createBarChart(duLieuChoBieuDo, nam);
		JPanel panel_chart = new JPanel();
		panel_chart.setLayout(null);
		panel_chart.setBackground(Color.WHITE);
		panel_chart.setBounds(10, 115, 1512, 645);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setBounds(133, 10, 1230, 612);
		panel_chart.add(chartPanel);

		panel_2.add(panel_chart);
		panel_2.validate();
		panel_2.repaint();
	}

	private static void taoBieuDoThongKeDangDuong(CategoryDataset duLieuChoBieuDo, int nam) {
		panel_2.removeAll();
		panel_2.validate();
		panel_2.repaint();

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("data\\icons\\logo3.png"));
		lblLogo.setBounds(21, 30, 300, 60);
		panel_2.add(lblLogo);

		JLabel lblTitle = new JLabel("");
		lblTitle.setIcon(new ImageIcon("data\\icons\\statistics-customer.png"));
		lblTitle.setBounds(393, 23, 878, 82);
		panel_2.add(lblTitle);

		JFreeChart barChart = createLineChart(duLieuChoBieuDo, nam);
		JPanel panel_chart = new JPanel();
		panel_chart.setLayout(null);
		panel_chart.setBackground(Color.WHITE);
		panel_chart.setBounds(10, 115, 1512, 645);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setBounds(133, 10, 1230, 612);
		panel_chart.add(chartPanel);

		panel_2.add(panel_chart);
		panel_2.validate();
		panel_2.repaint();
	}

	/**
	 * Create the frame.
	 */
	public ThongKeSoLuongKhachHang_GUI(NhanVien nv) {
		emp = nv;
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\statistics-chart-4.png"));
		// menuBar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setForeground(Color.RED);
		menuBar.setBackground(new Color(176, 224, 230));
		setJMenuBar(menuBar);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("data\\icons\\system.png"));
		label.setForeground(SystemColor.textHighlight);
		menuBar.add(label);

		JMenu menu = new JMenu("H\u1EC7 Th\u1ED1ng");
		menu.setForeground(SystemColor.textHighlight);
		menu.setFont(new Font("Arial", Font.PLAIN, 12));
		menu.setBackground(SystemColor.textHighlight);
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("Nh\u00E2n Vi\u00EAn");
		menuItem.setIcon(new ImageIcon("data\\icons\\employee2.png"));
		menuItem.setFont(new Font("Arial", Font.PLAIN, 12));
		menu.add(menuItem);

		JMenuItem menuItem_1 = new JMenuItem("T\u00E0i Kho\u1EA3n");
		menuItem_1.setIcon(new ImageIcon("data\\icons\\account.png"));
		menuItem_1.setFont(new Font("Arial", Font.PLAIN, 12));
		menu.add(menuItem_1);

		JMenuItem menuItem_2 = new JMenuItem("Ph\u00E2n Quy\u1EC1n");
		menuItem_2.setIcon(new ImageIcon("data\\icons\\phanquyen.png"));
		menuItem_2.setFont(new Font("Arial", Font.PLAIN, 12));
		menu.add(menuItem_2);

		JSeparator separator = new JSeparator();
		menu.add(separator);

		JMenuItem menuItem_3 = new JMenuItem("\u0110\u0103ng Nh\u1EADp");
		menuItem_3.setIcon(new ImageIcon("data\\icons\\login.png"));
		menuItem_3.setFont(new Font("Arial", Font.PLAIN, 12));
		menu.add(menuItem_3);

		JMenuItem menuItem_4 = new JMenuItem("\u0110\u1ED5i M\u1EADt Kh\u1EA9u");
		menuItem_4.setIcon(new ImageIcon("E:\\Documents\\eclipse1\\QuanLyNhaThuoc1\\data\\icons\\changekey.png"));
		menuItem_4.setFont(new Font("Arial", Font.PLAIN, 12));
		menu.add(menuItem_4);

		JMenuItem menuItem_5 = new JMenuItem("\u0110\u0103ng Xu\u1EA5t");
		menuItem_5.setIcon(new ImageIcon("E:\\Documents\\eclipse1\\QuanLyNhaThuoc1\\data\\icons\\logout.png"));
		menuItem_5.setFont(new Font("Arial", Font.PLAIN, 12));
		menu.add(menuItem_5);

		JSeparator separator_1 = new JSeparator();
		menu.add(separator_1);

		JMenuItem menuItem_6 = new JMenuItem("Giao Ca");
		menuItem_6.setIcon(new ImageIcon("E:\\Documents\\eclipse1\\QuanLyNhaThuoc1\\data\\icons\\changeca.png"));
		menuItem_6.setFont(new Font("Arial", Font.PLAIN, 12));
		menu.add(menuItem_6);

		JSeparator separator_2 = new JSeparator();
		menu.add(separator_2);

		JMenuItem menuItem_7 = new JMenuItem("C\u00E0i \u0110\u1EB7t");
		menuItem_7.setIcon(new ImageIcon("E:\\Documents\\eclipse1\\QuanLyNhaThuoc1\\data\\icons\\setting.png"));
		menuItem_7.setFont(new Font("Arial", Font.PLAIN, 12));
		menu.add(menuItem_7);

		JMenuItem menuItem_8 = new JMenuItem("Sao L\u01B0u D\u1EEF Li\u1EC7u");
		menuItem_8.setIcon(new ImageIcon("E:\\Documents\\eclipse1\\QuanLyNhaThuoc1\\data\\icons\\backup.png"));
		menuItem_8.setFont(new Font("Arial", Font.PLAIN, 12));
		menu.add(menuItem_8);

		JMenuItem menuItem_9 = new JMenuItem("Ph\u1EE5c H\u1ED3i D\u1EEF Li\u1EC7u");
		menuItem_9.setIcon(new ImageIcon("E:\\Documents\\eclipse1\\QuanLyNhaThuoc1\\data\\icons\\restore.png"));
		menuItem_9.setFont(new Font("Arial", Font.PLAIN, 12));
		menu.add(menuItem_9);

		JMenuItem menuItem_10 = new JMenuItem("Gi\u1EDBi Thi\u1EC7u");
		menuItem_10.setIcon(new ImageIcon("E:\\Documents\\eclipse1\\QuanLyNhaThuoc1\\data\\icons\\about.png"));
		menuItem_10.setFont(new Font("Arial", Font.PLAIN, 12));
		menu.add(menuItem_10);

		JMenuItem menuItem_11 = new JMenuItem("Tho\u00E1t");
		menuItem_11.setIcon(new ImageIcon("E:\\Documents\\eclipse1\\QuanLyNhaThuoc1\\data\\icons\\exit.png"));
		menuItem_11.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		menuItem_11.setFont(new Font("Arial", Font.PLAIN, 12));
		menu.add(menuItem_11);

		JMenu menu_2 = new JMenu("H\u00F3a \u0110\u01A1n Xu\u1EA5t");
		menu_2.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(menu_2);

		JMenuItem mntmNewMenuItem = new JMenuItem("B\u00E1n H\u00E0ng");
		mntmNewMenuItem.setFont(new Font("Arial", Font.PLAIN, 12));
		mntmNewMenuItem.setIcon(new ImageIcon("data\\icons\\sale1.png"));
		menu_2.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("T\u00ECm H\u00F3a \u0110\u01A1n B\u00E1n H\u00E0ng");
		mntmNewMenuItem_1.setFont(new Font("Arial", Font.PLAIN, 12));
		mntmNewMenuItem_1.setIcon(new ImageIcon("data\\icons\\searchsale1.png"));
		menu_2.add(mntmNewMenuItem_1);

		JMenu mnSnPhm = new JMenu("S\u1EA3n Ph\u1EA9m");
		mnSnPhm.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnSnPhm);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("T\u00ECm Ki\u1EBFm S\u1EA3n Ph\u1EA9m");
		mntmNewMenuItem_2.setIcon(new ImageIcon("E:\\Documents\\eclipse1\\QuanLyNhaThuoc1\\data\\icons\\search.png"));
		mntmNewMenuItem_2.setFont(new Font("Arial", Font.PLAIN, 12));
		mnSnPhm.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Th\u00EAm S\u1EA3n Ph\u1EA9m M\u1EDBi");
		mntmNewMenuItem_3.setIcon(new ImageIcon("E:\\Documents\\eclipse1\\QuanLyNhaThuoc1\\data\\icons\\add.png"));
		mntmNewMenuItem_3.setFont(new Font("Arial", Font.PLAIN, 12));
		mnSnPhm.add(mntmNewMenuItem_3);

		JMenu mnNewMenu = new JMenu("Kh\u00E1ch H\u00E0ng");
		mnNewMenu.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Qu\u1EA3n L\u00ED Kh\u00E1ch H\u00E0ng");
		mntmNewMenuItem_4.setFont(new Font("Arial", Font.PLAIN, 12));
		mnNewMenu.add(mntmNewMenuItem_4);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Th\u00E0nh Vi\u00EAn");
		mntmNewMenuItem_5.setFont(new Font("Arial", Font.PLAIN, 12));
		mnNewMenu.add(mntmNewMenuItem_5);

		JSeparator separator_4 = new JSeparator();
		mnNewMenu.add(separator_4);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Xem C\u00E1c Ch\u01B0\u01A1ng Tr\u00ECnh Khuy\u1EBFn M\u00E3i");
		mntmNewMenuItem_6.setFont(new Font("Arial", Font.PLAIN, 12));
		mnNewMenu.add(mntmNewMenuItem_6);

		JMenuItem mntmNewMenuItem_7 = new JMenuItem("T\u1EA1o Ch\u01B0\u01A1ng Tr\u00ECnh Khuy\u1EBFn M\u00E3i");
		mntmNewMenuItem_7.setFont(new Font("Arial", Font.PLAIN, 12));
		mnNewMenu.add(mntmNewMenuItem_7);

		JMenu mnNewMenu_1 = new JMenu("Danh M\u1EE5c");
		mnNewMenu_1.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Nh\u00E0 S\u1EA3n Xu\u1EA5t");
		mntmNewMenuItem_8.setFont(new Font("Arial", Font.PLAIN, 12));
		mnNewMenu_1.add(mntmNewMenuItem_8);

		JSeparator separator_5 = new JSeparator();
		mnNewMenu_1.add(separator_5);

		JMenuItem mntmNewMenuItem_9 = new JMenuItem("\u0110\u01A1n V\u1ECB T\u00EDnh");
		mntmNewMenuItem_9.setFont(new Font("Arial", Font.PLAIN, 12));
		mnNewMenu_1.add(mntmNewMenuItem_9);

		JMenuItem mntmNewMenuItem_10 = new JMenuItem("\u0110\u01A1n V\u1ECB Quy \u0110\u1ED5i");
		mntmNewMenuItem_10.setFont(new Font("Arial", Font.PLAIN, 12));
		mnNewMenu_1.add(mntmNewMenuItem_10);

		JMenu mnNewMenu_2 = new JMenu("B\u00E1o C\u00E1o");
		mnNewMenu_2.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmToBoCo = new JMenuItem("Thống kê doanh thu");
		mntmToBoCo.setFont(new Font("Arial", Font.PLAIN, 12));
		mnNewMenu_2.add(mntmToBoCo);

		JMenuItem mntmXemBoCo = new JMenuItem("Thống kê thuốc");
		mntmXemBoCo.setFont(new Font("Arial", Font.PLAIN, 12));
		mnNewMenu_2.add(mntmXemBoCo);

		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBorder(null);
		horizontalBox.setBackground(Color.WHITE);
		getContentPane().add(horizontalBox, BorderLayout.SOUTH);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(12);
		flowLayout.setHgap(10);
		horizontalBox.add(panel);

		JLabel lblNam = new JLabel("Chọn năm muốn xem số lượng khách hàng:");
		lblNam.setFont(new Font("Arial", Font.BOLD, 16));
		panel.add(lblNam);
		cbNam = new JComboBox<String>();
		cbNam.setFont(new Font("Arial", Font.PLAIN, 16));
		cbNam.setModel(new DefaultComboBoxModel<>(layDanhSachCacNam()));
		panel.add(cbNam);
		cbNam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string_Nam = cbNam.getSelectedItem().toString().trim();
				int nam = Integer.parseInt(string_Nam);
				String loaiBieuDo = cbLoaiBieuDo.getSelectedItem().toString().trim();
				if (loaiBieuDo.equalsIgnoreCase("Biểu đồ cột")) {
					taoBieuDoThongKeDangCot(taoDuLieuBieuDoTheoNam(nam), nam);
				} else if (loaiBieuDo.equalsIgnoreCase("Biểu đồ đường")) {
					taoBieuDoThongKeDangDuong(taoDuLieuBieuDoTheoNam(nam), nam);
				}
			}
		});

		JLabel lblLoaiBieuDo = new JLabel("Chọn loại biểu đồ:");
		lblLoaiBieuDo.setFont(new Font("Arial", Font.BOLD, 16));
		panel.add(lblLoaiBieuDo);

		String[] option = "Biểu đồ cột;Biểu đồ đường".split(";");
		cbLoaiBieuDo = new JComboBox<String>();
		cbLoaiBieuDo.setFont(new Font("Arial", Font.PLAIN, 16));
		cbLoaiBieuDo.setModel(new DefaultComboBoxModel<>(option));
		panel.add(cbLoaiBieuDo);
		cbLoaiBieuDo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string_Nam = cbNam.getSelectedItem().toString().trim();
				int nam = Integer.parseInt(string_Nam);
				String loaiBieuDo = cbLoaiBieuDo.getSelectedItem().toString().trim();
				if (loaiBieuDo.equalsIgnoreCase("Biểu đồ cột")) {
					taoBieuDoThongKeDangCot(taoDuLieuBieuDoTheoNam(nam), nam);
				} else if (loaiBieuDo.equalsIgnoreCase("Biểu đồ đường")) {
					taoBieuDoThongKeDangDuong(taoDuLieuBieuDoTheoNam(nam), nam);
				}
			}
		});

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setVgap(12);
		flowLayout_1.setHgap(10);
		horizontalBox.add(panel_1);

		JButton btnHoTro = new JButton("Hỗ trợ");
        btnHoTro.setIcon(new ImageIcon("data\\icons\\setting.png"));
        btnHoTro.setToolTipText("");
        btnHoTro.setFocusable(false);
        panel_1.add(btnHoTro);
        btnHoTro.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					String path = new File("").getAbsolutePath() + "\\data\\Help\\Help.chm";
					File file = new File(path);

					if (file.exists()) {
						Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + path);
					} else {
						throw new Exception("File \"Help.chm\" not found!");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Notice", JOptionPane.OK_OPTION);
				}
        	}
        });
        
        JButton btnDangXuat = new JButton("\u0110\u0103ng Xu\u1EA5t");
        btnDangXuat.setIcon(new ImageIcon("data\\icons\\logout.png"));
        btnDangXuat.setToolTipText("\u0110\u0103ng Xu\u1EA5t");
        btnDangXuat.setFocusable(false);
        btnHoTro.setMnemonic(KeyEvent.VK_D);
        panel_1.add(btnDangXuat);
        btnDangXuat.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		DangNhap_GUI dangNhap = new DangNhap_GUI();
        		dangNhap.setVisible(true);
        		setVisible(false);
        	}
        });

		JButton btnHome = new JButton("Trở về trang chủ");
		btnHome.setIcon(new ImageIcon("data\\icons\\home.png"));
		btnHome.setToolTipText("Tho\u00E1t");
		btnHome.setMnemonic(KeyEvent.VK_T);
		btnHome.setFocusable(false);
		btnHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (emp.getChucVu().equalsIgnoreCase("Quản lý nhà thuốc")) {
					QuanLy_GUI quanLy = new QuanLy_GUI(emp);
					quanLy.setVisible(true);
					setVisible(false);
				} else if (emp.getChucVu().equalsIgnoreCase("Nhân viên bán thuốc")) {
					NhanVien_GUI nhanVien = new NhanVien_GUI(emp);
					nhanVien.setVisible(true);
					setVisible(false);
				}
			}
		});
		panel_1.add(btnHome);

		panel_2 = new JPanel();
		panel_2.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlShadow));
		panel_2.setBackground(Color.WHITE);
		getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);

		String string_Nam = cbNam.getSelectedItem().toString().trim();
		int nam = Integer.parseInt(string_Nam);
		taoBieuDoThongKeDangCot(taoDuLieuBieuDoTheoNam(nam), nam);

		setTitle("Thống kê số lượng khách hàng");
		Toolkit tk = Toolkit.getDefaultToolkit();
		setSize(tk.getScreenSize().width, tk.getScreenSize().height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát ứng dụng hay không?", "Xác nhận",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
