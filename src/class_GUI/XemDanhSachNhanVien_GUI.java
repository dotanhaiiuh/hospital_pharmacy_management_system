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

import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import class_DAO.NhanVien_DAO;
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
import javax.swing.border.EtchedBorder;

public class XemDanhSachNhanVien_GUI extends JFrame implements WindowListener, ActionListener, ListSelectionListener {
	private JDatePickerImpl datePickerNgaySinh;
	private Properties p;
	private UtilDateModel model_ngaysinh;
	private JDatePanelImpl datePanelNgaySinh;
	private UtilDateModel model_ngayvaolam;
	private JDatePanelImpl datePanelNgayVaoLam;
	private JDatePickerImpl datePickerNgayVaoLam;
	private DefaultTableModel modelNhanVien;
	private JTable tableNhanVien;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	private JComboBox cbChucVu;
	private JComboBox cbTinhTrang;

	private JButton btnHome;

	private NhanVien emp;
	private ArrayList<NhanVien> danhSachTatCaNhanVien;
	private final int soPhanTuMuonHienThi = 21;

	private JComboBox cbMaNhanVien;
	private JButton btnSearchMaNhanVien;
	private JLabel lblMaNhanVien;
	private JComboBox cbSoDienThoai;
	private JButton btnSearchSoDienThoai;
	private JLabel lblSoDienThoai;
	private JComboBox cbDiaChiNhanVien;
	private JButton btnSearchDiaChiNhanVien;
	private JButton btnSearchNgaySinh;
	private JComboBox cbGioiTinh;
	private JButton btnSearchNgayVaoLam;
	private JButton btnSearchGioiTinh;
	private JButton btnSearchChucVu;
	private JButton btnSearchTinhTrang;
	private JButton btnXemChiTiet;
	private JButton btnReset;
	private JButton btnFirstPage;
	private JButton btnPreviousPage;
	private JLabel lblPageIndex;
	private JButton btnNextPage;
	private JButton btnLastPage;

