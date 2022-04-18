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
import javax.swing.SpinnerNumberModel;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import java.awt.FlowLayout;

import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import class_ConnectDB.ConnectDB;
import class_DAO.ChiTietHoaDon_DAO;
import class_DAO.HoaDon_DAO;
import class_DAO.KhachHang_DAO;
import class_DAO.PhieuNhapThuoc_DAO;
import class_DAO.Thuoc_DAO;
import class_Entities.NhanVien;
import class_Entities.Thuoc;
import class_Entities.ChiTietHoaDon;
import class_Entities.HoaDon;
import class_Entities.KhachHang;
import class_Entities.NhaCungCap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;

public class TaoHoaDon_GUI extends JFrame implements WindowListener, ListSelectionListener, ChangeListener {
	private JTextField txtTenThuoc;
	private JTextField txtDonGiaBan;
	private JTextField txtXuatXu;
	private DefaultTableModel modelChiTietHoaDon;
	private JTable tableChiTietHoaDon;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DecimalFormat fmt = new DecimalFormat("###,###");

	private JButton btnHome;

	private Connection con;

	private JTextField txtDonViThuoc;
	private JTextField txtMaHoaDon;
	private JTextField txtTenKhachHang;
	private JTextField txtEmail;
	private JTextField txtSoDienThoai;

	private JTextArea textAreaDiaChi;
	private JComboBox cbMaThuoc;
	private JComboBox cbMaKhachHang;
	private NhanVien emp;
	private JSpinner spinSoLuongBan;

	private boolean use_event_cbMaThuoc = false;
	private boolean use_event_cbMaKhachHang = false;
	private boolean use_event_spinner = false;

	private JLabel lblMaKhachHang;
	private JButton btnGenerateMaKH;
	private JTextField txtNgayHienTai;
	private JTextField txtTenNhaCungCap;
	private JTextField txtTongTienThanhToan;
	private JTextField txtTongTienConLai;

	private JTextField txtNgayLapHoaDon;
	private JTextField txtLoaiThuoc;
	private JTextField txtMienGiam;

	private final float mienGiam = 0.7f;

	private HoaDon hoaDon;
	private Thuoc_DAO thuoc_dao = new Thuoc_DAO();
	private ArrayList<Thuoc> danhSachThuoc = thuoc_dao.layTatCaThuocTonKho();
	private KhachHang_DAO khachhang_dao = new KhachHang_DAO();
	private ArrayList<KhachHang> danhSachKhachHang = khachhang_dao.layTatCaKhachHang();
	private JRadioButton radKhong;
	private JRadioButton radCo;
	private JLabel lblMaThuoc;
	private JButton btnGenerateMaThuoc;
	private JButton btnThem;
	private JButton btnInHoaDon;

	private KhachHang taoKhachHang() {
		String maKH = cbMaKhachHang.getSelectedItem().toString().trim();
		KhachHang kh = new KhachHang(maKH);
		return danhSachKhachHang.get(danhSachKhachHang.indexOf(kh));
	}

	private Thuoc taoThuoc() {
		String maThuoc = cbMaThuoc.getSelectedItem().toString().split("-")[0].trim();
		Thuoc thuoc = new Thuoc(maThuoc);
		return danhSachThuoc.get(danhSachThuoc.indexOf(thuoc));
	}

	private ChiTietHoaDon taoChiTietHoaDon() {
		Thuoc thuoc = taoThuoc();
		int soLuong = Integer.parseInt(spinSoLuongBan.getValue().toString().trim());
		String donGiaBan = txtDonGiaBan.getText().trim();
		return new ChiTietHoaDon(thuoc, soLuong, Float.parseFloat(loaiBoDinhDangTien(donGiaBan)));
	}

	private HoaDon taoHoaDon(NhanVien emp) {
		String maHD = txtMaHoaDon.getText().trim();
		KhachHang kh = taoKhachHang();
		Date ngayLapHoaDon = new Date();
		String theBaoHiem = "Không";
		if (radCo.isSelected()) {
			theBaoHiem = "Có";
		}
		return new HoaDon(maHD, kh, emp, ngayLapHoaDon, theBaoHiem, mienGiam);
	}

	private String phatSinhMaHoaDon() {
		try {
			HoaDon_DAO hoadon_dao = new HoaDon_DAO();
			String maHD_lastest = hoadon_dao.layMaHDCuoiCung().trim();
			String maHD = "HD" + String.valueOf(txtNgayLapHoaDon.getText().split("-")[2]);
			String stt_string_lastest = maHD_lastest.substring(6, maHD_lastest.length());
			int stt_int_lastest = Integer.parseInt(stt_string_lastest);
			String stt_current = String.valueOf(stt_int_lastest + 1);
			for (int i = 0; i < (9 - stt_current.length()); i++) {
				maHD += "0";
			}
			return maHD += stt_current;
		} catch (NullPointerException e) {
			return "HD" + LocalDate.now().getYear() + "000000001";
		}
	}

	private String layNgayHienTai() {
		Date ngayHienTai = new Date();
		return sdf.format(ngayHienTai);
	}

