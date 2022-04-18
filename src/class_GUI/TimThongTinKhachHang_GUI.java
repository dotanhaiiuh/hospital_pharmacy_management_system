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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import java.awt.FlowLayout;

import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import class_DAO.KhachHang_DAO;
import class_Entities.KhachHang;
import class_Entities.NhanVien;
import class_Equipment.DateLabelFormatter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

public class TimThongTinKhachHang_GUI extends JFrame implements WindowListener, ActionListener, ListSelectionListener {
	private JDatePickerImpl datePickerNgaySinh;
	private Properties p;
	private UtilDateModel model_ngaysinh;
	private JDatePanelImpl datePanelNgaySinh;
	private UtilDateModel model_ngaydangky;
	private JDatePanelImpl datePanelNgayDangKy;
	private JDatePickerImpl datePickerNgayDangKy;
	private DefaultTableModel modelKhachHang;
	private JTable tableKhachHang;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	private JComboBox cbSoDienThoai;
	private JComboBox cbMaTheBaoHiem;

	private JButton btnHome;

	private NhanVien emp;
	private ArrayList<KhachHang> danhSachTatCaKhachHang;
	private final int soPhanTuMuonHienThi = 21;
	
	private JComboBox cbMaKhachHang;
	private JButton btnSearchMaKhachHang;
	private JLabel lblMaNhanVien;
	private JLabel lblDiaChi;
	private JButton btnSearchDiaChiKhachHang;
	private JButton btnSearchNgaySinh;
	private JComboBox cbGioiTinh;
	private JButton btnSearchNgayDangKy;
	private JButton btnSearchGioiTinh;
	private JButton btnSearchMaTheBaoHiem;
	private JButton btnXemChiTiet;
	private JButton btnReset;
	private String tenChucNangCanThucHienKhiTimKiem;
	private JComboBox cbDiaChiKhachHang;
	private JButton btnSearchSoDienThoai;
	private JButton btnFirstPage;
	private JButton btnPreviousPage;
	private JLabel lblPageIndex;
	private JButton btnNextPage;
	private JButton btnLastPage;

	private KhachHang taoKhachHang() {
		int row = tableKhachHang.getSelectedRow();
		String maKH = String.valueOf(tableKhachHang.getValueAt(row, 1));
		KhachHang khachHang = new KhachHang(maKH);
		if (danhSachTatCaKhachHang.contains(khachHang)) {
			return danhSachTatCaKhachHang.get(danhSachTatCaKhachHang.indexOf(khachHang));
		}
		return null;
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
		modelKhachHang.getDataVector().removeAllElements();
	}

	private ArrayList<KhachHang> layDuLieuTrongTrang(ArrayList<KhachHang> danhSachKH, int soTrangMuonLay, int lastPage,
			int soPhanTuMuonHienThi) {
		ArrayList<KhachHang> kq = new ArrayList<KhachHang>();
		if (soTrangMuonLay < lastPage) {
			kq = new ArrayList<KhachHang>(
					danhSachKH.subList(soTrangMuonLay, (soTrangMuonLay * soPhanTuMuonHienThi) + 1));
		} else if (soTrangMuonLay == lastPage) {
			kq = new ArrayList<KhachHang>(danhSachKH.subList((lastPage - 1) * soPhanTuMuonHienThi, danhSachKH.size()));
		}
		return kq;
	}