	private NhanVien taoNhanVien() {
		int row = tableNhanVien.getSelectedRow();
		String maNV = String.valueOf(tableNhanVien.getValueAt(row, 1));
		NhanVien nhanVien = new NhanVien(maNV);
		if (danhSachTatCaNhanVien.contains(nhanVien)) {
			return danhSachTatCaNhanVien.get(danhSachTatCaNhanVien.indexOf(nhanVien));
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
		modelNhanVien.getDataVector().removeAllElements();
	}

	private ArrayList<NhanVien> layDuLieuTrongTrang(ArrayList<NhanVien> danhSachNV, int soTrangMuonLay, int lastPage,
			int soPhanTuMuonHienThi) {
		ArrayList<NhanVien> kq = new ArrayList<NhanVien>();
		if (soTrangMuonLay < lastPage) {
			kq = new ArrayList<NhanVien>(
					danhSachNV.subList(soTrangMuonLay, (soTrangMuonLay * soPhanTuMuonHienThi) + 1));
		} else if (soTrangMuonLay == lastPage) {
			kq = new ArrayList<NhanVien>(danhSachNV.subList((lastPage - 1) * soPhanTuMuonHienThi, danhSachNV.size()));
		}
		return kq;
	}

	private void capNhatDuLieuTrenBang_Init(ArrayList<NhanVien> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			xoaHetDuLieuTrenTableModel();
			int soThuTu = 1;
			for (NhanVien nhanVien : danhSachMuonCapNhat) {
				modelNhanVien.addRow(new Object[] { soThuTu++, nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(),
						sdf.format(nhanVien.getNgaySinh()), sdf.format(nhanVien.getNgayVaoLam()), nhanVien.getChucVu(),
						nhanVien.getGioiTinh(), nhanVien.getTinhTrang(), nhanVien.getSoCMND(), nhanVien.getEmail(),
						nhanVien.getSoDienThoai(), nhanVien.getDiaChi(), nhanVien.getTaiKhoan().getTrangThai() });
			}
			if (lengthArray > 0) {
				double countPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
				lblPageIndex.setText("1" + "/" + String.valueOf((int) countPage));
			} else {
				lblPageIndex.setText("1" + "/" + "1");
			}
			if (lengthArray < soPhanTuMuonHienThi) {
				modelNhanVien.setRowCount(lengthArray);
			} else {
				modelNhanVien.setRowCount(soPhanTuMuonHienThi);
			}
			tableNhanVien.setModel(modelNhanVien);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenBang_Search(ArrayList<NhanVien> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			int soThuTu = 1;
			xoaHetDuLieuTrenTableModel();
			for (NhanVien nhanVien : danhSachMuonCapNhat) {
				modelNhanVien.addRow(new Object[] { soThuTu++, nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(),
						sdf.format(nhanVien.getNgaySinh()), sdf.format(nhanVien.getNgayVaoLam()), nhanVien.getChucVu(),
						nhanVien.getGioiTinh(), nhanVien.getTinhTrang(), nhanVien.getSoCMND(), nhanVien.getEmail(),
						nhanVien.getSoDienThoai(), nhanVien.getDiaChi(), nhanVien.getTaiKhoan().getTrangThai() });
			}
			lblPageIndex.setText("1" + "/" + "1");
			modelNhanVien.setRowCount(lengthArray);
			tableNhanVien.setModel(modelNhanVien);
			capNhatTatCaComboBox();
		}
		else {
			lblPageIndex.setText("1" + "/" + "1");
			modelNhanVien.setRowCount(0);
			tableNhanVien.setModel(modelNhanVien);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenBang_Next(ArrayList<NhanVien> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			String string_SoTrangHienTai = lblPageIndex.getText().trim().split("/")[0];
			int soTrangHienTai = Integer.parseInt(string_SoTrangHienTai) + 1;
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			if (soTrangHienTai <= lastPage) {
				int soThuTu = ((soTrangHienTai - 1) * soPhanTuMuonHienThi) + 1;
				xoaHetDuLieuTrenTableModel();
				ArrayList<NhanVien> danhSachNV = layDuLieuTrongTrang(danhSachMuonCapNhat, soTrangHienTai,
						(int) lastPage, soPhanTuMuonHienThi);
				for (NhanVien nhanVien : danhSachMuonCapNhat) {
					modelNhanVien.addRow(new Object[] { soThuTu++, nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(),
							sdf.format(nhanVien.getNgaySinh()), sdf.format(nhanVien.getNgayVaoLam()),
							nhanVien.getChucVu(), nhanVien.getGioiTinh(), nhanVien.getTinhTrang(), nhanVien.getSoCMND(),
							nhanVien.getEmail(), nhanVien.getSoDienThoai(), nhanVien.getDiaChi(),
							nhanVien.getTaiKhoan().getTrangThai() });
				}
				lblPageIndex.setText(String.valueOf(soTrangHienTai) + "/" + String.valueOf((int) lastPage));
				modelNhanVien.setRowCount(danhSachNV.size());
				tableNhanVien.setModel(modelNhanVien);
				capNhatTatCaComboBox();
			}
		}
	}

	private void capNhatDuLieuTrenBang_Previous(ArrayList<NhanVien> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			String soThuTuDauTien = String.valueOf(tableNhanVien.getValueAt(0, 0));
			String string_SoTrangHienTai = lblPageIndex.getText().trim().split("/")[0];
			int soTrangHienTai = Integer.parseInt(string_SoTrangHienTai) - 1;
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			if (soTrangHienTai > 0) {
				int soThuTu = Integer.parseInt(soThuTuDauTien) - soPhanTuMuonHienThi;
				xoaHetDuLieuTrenTableModel();
				ArrayList<NhanVien> danhSachNV = layDuLieuTrongTrang(danhSachMuonCapNhat, soTrangHienTai,
						(int) lastPage, soPhanTuMuonHienThi);
				for (NhanVien nhanVien : danhSachMuonCapNhat) {
					modelNhanVien.addRow(new Object[] { soThuTu++, nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(),
							sdf.format(nhanVien.getNgaySinh()), sdf.format(nhanVien.getNgayVaoLam()),
							nhanVien.getChucVu(), nhanVien.getGioiTinh(), nhanVien.getTinhTrang(), nhanVien.getSoCMND(),
							nhanVien.getEmail(), nhanVien.getSoDienThoai(), nhanVien.getDiaChi(),
							nhanVien.getTaiKhoan().getTrangThai() });
				}
				lblPageIndex.setText(String.valueOf(soTrangHienTai) + "/" + String.valueOf((int) lastPage));
				modelNhanVien.setRowCount(danhSachNV.size());
				tableNhanVien.setModel(modelNhanVien);
				capNhatTatCaComboBox();
			}
		}
	}

	private void capNhatDuLieuTrenBang_Last(ArrayList<NhanVien> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			int last_index = (int) (soPhanTuMuonHienThi * (lastPage - 1));
			int soThuTu = last_index + 1;
			xoaHetDuLieuTrenTableModel();
			ArrayList<NhanVien> danhSachNV = new ArrayList<NhanVien>(
					danhSachMuonCapNhat.subList(last_index, lengthArray));
			for (NhanVien nhanVien : danhSachMuonCapNhat) {
				modelNhanVien.addRow(new Object[] { soThuTu++, nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(),
						sdf.format(nhanVien.getNgaySinh()), sdf.format(nhanVien.getNgayVaoLam()), nhanVien.getChucVu(),
						nhanVien.getGioiTinh(), nhanVien.getTinhTrang(), nhanVien.getSoCMND(), nhanVien.getEmail(),
						nhanVien.getSoDienThoai(), nhanVien.getDiaChi(), nhanVien.getTaiKhoan().getTrangThai() });
			}
			lblPageIndex.setText(String.valueOf((int) lastPage) + "/" + String.valueOf((int) lastPage));
			modelNhanVien.setRowCount(danhSachNV.size());
			tableNhanVien.setModel(modelNhanVien);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenComboBoxMaNhanVien() {
		cbMaNhanVien.removeAllItems();
		if (danhSachTatCaNhanVien.size() > 0) {
			ArrayList<String> danhSachNhanVien = new ArrayList<String>();
			for (NhanVien nhanVien : danhSachTatCaNhanVien) {
				String maNV = nhanVien.getMaNV();
				String tenNV = nhanVien.getHoTenNhanVien();
				String s = maNV + " - " + tenNV;
				if (!danhSachNhanVien.contains(s)) {
					danhSachNhanVien.add(s);
					cbMaNhanVien.addItem(s);
				}
			}
		}
		cbMaNhanVien.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxMaNhanVien() {
		ArrayList<String> danhSachNhanVien = new ArrayList<String>();
		for (NhanVien nhanVien : danhSachTatCaNhanVien) {
			String maNV = nhanVien.getMaNV();
			String tenNV = nhanVien.getHoTenNhanVien();
			String s = maNV + " - " + tenNV;
			if (!danhSachNhanVien.contains(s)) {
				danhSachNhanVien.add(s);
			}
		}
		return danhSachNhanVien;
	}

	private void capNhatDuLieuTrenComboBoxSoDienThoai() {
		cbSoDienThoai.removeAllItems();
		if (danhSachTatCaNhanVien.size() > 0) {
			ArrayList<String> danhSachSoDienThoai = new ArrayList<String>();
			for (NhanVien nhanVien : danhSachTatCaNhanVien) {
				String soDienThoai = nhanVien.getSoDienThoai();
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
		for (NhanVien nhanVien : danhSachTatCaNhanVien) {
			String soDienThoai = nhanVien.getSoDienThoai();
			if (!danhSachSoDienThoai.contains(soDienThoai)) {
				danhSachSoDienThoai.add(soDienThoai);
			}
		}
		return danhSachSoDienThoai;
	}

	private void capNhatDuLieuTrenComboBoxDiaChiNhanVien() {
		cbDiaChiNhanVien.removeAllItems();
		if (danhSachTatCaNhanVien.size() > 0) {
			ArrayList<String> danhSachDiaChiNhanVien = new ArrayList<String>();
			for (NhanVien nhanVien : danhSachTatCaNhanVien) {
				String diaChiNhanVien = nhanVien.getDiaChi();
				if (!danhSachDiaChiNhanVien.contains(diaChiNhanVien)) {
					danhSachDiaChiNhanVien.add(diaChiNhanVien);
					cbDiaChiNhanVien.addItem(diaChiNhanVien);
				}
			}
		}
		cbDiaChiNhanVien.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxDiaChiNhanVien() {
		ArrayList<String> danhSachDiaChiNhanVien = new ArrayList<String>();
		for (NhanVien nhanVien : danhSachTatCaNhanVien) {
			String diaChiNhanVien = nhanVien.getDiaChi();
			if (!danhSachDiaChiNhanVien.contains(diaChiNhanVien)) {
				danhSachDiaChiNhanVien.add(diaChiNhanVien);
			}
		}
		return danhSachDiaChiNhanVien;
	}

	private void capNhatDuLieuTrenComboBoxGioiTinh() {
		cbGioiTinh.removeAllItems();
		if (danhSachTatCaNhanVien.size() > 0) {
			ArrayList<String> cacGioiTinh = new ArrayList<String>();
			for (NhanVien nhanVien : danhSachTatCaNhanVien) {
				String gioiTinh = nhanVien.getGioiTinh();
				if (!cacGioiTinh.contains(gioiTinh)) {
					cacGioiTinh.add(gioiTinh);
					cbGioiTinh.addItem(gioiTinh);
				}
			}
		}
		cbGioiTinh.setSelectedIndex(-1);
	}

	private void capNhatDuLieuTrenComboBoxChucVu() {
		cbChucVu.removeAllItems();
		if (danhSachTatCaNhanVien.size() > 0) {
			ArrayList<String> cacChucVu = new ArrayList<String>();
			for (NhanVien nhanVien : danhSachTatCaNhanVien) {
				String chucVu = nhanVien.getChucVu();
				if (!cacChucVu.contains(chucVu)) {
					cacChucVu.add(chucVu);
					cbChucVu.addItem(chucVu);
				}
			}
		}
		cbChucVu.setSelectedIndex(-1);
	}

	private void capNhatDuLieuTrenComboBoxTinhTrang() {
		cbTinhTrang.removeAllItems();
		if (danhSachTatCaNhanVien.size() > 0) {
			ArrayList<String> cacTinhTrang = new ArrayList<String>();
			for (NhanVien nhanVien : danhSachTatCaNhanVien) {
				String tinhTrang = nhanVien.getTinhTrang();
				if (!cacTinhTrang.contains(tinhTrang)) {
					cacTinhTrang.add(tinhTrang);
					cbTinhTrang.addItem(tinhTrang);
				}
			}
		}
		cbTinhTrang.setSelectedIndex(-1);
	}

	private void capNhatTatCaComboBox() {
		capNhatDuLieuTrenComboBoxMaNhanVien();
		capNhatDuLieuTrenComboBoxGioiTinh();
		capNhatDuLieuTrenComboBoxChucVu();
		capNhatDuLieuTrenComboBoxTinhTrang();
		capNhatDuLieuTrenComboBoxSoDienThoai();
		capNhatDuLieuTrenComboBoxDiaChiNhanVien();
	}

	private void xoaTrang() {
		cbMaNhanVien.setSelectedItem("");
		datePickerNgaySinh.getJFormattedTextField().setText("");
		datePickerNgayVaoLam.getJFormattedTextField().setText("");
		cbGioiTinh.setSelectedIndex(-1);
		cbChucVu.setSelectedIndex(-1);
		cbTinhTrang.setSelectedIndex(-1);
		cbSoDienThoai.setSelectedItem("");
		cbDiaChiNhanVien.setSelectedItem("");
		cbMaNhanVien.requestFocus();
	}

	private void fillForm(int row) {
		if (row != -1) {
			String maNV = String.valueOf(tableNhanVien.getValueAt(row, 1));
			NhanVien nhanVien = new NhanVien(maNV);
			ArrayList<NhanVien> dsNhanVien = danhSachTatCaNhanVien;
			if (dsNhanVien.contains(nhanVien)) {
				nhanVien = dsNhanVien.get(danhSachTatCaNhanVien.indexOf(nhanVien));
				cbMaNhanVien.removeAllItems();
				cbMaNhanVien.addItem(maNV);
				cbMaNhanVien.setSelectedItem(maNV);

				Date ngaySinh = nhanVien.getNgaySinh();
				model_ngaysinh.setValue(ngaySinh);
				datePickerNgaySinh.getJFormattedTextField().setText(sdf.format(ngaySinh));

				Date ngayVaoLam = nhanVien.getNgayVaoLam();
				model_ngayvaolam.setValue(ngayVaoLam);
				datePickerNgayVaoLam.getJFormattedTextField().setText(sdf.format(ngayVaoLam));

				cbGioiTinh.setSelectedItem(nhanVien.getGioiTinh());
				cbChucVu.setSelectedItem(nhanVien.getChucVu());
				cbTinhTrang.setSelectedItem(nhanVien.getTinhTrang());

				String soDienThoai = nhanVien.getSoDienThoai();
				cbSoDienThoai.removeAllItems();
				cbSoDienThoai.addItem(soDienThoai);
				cbSoDienThoai.setSelectedItem(soDienThoai);

				String diaChi = nhanVien.getDiaChi();
				cbDiaChiNhanVien.removeAllItems();
				cbDiaChiNhanVien.addItem(diaChi);
				cbDiaChiNhanVien.setSelectedItem(diaChi);
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public XemDanhSachNhanVien_GUI(NhanVien nv) {
		emp = nv;
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\list-employee.png"));
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

		btnXemChiTiet = new JButton("Xem thông tin chi tiết về nhân viên");
		btnXemChiTiet.setBackground(Color.WHITE);
		btnXemChiTiet.setIcon(new ImageIcon("data\\icons\\more-details-16.png"));
		btnXemChiTiet.setFocusable(false);
		panel.add(btnXemChiTiet);
		btnXemChiTiet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableNhanVien.getSelectedRow();
				if (row != -1) {
					XemChiTietNhanVien_GUI xemChiTietNhanVien = new XemChiTietNhanVien_GUI(taoNhanVien(), emp);
					xemChiTietNhanVien.setVisible(true);
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn cần chọn nhân viên muốn xem chi tiết", "Cảnh báo",
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
		lblTitle.setIcon(new ImageIcon("data\\icons\\list-employee-title.png"));
		lblTitle.setBounds(428, 10, 785, 89);
		panel_2.add(lblTitle);

		JPanel panel_chart = new JPanel();
		panel_chart.setBackground(Color.WHITE);
		panel_chart.setBounds(-13, 109, 1535, 645);
		panel_chart.setLayout(null);
		panel_2.add(panel_chart);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(23, 10, 1502, 248);
		panel_3.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_chart.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblThongTinNhanVien = new JLabel("Thông tin nhân viên");
		lblThongTinNhanVien.setForeground(SystemColor.textHighlight);
		lblThongTinNhanVien.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinNhanVien.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinNhanVien.setBounds(20, 22, 1459, 35);
		panel_3.add(lblThongTinNhanVien);

		lblMaNhanVien = new JLabel("Tìm mã nhân viên:");
		lblMaNhanVien.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaNhanVien.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaNhanVien.setBounds(10, 68, 149, 26);
		panel_3.add(lblMaNhanVien);

		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgaySinh.setBounds(761, 68, 114, 26);
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

		JLabel lblNgayVaoLam = new JLabel("Ngày vào làm:");
		lblNgayVaoLam.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgayVaoLam.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgayVaoLam.setBounds(20, 114, 139, 26);
		panel_3.add(lblNgayVaoLam);

		model_ngayvaolam = new UtilDateModel();
		datePanelNgayVaoLam = new JDatePanelImpl(model_ngayvaolam, p);
		datePickerNgayVaoLam = new JDatePickerImpl(datePanelNgayVaoLam, new DateLabelFormatter());
		SpringLayout springLayout_2 = (SpringLayout) datePickerNgayVaoLam.getLayout();
		springLayout_2.putConstraint(SpringLayout.SOUTH, datePickerNgayVaoLam.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, datePickerNgayVaoLam);
		datePickerNgayVaoLam.setBounds(169, 114, 509, 27);
		datePickerNgayVaoLam.setFocusable(false);
		panel_3.add(datePickerNgayVaoLam);

		JLabel lblChucVu = new JLabel("Chức vụ:");
		lblChucVu.setHorizontalAlignment(SwingConstants.RIGHT);
		lblChucVu.setFont(new Font("Arial", Font.BOLD, 16));
		lblChucVu.setBounds(20, 157, 139, 26);
		panel_3.add(lblChucVu);

		cbChucVu = new JComboBox();
		cbChucVu.setFont(new Font("Arial", Font.BOLD, 16));
		cbChucVu.setBackground(Color.WHITE);
		cbChucVu.setBounds(169, 157, 509, 29);
		panel_3.add(cbChucVu);

		JLabel lblTinhTrang = new JLabel("Tình trạng:");
		lblTinhTrang.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTinhTrang.setFont(new Font("Arial", Font.BOLD, 16));
		lblTinhTrang.setBounds(761, 160, 114, 26);
		panel_3.add(lblTinhTrang);

		cbTinhTrang = new JComboBox();
		cbTinhTrang.setFont(new Font("Arial", Font.BOLD, 16));
		cbTinhTrang.setBackground(Color.WHITE);
		cbTinhTrang.setBounds(885, 160, 566, 29);
		panel_3.add(cbTinhTrang);

		cbDiaChiNhanVien = new JComboBox();
		cbDiaChiNhanVien.setEditable(true);
		cbDiaChiNhanVien.setSelectedIndex(-1);
		cbDiaChiNhanVien.setFont(new Font("Arial", Font.PLAIN, 16));
		cbDiaChiNhanVien.setBackground(Color.WHITE);
		cbDiaChiNhanVien.setBounds(885, 206, 566, 29);
		panel_3.add(cbDiaChiNhanVien);

		btnSearchNgaySinh = new JButton();
		btnSearchNgaySinh.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchNgaySinh.setToolTipText("Tìm kiếm nhân viên theo ngày sinh");
		btnSearchNgaySinh.setFocusable(false);
		btnSearchNgaySinh.setBorder(BorderFactory.createEmptyBorder());
		btnSearchNgaySinh.setBackground(Color.WHITE);
		btnSearchNgaySinh.setBounds(1461, 63, 31, 31);
		panel_3.add(btnSearchNgaySinh);

		cbMaNhanVien = new JComboBox();
		cbMaNhanVien.setEditable(true);
		cbMaNhanVien.setSelectedIndex(-1);
		cbMaNhanVien.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaNhanVien.setBackground(Color.WHITE);
		cbMaNhanVien.setBounds(169, 67, 509, 29);
		panel_3.add(cbMaNhanVien);

		btnSearchMaNhanVien = new JButton();
		btnSearchMaNhanVien.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchMaNhanVien.setToolTipText("Tìm kiếm nhân viên theo mã nhân viên");
		btnSearchMaNhanVien.setFocusable(false);
		btnSearchMaNhanVien.setBorder(BorderFactory.createEmptyBorder());
		btnSearchMaNhanVien.setBackground(Color.WHITE);
		btnSearchMaNhanVien.setBounds(688, 67, 31, 31);
		panel_3.add(btnSearchMaNhanVien);

		btnSearchNgayVaoLam = new JButton();
		btnSearchNgayVaoLam.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchNgayVaoLam.setToolTipText("Tìm kiếm nhân viên theo ngày vào làm");
		btnSearchNgayVaoLam.setFocusable(false);
		btnSearchNgayVaoLam.setBorder(BorderFactory.createEmptyBorder());
		btnSearchNgayVaoLam.setBackground(Color.WHITE);
		btnSearchNgayVaoLam.setBounds(688, 111, 31, 31);
		panel_3.add(btnSearchNgayVaoLam);

		btnSearchChucVu = new JButton();
		btnSearchChucVu.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchChucVu.setToolTipText("Tìm kiếm nhân viên theo chức vụ");
		btnSearchChucVu.setFocusable(false);
		btnSearchChucVu.setBorder(BorderFactory.createEmptyBorder());
		btnSearchChucVu.setBackground(Color.WHITE);
		btnSearchChucVu.setBounds(688, 157, 31, 31);
		panel_3.add(btnSearchChucVu);

		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGioiTinh.setFont(new Font("Arial", Font.BOLD, 16));
		lblGioiTinh.setBounds(761, 116, 114, 26);
		panel_3.add(lblGioiTinh);

		cbGioiTinh = new JComboBox();
		cbGioiTinh.setSelectedIndex(-1);
		cbGioiTinh.setFont(new Font("Arial", Font.BOLD, 16));
		cbGioiTinh.setBackground(Color.WHITE);
		cbGioiTinh.setBounds(885, 112, 566, 29);
		panel_3.add(cbGioiTinh);

		btnSearchGioiTinh = new JButton();
		btnSearchGioiTinh.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchGioiTinh.setToolTipText("Tìm kiếm nhân viên theo giới tính");
		btnSearchGioiTinh.setFocusable(false);
		btnSearchGioiTinh.setBorder(BorderFactory.createEmptyBorder());
		btnSearchGioiTinh.setBackground(Color.WHITE);
		btnSearchGioiTinh.setBounds(1461, 109, 31, 31);
		panel_3.add(btnSearchGioiTinh);

		btnSearchTinhTrang = new JButton();
		btnSearchTinhTrang.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchTinhTrang.setToolTipText("Tìm kiếm nhân viên theo tình trạng làm việc");
		btnSearchTinhTrang.setFocusable(false);
		btnSearchTinhTrang.setBorder(BorderFactory.createEmptyBorder());
		btnSearchTinhTrang.setBackground(Color.WHITE);
		btnSearchTinhTrang.setBounds(1461, 157, 31, 31);
		panel_3.add(btnSearchTinhTrang);

		btnSearchDiaChiNhanVien = new JButton();
		btnSearchDiaChiNhanVien.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchDiaChiNhanVien.setToolTipText("Tìm kiếm nhân viên theo địa chỉ");
		btnSearchDiaChiNhanVien.setFocusable(false);
		btnSearchDiaChiNhanVien.setBorder(BorderFactory.createEmptyBorder());
		btnSearchDiaChiNhanVien.setBackground(Color.WHITE);
		btnSearchDiaChiNhanVien.setBounds(1461, 204, 31, 31);
		panel_3.add(btnSearchDiaChiNhanVien);

		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiaChi.setFont(new Font("Arial", Font.BOLD, 16));
		lblDiaChi.setBounds(761, 206, 114, 26);
		panel_3.add(lblDiaChi);

		lblSoDienThoai = new JLabel("Tìm số điện thoại:");
		lblSoDienThoai.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoDienThoai.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoDienThoai.setBounds(10, 202, 149, 26);
		panel_3.add(lblSoDienThoai);

		cbSoDienThoai = new JComboBox();
		cbSoDienThoai.setEditable(true);
		cbSoDienThoai.setSelectedIndex(-1);
		cbSoDienThoai.setFont(new Font("Arial", Font.PLAIN, 16));
		cbSoDienThoai.setBackground(Color.WHITE);
		cbSoDienThoai.setBounds(169, 201, 509, 29);
		panel_3.add(cbSoDienThoai);

		btnSearchSoDienThoai = new JButton();
		btnSearchSoDienThoai.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchSoDienThoai.setToolTipText("Tìm kiếm nhân viên theo số điện thoại");
		btnSearchSoDienThoai.setFocusable(false);
		btnSearchSoDienThoai.setBorder(BorderFactory.createEmptyBorder());
		btnSearchSoDienThoai.setBackground(Color.WHITE);
		btnSearchSoDienThoai.setBounds(688, 201, 31, 31);
		panel_3.add(btnSearchSoDienThoai);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 268, 1502, 333);
		panel_chart.add(scrollPane);

		String[] headers = { "STT", "Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Ngày vào làm", "Chức vụ",
				"Giới Tính", "Tình trạng", "Số CMND", "Email", "Số điện thoại", "Địa chỉ", "Trạng thái tài khoản" };
		modelNhanVien = new DefaultTableModel(headers, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tableNhanVien = new JTable(modelNhanVien);
		tableNhanVien.getTableHeader().setBackground(Color.CYAN);
		tableNhanVien.getTableHeader().setFont(new Font(null, Font.PLAIN, 14));
		tableNhanVien.setSelectionBackground(Color.YELLOW);
		tableNhanVien.setSelectionForeground(Color.RED);
		tableNhanVien.setFont(new Font(null, Font.PLAIN, 14));
		tableNhanVien.setRowHeight(22);
		tableNhanVien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableNhanVien.setToolTipText("Nhấn đúp chuột vào dòng bất kỳ để xem thông tin chi tiết về nhân viên");
		scrollPane.setViewportView(tableNhanVien);
		tableNhanVien.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					XemChiTietNhanVien_GUI xemChiTietNhanVien = new XemChiTietNhanVien_GUI(taoNhanVien(), emp);
					xemChiTietNhanVien.setVisible(true);
				}
			}
		});
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(240, 240, 240));

		btnFirstPage = new JButton("");
		btnFirstPage.setIcon(new ImageIcon("data\\icons\\first-page-32.png"));
		btnFirstPage.setBounds(673, 611, 24, 24);
		panel_chart.add(btnFirstPage);

		btnPreviousPage = new JButton("");
		btnPreviousPage.setIcon(new ImageIcon("data\\icons\\previous-page-32.png"));
		btnPreviousPage.setBounds(707, 611, 24, 24);
		panel_chart.add(btnPreviousPage);

		lblPageIndex = new JLabel();
		lblPageIndex.setHorizontalAlignment(SwingConstants.CENTER);
		lblPageIndex.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPageIndex.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPageIndex.setBounds(741, 610, 67, 25);
		panel_chart.add(lblPageIndex);

		btnNextPage = new JButton("");
		btnNextPage.setIcon(new ImageIcon("data\\icons\\next-page-32.png"));
		btnNextPage.setBounds(818, 610, 24, 24);
		panel_chart.add(btnNextPage);

		btnLastPage = new JButton("");
		btnLastPage.setIcon(new ImageIcon("data\\icons\\last-index-32.png"));
		btnLastPage.setBounds(852, 610, 24, 24);
		panel_chart.add(btnLastPage);

		setTitle("Xem danh sách nhân viên");
		Toolkit tk = Toolkit.getDefaultToolkit();
		setSize(tk.getScreenSize().width, tk.getScreenSize().height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);

		NhanVien_DAO nhanvien_dao = new NhanVien_DAO();
		danhSachTatCaNhanVien = nhanvien_dao.layTatCaNhanVien();
		if (danhSachTatCaNhanVien != null) {
			capNhatDuLieuTrenBang_Init(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
		} else {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Chưa có nhân viên nào", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		}

		cbMaNhanVien.addActionListener(this);
		btnSearchMaNhanVien.addActionListener(this);
		btnSearchNgaySinh.addActionListener(this);
		btnSearchNgayVaoLam.addActionListener(this);
		btnSearchGioiTinh.addActionListener(this);
		btnSearchChucVu.addActionListener(this);
		btnSearchTinhTrang.addActionListener(this);
		cbSoDienThoai.addActionListener(this);
		btnSearchSoDienThoai.addActionListener(this);
		cbDiaChiNhanVien.addActionListener(this);
		btnSearchDiaChiNhanVien.addActionListener(this);
		btnNextPage.addActionListener(this);
		btnPreviousPage.addActionListener(this);
		btnLastPage.addActionListener(this);
		btnFirstPage.addActionListener(this);
		btnReset.addActionListener(this);
		tableNhanVien.getSelectionModel().addListSelectionListener(this);
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
		if (obj.equals(cbMaNhanVien)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxMaNhanVien();
				int soPhanTuHienCo = cbMaNhanVien.getItemCount();
				String tuHienTai = cbMaNhanVien.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxMaNhanVien();
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
							JOptionPane.showMessageDialog(cbMaNhanVien,
									"Không tìm thấy mã nhân viên hoặc tên nhân viên nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxMaNhanVien();
							cbMaNhanVien.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaNhanVien, "Không tìm thấy mã nhân viên hoặc tên nhân viên nào",
							"Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbMaNhanVien.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchMaNhanVien)) {
			tableNhanVien.clearSelection();
			try {
				String maNVNhapVao = cbMaNhanVien.getSelectedItem().toString().trim();
				if (!maNVNhapVao.equalsIgnoreCase("")) {
					Iterator<NhanVien> it = danhSachTatCaNhanVien.iterator();
					while (it.hasNext()) {
						NhanVien nhanVien = (NhanVien) it.next();
						String maNV = nhanVien.getMaNV() + " - " + nhanVien.getHoTenNhanVien();
						if (!maNV.toLowerCase().contains(maNVNhapVao.toLowerCase())) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				e1.printStackTrace();
			}
		} else if (obj.equals(btnSearchNgaySinh)) {
			tableNhanVien.clearSelection();
			String string_NgaySinhDangChon = datePickerNgaySinh.getJFormattedTextField().getText();
			if (!string_NgaySinhDangChon.equalsIgnoreCase("")) {
				LocalDate ngaySinhDangChon = LocalDate.parse(string_NgaySinhDangChon, dtf);
				Iterator<NhanVien> it = danhSachTatCaNhanVien.iterator();
				while (it.hasNext()) {
					NhanVien nhanVien = (NhanVien) it.next();
					String string_NgaySinh = sdf.format(nhanVien.getNgaySinh());
					LocalDate ngaySinh = LocalDate.parse(string_NgaySinh, dtf);
					if (!ngaySinh.equals(ngaySinhDangChon)) {
						it.remove();
					}
				}
				capNhatDuLieuTrenBang_Search(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
			}
		} else if (obj.equals(btnSearchNgayVaoLam)) {
			tableNhanVien.clearSelection();
			String string_NgayVaoLamDangChon = datePickerNgayVaoLam.getJFormattedTextField().getText();
			if (!string_NgayVaoLamDangChon.equalsIgnoreCase("")) {
				LocalDate ngayVaoLamDangChon = LocalDate.parse(string_NgayVaoLamDangChon, dtf);
				Iterator<NhanVien> it = danhSachTatCaNhanVien.iterator();
				while (it.hasNext()) {
					NhanVien nhanVien = (NhanVien) it.next();
					String string_NgayVaoLam = sdf.format(nhanVien.getNgayVaoLam());
					LocalDate ngayVaoLam = LocalDate.parse(string_NgayVaoLam, dtf);
					if (!ngayVaoLam.equals(ngayVaoLamDangChon)) {
						it.remove();
					}
				}
				capNhatDuLieuTrenBang_Search(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
			}
		} else if (obj.equals(btnSearchGioiTinh)) {
			tableNhanVien.clearSelection();
			try {
				String gioiTinhNhapVao = cbGioiTinh.getSelectedItem().toString().trim();
				if (!gioiTinhNhapVao.equalsIgnoreCase("")) {
					Iterator<NhanVien> it = danhSachTatCaNhanVien.iterator();
					while (it.hasNext()) {
						NhanVien nhanVien = (NhanVien) it.next();
						String gioiTinh = nhanVien.getGioiTinh();
						if (!gioiTinh.equalsIgnoreCase(gioiTinhNhapVao)) {
							it.remove();
						}
					}
				}
				capNhatDuLieuTrenBang_Search(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
			} catch (Exception e1) {
				cbGioiTinh.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchChucVu)) {
			tableNhanVien.clearSelection();
			try {
				String chucVuNhapVao = cbChucVu.getSelectedItem().toString().trim();
				if (!chucVuNhapVao.equalsIgnoreCase("")) {
					Iterator<NhanVien> it = danhSachTatCaNhanVien.iterator();
					while (it.hasNext()) {
						NhanVien nhanVien = (NhanVien) it.next();
						String chucVu = nhanVien.getChucVu();
						if (!chucVu.equalsIgnoreCase(chucVuNhapVao)) {
							it.remove();
						}
					}
				}
				capNhatDuLieuTrenBang_Search(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
			} catch (Exception e1) {
				cbChucVu.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchTinhTrang)) {
			tableNhanVien.clearSelection();
			try {
				String tinhTrangNhapVao = cbTinhTrang.getSelectedItem().toString().trim();
				if (!tinhTrangNhapVao.equalsIgnoreCase("")) {
					Iterator<NhanVien> it = danhSachTatCaNhanVien.iterator();
					while (it.hasNext()) {
						NhanVien nhanVien = (NhanVien) it.next();
						String tinhTrang = nhanVien.getTinhTrang();
						if (!tinhTrang.equalsIgnoreCase(tinhTrangNhapVao)) {
							it.remove();
						}
					}
				}
				capNhatDuLieuTrenBang_Search(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
			} catch (Exception e1) {
				cbTinhTrang.setSelectedItem("");
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
									"Không tìm thấy số điện thoại của nhân viên nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxSoDienThoai();
							cbSoDienThoai.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbSoDienThoai, "Không tìm thấy số điện thoại của nhân viên nào",
							"Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbSoDienThoai.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchSoDienThoai)) {
			tableNhanVien.clearSelection();
			try {
				String soDienThoaiNhapVao = cbSoDienThoai.getSelectedItem().toString().trim();
				if (!soDienThoaiNhapVao.equalsIgnoreCase("")) {
					Iterator<NhanVien> it = danhSachTatCaNhanVien.iterator();
					while (it.hasNext()) {
						NhanVien nhanVien = (NhanVien) it.next();
						String soDienThoai = nhanVien.getSoDienThoai();
						if (!soDienThoai.contains(soDienThoaiNhapVao)) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				cbSoDienThoai.setSelectedItem("");
			}
		} else if (obj.equals(cbDiaChiNhanVien)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxDiaChiNhanVien();
				int soPhanTuHienCo = cbDiaChiNhanVien.getItemCount();
				String tuHienTai = cbDiaChiNhanVien.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxDiaChiNhanVien();
						cbDiaChiNhanVien.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbDiaChiNhanVien.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbDiaChiNhanVien.addItem(cacUngVien.get(i));
							}
							cbDiaChiNhanVien.setSelectedItem(tuHienTai);
							cbDiaChiNhanVien.showPopup();
						} else if (soUngVien == 0) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbDiaChiNhanVien,
									"Không tìm thấy địa chỉ nhân viên nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxDiaChiNhanVien();
							cbDiaChiNhanVien.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbDiaChiNhanVien, "Không tìm thấy địa chỉ nhân viên nào", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbDiaChiNhanVien.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchDiaChiNhanVien)) {
			tableNhanVien.clearSelection();
			try {
				String diaChiNhanVienNhapVao = cbDiaChiNhanVien.getSelectedItem().toString().trim();
				if (!diaChiNhanVienNhapVao.equalsIgnoreCase("")) {
					Iterator<NhanVien> it = danhSachTatCaNhanVien.iterator();
					while (it.hasNext()) {
						NhanVien nhanVien = (NhanVien) it.next();
						String diaChiNhanVien = nhanVien.getDiaChi();
						if (!diaChiNhanVien.toLowerCase().trim().contains(diaChiNhanVienNhapVao.toLowerCase())) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null,
							"Bạn cần nhập địa chỉ nhân viên và nhấn Enter trước khi tìm kiếm", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				}
			} catch (NullPointerException e1) {
				cbDiaChiNhanVien.setSelectedItem("");
			}
		} else if (obj.equals(btnNextPage)) {
			tableNhanVien.clearSelection();
			capNhatDuLieuTrenBang_Next(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
		} else if (obj.equals(btnPreviousPage)) {
			tableNhanVien.clearSelection();
			capNhatDuLieuTrenBang_Previous(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
		} else if (obj.equals(btnLastPage)) {
			tableNhanVien.clearSelection();
			capNhatDuLieuTrenBang_Last(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
		} else if (obj.equals(btnFirstPage)) {
			tableNhanVien.clearSelection();
			capNhatDuLieuTrenBang_Init(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
		} else if (obj.equals(btnReset)) {
			tableNhanVien.clearSelection();
			NhanVien_DAO nhanvien_dao = new NhanVien_DAO();
			danhSachTatCaNhanVien = nhanvien_dao.layTatCaNhanVien();
			capNhatDuLieuTrenBang_Init(danhSachTatCaNhanVien, soPhanTuMuonHienThi);
			xoaTrang();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int row = tableNhanVien.getSelectedRow();
		fillForm(row);
	}
}