	private ArrayList<String> initCandidateThuoc(ArrayList<Thuoc> ds) {
		ArrayList<String> dataSet = new ArrayList<String>();
		for (Thuoc thuoc : ds) {
			String element = thuoc.getMaThuoc() + " - " + thuoc.getTenThuoc() + " - "
					+ thuoc.getNhaCungCap().getTenNCC();
			dataSet.add(element);
		}
		return dataSet;
	}

	private ArrayList<String> initCandidateKhachHang(ArrayList<KhachHang> ds) {
		ArrayList<String> dataSet = new ArrayList<String>();
		for (KhachHang khachHang : ds) {
			String element = khachHang.getMaKH() + " - " + khachHang.getHoTenKhachHang();
			dataSet.add(element);
		}
		return dataSet;
	}

	private ArrayList<String> searchCandidate(String textHienTai, ArrayList<String> s) {
		ArrayList<String> candidate = new ArrayList<String>();
		for (String element : s) {
			if (element.toLowerCase().trim().contains(textHienTai.toLowerCase().trim())) {
				candidate.add(element);
			}
		}
		return candidate;
	}

	private void generateThuocByMaThuoc(String maThuoc, ArrayList<Thuoc> ds) {
		Thuoc thuoc = new Thuoc(maThuoc);
		if (ds.contains(thuoc)) {
			thuoc = ds.get(ds.indexOf(thuoc));
			NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
			txtTenThuoc.setText(thuoc.getTenThuoc().trim());
			int soLuongTon = thuoc.getSoLuongTon();
			if (soLuongTon > 0) {
				SpinnerNumberModel modelSoLuongBanThucTe = new SpinnerNumberModel();
				modelSoLuongBanThucTe.setValue(1);
				modelSoLuongBanThucTe.setMinimum(1);
				spinSoLuongBan.setModel(modelSoLuongBanThucTe);
				spinSoLuongBan.setEditor(new JSpinner.NumberEditor(spinSoLuongBan));
			} else {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Sản phẩm hiện tại không có hàng", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
				btnThem.setEnabled(false);
			}
			PhieuNhapThuoc_DAO phieunhapthuoc_dao = new PhieuNhapThuoc_DAO();
			float donGiaBan = phieunhapthuoc_dao.layDonGiaMoiNhatTheoMaThuoc(maThuoc);
			txtDonGiaBan.setText(fmt.format(donGiaBan * 1.15));
			txtLoaiThuoc.setText(thuoc.getLoaiThuoc().trim());
			txtDonViThuoc.setText(thuoc.getDonViThuoc().trim());
			txtXuatXu.setText(thuoc.getXuatXu().trim());
			txtTenNhaCungCap.setText(thuoc.getNhaCungCap().getTenNCC());
			btnThem.setEnabled(true);
		}
	}

	private void generateKhachHangByMaKH(String maKH, ArrayList<KhachHang> ds) {
		KhachHang khachHang = new KhachHang(maKH);
		if (ds.contains(khachHang)) {
			khachHang = ds.get(ds.indexOf(khachHang));
			txtTenKhachHang.setText(khachHang.getHoTenKhachHang().trim());
			txtEmail.setText(khachHang.getEmail().trim());
			radKhong.setSelected(true);
			txtSoDienThoai.setText(khachHang.getSoDienThoai().trim());
			textAreaDiaChi.setText(khachHang.getDiaChi().trim());
		}
	}

	private void xoaHetDuLieuTrenTableModel() {
		modelChiTietHoaDon.getDataVector().removeAllElements();
	}

	private void capNhatDuLieuTrenBang() {
		xoaHetDuLieuTrenTableModel();
		int soThuTu = 1;
		for (ChiTietHoaDon cthd : hoaDon.getDanhSachChiTietHoaDon()) {
			Thuoc thuoc = cthd.getThuoc();
			float thanhTien = cthd.getDonGiaBan() * cthd.getSoLuong();
			modelChiTietHoaDon.addRow(new Object[] { soThuTu++, thuoc.getMaThuoc(), thuoc.getTenThuoc(),
					thuoc.getLoaiThuoc(), thuoc.getDonViThuoc(), thuoc.getXuatXu(), thuoc.getNhaCungCap().getTenNCC(),
					fmt.format(cthd.getDonGiaBan()), cthd.getSoLuong(), fmt.format(thanhTien) });
		}
		tableChiTietHoaDon.setModel(modelChiTietHoaDon);
	}

	private float tinhMienGiam() {
		if (radKhong.isSelected()) {
			return 0;
		} else {
			return hoaDon.tinhTongThanhTien() * mienGiam;
		}
	}

	private float tinhTongTienConLai() {
		return hoaDon.tinhTongThanhTien() - tinhMienGiam();
	}

