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
import javax.swing.SpringLayout;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import java.awt.FlowLayout;

import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import class_DAO.HoaDon_DAO;
import class_Entities.NhanVien;
import class_Equipment.DateLabelFormatter;
import class_Entities.HoaDon;
import class_Entities.KhachHang;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.border.EtchedBorder;

public class XemDanhSachHoaDon_GUI extends JFrame implements ActionListener, WindowListener, ListSelectionListener {
	private DefaultTableModel modelHoaDon;
	private JTable tableHoaDon;

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	private JButton btnHome;

	private JComboBox cbMaKhachHang;
	private NhanVien emp;

	private JLabel lblMaKhachHang;
	private JTextField txtNgayHienTai;

	private UtilDateModel model_ngaylaphoadon;
	private Properties p;
	private JDatePanelImpl datePanelNgayLapHoaDon;
	private JDatePickerImpl datePickerNgayLapHoaDon;

	private ArrayList<HoaDon> danhSachTatCaHoaDon;
	private final int soPhanTuMuonHienThi = 21;
	
	private JComboBox cbMaHoaDon;
	private JComboBox cbMaNhanVien;
	private JButton btnTimKiem;
	private JButton btnReset;
	private JLabel lblPageIndex;
	private AbstractButton btnNextPage;
	private JButton btnPreviousPage;
	private JButton btnLastPage;
	private JButton btnFirstPage;

