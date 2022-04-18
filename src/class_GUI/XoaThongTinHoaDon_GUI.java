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
import javax.swing.ListSelectionModel;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import class_DAO.ChiTietHoaDon_DAO;
import class_DAO.HoaDon_DAO;
import class_DAO.Thuoc_DAO;
import class_Entities.Thuoc;
import class_Entities.ChiTietHoaDon;
import class_Entities.HoaDon;
import class_Entities.KhachHang;
import class_Entities.NhanVien;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JRadioButton;

public class XoaThongTinHoaDon_GUI extends JFrame implements WindowListener, ListSelectionListener {
	private JTextField txtTenThuoc;
	private JTextField txtDonGiaBan;
	private JTextField txtXuatXu;
	private DefaultTableModel modelChiTietHoaDon;
	private JTable tableChiTietHoaDon;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	private JButton btnBack;

	private JTextField txtDonViThuoc;
	private JTextField txtMaHoaDon;
	private JTextField txtTenKhachHang;
	private JTextField txtEmail;
	private JTextField txtSoDienThoai;

	private JTextArea textAreaDiaChi;

	private JLabel lblMaKhachHang;
	private JTextField txtNgayHienTai;
	private JTextField txtTenNhaCungCap;

	private JTextField txtTongTienThanhToan;

	private DecimalFormat fmt = new DecimalFormat("###,###");
	private JTextField txtNgayLapHoaDon;
	private JTextField txtLoaiThuoc;

	private ArrayList<ChiTietHoaDon> lineItems = new ArrayList<ChiTietHoaDon>();
	private JRadioButton radKhong;
	private JRadioButton radCo;
	private JLabel lblMaThuoc;
	private JTextField txtMaThuoc;
	private JTextField txtSoLuongBan;
	private JTextField txtMaKhachHang;
	private JTextField txtTongTienTraLaiChoKhach;
	private JTextField txtMienGiam;

	private HoaDon hoaDon;
	private NhanVien emp;
	private String tenChucNangYeuCauBanDau;

	private String layNgayHienTai() {
		Date ngayHienTai = new Date();
		return sdf.format(ngayHienTai);
	}

	private void xoaHetDuLieuTrenTableModel() {
		modelChiTietHoaDon.getDataVector().removeAllElements();
	}

	private ArrayList<ChiTietHoaDon> layDSChiTietHoaDon(HoaDon hoaDon) {
		String maHoaDon = hoaDon.getMaHD().trim();
		ChiTietHoaDon_DAO chitiethoaDon_dao = new ChiTietHoaDon_DAO();
		return chitiethoaDon_dao.layDSChiTietHoaDon(maHoaDon);
	}

	private void capNhatDuLieuTrenBang() {
		xoaHetDuLieuTrenTableModel();
		int soThuTu = 1;
		Thuoc_DAO thuoc_dao = new Thuoc_DAO();
		for (ChiTietHoaDon cthd : lineItems) {
			Thuoc thuoc = thuoc_dao.layThuoc(cthd.getThuoc().getMaThuoc());
			float thanhTien = cthd.getDonGiaBan() * cthd.getSoLuong();
			modelChiTietHoaDon.addRow(new Object[] { soThuTu++, thuoc.getMaThuoc(), thuoc.getTenThuoc(),
					thuoc.getLoaiThuoc(), thuoc.getDonViThuoc(), thuoc.getXuatXu(), thuoc.getNhaCungCap().getTenNCC(),
					fmt.format(cthd.getDonGiaBan()), cthd.getSoLuong(), fmt.format(thanhTien) });
		}
		tableChiTietHoaDon.setModel(modelChiTietHoaDon);
	}