	private void capNhatDuLieuTrenBang_Init(ArrayList<KhachHang> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			xoaHetDuLieuTrenTableModel();
			int soThuTu = 1;
			for (KhachHang khachHang : danhSachMuonCapNhat) {
				modelKhachHang.addRow(new Object[] { soThuTu++, khachHang.getMaKH(), khachHang.getHoTenKhachHang(),
						sdf.format(khachHang.getNgaySinh()), sdf.format(khachHang.getNgayDangKy()),
						khachHang.getMaTheBaoHiem(), khachHang.getGioiTinh(), khachHang.getSoCMND(),
						khachHang.getEmail(), khachHang.getSoDienThoai(), khachHang.getDiaChi() });
			}
			if (lengthArray > 0) {
				double countPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
				lblPageIndex.setText("1" + "/" + String.valueOf((int) countPage));
			} else {
				lblPageIndex.setText("1" + "/" + "1");
			}
			if (lengthArray < soPhanTuMuonHienThi) {
				modelKhachHang.setRowCount(lengthArray);
			} else {
				modelKhachHang.setRowCount(soPhanTuMuonHienThi);
			}
			tableKhachHang.setModel(modelKhachHang);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenBang_Search(ArrayList<KhachHang> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			int soThuTu = 1;
			xoaHetDuLieuTrenTableModel();
			for (KhachHang khachHang : danhSachMuonCapNhat) {
				modelKhachHang.addRow(new Object[] { soThuTu++, khachHang.getMaKH(), khachHang.getHoTenKhachHang(),
						sdf.format(khachHang.getNgaySinh()), sdf.format(khachHang.getNgayDangKy()),
						khachHang.getMaTheBaoHiem(), khachHang.getGioiTinh(), khachHang.getSoCMND(),
						khachHang.getEmail(), khachHang.getSoDienThoai(), khachHang.getDiaChi() });
			}
			lblPageIndex.setText("1" + "/" + "1");
			modelKhachHang.setRowCount(lengthArray);
			tableKhachHang.setModel(modelKhachHang);
			capNhatTatCaComboBox();
		}
		else {
			lblPageIndex.setText("1" + "/" + "1");
			modelKhachHang.setRowCount(0);
			tableKhachHang.setModel(modelKhachHang);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenBang_Next(ArrayList<KhachHang> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			String string_SoTrangHienTai = lblPageIndex.getText().trim().split("/")[0];
			int soTrangHienTai = Integer.parseInt(string_SoTrangHienTai) + 1;
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			if (soTrangHienTai <= lastPage) {
				int soThuTu = ((soTrangHienTai - 1) * soPhanTuMuonHienThi) + 1;
				xoaHetDuLieuTrenTableModel();
				ArrayList<KhachHang> danhSachKhachHang = layDuLieuTrongTrang(danhSachMuonCapNhat, soTrangHienTai,
						(int) lastPage, soPhanTuMuonHienThi);
				for (KhachHang khachHang : danhSachMuonCapNhat) {
					modelKhachHang.addRow(new Object[] { soThuTu++, khachHang.getMaKH(), khachHang.getHoTenKhachHang(),
							sdf.format(khachHang.getNgaySinh()), sdf.format(khachHang.getNgayDangKy()),
							khachHang.getMaTheBaoHiem(), khachHang.getGioiTinh(), khachHang.getSoCMND(),
							khachHang.getEmail(), khachHang.getSoDienThoai(), khachHang.getDiaChi() });
				}
				lblPageIndex.setText(String.valueOf(soTrangHienTai) + "/" + String.valueOf((int) lastPage));
				modelKhachHang.setRowCount(danhSachKhachHang.size());
				tableKhachHang.setModel(modelKhachHang);
				capNhatTatCaComboBox();
			}
		}
	}

	private void capNhatDuLieuTrenBang_Previous(ArrayList<KhachHang> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			String soThuTuDauTien = String.valueOf(tableKhachHang.getValueAt(0, 0));
			String string_SoTrangHienTai = lblPageIndex.getText().trim().split("/")[0];
			int soTrangHienTai = Integer.parseInt(string_SoTrangHienTai) - 1;
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			if (soTrangHienTai > 0) {
				int soThuTu = Integer.parseInt(soThuTuDauTien) - soPhanTuMuonHienThi;
				xoaHetDuLieuTrenTableModel();
				ArrayList<KhachHang> danhSachKhachHang = layDuLieuTrongTrang(danhSachMuonCapNhat, soTrangHienTai,
						(int) lastPage, soPhanTuMuonHienThi);
				for (KhachHang khachHang : danhSachMuonCapNhat) {
					modelKhachHang.addRow(new Object[] { soThuTu++, khachHang.getMaKH(), khachHang.getHoTenKhachHang(),
							sdf.format(khachHang.getNgaySinh()), sdf.format(khachHang.getNgayDangKy()),
							khachHang.getMaTheBaoHiem(), khachHang.getGioiTinh(), khachHang.getSoCMND(),
							khachHang.getEmail(), khachHang.getSoDienThoai(), khachHang.getDiaChi() });
				}
				lblPageIndex.setText(String.valueOf(soTrangHienTai) + "/" + String.valueOf((int) lastPage));
				modelKhachHang.setRowCount(danhSachKhachHang.size());
				tableKhachHang.setModel(modelKhachHang);
				capNhatTatCaComboBox();
			}
		}
	}