	private void fillForm(int row) {
		if (row != -1) {
			String maThuoc = String.valueOf(tableChiTietHoaDon.getValueAt(row, 1));
			Thuoc thuoc = new Thuoc(maThuoc);
			ArrayList<Thuoc> dsThuoc = danhSachThuoc;
			if (dsThuoc.contains(thuoc)) {
				thuoc = dsThuoc.get(danhSachThuoc.indexOf(thuoc));
				cbMaThuoc.removeAllItems();
				cbMaThuoc.addItem(thuoc.getMaThuoc());
				cbMaThuoc.setSelectedItem(thuoc.getMaThuoc());
				cbMaThuoc.setEditable(false);
				txtTenThuoc.setText(thuoc.getTenThuoc());
				txtLoaiThuoc.setText(thuoc.getLoaiThuoc());
				txtDonViThuoc.setText(thuoc.getDonViThuoc());
				txtXuatXu.setText(thuoc.getXuatXu());
				txtTenNhaCungCap.setText(thuoc.getNhaCungCap().getTenNCC());
				int soLuongBan = (int) tableChiTietHoaDon.getValueAt(row, 8);
				spinSoLuongBan.setValue(soLuongBan);
				String donGiaBan = (String) tableChiTietHoaDon.getValueAt(row, 7);
				txtDonGiaBan.setText(donGiaBan);
			}
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

	private void xoaTrang() {
		use_event_cbMaThuoc = false;
		use_event_cbMaKhachHang = false;
		use_event_spinner = false;
		cbMaThuoc.setEditable(true);
		cbMaThuoc.setSelectedItem("");
		lblMaThuoc.setText("Tìm mã thuốc:");
		btnGenerateMaThuoc.setEnabled(true);
		txtTenThuoc.setText("");
		SpinnerNumberModel modelSoLuongBan = new SpinnerNumberModel(1, 1, 1, 0);
		spinSoLuongBan.setModel(modelSoLuongBan);
		spinSoLuongBan.setEditor(new JSpinner.DefaultEditor(spinSoLuongBan));
		txtDonGiaBan.setText("");
		txtLoaiThuoc.setText("");
		txtDonViThuoc.setText("");
		txtXuatXu.setText("");
		txtTenNhaCungCap.setText("");
		tableChiTietHoaDon.clearSelection();
		btnThem.setEnabled(true);
		cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnGenerateMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		cbMaKhachHang.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnGenerateMaKH.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		cbMaThuoc.requestFocus();
	}

	private void xuatHoaDon() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = ConnectDB.getConnection();
			JasperDesign jdesign = JRXmlLoader.load("report//HoaDonBanThuoc.jrxml");
			String maHD = txtMaHoaDon.getText().trim();
			String query = "SELECT DISTINCT HD.MaHD, HD.MaKH, HoTenKhachHang, HD.MaNV, HoTenNhanVien, NgayLapHoaDon, TheBaoHiem, TH.MaThuoc, TenThuoc, LoaiThuoc, DonViThuoc, XuatXu, TenNCC, Soluong, DonGiaBan, SoLuong * DonGiaBan AS ThanhTien, MienGiam\r\n"
					+ "FROM ChiTietHoaDon CT JOIN HoaDon HD ON CT.MaHD = HD.MaHD \r\n"
					+ "                      JOIN Thuoc TH ON CT.MaThuoc = TH.MaThuoc \r\n"
					+ "					  JOIN NhaCungCap NCC ON TH.MaNCC = NCC.MaNCC\r\n"
					+ "					  JOIN KhachHang KH ON KH.MaKH = HD.MaKH\r\n"
					+ "					  JOIN NhanVien NV ON NV.MaNV = HD.MaNV\r\n" + "WHERE HD.MaHD = '" + maHD + "'";
			JRDesignQuery updateQuery = new JRDesignQuery();
			updateQuery.setText(query);
			jdesign.setQuery(updateQuery);

			JasperReport jreport = JasperCompileManager.compileReport(jdesign);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, null, con);

			JasperViewer.viewReport(jprint, false);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean validDataAdd() {
		try {
			String maThuoc = cbMaThuoc.getSelectedItem().toString().trim();
			if (maThuoc.equalsIgnoreCase("")) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(cbMaThuoc, "Bạn phải nhập mã thuốc", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				cbMaThuoc.requestFocus();
				return false;
			} else {
				cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		} catch (NullPointerException e) {
			cbMaThuoc.setSelectedItem("");
			return false;
		}

		if (btnGenerateMaThuoc.isEnabled()) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(btnGenerateMaThuoc, "Bạn chưa xác nhận mã thuốc", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			btnGenerateMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
			btnGenerateMaThuoc.requestFocus();
			return false;
		} else {
			btnGenerateMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		return true;
	}

	private boolean validDataSave() {
		try {
			String maKH = cbMaKhachHang.getSelectedItem().toString().trim();
			if (maKH.equalsIgnoreCase("")) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(cbMaKhachHang, "Bạn phải nhập mã khách hàng", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				cbMaKhachHang.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				cbMaKhachHang.requestFocus();
				return false;
			} else {
				cbMaKhachHang.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		} catch (NullPointerException e) {
			cbMaKhachHang.setSelectedItem("");
			return false;
		}

		if (btnGenerateMaKH.isEnabled()) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(btnGenerateMaKH, "Bạn chưa xác nhận mã khách hàng", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			btnGenerateMaKH.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
			btnGenerateMaKH.requestFocus();
			return false;
		} else {
			btnGenerateMaKH.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		int count_row = tableChiTietHoaDon.getRowCount();
		if (count_row < 1) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Hóa đơn chưa có sản phẩm nào", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Create the frame.
	 */
	public TaoHoaDon_GUI(NhanVien e) {
		emp = e;
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\icons8-invoice-24.png"));
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

		JButton btnXoaTrang = new JButton("Xóa trắng");
		btnXoaTrang.setBackground(Color.WHITE);
		btnXoaTrang.setIcon(new ImageIcon("data\\icons\\clear.png"));
		btnXoaTrang.setFocusable(false);
		panel.add(btnXoaTrang);
		btnXoaTrang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xoaTrang();
			}
		});

		btnThem = new JButton("Thêm");
		btnThem.setEnabled(false);
		btnThem.setBackground(Color.WHITE);
		btnThem.setIcon(new ImageIcon("data\\icons\\add.png"));
		btnThem.setFocusable(false);
		panel.add(btnThem);
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					spinSoLuongBan.commitEdit();
					if (use_event_spinner) {
						String maThuoc = cbMaThuoc.getSelectedItem().toString().trim();
						Thuoc_DAO thuoc_dao = new Thuoc_DAO();
						int soLuongTon = thuoc_dao.laySoLuongTonCuaThuoc(maThuoc);
						Toolkit.getDefaultToolkit().beep();
						int confirm = JOptionPane.showConfirmDialog(null, "Bạn có muốn mua toàn bộ " + soLuongTon + " sản phẩm " + txtTenThuoc.getText() + " hay không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
						if (confirm == JOptionPane.YES_OPTION) {
							if (validDataAdd()) {
								ChiTietHoaDon cthd = taoChiTietHoaDon();
								if (!hoaDon.themMotChiTietHoaDon(cthd)) {
									Toolkit.getDefaultToolkit().beep();
									JOptionPane.showMessageDialog(null, "Bị trùng mã thuốc", "Cảnh báo",
											JOptionPane.WARNING_MESSAGE);
								} else {
									capNhatDuLieuTrenBang();
									txtTongTienThanhToan.setText(fmt.format(hoaDon.tinhTongThanhTien()));
									txtMienGiam.setText(fmt.format(tinhMienGiam()));
								}
								btnThem.setEnabled(false);
							}
						}
					} else {
						if (validDataAdd()) {
							ChiTietHoaDon cthd = taoChiTietHoaDon();
							if (!hoaDon.themMotChiTietHoaDon(cthd)) {
								Toolkit.getDefaultToolkit().beep();
								JOptionPane.showMessageDialog(null, "Bị trùng mã thuốc", "Cảnh báo",
										JOptionPane.WARNING_MESSAGE);
							} else {
								capNhatDuLieuTrenBang();
								txtTongTienThanhToan.setText(fmt.format(hoaDon.tinhTongThanhTien()));
								txtMienGiam.setText(fmt.format(tinhMienGiam()));
							}
						}
						btnThem.setEnabled(false);
					}
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JButton btnXoa = new JButton("Xóa");
		btnXoa.setBackground(Color.WHITE);
		btnXoa.setIcon(new ImageIcon("data\\icons\\delete.png"));
		btnXoa.setFocusable(false);
		panel.add(btnXoa);
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableChiTietHoaDon.getSelectedRow();
				if (row != -1) {
					Toolkit.getDefaultToolkit().beep();
					int confirm = JOptionPane.showConfirmDialog(null,
							"Bạn có chắc chắn muốn xóa dòng sản phẩm này hay không?", "Chú ý",
							JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						if (hoaDon.xoaMotChiTietHoaDon(row)) {
							tableChiTietHoaDon.clearSelection();
							capNhatDuLieuTrenBang();
							xoaTrang();
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "Đã xóa mục thành công", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn cần chọn mục cần xóa", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JButton btnCapNhat = new JButton("Cập nhật");
		btnCapNhat.setBackground(Color.WHITE);
		btnCapNhat.setIcon(new ImageIcon("data\\icons\\edit.png"));
		btnCapNhat.setFocusable(false);
		btnCapNhat.setToolTipText("Cập nhật số lượng bán");
		panel.add(btnCapNhat);
		btnCapNhat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableChiTietHoaDon.getSelectedRow();
				if (row != -1) {
					Toolkit.getDefaultToolkit().beep();
					int confirm = JOptionPane.showConfirmDialog(null,
							"Bạn có chắc chắn muốn cập nhật số lượng bán không?", "Chú ý", JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						String maThuoc = cbMaThuoc.getSelectedItem().toString().split("-")[0].trim();
						int soLuongBanMoi = Integer.parseInt(spinSoLuongBan.getValue().toString().trim());
						if (hoaDon.capNhatSoLuongBan(maThuoc, soLuongBanMoi)) {
							tableChiTietHoaDon.clearSelection();
							capNhatDuLieuTrenBang();
							xoaTrang();
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "Không thành công");
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn cần chọn mục cần sửa số lượng bán", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JButton btnLuu = new JButton("Lưu");
		btnLuu.setBackground(Color.WHITE);
		btnLuu.setIcon(new ImageIcon("data\\icons\\save-16.png"));
		btnLuu.setFocusable(false);
		panel.add(btnLuu);
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean themHoaDon = false;
				int count_row = 0;

				txtTongTienThanhToan.setText(fmt.format(hoaDon.tinhTongThanhTien()));
				txtMienGiam.setText(fmt.format(tinhMienGiam()));
				txtTongTienConLai.setText(fmt.format(tinhTongTienConLai()));

				if (validDataSave()) {
					HoaDon_DAO hoadon_dao = new HoaDon_DAO();
					if (hoadon_dao.themHoaDon(taoHoaDon(emp), taoKhachHang())) {
						themHoaDon = true;
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Lỗi khi thêm thông tin hóa đơn vào CSDL",
								"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
					}

					ChiTietHoaDon_DAO cthd_dao = new ChiTietHoaDon_DAO();
					for (ChiTietHoaDon cthd : hoaDon.getDanhSachChiTietHoaDon()) {
						Thuoc thuoc = danhSachThuoc.get(danhSachThuoc.indexOf(cthd.getThuoc()));
						int soLuongTon = thuoc.getSoLuongTon() - cthd.getSoLuong();
						if (cthd_dao.themChiTietHoaDon(taoHoaDon(emp), cthd)) {
							if (thuoc_dao.capNhatSoLuongTonCuaThuoc(soLuongTon, cthd.getThuoc().getMaThuoc().trim())) {
								count_row += 1;
							} else {
								Toolkit.getDefaultToolkit().beep();
								JOptionPane.showMessageDialog(null, "Lỗi cập nhật số lượng tồn của thuốc",
										"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "Lỗi khi thêm danh sách sản phẩm vào CSDL",
									"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
						}
					}

					if (themHoaDon && count_row == hoaDon.getDanhSachChiTietHoaDon().size()) {
						Toolkit.getDefaultToolkit().beep();
						if (JOptionPane.showConfirmDialog(null,
								"Lưu thông tin hóa đơn thành công. Bạn có muốn in hóa đơn hay không?",
								"Xác nhận in hóa đơn", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							xuatHoaDon();
						}
						btnInHoaDon.setEnabled(true);
						btnLuu.setEnabled(false);
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Lưu hóa đơn không thành công", "Lỗi thêm hóa đơn vào CSDL",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		btnInHoaDon = new JButton("In hóa đơn");
		btnInHoaDon.setBackground(Color.WHITE);
		btnInHoaDon.setEnabled(false);
		btnInHoaDon.setIcon(new ImageIcon("data\\icons\\print.png"));
		btnInHoaDon.setFocusable(false);
		panel.add(btnInHoaDon);
		btnInHoaDon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				xuatHoaDon();
			}
		});

		JButton btnBatDauHoaDonMoi = new JButton("Bắt đầu hóa đơn mới");
		btnBatDauHoaDonMoi.setBackground(Color.WHITE);
		btnBatDauHoaDonMoi.setIcon(new ImageIcon("data\\icons\\new-icon-16.png"));
		btnBatDauHoaDonMoi.setFocusable(false);
		panel.add(btnBatDauHoaDonMoi);
		btnBatDauHoaDonMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!btnInHoaDon.isEnabled()) {
					Toolkit.getDefaultToolkit().beep();
					int confirm = JOptionPane.showConfirmDialog(null,
							"Bạn chưa hoàn thành hóa đơn này. Bạn có muốn bắt đầu hóa đơn mới hay không?", "Cảnh báo",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (confirm == JOptionPane.YES_OPTION) {
						TaoHoaDon_GUI taoHoaDon = new TaoHoaDon_GUI(emp);
						taoHoaDon.setVisible(true);
						setVisible(false);
					}
				} else {
					TaoHoaDon_GUI taoHoaDon = new TaoHoaDon_GUI(emp);
					taoHoaDon.setVisible(true);
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

		JButton btnSetting = new JButton("Help");
		btnSetting.setIcon(new ImageIcon("data\\icons\\setting.png"));
		btnSetting.setFocusable(false);
		panel_1.add(btnSetting);

		JButton btnDangXuat = new JButton("\u0110\u0103ng Xu\u1EA5t");
		btnDangXuat.setIcon(new ImageIcon("data\\icons\\logout.png"));
		btnDangXuat.setFocusable(false);
		panel_1.add(btnDangXuat);

		btnHome = new JButton("Trở về trang chủ");
		btnHome.setIcon(new ImageIcon("data\\icons\\home.png"));
		btnHome.setMnemonic(KeyEvent.VK_T);
		btnHome.setFocusable(false);
		panel_1.add(btnHome);
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
		lblTitle.setIcon(new ImageIcon("data\\icons\\purchase-title.png"));
		lblTitle.setBounds(451, 6, 596, 93);
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

		lblMaThuoc = new JLabel("Tìm mã thuốc:");
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

		txtNgayLapHoaDon = new JTextField(sdf.format(new Date()));
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

		txtMaHoaDon = new JTextField(phatSinhMaHoaDon());
		txtMaHoaDon.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaHoaDon.setEditable(false);
		txtMaHoaDon.setColumns(10);
		txtMaHoaDon.setBackground(Color.WHITE);
		txtMaHoaDon.setBounds(169, 67, 308, 29);
		panel_3.add(txtMaHoaDon);

		SpinnerNumberModel modelSoLuongBan = new SpinnerNumberModel(1, 1, 1, 0);
		spinSoLuongBan = new JSpinner(modelSoLuongBan);
		spinSoLuongBan.setFont(new Font("Arial", Font.PLAIN, 16));
		spinSoLuongBan.setBounds(168, 156, 308, 29);
		spinSoLuongBan.setEditor(new JSpinner.DefaultEditor(spinSoLuongBan));
		panel_3.add(spinSoLuongBan);

		cbMaThuoc = new JComboBox();
		cbMaThuoc.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaThuoc.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaThuoc.setEditable(true);
		cbMaThuoc.setBounds(168, 110, 267, 29);
		cbMaThuoc.setSelectedItem("");
		panel_3.add(cbMaThuoc);
		cbMaThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				use_event_cbMaThuoc = true;
				ArrayList<String> cacPhanTuHienCo = initCandidateThuoc(danhSachThuoc);
				int soPhanTuHienCo = cacPhanTuHienCo.size();
				String tuHienTai = cbMaThuoc.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						cbMaThuoc.removeAllItems();
						for (int i = 0; i < soPhanTuHienCo; i++) {
							cbMaThuoc.addItem(cacPhanTuHienCo.get(i));
						}
						cbMaThuoc.setSelectedItem("");
						cbMaThuoc.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbMaThuoc.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbMaThuoc.addItem(cacUngVien.get(i));
							}
							cbMaThuoc.setSelectedItem(tuHienTai);
							cbMaThuoc.showPopup();
						} else if (soUngVien == 0) {
							cbMaThuoc.removeAllItems();
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbMaThuoc, "Không tìm thấy mã thuốc nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaThuoc, "Không tìm thấy mã thuốc nào trong CSDL", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

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

		btnGenerateMaThuoc = new JButton("");
		btnGenerateMaThuoc.setToolTipText("Tìm thuốc");
		btnGenerateMaThuoc.setBackground(Color.WHITE);
		btnGenerateMaThuoc.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnGenerateMaThuoc.setBorder(BorderFactory.createEmptyBorder());
		btnGenerateMaThuoc.setFocusable(false);
		btnGenerateMaThuoc.setBounds(445, 110, 31, 31);
		panel_3.add(btnGenerateMaThuoc);
		btnGenerateMaThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (use_event_cbMaThuoc) {
					String tuHienTai = cbMaThuoc.getSelectedItem().toString().trim();
					int row_count = cbMaThuoc.getItemCount();
					if (!tuHienTai.equalsIgnoreCase("")) {
						String[] tachTuHienTai = tuHienTai.split("-");
						String maThuoc = tachTuHienTai[0].trim();
						if (row_count > 0) {
							if (row_count == 1) {
								generateThuocByMaThuoc(maThuoc, danhSachThuoc);
							}
							cbMaThuoc.setSelectedItem(maThuoc);
							cbMaThuoc.setEditable(false);
							lblMaThuoc.setText("Mã thuốc:");
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbMaThuoc, "Không tìm thấy mã thuốc nào", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(cbMaThuoc, "Bạn phải nhập mã thuốc", "Cảnh báo",
								JOptionPane.WARNING_MESSAGE);
					}
					btnGenerateMaThuoc.setEnabled(false);
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaThuoc,
							"Vui lòng nhấn Enter trong khung nhập liệu \"Tìm mã thuốc\" trước khi nhấn nút này",
							"Cảnh báo", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

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

		lblMaKhachHang = new JLabel("Tìm mã khách hàng:");
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

		cbMaKhachHang = new JComboBox();
		cbMaKhachHang.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaKhachHang.setEditable(true);
		cbMaKhachHang.setBackground(Color.WHITE);
		cbMaKhachHang.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaKhachHang.setBounds(191, 60, 210, 29);
		cbMaKhachHang.setSelectedItem("");
		panel_4.add(cbMaKhachHang);
		cbMaKhachHang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				use_event_cbMaKhachHang = true;
				ArrayList<String> cacPhanTuHienCo = initCandidateKhachHang(danhSachKhachHang);
				int soPhanTuHienCo = cacPhanTuHienCo.size();
				String tuHienTai = cbMaKhachHang.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						cbMaKhachHang.removeAllItems();
						for (int i = 0; i < soPhanTuHienCo; i++) {
							cbMaKhachHang.addItem(cacPhanTuHienCo.get(i));
						}
						cbMaKhachHang.setSelectedItem("");
						cbMaKhachHang.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbMaKhachHang.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbMaKhachHang.addItem(cacUngVien.get(i));
							}
							cbMaKhachHang.setSelectedItem(tuHienTai);
							cbMaKhachHang.showPopup();
						} else if (soUngVien == 0) {
							cbMaKhachHang.removeAllItems();
							Toolkit.getDefaultToolkit().beep();
							int confirm = JOptionPane.showConfirmDialog(cbMaKhachHang,
									"Không tìm thấy mã khách hàng này trong CSDL. Bạn có muốn thêm khách hàng này vào CSDL hay không?",
									"Thông báo", JOptionPane.YES_NO_OPTION);
							if (confirm == JOptionPane.YES_NO_OPTION) {
								ThemKhacHang_GUI themKhacHang = new ThemKhacHang_GUI(emp);
								themKhacHang.setVisible(true);
								setVisible(false);
							} else {
								Toolkit.getDefaultToolkit().beep();
								JOptionPane.showMessageDialog(cbMaKhachHang,
										"Bạn không thể tiếp tục hóa đơn khi chưa thêm khách hàng vào CSDL",
										"Lỗi không thiếu thông tin khách hàng", JOptionPane.ERROR_MESSAGE);
								setVisible(false);
							}
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaKhachHang, "Không tìm thấy mã khách hàng nào trong CSDL",
							"Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		btnGenerateMaKH = new JButton("");
		btnGenerateMaKH.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnGenerateMaKH.setToolTipText("Tìm khách hàng");
		btnGenerateMaKH.setFocusable(false);
		btnGenerateMaKH.setBorder(BorderFactory.createEmptyBorder());
		btnGenerateMaKH.setBackground(Color.WHITE);
		btnGenerateMaKH.setBounds(411, 61, 31, 31);
		panel_4.add(btnGenerateMaKH);
		btnGenerateMaKH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (use_event_cbMaKhachHang) {
					String tuHienTai = cbMaKhachHang.getSelectedItem().toString().trim();
					int row_count = cbMaKhachHang.getItemCount();
					if (!tuHienTai.equalsIgnoreCase("")) {
						String[] tachTuHienTai = tuHienTai.split("-");
						String maKH = tachTuHienTai[0].trim();
						if (row_count > 0) {
							if (row_count == 1) {
								generateKhachHangByMaKH(maKH, danhSachKhachHang);
							}
							cbMaKhachHang.setSelectedItem(maKH);
							cbMaKhachHang.setEditable(false);
							lblMaKhachHang.setText("Mã khách hàng:");
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbMaKhachHang, "Không tìm thấy mã khách hàng nào",
									"Thông báo", JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(cbMaKhachHang, "Bạn phải nhập mã khách hàng", "Cảnh báo",
								JOptionPane.WARNING_MESSAGE);
					}
					btnGenerateMaKH.setEnabled(false);
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaKhachHang,
							"Vui lòng nhấn Enter trong khung nhập liệu \"Tìm mã khách hàng\" trước khi nhấn nút này",
							"Cảnh báo", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

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
		radCo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (use_event_cbMaKhachHang) {
					String tuHienTai = cbMaKhachHang.getSelectedItem().toString().trim();
					int row_count = cbMaKhachHang.getItemCount();
					if (!tuHienTai.equalsIgnoreCase("")) {
						String[] tachTuHienTai = tuHienTai.split("-");
						String maKH = tachTuHienTai[0].trim();
						KhachHang khachHang = new KhachHang(maKH);
						if (danhSachKhachHang.contains(khachHang)) {
							khachHang = danhSachKhachHang.get(danhSachKhachHang.indexOf(khachHang));
							if (khachHang.getMaTheBaoHiem().trim().equalsIgnoreCase("")) {
								radKhong.setSelected(true);
								radCo.setEnabled(false);
								Toolkit.getDefaultToolkit().beep();
								int confirm = JOptionPane.showConfirmDialog(radCo,
										"Khách hàng hiện tại chưa nhập mã thẻ bảo hiểm. Bạn có muốn cập nhật lại thông tin khách hàng này hay không?",
										"Thông báo", JOptionPane.YES_NO_OPTION);
								if (confirm == JOptionPane.YES_OPTION) {
									SuaThongTinKhacHang_GUI suaThongTinKhacHang = new SuaThongTinKhacHang_GUI(khachHang,
											emp, getTitle());
									suaThongTinKhacHang.setVisible(true);
								}
							}
						}
					}
				}
			}
		});

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
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
		scrollPane.setViewportView(tableChiTietHoaDon);

		JLabel lblTongTienThanhToan = new JLabel("Tổng tiền thanh toán:");
		lblTongTienThanhToan.setForeground(Color.RED);
		lblTongTienThanhToan.setHorizontalAlignment(SwingConstants.LEFT);
		lblTongTienThanhToan.setFont(new Font("Arial", Font.BOLD, 18));
		lblTongTienThanhToan.setBounds(10, 609, 196, 26);
		panel_chart.add(lblTongTienThanhToan);

		txtTongTienThanhToan = new JTextField();
		txtTongTienThanhToan.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTongTienThanhToan.setForeground(Color.BLACK);
		txtTongTienThanhToan.setFont(new Font("Arial", Font.BOLD, 18));
		txtTongTienThanhToan.setEditable(false);
		txtTongTienThanhToan.setColumns(10);
		txtTongTienThanhToan.setBackground(Color.WHITE);
		txtTongTienThanhToan.setBounds(203, 609, 243, 29);
		panel_chart.add(txtTongTienThanhToan);

		JLabel lblDonViTien = new JLabel("VND");
		lblDonViTien.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDonViTien.setFont(new Font("Arial", Font.BOLD, 18));
		lblDonViTien.setBounds(443, 609, 49, 26);
		panel_chart.add(lblDonViTien);

		JLabel lblTongTienConLai = new JLabel("Tổng tiền còn lại:");
		lblTongTienConLai.setForeground(Color.BLUE);
		lblTongTienConLai.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTongTienConLai.setFont(new Font("Arial", Font.BOLD, 18));
		lblTongTienConLai.setBounds(872, 609, 166, 26);
		panel_chart.add(lblTongTienConLai);

		txtTongTienConLai = new JTextField();
		txtTongTienConLai.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTongTienConLai.setEditable(false);
		txtTongTienConLai.setForeground(Color.RED);
		txtTongTienConLai.setFont(new Font("Arial", Font.BOLD, 18));
		txtTongTienConLai.setColumns(10);
		txtTongTienConLai.setBackground(Color.WHITE);
		txtTongTienConLai.setBounds(1040, 609, 231, 29);
		panel_chart.add(txtTongTienConLai);
		txtTongTienConLai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String soTienKhachDua = txtTongTienConLai.getText().trim();
				float float_SoTienKhachDua = Float.parseFloat(loaiBoDinhDangTien(soTienKhachDua));
				txtTongTienConLai.setText(fmt.format(float_SoTienKhachDua));
			}
		});

		JLabel lblDonViTien_1 = new JLabel("VND");
		lblDonViTien_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDonViTien_1.setFont(new Font("Arial", Font.BOLD, 18));
		lblDonViTien_1.setBounds(1277, 609, 49, 26);
		panel_chart.add(lblDonViTien_1);

		JButton btnTinhTienThua = new JButton("Tính tiền thừa");
		btnTinhTienThua.setForeground(Color.RED);
		btnTinhTienThua.setBackground(Color.WHITE);
		btnTinhTienThua.setIcon(new ImageIcon("data\\icons\\icons8-calculate-24.png"));
		btnTinhTienThua.setFont(new Font("Arial", Font.PLAIN, 18));
		btnTinhTienThua.setBounds(1336, 606, 176, 32);
		panel_chart.add(btnTinhTienThua);
		btnTinhTienThua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tongTienThanhToan = txtTongTienThanhToan.getText().trim();
				String mienGiam = txtMienGiam.getText().trim();
				String tongTienConLai = txtTongTienConLai.getText().trim();
				TinhTienThuaChoKhachHang_GUI tinhTienThua = new TinhTienThuaChoKhachHang_GUI(tongTienThanhToan,
						mienGiam, tongTienConLai);
				tinhTienThua.setVisible(true);
			}
		});

		JLabel lblMienGiam = new JLabel("Miễn giảm:");
		lblMienGiam.setForeground(new Color(148, 0, 211));
		lblMienGiam.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMienGiam.setFont(new Font("Arial", Font.BOLD, 18));
		lblMienGiam.setBounds(494, 609, 104, 26);
		panel_chart.add(lblMienGiam);

		txtMienGiam = new JTextField();
		txtMienGiam.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMienGiam.setForeground(Color.RED);
		txtMienGiam.setFont(new Font("Arial", Font.BOLD, 18));
		txtMienGiam.setColumns(10);
		txtMienGiam.setBackground(Color.WHITE);
		txtMienGiam.setBounds(608, 609, 209, 29);
		panel_chart.add(txtMienGiam);

		JLabel lblDonViTien_1_1 = new JLabel("VND");
		lblDonViTien_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDonViTien_1_1.setFont(new Font("Arial", Font.BOLD, 18));
		lblDonViTien_1_1.setBounds(824, 609, 49, 26);
		panel_chart.add(lblDonViTien_1_1);

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

		setTitle("Tạo hóa đơn");
		Toolkit tk = Toolkit.getDefaultToolkit();
		setSize(tk.getScreenSize().width, tk.getScreenSize().height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);

		hoaDon = new HoaDon(phatSinhMaHoaDon(), new KhachHang(), emp, new Date(), "", mienGiam);

		spinSoLuongBan.addChangeListener(this);
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

	@Override
	public void stateChanged(ChangeEvent e) {
		if (!btnGenerateMaThuoc.isEnabled()) {
			String maThuoc = cbMaThuoc.getSelectedItem().toString().trim();
			Thuoc_DAO thuoc_dao = new Thuoc_DAO();
			int soLuongTon = thuoc_dao.laySoLuongTonCuaThuoc(maThuoc);
			SpinnerNumberModel model_soluongban = (SpinnerNumberModel) spinSoLuongBan.getModel();
			int soLuongBan = (int) model_soluongban.getValue();
			if (soLuongBan > soLuongTon) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(spinSoLuongBan,
						"Số lượng bán không được lớn hơn số lượng tồn là: " + soLuongTon, "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				spinSoLuongBan.getModel().setValue(soLuongTon);
				spinSoLuongBan.setEditor(new JSpinner.DefaultEditor(spinSoLuongBan));
				use_event_spinner = true;
			}
		}
	}
}
