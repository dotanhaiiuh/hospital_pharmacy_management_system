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
import java.awt.event.InputEvent;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.border.MatteBorder;

import class_Entities.NhanVien;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;

public class NhanVienKho_GUI extends JFrame implements WindowListener {
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private NhanVien emp;
	
	/**
	 * Create the frame.
	 */
	public NhanVienKho_GUI(NhanVien nv) {
		emp = nv;
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\iconimange.png"));
		//menuBar
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
        
        JMenuItem menuItem_1 = new JMenuItem("T\u00E0i Kho\u1EA3n");
        menuItem_1.setIcon(new ImageIcon("data\\icons\\account.png"));
        menuItem_1.setFont(new Font("Arial", Font.PLAIN, 12));
        menu.add(menuItem_1);
        
        JMenuItem menuItem_3 = new JMenuItem("\u0110\u0103ng Nh\u1EADp");
        menuItem_3.setIcon(new ImageIcon("data\\icons\\login.png"));
        menuItem_3.setFont(new Font("Arial", Font.PLAIN, 12));
        menu.add(menuItem_3);
        
        JMenuItem menuItem_4 = new JMenuItem("\u0110\u1ED5i M\u1EADt Kh\u1EA9u");
        menuItem_4.setIcon(new ImageIcon("data\\icons\\changekey.png"));
        menuItem_4.setFont(new Font("Arial", Font.PLAIN, 12));
        menu.add(menuItem_4);
        
        JSeparator separator = new JSeparator();
        menu.add(separator);
        
        JMenuItem menuItem_5 = new JMenuItem("\u0110\u0103ng Xu\u1EA5t");
        menuItem_5.setIcon(new ImageIcon("data\\icons\\logout.png"));
        menuItem_5.setFont(new Font("Arial", Font.PLAIN, 12));
        menu.add(menuItem_5);
        
        JSeparator separator_1 = new JSeparator();
        menu.add(separator_1);
        
        JMenuItem menuItem_7 = new JMenuItem("C\u00E0i \u0110\u1EB7t");
        menuItem_7.setIcon(new ImageIcon("data\\icons\\setting.png"));
        menuItem_7.setFont(new Font("Arial", Font.PLAIN, 12));
        menu.add(menuItem_7);
        
        JMenuItem menuItem_8 = new JMenuItem("Sao L\u01B0u D\u1EEF Li\u1EC7u");
        menuItem_8.setIcon(new ImageIcon("data\\icons\\backup.png"));
        menuItem_8.setFont(new Font("Arial", Font.PLAIN, 12));
        menu.add(menuItem_8);
        
        JMenuItem menuItem_9 = new JMenuItem("Ph\u1EE5c H\u1ED3i D\u1EEF Li\u1EC7u");
        menuItem_9.setIcon(new ImageIcon("data\\icons\\restore.png"));
        menuItem_9.setFont(new Font("Arial", Font.PLAIN, 12));
        menu.add(menuItem_9);
        
        JMenuItem menuItem_10 = new JMenuItem("Gi\u1EDBi Thi\u1EC7u");
        menuItem_10.setIcon(new ImageIcon("data\\icons\\about.png"));
        menuItem_10.setFont(new Font("Arial", Font.PLAIN, 12));
        menu.add(menuItem_10);
        
        JSeparator separator_2 = new JSeparator();
        menu.add(separator_2);
        
        JMenuItem menuItem_11 = new JMenuItem("Tho\u00E1t");
        menuItem_11.setIcon(new ImageIcon("data\\icons\\exit.png"));
        menuItem_11.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
        menuItem_11.setFont(new Font("Arial", Font.PLAIN, 12));
        menu.add(menuItem_11);
        menuItem_11.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Toolkit.getDefaultToolkit().beep();
				if (JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát ứng dụng hay không?", "Xác nhận",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
        	}
        });
        
        JMenu mnSnPhm = new JMenu("S\u1EA3n Ph\u1EA9m");
        mnSnPhm.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mnSnPhm);
        
        JMenuItem mnitemTimKiemThuoc = new JMenuItem("Tìm Kiếm Thuốc");
        mnitemTimKiemThuoc.setIcon(new ImageIcon("data\\icons\\search.png"));
        mnitemTimKiemThuoc.setFont(new Font("Arial", Font.PLAIN, 12));
        mnSnPhm.add(mnitemTimKiemThuoc);
        mnitemTimKiemThuoc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimKiemThuoc_GUI timKiemThuoc = new TimKiemThuoc_GUI(emp);
        		timKiemThuoc.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JMenuItem mnitemNhapThuoc = new JMenuItem("Thêm Phiếu Nhập Thuốc Mới");
        mnitemNhapThuoc.setIcon(new ImageIcon("data\\icons\\add.png"));
        mnitemNhapThuoc.setFont(new Font("Arial", Font.PLAIN, 12));
        mnSnPhm.add(mnitemNhapThuoc);
        mnitemNhapThuoc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		NhapThuoc_GUI nhapThuoc = new NhapThuoc_GUI(emp);
        		nhapThuoc.setVisible(true);
        		setVisible(false);
        	}
        });
        
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
        
        JMenuItem mnitemThongKeThuocHetHan = new JMenuItem("Thống kê thuốc hết hạn");
        mnitemThongKeThuocHetHan.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu_2.add(mnitemThongKeThuocHetHan);
        mnitemThongKeThuocHetHan.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThongKeThuocHetHan_GUI thongKeThuocHetHan = new ThongKeThuocHetHan_GUI(emp);
        		thongKeThuocHetHan.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JMenuItem mnitemThongKeThuocTonKho = new JMenuItem("Thống kê thuốc tồn kho");
        mnitemThongKeThuocTonKho.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu_2.add(mnitemThongKeThuocTonKho);
        mnitemThongKeThuocTonKho.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThongKeThuocTonKho_GUI thongKeThuocTonKho = new ThongKeThuocTonKho_GUI(emp);
        		thongKeThuocTonKho.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JMenuItem mnitemDoanhSoBan = new JMenuItem("Thống kê doanh số bán");
        mnitemDoanhSoBan.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu_2.add(mnitemDoanhSoBan);
        mnitemDoanhSoBan.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThongKeDoanhSoBan_GUI thongKeDoanhSoBan = new ThongKeDoanhSoBan_GUI(emp);
        		thongKeDoanhSoBan.setVisible(true);
        		setVisible(false);
        	}
        });
        
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
        
        JButton btnGhiChu = new JButton("Ghi chú");
        btnGhiChu.setToolTipText("");
        btnGhiChu.setIcon(new ImageIcon("data\\icons\\note.png"));
        btnGhiChu.setFocusable(false);
        panel.add(btnGhiChu);
        
        JButton btnTK = new JButton("T\u00E0i Kho\u1EA3n");
        btnTK.setToolTipText("T\u00E0i Kho\u1EA3n");
        btnTK.setIcon(new ImageIcon("data\\icons\\account.png"));
        btnTK.setFocusable(false);
        panel.add(btnTK);
        
        Component horizontalStrut_1 = Box.createHorizontalStrut(20);
        panel.add(horizontalStrut_1);
        
        Component horizontalStrut_2 = Box.createHorizontalStrut(20);
        panel.add(horizontalStrut_2);
        
        Component horizontalGlue_1 = Box.createHorizontalGlue();
        horizontalBox.add(horizontalGlue_1);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
        flowLayout_1.setVgap(12);
        flowLayout_1.setHgap(10);
        horizontalBox.add(panel_1);
        
        Component horizontalStrut_3 = Box.createHorizontalStrut(20);
        panel_1.add(horizontalStrut_3);
        
        Component horizontalStrut = Box.createHorizontalStrut(20);
        panel_1.add(horizontalStrut);
        
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
        
        JButton btnExit = new JButton("Tho\u00E1t");
        btnExit.setIcon(new ImageIcon("data\\icons\\exit.png"));
        btnExit.setToolTipText("Tho\u00E1t");
        btnExit.setMnemonic(KeyEvent.VK_T);
        btnExit.setFocusable(false);
        panel_1.add(btnExit);
        btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Toolkit.getDefaultToolkit().beep();
				if (JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát ứng dụng hay không?", "Xác nhận",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
        
        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlShadow));
        panel_2.setBackground(Color.WHITE);
        getContentPane().add(panel_2, BorderLayout.CENTER);
        panel_2.setLayout(null);
        
        JLabel lblTenNhanVien = new JLabel(emp.getHoTenNhanVien());
        lblTenNhanVien.setFont(new Font("Arial", Font.BOLD, 14));
        lblTenNhanVien.setBounds(1309, 20, 223, 27);
        panel_2.add(lblTenNhanVien);
        
        JLabel lblImage = new JLabel("");
        lblImage.setIcon(new ImageIcon(emp.getTaiKhoan().getHinhAnh()));
        lblImage.setBounds(1165, 19, 134, 134);
        panel_2.add(lblImage);
        
        JLabel lblLogo = new JLabel("");
        lblLogo.setIcon(new ImageIcon("data\\icons\\logo3.png"));
        lblLogo.setBounds(21, 30, 300, 60);
        panel_2.add(lblLogo);
        
        JLabel lblTitle = new JLabel("");
        lblTitle.setIcon(new ImageIcon("data\\icons\\list-function.png"));
        lblTitle.setBounds(345, 71, 792, 82);
        panel_2.add(lblTitle);
        
        JLabel lblNgaySinh = new JLabel(sdf.format(emp.getNgaySinh()));
        lblNgaySinh.setForeground(Color.RED);
        lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 14));
        lblNgaySinh.setBounds(1309, 51, 223, 27);
        panel_2.add(lblNgaySinh);
        
        JLabel lblChucVu = new JLabel(emp.getChucVu());
        lblChucVu.setForeground(Color.BLUE);
        lblChucVu.setFont(new Font("Arial", Font.BOLD, 14));
        lblChucVu.setBounds(1309, 82, 223, 27);
        panel_2.add(lblChucVu);
        
        JLabel lblTrangThai = new JLabel(emp.getTinhTrang());
        lblTrangThai.setForeground(Color.MAGENTA);
        lblTrangThai.setFont(new Font("Arial", Font.BOLD, 14));
        lblTrangThai.setBounds(1309, 114, 223, 27);
        panel_2.add(lblTrangThai);
        
        JButton btnNhapThuoc = new JButton("Nhập thuốc");
        btnNhapThuoc.setIcon(new ImageIcon("data\\icons\\import-medicine-48.png"));
        btnNhapThuoc.setFont(new Font("Arial", Font.BOLD, 20));
        btnNhapThuoc.setFocusable(false);
        btnNhapThuoc.setBackground(new Color(0, 255, 255));
        btnNhapThuoc.setBounds(10, 256, 366, 82);
        panel_2.add(btnNhapThuoc);
        btnNhapThuoc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		NhapThuoc_GUI nhapThuoc = new NhapThuoc_GUI(emp);
        		nhapThuoc.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXemDanhSachNhapThuoc = new JButton("Xem danh sách nhập thuốc");
        btnXemDanhSachNhapThuoc.setForeground(Color.WHITE);
        btnXemDanhSachNhapThuoc.setIcon(new ImageIcon("data\\icons\\list-import-medicine-48.png"));
        btnXemDanhSachNhapThuoc.setFont(new Font("Arial", Font.BOLD, 20));
        btnXemDanhSachNhapThuoc.setFocusable(false);
        btnXemDanhSachNhapThuoc.setBackground(new Color(0, 128, 128));
        btnXemDanhSachNhapThuoc.setBounds(762, 256, 366, 82);
        panel_2.add(btnXemDanhSachNhapThuoc);
        btnXemDanhSachNhapThuoc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		XemDanhSachPhieuNhapThuoc_GUI danhSachPhieuNhapThuoc = new XemDanhSachPhieuNhapThuoc_GUI(emp);
        		danhSachPhieuNhapThuoc.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnTimKiemThuoc = new JButton("Tìm kiếm thuốc");
        btnTimKiemThuoc.setIcon(new ImageIcon("data\\icons\\search-medicine-48.png"));
        btnTimKiemThuoc.setFont(new Font("Arial", Font.BOLD, 20));
        btnTimKiemThuoc.setFocusable(false);
        btnTimKiemThuoc.setBackground(new Color(255, 165, 0));
        btnTimKiemThuoc.setBounds(386, 256, 366, 82);
        panel_2.add(btnTimKiemThuoc);
        btnTimKiemThuoc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimKiemThuoc_GUI timKiemThuoc = new TimKiemThuoc_GUI(emp);
        		timKiemThuoc.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXoaThongTinNhapThuoc = new JButton("Xóa thông tin nhập thuốc");
        btnXoaThongTinNhapThuoc.setIcon(new ImageIcon("data\\icons\\icons8-delete-bin-48.png"));
        btnXoaThongTinNhapThuoc.setFont(new Font("Arial", Font.BOLD, 20));
        btnXoaThongTinNhapThuoc.setFocusable(false);
        btnXoaThongTinNhapThuoc.setBackground(new Color(255, 255, 0));
        btnXoaThongTinNhapThuoc.setBounds(1138, 256, 366, 82);
        panel_2.add(btnXoaThongTinNhapThuoc);
        btnXoaThongTinNhapThuoc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimPhieuNhapThuoc_GUI timKiemPhieuNhapThuoc = new TimPhieuNhapThuoc_GUI(emp);
        		timKiemPhieuNhapThuoc.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JLabel lblQuanLyThuoc = new JLabel("Quản lý thuốc");
        lblQuanLyThuoc.setForeground(Color.RED);
        lblQuanLyThuoc.setFont(new Font("Arial", Font.BOLD, 40));
        lblQuanLyThuoc.setHorizontalAlignment(SwingConstants.CENTER);
        lblQuanLyThuoc.setBounds(632, 174, 266, 60);
        panel_2.add(lblQuanLyThuoc);
        
        JSeparator separator_3 = new JSeparator();
        separator_3.setForeground(Color.BLACK);
        separator_3.setBounds(10, 206, 589, 2);
        panel_2.add(separator_3);
        
        JSeparator separator_3_1 = new JSeparator();
        separator_3_1.setForeground(Color.BLACK);
        separator_3_1.setBounds(908, 206, 596, 2);
        panel_2.add(separator_3_1);
        
        JButton btnThongKeThuocTonKho = new JButton("Thống kê thuốc tồn kho");
        btnThongKeThuocTonKho.setIcon(new ImageIcon("data\\icons\\statistics-chart-2-48.png"));
        btnThongKeThuocTonKho.setFont(new Font("Arial", Font.BOLD, 20));
        btnThongKeThuocTonKho.setFocusable(false);
        btnThongKeThuocTonKho.setBackground(new Color(255, 255, 0));
        btnThongKeThuocTonKho.setBounds(584, 432, 366, 82);
        panel_2.add(btnThongKeThuocTonKho);
        btnThongKeThuocTonKho.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThongKeThuocTonKho_GUI thongKeThuocTonKho = new ThongKeThuocTonKho_GUI(emp);
        		thongKeThuocTonKho.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JLabel lblThongKeThuoc = new JLabel("Thống kê thuốc");
        lblThongKeThuoc.setHorizontalAlignment(SwingConstants.CENTER);
        lblThongKeThuoc.setForeground(Color.MAGENTA);
        lblThongKeThuoc.setFont(new Font("Arial", Font.BOLD, 40));
        lblThongKeThuoc.setBounds(609, 362, 299, 60);
        panel_2.add(lblThongKeThuoc);
        
        JSeparator separator_3_2 = new JSeparator();
        separator_3_2.setForeground(Color.BLACK);
        separator_3_2.setBounds(10, 396, 575, 2);
        panel_2.add(separator_3_2);
        
        JSeparator separator_3_1_1 = new JSeparator();
        separator_3_1_1.setForeground(Color.BLACK);
        separator_3_1_1.setBounds(918, 396, 586, 2);
        panel_2.add(separator_3_1_1);
        
        JLabel lblQuanLyNhaCungCap = new JLabel("Quản lý nhà cung cấp");
        lblQuanLyNhaCungCap.setHorizontalAlignment(SwingConstants.CENTER);
        lblQuanLyNhaCungCap.setForeground(Color.BLUE);
        lblQuanLyNhaCungCap.setFont(new Font("Arial", Font.BOLD, 40));
        lblQuanLyNhaCungCap.setBounds(566, 545, 414, 60);
        panel_2.add(lblQuanLyNhaCungCap);
        
        JSeparator separator_3_3 = new JSeparator();
        separator_3_3.setForeground(Color.BLACK);
        separator_3_3.setBounds(10, 576, 540, 2);
        panel_2.add(separator_3_3);
        
        JSeparator separator_3_1_2 = new JSeparator();
        separator_3_1_2.setForeground(Color.BLACK);
        separator_3_1_2.setBounds(990, 576, 514, 2);
        panel_2.add(separator_3_1_2);
        
        JButton btnThemNhaCungCap = new JButton("Thêm nhà cung cấp");
        btnThemNhaCungCap.setIcon(new ImageIcon("data\\icons\\icons8-add-48.png"));
        btnThemNhaCungCap.setFont(new Font("Arial", Font.BOLD, 20));
        btnThemNhaCungCap.setFocusable(false);
        btnThemNhaCungCap.setBackground(new Color(255, 255, 0));
        btnThemNhaCungCap.setBounds(10, 626, 366, 82);
        panel_2.add(btnThemNhaCungCap);
        btnThemNhaCungCap.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThemNhaCungCap_GUI themNhaCungCap = new ThemNhaCungCap_GUI(emp);
        		themNhaCungCap.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXemDanhSachNhaCungCap = new JButton("Xem danh sách nhà cung cấp");
        btnXemDanhSachNhaCungCap.setForeground(Color.WHITE);
        btnXemDanhSachNhaCungCap.setIcon(new ImageIcon("data\\icons\\list-supplier-48.png"));
        btnXemDanhSachNhaCungCap.setFont(new Font("Arial", Font.BOLD, 20));
        btnXemDanhSachNhaCungCap.setFocusable(false);
        btnXemDanhSachNhaCungCap.setBackground(new Color(30, 144, 255));
        btnXemDanhSachNhaCungCap.setBounds(386, 626, 366, 82);
        panel_2.add(btnXemDanhSachNhaCungCap);
        btnXemDanhSachNhaCungCap.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		XemDanhSachNhaCungCap_GUI xemDanhSachNhaCungCap = new XemDanhSachNhaCungCap_GUI(emp);
        		xemDanhSachNhaCungCap.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnSuaThongTinNhaCungCap = new JButton("Sửa thông tin nhà cung cấp");
        btnSuaThongTinNhaCungCap.setIcon(new ImageIcon("data\\icons\\icons8-edit-supplier-48.png"));
        btnSuaThongTinNhaCungCap.setFont(new Font("Arial", Font.BOLD, 20));
        btnSuaThongTinNhaCungCap.setFocusable(false);
        btnSuaThongTinNhaCungCap.setBackground(new Color(204, 255, 255));
        btnSuaThongTinNhaCungCap.setBounds(762, 626, 366, 82);
        panel_2.add(btnSuaThongTinNhaCungCap);
        btnSuaThongTinNhaCungCap.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimNhaCungCap_GUI timNhaCungCap = new TimNhaCungCap_GUI(emp, btnSuaThongTinNhaCungCap.getText());
        		timNhaCungCap.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXoaThongTinNhaCungCap = new JButton("Xóa thông tin nhà cung cấp");
        btnXoaThongTinNhaCungCap.setForeground(Color.WHITE);
        btnXoaThongTinNhaCungCap.setIcon(new ImageIcon("data\\icons\\icons8-delete-supplier-48.png"));
        btnXoaThongTinNhaCungCap.setFont(new Font("Arial", Font.BOLD, 20));
        btnXoaThongTinNhaCungCap.setFocusable(false);
        btnXoaThongTinNhaCungCap.setBackground(new Color(0, 128, 128));
        btnXoaThongTinNhaCungCap.setBounds(1138, 626, 366, 82);
        panel_2.add(btnXoaThongTinNhaCungCap);
        btnXoaThongTinNhaCungCap.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimNhaCungCap_GUI timNhaCungCap = new TimNhaCungCap_GUI(emp, btnXoaThongTinNhaCungCap.getText());
        		timNhaCungCap.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnThongKeThuocHetHan = new JButton("Thống kê thuốc hết hạn");
        btnThongKeThuocHetHan.setIcon(new ImageIcon("data\\icons\\statistics-chart-1-48.png"));
        btnThongKeThuocHetHan.setFont(new Font("Arial", Font.BOLD, 20));
        btnThongKeThuocHetHan.setFocusable(false);
        btnThongKeThuocHetHan.setBackground(new Color(0, 255, 51));
        btnThongKeThuocHetHan.setBounds(208, 432, 366, 82);
        panel_2.add(btnThongKeThuocHetHan);
        btnThongKeThuocHetHan.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThongKeThuocHetHan_GUI thongKeThuocHetHan = new ThongKeThuocHetHan_GUI(emp);
        		thongKeThuocHetHan.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnThongKeDoanhSoBan = new JButton("Thống kê doanh số bán");
        btnThongKeDoanhSoBan.setIcon(new ImageIcon("data\\icons\\statistics-chart-3-48.png"));
        btnThongKeDoanhSoBan.setFont(new Font("Arial", Font.BOLD, 20));
        btnThongKeDoanhSoBan.setFocusable(false);
        btnThongKeDoanhSoBan.setBackground(new Color(0, 191, 255));
        btnThongKeDoanhSoBan.setBounds(960, 432, 366, 82);
        panel_2.add(btnThongKeDoanhSoBan);
        btnThongKeDoanhSoBan.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThongKeDoanhSoBan_GUI thongKeDoanhSoBan = new ThongKeDoanhSoBan_GUI(emp);
        		thongKeDoanhSoBan.setVisible(true);
        		setVisible(false);
        	}
        });
        
		setTitle("Quản lý kho");
		Toolkit tk = Toolkit.getDefaultToolkit();
		setSize(tk.getScreenSize().width, tk.getScreenSize().height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		Toolkit.getDefaultToolkit().beep();
		if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát ứng dụng hay không?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
		{
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
