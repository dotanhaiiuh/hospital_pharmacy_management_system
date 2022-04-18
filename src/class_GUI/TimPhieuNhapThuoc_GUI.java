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

import class_DAO.PhieuNhapThuoc_DAO;
import class_Entities.NhanVien;
import class_Entities.PhieuNhapThuoc;
import class_Entities.Thuoc;
import class_Equipment.DateLabelFormatter;
import class_Entities.NhaCungCap;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.text.DecimalFormat;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TimPhieuNhapThuoc_GUI extends JFrame implements ActionListener, WindowListener, ListSelectionListener {
	private DefaultTableModel modelPhieuNhapThuoc;
	private JTable tablePhieuNhapThuoc;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private DecimalFormat fmt = new DecimalFormat("###,###");

	private JButton btnHome;

	private NhanVien emp;

	private JLabel lblNgayLapPhieu;
	private JTextField txtNgayHienTai;

	private ArrayList<PhieuNhapThuoc> danhSachTatCaPhieuNhapThuoc;
	private final int soPhanTuMuonHienThi = 21;

	private JComboBox cbMaPhieuNhap;
	private JButton btnReset;

	private JLabel lblMaPhieuNhap;
	private JLabel lblNgayHetHan;
	private UtilDateModel model_ngaylapphieu;
	private JDatePanelImpl datePanelNgayLapPhieu;
	private JDatePickerImpl datePickerNgayLapPhieu;
	private Properties p;
	private UtilDateModel model_ngaysanxuat;
	private JDatePanelImpl datePanelNgaySanXuat;
	private JDatePickerImpl datePickerNgaySanXuat;
	private UtilDateModel model_ngayhethan;
	private JDatePanelImpl datePanelNgayHetHan;
	private JDatePickerImpl datePickerNgayHetHan;
	private JComboBox cbMaThuoc;
	private JComboBox cbLoaiThuoc;
	private JComboBox cbDonViThuoc;
	private JComboBox cbXuatXu;
	private JComboBox cbMaNhaCungCap;
	private JComboBox cbMaNhanVien;
	private JButton btnSearchMaPhieuNhap;
	private JButton btnSearchNgayLapPhieuNhap;
	private JButton btnSearchNgaySanXuat;
	private JButton btnSearchNgayHetHan;
	private JButton btnSearchMaThuoc;
	private JLabel lblMaThuoc;
	private JButton btnSearchMaNhanVien;
	private JLabel lblMaNhanVien;
	private JButton btnSearchMaNhaCungCap;
	private JLabel lblMaNhaCungCap;
	private JButton btnSearchLoaiThuoc;
	private JButton btnSearchDonViThuoc;
	private JButton btnSearchXuatXu;
	private JButton btnXemChiTiet;
	private JButton btnFirstPage;
	private AbstractButton btnPreviousPage;
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
		modelPhieuNhapThuoc.getDataVector().removeAllElements();
	}

	private ArrayList<PhieuNhapThuoc> layDuLieuTrongTrang(ArrayList<PhieuNhapThuoc> danhSachPNT, int soTrangMuonLay,
			int lastPage, int soPhanTuMuonHienThi) {
		ArrayList<PhieuNhapThuoc> kq = new ArrayList<PhieuNhapThuoc>();
		if (soTrangMuonLay < lastPage) {
			kq = new ArrayList<PhieuNhapThuoc>(
					danhSachPNT.subList(soTrangMuonLay, (soTrangMuonLay * soPhanTuMuonHienThi) + 1));
		} else if (soTrangMuonLay == lastPage) {
			kq = new ArrayList<PhieuNhapThuoc>(
					danhSachPNT.subList((lastPage - 1) * soPhanTuMuonHienThi, danhSachPNT.size()));
		}
		return kq;
	}

	private void capNhatDuLieuTrenBang_Init(ArrayList<PhieuNhapThuoc> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			xoaHetDuLieuTrenTableModel();
			int soThuTu = 1;
			for (PhieuNhapThuoc phieuNhapThuoc : danhSachMuonCapNhat) {
				Thuoc thuoc = phieuNhapThuoc.getThuoc();
				NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
				NhanVien nhanVien = phieuNhapThuoc.getNhanVien();
				modelPhieuNhapThuoc.addRow(new Object[] { soThuTu++, phieuNhapThuoc.getMaPhieu(),
						sdf.format(phieuNhapThuoc.getNgayNhap()), thuoc.getMaThuoc(), thuoc.getTenThuoc(),
						sdf.format(phieuNhapThuoc.getNgaySanXuat()), sdf.format(phieuNhapThuoc.getNgayHetHan()),
						thuoc.getLoaiThuoc(), thuoc.getDonViThuoc(), thuoc.getXuatXu(), nhaCungCap.getMaNCC(),
						nhaCungCap.getTenNCC(), nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(),
						fmt.format(phieuNhapThuoc.getDonGiaMua()), String.valueOf(phieuNhapThuoc.getSoLuongNhap()) });
			}
			if (lengthArray > 0) {
				double countPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
				lblPageIndex.setText("1" + "/" + String.valueOf((int) countPage));
			} else {
				lblPageIndex.setText("1" + "/" + "1");
			}
			if (lengthArray < soPhanTuMuonHienThi) {
				modelPhieuNhapThuoc.setRowCount(lengthArray);
			} else {
				modelPhieuNhapThuoc.setRowCount(soPhanTuMuonHienThi);
			}
			tablePhieuNhapThuoc.setModel(modelPhieuNhapThuoc);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenBang_Search(ArrayList<PhieuNhapThuoc> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			int soThuTu = 1;
			xoaHetDuLieuTrenTableModel();
			for (PhieuNhapThuoc phieuNhapThuoc : danhSachMuonCapNhat) {
				Thuoc thuoc = phieuNhapThuoc.getThuoc();
				NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
				NhanVien nhanVien = phieuNhapThuoc.getNhanVien();
				modelPhieuNhapThuoc.addRow(new Object[] { soThuTu++, phieuNhapThuoc.getMaPhieu(),
						sdf.format(phieuNhapThuoc.getNgayNhap()), thuoc.getMaThuoc(), thuoc.getTenThuoc(),
						sdf.format(phieuNhapThuoc.getNgaySanXuat()), sdf.format(phieuNhapThuoc.getNgayHetHan()),
						thuoc.getLoaiThuoc(), thuoc.getDonViThuoc(), thuoc.getXuatXu(), nhaCungCap.getMaNCC(),
						nhaCungCap.getTenNCC(), nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(),
						fmt.format(phieuNhapThuoc.getDonGiaMua()), String.valueOf(phieuNhapThuoc.getSoLuongNhap()) });
			}
			lblPageIndex.setText("1" + "/" + "1");
			modelPhieuNhapThuoc.setRowCount(lengthArray);
			tablePhieuNhapThuoc.setModel(modelPhieuNhapThuoc);
			capNhatTatCaComboBox();
		}
		else {
			lblPageIndex.setText("1" + "/" + "1");
			modelPhieuNhapThuoc.setRowCount(0);
			tablePhieuNhapThuoc.setModel(modelPhieuNhapThuoc);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenBang_Next(ArrayList<PhieuNhapThuoc> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			String string_SoTrangHienTai = lblPageIndex.getText().trim().split("/")[0];
			int soTrangHienTai = Integer.parseInt(string_SoTrangHienTai) + 1;
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			if (soTrangHienTai <= lastPage) {
				int soThuTu = ((soTrangHienTai - 1) * soPhanTuMuonHienThi) + 1;
				xoaHetDuLieuTrenTableModel();
				ArrayList<PhieuNhapThuoc> danhSachPNT = layDuLieuTrongTrang(danhSachMuonCapNhat, soTrangHienTai,
						(int) lastPage, soPhanTuMuonHienThi);
				for (PhieuNhapThuoc phieuNhapThuoc : danhSachMuonCapNhat) {
					Thuoc thuoc = phieuNhapThuoc.getThuoc();
					NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
					NhanVien nhanVien = phieuNhapThuoc.getNhanVien();
					modelPhieuNhapThuoc.addRow(new Object[] { soThuTu++, phieuNhapThuoc.getMaPhieu(),
							sdf.format(phieuNhapThuoc.getNgayNhap()), thuoc.getMaThuoc(), thuoc.getTenThuoc(),
							sdf.format(phieuNhapThuoc.getNgaySanXuat()), sdf.format(phieuNhapThuoc.getNgayHetHan()),
							thuoc.getLoaiThuoc(), thuoc.getDonViThuoc(), thuoc.getXuatXu(), nhaCungCap.getMaNCC(),
							nhaCungCap.getTenNCC(), nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(),
							fmt.format(phieuNhapThuoc.getDonGiaMua()),
							String.valueOf(phieuNhapThuoc.getSoLuongNhap()) });
				}
				lblPageIndex.setText(String.valueOf(soTrangHienTai) + "/" + String.valueOf((int) lastPage));
				modelPhieuNhapThuoc.setRowCount(danhSachPNT.size());
				tablePhieuNhapThuoc.setModel(modelPhieuNhapThuoc);
				capNhatTatCaComboBox();
			}
		}
	}

	private void capNhatDuLieuTrenBang_Previous(ArrayList<PhieuNhapThuoc> danhSachMuonCapNhat,
			int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			String soThuTuDauTien = String.valueOf(tablePhieuNhapThuoc.getValueAt(0, 0));
			String string_SoTrangHienTai = lblPageIndex.getText().trim().split("/")[0];
			int soTrangHienTai = Integer.parseInt(string_SoTrangHienTai) - 1;
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			if (soTrangHienTai > 0) {
				int soThuTu = Integer.parseInt(soThuTuDauTien) - soPhanTuMuonHienThi;
				xoaHetDuLieuTrenTableModel();
				ArrayList<PhieuNhapThuoc> danhSachPNT = layDuLieuTrongTrang(danhSachMuonCapNhat, soTrangHienTai,
						(int) lastPage, soPhanTuMuonHienThi);
				for (PhieuNhapThuoc phieuNhapThuoc : danhSachMuonCapNhat) {
					Thuoc thuoc = phieuNhapThuoc.getThuoc();
					NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
					NhanVien nhanVien = phieuNhapThuoc.getNhanVien();
					modelPhieuNhapThuoc.addRow(new Object[] { soThuTu++, phieuNhapThuoc.getMaPhieu(),
							sdf.format(phieuNhapThuoc.getNgayNhap()), thuoc.getMaThuoc(), thuoc.getTenThuoc(),
							sdf.format(phieuNhapThuoc.getNgaySanXuat()), sdf.format(phieuNhapThuoc.getNgayHetHan()),
							thuoc.getLoaiThuoc(), thuoc.getDonViThuoc(), thuoc.getXuatXu(), nhaCungCap.getMaNCC(),
							nhaCungCap.getTenNCC(), nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(),
							fmt.format(phieuNhapThuoc.getDonGiaMua()),
							String.valueOf(phieuNhapThuoc.getSoLuongNhap()) });
				}
				lblPageIndex.setText(String.valueOf(soTrangHienTai) + "/" + String.valueOf((int) lastPage));
				modelPhieuNhapThuoc.setRowCount(danhSachPNT.size());
				tablePhieuNhapThuoc.setModel(modelPhieuNhapThuoc);
				capNhatTatCaComboBox();
			}
		}
	}

	private void capNhatDuLieuTrenBang_Last(ArrayList<PhieuNhapThuoc> danhSachMuonCapNhat, int soPhanTuMuonHienThi) {
		int lengthArray = danhSachMuonCapNhat.size();
		if (lengthArray > 0) {
			double lastPage = Math.ceil(lengthArray * 1.0 / soPhanTuMuonHienThi * 1.0);
			int last_index = (int) (soPhanTuMuonHienThi * (lastPage - 1));
			int soThuTu = last_index + 1;
			xoaHetDuLieuTrenTableModel();
			ArrayList<PhieuNhapThuoc> danhSachKhachHang = new ArrayList<PhieuNhapThuoc>(
					danhSachMuonCapNhat.subList(last_index, lengthArray));
			for (PhieuNhapThuoc phieuNhapThuoc : danhSachMuonCapNhat) {
				Thuoc thuoc = phieuNhapThuoc.getThuoc();
				NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
				NhanVien nhanVien = phieuNhapThuoc.getNhanVien();
				modelPhieuNhapThuoc.addRow(new Object[] { soThuTu++, phieuNhapThuoc.getMaPhieu(),
						sdf.format(phieuNhapThuoc.getNgayNhap()), thuoc.getMaThuoc(), thuoc.getTenThuoc(),
						sdf.format(phieuNhapThuoc.getNgaySanXuat()), sdf.format(phieuNhapThuoc.getNgayHetHan()),
						thuoc.getLoaiThuoc(), thuoc.getDonViThuoc(), thuoc.getXuatXu(), nhaCungCap.getMaNCC(),
						nhaCungCap.getTenNCC(), nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(),
						fmt.format(phieuNhapThuoc.getDonGiaMua()), String.valueOf(phieuNhapThuoc.getSoLuongNhap()) });
			}
			lblPageIndex.setText(String.valueOf((int) lastPage) + "/" + String.valueOf((int) lastPage));
			modelPhieuNhapThuoc.setRowCount(danhSachKhachHang.size());
			tablePhieuNhapThuoc.setModel(modelPhieuNhapThuoc);
			capNhatTatCaComboBox();
		}
	}

	private void capNhatDuLieuTrenComboBoxMaPhieuNhapThuoc() {
		cbMaPhieuNhap.removeAllItems();
		if (danhSachTatCaPhieuNhapThuoc.size() > 0) {
			for (PhieuNhapThuoc phieuNhapThuoc : danhSachTatCaPhieuNhapThuoc) {
				String maPhieuNhapThuoc = phieuNhapThuoc.getMaPhieu();
				cbMaPhieuNhap.addItem(maPhieuNhapThuoc);
			}
		}
		cbMaPhieuNhap.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxMaPhieuNhapThuoc() {
		ArrayList<String> danhSachPhieuNhapThuoc = new ArrayList<String>();
		for (PhieuNhapThuoc phieuNhapThuoc : danhSachTatCaPhieuNhapThuoc) {
			String maPhieuNhapThuoc = phieuNhapThuoc.getMaPhieu();
			if (!danhSachPhieuNhapThuoc.contains(maPhieuNhapThuoc)) {
				danhSachPhieuNhapThuoc.add(maPhieuNhapThuoc);
			}
		}
		return danhSachPhieuNhapThuoc;
	}

	private void capNhatDuLieuTrenComboBoxMaThuoc() {
		cbMaThuoc.removeAllItems();
		if (danhSachTatCaPhieuNhapThuoc.size() > 0) {
			ArrayList<String> danhSachThuoc = new ArrayList<String>();
			for (PhieuNhapThuoc phieuNhapThuoc : danhSachTatCaPhieuNhapThuoc) {
				Thuoc thuoc = phieuNhapThuoc.getThuoc();
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
		for (PhieuNhapThuoc phieuNhapThuoc : danhSachTatCaPhieuNhapThuoc) {
			Thuoc thuoc = phieuNhapThuoc.getThuoc();
			String maThuoc = thuoc.getMaThuoc();
			String tenThuoc = thuoc.getTenThuoc();
			String s = maThuoc + " - " + tenThuoc;
			if (!danhSachThuoc.contains(s)) {
				danhSachThuoc.add(s);
			}
		}
		return danhSachThuoc;
	}

	private void capNhatDuLieuTrenComboBoxMaNhaCungCap() {
		cbMaNhaCungCap.removeAllItems();
		if (danhSachTatCaPhieuNhapThuoc.size() > 0) {
			ArrayList<String> cacNhaCungCap = new ArrayList<String>();
			for (PhieuNhapThuoc phieuNhapThuoc : danhSachTatCaPhieuNhapThuoc) {
				NhaCungCap nhaCungCap = phieuNhapThuoc.getThuoc().getNhaCungCap();
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
		for (PhieuNhapThuoc phieuNhapThuoc : danhSachTatCaPhieuNhapThuoc) {
			NhaCungCap nhaCungCap = phieuNhapThuoc.getThuoc().getNhaCungCap();
			String maNhaCungCap = nhaCungCap.getMaNCC();
			String tenNhaCungCap = nhaCungCap.getTenNCC();
			String s = maNhaCungCap + " - " + tenNhaCungCap;
			if (!danhSachNhaCungCap.contains(s)) {
				danhSachNhaCungCap.add(s);
			}
		}
		return danhSachNhaCungCap;
	}

	private void capNhatDuLieuTrenComboBoxMaNhanVien() {
		cbMaNhanVien.removeAllItems();
		if (danhSachTatCaPhieuNhapThuoc.size() > 0) {
			ArrayList<String> danhSachNhanVien = new ArrayList<String>();
			for (PhieuNhapThuoc phieuNhapThuoc : danhSachTatCaPhieuNhapThuoc) {
				NhanVien nhanVien = phieuNhapThuoc.getNhanVien();
				String maNhanVien = nhanVien.getMaNV();
				String tenNhanVien = nhanVien.getHoTenNhanVien();
				String s = maNhanVien + " - " + tenNhanVien;
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
		for (PhieuNhapThuoc phieuNhapThuoc : danhSachTatCaPhieuNhapThuoc) {
			NhanVien nhanVien = phieuNhapThuoc.getNhanVien();
			String maNhanVien = nhanVien.getMaNV();
			String tenNhanVien = nhanVien.getHoTenNhanVien();
			String s = maNhanVien + " - " + tenNhanVien;
			if (!danhSachNhanVien.contains(s)) {
				danhSachNhanVien.add(s);
			}
		}
		return danhSachNhanVien;
	}

	private void capNhatDuLieuTrenComboBoxLoaiThuoc() {
		cbLoaiThuoc.removeAllItems();
		if (danhSachTatCaPhieuNhapThuoc.size() > 0) {
			ArrayList<String> cacLoaiThuoc = new ArrayList<String>();
			for (PhieuNhapThuoc phieuNhapThuoc : danhSachTatCaPhieuNhapThuoc) {
				String loaiThuoc = phieuNhapThuoc.getThuoc().getLoaiThuoc();
				if (!cacLoaiThuoc.contains(loaiThuoc)) {
					cacLoaiThuoc.add(loaiThuoc);
					cbLoaiThuoc.addItem(loaiThuoc);
				}
			}
		}
		cbLoaiThuoc.setSelectedIndex(-1);
	}

	private void capNhatDuLieuTrenComboBoxDonViThuoc() {
		cbDonViThuoc.removeAllItems();
		if (danhSachTatCaPhieuNhapThuoc.size() > 0) {
			ArrayList<String> cacDonViThuoc = new ArrayList<String>();
			for (PhieuNhapThuoc phieuNhapThuoc : danhSachTatCaPhieuNhapThuoc) {
				String donViThuoc = phieuNhapThuoc.getThuoc().getDonViThuoc();
				if (!cacDonViThuoc.contains(donViThuoc)) {
					cacDonViThuoc.add(donViThuoc);
					cbDonViThuoc.addItem(donViThuoc);
				}
			}
		}
		cbDonViThuoc.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxDonViThuoc() {
		ArrayList<String> danhSachDonViThuoc = new ArrayList<String>();
		for (PhieuNhapThuoc phieuNhapThuoc : danhSachTatCaPhieuNhapThuoc) {
			String donViThuoc = phieuNhapThuoc.getThuoc().getDonViThuoc();
			if (!danhSachDonViThuoc.contains(donViThuoc)) {
				danhSachDonViThuoc.add(donViThuoc);
			}
		}
		return danhSachDonViThuoc;
	}

	private void capNhatDuLieuTrenComboBoxXuatXu() {
		cbXuatXu.removeAllItems();
		if (danhSachTatCaPhieuNhapThuoc.size() > 0) {
			ArrayList<String> cacXuatXu = new ArrayList<String>();
			for (PhieuNhapThuoc phieuNhapThuoc : danhSachTatCaPhieuNhapThuoc) {
				String xuatXu = phieuNhapThuoc.getThuoc().getXuatXu();
				if (!cacXuatXu.contains(xuatXu)) {
					cacXuatXu.add(xuatXu);
					cbXuatXu.addItem(xuatXu);
				}
			}
		}
		cbXuatXu.setSelectedItem("");
	}

	private ArrayList<String> layToanBoDuLieuTrenComboBoxXuatXu() {
		ArrayList<String> danhSachXuatXu = new ArrayList<String>();
		for (PhieuNhapThuoc phieuNhapThuoc : danhSachTatCaPhieuNhapThuoc) {
			String xuatXu = phieuNhapThuoc.getThuoc().getXuatXu();
			if (!danhSachXuatXu.contains(xuatXu)) {
				danhSachXuatXu.add(xuatXu);
			}
		}
		return danhSachXuatXu;
	}

	private void capNhatTatCaComboBox() {
		capNhatDuLieuTrenComboBoxMaPhieuNhapThuoc();
		capNhatDuLieuTrenComboBoxMaThuoc();
		capNhatDuLieuTrenComboBoxMaNhaCungCap();
		capNhatDuLieuTrenComboBoxMaNhanVien();
		capNhatDuLieuTrenComboBoxLoaiThuoc();
		capNhatDuLieuTrenComboBoxDonViThuoc();
		capNhatDuLieuTrenComboBoxXuatXu();
	}

	private PhieuNhapThuoc layPhieuNhapThuocByMaPhieu(String maPhieuNhapThuoc,
			ArrayList<PhieuNhapThuoc> danhSachHienTai) {
		for (PhieuNhapThuoc phieuNhapThuoc : danhSachHienTai) {
			if (phieuNhapThuoc.getMaPhieu().equalsIgnoreCase(maPhieuNhapThuoc)) {
				return phieuNhapThuoc;
			}
		}
		return null;
	}

	private void xoaTrang() {
		cbMaPhieuNhap.setSelectedItem("");
		datePickerNgayLapPhieu.getJFormattedTextField().setText("");
		datePickerNgaySanXuat.getJFormattedTextField().setText("");
		datePickerNgayHetHan.getJFormattedTextField().setText("");
		cbMaThuoc.setSelectedItem("");
		cbMaNhanVien.setSelectedItem("");
		cbMaNhaCungCap.setSelectedItem("");
		cbLoaiThuoc.setSelectedIndex(-1);
		cbDonViThuoc.setSelectedItem("");
		cbXuatXu.setSelectedItem("");
		cbMaPhieuNhap.requestFocus();
	}

	private void fillForm(int row) {
		if (row != -1) {
			String maPhieuNhapThuoc = String.valueOf(tablePhieuNhapThuoc.getValueAt(row, 1));
			PhieuNhapThuoc phieuNhapThuoc = new PhieuNhapThuoc(maPhieuNhapThuoc);
			ArrayList<PhieuNhapThuoc> dsPhieuNhapThuoc = danhSachTatCaPhieuNhapThuoc;
			if (dsPhieuNhapThuoc.contains(phieuNhapThuoc)) {
				phieuNhapThuoc = dsPhieuNhapThuoc.get(danhSachTatCaPhieuNhapThuoc.indexOf(phieuNhapThuoc));
				Thuoc thuoc = phieuNhapThuoc.getThuoc();
				NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
				NhanVien nhanVien = phieuNhapThuoc.getNhanVien();

				cbMaPhieuNhap.removeAllItems();
				cbMaPhieuNhap.addItem(maPhieuNhapThuoc);
				cbMaPhieuNhap.setSelectedItem(maPhieuNhapThuoc);

				Date ngayLapPhieu = phieuNhapThuoc.getNgayNhap();
				model_ngaylapphieu.setValue(ngayLapPhieu);
				datePickerNgayLapPhieu.getJFormattedTextField().setText(sdf.format(ngayLapPhieu));

				String maThuoc = thuoc.getMaThuoc();
				cbMaThuoc.removeAllItems();
				cbMaThuoc.addItem(maThuoc);
				cbMaThuoc.setSelectedItem(maThuoc);

				String maNhaCungCap = nhaCungCap.getMaNCC();
				cbMaNhaCungCap.removeAllItems();
				cbMaNhaCungCap.addItem(maNhaCungCap);
				cbMaNhaCungCap.setSelectedItem(maNhaCungCap);

				String maNhanVien = nhanVien.getMaNV();
				cbMaNhanVien.removeAllItems();
				cbMaNhanVien.addItem(maNhanVien);
				cbMaNhanVien.setSelectedItem(maNhanVien);

				Date ngaySanXuat = phieuNhapThuoc.getNgaySanXuat();
				model_ngaysanxuat.setValue(ngaySanXuat);
				datePickerNgaySanXuat.getJFormattedTextField().setText(sdf.format(ngaySanXuat));

				Date ngayHetHan = phieuNhapThuoc.getNgayHetHan();
				model_ngayhethan.setValue(ngayHetHan);
				datePickerNgayHetHan.getJFormattedTextField().setText(sdf.format(ngayHetHan));

				cbLoaiThuoc.setSelectedItem(thuoc.getLoaiThuoc());

				String donViThuoc = thuoc.getDonViThuoc();
				cbDonViThuoc.removeAllItems();
				cbDonViThuoc.addItem(donViThuoc);
				cbDonViThuoc.setSelectedItem(donViThuoc);

				String xuatXu = thuoc.getXuatXu();
				cbXuatXu.removeAllItems();
				cbXuatXu.addItem(xuatXu);
				cbXuatXu.setSelectedItem(xuatXu);
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public TimPhieuNhapThuoc_GUI(NhanVien e) {
		emp = e;
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

		btnXemChiTiet = new JButton("Xem chi tiết phiếu nhập thuốc");
		btnXemChiTiet.setBackground(Color.WHITE);
		btnXemChiTiet.setIcon(new ImageIcon("data\\icons\\more-details-16.png"));
		btnXemChiTiet.setFocusable(false);
		panel.add(btnXemChiTiet);

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
		lblTitle.setIcon(new ImageIcon("data\\icons\\search-import-medicine-title.png"));
		lblTitle.setBounds(426, 10, 719, 93);
		panel_2.add(lblTitle);

		JPanel panel_chart = new JPanel();
		panel_chart.setBackground(Color.WHITE);
		panel_chart.setBounds(0, 109, 1522, 651);
		panel_chart.setLayout(null);
		panel_2.add(panel_chart);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(10, 10, 1502, 276);
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_chart.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblThongTinPhieuNhapThuoc = new JLabel("Thông tin phiếu nhập thuốc");
		lblThongTinPhieuNhapThuoc.setForeground(SystemColor.textHighlight);
		lblThongTinPhieuNhapThuoc.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinPhieuNhapThuoc.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinPhieuNhapThuoc.setBounds(10, 10, 1482, 41);
		panel_3.add(lblThongTinPhieuNhapThuoc);

		lblMaPhieuNhap = new JLabel("Mã phiếu nhập:");
		lblMaPhieuNhap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaPhieuNhap.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaPhieuNhap.setBounds(7, 61, 142, 26);
		panel_3.add(lblMaPhieuNhap);

		lblNgayLapPhieu = new JLabel("Ngày lập phiếu:");
		lblNgayLapPhieu.setBounds(788, 61, 139, 26);
		lblNgayLapPhieu.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgayLapPhieu.setFont(new Font("Arial", Font.BOLD, 16));
		panel_3.add(lblNgayLapPhieu);

		p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		model_ngaylapphieu = new UtilDateModel();
		datePanelNgayLapPhieu = new JDatePanelImpl(model_ngaylapphieu, p);
		datePickerNgayLapPhieu = new JDatePickerImpl(datePanelNgayLapPhieu, new DateLabelFormatter());
		SpringLayout springLayout_1 = (SpringLayout) datePickerNgayLapPhieu.getLayout();
		springLayout_1.putConstraint(SpringLayout.SOUTH, datePickerNgayLapPhieu.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, datePickerNgayLapPhieu);
		datePickerNgayLapPhieu.setBounds(932, 58, 521, 29);
		datePickerNgayLapPhieu.setFocusable(false);
		panel_3.add(datePickerNgayLapPhieu);

		cbMaPhieuNhap = new JComboBox();
		cbMaPhieuNhap.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaPhieuNhap.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaPhieuNhap.setEditable(true);
		cbMaPhieuNhap.setBackground(Color.WHITE);
		cbMaPhieuNhap.setBounds(151, 60, 571, 29);
		panel_3.add(cbMaPhieuNhap);

		lblNgayHetHan = new JLabel("Ngày hết hạn:");
		lblNgayHetHan.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgayHetHan.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgayHetHan.setBounds(788, 105, 139, 26);
		panel_3.add(lblNgayHetHan);

		model_ngayhethan = new UtilDateModel();
		datePanelNgayHetHan = new JDatePanelImpl(model_ngayhethan, p);
		datePickerNgayHetHan = new JDatePickerImpl(datePanelNgayHetHan, new DateLabelFormatter());
		SpringLayout springLayout_3 = (SpringLayout) datePickerNgayHetHan.getLayout();
		springLayout_3.putConstraint(SpringLayout.SOUTH, datePickerNgayHetHan.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, datePickerNgayHetHan);
		datePickerNgayHetHan.setBounds(932, 102, 521, 29);
		datePickerNgayHetHan.setFocusable(false);
		panel_3.add(datePickerNgayHetHan);

		JLabel lblNgaySanXuat = new JLabel("Ngày sản xuất:");
		lblNgaySanXuat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgaySanXuat.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgaySanXuat.setBounds(10, 105, 139, 26);
		panel_3.add(lblNgaySanXuat);

		model_ngaysanxuat = new UtilDateModel();
		datePanelNgaySanXuat = new JDatePanelImpl(model_ngaysanxuat, p);
		datePickerNgaySanXuat = new JDatePickerImpl(datePanelNgaySanXuat, new DateLabelFormatter());
		SpringLayout springLayout_2 = (SpringLayout) datePickerNgaySanXuat.getLayout();
		springLayout_2.putConstraint(SpringLayout.SOUTH, datePickerNgaySanXuat.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, datePickerNgaySanXuat);
		datePickerNgaySanXuat.setBounds(151, 105, 571, 29);
		datePickerNgaySanXuat.setFocusable(false);
		panel_3.add(datePickerNgaySanXuat);

		lblMaNhaCungCap = new JLabel("Mã nhà cung cấp:");
		lblMaNhaCungCap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaNhaCungCap.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaNhaCungCap.setBounds(10, 191, 139, 26);
		panel_3.add(lblMaNhaCungCap);

		btnSearchMaPhieuNhap = new JButton();
		btnSearchMaPhieuNhap.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchMaPhieuNhap.setToolTipText("Tìm kiếm phiếu nhập thuốc theo mã phiếu nhập thuốc");
		btnSearchMaPhieuNhap.setFocusable(false);
		btnSearchMaPhieuNhap.setBorder(BorderFactory.createEmptyBorder());
		btnSearchMaPhieuNhap.setBackground(Color.WHITE);
		btnSearchMaPhieuNhap.setBounds(732, 61, 31, 31);
		panel_3.add(btnSearchMaPhieuNhap);

		btnSearchNgayLapPhieuNhap = new JButton();
		btnSearchNgayLapPhieuNhap.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchNgayLapPhieuNhap.setToolTipText("Tìm kiếm phiếu nhập thuốc theo ngày lập phiếu nhập thuốc");
		btnSearchNgayLapPhieuNhap.setFocusable(false);
		btnSearchNgayLapPhieuNhap.setBorder(BorderFactory.createEmptyBorder());
		btnSearchNgayLapPhieuNhap.setBackground(Color.WHITE);
		btnSearchNgayLapPhieuNhap.setBounds(1461, 56, 31, 31);
		panel_3.add(btnSearchNgayLapPhieuNhap);

		btnSearchNgaySanXuat = new JButton();
		btnSearchNgaySanXuat.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchNgaySanXuat.setToolTipText("Tìm kiếm phiếu nhập thuốc theo ngày sản xuất");
		btnSearchNgaySanXuat.setFocusable(false);
		btnSearchNgaySanXuat.setBorder(BorderFactory.createEmptyBorder());
		btnSearchNgaySanXuat.setBackground(Color.WHITE);
		btnSearchNgaySanXuat.setBounds(732, 100, 31, 31);
		panel_3.add(btnSearchNgaySanXuat);

		btnSearchNgayHetHan = new JButton();
		btnSearchNgayHetHan.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchNgayHetHan.setToolTipText("Tìm kiếm phiếu nhập thuốc theo ngày hết hạn");
		btnSearchNgayHetHan.setFocusable(false);
		btnSearchNgayHetHan.setBorder(BorderFactory.createEmptyBorder());
		btnSearchNgayHetHan.setBackground(Color.WHITE);
		btnSearchNgayHetHan.setBounds(1461, 100, 31, 31);
		panel_3.add(btnSearchNgayHetHan);

		lblMaThuoc = new JLabel("Mã thuốc:");
		lblMaThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaThuoc.setBounds(7, 149, 142, 26);
		panel_3.add(lblMaThuoc);

		cbMaThuoc = new JComboBox();
		cbMaThuoc.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaThuoc.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaThuoc.setEditable(true);
		cbMaThuoc.setBackground(Color.WHITE);
		cbMaThuoc.setBounds(151, 148, 571, 29);
		panel_3.add(cbMaThuoc);

		btnSearchMaThuoc = new JButton();
		btnSearchMaThuoc.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchMaThuoc.setToolTipText("Tìm kiếm phiếu nhập thuốc theo mã thuốc");
		btnSearchMaThuoc.setFocusable(false);
		btnSearchMaThuoc.setBorder(BorderFactory.createEmptyBorder());
		btnSearchMaThuoc.setBackground(Color.WHITE);
		btnSearchMaThuoc.setBounds(732, 149, 31, 31);
		panel_3.add(btnSearchMaThuoc);

		btnSearchMaNhanVien = new JButton();
		btnSearchMaNhanVien.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchMaNhanVien.setToolTipText("Tìm kiếm phiếu nhập thuốc theo theo mã nhân viên");
		btnSearchMaNhanVien.setFocusable(false);
		btnSearchMaNhanVien.setBorder(BorderFactory.createEmptyBorder());
		btnSearchMaNhanVien.setBackground(Color.WHITE);
		btnSearchMaNhanVien.setBounds(1461, 146, 31, 31);
		panel_3.add(btnSearchMaNhanVien);

		cbMaNhaCungCap = new JComboBox();
		cbMaNhaCungCap.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaNhaCungCap.setEditable(true);
		cbMaNhaCungCap.setBackground(Color.WHITE);
		cbMaNhaCungCap.setBounds(151, 190, 571, 29);
		panel_3.add(cbMaNhaCungCap);

		btnSearchMaNhaCungCap = new JButton();
		btnSearchMaNhaCungCap.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchMaNhaCungCap.setToolTipText("Tìm kiếm phiếu nhập thuốc theo mã nhà cung cấp");
		btnSearchMaNhaCungCap.setFocusable(false);
		btnSearchMaNhaCungCap.setBorder(BorderFactory.createEmptyBorder());
		btnSearchMaNhaCungCap.setBackground(Color.WHITE);
		btnSearchMaNhaCungCap.setBounds(732, 191, 31, 31);
		panel_3.add(btnSearchMaNhaCungCap);

		lblMaNhanVien = new JLabel("Mã nhân viên:");
		lblMaNhanVien.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaNhanVien.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaNhanVien.setBounds(788, 149, 139, 26);
		panel_3.add(lblMaNhanVien);

		cbMaNhanVien = new JComboBox();
		cbMaNhanVien.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaNhanVien.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaNhanVien.setEditable(true);
		cbMaNhanVien.setBackground(Color.WHITE);
		cbMaNhanVien.setBounds(932, 148, 521, 29);
		panel_3.add(cbMaNhanVien);

		JLabel lblLoaiThuoc = new JLabel("Loại thuốc:");
		lblLoaiThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoaiThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblLoaiThuoc.setBounds(788, 194, 139, 26);
		panel_3.add(lblLoaiThuoc);

		cbLoaiThuoc = new JComboBox();
		cbLoaiThuoc.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbLoaiThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		cbLoaiThuoc.setBackground(Color.WHITE);
		cbLoaiThuoc.setBounds(932, 193, 521, 29);
		panel_3.add(cbLoaiThuoc);

		btnSearchLoaiThuoc = new JButton();
		btnSearchLoaiThuoc.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchLoaiThuoc.setToolTipText("Tìm kiếm phiếu nhập thuốc theo loại thuốc");
		btnSearchLoaiThuoc.setFocusable(false);
		btnSearchLoaiThuoc.setBorder(BorderFactory.createEmptyBorder());
		btnSearchLoaiThuoc.setBackground(Color.WHITE);
		btnSearchLoaiThuoc.setBounds(1461, 191, 31, 31);
		panel_3.add(btnSearchLoaiThuoc);

		JLabel lblDonViThuoc = new JLabel("Đơn vị thuốc:");
		lblDonViThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDonViThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblDonViThuoc.setBounds(10, 233, 139, 26);
		panel_3.add(lblDonViThuoc);

		cbDonViThuoc = new JComboBox();
		cbDonViThuoc.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbDonViThuoc.setFont(new Font("Arial", Font.PLAIN, 16));
		cbDonViThuoc.setEditable(true);
		cbDonViThuoc.setBackground(Color.WHITE);
		cbDonViThuoc.setBounds(151, 232, 571, 29);
		panel_3.add(cbDonViThuoc);

		btnSearchDonViThuoc = new JButton();
		btnSearchDonViThuoc.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchDonViThuoc.setToolTipText("Tìm kiếm phiếu nhập thuốc theo đơn vị thuốc");
		btnSearchDonViThuoc.setFocusable(false);
		btnSearchDonViThuoc.setBorder(BorderFactory.createEmptyBorder());
		btnSearchDonViThuoc.setBackground(Color.WHITE);
		btnSearchDonViThuoc.setBounds(732, 233, 31, 31);
		panel_3.add(btnSearchDonViThuoc);

		JLabel lblXuatXu = new JLabel("Xuất xứ:");
		lblXuatXu.setHorizontalAlignment(SwingConstants.RIGHT);
		lblXuatXu.setFont(new Font("Arial", Font.BOLD, 16));
		lblXuatXu.setBounds(788, 236, 139, 26);
		panel_3.add(lblXuatXu);

		cbXuatXu = new JComboBox();
		cbXuatXu.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbXuatXu.setFont(new Font("Arial", Font.PLAIN, 16));
		cbXuatXu.setEditable(true);
		cbXuatXu.setBackground(Color.WHITE);
		cbXuatXu.setBounds(932, 235, 521, 29);
		panel_3.add(cbXuatXu);

		btnSearchXuatXu = new JButton();
		btnSearchXuatXu.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnSearchXuatXu.setToolTipText("Tìm kiếm phiếu nhập thuốc theo xuất xứ");
		btnSearchXuatXu.setFocusable(false);
		btnSearchXuatXu.setBorder(BorderFactory.createEmptyBorder());
		btnSearchXuatXu.setBackground(Color.WHITE);
		btnSearchXuatXu.setBounds(1461, 233, 31, 31);
		panel_3.add(btnSearchXuatXu);

		String[] headers = { "STT", "Mã phiếu nhập", "Ngày lập phiếu", "Mã thuốc", "Tên thuốc", "Ngày sản xuất",
				"Ngày hết hạn", "Loại thuốc", "Đơn vị thuốc", "Xuất xứ", "Mã nhà cung cấp", "Tên nhà cung cấp",
				"Mã nhân viên", "Tên nhân viên", "Đơn giá mua", "Số lượng nhập" };
		modelPhieuNhapThuoc = new DefaultTableModel(headers, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tablePhieuNhapThuoc = new JTable(modelPhieuNhapThuoc);
		tablePhieuNhapThuoc.getTableHeader().setBackground(Color.CYAN);
		tablePhieuNhapThuoc.getTableHeader().setFont(new Font(null, Font.PLAIN, 14));
		tablePhieuNhapThuoc.setSelectionBackground(Color.YELLOW);
		tablePhieuNhapThuoc.setSelectionForeground(Color.RED);
		tablePhieuNhapThuoc.setFont(new Font(null, Font.PLAIN, 14));
		tablePhieuNhapThuoc.setRowHeight(22);
		tablePhieuNhapThuoc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablePhieuNhapThuoc.setToolTipText("Nhấn đúp chuột vào dòng bất kỳ để xem chi tiết phiếu nhập thuốc");
		tablePhieuNhapThuoc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = tablePhieuNhapThuoc.getSelectedRow();
					String maPhieuNhapThuoc = String.valueOf(tablePhieuNhapThuoc.getValueAt(row, 1));
					PhieuNhapThuoc phieuNhapThuoc = layPhieuNhapThuocByMaPhieu(maPhieuNhapThuoc,
							danhSachTatCaPhieuNhapThuoc);
					if (phieuNhapThuoc != null) {
						XoaPhieuNhapThuoc_GUI xoaPhieuNhapThuoc = new XoaPhieuNhapThuoc_GUI(phieuNhapThuoc, emp);
						xoaPhieuNhapThuoc.setVisible(true);
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Không tìm thấy phiếu nhập thuốc có mã bạn đã chọn",
								"Thông báo", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(240, 240, 240));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 296, 1502, 311);
		panel_chart.add(scrollPane);
		scrollPane.setViewportView(tablePhieuNhapThuoc);

		btnFirstPage = new JButton("");
		btnFirstPage.setIcon(new ImageIcon("data\\icons\\first-page-32.png"));
		btnFirstPage.setBounds(661, 617, 24, 24);
		panel_chart.add(btnFirstPage);

		btnPreviousPage = new JButton("");
		btnPreviousPage.setIcon(new ImageIcon("data\\icons\\previous-page-32.png"));
		btnPreviousPage.setBounds(695, 617, 24, 24);
		panel_chart.add(btnPreviousPage);

		lblPageIndex = new JLabel();
		lblPageIndex.setHorizontalAlignment(SwingConstants.CENTER);
		lblPageIndex.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPageIndex.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPageIndex.setBounds(729, 616, 67, 25);
		panel_chart.add(lblPageIndex);

		btnNextPage = new JButton("");
		btnNextPage.setIcon(new ImageIcon("data\\icons\\next-page-32.png"));
		btnNextPage.setBounds(806, 616, 24, 24);
		panel_chart.add(btnNextPage);

		btnLastPage = new JButton("");
		btnLastPage.setIcon(new ImageIcon("data\\icons\\last-index-32.png"));
		btnLastPage.setBounds(840, 616, 24, 24);
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

		setTitle("Tìm thông tin phiếu nhập thuốc");
		Toolkit tk = Toolkit.getDefaultToolkit();
		setSize(tk.getScreenSize().width, tk.getScreenSize().height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);

		PhieuNhapThuoc_DAO phieunhapthuoc_dao = new PhieuNhapThuoc_DAO();
		danhSachTatCaPhieuNhapThuoc = phieunhapthuoc_dao.layTatCaPhieuNhapThuoc();
		if (danhSachTatCaPhieuNhapThuoc != null) {
			capNhatDuLieuTrenBang_Init(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
		} else {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Chưa có phiếu nhập thuốc nào", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		}

		cbMaPhieuNhap.addActionListener(this);
		btnSearchMaPhieuNhap.addActionListener(this);
		btnSearchNgayLapPhieuNhap.addActionListener(this);
		btnSearchNgaySanXuat.addActionListener(this);
		btnSearchNgayHetHan.addActionListener(this);
		cbMaThuoc.addActionListener(this);
		btnSearchMaThuoc.addActionListener(this);
		cbMaNhanVien.addActionListener(this);
		btnSearchMaNhanVien.addActionListener(this);
		cbMaNhaCungCap.addActionListener(this);
		btnSearchMaNhaCungCap.addActionListener(this);
		btnSearchLoaiThuoc.addActionListener(this);
		cbDonViThuoc.addActionListener(this);
		btnSearchDonViThuoc.addActionListener(this);
		cbXuatXu.addActionListener(this);
		btnSearchXuatXu.addActionListener(this);
		btnXemChiTiet.addActionListener(this);
		btnNextPage.addActionListener(this);
		btnPreviousPage.addActionListener(this);
		btnLastPage.addActionListener(this);
		btnFirstPage.addActionListener(this);
		btnReset.addActionListener(this);
		tablePhieuNhapThuoc.getSelectionModel().addListSelectionListener(this);
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
		int row = tablePhieuNhapThuoc.getSelectedRow();
		fillForm(row);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(cbMaPhieuNhap)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxMaPhieuNhapThuoc();
				int soPhanTuHienCo = cbMaPhieuNhap.getItemCount();
				String tuHienTai = cbMaPhieuNhap.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxMaPhieuNhapThuoc();
						cbMaPhieuNhap.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbMaPhieuNhap.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbMaPhieuNhap.addItem(cacUngVien.get(i));
							}
							cbMaPhieuNhap.setSelectedItem(tuHienTai);
							cbMaPhieuNhap.showPopup();
						} else if (soUngVien == 0) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbMaPhieuNhap,
									"Không tìm thấy mã phiếu nhập thuốc nào phù hợp", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxMaPhieuNhapThuoc();
							cbMaPhieuNhap.showPopup();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaPhieuNhap, "Không tìm thấy mã phiếu nhập thuốc nào", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbMaPhieuNhap.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchMaPhieuNhap)) {
			tablePhieuNhapThuoc.clearSelection();
			try {
				String maPhieuNhapThuocNhapVao = cbMaPhieuNhap.getSelectedItem().toString().trim();
				if (!maPhieuNhapThuocNhapVao.equalsIgnoreCase("")) {
					Iterator<PhieuNhapThuoc> it = danhSachTatCaPhieuNhapThuoc.iterator();
					while (it.hasNext()) {
						PhieuNhapThuoc phieuNhapThuoc = (PhieuNhapThuoc) it.next();
						String maPhieuNhapThuoc = phieuNhapThuoc.getMaPhieu();
						if (!maPhieuNhapThuoc.toLowerCase().contains(maPhieuNhapThuocNhapVao.toLowerCase())) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				e1.printStackTrace();
			}
		} else if (obj.equals(btnSearchNgayLapPhieuNhap)) {
			tablePhieuNhapThuoc.clearSelection();
			String string_NgayLapPhieuDangChon = datePickerNgayLapPhieu.getJFormattedTextField().getText();
			if (!string_NgayLapPhieuDangChon.equalsIgnoreCase("")) {
				LocalDate ngayLapPhieuDangChon = LocalDate.parse(string_NgayLapPhieuDangChon, dtf);
				Iterator<PhieuNhapThuoc> it = danhSachTatCaPhieuNhapThuoc.iterator();
				while (it.hasNext()) {
					PhieuNhapThuoc phieuNhapThuoc = (PhieuNhapThuoc) it.next();
					String string_NgayLapPhieu = sdf.format(phieuNhapThuoc.getNgayNhap());
					LocalDate ngayLapPhieu = LocalDate.parse(string_NgayLapPhieu, dtf);
					if (!ngayLapPhieu.equals(ngayLapPhieuDangChon)) {
						it.remove();
					}
				}
				capNhatDuLieuTrenBang_Search(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
			}
		} else if (obj.equals(btnSearchNgaySanXuat)) {
			tablePhieuNhapThuoc.clearSelection();
			String string_NgaySanXuatDangChon = datePickerNgaySanXuat.getJFormattedTextField().getText();
			if (!string_NgaySanXuatDangChon.equalsIgnoreCase("")) {
				LocalDate ngaySanXuatDangChon = LocalDate.parse(string_NgaySanXuatDangChon, dtf);
				Iterator<PhieuNhapThuoc> it = danhSachTatCaPhieuNhapThuoc.iterator();
				while (it.hasNext()) {
					PhieuNhapThuoc phieuNhapThuoc = (PhieuNhapThuoc) it.next();
					String string_NgaySanXuat = sdf.format(phieuNhapThuoc.getNgaySanXuat());
					LocalDate ngaySanXuat = LocalDate.parse(string_NgaySanXuat, dtf);
					if (!ngaySanXuat.equals(ngaySanXuatDangChon)) {
						it.remove();
					}
				}
				capNhatDuLieuTrenBang_Search(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
			}
		} else if (obj.equals(btnSearchNgayHetHan)) {
			tablePhieuNhapThuoc.clearSelection();
			String string_NgayHetHanDangChon = datePickerNgayHetHan.getJFormattedTextField().getText();
			if (!string_NgayHetHanDangChon.equalsIgnoreCase("")) {
				LocalDate ngayHetHanDangChon = LocalDate.parse(string_NgayHetHanDangChon, dtf);
				Iterator<PhieuNhapThuoc> it = danhSachTatCaPhieuNhapThuoc.iterator();
				while (it.hasNext()) {
					PhieuNhapThuoc phieuNhapThuoc = (PhieuNhapThuoc) it.next();
					String string_NgayHetHan = sdf.format(phieuNhapThuoc.getNgayHetHan());
					LocalDate ngayHetHan = LocalDate.parse(string_NgayHetHan, dtf);
					if (!ngayHetHan.equals(ngayHetHanDangChon)) {
						it.remove();
					}
				}
				capNhatDuLieuTrenBang_Search(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
			}
		} else if (obj.equals(cbMaThuoc)) {
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
		} else if (obj.equals(btnSearchMaThuoc)) {
			tablePhieuNhapThuoc.clearSelection();
			try {
				String maThuocNhapVao = cbMaThuoc.getSelectedItem().toString().trim();
				if (!maThuocNhapVao.equalsIgnoreCase("")) {
					Iterator<PhieuNhapThuoc> it = danhSachTatCaPhieuNhapThuoc.iterator();
					while (it.hasNext()) {
						PhieuNhapThuoc phieuNhapThuoc = (PhieuNhapThuoc) it.next();
						Thuoc thuoc = phieuNhapThuoc.getThuoc();
						String maThuoc = thuoc.getMaThuoc() + " - " + thuoc.getTenThuoc();
						if (!maThuoc.toLowerCase().contains(maThuocNhapVao.toLowerCase())) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				e1.printStackTrace();
			}
		} else if (obj.equals(cbMaNhanVien)) {
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
			tablePhieuNhapThuoc.clearSelection();
			try {
				String maNhanVienNhapVao = cbMaNhanVien.getSelectedItem().toString().trim();
				if (!maNhanVienNhapVao.equalsIgnoreCase("")) {
					Iterator<PhieuNhapThuoc> it = danhSachTatCaPhieuNhapThuoc.iterator();
					while (it.hasNext()) {
						PhieuNhapThuoc phieuNhapThuoc = (PhieuNhapThuoc) it.next();
						NhanVien nhanVien = phieuNhapThuoc.getNhanVien();
						String maNhanVien = nhanVien.getMaNV() + " - " + nhanVien.getHoTenNhanVien();
						if (!maNhanVien.toLowerCase().contains(maNhanVienNhapVao.toLowerCase())) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				cbMaNhanVien.setSelectedItem("");
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
		} else if (obj.equals(btnSearchMaNhaCungCap)) {
			tablePhieuNhapThuoc.clearSelection();
			try {
				String maNCCNhapVao = cbMaNhaCungCap.getSelectedItem().toString().trim();
				if (!maNCCNhapVao.equalsIgnoreCase("")) {
					Iterator<PhieuNhapThuoc> it = danhSachTatCaPhieuNhapThuoc.iterator();
					while (it.hasNext()) {
						PhieuNhapThuoc phieuNhapThuoc = (PhieuNhapThuoc) it.next();
						NhaCungCap nhaCungCap = phieuNhapThuoc.getThuoc().getNhaCungCap();
						String maNCC = nhaCungCap.getMaNCC() + " - " + nhaCungCap.getTenNCC();
						if (!maNCC.toLowerCase().contains(maNCCNhapVao.toLowerCase())) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				e1.printStackTrace();
			}
		} else if (obj.equals(btnSearchLoaiThuoc)) {
			tablePhieuNhapThuoc.clearSelection();
			try {
				String loaiThuocNhapVao = cbLoaiThuoc.getSelectedItem().toString().trim();
				if (!loaiThuocNhapVao.equalsIgnoreCase("")) {
					Iterator<PhieuNhapThuoc> it = danhSachTatCaPhieuNhapThuoc.iterator();
					while (it.hasNext()) {
						PhieuNhapThuoc phieuNhapThuoc = (PhieuNhapThuoc) it.next();
						String loaiThuoc = phieuNhapThuoc.getThuoc().getLoaiThuoc();
						if (!loaiThuoc.equalsIgnoreCase(loaiThuocNhapVao)) {
							it.remove();
						}
					}
				}
				capNhatDuLieuTrenBang_Search(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
			} catch (Exception e1) {
				cbLoaiThuoc.setSelectedItem("");
			}
		} else if (obj.equals(cbDonViThuoc)) {
			try {
				ArrayList<String> cacPhanTuHienCo = layToanBoDuLieuTrenComboBoxDonViThuoc();
				int soPhanTuHienCo = cbDonViThuoc.getItemCount();
				String tuHienTai = cbDonViThuoc.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						capNhatDuLieuTrenComboBoxDonViThuoc();
						cbDonViThuoc.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbDonViThuoc.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbDonViThuoc.addItem(cacUngVien.get(i));
							}
							cbDonViThuoc.setSelectedItem(tuHienTai);
							cbDonViThuoc.showPopup();
						} else if (soUngVien == 0) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(cbDonViThuoc, "Không tìm thấy đơn vị thuốc nào phù hợp",
									"Thông báo", JOptionPane.INFORMATION_MESSAGE);
							capNhatDuLieuTrenComboBoxDonViThuoc();
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbDonViThuoc, "Không tìm thấy đơn vị thuốc nào", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException e2) {
				cbDonViThuoc.setSelectedItem("");
			}
		} else if (obj.equals(btnSearchDonViThuoc)) {
			tablePhieuNhapThuoc.clearSelection();
			try {
				String donViThuocNhapVao = cbDonViThuoc.getSelectedItem().toString().trim();
				if (!donViThuocNhapVao.equalsIgnoreCase("")) {
					Iterator<PhieuNhapThuoc> it = danhSachTatCaPhieuNhapThuoc.iterator();
					while (it.hasNext()) {
						PhieuNhapThuoc phieuNhapThuoc = (PhieuNhapThuoc) it.next();
						String donViThuoc = phieuNhapThuoc.getThuoc().getDonViThuoc();
						if (!donViThuoc.toLowerCase().contains(donViThuocNhapVao.toLowerCase())) {
							it.remove();
						}
					}
					capNhatDuLieuTrenBang_Search(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
				}
			} catch (NullPointerException e1) {
				e1.printStackTrace();
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
		} else if (obj.equals(btnSearchXuatXu)) {
			tablePhieuNhapThuoc.clearSelection();
			try {
				String xuatXuNhapVao = cbXuatXu.getSelectedItem().toString().trim();
				if (!xuatXuNhapVao.equalsIgnoreCase("")) {
					Iterator<PhieuNhapThuoc> it = danhSachTatCaPhieuNhapThuoc.iterator();
					while (it.hasNext()) {
						PhieuNhapThuoc phieuNhapThuoc = (PhieuNhapThuoc) it.next();
						String xuatXu = phieuNhapThuoc.getThuoc().getXuatXu();
						if (!xuatXu.toLowerCase().contains(xuatXuNhapVao.toLowerCase())) {
							it.remove();
						}
					}
				}
				capNhatDuLieuTrenBang_Search(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
			} catch (NullPointerException e1) {
				e1.printStackTrace();
			}
		} else if (obj.equals(btnXemChiTiet)) {
			int row = tablePhieuNhapThuoc.getSelectedRow();
			if (row != -1) {
				String maPhieuNhapThuoc = String.valueOf(tablePhieuNhapThuoc.getValueAt(row, 1));
				PhieuNhapThuoc phieuNhapThuoc = layPhieuNhapThuocByMaPhieu(maPhieuNhapThuoc,
						danhSachTatCaPhieuNhapThuoc);
				if (phieuNhapThuoc != null) {
					XoaPhieuNhapThuoc_GUI xoaPhieuNhapThuoc = new XoaPhieuNhapThuoc_GUI(phieuNhapThuoc, emp);
					xoaPhieuNhapThuoc.setVisible(true);
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Không tìm thấy phiếu nhập thuốc có mã bạn đã chọn",
							"Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Bạn cần chọn phiếu nhập thuốc muốn xem chi tiết", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (obj.equals(btnNextPage)) {
			tablePhieuNhapThuoc.clearSelection();
			capNhatDuLieuTrenBang_Next(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
		} else if (obj.equals(btnPreviousPage)) {
			tablePhieuNhapThuoc.clearSelection();
			capNhatDuLieuTrenBang_Previous(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
		} else if (obj.equals(btnLastPage)) {
			tablePhieuNhapThuoc.clearSelection();
			capNhatDuLieuTrenBang_Last(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
		} else if (obj.equals(btnFirstPage)) {
			tablePhieuNhapThuoc.clearSelection();
			capNhatDuLieuTrenBang_Init(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
		} else if (obj.equals(btnReset)) {
			tablePhieuNhapThuoc.clearSelection();
			PhieuNhapThuoc_DAO phieunhapthuoc_dao = new PhieuNhapThuoc_DAO();
			danhSachTatCaPhieuNhapThuoc = phieunhapthuoc_dao.layTatCaPhieuNhapThuoc();
			capNhatDuLieuTrenBang_Init(danhSachTatCaPhieuNhapThuoc, soPhanTuMuonHienThi);
			xoaTrang();
		}
	}
}
