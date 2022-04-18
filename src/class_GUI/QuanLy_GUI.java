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

import class_DAO.TaiKhoan_DAO;
import class_Entities.NhanVien;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;

public class QuanLy_GUI extends JFrame implements WindowListener {
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private NhanVien emp;
	private int soLanNhapSai = 0;
	
	/**
	 * Create the frame.
	 */
	public QuanLy_GUI(NhanVien nv) {
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
        menuItem_4.setIcon(new ImageIcon("data\\icons\\changekey.png"));
        menuItem_4.setFont(new Font("Arial", Font.PLAIN, 12));
        menu.add(menuItem_4);
        
        JMenuItem menuItem_5 = new JMenuItem("\u0110\u0103ng Xu\u1EA5t");
        menuItem_5.setIcon(new ImageIcon("data\\icons\\logout.png"));
        menuItem_5.setFont(new Font("Arial", Font.PLAIN, 12));
        menu.add(menuItem_5);
        
        JSeparator separator_1 = new JSeparator();
        menu.add(separator_1);
        
        JMenuItem menuItem_6 = new JMenuItem("Giao Ca");
        menuItem_6.setIcon(new ImageIcon("data\\icons\\changeca.png"));
        menuItem_6.setFont(new Font("Arial", Font.PLAIN, 12));
        menu.add(menuItem_6);
        
        JSeparator separator_2 = new JSeparator();
        menu.add(separator_2);
        
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
        
        JMenu mnHan = new JMenu("Hóa Đơn");
        mnHan.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mnHan);
        
        JMenuItem mnitemBanThuoc = new JMenuItem("Bán thuốc");
        mnitemBanThuoc.setFont(new Font("Arial", Font.PLAIN, 12));
        mnitemBanThuoc.setIcon(new ImageIcon("data\\icons\\sale1.png"));
        mnHan.add(mnitemBanThuoc);
        mnitemBanThuoc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TaoHoaDon_GUI taoHoaDon = new TaoHoaDon_GUI(emp);
        		taoHoaDon.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JMenuItem mnitemTimHoaDon = new JMenuItem("Tìm Hóa Đơn Bán Thuốc");
        mnitemTimHoaDon.setFont(new Font("Arial", Font.PLAIN, 12));
        mnitemTimHoaDon.setIcon(new ImageIcon("data\\icons\\searchsale1.png"));
        mnHan.add(mnitemTimHoaDon);
        mnitemTimHoaDon.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		XemDanhSachHoaDon_GUI xemDanhSachHoaDon = new XemDanhSachHoaDon_GUI(emp);
        		xemDanhSachHoaDon.setVisible(true);
        		setVisible(false);
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
        
        JMenuItem mnitemNhaSanXuat = new JMenuItem("Nh\u00E0 S\u1EA3n Xu\u1EA5t");
        mnitemNhaSanXuat.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu_1.add(mnitemNhaSanXuat);
        
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
        