	private String layNgayHienTai() {
		Date ngayHienTai = new Date();
		return sdf.format(ngayHienTai);
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

	private void xoaHetDuLieuTrenTableModel() {
		modelHoaDon.getDataVector().removeAllElements();
	}

	private ArrayList<HoaDon> layDuLieuTrongTrang(ArrayList<HoaDon> danhSachHD, int soTrangMuonLay, int lastPage,
			int soPhanTuMuonHienThi) {
		ArrayList<HoaDon> kq = new ArrayList<HoaDon>();
		if (soTrangMuonLay < lastPage) {
			kq = new ArrayList<HoaDon>(danhSachHD.subList(soTrangMuonLay, (soTrangMuonLay * soPhanTuMuonHienThi) + 1));
		} else if (soTrangMuonLay == lastPage) {
			kq = new ArrayList<HoaDon>(danhSachHD.subList((lastPage - 1) * soPhanTuMuonHienThi, danhSachHD.size()));
		}
		return kq;
	}

	private void capNhatDuLieuTrenBang_Init(ArrayList<HoaDon> danhSachHDMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachHDMuonCapNhat.size();
		if (lengthArray > 0) {
			xoaHetDuLieuTrenTableModel();
			int soThuTu = 1;
			for (HoaDon hoaDon : danhSachHDMuonCapNhat) {
				KhachHang khachHang = hoaDon.getKhachHang();
				NhanVien nhanVien = hoaDon.getNhanVien();
				modelHoaDon.addRow(new Object[] { soThuTu++, hoaDon.getMaHD(), sdf.format(hoaDon.getNgayLapHoaDon()),
						khachHang.getMaKH(), khachHang.getHoTenKhachHang(), nhanVien.getMaNV(),
						nhanVien.getHoTenNhanVien(), hoaDon.getTheBaoHiem() });
			}
			if (lengthArray > 0) {
				double countPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
				lblPageIndex.setText("1" + "/" + String.valueOf((int) countPage));
			} else {
				lblPageIndex.setText("1" + "/" + "1");
			}
			if (lengthArray < soPhanTuMuonHienThi) {
				modelHoaDon.setRowCount(lengthArray);
			} else {
				modelHoaDon.setRowCount(soPhanTuMuonHienThi);
			}
			tableHoaDon.setModel(modelHoaDon);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenBang_Search(ArrayList<HoaDon> danhSachHDMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachHDMuonCapNhat.size();
		if (lengthArray > 0) {
			int soThuTu = 1;
			xoaHetDuLieuTrenTableModel();
			ArrayList<HoaDon> danhSachHoaDon = timKiemHoaDon(danhSachHDMuonCapNhat);
			if (danhSachHoaDon.size() > 0) {
				for (HoaDon hoaDon : danhSachHoaDon) {
					KhachHang khachHang = hoaDon.getKhachHang();
					NhanVien nhanVien = hoaDon.getNhanVien();
					modelHoaDon.addRow(new Object[] { soThuTu++, hoaDon.getMaHD(),
							sdf.format(hoaDon.getNgayLapHoaDon()), khachHang.getMaKH(), khachHang.getHoTenKhachHang(),
							nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(), hoaDon.getTheBaoHiem() });
				}
			} else {
				modelHoaDon.fireTableDataChanged();
			}
			lblPageIndex.setText("1" + "/" + "1");
			modelHoaDon.setRowCount(danhSachHoaDon.size());
			tableHoaDon.setModel(modelHoaDon);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenBang_Next(ArrayList<HoaDon> danhSachHDMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachHDMuonCapNhat.size();
		if (lengthArray > 0) {
			String string_SoTrangHienTai = lblPageIndex.getText().trim().split("/")[0];
			int soTrangHienTai = Integer.parseInt(string_SoTrangHienTai) + 1;
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			if (soTrangHienTai <= lastPage) {
				int soThuTu = ((soTrangHienTai - 1) * soPhanTuMuonHienThi) + 1;
				xoaHetDuLieuTrenTableModel();
				ArrayList<HoaDon> danhSachHoaDon = layDuLieuTrongTrang(danhSachHDMuonCapNhat, soTrangHienTai,
						(int) lastPage, soPhanTuMuonHienThi);
				for (HoaDon hoaDon : danhSachHoaDon) {
					KhachHang khachHang = hoaDon.getKhachHang();
					NhanVien nhanVien = hoaDon.getNhanVien();
					modelHoaDon.addRow(new Object[] { soThuTu++, hoaDon.getMaHD(),
							sdf.format(hoaDon.getNgayLapHoaDon()), khachHang.getMaKH(), khachHang.getHoTenKhachHang(),
							nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(), hoaDon.getTheBaoHiem() });
				}
				lblPageIndex.setText(String.valueOf(soTrangHienTai) + "/" + String.valueOf((int) lastPage));
				modelHoaDon.setRowCount(danhSachHoaDon.size());
				tableHoaDon.setModel(modelHoaDon);
				capNhatTatCaComboBox();
			}
		}
	}

	private void capNhatDuLieuTrenBang_Previous(ArrayList<HoaDon> danhSachHDMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachHDMuonCapNhat.size();
		if (lengthArray > 0) {
			String soThuTuDauTien = String.valueOf(tableHoaDon.getValueAt(0, 0));
			String string_SoTrangHienTai = lblPageIndex.getText().trim().split("/")[0];
			int soTrangHienTai = Integer.parseInt(string_SoTrangHienTai) - 1;
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			if (soTrangHienTai > 0) {
				int soThuTu = Integer.parseInt(soThuTuDauTien) - soPhanTuMuonHienThi;
				xoaHetDuLieuTrenTableModel();
				ArrayList<HoaDon> danhSachHoaDon = layDuLieuTrongTrang(danhSachHDMuonCapNhat, soTrangHienTai,
						(int) lastPage, soPhanTuMuonHienThi);
				for (HoaDon hoaDon : danhSachHoaDon) {
					KhachHang khachHang = hoaDon.getKhachHang();
					NhanVien nhanVien = hoaDon.getNhanVien();
					modelHoaDon.addRow(new Object[] { soThuTu++, hoaDon.getMaHD(),
							sdf.format(hoaDon.getNgayLapHoaDon()), khachHang.getMaKH(), khachHang.getHoTenKhachHang(),
							nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(), hoaDon.getTheBaoHiem() });
				}
				lblPageIndex.setText(String.valueOf(soTrangHienTai) + "/" + String.valueOf((int) lastPage));
				modelHoaDon.setRowCount(danhSachHoaDon.size());
				tableHoaDon.setModel(modelHoaDon);
				capNhatTatCaComboBox();
			}
		}
	}

	private void capNhatDuLieuTrenBang_Last(ArrayList<HoaDon> danhSachHDMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachHDMuonCapNhat.size();
		if (lengthArray > 0) {
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			int last_index = (int) (soPhanTuMuonHienThi * (lastPage - 1));
			int soThuTu = last_index + 1;
			xoaHetDuLieuTrenTableModel();
			ArrayList<HoaDon> danhSachHoaDon = new ArrayList<HoaDon>(
					danhSachHDMuonCapNhat.subList(last_index, lengthArray));
			for (HoaDon hoaDon : danhSachHoaDon) {
				KhachHang khachHang = hoaDon.getKhachHang();
				NhanVien nhanVien = hoaDon.getNhanVien();
				modelHoaDon.addRow(new Object[] { soThuTu++, hoaDon.getMaHD(), sdf.format(hoaDon.getNgayLapHoaDon()),
						khachHang.getMaKH(), khachHang.getHoTenKhachHang(), nhanVien.getMaNV(),
						nhanVien.getHoTenNhanVien(), hoaDon.getTheBaoHiem() });
			}
			lblPageIndex.setText(String.valueOf((int) lastPage) + "/" + String.valueOf((int) lastPage));
			modelHoaDon.setRowCount(danhSachHoaDon.size());
			tableHoaDon.setModel(modelHoaDon);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenComboBoxMaHD() {
		cbMaHoaDon.removeAllItems();
		danhSachTatCaHoaDon.forEach(hd -> cbMaHoaDon.addItem(hd.getMaHD()));
		cbMaHoaDon.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxMaHD() {
		ArrayList<String> danhSachMaHD = new ArrayList<String>();
		danhSachTatCaHoaDon.forEach(hd -> danhSachMaHD.add(hd.getMaHD()));
		return danhSachMaHD;
	}

	private void capNhatDuLieuTrenComboBoxMaKH() {
		cbMaKhachHang.removeAllItems();
		ArrayList<String> danhSachMaKH = new ArrayList<String>();
		for (HoaDon hoaDon : danhSachTatCaHoaDon) {
			String maKH = hoaDon.getKhachHang().getMaKH();
			if (!danhSachMaKH.contains(maKH)) {
				danhSachMaKH.add(maKH);
				cbMaKhachHang.addItem(maKH);
			}
		}
		cbMaKhachHang.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxMaKH() {
		ArrayList<String> danhSachMaKH = new ArrayList<String>();
		for (HoaDon hoaDon : danhSachTatCaHoaDon) {
			String maKH = hoaDon.getKhachHang().getMaKH();
			if (!danhSachMaKH.contains(maKH)) {
				danhSachMaKH.add(maKH);
			}
		}
		return danhSachMaKH;
	}

	private void capNhatDuLieuTrenComboBoxMaNV() {
		cbMaNhanVien.removeAllItems();
		ArrayList<String> danhSachMaNV = new ArrayList<String>();
		for (HoaDon hoaDon : danhSachTatCaHoaDon) {
			String maNV = hoaDon.getNhanVien().getMaNV();
			if (!danhSachMaNV.contains(maNV)) {
				danhSachMaNV.add(maNV);
				cbMaNhanVien.addItem(maNV);
			}
		}
		cbMaNhanVien.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxMaNV() {
		ArrayList<String> danhSachMaNV = new ArrayList<String>();
		for (HoaDon hoaDon : danhSachTatCaHoaDon) {
			String maNV = hoaDon.getNhanVien().getMaNV();
			if (!danhSachMaNV.contains(maNV)) {
				danhSachMaNV.add(maNV);
			}
		}
		return danhSachMaNV;
	}

	private void capNhatTatCaComboBox() {
		capNhatDuLieuTrenComboBoxMaHD();
		capNhatDuLieuTrenComboBoxMaKH();
		capNhatDuLieuTrenComboBoxMaNV();
	}

	private ArrayList<HoaDon> timKiemHoaDon(ArrayList<HoaDon> danhSachCanTimKiem) {
		tableHoaDon.clearSelection();
		ArrayList<HoaDon> danhSachTimThay = new ArrayList<HoaDon>();
		try {
			String maHDNhap = cbMaHoaDon.getSelectedItem().toString().trim();
			if (!maHDNhap.equalsIgnoreCase("")) {
				for (HoaDon hoaDon : danhSachCanTimKiem) {
					String maHD = hoaDon.getMaHD();
					if (maHD.toLowerCase().contains(maHDNhap.toLowerCase())) {
						danhSachTimThay.add(hoaDon);
					}
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		String string_NgayLapHoaDonDangChon = datePickerNgayLapHoaDon.getJFormattedTextField().getText();
		if (!string_NgayLapHoaDonDangChon.equalsIgnoreCase("")) {
			LocalDate ngayLapHoaDonDangChon = LocalDate.parse(string_NgayLapHoaDonDangChon, dtf);
			for (HoaDon hoaDon : danhSachCanTimKiem) {
				String string_NgayLapHoaDon = sdf.format(hoaDon.getNgayLapHoaDon());
				LocalDate ngayLapHoaDon = LocalDate.parse(string_NgayLapHoaDon, dtf);
				if (ngayLapHoaDon.equals(ngayLapHoaDonDangChon)) {
					danhSachTimThay.add(hoaDon);
				}
			}
		}

		try {
			String maKHNhap = cbMaKhachHang.getSelectedItem().toString().trim();
			if (!maKHNhap.equalsIgnoreCase("")) {
				for (HoaDon hoaDon : danhSachCanTimKiem) {
					String maKH = hoaDon.getKhachHang().getMaKH();
					if (maKH.toLowerCase().contains(maKHNhap.toLowerCase())) {
						danhSachTimThay.add(hoaDon);
					}
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		try {
			String maNVNhap = cbMaNhanVien.getSelectedItem().toString().trim();
			if (!maNVNhap.equalsIgnoreCase("")) {
				for (HoaDon hoaDon : danhSachCanTimKiem) {
					String maNV = hoaDon.getNhanVien().getMaNV();
					if (maNV.toLowerCase().contains(maNVNhap.toLowerCase())) {
						danhSachTimThay.add(hoaDon);
					}
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return danhSachTimThay;
	}

	private void xoaTrang() {
		cbMaHoaDon.setSelectedItem("");
		datePickerNgayLapHoaDon.getJFormattedTextField().setText("");
		cbMaKhachHang.setSelectedItem("");
		cbMaNhanVien.setSelectedItem("");
		cbMaHoaDon.requestFocus();
	}

	private void fillForm(int row) {
		if (row != -1) {
			String maHoaDon = String.valueOf(tableHoaDon.getValueAt(row, 1));
			HoaDon hoaDon = new HoaDon(maHoaDon);
			ArrayList<HoaDon> dsHoaDon = danhSachTatCaHoaDon;
			if (dsHoaDon.contains(hoaDon)) {
				hoaDon = dsHoaDon.get(danhSachTatCaHoaDon.indexOf(hoaDon));
				cbMaHoaDon.removeAllItems();
				cbMaHoaDon.addItem(maHoaDon);
				cbMaHoaDon.setSelectedItem(maHoaDon);

				Date ngayLapHoaDon = hoaDon.getNgayLapHoaDon();
				model_ngaylaphoadon.setValue(ngayLapHoaDon);
				datePickerNgayLapHoaDon.getJFormattedTextField().setText(sdf.format(ngayLapHoaDon));

				String maKhachHang = hoaDon.getKhachHang().getMaKH();
				cbMaKhachHang.removeAllItems();
				cbMaKhachHang.addItem(maKhachHang);
				cbMaKhachHang.setSelectedItem(maKhachHang);

				String maNhanVien = hoaDon.getNhanVien().getMaNV();
				cbMaNhanVien.removeAllItems();
				cbMaNhanVien.addItem(maNhanVien);
				cbMaNhanVien.setSelectedItem(maNhanVien);
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public XemDanhSachHoaDon_GUI(NhanVien e) {
		emp = e;
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\icons8-summary-list-24.png"));
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

		btnReset = new JButton("Trở về mặc định");
		btnReset.setBackground(Color.WHITE);
		btnReset.setIcon(new ImageIcon("data\\icons\\reset-16.png"));
		btnReset.setFocusable(false);
		btnReset.setToolTipText("Đặt lại dữ liệu ban đầu");
		panel.add(btnReset);

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
		lblTitle.setIcon(new ImageIcon("data\\icons\\list-purchase-title.png"));
		lblTitle.setBounds(432, 10, 727, 93);
		panel_2.add(lblTitle);

		JPanel panel_chart = new JPanel();
		panel_chart.setBackground(Color.WHITE);
		panel_chart.setBounds(0, 109, 1522, 651);
		panel_chart.setLayout(null);
		panel_2.add(panel_chart);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(10, 10, 1502, 101);
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_chart.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblThongTinHoaDon = new JLabel("Thông tin hóa đơn");
		lblThongTinHoaDon.setForeground(SystemColor.textHighlight);
		lblThongTinHoaDon.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinHoaDon.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinHoaDon.setBounds(20, 10, 1472, 41);
		panel_3.add(lblThongTinHoaDon);

		p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		model_ngaylaphoadon = new UtilDateModel();
		datePanelNgayLapHoaDon = new JDatePanelImpl(model_ngaylaphoadon, p);
		datePickerNgayLapHoaDon = new JDatePickerImpl(datePanelNgayLapHoaDon, new DateLabelFormatter());
		SpringLayout springLayout_2 = (SpringLayout) datePickerNgayLapHoaDon.getLayout();
		springLayout_2.putConstraint(SpringLayout.SOUTH, datePickerNgayLapHoaDon.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, datePickerNgayLapHoaDon);
		datePickerNgayLapHoaDon.setBounds(450, 61, 212, 30);
		datePickerNgayLapHoaDon.setFocusable(false);
		panel_3.add(datePickerNgayLapHoaDon);

		JLabel lblMaHoaDon = new JLabel("Mã hóa đơn:");
		lblMaHoaDon.setHorizontalAlignment(SwingConstants.LEFT);
		lblMaHoaDon.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaHoaDon.setBounds(10, 61, 96, 26);
		panel_3.add(lblMaHoaDon);

		lblMaKhachHang = new JLabel("Mã khách hàng:");
		lblMaKhachHang.setBounds(672, 61, 126, 26);
		lblMaKhachHang.setHorizontalAlignment(SwingConstants.LEFT);
		lblMaKhachHang.setFont(new Font("Arial", Font.BOLD, 16));
		panel_3.add(lblMaKhachHang);

		cbMaKhachHang = new JComboBox();
		cbMaKhachHang.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaKhachHang.setEditable(true);
		cbMaKhachHang.setBackground(Color.WHITE);
		cbMaKhachHang.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaKhachHang.setSelectedItem("");
		cbMaKhachHang.setBounds(801, 60, 250, 29);
		panel_3.add(cbMaKhachHang);

		cbMaHoaDon = new JComboBox();
		cbMaHoaDon.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaHoaDon.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaHoaDon.setEditable(true);
		cbMaHoaDon.setBackground(Color.WHITE);
		cbMaHoaDon.setBounds(110, 61, 259, 29);
		panel_3.add(cbMaHoaDon);

		JLabel lblNgayLap = new JLabel("Ngày lập:");
		lblNgayLap.setHorizontalAlignment(SwingConstants.LEFT);
		lblNgayLap.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgayLap.setBounds(373, 61, 77, 26);
		panel_3.add(lblNgayLap);

		JLabel lblMaNhanVien = new JLabel("Mã nhân viên:");
		lblMaNhanVien.setHorizontalAlignment(SwingConstants.LEFT);
		lblMaNhanVien.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaNhanVien.setBounds(1054, 61, 106, 26);
		panel_3.add(lblMaNhanVien);

		cbMaNhanVien = new JComboBox();
		cbMaNhanVien.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaNhanVien.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaNhanVien.setEditable(true);
		cbMaNhanVien.setBackground(Color.WHITE);
		cbMaNhanVien.setBounds(1164, 61, 234, 29);
		panel_3.add(cbMaNhanVien);

		btnTimKiem = new JButton("Tìm");
		btnTimKiem.setHorizontalAlignment(SwingConstants.LEFT);
		btnTimKiem.setIcon(new ImageIcon("data\\icons\\search16.png"));
		btnTimKiem.setBackground(Color.WHITE);
		btnTimKiem.setFont(new Font("Arial", Font.PLAIN, 16));
		btnTimKiem.setBounds(1408, 61, 84, 30);
		panel_3.add(btnTimKiem);

		String[] headers = { "STT", "Mã hóa đơn", "Ngày lập hóa đơn", "Mã khách hàng", "Tên khách hàng", "Mã nhân viên",
				"Tên nhân viên", "Thẻ bảo hiểm" };
		modelHoaDon = new DefaultTableModel(headers, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tableHoaDon = new JTable(modelHoaDon);
		tableHoaDon.getTableHeader().setBackground(Color.CYAN);
		tableHoaDon.getTableHeader().setFont(new Font(null, Font.PLAIN, 14));
		tableHoaDon.setSelectionBackground(Color.YELLOW);
		tableHoaDon.setSelectionForeground(Color.RED);
		tableHoaDon.setFont(new Font(null, Font.PLAIN, 14));
		tableHoaDon.setRowHeight(22);
		tableHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 121, 1502, 485);
		panel_chart.add(scrollPane);
		scrollPane.setViewportView(tableHoaDon);

		lblPageIndex = new JLabel();
		lblPageIndex.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPageIndex.setHorizontalAlignment(SwingConstants.CENTER);
		lblPageIndex.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPageIndex.setBounds(731, 616, 67, 25);
		panel_chart.add(lblPageIndex);

		btnNextPage = new JButton("");
		btnNextPage.setIcon(new ImageIcon("data\\icons\\next-page-32.png"));
		btnNextPage.setBounds(808, 616, 24, 24);
		panel_chart.add(btnNextPage);

		btnPreviousPage = new JButton("");
		btnPreviousPage.setIcon(new ImageIcon("data\\icons\\previous-page-32.png"));
		btnPreviousPage.setBounds(697, 617, 24, 24);
		panel_chart.add(btnPreviousPage);

		btnLastPage = new JButton("");
		btnLastPage.setIcon(new ImageIcon("data\\icons\\last-index-32.png"));
		btnLastPage.setBounds(842, 616, 24, 24);
		panel_chart.add(btnLastPage);

		btnFirstPage = new JButton("");
		btnFirstPage.setIcon(new ImageIcon("data\\icons\\first-page-32.png"));
		btnFirstPage.setBounds(663, 617, 24, 24);
		panel_chart.add(btnFirstPage);

		JLabel lblXemNgay = new JLabel("Ngày hiện tại:");
		lblXemNgay.setHorizontalAlignment(SwingConstants.LEFT);
		lblXemNgay.setFont(new Font("Arial", Font.BOLD, 16));
		lblXemNgay.setBounds(1240, 26, 113, 26);
		panel_2.add(lblXemNgay);

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

		setTitle("Xem danh sách hóa đơn");
		Toolkit tk = Toolkit.getDefaultToolkit();
		setSize(tk.getScreenSize().width, tk.getScreenSize().height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);

		HoaDon_DAO hoadon_dao = new HoaDon_DAO();
		danhSachTatCaHoaDon = hoadon_dao.layTatCaHoaDon();
		if (danhSachTatCaHoaDon != null) {
			capNhatDuLieuTrenBang_Init(danhSachTatCaHoaDon, soPhanTuMuonHienThi);
		} else {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Chưa có hóa đơn nào", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		}

		cbMaHoaDon.addActionListener(this);
		cbMaKhachHang.addActionListener(this);
		cbMaNhanVien.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnNextPage.addActionListener(this);
		btnPreviousPage.addActionListener(this);
		btnLastPage.addActionListener(this);
		btnFirstPage.addActionListener(this);
		btnReset.addActionListener(this);
		tableHoaDon.getSelectionModel().addListSelectionListener(this);
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
		int row = tableHoaDon.getSelectedRow();
		fillForm(row);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(cbMaHoaDon)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxMaHD();
				int soPhanTuHienCo = cbMaHoaDon.getItemCount();
				String tuHienTai = cbMaHoaDon.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxMaHD();
						cbMaHoaDon.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbMaHoaDon.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbMaHoaDon.addItem(cacUngVien.get(i));
							}
							cbMaHoaDon.setSelectedItem(tuHienTai);
							cbMaHoaDon.showPopup();
						} else if (soUngVien == 0) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbMaHoaDon, "Không tìm thấy mã hóa đơn nào phù hợp",
									"Thông báo", JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxMaHD();
							cbMaHoaDon.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaHoaDon, "Không tìm thấy mã hóa đơn nào", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbMaHoaDon.setSelectedItem("");
			}
		} else if (obj.equals(cbMaKhachHang)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxMaKH();
				int soPhanTuHienCo = cbMaKhachHang.getItemCount();
				String tuHienTai = cbMaKhachHang.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxMaKH();
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
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbMaKhachHang, "Không tìm thấy mã khách hàng nào phù hợp",
									"Thông báo", JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxMaKH();
							cbMaKhachHang.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaKhachHang, "Không tìm thấy mã khách hàng nào", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbMaKhachHang.setSelectedItem("");
			}
		} else if (obj.equals(cbMaNhanVien)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxMaNV();
				int soPhanTuHienCo = cbMaNhanVien.getItemCount();
				String tuHienTai = cbMaNhanVien.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxMaNV();
						cbMaNhanVien.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbMaNhanVien.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbMaNhanVien.addItem(cacUngVien.get(i));
							}
							cbMaNhanVien.setSelectedItem(tuHienTai);
							cbMaNhanVien.showPopup();
						} else if (soUngVien == 0) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbMaNhanVien, "Không tìm thấy mã nhân viên nào phù hợp",
									"Thông báo", JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxMaNV();
							cbMaNhanVien.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaNhanVien, "Không tìm thấy mã nhân viên nào", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbMaNhanVien.setSelectedItem("");
			}
		} else if (obj.equals(btnTimKiem)) {
			tableHoaDon.clearSelection();
			capNhatDuLieuTrenBang_Search(danhSachTatCaHoaDon, soPhanTuMuonHienThi);
		} else if (obj.equals(btnNextPage)) {
			tableHoaDon.clearSelection();
			capNhatDuLieuTrenBang_Next(danhSachTatCaHoaDon, soPhanTuMuonHienThi);
		} else if (obj.equals(btnPreviousPage)) {
			tableHoaDon.clearSelection();
			capNhatDuLieuTrenBang_Previous(danhSachTatCaHoaDon, soPhanTuMuonHienThi);
		} else if (obj.equals(btnLastPage)) {
			tableHoaDon.clearSelection();
			capNhatDuLieuTrenBang_Last(danhSachTatCaHoaDon, soPhanTuMuonHienThi);
		} else if (obj.equals(btnFirstPage)) {
			tableHoaDon.clearSelection();
			capNhatDuLieuTrenBang_Init(danhSachTatCaHoaDon, soPhanTuMuonHienThi);
		} else if (obj.equals(btnReset)) {
			tableHoaDon.clearSelection();
			capNhatDuLieuTrenBang_Init(danhSachTatCaHoaDon, soPhanTuMuonHienThi);
			xoaTrang();
		}
	}
}