	private void initForm() {
		txtMaHoaDon.setText(hoaDon.getMaHD());
		txtNgayLapHoaDon.setText(sdf.format(hoaDon.getNgayLapHoaDon()));
		KhachHang khachHang = hoaDon.getKhachHang();
		txtMaKhachHang.setText(khachHang.getMaKH());
		txtTenKhachHang.setText(khachHang.getHoTenKhachHang());
		String theBaoHiem = hoaDon.getTheBaoHiem();
		if (theBaoHiem.equalsIgnoreCase("Có")) {
			radCo.setSelected(true);
			radKhong.setEnabled(false);
		} else {
			radKhong.setSelected(true);
			radCo.setEnabled(false);
		}
		txtEmail.setText(khachHang.getEmail());
		txtSoDienThoai.setText(khachHang.getSoDienThoai());
		textAreaDiaChi.setText(khachHang.getDiaChi());
		lineItems = layDSChiTietHoaDon(hoaDon);
		capNhatDuLieuTrenBang();
	}

	private void fillForm(int row) {
		if (row != -1) {
			String maThuoc = String.valueOf(tableChiTietHoaDon.getValueAt(row, 1));
			txtMaThuoc.setText(maThuoc);
			String tenThuoc = String.valueOf(tableChiTietHoaDon.getValueAt(row, 2));
			txtTenThuoc.setText(tenThuoc);
			String loaiThuoc = String.valueOf(tableChiTietHoaDon.getValueAt(row, 3));
			txtLoaiThuoc.setText(loaiThuoc);
			String donViThuoc = String.valueOf(tableChiTietHoaDon.getValueAt(row, 4));
			txtDonViThuoc.setText(donViThuoc);
			String xuatXu = String.valueOf(tableChiTietHoaDon.getValueAt(row, 5));
			txtXuatXu.setText(xuatXu);
			String nhaCungCap = String.valueOf(tableChiTietHoaDon.getValueAt(row, 6));
			txtTenNhaCungCap.setText(nhaCungCap);
			String donGiaBan = String.valueOf(tableChiTietHoaDon.getValueAt(row, 7));
			txtDonGiaBan.setText(donGiaBan);
			String soLuongBan = String.valueOf(tableChiTietHoaDon.getValueAt(row, 8));
			txtSoLuongBan.setText(soLuongBan);
		}
	}

	private String loaiBoDinhDangTien(String soTien) {
		if (soTien.contains(",")) {
			String[] tachChuoi = soTien.split(",");
			soTien = "";
			for (String string : tachChuoi) {
				soTien += string;
			}
		}
		return soTien;
	}

	private float tinhTongThanhTien() {
		float tongThanhTien = 0;
		for (ChiTietHoaDon cthd : layDSChiTietHoaDon(hoaDon)) {
			float thanhTien = cthd.getDonGiaBan() * cthd.getSoLuong();
			tongThanhTien += thanhTien;
		}
		return tongThanhTien;
	}

	private float tinhMienGiam() {
		return tinhTongThanhTien() * hoaDon.getMienGiam();
	}

	private float tinhTongTienTraLaiChoKhach() {
		return tinhTongThanhTien() - tinhMienGiam();
	}

