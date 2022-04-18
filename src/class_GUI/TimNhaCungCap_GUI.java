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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import class_DAO.NhaCungCap_DAO;
import class_Entities.NhanVien;
import class_Entities.NhaCungCap;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.border.EtchedBorder;

public class TimNhaCungCap_GUI extends JFrame implements ActionListener, WindowListener, ListSelectionListener {
	private DefaultTableModel modelNhaCungCap;
	private JTable tableNhaCungCap;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	private JButton btnHome;
	private NhanVien emp;

	private JTextField txtNgayHienTai;

	private ArrayList<NhaCungCap> danhSachTatCaNhaCungCap;
	private final int soPhanTuMuonHienThi = 21;

	private JComboBox cbMaNCC;
	private JButton btnReset;

	private JComboBox cbTenNCC;
	private JLabel lblMaNhaCungCap;
	private JComboBox cbSoDienThoai;
	private JComboBox cbDiaChiNhaCungCap;
	private JButton btnXemChiTiet;
	private JButton btnSearchMaNCC;
	private JButton btnSearchTenNCC;
	private JLabel lblTenNhaCungCap;
	private JLabel lblSoDienThoai;
	private JButton btnSearchSoDienThoai;
	private JLabel lblDiaChiNhaCungCap;
	private JButton btnSearchDiaChiNhaCungCap;
	private String tenChucNangCanThucHienKhiTimKiem;
	private JButton btnFirstPage;
	private JButton btnPreviousPage;
	private JLabel lblPageIndex;
	private JButton btnNextPage;
	private JButton btnLastPage;

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
		modelNhaCungCap.getDataVector().removeAllElements();
	}

	private ArrayList<NhaCungCap> layDuLieuTrongTrang(ArrayList<NhaCungCap> danhSachNCC, int soTrangMuonLay,
			int lastPage, int soPhanTuMuonHienThi) {
		ArrayList<NhaCungCap> kq = new ArrayList<NhaCungCap>();
		if (soTrangMuonLay < lastPage) {
			kq = new ArrayList<NhaCungCap>(
					danhSachNCC.subList(soTrangMuonLay, (soTrangMuonLay * soPhanTuMuonHienThi) + 1));
		} else if (soTrangMuonLay == lastPage) {
			kq = new ArrayList<NhaCungCap>(
					danhSachNCC.subList((lastPage - 1) * soPhanTuMuonHienThi, danhSachNCC.size()));
		}
		return kq;
	}

	private void capNhatDuLieuTrenBang_Init(ArrayList<NhaCungCap> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			xoaHetDuLieuTrenTableModel();
			int soThuTu = 1;
			for (NhaCungCap nhaCungCap : danhSachMuonCapNhat) {
				modelNhaCungCap.addRow(new Object[] { soThuTu++, nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC(),
						nhaCungCap.getEmail(), nhaCungCap.getSoDienThoai(), nhaCungCap.getDiaChi() });
			}
			if (lengthArray > 0) {
				double countPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
				lblPageIndex.setText("1" + "/" + String.valueOf((int) countPage));
			} else {
				lblPageIndex.setText("1" + "/" + "1");
			}
			if (lengthArray < soPhanTuMuonHienThi) {
				modelNhaCungCap.setRowCount(lengthArray);
			} else {
				modelNhaCungCap.setRowCount(soPhanTuMuonHienThi);
			}
			tableNhaCungCap.setModel(modelNhaCungCap);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenBang_Search(ArrayList<NhaCungCap> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			int soThuTu = 1;
			xoaHetDuLieuTrenTableModel();
			for (NhaCungCap nhaCungCap : danhSachMuonCapNhat) {
				modelNhaCungCap.addRow(new Object[] { soThuTu++, nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC(),
						nhaCungCap.getEmail(), nhaCungCap.getSoDienThoai(), nhaCungCap.getDiaChi() });
			}
			lblPageIndex.setText("1" + "/" + "1");
			modelNhaCungCap.setRowCount(lengthArray);
			tableNhaCungCap.setModel(modelNhaCungCap);
			capNhatTatCaComboBox();
		}
		else {
			lblPageIndex.setText("1" + "/" + "1");
			modelNhaCungCap.setRowCount(0);
			tableNhaCungCap.setModel(modelNhaCungCap);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenBang_Next(ArrayList<NhaCungCap> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			String string_SoTrangHienTai = lblPageIndex.getText().trim().split("/")[0];
			int soTrangHienTai = Integer.parseInt(string_SoTrangHienTai) + 1;
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			if (soTrangHienTai <= lastPage) {
				int soThuTu = ((soTrangHienTai - 1) * soPhanTuMuonHienThi) + 1;
				xoaHetDuLieuTrenTableModel();
				ArrayList<NhaCungCap> danhSachNCC = layDuLieuTrongTrang(danhSachMuonCapNhat, soTrangHienTai,
						(int) lastPage, soPhanTuMuonHienThi);
				for (NhaCungCap nhaCungCap : danhSachMuonCapNhat) {
					modelNhaCungCap.addRow(new Object[] { soThuTu++, nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC(),
							nhaCungCap.getEmail(), nhaCungCap.getSoDienThoai(), nhaCungCap.getDiaChi() });
				}
				lblPageIndex.setText(String.valueOf(soTrangHienTai) + "/" + String.valueOf((int) lastPage));
				modelNhaCungCap.setRowCount(danhSachNCC.size());
				tableNhaCungCap.setModel(modelNhaCungCap);
				capNhatTatCaComboBox();
			}
		}
	}

	private void capNhatDuLieuTrenBang_Previous(ArrayList<NhaCungCap> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			String soThuTuDauTien = String.valueOf(tableNhaCungCap.getValueAt(0, 0));
			String string_SoTrangHienTai = lblPageIndex.getText().trim().split("/")[0];
			int soTrangHienTai = Integer.parseInt(string_SoTrangHienTai) - 1;
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			if (soTrangHienTai > 0) {
				int soThuTu = Integer.parseInt(soThuTuDauTien) - soPhanTuMuonHienThi;
				xoaHetDuLieuTrenTableModel();
				ArrayList<NhaCungCap> danhSachNCC = layDuLieuTrongTrang(danhSachMuonCapNhat, soTrangHienTai,
						(int) lastPage, soPhanTuMuonHienThi);
				for (NhaCungCap nhaCungCap : danhSachMuonCapNhat) {
					modelNhaCungCap.addRow(new Object[] { soThuTu++, nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC(),
							nhaCungCap.getEmail(), nhaCungCap.getSoDienThoai(), nhaCungCap.getDiaChi() });
				}
				lblPageIndex.setText(String.valueOf(soTrangHienTai) + "/" + String.valueOf((int) lastPage));
				modelNhaCungCap.setRowCount(danhSachNCC.size());
				tableNhaCungCap.setModel(modelNhaCungCap);
				capNhatTatCaComboBox();
			}
		}
	}

	private void capNhatDuLieuTrenBang_Last(ArrayList<NhaCungCap> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			int last_index = (int) (soPhanTuMuonHienThi * (lastPage - 1));
			int soThuTu = last_index + 1;
			xoaHetDuLieuTrenTableModel();
			ArrayList<NhaCungCap> danhSachNCC = new ArrayList<NhaCungCap>(
					danhSachMuonCapNhat.subList(last_index, lengthArray));
			for (NhaCungCap nhaCungCap : danhSachMuonCapNhat) {
				modelNhaCungCap.addRow(new Object[] { soThuTu++, nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC(),
						nhaCungCap.getEmail(), nhaCungCap.getSoDienThoai(), nhaCungCap.getDiaChi() });
			}
			lblPageIndex.setText(String.valueOf((int) lastPage) + "/" + String.valueOf((int) lastPage));
			modelNhaCungCap.setRowCount(danhSachNCC.size());
			tableNhaCungCap.setModel(modelNhaCungCap);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenComboBoxMaNhaCungCap() {
		cbMaNCC.removeAllItems();
		if (danhSachTatCaNhaCungCap.size() > 0) {
			ArrayList<String> danhSachMaNhaCungcap = new ArrayList<String>();
			for (NhaCungCap nhaCungCap : danhSachTatCaNhaCungCap) {
				String maNCC = nhaCungCap.getMaNCC();
				if (!danhSachMaNhaCungcap.contains(maNCC)) {
					danhSachMaNhaCungcap.add(maNCC);
					cbMaNCC.addItem(maNCC);
				}
			}
		}
		cbMaNCC.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxMaNhaCungCap() {
		ArrayList<String> danhSachMaNhaCungcap = new ArrayList<String>();
		for (NhaCungCap nhaCungCap : danhSachTatCaNhaCungCap) {
			String maNCC = nhaCungCap.getMaNCC();
			if (!danhSachMaNhaCungcap.contains(maNCC)) {
				danhSachMaNhaCungcap.add(maNCC);
			}
		}
		return danhSachMaNhaCungcap;
	}

	private void capNhatDuLieuTrenComboBoxTenNhaCungCap() {
		cbTenNCC.removeAllItems();
		if (danhSachTatCaNhaCungCap.size() > 0) {
			ArrayList<String> danhSachTenNhaCungCap = new ArrayList<String>();
			for (NhaCungCap nhaCungCap : danhSachTatCaNhaCungCap) {
				String tenNCC = nhaCungCap.getTenNCC();
				if (!danhSachTenNhaCungCap.contains(tenNCC)) {
					danhSachTenNhaCungCap.add(tenNCC);
					cbTenNCC.addItem(tenNCC);
				}
			}
		}
		cbTenNCC.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxTenNhaCungCap() {
		ArrayList<String> danhSachTenNhaCungCap = new ArrayList<String>();
		for (NhaCungCap nhaCungCap : danhSachTatCaNhaCungCap) {
			String tenNCC = nhaCungCap.getTenNCC();
			if (!danhSachTenNhaCungCap.contains(tenNCC)) {
				danhSachTenNhaCungCap.add(tenNCC);
			}
		}
		return danhSachTenNhaCungCap;
	}

	private void capNhatDuLieuTrenComboBoxSoDienThoai() {
		cbSoDienThoai.removeAllItems();
		if (danhSachTatCaNhaCungCap.size() > 0) {
			ArrayList<String> danhSachSoDienThoai = new ArrayList<String>();
			for (NhaCungCap nhaCungCap : danhSachTatCaNhaCungCap) {
				String soDienThoai = nhaCungCap.getSoDienThoai();
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
		for (NhaCungCap nhaCungCap : danhSachTatCaNhaCungCap) {
			String soDienThoai = nhaCungCap.getSoDienThoai();
			if (!danhSachSoDienThoai.contains(soDienThoai)) {
				danhSachSoDienThoai.add(soDienThoai);
			}
		}
		return danhSachSoDienThoai;
	}

	private void capNhatDuLieuTrenComboBoxDiaChiNhaCungCap() {
		cbDiaChiNhaCungCap.removeAllItems();
		if (danhSachTatCaNhaCungCap.size() > 0) {
			ArrayList<String> danhSachDiaChiNCC = new ArrayList<String>();
			for (NhaCungCap nhaCungCap : danhSachTatCaNhaCungCap) {
				String diaChiNhaCungCap = nhaCungCap.getDiaChi();
				if (!danhSachDiaChiNCC.contains(diaChiNhaCungCap)) {
					danhSachDiaChiNCC.add(diaChiNhaCungCap);
					cbDiaChiNhaCungCap.addItem(diaChiNhaCungCap);
				}
			}
		}
		cbDiaChiNhaCungCap.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxDiaChiNhaCungCap() {
		ArrayList<String> danhSachDiaChiNCC = new ArrayList<String>();
		for (NhaCungCap nhaCungCap : danhSachTatCaNhaCungCap) {
			String diaChiNhaCungCap = nhaCungCap.getDiaChi();
			if (!danhSachDiaChiNCC.contains(diaChiNhaCungCap)) {
				danhSachDiaChiNCC.add(diaChiNhaCungCap);
			}
		}
		return danhSachDiaChiNCC;
	}

	private void capNhatTatCaComboBox() {
		capNhatDuLieuTrenComboBoxMaNhaCungCap();
		capNhatDuLieuTrenComboBoxTenNhaCungCap();
		capNhatDuLieuTrenComboBoxSoDienThoai();
		capNhatDuLieuTrenComboBoxDiaChiNhaCungCap();
	}

	private NhaCungCap taoNhaCungCap() {
		int row = tableNhaCungCap.getSelectedRow();
		String maNCC = String.valueOf(tableNhaCungCap.getValueAt(row, 1));
		NhaCungCap nhaCungCap = new NhaCungCap(maNCC);
		if (danhSachTatCaNhaCungCap.contains(nhaCungCap)) {
			return danhSachTatCaNhaCungCap.get(danhSachTatCaNhaCungCap.indexOf(nhaCungCap));
		}
		return null;
	}

	private void xoaTrang() {
		cbMaNCC.setSelectedItem("");
		cbTenNCC.setSelectedItem("");
		cbSoDienThoai.setSelectedItem("");
		cbDiaChiNhaCungCap.setSelectedItem("");
		cbMaNCC.requestFocus();
	}

	private void fillForm(int row) {
		if (row != -1) {
			String maNCC = String.valueOf(tableNhaCungCap.getValueAt(row, 1));
			NhaCungCap nhaCungCap = new NhaCungCap(maNCC);
			ArrayList<NhaCungCap> dsNhaCungCap = danhSachTatCaNhaCungCap;
			if (dsNhaCungCap.contains(nhaCungCap)) {
				nhaCungCap = dsNhaCungCap.get(danhSachTatCaNhaCungCap.indexOf(nhaCungCap));
				cbMaNCC.removeAllItems();
				cbMaNCC.addItem(maNCC);
				cbMaNCC.setSelectedItem(maNCC);

				String tenNCC = nhaCungCap.getTenNCC();
				cbTenNCC.removeAllItems();
				cbTenNCC.addItem(tenNCC);
				cbTenNCC.setSelectedItem(tenNCC);

				String soDienThoai = nhaCungCap.getSoDienThoai();
				cbSoDienThoai.removeAllItems();
				cbSoDienThoai.addItem(soDienThoai);
				cbSoDienThoai.setSelectedItem(soDienThoai);

				String diaChi = nhaCungCap.getDiaChi();
				cbDiaChiNhaCungCap.removeAllItems();
				cbDiaChiNhaCungCap.addItem(diaChi);
				cbDiaChiNhaCungCap.setSelectedItem(diaChi);
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public TimNhaCungCap_GUI(NhanVien e, String tenChucNangDaChon) {
		emp = e;
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

		btnXemChiTiet = new JButton("Xem chi tiết nhà cung cấp");
		btnXemChiTiet.setBackground(Color.WHITE);
		btnXemChiTiet.setIcon(new ImageIcon("data\\icons\\more-details-16.png"));
		btnXemChiTiet.setFocusable(false);
		panel.add(btnXemChiTiet);
		btnXemChiTiet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableNhaCungCap.getSelectedRow();
				if (row != -1) {
					if (tenChucNangCanThucHienKhiTimKiem.equalsIgnoreCase("Sửa thông tin nhà cung cấp")) {
						SuaThongTinNhaCungCap_GUI suaThongTinNhaCungCap = new SuaThongTinNhaCungCap_GUI(taoNhaCungCap(),
								emp, getTitle());
						suaThongTinNhaCungCap.setVisible(true);
					} else if (tenChucNangCanThucHienKhiTimKiem.equalsIgnoreCase("Xóa thông tin nhà cung cấp")) {
						XoaThongTinNhaCungCap_GUI xoaThongTinNhaCungCap = new XoaThongTinNhaCungCap_GUI(taoNhaCungCap(),
								emp, false);
						xoaThongTinNhaCungCap.setVisible(true);
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn cần chọn nhà cung cấp muốn xem chi tiết", "Cảnh báo",
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
				} else if (emp.getChucVu().equalsIgnoreCase("Nhân viên kho")) {
					NhanVienKho_GUI nhanVienKho = new NhanVienKho_GUI(emp);
					nhanVienKho.setVisible(true);
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
		lblTitle.setIcon(new ImageIcon("data\\icons\\search-supplier-title.png"));
		lblTitle.setBounds(458, 10, 634, 93);
		panel_2.add(lblTitle);

		JPanel panel_chart = new JPanel();
		panel_chart.setBackground(Color.WHITE);
		panel_chart.setBounds(0, 109, 1522, 651);
		panel_chart.setLayout(null);
		panel_2.add(panel_chart);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(10, 10, 1502, 144);
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_chart.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblThongTinNhaCungCap = new JLabel("Thông tin nhà cung cấp");
		lblThongTinNhaCungCap.setForeground(SystemColor.textHighlight);
		lblThongTinNhaCungCap.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinNhaCungCap.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinNhaCungCap.setBounds(10, 10, 1482, 41);
		panel_3.add(lblThongTinNhaCungCap);

		cbMaNCC = new JComboBox();
		cbMaNCC.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaNCC.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaNCC.setEditable(true);
		cbMaNCC.setBackground(Color.WHITE);
		cbMaNCC.setBounds(153, 61, 535, 29);
		panel_3.add(cbMaNCC);

		lblMaNhaCungCap = new JLabel("Mã nhà cung cấp:");
		lblMaNhaCungCap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaNhaCungCap.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaNhaCungCap.setBounds(10, 61, 141, 26);
		panel_3.add(lblMaNhaCungCap);

		lblTenNhaCungCap = new JLabel("Tên nhà cung cấp:");
		lblTenNhaCungCap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTenNhaCungCap.setFont(new Font("Arial", Font.BOLD, 16));
		lblTenNhaCungCap.setBounds(755, 62, 182, 26);
		panel_3.add(lblTenNhaCungCap);

		cbTenNCC = new JComboBox();
		cbTenNCC.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbTenNCC.setFont(new Font("Arial", Font.PLAIN, 16));
		cbTenNCC.setEditable(true);
		cbTenNCC.setBackground(Color.WHITE);
		cbTenNCC.setBounds(940, 61, 519, 29);
		panel_3.add(cbTenNCC);

		btnSearchMaNCC = new JButton();
		btnSearchMaNCC.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchMaNCC.setToolTipText("Tìm kiếm nhà cung cấp theo mã nhà cung cấp");
		btnSearchMaNCC.setFocusable(false);
		btnSearchMaNCC.setBorder(BorderFactory.createEmptyBorder());
		btnSearchMaNCC.setBackground(Color.WHITE);
		btnSearchMaNCC.setBounds(691, 61, 31, 31);
		panel_3.add(btnSearchMaNCC);

		lblDiaChiNhaCungCap = new JLabel("Địa chỉ nhà cung cấp:");
		lblDiaChiNhaCungCap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiaChiNhaCungCap.setFont(new Font("Arial", Font.BOLD, 16));
		lblDiaChiNhaCungCap.setBounds(732, 103, 204, 26);
		panel_3.add(lblDiaChiNhaCungCap);

		lblSoDienThoai = new JLabel("Số điện thoại:");
		lblSoDienThoai.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoDienThoai.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoDienThoai.setBounds(10, 100, 141, 26);
		panel_3.add(lblSoDienThoai);

		cbSoDienThoai = new JComboBox();
		cbSoDienThoai.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbSoDienThoai.setFont(new Font("Arial", Font.PLAIN, 16));
		cbSoDienThoai.setEditable(true);
		cbSoDienThoai.setBackground(Color.WHITE);
		cbSoDienThoai.setBounds(153, 100, 535, 29);
		panel_3.add(cbSoDienThoai);

		btnSearchSoDienThoai = new JButton();
		btnSearchSoDienThoai.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchSoDienThoai.setToolTipText("Tìm kiếm nhà cung cấp theo số điện thoại");
		btnSearchSoDienThoai.setFocusable(false);
		btnSearchSoDienThoai.setBorder(BorderFactory.createEmptyBorder());
		btnSearchSoDienThoai.setBackground(Color.WHITE);
		btnSearchSoDienThoai.setBounds(691, 100, 31, 31);
		panel_3.add(btnSearchSoDienThoai);

		btnSearchTenNCC = new JButton();
		btnSearchTenNCC.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchTenNCC.setToolTipText("Tìm kiếm nhà cung cấp theo tên nhà cung cấp");
		btnSearchTenNCC.setFocusable(false);
		btnSearchTenNCC.setBorder(BorderFactory.createEmptyBorder());
		btnSearchTenNCC.setBackground(Color.WHITE);
		btnSearchTenNCC.setBounds(1461, 59, 31, 31);
		panel_3.add(btnSearchTenNCC);

		cbDiaChiNhaCungCap = new JComboBox();
		cbDiaChiNhaCungCap.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbDiaChiNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 16));
		cbDiaChiNhaCungCap.setEditable(true);
		cbDiaChiNhaCungCap.setBackground(Color.WHITE);
		cbDiaChiNhaCungCap.setBounds(940, 100, 519, 29);
		panel_3.add(cbDiaChiNhaCungCap);

		btnSearchDiaChiNhaCungCap = new JButton();
		btnSearchDiaChiNhaCungCap.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchDiaChiNhaCungCap.setToolTipText("Tìm kiếm nhà cung cấp theo địa chỉ");
		btnSearchDiaChiNhaCungCap.setFocusable(false);
		btnSearchDiaChiNhaCungCap.setBorder(BorderFactory.createEmptyBorder());
		btnSearchDiaChiNhaCungCap.setBackground(Color.WHITE);
		btnSearchDiaChiNhaCungCap.setBounds(1461, 98, 31, 31);
		panel_3.add(btnSearchDiaChiNhaCungCap);

		String[] headers = { "STT", "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ email", "Số điện thoại",
				"Địa chỉ" };
		modelNhaCungCap = new DefaultTableModel(headers, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tableNhaCungCap = new JTable(modelNhaCungCap);
		tableNhaCungCap.getTableHeader().setBackground(Color.CYAN);
		tableNhaCungCap.getTableHeader().setFont(new Font(null, Font.PLAIN, 14));
		tableNhaCungCap.setSelectionBackground(Color.YELLOW);
		tableNhaCungCap.setSelectionForeground(Color.RED);
		tableNhaCungCap.setFont(new Font(null, Font.PLAIN, 14));
		tableNhaCungCap.setRowHeight(22);
		tableNhaCungCap.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableNhaCungCap.setToolTipText("Nhấn đúp chuột vào dòng bất kỳ để xem chi tiết nhà cung cấp");
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 164, 1502, 443);
		panel_chart.add(scrollPane);
		scrollPane.setViewportView(tableNhaCungCap);
		tableNhaCungCap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (tenChucNangCanThucHienKhiTimKiem.equalsIgnoreCase("Sửa thông tin nhà cung cấp")) {
						SuaThongTinNhaCungCap_GUI suaThongTinNhaCungCap = new SuaThongTinNhaCungCap_GUI(taoNhaCungCap(),
								emp, getTitle());
						suaThongTinNhaCungCap.setVisible(true);
					} else if (tenChucNangCanThucHienKhiTimKiem.equalsIgnoreCase("Xóa thông tin nhà cung cấp")) {
						XoaThongTinNhaCungCap_GUI xoaThongTinNhaCungCap = new XoaThongTinNhaCungCap_GUI(taoNhaCungCap(),
								emp, false);
						xoaThongTinNhaCungCap.setVisible(true);
					}
				}
			}
		});

		btnFirstPage = new JButton("");
		btnFirstPage.setIcon(new ImageIcon("data\\icons\\first-page-32.png"));
		btnFirstPage.setBounds(662, 618, 24, 24);
		panel_chart.add(btnFirstPage);

		btnPreviousPage = new JButton("");
		btnPreviousPage.setIcon(new ImageIcon("data\\icons\\previous-page-32.png"));
		btnPreviousPage.setBounds(696, 618, 24, 24);
		panel_chart.add(btnPreviousPage);

		lblPageIndex = new JLabel();
		lblPageIndex.setHorizontalAlignment(SwingConstants.CENTER);
		lblPageIndex.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPageIndex.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPageIndex.setBounds(730, 617, 67, 25);
		panel_chart.add(lblPageIndex);

		btnNextPage = new JButton("");
		btnNextPage.setIcon(new ImageIcon("data\\icons\\next-page-32.png"));
		btnNextPage.setBounds(807, 617, 24, 24);
		panel_chart.add(btnNextPage);

		btnLastPage = new JButton("");
		btnLastPage.setIcon(new ImageIcon("data\\icons\\last-index-32.png"));
		btnLastPage.setBounds(841, 617, 24, 24);
		panel_chart.add(btnLastPage);

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

		setTitle("Tìm kiếm nhà cung cấp");
		Toolkit tk = Toolkit.getDefaultToolkit();
		setSize(tk.getScreenSize().width, tk.getScreenSize().height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);

		NhaCungCap_DAO nhacungcap_dao = new NhaCungCap_DAO();
		danhSachTatCaNhaCungCap = nhacungcap_dao.layTatCaNhaCungCap();
		if (danhSachTatCaNhaCungCap != null) {
			capNhatDuLieuTrenBang_Init(danhSachTatCaNhaCungCap, soPhanTuMuonHienThi);
		} else {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Chưa có nhà cung cấp nào", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		}

		cbMaNCC.addActionListener(this);
		btnSearchMaNCC.addActionListener(this);
		cbTenNCC.addActionListener(this);
		btnSearchTenNCC.addActionListener(this);
		cbSoDienThoai.addActionListener(this);
		btnSearchSoDienThoai.addActionListener(this);
		cbDiaChiNhaCungCap.addActionListener(this);
		btnSearchDiaChiNhaCungCap.addActionListener(this);
		btnNextPage.addActionListener(this);
		btnPreviousPage.addActionListener(this);
		btnLastPage.addActionListener(this);
		btnFirstPage.addActionListener(this);
		btnReset.addActionListener(this);
		tableNhaCungCap.getSelectionModel().addListSelectionListener(this);
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
		int row = tableNhaCungCap.getSelectedRow();
		fillForm(row);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(cbMaNCC)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxMaNhaCungCap();
				int soPhanTuHienCo = cbMaNCC.getItemCount();
				String tuHienTai = cbMaNCC.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxMaNhaCungCap();
						cbMaNCC.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbMaNCC.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbMaNCC.addItem(cacUngVien.get(i));
							}
							cbMaNCC.setSelectedItem(tuHienTai);
							cbMaNCC.showPopup();
						} else if (soUngVien == 0) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbMaNCC, "Không tìm thấy mã nhà cung cấp nào phù hợp",
									"Thông báo", JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxMaNhaCungCap();
							cbMaNCC.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaNCC, "Không tìm thấy mã nhà cung cấp nào", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbMaNCC.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchMaNCC)) {
			tableNhaCungCap.clearSelection();
			try {
				String maNCCNhapVao = cbMaNCC.getSelectedItem().toString().trim();
				if (!maNCCNhapVao.equalsIgnoreCase("")) {
					Iterator<NhaCungCap> it = danhSachTatCaNhaCungCap.iterator();
					while (it.hasNext()) {
						NhaCungCap nhaCungCap = (NhaCungCap) it.next();
						String maNCC = nhaCungCap.getMaNCC();
						if (!maNCC.toLowerCase().contains(maNCCNhapVao.toLowerCase())) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaNhaCungCap, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				e1.printStackTrace();
			}
		} else if (obj.equals(cbTenNCC)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxTenNhaCungCap();
				int soPhanTuHienCo = cbTenNCC.getItemCount();
				String tuHienTai = cbTenNCC.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxTenNhaCungCap();
						cbTenNCC.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbTenNCC.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbTenNCC.addItem(cacUngVien.get(i));
							}
							cbTenNCC.setSelectedItem(tuHienTai);
							cbTenNCC.showPopup();
						} else if (soUngVien == 0) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbTenNCC, "Không tìm thấy tên nhà cung cấp nào phù hợp",
									"Thông báo", JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxTenNhaCungCap();
							cbTenNCC.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbTenNCC, "Không tìm thấy tên nhà cung cấp nào", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbTenNCC.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchTenNCC)) {
			tableNhaCungCap.clearSelection();
			try {
				String tenNCCNhapVao = cbTenNCC.getSelectedItem().toString().trim();
				if (!tenNCCNhapVao.equalsIgnoreCase("")) {
					Iterator<NhaCungCap> it = danhSachTatCaNhaCungCap.iterator();
					while (it.hasNext()) {
						NhaCungCap nhaCungCap = (NhaCungCap) it.next();
						String tenNCC = nhaCungCap.getTenNCC();
						if (!tenNCC.toLowerCase().contains(tenNCCNhapVao.toLowerCase())) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaNhaCungCap, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				e1.printStackTrace();
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
									"Không tìm thấy số điện thoại nhà cung cấp nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxSoDienThoai();
							cbSoDienThoai.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbSoDienThoai, "Không tìm thấy số điện thoại nhà cung cấp nào",
							"Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbSoDienThoai.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchSoDienThoai)) {
			tableNhaCungCap.clearSelection();
			try {
				String soDienThoaiNhapVao = cbSoDienThoai.getSelectedItem().toString().trim();
				if (!soDienThoaiNhapVao.equalsIgnoreCase("")) {
					Iterator<NhaCungCap> it = danhSachTatCaNhaCungCap.iterator();
					while (it.hasNext()) {
						NhaCungCap nhaCungCap = (NhaCungCap) it.next();
						String soDienThoai = nhaCungCap.getSoDienThoai();
						if (!soDienThoai.contains(soDienThoaiNhapVao)) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaNhaCungCap, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				e1.printStackTrace();
			}
		} else if (obj.equals(cbDiaChiNhaCungCap)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxDiaChiNhaCungCap();
				int soPhanTuHienCo = cbDiaChiNhaCungCap.getItemCount();
				String tuHienTai = cbDiaChiNhaCungCap.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxDiaChiNhaCungCap();
						cbDiaChiNhaCungCap.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbDiaChiNhaCungCap.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbDiaChiNhaCungCap.addItem(cacUngVien.get(i));
							}
							cbDiaChiNhaCungCap.setSelectedItem(tuHienTai);
							cbDiaChiNhaCungCap.showPopup();
						} else if (soUngVien == 0) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbDiaChiNhaCungCap,
									"Không tìm thấy địa chỉ nhà cung cấp nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxDiaChiNhaCungCap();
							cbDiaChiNhaCungCap.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbDiaChiNhaCungCap, "Không tìm thấy địa chỉ nhà cung cấp nào",
							"Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbDiaChiNhaCungCap.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchDiaChiNhaCungCap)) {
			tableNhaCungCap.clearSelection();
			try {
				String diaChiNCCNhapVao = cbDiaChiNhaCungCap.getSelectedItem().toString().trim();
				if (!diaChiNCCNhapVao.equalsIgnoreCase("")) {
					Iterator<NhaCungCap> it = danhSachTatCaNhaCungCap.iterator();
					while (it.hasNext()) {
						NhaCungCap nhaCungCap = (NhaCungCap) it.next();
						String diaChiNCC = nhaCungCap.getDiaChi();
						if (!diaChiNCC.toLowerCase().contains(diaChiNCCNhapVao.toLowerCase())) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaNhaCungCap, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				e1.printStackTrace();
			}
		} else if (obj.equals(btnNextPage)) {
			tableNhaCungCap.clearSelection();
			capNhatDuLieuTrenBang_Next(danhSachTatCaNhaCungCap, soPhanTuMuonHienThi);
		} else if (obj.equals(btnPreviousPage)) {
			tableNhaCungCap.clearSelection();
			capNhatDuLieuTrenBang_Previous(danhSachTatCaNhaCungCap, soPhanTuMuonHienThi);
		} else if (obj.equals(btnLastPage)) {
			tableNhaCungCap.clearSelection();
			capNhatDuLieuTrenBang_Last(danhSachTatCaNhaCungCap, soPhanTuMuonHienThi);
		} else if (obj.equals(btnFirstPage)) {
			tableNhaCungCap.clearSelection();
			capNhatDuLieuTrenBang_Init(danhSachTatCaNhaCungCap, soPhanTuMuonHienThi);
		} else if (obj.equals(btnReset)) {
			tableNhaCungCap.clearSelection();
			NhaCungCap_DAO nhacungcap_dao = new NhaCungCap_DAO();
			danhSachTatCaNhaCungCap = nhacungcap_dao.layTatCaNhaCungCap();
			capNhatDuLieuTrenBang_Init(danhSachTatCaNhaCungCap, soPhanTuMuonHienThi);
			xoaTrang();
		}
	}
}
