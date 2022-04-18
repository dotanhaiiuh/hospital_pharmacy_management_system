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

import class_DAO.Thuoc_DAO;
import class_Entities.NhanVien;
import class_Entities.Thuoc;
import class_Entities.NhaCungCap;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.border.EtchedBorder;

public class XemDanhSachThuocTonKho_GUI extends JFrame
		implements ActionListener, WindowListener, ListSelectionListener {
	private DefaultTableModel modelThuoc;
	private JTable tableThuoc;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	private JButton btnBack;

	private JComboBox cbLoaiThuoc;
	private NhanVien emp;

	private JLabel lblLoaiThuoc;
	private JTextField txtNgayHienTai;

	private ArrayList<Thuoc> danhSachTatCaThuocTonKho;
	private final int soPhanTuMuonHienThi = 21;

	private JComboBox cbMaThuoc;
	private JComboBox cbMaNhaCungCap;
	private JButton btnTimKiem;
	private JButton btnReset;

	private JLabel lblMaThuoc;
	private JComboBox cbXuatXu;
	private JLabel lblMaNhaCungCap;
	private AbstractButton btnFirstPage;
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
		modelThuoc.getDataVector().removeAllElements();
	}

	private ArrayList<Thuoc> layDuLieuTrongTrang(ArrayList<Thuoc> danhSachThuoc, int soTrangMuonLay, int lastPage,
			int soPhanTuMuonHienThi) {
		ArrayList<Thuoc> kq = new ArrayList<Thuoc>();
		if (soTrangMuonLay < lastPage) {
			kq = new ArrayList<Thuoc>(
					danhSachThuoc.subList(soTrangMuonLay, (soTrangMuonLay * soPhanTuMuonHienThi) + 1));
		} else if (soTrangMuonLay == lastPage) {
			kq = new ArrayList<Thuoc>(
					danhSachThuoc.subList((lastPage - 1) * soPhanTuMuonHienThi, danhSachThuoc.size()));
		}
		return kq;
	}

	private void capNhatDuLieuTrenBang_Init(ArrayList<Thuoc> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			xoaHetDuLieuTrenTableModel();
			int soThuTu = 1;
			for (Thuoc thuoc : danhSachMuonCapNhat) {
				NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
				modelThuoc.addRow(new Object[] { soThuTu++, thuoc.getMaThuoc(), thuoc.getTenThuoc(),
						thuoc.getLoaiThuoc(), thuoc.getDonViThuoc(), thuoc.getXuatXu(),
						String.valueOf(thuoc.getSoLuongTon()), nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC() });
			}
			if (lengthArray > 0) {
				double countPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
				lblPageIndex.setText("1" + "/" + String.valueOf((int) countPage));
			} else {
				lblPageIndex.setText("1" + "/" + "1");
			}
			if (lengthArray < soPhanTuMuonHienThi) {
				modelThuoc.setRowCount(lengthArray);
			} else {
				modelThuoc.setRowCount(soPhanTuMuonHienThi);
			}
			tableThuoc.setModel(modelThuoc);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenBang_Search(ArrayList<Thuoc> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			int soThuTu = 1;
			xoaHetDuLieuTrenTableModel();
			ArrayList<Thuoc> danhSachThuoc = timKiemThuoc(danhSachMuonCapNhat);
			for (Thuoc thuoc : danhSachThuoc) {
				NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
				modelThuoc.addRow(new Object[] { soThuTu++, thuoc.getMaThuoc(), thuoc.getTenThuoc(),
						thuoc.getLoaiThuoc(), thuoc.getDonViThuoc(), thuoc.getXuatXu(),
						String.valueOf(thuoc.getSoLuongTon()), nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC() });
			}
			lblPageIndex.setText("1" + "/" + "1");
			modelThuoc.setRowCount(danhSachThuoc.size());
			tableThuoc.setModel(modelThuoc);
			capNhatTatCaComboBox();
		}
		else {
			lblPageIndex.setText("1" + "/" + "1");
			modelThuoc.setRowCount(0);
			tableThuoc.setModel(modelThuoc);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenBang_Next(ArrayList<Thuoc> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			String string_SoTrangHienTai = lblPageIndex.getText().trim().split("/")[0];
			int soTrangHienTai = Integer.parseInt(string_SoTrangHienTai) + 1;
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			if (soTrangHienTai <= lastPage) {
				int soThuTu = ((soTrangHienTai - 1) * soPhanTuMuonHienThi) + 1;
				xoaHetDuLieuTrenTableModel();
				ArrayList<Thuoc> danhSachThuoc = layDuLieuTrongTrang(danhSachMuonCapNhat, soTrangHienTai,
						(int) lastPage, soPhanTuMuonHienThi);
				for (Thuoc thuoc : danhSachMuonCapNhat) {
					NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
					modelThuoc.addRow(new Object[] { soThuTu++, thuoc.getMaThuoc(), thuoc.getTenThuoc(),
							thuoc.getLoaiThuoc(), thuoc.getDonViThuoc(), thuoc.getXuatXu(),
							String.valueOf(thuoc.getSoLuongTon()), nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC() });
				}
				lblPageIndex.setText(String.valueOf(soTrangHienTai) + "/" + String.valueOf((int) lastPage));
				modelThuoc.setRowCount(danhSachThuoc.size());
				tableThuoc.setModel(modelThuoc);
				capNhatTatCaComboBox();
			}
		}
	}

	private void capNhatDuLieuTrenBang_Previous(ArrayList<Thuoc> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			String soThuTuDauTien = String.valueOf(tableThuoc.getValueAt(0, 0));
			String string_SoTrangHienTai = lblPageIndex.getText().trim().split("/")[0];
			int soTrangHienTai = Integer.parseInt(string_SoTrangHienTai) - 1;
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			if (soTrangHienTai > 0) {
				int soThuTu = Integer.parseInt(soThuTuDauTien) - soPhanTuMuonHienThi;
				xoaHetDuLieuTrenTableModel();
				ArrayList<Thuoc> danhSachThuoc = layDuLieuTrongTrang(danhSachMuonCapNhat, soTrangHienTai,
						(int) lastPage, soPhanTuMuonHienThi);
				for (Thuoc thuoc : danhSachMuonCapNhat) {
					NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
					modelThuoc.addRow(new Object[] { soThuTu++, thuoc.getMaThuoc(), thuoc.getTenThuoc(),
							thuoc.getLoaiThuoc(), thuoc.getDonViThuoc(), thuoc.getXuatXu(),
							String.valueOf(thuoc.getSoLuongTon()), nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC() });
				}
				lblPageIndex.setText(String.valueOf(soTrangHienTai) + "/" + String.valueOf((int) lastPage));
				modelThuoc.setRowCount(danhSachThuoc.size());
				tableThuoc.setModel(modelThuoc);
				capNhatTatCaComboBox();
			}
		}
	}

	private void capNhatDuLieuTrenBang_Last(ArrayList<Thuoc> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			int last_index = (int) (soPhanTuMuonHienThi * (lastPage - 1));
			int soThuTu = last_index + 1;
			xoaHetDuLieuTrenTableModel();
			ArrayList<Thuoc> danhSachThuoc = new ArrayList<Thuoc>(danhSachMuonCapNhat.subList(last_index, lengthArray));
			for (Thuoc thuoc : danhSachMuonCapNhat) {
				NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
				modelThuoc.addRow(new Object[] { soThuTu++, thuoc.getMaThuoc(), thuoc.getTenThuoc(),
						thuoc.getLoaiThuoc(), thuoc.getDonViThuoc(), thuoc.getXuatXu(),
						String.valueOf(thuoc.getSoLuongTon()), nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC() });
			}
			lblPageIndex.setText(String.valueOf((int) lastPage) + "/" + String.valueOf((int) lastPage));
			modelThuoc.setRowCount(danhSachThuoc.size());
			tableThuoc.setModel(modelThuoc);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenComboBoxMaThuoc() {
		cbMaThuoc.removeAllItems();
		if (danhSachTatCaThuocTonKho.size() > 0) {
			ArrayList<String> danhSachThuoc = new ArrayList<String>();
			for (Thuoc thuoc : danhSachTatCaThuocTonKho) {
				String maThuoc = thuoc.getMaThuoc();
				String tenThuoc = thuoc.getTenThuoc();
				String s = maThuoc + " - " + tenThuoc;
				if (!danhSachThuoc.contains(s)) {
					danhSachThuoc.add(s);
					cbMaThuoc.addItem(s);
				}
			}
		}
		cbMaThuoc.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxMaThuoc() {
		ArrayList<String> danhSachThuoc = new ArrayList<String>();
		for (Thuoc thuoc : danhSachTatCaThuocTonKho) {
			String maThuoc = thuoc.getMaThuoc();
			String tenThuoc = thuoc.getTenThuoc();
			String s = maThuoc + " - " + tenThuoc;
			if (!danhSachThuoc.contains(s)) {
				danhSachThuoc.add(s);
			}
		}
		return danhSachThuoc;
	}

	private void capNhatDuLieuTrenComboBoxLoaiThuoc() {
		cbLoaiThuoc.removeAllItems();
		if (danhSachTatCaThuocTonKho.size() > 0) {
			ArrayList<String> cacLoaiThuoc = new ArrayList<String>();
			for (Thuoc thuoc : danhSachTatCaThuocTonKho) {
				String loaiThuoc = thuoc.getLoaiThuoc();
				if (!cacLoaiThuoc.contains(loaiThuoc)) {
					cacLoaiThuoc.add(loaiThuoc);
					cbLoaiThuoc.addItem(loaiThuoc);
				}
			}
		}
		cbLoaiThuoc.setSelectedIndex(-1);
	}

	private void capNhatDuLieuTrenComboBoxXuatXu() {
		cbXuatXu.removeAllItems();
		if (danhSachTatCaThuocTonKho.size() > 0) {
			ArrayList<String> cacXuatXu = new ArrayList<String>();
			for (Thuoc thuoc : danhSachTatCaThuocTonKho) {
				String xuatXu = thuoc.getXuatXu();
				if (!cacXuatXu.contains(xuatXu)) {
					cacXuatXu.add(xuatXu);
					cbXuatXu.addItem(xuatXu);
				}
			}
		}
		cbXuatXu.setSelectedIndex(-1);
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxXuatXu() {
		ArrayList<String> danhSachXuatXu = new ArrayList<String>();
		for (Thuoc thuoc : danhSachTatCaThuocTonKho) {
			String xuatXu = thuoc.getXuatXu();
			if (!danhSachXuatXu.contains(xuatXu)) {
				danhSachXuatXu.add(xuatXu);
			}
		}
		return danhSachXuatXu;
	}

	private void capNhatDuLieuTrenComboBoxMaNhaCungCap() {
		cbMaNhaCungCap.removeAllItems();
		if (danhSachTatCaThuocTonKho.size() > 0) {
			ArrayList<String> cacNhaCungCap = new ArrayList<String>();
			for (Thuoc thuoc : danhSachTatCaThuocTonKho) {
				NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
				String maNhaCungCap = nhaCungCap.getMaNCC();
				String tenNhaCungCap = nhaCungCap.getTenNCC();
				String s = maNhaCungCap + " - " + tenNhaCungCap;
				if (!cacNhaCungCap.contains(s)) {
					cacNhaCungCap.add(s);
					cbMaNhaCungCap.addItem(s);
				}
			}
		}
		cbMaNhaCungCap.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxMaNhaCungCap() {
		ArrayList<String> danhSachNhaCungCap = new ArrayList<String>();
		for (Thuoc thuoc : danhSachTatCaThuocTonKho) {
			NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
			String maNhaCungCap = nhaCungCap.getMaNCC();
			String tenNhaCungCap = nhaCungCap.getTenNCC();
			String s = maNhaCungCap + " - " + tenNhaCungCap;
			if (!danhSachNhaCungCap.contains(s)) {
				danhSachNhaCungCap.add(s);
			}
		}
		return danhSachNhaCungCap;
	}

	private void capNhatTatCaComboBox() {
		capNhatDuLieuTrenComboBoxMaThuoc();
		capNhatDuLieuTrenComboBoxLoaiThuoc();
		capNhatDuLieuTrenComboBoxXuatXu();
		capNhatDuLieuTrenComboBoxMaNhaCungCap();
	}

	private ArrayList<Thuoc> timKiemThuoc(ArrayList<Thuoc> danhSachCanTimKiem) {
		tableThuoc.clearSelection();
		ArrayList<Thuoc> danhSachTimDuoc = new ArrayList<Thuoc>();
		try {
			String maThuocNhapVao = cbMaThuoc.getSelectedItem().toString().trim();
			if (!maThuocNhapVao.equalsIgnoreCase("")) {
				for (Thuoc thuoc : danhSachCanTimKiem) {
					String maThuoc = thuoc.getMaThuoc() + " - " + thuoc.getTenThuoc();
					if (maThuoc.toLowerCase().contains(maThuocNhapVao.toLowerCase())) {
						danhSachTimDuoc.add(thuoc);
					}
				}
			}
		} catch (NullPointerException e) {
			cbMaThuoc.setSelectedItem("");
		}

		try {
			String loaiThuocNhapVao = cbLoaiThuoc.getSelectedItem().toString().trim();
			if (!loaiThuocNhapVao.equalsIgnoreCase("")) {
				for (Thuoc thuoc : danhSachCanTimKiem) {
					String loaiThuoc = thuoc.getLoaiThuoc();
					if (loaiThuoc.equalsIgnoreCase(loaiThuocNhapVao)) {
						danhSachTimDuoc.add(thuoc);
					}
				}
			}
		} catch (Exception e) {
			cbLoaiThuoc.setSelectedItem("");
		}

		try {
			String xuatXuNhapVao = cbXuatXu.getSelectedItem().toString().trim();
			if (!xuatXuNhapVao.equalsIgnoreCase("")) {
				for (Thuoc thuoc : danhSachCanTimKiem) {
					String xuatXu = thuoc.getXuatXu();
					if (xuatXu.toLowerCase().contains(xuatXuNhapVao.toLowerCase())) {
						danhSachTimDuoc.add(thuoc);
					}
				}
			}
		} catch (Exception e) {
			cbXuatXu.setSelectedItem("");
		}

		try {
			String maNCCNhapVao = cbMaNhaCungCap.getSelectedItem().toString().trim();
			if (!maNCCNhapVao.equalsIgnoreCase("")) {
				for (Thuoc thuoc : danhSachCanTimKiem) {
					NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
					String maNCC = nhaCungCap.getMaNCC() + " - " + nhaCungCap.getTenNCC();
					if (maNCC.toLowerCase().contains(maNCCNhapVao.toLowerCase())) {
						danhSachTimDuoc.add(thuoc);
					}
				}
			}
		} catch (NullPointerException e) {
			cbMaNhaCungCap.setSelectedItem("");
		}

		return danhSachTimDuoc;
	}

	private void xoaTrang() {
		cbMaThuoc.setSelectedItem("");
		cbLoaiThuoc.setSelectedIndex(-1);
		cbMaNhaCungCap.setSelectedItem("");
		cbXuatXu.setSelectedItem("");
		cbMaThuoc.requestFocus();
	}

	private void fillForm(int row) {
		if (row != -1) {
			String maThuoc = String.valueOf(tableThuoc.getValueAt(row, 1));
			Thuoc thuoc = new Thuoc(maThuoc);
			ArrayList<Thuoc> dsThuoc = danhSachTatCaThuocTonKho;
			if (dsThuoc.contains(thuoc)) {
				thuoc = dsThuoc.get(danhSachTatCaThuocTonKho.indexOf(thuoc));
				cbMaThuoc.removeAllItems();
				cbMaThuoc.addItem(maThuoc);
				cbMaThuoc.setSelectedItem(maThuoc);

				cbLoaiThuoc.setSelectedItem(thuoc.getLoaiThuoc());

				String xuatXu = thuoc.getXuatXu();
				cbXuatXu.removeAllItems();
				cbXuatXu.addItem(xuatXu);
				cbXuatXu.setSelectedItem(xuatXu);

				String maNhaCungCap = thuoc.getNhaCungCap().getMaNCC();
				cbMaNhaCungCap.removeAllItems();
				cbMaNhaCungCap.addItem(maNhaCungCap);
				cbMaNhaCungCap.setSelectedItem(maNhaCungCap);
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public XemDanhSachThuocTonKho_GUI(NhanVien e) {
		emp = e;
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\search-medicine-24.png"));
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
		lblTitle.setIcon(new ImageIcon("data\\icons\\list-medicine-inventory-title.png"));
		lblTitle.setBounds(380, 10, 810, 93);
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

		JLabel lblThongTinThuoc = new JLabel("Thông tin thuốc");
		lblThongTinThuoc.setForeground(SystemColor.textHighlight);
		lblThongTinThuoc.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinThuoc.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinThuoc.setBounds(10, 10, 1482, 41);
		panel_3.add(lblThongTinThuoc);

		lblMaThuoc = new JLabel("Mã và tên thuốc:");
		lblMaThuoc.setHorizontalAlignment(SwingConstants.LEFT);
		lblMaThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaThuoc.setBounds(7, 61, 127, 26);
		panel_3.add(lblMaThuoc);

		lblLoaiThuoc = new JLabel("Loại thuốc:");
		lblLoaiThuoc.setBounds(442, 61, 86, 26);
		lblLoaiThuoc.setHorizontalAlignment(SwingConstants.LEFT);
		lblLoaiThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		panel_3.add(lblLoaiThuoc);

		cbLoaiThuoc = new JComboBox();
		cbLoaiThuoc.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbLoaiThuoc.setBackground(Color.WHITE);
		cbLoaiThuoc.setFont(new Font("Arial", Font.PLAIN, 16));
		cbLoaiThuoc.setBounds(531, 60, 168, 29);
		panel_3.add(cbLoaiThuoc);

		cbMaThuoc = new JComboBox();
		cbMaThuoc.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaThuoc.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaThuoc.setEditable(true);
		cbMaThuoc.setBackground(Color.WHITE);
		cbMaThuoc.setBounds(136, 61, 300, 29);
		panel_3.add(cbMaThuoc);

		lblMaNhaCungCap = new JLabel("Mã và tên nhà cung cấp:");
		lblMaNhaCungCap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaNhaCungCap.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaNhaCungCap.setBounds(974, 61, 193, 26);
		panel_3.add(lblMaNhaCungCap);

		cbMaNhaCungCap = new JComboBox();
		cbMaNhaCungCap.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaNhaCungCap.setEditable(true);
		cbMaNhaCungCap.setBackground(Color.WHITE);
		cbMaNhaCungCap.setBounds(1169, 61, 229, 29);
		panel_3.add(cbMaNhaCungCap);

		btnTimKiem = new JButton("Tìm");
		btnTimKiem.setHorizontalAlignment(SwingConstants.LEFT);
		btnTimKiem.setIcon(new ImageIcon("data\\icons\\search16.png"));
		btnTimKiem.setBackground(Color.WHITE);
		btnTimKiem.setFont(new Font("Arial", Font.PLAIN, 16));
		btnTimKiem.setBounds(1408, 61, 84, 30);
		panel_3.add(btnTimKiem);

		JLabel lblXuatXu = new JLabel("Xuất xứ:");
		lblXuatXu.setHorizontalAlignment(SwingConstants.LEFT);
		lblXuatXu.setFont(new Font("Arial", Font.BOLD, 16));
		lblXuatXu.setBounds(705, 61, 70, 26);
		panel_3.add(lblXuatXu);

		cbXuatXu = new JComboBox();
		cbXuatXu.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbXuatXu.setFont(new Font("Arial", Font.PLAIN, 16));
		cbXuatXu.setEditable(true);
		cbXuatXu.setBackground(Color.WHITE);
		cbXuatXu.setBounds(775, 61, 199, 29);
		panel_3.add(cbXuatXu);

		String[] headers = { "STT", "Mã thuốc", "Tên thuốc", "Loại thuốc", "Đơn vị thuốc", "Xuất xứ", "Số lượng tồn",
				"Mã nhà cung cấp", "Tên nhà cung cấp" };
		modelThuoc = new DefaultTableModel(headers, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tableThuoc = new JTable(modelThuoc);
		tableThuoc.getTableHeader().setBackground(Color.CYAN);
		tableThuoc.getTableHeader().setFont(new Font(null, Font.PLAIN, 14));
		tableThuoc.setSelectionBackground(Color.YELLOW);
		tableThuoc.setSelectionForeground(Color.RED);
		tableThuoc.setFont(new Font(null, Font.PLAIN, 14));
		tableThuoc.setRowHeight(22);
		tableThuoc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableThuoc.setToolTipText("Nhấn đúp chuột vào dòng bất kỳ để xem chi tiết hóa đơn");
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 121, 1502, 486);
		panel_chart.add(scrollPane);
		scrollPane.setViewportView(tableThuoc);

		btnFirstPage = new JButton("");
		btnFirstPage.setIcon(new ImageIcon("data\\icons\\first-page-32.png"));
		btnFirstPage.setBounds(659, 617, 24, 24);
		panel_chart.add(btnFirstPage);

		btnPreviousPage = new JButton("");
		btnPreviousPage.setIcon(new ImageIcon("data\\icons\\previous-page-32.png"));
		btnPreviousPage.setBounds(693, 617, 24, 24);
		panel_chart.add(btnPreviousPage);

		lblPageIndex = new JLabel();
		lblPageIndex.setHorizontalAlignment(SwingConstants.CENTER);
		lblPageIndex.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPageIndex.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPageIndex.setBounds(727, 616, 67, 25);
		panel_chart.add(lblPageIndex);

		btnNextPage = new JButton("");
		btnNextPage.setIcon(new ImageIcon("data\\icons\\next-page-32.png"));
		btnNextPage.setBounds(804, 616, 24, 24);
		panel_chart.add(btnNextPage);

		btnLastPage = new JButton("");
		btnLastPage.setIcon(new ImageIcon("data\\icons\\last-index-32.png"));
		btnLastPage.setBounds(838, 616, 24, 24);
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

		setTitle("Tìm kiếm thuốc");
		Toolkit tk = Toolkit.getDefaultToolkit();
		setSize(tk.getScreenSize().width, tk.getScreenSize().height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);

		Thuoc_DAO thuoc_dao = new Thuoc_DAO();
		danhSachTatCaThuocTonKho = thuoc_dao.layTatCaThuocTonKho();
		if (danhSachTatCaThuocTonKho != null) {
			capNhatDuLieuTrenBang_Init(danhSachTatCaThuocTonKho, soPhanTuMuonHienThi);
		} else {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Chưa có thuốc nào", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		}

		cbMaThuoc.addActionListener(this);
		cbLoaiThuoc.addActionListener(this);
		cbXuatXu.addActionListener(this);
		cbMaNhaCungCap.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnNextPage.addActionListener(this);
		btnPreviousPage.addActionListener(this);
		btnLastPage.addActionListener(this);
		btnFirstPage.addActionListener(this);
		btnReset.addActionListener(this);
		tableThuoc.getSelectionModel().addListSelectionListener(this);
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
		int row = tableThuoc.getSelectedRow();
		fillForm(row);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(cbMaThuoc)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxMaThuoc();
				int soPhanTuHienCo = cbMaThuoc.getItemCount();
				String tuHienTai = cbMaThuoc.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxMaThuoc();
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
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbMaThuoc,
									"Không tìm thấy mã thuốc hoặc tên thuốc nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxMaThuoc();
							cbMaThuoc.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaThuoc, "Không tìm thấy mã thuốc hoặc tên thuốc nào", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbMaThuoc.setSelectedItem("");
			}
		} else if (obj.equals(cbXuatXu)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxXuatXu();
				int soPhanTuHienCo = cbXuatXu.getItemCount();
				String tuHienTai = cbXuatXu.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxXuatXu();
						cbXuatXu.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbXuatXu.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbXuatXu.addItem(cacUngVien.get(i));
							}
							cbXuatXu.setSelectedItem(tuHienTai);
							cbXuatXu.showPopup();
						} else if (soUngVien == 0) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbXuatXu, "Không tìm thấy xuất xứ nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxXuatXu();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbXuatXu, "Không tìm thấy xuất xứ nào", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbXuatXu.setSelectedItem("");
			}
		} else if (obj.equals(cbMaNhaCungCap)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxMaNhaCungCap();
				int soPhanTuHienCo = cbMaNhaCungCap.getItemCount();
				String tuHienTai = cbMaNhaCungCap.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxMaNhaCungCap();
						cbMaNhaCungCap.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbMaNhaCungCap.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbMaNhaCungCap.addItem(cacUngVien.get(i));
							}
							cbMaNhaCungCap.setSelectedItem(tuHienTai);
							cbMaNhaCungCap.showPopup();
						} else if (soUngVien == 0) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbMaNhaCungCap,
									"Không tìm thấy mã nhà cung cấp hoặc tên nhà cung cấp nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxMaNhaCungCap();
							cbMaNhaCungCap.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaNhaCungCap,
							"Không tìm thấy mã nhà cung cấp hoặc tên nhà cung cấp nào", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbMaNhaCungCap.setSelectedItem("");
			}
		} else if (obj.equals(btnTimKiem)) {
			capNhatDuLieuTrenBang_Search(danhSachTatCaThuocTonKho, soPhanTuMuonHienThi);
		} else if (obj.equals(btnNextPage)) {
			tableThuoc.clearSelection();
			capNhatDuLieuTrenBang_Next(danhSachTatCaThuocTonKho, soPhanTuMuonHienThi);
		} else if (obj.equals(btnPreviousPage)) {
			tableThuoc.clearSelection();
			capNhatDuLieuTrenBang_Previous(danhSachTatCaThuocTonKho, soPhanTuMuonHienThi);
		} else if (obj.equals(btnLastPage)) {
			tableThuoc.clearSelection();
			capNhatDuLieuTrenBang_Last(danhSachTatCaThuocTonKho, soPhanTuMuonHienThi);
		} else if (obj.equals(btnFirstPage)) {
			tableThuoc.clearSelection();
			capNhatDuLieuTrenBang_Init(danhSachTatCaThuocTonKho, soPhanTuMuonHienThi);
		} else if (obj.equals(btnReset)) {
			tableThuoc.clearSelection();
			capNhatDuLieuTrenBang_Init(danhSachTatCaThuocTonKho, soPhanTuMuonHienThi);
			xoaTrang();
		}
	}
}