	/**
	 * Create the frame.
	 */
	public XoaThongTinHoaDon_GUI(HoaDon hd, NhanVien nv, String tenChucNangYeuCauBanDau) {
		hoaDon = hd;
		emp = nv;
		this.tenChucNangYeuCauBanDau = tenChucNangYeuCauBanDau;
		
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\icons8-remove-24.png"));
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
		mntmNewMenuItem_2.setIcon(new ImageIcon("data\\icons\\search.png"));
		mntmNewMenuItem_2.setFont(new Font("Arial", Font.PLAIN, 12));
		mnSnPhm.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Th\u00EAm S\u1EA3n Ph\u1EA9m M\u1EDBi");
		mntmNewMenuItem_3.setIcon(new ImageIcon("data\\icons\\add.png"));
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

		JButton btnXoa = new JButton("Xóa hóa đơn bán thuốc");
		btnXoa.setBackground(Color.WHITE);
		btnXoa.setIcon(new ImageIcon("data\\icons\\delete.png"));
		btnXoa.setFocusable(false);
		panel.add(btnXoa);
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count_row = 0;
				Toolkit.getDefaultToolkit().beep();
				int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa hóa đơn này hay không?",
						"Chú ý", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					ChiTietHoaDon_DAO cthd_dao = new ChiTietHoaDon_DAO();
					Thuoc_DAO thuoc_dao = new Thuoc_DAO();
					String maHoaDon = txtMaHoaDon.getText().trim();
					for (ChiTietHoaDon cthd : lineItems) {
						String maThuoc = cthd.getThuoc().getMaThuoc();
						if (cthd_dao.xoaMotChiTietHoaDon(maHoaDon, maThuoc)) {
							Thuoc thuoc = thuoc_dao.layThuoc(maThuoc);
							int soLuongBan = cthd.getSoLuong();
							int soLuongTon = thuoc.getSoLuongTon() + soLuongBan;
							if (thuoc_dao.capNhatSoLuongTonCuaThuoc(soLuongTon, maThuoc)) {
								count_row += 1;
							} else {
								Toolkit.getDefaultToolkit().beep();
								JOptionPane.showMessageDialog(null,
										"Lỗi khi cập nhật lại số lượng tồn của sản phẩm này trong CSDL",
										"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null,
									"Lỗi khi xóa dòng sản phẩm thứ: " + (lineItems.indexOf(cthd) + 1) + " trong CSDL",
									"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
						}
					}
					
					HoaDon_DAO hoadon_dao = new HoaDon_DAO();
					if (hoadon_dao.xoaMotHoaDon(maHoaDon) && count_row == lineItems.size()) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Xóa hóa đơn thành công", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
						TimThongTinHoaDon_GUI timThongTinHoaDon = new TimThongTinHoaDon_GUI(emp, tenChucNangYeuCauBanDau);
						timThongTinHoaDon.setVisible(true);
						setVisible(false);
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Xóa hóa đơn không thành công", "Lỗi kết nối CSDL",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		JButton btnHuy = new JButton("Hủy");
		btnHuy.setIcon(new ImageIcon("data\\icons\\exit.png"));
		btnHuy.setFocusable(false);
		btnHuy.setBackground(Color.WHITE);
		panel.add(btnHuy);
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (emp.getChucVu().equalsIgnoreCase("Quản lý nhà thuốc")) {
					QuanLy_GUI quanLy = new QuanLy_GUI(emp);
					quanLy.setVisible(true);
					setVisible(false);
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

		btnBack = new JButton("Trở về trang trước");
		btnBack.setIcon(new ImageIcon("data\\icons\\home.png"));
		btnBack.setMnemonic(KeyEvent.VK_T);
		btnBack.setFocusable(false);
		panel_1.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlShadow));
		panel_2.setBackground(Color.WHITE);
		getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("data\\icons\\logo3.png"));
		lblLogo.setBounds(30, 26, 300, 60);
		panel_2.add(lblLogo);

		JLabel lblTitle = new JLabel("");
		lblTitle.setIcon(new ImageIcon(
				"data\\icons\\delete-purchase-title.png"));
		lblTitle.setBounds(387, 10, 800, 93);
		panel_2.add(lblTitle);

		JPanel panel_chart = new JPanel();
		panel_chart.setBackground(Color.WHITE);
		panel_chart.setBounds(0, 109, 1522, 651);
		panel_chart.setLayout(null);
		panel_2.add(panel_chart);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(10, 10, 1028, 301);
		panel_3.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_chart.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblThongTinHoaDon = new JLabel("Thông tin hóa đơn");
		lblThongTinHoaDon.setForeground(SystemColor.textHighlight);
		lblThongTinHoaDon.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinHoaDon.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinHoaDon.setBounds(20, 10, 957, 41);
		panel_3.add(lblThongTinHoaDon);

		JLabel lblTenThuoc = new JLabel("Tên thuốc:");
		lblTenThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTenThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblTenThuoc.setBounds(497, 113, 108, 26);
		panel_3.add(lblTenThuoc);

		txtTenThuoc = new JTextField();
		txtTenThuoc.setEditable(false);
		txtTenThuoc.setFont(new Font("Arial", Font.PLAIN, 16));
		txtTenThuoc.setColumns(10);
		txtTenThuoc.setBackground(Color.WHITE);
		txtTenThuoc.setBounds(628, 110, 349, 29);
		panel_3.add(txtTenThuoc);

		lblMaThuoc = new JLabel("Mã thuốc:");
		lblMaThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaThuoc.setBounds(20, 111, 125, 26);
		panel_3.add(lblMaThuoc);

		JLabel lblNgayLap = new JLabel("Ngày lập:");
		lblNgayLap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgayLap.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgayLap.setBounds(497, 68, 108, 26);
		panel_3.add(lblNgayLap);

		JLabel lblLoaiThuoc = new JLabel("Loại thuốc:");
		lblLoaiThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoaiThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblLoaiThuoc.setBounds(31, 207, 114, 26);
		panel_3.add(lblLoaiThuoc);

		JLabel lblDonViThuoc = new JLabel("Đơn vị thuốc:");
		lblDonViThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDonViThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblDonViThuoc.setBounds(497, 207, 108, 26);
		panel_3.add(lblDonViThuoc);

		JLabel lblDonGiaBan = new JLabel("Đơn giá bán:");
		lblDonGiaBan.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDonGiaBan.setFont(new Font("Arial", Font.BOLD, 16));
		lblDonGiaBan.setBounds(497, 159, 108, 26);
		panel_3.add(lblDonGiaBan);

		txtDonGiaBan = new JTextField();
		txtDonGiaBan.setEditable(false);
		txtDonGiaBan.setFont(new Font("Arial", Font.PLAIN, 16));
		txtDonGiaBan.setColumns(10);
		txtDonGiaBan.setBackground(Color.WHITE);
		txtDonGiaBan.setBounds(628, 158, 349, 29);
		panel_3.add(txtDonGiaBan);

		JLabel lblSoLuongBan = new JLabel("Số lượng bán:");
		lblSoLuongBan.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoLuongBan.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoLuongBan.setBounds(20, 159, 125, 26);
		panel_3.add(lblSoLuongBan);

		JLabel lblXuatXu = new JLabel("Xuất xứ:");
		lblXuatXu.setHorizontalAlignment(SwingConstants.RIGHT);
		lblXuatXu.setFont(new Font("Arial", Font.BOLD, 16));
		lblXuatXu.setBounds(20, 256, 125, 26);
		panel_3.add(lblXuatXu);

		txtXuatXu = new JTextField();
		txtXuatXu.setEditable(false);
		txtXuatXu.setFont(new Font("Arial", Font.PLAIN, 16));
		txtXuatXu.setColumns(10);
		txtXuatXu.setBackground(Color.WHITE);
		txtXuatXu.setBounds(168, 255, 308, 29);
		panel_3.add(txtXuatXu);

		txtDonViThuoc = new JTextField();
		txtDonViThuoc.setEditable(false);
		txtDonViThuoc.setFont(new Font("Arial", Font.PLAIN, 16));
		txtDonViThuoc.setColumns(10);
		txtDonViThuoc.setBackground(Color.WHITE);
		txtDonViThuoc.setBounds(628, 206, 349, 29);
		panel_3.add(txtDonViThuoc);

		txtNgayLapHoaDon = new JTextField();
		txtNgayLapHoaDon.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNgayLapHoaDon.setEditable(false);
		txtNgayLapHoaDon.setColumns(10);
		txtNgayLapHoaDon.setBackground(Color.WHITE);
		txtNgayLapHoaDon.setBounds(628, 65, 349, 29);
		panel_3.add(txtNgayLapHoaDon);

		JLabel lblMaHoaDon = new JLabel("Mã hóa đơn:");
		lblMaHoaDon.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaHoaDon.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaHoaDon.setBounds(20, 66, 126, 26);
		panel_3.add(lblMaHoaDon);

		txtMaHoaDon = new JTextField();
		txtMaHoaDon.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaHoaDon.setEditable(false);
		txtMaHoaDon.setColumns(10);
		txtMaHoaDon.setBackground(Color.WHITE);
		txtMaHoaDon.setBounds(169, 67, 308, 29);
		panel_3.add(txtMaHoaDon);

		JLabel lblTenNhaCungCap = new JLabel("Nhà cung cấp:");
		lblTenNhaCungCap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTenNhaCungCap.setFont(new Font("Arial", Font.BOLD, 16));
		lblTenNhaCungCap.setBounds(497, 254, 114, 26);
		panel_3.add(lblTenNhaCungCap);

		txtTenNhaCungCap = new JTextField();
		txtTenNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 16));
		txtTenNhaCungCap.setEditable(false);
		txtTenNhaCungCap.setColumns(10);
		txtTenNhaCungCap.setBackground(Color.WHITE);
		txtTenNhaCungCap.setBounds(628, 253, 349, 29);
		panel_3.add(txtTenNhaCungCap);

		txtLoaiThuoc = new JTextField();
		txtLoaiThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		txtLoaiThuoc.setEditable(false);
		txtLoaiThuoc.setColumns(10);
		txtLoaiThuoc.setBackground(Color.WHITE);
		txtLoaiThuoc.setBounds(168, 204, 308, 29);
		panel_3.add(txtLoaiThuoc);

		txtMaThuoc = new JTextField();
		txtMaThuoc.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaThuoc.setEditable(false);
		txtMaThuoc.setColumns(10);
		txtMaThuoc.setBackground(Color.WHITE);
		txtMaThuoc.setBounds(168, 110, 308, 29);
		panel_3.add(txtMaThuoc);

		txtSoLuongBan = new JTextField();
		txtSoLuongBan.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoLuongBan.setEditable(false);
		txtSoLuongBan.setColumns(10);
		txtSoLuongBan.setBackground(Color.WHITE);
		txtSoLuongBan.setBounds(168, 156, 308, 29);
		panel_3.add(txtSoLuongBan);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(1048, 10, 464, 301);
		panel_4.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_chart.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblThongTinKhacHang = new JLabel("Thông tin khách hàng");
		lblThongTinKhacHang.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinKhacHang.setForeground(SystemColor.textHighlight);
		lblThongTinKhacHang.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinKhacHang.setBounds(10, 10, 445, 41);
		panel_4.add(lblThongTinKhacHang);

		lblMaKhachHang = new JLabel("Mã khách hàng:");
		lblMaKhachHang.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaKhachHang.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaKhachHang.setBounds(10, 61, 171, 26);
		panel_4.add(lblMaKhachHang);

		String[] option_trangthai = "Tài khoản đang mở;Tài khoản đang khóa".split(";");

		JLabel lblTenKhachHang = new JLabel("Tên khách hàng:");
		lblTenKhachHang.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTenKhachHang.setFont(new Font("Arial", Font.BOLD, 16));
		lblTenKhachHang.setBounds(10, 103, 171, 26);
		panel_4.add(lblTenKhachHang);

		txtTenKhachHang = new JTextField();
		txtTenKhachHang.setBackground(Color.WHITE);
		txtTenKhachHang.setEditable(false);
		txtTenKhachHang.setFont(new Font("Arial", Font.PLAIN, 16));
		txtTenKhachHang.setColumns(10);
		txtTenKhachHang.setBounds(191, 102, 251, 29);
		panel_4.add(txtTenKhachHang);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Arial", Font.BOLD, 16));
		lblEmail.setBounds(10, 184, 171, 26);
		panel_4.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBackground(Color.WHITE);
		txtEmail.setEditable(false);
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 16));
		txtEmail.setColumns(10);
		txtEmail.setBounds(191, 183, 251, 29);
		panel_4.add(txtEmail);

		JLabel lblSoDienThoai = new JLabel("Số điện thoại:");
		lblSoDienThoai.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoDienThoai.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoDienThoai.setBounds(20, 224, 161, 26);
		panel_4.add(lblSoDienThoai);

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setBackground(Color.WHITE);
		txtSoDienThoai.setEditable(false);
		txtSoDienThoai.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoDienThoai.setColumns(10);
		txtSoDienThoai.setBounds(191, 223, 251, 29);
		panel_4.add(txtSoDienThoai);

		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiaChi.setFont(new Font("Arial", Font.BOLD, 16));
		lblDiaChi.setBounds(20, 262, 161, 26);
		panel_4.add(lblDiaChi);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(191, 262, 251, 26);
		panel_4.add(scrollPane_1);

		JLabel lblTheBaoHiem = new JLabel("Thẻ bảo hiểm:");
		lblTheBaoHiem.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTheBaoHiem.setFont(new Font("Arial", Font.BOLD, 16));
		lblTheBaoHiem.setBounds(10, 144, 171, 26);
		panel_4.add(lblTheBaoHiem);

		radKhong = new JRadioButton("Không");
		radKhong.setSelected(true);
		radKhong.setFont(new Font("Arial", Font.PLAIN, 16));
		radKhong.setBackground(Color.WHITE);
		radKhong.setBounds(191, 147, 77, 21);
		panel_4.add(radKhong);

		radCo = new JRadioButton("Có");
		radCo.setBackground(Color.WHITE);
		radCo.setFont(new Font("Arial", Font.PLAIN, 16));
		radCo.setBounds(280, 147, 54, 21);
		panel_4.add(radCo);

		ButtonGroup group = new ButtonGroup();
		group.add(radKhong);
		group.add(radCo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 321, 1502, 278);
		panel_chart.add(scrollPane);

		textAreaDiaChi = new JTextArea();
		textAreaDiaChi.setEditable(false);
		textAreaDiaChi.setLineWrap(true);
		textAreaDiaChi.setFont(new Font("Arial", Font.PLAIN, 16));
		scrollPane_1.setViewportView(textAreaDiaChi);

		txtMaKhachHang = new JTextField();
		txtMaKhachHang.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaKhachHang.setEditable(false);
		txtMaKhachHang.setColumns(10);
		txtMaKhachHang.setBackground(Color.WHITE);
		txtMaKhachHang.setBounds(191, 58, 251, 29);
		panel_4.add(txtMaKhachHang);

		String[] headers = { "STT", "Mã thuốc", "Tên thuốc", "Loại thuốc", "Đơn vị thuốc", "Xuất xứ", "Nhà cung cấp",
				"Đơn giá", "Số lượng", "Thành tiền" };
		modelChiTietHoaDon = new DefaultTableModel(headers, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tableChiTietHoaDon = new JTable(modelChiTietHoaDon);
		tableChiTietHoaDon.getTableHeader().setBackground(Color.CYAN);
		tableChiTietHoaDon.getTableHeader().setFont(new Font(null, Font.PLAIN, 14));
		tableChiTietHoaDon.setSelectionBackground(Color.YELLOW);
		tableChiTietHoaDon.setSelectionForeground(Color.RED);
		tableChiTietHoaDon.setFont(new Font(null, Font.PLAIN, 14));
		tableChiTietHoaDon.setRowHeight(22);
		tableChiTietHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableChiTietHoaDon.getSelectionModel().addListSelectionListener(this);
		scrollPane.setViewportView(tableChiTietHoaDon);

		JLabel lblTongTienThanhToan = new JLabel("Tổng tiền thanh toán:");
		lblTongTienThanhToan.setForeground(Color.BLACK);
		lblTongTienThanhToan.setHorizontalAlignment(SwingConstants.LEFT);
		lblTongTienThanhToan.setFont(new Font("Arial", Font.BOLD, 18));
		lblTongTienThanhToan.setBounds(10, 612, 194, 26);
		panel_chart.add(lblTongTienThanhToan);

		txtTongTienThanhToan = new JTextField(fmt.format(tinhTongThanhTien()));
		txtTongTienThanhToan.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTongTienThanhToan.setEditable(false);
		txtTongTienThanhToan.setForeground(Color.RED);
		txtTongTienThanhToan.setFont(new Font("Arial", Font.BOLD, 18));
		txtTongTienThanhToan.setColumns(10);
		txtTongTienThanhToan.setBackground(Color.WHITE);
		txtTongTienThanhToan.setBounds(201, 611, 231, 29);
		panel_chart.add(txtTongTienThanhToan);
		txtTongTienThanhToan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String soTienKhachDua = txtTongTienThanhToan.getText().trim();
				float float_SoTienKhachDua = Float.parseFloat(loaiBoDinhDangTien(soTienKhachDua));
				txtTongTienThanhToan.setText(fmt.format(float_SoTienKhachDua));
			}
		});

		JLabel lblDonViTien_1 = new JLabel("VND");
		lblDonViTien_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDonViTien_1.setFont(new Font("Arial", Font.BOLD, 18));
		lblDonViTien_1.setBounds(442, 612, 49, 26);
		panel_chart.add(lblDonViTien_1);

		JLabel lblTongTienTraLaiChoKhach = new JLabel("Tổng tiền trả lại cho khách:");
		lblTongTienTraLaiChoKhach.setHorizontalAlignment(SwingConstants.LEFT);
		lblTongTienTraLaiChoKhach.setForeground(Color.RED);
		lblTongTienTraLaiChoKhach.setFont(new Font("Arial", Font.BOLD, 18));
		lblTongTienTraLaiChoKhach.setBounds(898, 612, 251, 26);
		panel_chart.add(lblTongTienTraLaiChoKhach);

		txtTongTienTraLaiChoKhach = new JTextField(fmt.format(tinhTongTienTraLaiChoKhach()));
		txtTongTienTraLaiChoKhach.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTongTienTraLaiChoKhach.setForeground(Color.RED);
		txtTongTienTraLaiChoKhach.setFont(new Font("Arial", Font.BOLD, 18));
		txtTongTienTraLaiChoKhach.setEditable(false);
		txtTongTienTraLaiChoKhach.setColumns(10);
		txtTongTienTraLaiChoKhach.setBackground(Color.WHITE);
		txtTongTienTraLaiChoKhach.setBounds(1146, 609, 307, 29);
		panel_chart.add(txtTongTienTraLaiChoKhach);

		JLabel lblDonViTien_3 = new JLabel("VND");
		lblDonViTien_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblDonViTien_3.setFont(new Font("Arial", Font.BOLD, 18));
		lblDonViTien_3.setBounds(1463, 612, 49, 26);
		panel_chart.add(lblDonViTien_3);

		JLabel lblMienGiam = new JLabel("Miễn giảm:");
		lblMienGiam.setHorizontalAlignment(SwingConstants.LEFT);
		lblMienGiam.setForeground(Color.BLUE);
		lblMienGiam.setFont(new Font("Arial", Font.BOLD, 18));
		lblMienGiam.setBounds(497, 612, 102, 26);
		panel_chart.add(lblMienGiam);

		txtMienGiam = new JTextField(fmt.format(tinhMienGiam()));
		txtMienGiam.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMienGiam.setForeground(Color.RED);
		txtMienGiam.setFont(new Font("Arial", Font.BOLD, 18));
		txtMienGiam.setEditable(false);
		txtMienGiam.setColumns(10);
		txtMienGiam.setBackground(Color.WHITE);
		txtMienGiam.setBounds(598, 609, 231, 29);
		panel_chart.add(txtMienGiam);

		JLabel lblDonViTien_2 = new JLabel("VND");
		lblDonViTien_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDonViTien_2.setFont(new Font("Arial", Font.BOLD, 18));
		lblDonViTien_2.setBounds(839, 612, 49, 26);
		panel_chart.add(lblDonViTien_2);

		JLabel lblXemNay = new JLabel("Ngày hiện tại:");
		lblXemNay.setHorizontalAlignment(SwingConstants.LEFT);
		lblXemNay.setFont(new Font("Arial", Font.BOLD, 16));
		lblXemNay.setBounds(1240, 26, 113, 26);
		panel_2.add(lblXemNay);

		txtNgayHienTai = new JTextField();
		String[] ngayHienTai = layNgayHienTai().split("-");
		String string_NgayHienTai = "Ngày " + ngayHienTai[0] + " tháng " + ngayHienTai[1] + " năm " + ngayHienTai[2];
		txtNgayHienTai.setText(string_NgayHienTai);
		txtNgayHienTai.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNgayHienTai.setEditable(false);
		txtNgayHienTai.setColumns(10);
		txtNgayHienTai.setBackground(Color.WHITE);
		txtNgayHienTai.setBounds(1240, 62, 271, 29);
		panel_2.add(txtNgayHienTai);

		setTitle("Xóa thông tin hóa đơn");
		Toolkit tk = Toolkit.getDefaultToolkit();
		setSize(tk.getScreenSize().width, tk.getScreenSize().height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		initForm();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		Toolkit.getDefaultToolkit().beep();
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

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int row = tableChiTietHoaDon.getSelectedRow();
		fillForm(row);
	}
}