        JMenuItem mnitemThongKeSoLuongKhachHang = new JMenuItem("Thống kê số lượng khách hàng");
        mnitemThongKeSoLuongKhachHang.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu_2.add(mnitemThongKeSoLuongKhachHang);
        mnitemThongKeSoLuongKhachHang.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThongKeSoLuongKhachHang_GUI thongKeSoLuongKhachHang = new ThongKeSoLuongKhachHang_GUI(emp);
        		thongKeSoLuongKhachHang.setVisible(true);
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
        btnTK.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TaiKhoan_GUI taiKhoan = new TaiKhoan_GUI(emp);
        		taiKhoan.setVisible(true);
        	}
        });
        
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
        btnExit.setFocusable(false);
        btnExit.setMnemonic(KeyEvent.VK_T);
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
        
        JLabel lblQuanLyThuoc = new JLabel("Quản lý thuốc:");
        lblQuanLyThuoc.setFont(new Font("Arial", Font.BOLD, 13));
        lblQuanLyThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
        lblQuanLyThuoc.setBounds(90, 196, 123, 27);
        panel_2.add(lblQuanLyThuoc);
        
        JButton btnNhapThuoc = new JButton("Nhập thuốc");
        btnNhapThuoc.setIcon(new ImageIcon("data\\icons\\import-medicine.png"));
        btnNhapThuoc.setHorizontalAlignment(SwingConstants.LEFT);
        btnNhapThuoc.setFont(new Font("Arial", Font.PLAIN, 12));
        btnNhapThuoc.setBackground(Color.WHITE);
        btnNhapThuoc.setBounds(223, 192, 192, 34);
        btnNhapThuoc.setFocusable(false);
        panel_2.add(btnNhapThuoc);
        btnNhapThuoc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		NhapThuoc_GUI nhapThuoc = new NhapThuoc_GUI(emp);
        		nhapThuoc.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnTinKiemThuoc = new JButton("Tìm kiếm thuốc");
        btnTinKiemThuoc.setBackground(Color.WHITE);
        btnTinKiemThuoc.setIcon(new ImageIcon("data\\icons\\search-medicine-24.png"));
        btnTinKiemThuoc.setHorizontalAlignment(SwingConstants.LEFT);
        btnTinKiemThuoc.setFont(new Font("Arial", Font.PLAIN, 12));
        btnTinKiemThuoc.setBounds(436, 192, 223, 34);
        btnTinKiemThuoc.setFocusable(false);
        panel_2.add(btnTinKiemThuoc);
        btnTinKiemThuoc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimKiemThuoc_GUI timKiemThuoc = new TimKiemThuoc_GUI(emp);
        		timKiemThuoc.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXemDanhSachPhieuNhapThuoc = new JButton("Xem danh sách nhập thuốc");
        btnXemDanhSachPhieuNhapThuoc.setBackground(Color.WHITE);
        btnXemDanhSachPhieuNhapThuoc.setIcon(new ImageIcon("data\\icons\\list-import-medicine.png"));
        btnXemDanhSachPhieuNhapThuoc.setHorizontalAlignment(SwingConstants.LEFT);
        btnXemDanhSachPhieuNhapThuoc.setFont(new Font("Arial", Font.PLAIN, 12));
        btnXemDanhSachPhieuNhapThuoc.setBounds(681, 192, 216, 34);
        btnXemDanhSachPhieuNhapThuoc.setFocusable(false);
        panel_2.add(btnXemDanhSachPhieuNhapThuoc);
        btnXemDanhSachPhieuNhapThuoc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		XemDanhSachPhieuNhapThuoc_GUI danhSachPhieuNhapThuoc = new XemDanhSachPhieuNhapThuoc_GUI(emp);
        		danhSachPhieuNhapThuoc.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXoaThongTinNhapThuoc = new JButton("Xóa thông tin nhập thuốc");
        btnXoaThongTinNhapThuoc.setIcon(new ImageIcon("data\\icons\\icons8-delete-bin-24.png"));
        btnXoaThongTinNhapThuoc.setHorizontalAlignment(SwingConstants.LEFT);
        btnXoaThongTinNhapThuoc.setFont(new Font("Arial", Font.PLAIN, 12));
        btnXoaThongTinNhapThuoc.setFocusable(false);
        btnXoaThongTinNhapThuoc.setBackground(Color.WHITE);
        btnXoaThongTinNhapThuoc.setBounds(921, 192, 228, 34);
        panel_2.add(btnXoaThongTinNhapThuoc);
        btnXoaThongTinNhapThuoc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimPhieuNhapThuoc_GUI timKiemPhieuNhapThuoc = new TimPhieuNhapThuoc_GUI(emp);
        		timKiemPhieuNhapThuoc.setVisible(true);
        		setVisible(false);
        	}
        });
        
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
        
        JLabel lblQuanLyHoaDon = new JLabel("Quản lý hóa đơn:");
        lblQuanLyHoaDon.setHorizontalAlignment(SwingConstants.RIGHT);
        lblQuanLyHoaDon.setFont(new Font("Arial", Font.BOLD, 13));
        lblQuanLyHoaDon.setBounds(90, 250, 123, 27);
        panel_2.add(lblQuanLyHoaDon);
        
        JButton btnTaoHoaDon = new JButton("Tạo hóa đơn");
        btnTaoHoaDon.setIcon(new ImageIcon("data\\icons\\icons8-invoice-24.png"));
        btnTaoHoaDon.setHorizontalAlignment(SwingConstants.LEFT);
        btnTaoHoaDon.setFont(new Font("Arial", Font.PLAIN, 12));
        btnTaoHoaDon.setFocusable(false);
        btnTaoHoaDon.setBackground(Color.WHITE);
        btnTaoHoaDon.setBounds(223, 246, 192, 34);
        panel_2.add(btnTaoHoaDon);
        btnTaoHoaDon.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TaoHoaDon_GUI taoHoaDon = new TaoHoaDon_GUI(emp);
        		taoHoaDon.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXemDanhSachHoaDon = new JButton("Xem danh sách hóa đơn");
        btnXemDanhSachHoaDon.setIcon(new ImageIcon("data\\icons\\icons8-summary-list-24.png"));
        btnXemDanhSachHoaDon.setHorizontalAlignment(SwingConstants.LEFT);
        btnXemDanhSachHoaDon.setFont(new Font("Arial", Font.PLAIN, 12));
        btnXemDanhSachHoaDon.setFocusable(false);
        btnXemDanhSachHoaDon.setBackground(Color.WHITE);
        btnXemDanhSachHoaDon.setBounds(436, 246, 223, 34);
        panel_2.add(btnXemDanhSachHoaDon);
        btnXemDanhSachHoaDon.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		XemDanhSachHoaDon_GUI xemDanhSachHoaDon = new XemDanhSachHoaDon_GUI(emp);
        		xemDanhSachHoaDon.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnSuaThongTinHoaDon = new JButton("Sửa thông tin hóa đơn");
        btnSuaThongTinHoaDon.setIcon(new ImageIcon("data\\icons\\icons8-edit-24.png"));
        btnSuaThongTinHoaDon.setHorizontalAlignment(SwingConstants.LEFT);
        btnSuaThongTinHoaDon.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSuaThongTinHoaDon.setFocusable(false);
        btnSuaThongTinHoaDon.setBackground(Color.WHITE);
        btnSuaThongTinHoaDon.setBounds(681, 246, 216, 34);
        panel_2.add(btnSuaThongTinHoaDon);
        btnSuaThongTinHoaDon.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimThongTinHoaDon_GUI timThongTinHoaDon = new TimThongTinHoaDon_GUI(emp, btnSuaThongTinHoaDon.getText());
        		timThongTinHoaDon.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXoaThongTinHoaDon = new JButton("Xóa thông tin hóa đơn");
        btnXoaThongTinHoaDon.setIcon(new ImageIcon("data\\icons\\icons8-remove-24.png"));
        btnXoaThongTinHoaDon.setHorizontalAlignment(SwingConstants.LEFT);
        btnXoaThongTinHoaDon.setFont(new Font("Arial", Font.PLAIN, 12));
        btnXoaThongTinHoaDon.setFocusable(false);
        btnXoaThongTinHoaDon.setBackground(Color.WHITE);
        btnXoaThongTinHoaDon.setBounds(921, 246, 228, 34);
        panel_2.add(btnXoaThongTinHoaDon);
        btnXoaThongTinHoaDon.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimThongTinHoaDon_GUI timThongTinHoaDon = new TimThongTinHoaDon_GUI(emp, btnXoaThongTinHoaDon.getText());
        		timThongTinHoaDon.setVisible(true);
        		setVisible(false);
        	}
        });
        
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
        
        JLabel lblQuanLyNhaCungCap = new JLabel("Quản lý nhà cung cấp:");
        lblQuanLyNhaCungCap.setHorizontalAlignment(SwingConstants.RIGHT);
        lblQuanLyNhaCungCap.setFont(new Font("Arial", Font.BOLD, 13));
        lblQuanLyNhaCungCap.setBounds(53, 307, 160, 27);
        panel_2.add(lblQuanLyNhaCungCap);
        
        JButton btnThemNhaCungCap = new JButton("Thêm nhà cung cấp");
        btnThemNhaCungCap.setIcon(new ImageIcon("data\\icons\\add1.png"));
        btnThemNhaCungCap.setHorizontalAlignment(SwingConstants.LEFT);
        btnThemNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 12));
        btnThemNhaCungCap.setFocusable(false);
        btnThemNhaCungCap.setBackground(Color.WHITE);
        btnThemNhaCungCap.setBounds(223, 303, 192, 34);
        panel_2.add(btnThemNhaCungCap);
        btnThemNhaCungCap.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThemNhaCungCap_GUI themNhaCungCap = new ThemNhaCungCap_GUI(emp);
        		themNhaCungCap.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXemDanhSachNhaCungCap = new JButton("Xem danh sách nhà cung cấp");
        btnXemDanhSachNhaCungCap.setIcon(new ImageIcon("data\\icons\\icons8-list-supplier-24.png"));
        btnXemDanhSachNhaCungCap.setHorizontalAlignment(SwingConstants.LEFT);
        btnXemDanhSachNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 12));
        btnXemDanhSachNhaCungCap.setFocusable(false);
        btnXemDanhSachNhaCungCap.setBackground(Color.WHITE);
        btnXemDanhSachNhaCungCap.setBounds(436, 303, 223, 34);
        panel_2.add(btnXemDanhSachNhaCungCap);
        btnXemDanhSachNhaCungCap.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		XemDanhSachNhaCungCap_GUI xemDanhSachNhaCungCap = new XemDanhSachNhaCungCap_GUI(emp);
        		xemDanhSachNhaCungCap.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnSuaThongTinNhaCungCap = new JButton("Sửa thông tin nhà cung cấp");
        btnSuaThongTinNhaCungCap.setIcon(new ImageIcon("data\\icons\\icons8-edit-suppier-24.png"));
        btnSuaThongTinNhaCungCap.setHorizontalAlignment(SwingConstants.LEFT);
        btnSuaThongTinNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSuaThongTinNhaCungCap.setFocusable(false);
        btnSuaThongTinNhaCungCap.setBackground(Color.WHITE);
        btnSuaThongTinNhaCungCap.setBounds(681, 303, 216, 34);
        panel_2.add(btnSuaThongTinNhaCungCap);
        btnSuaThongTinNhaCungCap.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimNhaCungCap_GUI timNhaCungCap = new TimNhaCungCap_GUI(emp, btnSuaThongTinNhaCungCap.getText());
        		timNhaCungCap.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXoaThongTinNhaCungCap = new JButton("Xóa thông tin nhà cung cấp");
        btnXoaThongTinNhaCungCap.setIcon(new ImageIcon("data\\icons\\icons8-delete-supplier-24.png"));
        btnXoaThongTinNhaCungCap.setHorizontalAlignment(SwingConstants.LEFT);
        btnXoaThongTinNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 12));
        btnXoaThongTinNhaCungCap.setFocusable(false);
        btnXoaThongTinNhaCungCap.setBackground(Color.WHITE);
        btnXoaThongTinNhaCungCap.setBounds(921, 303, 228, 34);
        panel_2.add(btnXoaThongTinNhaCungCap);
        btnXoaThongTinNhaCungCap.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimNhaCungCap_GUI timNhaCungCap = new TimNhaCungCap_GUI(emp, btnXoaThongTinNhaCungCap.getText());
        		timNhaCungCap.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JLabel lblThongKeThuoc = new JLabel("Quản lý thống kê:");
        lblThongKeThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
        lblThongKeThuoc.setFont(new Font("Arial", Font.BOLD, 13));
        lblThongKeThuoc.setBounds(90, 364, 123, 27);
        panel_2.add(lblThongKeThuoc);
        
        JButton btnThongKeThuocHetHan = new JButton("Thống kê thuốc hết hạn");
        btnThongKeThuocHetHan.setIcon(new ImageIcon("data\\icons\\statistics-chart-1.png"));
        btnThongKeThuocHetHan.setHorizontalAlignment(SwingConstants.LEFT);
        btnThongKeThuocHetHan.setFont(new Font("Arial", Font.PLAIN, 12));
        btnThongKeThuocHetHan.setFocusable(false);
        btnThongKeThuocHetHan.setBackground(Color.WHITE);
        btnThongKeThuocHetHan.setBounds(223, 360, 192, 34);
        panel_2.add(btnThongKeThuocHetHan);
        btnThongKeThuocHetHan.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThongKeThuocHetHan_GUI thongKeThuocHetHan = new ThongKeThuocHetHan_GUI(emp);
        		thongKeThuocHetHan.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnThongKeThuocTonKho = new JButton("Thống kê thuốc tồn kho");
        btnThongKeThuocTonKho.setIcon(new ImageIcon("data\\icons\\statistics-chart-2.png"));
        btnThongKeThuocTonKho.setHorizontalAlignment(SwingConstants.LEFT);
        btnThongKeThuocTonKho.setFont(new Font("Arial", Font.PLAIN, 12));
        btnThongKeThuocTonKho.setFocusable(false);
        btnThongKeThuocTonKho.setBackground(Color.WHITE);
        btnThongKeThuocTonKho.setBounds(436, 360, 223, 34);
        panel_2.add(btnThongKeThuocTonKho);
        btnThongKeThuocTonKho.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThongKeThuocTonKho_GUI thongKeThuocTonKho = new ThongKeThuocTonKho_GUI(emp);
        		thongKeThuocTonKho.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnThongKeDoanhSoBan = new JButton("Thống kê doanh số bán");
        btnThongKeDoanhSoBan.setIcon(new ImageIcon("data\\icons\\statistics-chart-3.png"));
        btnThongKeDoanhSoBan.setHorizontalAlignment(SwingConstants.LEFT);
        btnThongKeDoanhSoBan.setFont(new Font("Arial", Font.PLAIN, 12));
        btnThongKeDoanhSoBan.setFocusable(false);
        btnThongKeDoanhSoBan.setBackground(Color.WHITE);
        btnThongKeDoanhSoBan.setBounds(681, 360, 216, 34);
        panel_2.add(btnThongKeDoanhSoBan);
        btnThongKeDoanhSoBan.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThongKeDoanhSoBan_GUI thongKeDoanhSoBan = new ThongKeDoanhSoBan_GUI(emp);
        		thongKeDoanhSoBan.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JLabel lblQuanLyNhanVien = new JLabel("Quản lý nhân viên:");
        lblQuanLyNhanVien.setHorizontalAlignment(SwingConstants.RIGHT);
        lblQuanLyNhanVien.setFont(new Font("Arial", Font.BOLD, 13));
        lblQuanLyNhanVien.setBounds(53, 418, 160, 27);
        panel_2.add(lblQuanLyNhanVien);
        
        JButton btnThemNhanVien = new JButton("Thêm nhân viên");
        btnThemNhanVien.setIcon(new ImageIcon("data\\icons\\add-employee.png"));
        btnThemNhanVien.setHorizontalAlignment(SwingConstants.LEFT);
        btnThemNhanVien.setFont(new Font("Arial", Font.PLAIN, 12));
        btnThemNhanVien.setFocusable(false);
        btnThemNhanVien.setBackground(Color.WHITE);
        btnThemNhanVien.setBounds(223, 414, 192, 34);
        panel_2.add(btnThemNhanVien);
        btnThemNhanVien.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThemNhanVien_GUI themNhanVien = new ThemNhanVien_GUI(emp);
        		themNhanVien.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXemDanhSachNhanVien = new JButton("Xem danh sách nhân viên");
        btnXemDanhSachNhanVien.setIcon(new ImageIcon("data\\icons\\list-employee.png"));
        btnXemDanhSachNhanVien.setHorizontalAlignment(SwingConstants.LEFT);
        btnXemDanhSachNhanVien.setFont(new Font("Arial", Font.PLAIN, 12));
        btnXemDanhSachNhanVien.setFocusable(false);
        btnXemDanhSachNhanVien.setBackground(Color.WHITE);
        btnXemDanhSachNhanVien.setBounds(436, 414, 223, 34);
        panel_2.add(btnXemDanhSachNhanVien);
        btnXemDanhSachNhanVien.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		XemDanhSachNhanVien_GUI xemDanhSachNhanVien = new XemDanhSachNhanVien_GUI(emp);
        		xemDanhSachNhanVien.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnSuaThongTinNhanVien = new JButton("Sửa thông tin nhân viên");
        btnSuaThongTinNhanVien.setIcon(new ImageIcon("data\\icons\\edit-employee.png"));
        btnSuaThongTinNhanVien.setHorizontalAlignment(SwingConstants.LEFT);
        btnSuaThongTinNhanVien.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSuaThongTinNhanVien.setFocusable(false);
        btnSuaThongTinNhanVien.setBackground(Color.WHITE);
        btnSuaThongTinNhanVien.setBounds(681, 414, 216, 34);
        panel_2.add(btnSuaThongTinNhanVien);
        btnSuaThongTinNhanVien.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimThongTinNhanVien_GUI timThongTinNhanVien = new TimThongTinNhanVien_GUI(emp, btnSuaThongTinNhanVien.getText());
        		timThongTinNhanVien.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXoaThongTinNhanVien = new JButton("Xóa thông tin nhân viên");
        btnXoaThongTinNhanVien.setIcon(new ImageIcon("data\\icons\\delete-employee.png"));
        btnXoaThongTinNhanVien.setHorizontalAlignment(SwingConstants.LEFT);
        btnXoaThongTinNhanVien.setFont(new Font("Arial", Font.PLAIN, 12));
        btnXoaThongTinNhanVien.setFocusable(false);
        btnXoaThongTinNhanVien.setBackground(Color.WHITE);
        btnXoaThongTinNhanVien.setBounds(921, 414, 228, 34);
        panel_2.add(btnXoaThongTinNhanVien);
        btnXoaThongTinNhanVien.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String matKhauXoa = JOptionPane.showInputDialog("Bạn cần nhập mật khẩu để truy cập chức năng này:").trim();
				if (!matKhauXoa.equalsIgnoreCase("")) {
					if (matKhauXoa.equals("Delete")) {
						TimThongTinNhanVien_GUI timThongTinNhanVien = new TimThongTinNhanVien_GUI(emp, btnXoaThongTinNhanVien.getText());
		        		timThongTinNhanVien.setVisible(true);
		        		setVisible(false);
					} else {
						soLanNhapSai++;
						if (soLanNhapSai >= 3) {
							TaiKhoan_DAO taikhoan_dao = new TaiKhoan_DAO();
							if (taikhoan_dao.khoaTaiKhoan(emp.getMaNV())) {
								Toolkit.getDefaultToolkit().beep();
								JOptionPane.showMessageDialog(null,
										"Tài khoản của bạn đã bị khóa do nhập sai mật khẩu xóa quá 3 lần liên tiếp",
										"Tài khoản bị khóa do vi phạm chính sách bảo mật",
										JOptionPane.ERROR_MESSAGE);
							}
							DangNhap_GUI dangNhap = new DangNhap_GUI();
							dangNhap.setVisible(true);
							setVisible(false);
						}
					}
				}
        	}
        });
        
        JButton btnThongKeSoLuongKhachHang = new JButton("Thống kê số lượng khách hàng");
        btnThongKeSoLuongKhachHang.setIcon(new ImageIcon("data\\icons\\statistics-chart-4.png"));
        btnThongKeSoLuongKhachHang.setHorizontalAlignment(SwingConstants.LEFT);
        btnThongKeSoLuongKhachHang.setFont(new Font("Arial", Font.PLAIN, 12));
        btnThongKeSoLuongKhachHang.setFocusable(false);
        btnThongKeSoLuongKhachHang.setBackground(Color.WHITE);
        btnThongKeSoLuongKhachHang.setBounds(921, 357, 228, 34);
        panel_2.add(btnThongKeSoLuongKhachHang);
        btnThongKeSoLuongKhachHang.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThongKeSoLuongKhachHang_GUI thongKeSoLuongKhachHang = new ThongKeSoLuongKhachHang_GUI(emp);
        		thongKeSoLuongKhachHang.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JLabel lblQuanLyKhachHang = new JLabel("Quản lý khách hàng:");
        lblQuanLyKhachHang.setHorizontalAlignment(SwingConstants.RIGHT);
        lblQuanLyKhachHang.setFont(new Font("Arial", Font.BOLD, 13));
        lblQuanLyKhachHang.setBounds(53, 472, 160, 27);
        panel_2.add(lblQuanLyKhachHang);
        
        JButton btnThemKhachHang = new JButton("Thêm khách hàng");
        btnThemKhachHang.setIcon(new ImageIcon("data\\icons\\add-customer.png"));
        btnThemKhachHang.setHorizontalAlignment(SwingConstants.LEFT);
        btnThemKhachHang.setFont(new Font("Arial", Font.PLAIN, 12));
        btnThemKhachHang.setFocusable(false);
        btnThemKhachHang.setBackground(Color.WHITE);
        btnThemKhachHang.setBounds(223, 468, 192, 34);
        panel_2.add(btnThemKhachHang);
        btnThemKhachHang.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThemKhacHang_GUI themKhacHang = new ThemKhacHang_GUI(emp);
        		themKhacHang.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXemDanhSachKhachHang = new JButton("Xem danh sách khách hàng");
        btnXemDanhSachKhachHang.setIcon(new ImageIcon("data\\icons\\list-customer.png"));
        btnXemDanhSachKhachHang.setHorizontalAlignment(SwingConstants.LEFT);
        btnXemDanhSachKhachHang.setFont(new Font("Arial", Font.PLAIN, 12));
        btnXemDanhSachKhachHang.setFocusable(false);
        btnXemDanhSachKhachHang.setBackground(Color.WHITE);
        btnXemDanhSachKhachHang.setBounds(436, 468, 223, 34);
        panel_2.add(btnXemDanhSachKhachHang);
        btnXemDanhSachKhachHang.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		XemDanhSachKhachHang_GUI xemDanhSachKhachHang = new XemDanhSachKhachHang_GUI(emp);
        		xemDanhSachKhachHang.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnSuaTaiKhoanKhachHang = new JButton("Sửa thông tin khách hàng");
        btnSuaTaiKhoanKhachHang.setIcon(new ImageIcon("data\\icons\\edit-customer.png"));
        btnSuaTaiKhoanKhachHang.setHorizontalAlignment(SwingConstants.LEFT);
        btnSuaTaiKhoanKhachHang.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSuaTaiKhoanKhachHang.setFocusable(false);
        btnSuaTaiKhoanKhachHang.setBackground(Color.WHITE);
        btnSuaTaiKhoanKhachHang.setBounds(681, 468, 216, 34);
        panel_2.add(btnSuaTaiKhoanKhachHang);
        btnSuaTaiKhoanKhachHang.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimThongTinKhachHang_GUI timThongTinKhachHang = new TimThongTinKhachHang_GUI(nv, btnSuaTaiKhoanKhachHang.getText());
        		timThongTinKhachHang.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JButton btnXoaTaiKhoanKhachHang = new JButton("Xóa thông tin khách hàng");
        btnXoaTaiKhoanKhachHang.setIcon(new ImageIcon("data\\icons\\delete-customer.png"));
        btnXoaTaiKhoanKhachHang.setHorizontalAlignment(SwingConstants.LEFT);
        btnXoaTaiKhoanKhachHang.setFont(new Font("Arial", Font.PLAIN, 12));
        btnXoaTaiKhoanKhachHang.setFocusable(false);
        btnXoaTaiKhoanKhachHang.setBackground(Color.WHITE);
        btnXoaTaiKhoanKhachHang.setBounds(921, 468, 228, 34);
        panel_2.add(btnXoaTaiKhoanKhachHang);
        btnXoaTaiKhoanKhachHang.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TimThongTinKhachHang_GUI timThongTinKhachHang = new TimThongTinKhachHang_GUI(nv, btnXoaTaiKhoanKhachHang.getText());
        		timThongTinKhachHang.setVisible(true);
        		setVisible(false);
        	}
        });
        
		setTitle("Quản lý quầy thuốc bệnh viện");
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