	private void capNhatDuLieuTrenBang_Last(ArrayList<KhachHang> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			int last_index = (int) (soPhanTuMuonHienThi * (lastPage - 1));
			int soThuTu = last_index + 1;
			xoaHetDuLieuTrenTableModel();
			ArrayList<KhachHang> danhSachKhachHang = new ArrayList<KhachHang>(
					danhSachMuonCapNhat.subList(last_index, lengthArray));
			for (KhachHang khachHang : danhSachMuonCapNhat) {
				modelKhachHang.addRow(new Object[] { soThuTu++, khachHang.getMaKH(), khachHang.getHoTenKhachHang(),
						sdf.format(khachHang.getNgaySinh()), sdf.format(khachHang.getNgayDangKy()),
						khachHang.getMaTheBaoHiem(), khachHang.getGioiTinh(), khachHang.getSoCMND(),
						khachHang.getEmail(), khachHang.getSoDienThoai(), khachHang.getDiaChi() });
			}
			lblPageIndex.setText(String.valueOf((int) lastPage) + "/" + String.valueOf((int) lastPage));
			modelKhachHang.setRowCount(danhSachKhachHang.size());
			tableKhachHang.setModel(modelKhachHang);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenComboBoxMaKhachHang() {
		cbMaKhachHang.removeAllItems();
		if (danhSachTatCaKhachHang.size() > 0) {
			ArrayList<String> danhSachKhachHang = new ArrayList<String>();
			for (KhachHang khachHang : danhSachTatCaKhachHang) {
				String maKH = khachHang.getMaKH();
				String tenKH = khachHang.getHoTenKhachHang();
				String s = maKH + " - " + tenKH;
				if (!danhSachKhachHang.contains(s)) {
					danhSachKhachHang.add(s);
					cbMaKhachHang.addItem(s);
				}
			}
		}
		cbMaKhachHang.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxMaKhachHang() {
		ArrayList<String> danhSachKhachHang = new ArrayList<String>();
		for (KhachHang khachHang : danhSachTatCaKhachHang) {
			String maKH = khachHang.getMaKH();
			String tenKH = khachHang.getHoTenKhachHang();
			String s = maKH + " - " + tenKH;
			if (!danhSachKhachHang.contains(s)) {
				danhSachKhachHang.add(s);
			}
		}
		return danhSachKhachHang;
	}

	private void capNhatDuLieuTrenComboBoxSoDienThoai() {
		cbSoDienThoai.removeAllItems();
		if (danhSachTatCaKhachHang.size() > 0) {
			ArrayList<String> danhSachSoDienThoai = new ArrayList<String>();
			for (KhachHang khachHang : danhSachTatCaKhachHang) {
				String soDienThoai = khachHang.getSoDienThoai();
				if (!danhSachSoDienThoai.contains(soDienThoai)) {
					danhSachSoDienThoai.add(soDienThoai);
					cbSoDienThoai.addItem(soDienThoai);
				}
			}
		}
		cbSoDienThoai.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxSoDienThoai() {
		ArrayList<String> danhSachSoDienThoai = new ArrayList<String>();
		for (KhachHang khachHang : danhSachTatCaKhachHang) {
			String soDienThoai = khachHang.getSoDienThoai();
			if (!danhSachSoDienThoai.contains(soDienThoai)) {
				danhSachSoDienThoai.add(soDienThoai);
			}
		}
		return danhSachSoDienThoai;
	}

	private void capNhatDuLieuTrenComboBoxDiaChiKhachHang() {
		cbDiaChiKhachHang.removeAllItems();
		if (danhSachTatCaKhachHang.size() > 0) {
			ArrayList<String> danhSachDiaChiKhachHang = new ArrayList<String>();
			for (KhachHang khachHang : danhSachTatCaKhachHang) {
				String diaChiKhachHang = khachHang.getDiaChi();
				if (!danhSachDiaChiKhachHang.contains(diaChiKhachHang)) {
					danhSachDiaChiKhachHang.add(diaChiKhachHang);
					cbDiaChiKhachHang.addItem(diaChiKhachHang);
				}
			}
		}
		cbDiaChiKhachHang.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxDiaChiKhachHang() {
		ArrayList<String> danhSachDiaChiKhachHang = new ArrayList<String>();
		for (KhachHang khachHang : danhSachTatCaKhachHang) {
			String diaChiKhachHang = khachHang.getDiaChi();
			if (!danhSachDiaChiKhachHang.contains(diaChiKhachHang)) {
				danhSachDiaChiKhachHang.add(diaChiKhachHang);
			}
		}
		return danhSachDiaChiKhachHang;
	}

	private void capNhatDuLieuTrenComboBoxMaTheBaoHiem() {
		cbMaTheBaoHiem.removeAllItems();
		if (danhSachTatCaKhachHang.size() > 0) {
			ArrayList<String> cacMaTheBaoHiem = new ArrayList<String>();
			for (KhachHang khachHang : danhSachTatCaKhachHang) {
				String maTheBaoHiem = khachHang.getMaTheBaoHiem().trim();
				if (!maTheBaoHiem.equalsIgnoreCase("") && !cacMaTheBaoHiem.contains(maTheBaoHiem)) {
					cacMaTheBaoHiem.add(maTheBaoHiem);
					cbMaTheBaoHiem.addItem(maTheBaoHiem);
				}
			}
		}
		cbMaTheBaoHiem.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxMaTheBaoHiem() {
		ArrayList<String> danhSachMaTheBaoHiem = new ArrayList<String>();
		for (KhachHang khachHang : danhSachTatCaKhachHang) {
			String maTheBaoHiem = khachHang.getMaTheBaoHiem().trim();
			if (!maTheBaoHiem.equalsIgnoreCase("") && !danhSachMaTheBaoHiem.contains(maTheBaoHiem)) {
				danhSachMaTheBaoHiem.add(maTheBaoHiem);
				cbMaTheBaoHiem.addItem(maTheBaoHiem);
			}
		}
		return danhSachMaTheBaoHiem;
	}

	private void capNhatDuLieuTrenComboBoxGioiTinh() {
		cbGioiTinh.removeAllItems();
		if (danhSachTatCaKhachHang.size() > 0) {
			ArrayList<String> cacGioiTinh = new ArrayList<String>();
			for (KhachHang khachHang : danhSachTatCaKhachHang) {
				String gioiTinh = khachHang.getGioiTinh();
				if (!cacGioiTinh.contains(gioiTinh)) {
					cacGioiTinh.add(gioiTinh);
					cbGioiTinh.addItem(gioiTinh);
				}
			}
		}
		cbGioiTinh.setSelectedIndex(-1);
	}

	private void capNhatTatCaComboBox() {
		capNhatDuLieuTrenComboBoxMaKhachHang();
		capNhatDuLieuTrenComboBoxMaTheBaoHiem();
		capNhatDuLieuTrenComboBoxGioiTinh();
		capNhatDuLieuTrenComboBoxSoDienThoai();
		capNhatDuLieuTrenComboBoxDiaChiKhachHang();
	}

	private void xoaTrang() {
		cbMaKhachHang.setSelectedItem("");
		datePickerNgaySinh.getJFormattedTextField().setText("");
		datePickerNgayDangKy.getJFormattedTextField().setText("");
		cbGioiTinh.setSelectedIndex(-1);
		cbSoDienThoai.setSelectedItem("");
		cbMaTheBaoHiem.setSelectedItem("");
		cbDiaChiKhachHang.setSelectedItem("");
		cbMaKhachHang.requestFocus();
	}

	private void fillForm(int row) {
		if (row != -1) {
			String maKH = String.valueOf(tableKhachHang.getValueAt(row, 1));
			KhachHang khachHang = new KhachHang(maKH);
			ArrayList<KhachHang> dsKhachHang = danhSachTatCaKhachHang;

			if (dsKhachHang.contains(khachHang)) {
				khachHang = dsKhachHang.get(danhSachTatCaKhachHang.indexOf(khachHang));
				cbMaKhachHang.removeAllItems();
				cbMaKhachHang.addItem(maKH);
				cbMaKhachHang.setSelectedItem(maKH);

				Date ngaySinh = khachHang.getNgaySinh();
				model_ngaysinh.setValue(ngaySinh);
				datePickerNgaySinh.getJFormattedTextField().setText(sdf.format(ngaySinh));

				Date ngayDangKy = khachHang.getNgayDangKy();
				model_ngaydangky.setValue(ngayDangKy);
				datePickerNgayDangKy.getJFormattedTextField().setText(sdf.format(ngayDangKy));

				cbGioiTinh.setSelectedItem(khachHang.getGioiTinh());

				String maTheBaoHiem = khachHang.getMaTheBaoHiem();
				cbMaTheBaoHiem.removeAllItems();
				cbMaTheBaoHiem.addItem(maTheBaoHiem);
				cbMaTheBaoHiem.setSelectedItem(maTheBaoHiem);

				String soDienThoai = khachHang.getSoDienThoai();
				cbSoDienThoai.removeAllItems();
				cbSoDienThoai.addItem(soDienThoai);
				cbSoDienThoai.setSelectedItem(soDienThoai);

				String diaChi = khachHang.getDiaChi();
				cbDiaChiKhachHang.removeAllItems();
				cbDiaChiKhachHang.addItem(diaChi);
				cbDiaChiKhachHang.setSelectedItem(diaChi);
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public TimThongTinKhachHang_GUI(NhanVien nv, String tenChucNangDaChon) {
		emp = nv;
		tenChucNangCanThucHienKhiTimKiem = tenChucNangDaChon;
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\search24.png"));
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

		btnXemChiTiet = new JButton("Xem thông tin chi tiết về khách hàng");
		btnXemChiTiet.setBackground(Color.WHITE);
		btnXemChiTiet.setIcon(new ImageIcon("data\\icons\\more-details-16.png"));
		btnXemChiTiet.setFocusable(false);
		panel.add(btnXemChiTiet);
		btnXemChiTiet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableKhachHang.getSelectedRow();
				if (row != -1) {
					if (tenChucNangCanThucHienKhiTimKiem.equalsIgnoreCase("Sửa thông tin khách hàng")) {
						SuaThongTinKhacHang_GUI suaThongTinKhacHang = new SuaThongTinKhacHang_GUI(taoKhachHang(), emp,
								getTitle());
						suaThongTinKhacHang.setVisible(true);
					} else if (tenChucNangCanThucHienKhiTimKiem.equalsIgnoreCase("Xóa thông tin khách hàng")) {
						XoaThongTinKhacHang_GUI xoaThongTinKhacHang = new XoaThongTinKhacHang_GUI(taoKhachHang(), emp,
								false);
						xoaThongTinKhacHang.setVisible(true);
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn cần chọn khách hàng muốn xem chi tiết", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

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
		btnHome.setToolTipText("Tho\u00E1t");
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
		lblTitle.setIcon(new ImageIcon("data\\icons\\search-customer-title.png"));
		lblTitle.setBounds(428, 10, 842, 89);
		panel_2.add(lblTitle);

		JPanel panel_chart = new JPanel();
		panel_chart.setBackground(Color.WHITE);
		panel_chart.setBounds(-13, 109, 1535, 645);
		panel_chart.setLayout(null);
		panel_2.add(panel_chart);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(23, 10, 1502, 242);
		panel_3.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_chart.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblThongTinKhachHang = new JLabel("Thông tin khách hàng");
		lblThongTinKhachHang.setForeground(SystemColor.textHighlight);
		lblThongTinKhachHang.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinKhachHang.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinKhachHang.setBounds(20, 22, 1472, 35);
		panel_3.add(lblThongTinKhachHang);

		lblMaNhanVien = new JLabel("Mã khách hàng:");
		lblMaNhanVien.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaNhanVien.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaNhanVien.setBounds(10, 68, 130, 26);
		panel_3.add(lblMaNhanVien);

		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgaySinh.setBounds(745, 68, 130, 26);
		panel_3.add(lblNgaySinh);

		p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		model_ngaysinh = new UtilDateModel();
		datePanelNgaySinh = new JDatePanelImpl(model_ngaysinh, p);
		datePickerNgaySinh = new JDatePickerImpl(datePanelNgaySinh, new DateLabelFormatter());
		SpringLayout springLayout_1 = (SpringLayout) datePickerNgaySinh.getLayout();
		springLayout_1.putConstraint(SpringLayout.SOUTH, datePickerNgaySinh.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, datePickerNgaySinh);
		datePickerNgaySinh.setBounds(885, 67, 566, 27);
		datePickerNgaySinh.setFocusable(false);
		panel_3.add(datePickerNgaySinh);

		JLabel lblNgayDangKy = new JLabel("Ngày đăng ký:");
		lblNgayDangKy.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgayDangKy.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgayDangKy.setBounds(10, 114, 130, 26);
		panel_3.add(lblNgayDangKy);

		model_ngaydangky = new UtilDateModel();
		datePanelNgayDangKy = new JDatePanelImpl(model_ngaydangky, p);
		datePickerNgayDangKy = new JDatePickerImpl(datePanelNgayDangKy, new DateLabelFormatter());
		SpringLayout springLayout_2 = (SpringLayout) datePickerNgayDangKy.getLayout();
		springLayout_2.putConstraint(SpringLayout.SOUTH, datePickerNgayDangKy.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, datePickerNgayDangKy);
		datePickerNgayDangKy.setBounds(146, 114, 532, 27);
		datePickerNgayDangKy.setFocusable(false);
		panel_3.add(datePickerNgayDangKy);

		JLabel lblSoDienThoai = new JLabel("Số điện thoại:");
		lblSoDienThoai.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoDienThoai.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoDienThoai.setBounds(10, 157, 130, 26);
		panel_3.add(lblSoDienThoai);

		cbSoDienThoai = new JComboBox();
		cbSoDienThoai.setEditable(true);
		cbSoDienThoai.setFont(new Font("Arial", Font.BOLD, 16));
		cbSoDienThoai.setBackground(Color.WHITE);
		cbSoDienThoai.setBounds(146, 157, 532, 29);
		panel_3.add(cbSoDienThoai);

		JLabel lblMaTheBaoHiem = new JLabel("Mã thẻ bảo hiểm:");
		lblMaTheBaoHiem.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaTheBaoHiem.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaTheBaoHiem.setBounds(745, 160, 130, 26);
		panel_3.add(lblMaTheBaoHiem);

		cbMaTheBaoHiem = new JComboBox();
		cbMaTheBaoHiem.setEditable(true);
		cbMaTheBaoHiem.setFont(new Font("Arial", Font.BOLD, 16));
		cbMaTheBaoHiem.setBackground(Color.WHITE);
		cbMaTheBaoHiem.setBounds(885, 157, 566, 29);
		panel_3.add(cbMaTheBaoHiem);

		btnSearchNgaySinh = new JButton();
		btnSearchNgaySinh.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchNgaySinh.setToolTipText("Tìm kiếm khách hàng theo ngày sinh");
		btnSearchNgaySinh.setFocusable(false);
		btnSearchNgaySinh.setBorder(BorderFactory.createEmptyBorder());
		btnSearchNgaySinh.setBackground(Color.WHITE);
		btnSearchNgaySinh.setBounds(1461, 63, 31, 31);
		panel_3.add(btnSearchNgaySinh);

		cbMaKhachHang = new JComboBox();
		cbMaKhachHang.setEditable(true);
		cbMaKhachHang.setSelectedIndex(-1);
		cbMaKhachHang.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaKhachHang.setBackground(Color.WHITE);
		cbMaKhachHang.setBounds(146, 67, 532, 29);
		panel_3.add(cbMaKhachHang);

		btnSearchMaKhachHang = new JButton();
		btnSearchMaKhachHang.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchMaKhachHang.setToolTipText("Tìm kiếm khách hàng theo mã khách hàng");
		btnSearchMaKhachHang.setFocusable(false);
		btnSearchMaKhachHang.setBorder(BorderFactory.createEmptyBorder());
		btnSearchMaKhachHang.setBackground(Color.WHITE);
		btnSearchMaKhachHang.setBounds(688, 67, 31, 31);
		panel_3.add(btnSearchMaKhachHang);

		btnSearchNgayDangKy = new JButton();
		btnSearchNgayDangKy.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchNgayDangKy.setToolTipText("Tìm kiếm khách hàng theo ngày vào làm");
		btnSearchNgayDangKy.setFocusable(false);
		btnSearchNgayDangKy.setBorder(BorderFactory.createEmptyBorder());
		btnSearchNgayDangKy.setBackground(Color.WHITE);
		btnSearchNgayDangKy.setBounds(688, 111, 31, 31);
		panel_3.add(btnSearchNgayDangKy);

		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGioiTinh.setFont(new Font("Arial", Font.BOLD, 16));
		lblGioiTinh.setBounds(745, 116, 130, 26);
		panel_3.add(lblGioiTinh);

		cbGioiTinh = new JComboBox();
		cbGioiTinh.setSelectedIndex(-1);
		cbGioiTinh.setFont(new Font("Arial", Font.BOLD, 16));
		cbGioiTinh.setBackground(Color.WHITE);
		cbGioiTinh.setBounds(885, 112, 566, 29);
		panel_3.add(cbGioiTinh);

		btnSearchGioiTinh = new JButton();
		btnSearchGioiTinh.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchGioiTinh.setToolTipText("Tìm kiếm khách hàng theo giới tính");
		btnSearchGioiTinh.setFocusable(false);
		btnSearchGioiTinh.setBorder(BorderFactory.createEmptyBorder());
		btnSearchGioiTinh.setBackground(Color.WHITE);
		btnSearchGioiTinh.setBounds(1461, 109, 31, 31);
		panel_3.add(btnSearchGioiTinh);

		btnSearchMaTheBaoHiem = new JButton();
		btnSearchMaTheBaoHiem.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchMaTheBaoHiem.setToolTipText("Tìm kiếm khách hàng theo mã thẻ bảo hiểm");
		btnSearchMaTheBaoHiem.setFocusable(false);
		btnSearchMaTheBaoHiem.setBorder(BorderFactory.createEmptyBorder());
		btnSearchMaTheBaoHiem.setBackground(Color.WHITE);
		btnSearchMaTheBaoHiem.setBounds(1461, 156, 31, 31);
		panel_3.add(btnSearchMaTheBaoHiem);

		btnSearchDiaChiKhachHang = new JButton();
		btnSearchDiaChiKhachHang.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchDiaChiKhachHang.setToolTipText("Tìm kiếm khách hàng theo địa chỉ");
		btnSearchDiaChiKhachHang.setFocusable(false);
		btnSearchDiaChiKhachHang.setBorder(BorderFactory.createEmptyBorder());
		btnSearchDiaChiKhachHang.setBackground(Color.WHITE);
		btnSearchDiaChiKhachHang.setBounds(1461, 198, 31, 31);
		panel_3.add(btnSearchDiaChiKhachHang);

		lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiaChi.setFont(new Font("Arial", Font.BOLD, 16));
		lblDiaChi.setBounds(10, 198, 130, 26);
		panel_3.add(lblDiaChi);

		btnSearchSoDienThoai = new JButton();
		btnSearchSoDienThoai.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchSoDienThoai.setToolTipText("Tìm kiếm khách hàng theo số điện thoại");
		btnSearchSoDienThoai.setFocusable(false);
		btnSearchSoDienThoai.setBorder(BorderFactory.createEmptyBorder());
		btnSearchSoDienThoai.setBackground(Color.WHITE);
		btnSearchSoDienThoai.setBounds(688, 157, 31, 31);
		panel_3.add(btnSearchSoDienThoai);

		cbDiaChiKhachHang = new JComboBox();
		cbDiaChiKhachHang.setFont(new Font("Arial", Font.BOLD, 16));
		cbDiaChiKhachHang.setEditable(true);
		cbDiaChiKhachHang.setBackground(Color.WHITE);
		cbDiaChiKhachHang.setBounds(146, 199, 1305, 29);
		panel_3.add(cbDiaChiKhachHang);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 262, 1502, 339);
		panel_chart.add(scrollPane);

		String[] headers = { "STT", "Mã khách hàng", "Tên khách hàng", "Ngày sinh", "Ngày đăng ký", "Mã thẻ bảo hiểm",
				"Giới Tính", "Số CMND", "Email", "Số điện thoại", "Địa chỉ" };
		modelKhachHang = new DefaultTableModel(headers, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tableKhachHang = new JTable(modelKhachHang);
		tableKhachHang.getTableHeader().setBackground(Color.CYAN);
		tableKhachHang.getTableHeader().setFont(new Font(null, Font.PLAIN, 14));
		tableKhachHang.setSelectionBackground(Color.YELLOW);
		tableKhachHang.setSelectionForeground(Color.RED);
		tableKhachHang.setFont(new Font(null, Font.PLAIN, 14));
		tableKhachHang.setRowHeight(22);
		tableKhachHang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableKhachHang.setToolTipText("Nhấn đúp chuột vào dòng bất kỳ để xem thông tin chi tiết về khách hàng");
		scrollPane.setViewportView(tableKhachHang);
		tableKhachHang.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (tenChucNangCanThucHienKhiTimKiem.equalsIgnoreCase("Sửa thông tin khách hàng")) {
						SuaThongTinKhacHang_GUI suaThongTinKhacHang = new SuaThongTinKhacHang_GUI(taoKhachHang(), emp,
								getTitle());
						suaThongTinKhacHang.setVisible(true);
					} else if (tenChucNangCanThucHienKhiTimKiem.equalsIgnoreCase("Xóa thông tin khách hàng")) {
						XoaThongTinKhacHang_GUI xoaThongTinKhacHang = new XoaThongTinKhacHang_GUI(taoKhachHang(), emp,
								false);
						xoaThongTinKhacHang.setVisible(true);
					}
				}
			}
		});
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(240, 240, 240));

		btnFirstPage = new JButton("");
		btnFirstPage.setIcon(new ImageIcon("data\\icons\\first-page-32.png"));
		btnFirstPage.setBounds(674, 612, 24, 24);
		panel_chart.add(btnFirstPage);

		btnPreviousPage = new JButton("");
		btnPreviousPage.setIcon(new ImageIcon("data\\icons\\previous-page-32.png"));
		btnPreviousPage.setBounds(708, 612, 24, 24);
		panel_chart.add(btnPreviousPage);

		lblPageIndex = new JLabel();
		lblPageIndex.setHorizontalAlignment(SwingConstants.CENTER);
		lblPageIndex.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPageIndex.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPageIndex.setBounds(742, 611, 67, 25);
		panel_chart.add(lblPageIndex);

		btnNextPage = new JButton("");
		btnNextPage.setIcon(new ImageIcon("data\\icons\\next-page-32.png"));
		btnNextPage.setBounds(819, 611, 24, 24);
		panel_chart.add(btnNextPage);

		btnLastPage = new JButton("");
		btnLastPage.setIcon(new ImageIcon("data\\icons\\last-index-32.png"));
		btnLastPage.setBounds(853, 611, 24, 24);
		panel_chart.add(btnLastPage);

		setTitle("Tìm thông tin khách hàng");
		Toolkit tk = Toolkit.getDefaultToolkit();
		setSize(tk.getScreenSize().width, tk.getScreenSize().height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);

		KhachHang_DAO khachhang_dao = new KhachHang_DAO();
		danhSachTatCaKhachHang = khachhang_dao.layTatCaKhachHang();
		if (danhSachTatCaKhachHang != null) {
			capNhatDuLieuTrenBang_Init(danhSachTatCaKhachHang, soPhanTuMuonHienThi);
		} else {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Chưa có khách hàng nào", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		}

		cbMaKhachHang.addActionListener(this);
		btnSearchMaKhachHang.addActionListener(this);
		btnSearchNgaySinh.addActionListener(this);
		btnSearchNgayDangKy.addActionListener(this);
		btnSearchGioiTinh.addActionListener(this);
		btnSearchMaTheBaoHiem.addActionListener(this);
		cbMaTheBaoHiem.addActionListener(this);
		cbSoDienThoai.addActionListener(this);
		btnSearchSoDienThoai.addActionListener(this);
		btnSearchDiaChiKhachHang.addActionListener(this);
		btnNextPage.addActionListener(this);
		btnPreviousPage.addActionListener(this);
		btnLastPage.addActionListener(this);
		btnFirstPage.addActionListener(this);
		btnReset.addActionListener(this);
		tableKhachHang.getSelectionModel().addListSelectionListener(this);
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
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(cbMaKhachHang)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxMaKhachHang();
				int soPhanTuHienCo = cbMaKhachHang.getItemCount();
				String tuHienTai = cbMaKhachHang.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxMaKhachHang();
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
							JOptionPane.showMessageDialog(cbMaKhachHang,
									"Không tìm thấy mã khách hàng hoặc tên khách hàng nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxMaKhachHang();
							cbMaKhachHang.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaKhachHang, "Không tìm thấy mã khách hàng hoặc tên khách hàng nào",
							"Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbMaKhachHang.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchMaKhachHang)) {
			tableKhachHang.clearSelection();
			try {
				String maKHNhapVao = cbMaKhachHang.getSelectedItem().toString().trim();
				if (!maKHNhapVao.equalsIgnoreCase("")) {
					Iterator<KhachHang> it = danhSachTatCaKhachHang.iterator();
					while (it.hasNext()) {
						KhachHang khachHang = (KhachHang) it.next();
						String maKH = khachHang.getMaKH() + " - " + khachHang.getHoTenKhachHang();
						if (!maKH.toLowerCase().contains(maKHNhapVao.toLowerCase())) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaKhachHang, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				e1.printStackTrace();
			}
		} else if (obj.equals(cbMaTheBaoHiem)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxMaTheBaoHiem();
				int soPhanTuHienCo = cbMaTheBaoHiem.getItemCount();
				String tuHienTai = cbMaTheBaoHiem.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxMaTheBaoHiem();
						cbMaTheBaoHiem.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbMaTheBaoHiem.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbMaTheBaoHiem.addItem(cacUngVien.get(i));
							}
							cbMaTheBaoHiem.setSelectedItem(tuHienTai);
							cbMaTheBaoHiem.showPopup();
						} else if (soUngVien == 0) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbMaTheBaoHiem,
									"Không tìm thấy mã thẻ bảo hiểm của khách hàng nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxMaTheBaoHiem();
							cbMaTheBaoHiem.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaTheBaoHiem, "Không tìm thấy mã thẻ bảo hiểm nào", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbMaTheBaoHiem.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchMaTheBaoHiem)) {
			tableKhachHang.clearSelection();
			try {
				String maTheBaoHiemNhapVao = cbMaTheBaoHiem.getSelectedItem().toString().trim();
				if (!maTheBaoHiemNhapVao.equalsIgnoreCase("")) {
					Iterator<KhachHang> it = danhSachTatCaKhachHang.iterator();
					while (it.hasNext()) {
						KhachHang khachHang = (KhachHang) it.next();
						String maTheBaoHiem = khachHang.getMaTheBaoHiem();
						if (!maTheBaoHiem.contains(maTheBaoHiemNhapVao)) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaKhachHang, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				e1.printStackTrace();
			}
		} else if (obj.equals(btnSearchNgaySinh)) {
			tableKhachHang.clearSelection();
			String string_NgaySinhDangChon = datePickerNgaySinh.getJFormattedTextField().getText();
			if (!string_NgaySinhDangChon.equalsIgnoreCase("")) {
				LocalDate ngaySinhDangChon = LocalDate.parse(string_NgaySinhDangChon, dtf);
				Iterator<KhachHang> it = danhSachTatCaKhachHang.iterator();
				while (it.hasNext()) {
					KhachHang khachHang = (KhachHang) it.next();
					String string_NgaySinh = sdf.format(khachHang.getNgaySinh());
					LocalDate ngaySinh = LocalDate.parse(string_NgaySinh, dtf);
					if (!ngaySinh.equals(ngaySinhDangChon)) {
						it.remove();
					}
				}
				capNhatDuLieuTrenBang_Search(danhSachTatCaKhachHang, soPhanTuMuonHienThi);
			}
		} else if (obj.equals(btnSearchNgayDangKy)) {
			tableKhachHang.clearSelection();
			String string_NgayDangKyDangChon = datePickerNgayDangKy.getJFormattedTextField().getText();
			if (!string_NgayDangKyDangChon.equalsIgnoreCase("")) {
				LocalDate ngayDangKyDangChon = LocalDate.parse(string_NgayDangKyDangChon, dtf);
				Iterator<KhachHang> it = danhSachTatCaKhachHang.iterator();
				while (it.hasNext()) {
					KhachHang khachHang = (KhachHang) it.next();
					String string_NgayDangKy = sdf.format(khachHang.getNgayDangKy());
					LocalDate ngayDangKy = LocalDate.parse(string_NgayDangKy, dtf);
					if (!ngayDangKy.equals(ngayDangKyDangChon)) {
						it.remove();
					}
				}
				capNhatDuLieuTrenBang_Search(danhSachTatCaKhachHang, soPhanTuMuonHienThi);
			}
		} else if (obj.equals(btnSearchGioiTinh)) {
			tableKhachHang.clearSelection();
			try {
				String gioiTinhNhapVao = cbGioiTinh.getSelectedItem().toString().trim();
				if (!gioiTinhNhapVao.equalsIgnoreCase("")) {
					Iterator<KhachHang> it = danhSachTatCaKhachHang.iterator();
					while (it.hasNext()) {
						KhachHang khachHang = (KhachHang) it.next();
						String gioiTinh = khachHang.getGioiTinh();
						if (!gioiTinh.equalsIgnoreCase(gioiTinhNhapVao)) {
							it.remove();
						}
					}
				}
				capNhatDuLieuTrenBang_Search(danhSachTatCaKhachHang, soPhanTuMuonHienThi);
			} catch (Exception e1) {
				cbGioiTinh.setSelectedItem("");
			}
		} else if (obj.equals(cbSoDienThoai)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxSoDienThoai();
				int soPhanTuHienCo = cbSoDienThoai.getItemCount();
				String tuHienTai = cbSoDienThoai.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxSoDienThoai();
						cbSoDienThoai.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbSoDienThoai.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbSoDienThoai.addItem(cacUngVien.get(i));
							}
							cbSoDienThoai.setSelectedItem(tuHienTai);
							cbSoDienThoai.showPopup();
						} else if (soUngVien == 0) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbSoDienThoai,
									"Không tìm thấy số điện thoại của khách hàng nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxSoDienThoai();
							cbSoDienThoai.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbSoDienThoai, "Không tìm thấy số điện thoại của khách hàng nào",
							"Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbSoDienThoai.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchSoDienThoai)) {
			tableKhachHang.clearSelection();
			try {
				String soDienThoaiNhapVao = cbSoDienThoai.getSelectedItem().toString().trim();
				if (!soDienThoaiNhapVao.equalsIgnoreCase("")) {
					Iterator<KhachHang> it = danhSachTatCaKhachHang.iterator();
					while (it.hasNext()) {
						KhachHang khachHang = (KhachHang) it.next();
						String soDienThoai = khachHang.getSoDienThoai();
						if (!soDienThoai.contains(soDienThoaiNhapVao)) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaKhachHang, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				cbSoDienThoai.setSelectedItem("");
			}
		} else if (obj.equals(cbDiaChiKhachHang)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxDiaChiKhachHang();
				int soPhanTuHienCo = cbDiaChiKhachHang.getItemCount();
				String tuHienTai = cbDiaChiKhachHang.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxDiaChiKhachHang();
						cbDiaChiKhachHang.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbDiaChiKhachHang.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbDiaChiKhachHang.addItem(cacUngVien.get(i));
							}
							cbDiaChiKhachHang.setSelectedItem(tuHienTai);
							cbDiaChiKhachHang.showPopup();
						} else if (soUngVien == 0) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbDiaChiKhachHang,
									"Không tìm thấy địa chỉ khách hàng nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxDiaChiKhachHang();
							cbDiaChiKhachHang.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbDiaChiKhachHang, "Không tìm thấy địa chỉ khách hàng nào",
							"Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbDiaChiKhachHang.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchDiaChiKhachHang)) {
			tableKhachHang.clearSelection();
			try {
				String diaChiKhachHangNhapVao = cbDiaChiKhachHang.getSelectedItem().toString().trim();
				if (!diaChiKhachHangNhapVao.equalsIgnoreCase("")) {
					Iterator<KhachHang> it = danhSachTatCaKhachHang.iterator();
					while (it.hasNext()) {
						KhachHang khachHang = (KhachHang) it.next();
						String diaChiKhachHang = khachHang.getDiaChi();
						if (!diaChiKhachHang.toLowerCase().trim().contains(diaChiKhachHangNhapVao.toLowerCase())) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaKhachHang, soPhanTuMuonHienThi);
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null,
							"Bạn cần nhập địa chỉ khách hàng và nhấn Enter trước khi tìm kiếm", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				}
			} catch (NullPointerException e1) {
				cbDiaChiKhachHang.setSelectedItem("");
			}
		} else if (obj.equals(btnNextPage)) {
			tableKhachHang.clearSelection();
			capNhatDuLieuTrenBang_Next(danhSachTatCaKhachHang, soPhanTuMuonHienThi);
		} else if (obj.equals(btnPreviousPage)) {
			tableKhachHang.clearSelection();
			capNhatDuLieuTrenBang_Previous(danhSachTatCaKhachHang, soPhanTuMuonHienThi);
		} else if (obj.equals(btnLastPage)) {
			tableKhachHang.clearSelection();
			capNhatDuLieuTrenBang_Last(danhSachTatCaKhachHang, soPhanTuMuonHienThi);
		} else if (obj.equals(btnFirstPage)) {
			tableKhachHang.clearSelection();
			capNhatDuLieuTrenBang_Init(danhSachTatCaKhachHang, soPhanTuMuonHienThi);
		} else if (obj.equals(btnReset)) {
			tableKhachHang.clearSelection();
			KhachHang_DAO khachhang_dao = new KhachHang_DAO();
			danhSachTatCaKhachHang = khachhang_dao.layTatCaKhachHang();
			capNhatDuLieuTrenBang_Init(danhSachTatCaKhachHang, soPhanTuMuonHienThi);
			xoaTrang();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int row = tableKhachHang.getSelectedRow();
		fillForm(row);
	}
}
