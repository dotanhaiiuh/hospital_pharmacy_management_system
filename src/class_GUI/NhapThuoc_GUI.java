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
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import java.awt.FlowLayout;

import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import class_DAO.NhaCungCap_DAO;
import class_DAO.PhieuNhapThuoc_DAO;
import class_DAO.Thuoc_DAO;
import class_Entities.NhanVien;
import class_Entities.PhieuNhapThuoc;
import class_Entities.Thuoc;
import class_Entities.NhaCungCap;
import class_Equipment.DateLabelFormatter;
import class_Equipment.RegularExpression;

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
import java.util.Properties;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ChangeEvent;

public class NhapThuoc_GUI extends JFrame implements WindowListener, ListSelectionListener {
	private JTextField txtTenThuoc;
	private Properties p;
	private JTextField txtDonViThuoc;
	private JTextField txtXuatXu;
	private DefaultTableModel modelPhieuNhapThuoc;
	private JTable tablePhieuNhapThuoc;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private DecimalFormat fmt = new DecimalFormat("###,###");

	private JComboBox<String> cbLoaiThuoc;

	private JButton btnHome;

	private UtilDateModel model_ngaysanxuat;

	private JDatePanelImpl datePanelNgaySanXuat;

	private JDatePickerImpl datePickerNgaySanXuat;

	private UtilDateModel model_ngayhethan;

	private JDatePanelImpl datePanelNgayHetHan;

	private JDatePickerImpl datePickerNgayHetHan;
	private JTextField txtDonGiaMua;
	private JTextField txtMaPhieuNhap;
	private JTextField txtSoLuongTon;
	private JTextField txtTenNhaCungCap;
	private JTextField txtEmail;
	private JTextField txtSoDienThoai;

	private JTextArea textAreaDiaChi;
	private JComboBox cbMaThuoc;
	private JComboBox cbMaNhaCungCap;
	private NhanVien emp;
	private JSpinner spinSoLuongNhap;

	private boolean use_event_cbMaThuoc = false;
	private boolean use_event_cbMaNhaCungCap = false;
	private boolean use_phatSinhMaThuoc = false;
	private boolean use_phatSinhMaNhaCungCap = false;
	private JLabel lblMaNhaCungCap;
	private JButton btnGenerateMaNCC;
	private JButton btnLuuNhaCungCap;
	private JTextField txtNgayHienTai;
	private JTextField txtNgayNhap;

	private JButton btnGenerateMaThuoc;
	private JLabel lblMaThuoc;
	private JTextField txtTongTienNhapThuoc;
	private JButton btnXoa;
	private JButton btnThem;

	private String layNgayHienTai() {
		Date ngayHienTai = new Date();
		return sdf.format(ngayHienTai);
	}

	private NhaCungCap taoNhaCungCap() {
		String maNCC = cbMaNhaCungCap.getSelectedItem().toString().split("-")[0].trim();
		String tenNCC = txtTenNhaCungCap.getText().trim();
		String email = txtEmail.getText().trim();
		String soDienThoai = txtSoDienThoai.getText().trim();
		String diaChi = textAreaDiaChi.getText().trim();
		return new NhaCungCap(maNCC, tenNCC, email, soDienThoai, diaChi);
	}

	private Thuoc taoThuoc() {
		String maThuoc = cbMaThuoc.getSelectedItem().toString().split("-")[0].trim();
		String tenThuoc = txtTenThuoc.getText().trim();
		String loaiThuoc = cbLoaiThuoc.getSelectedItem().toString().trim();
		String donViThuoc = txtDonViThuoc.getText().trim();
		String xuatXu = txtXuatXu.getText().trim();
		int soLuongTon = Integer.parseInt(txtSoLuongTon.getText().trim());
		int soLuongNhap = Integer.parseInt(spinSoLuongNhap.getValue().toString().trim());
		return new Thuoc(maThuoc, tenThuoc, loaiThuoc, donViThuoc, xuatXu, soLuongNhap + soLuongTon, taoNhaCungCap());
	}

	private PhieuNhapThuoc taoPhieuNhapThuoc() {
		String maPhieu = txtMaPhieuNhap.getText().trim();
		Date ngayNhap = new Date();
		Date ngaySanXuat = model_ngaysanxuat.getValue();
		Date ngayHetHan = model_ngayhethan.getValue();
		String soTienNhap = txtDonGiaMua.getText().trim();
		float donGiaMua = Float.parseFloat(loaiBoDinhDangTien(soTienNhap));
		int soLuongNhap = Integer.parseInt(spinSoLuongNhap.getValue().toString().trim());
		return new PhieuNhapThuoc(maPhieu, emp, taoThuoc(), ngayNhap, ngaySanXuat, ngayHetHan, donGiaMua, soLuongNhap);
	}

	private String phatSinhMaPhieuNhapThuoc() {
		try {
			PhieuNhapThuoc_DAO phieunhapthuoc_dao = new PhieuNhapThuoc_DAO();
			String maphieu_lastest = phieunhapthuoc_dao.layMaPhieuNhapThuocCuoiCung().trim();
			Date ngayHienTai = new Date();
			String maPhieuNhapThuoc = "PH" + sdf.format(ngayHienTai).split("-")[2];
			String stt_string_lastest = maphieu_lastest.substring(6, maphieu_lastest.length());
			int stt_int_lastest = Integer.parseInt(stt_string_lastest);
			String stt_current = String.valueOf(stt_int_lastest + 1);
			for (int i = 0; i < (6 - stt_current.length()); i++) {
				maPhieuNhapThuoc += "0";
			}
			return maPhieuNhapThuoc += stt_current;
		} catch (NullPointerException e) {
			return "PH" + LocalDate.now().getYear() + "000001";
		}
	}

	private void phatSinhMaThuoc() {
		use_phatSinhMaThuoc = true;

		Thuoc_DAO thuoc_dao = new Thuoc_DAO();
		String maThuoc_lastest = thuoc_dao.layMaThuocCuoiCung().trim();
		String maThuoc = "TH";

		if (maThuoc_lastest != null) {
			String stt_string_lastest = maThuoc_lastest.substring(2, maThuoc_lastest.length());
			int stt_int_lastest = Integer.parseInt(stt_string_lastest);
			String stt_current = String.valueOf(stt_int_lastest + 1);
			for (int i = 0; i < (10 - stt_current.length()); i++) {
				maThuoc += "0";
			}
			maThuoc += stt_current;
			cbMaThuoc.setSelectedItem(maThuoc);
		} else {
			maThuoc += "0000000001";
			cbMaThuoc.setSelectedItem(maThuoc);
		}
	}

	private void phatSinhMaNhaCungCap() {
		use_phatSinhMaNhaCungCap = true;

		NhaCungCap_DAO nhacungcap_dao = new NhaCungCap_DAO();
		String maNhaCungCap_lastest = nhacungcap_dao.layMaNCCCuoiCung().trim();
		String maNhaCungCap = "NCC";

		if (maNhaCungCap_lastest != null) {
			String stt_string_lastest = maNhaCungCap_lastest.substring(3, maNhaCungCap_lastest.length());
			int stt_int_lastest = Integer.parseInt(stt_string_lastest);
			String stt_current = String.valueOf(stt_int_lastest + 1);
			for (int i = 0; i < (5 - stt_current.length()); i++) {
				maNhaCungCap += "0";
			}
			maNhaCungCap += stt_current;
			cbMaNhaCungCap.setSelectedItem(maNhaCungCap);
		} else {
			maNhaCungCap += "00001";
			cbMaNhaCungCap.setSelectedItem(maNhaCungCap);
		}
	}

	private ArrayList<String> initCandidateThuoc() {
		Thuoc_DAO thuoc_dao = new Thuoc_DAO();
		ArrayList<Thuoc> danhSachThuoc = thuoc_dao.layTatCaThuoc();
		ArrayList<String> dataSet = new ArrayList<String>();
		for (Thuoc thuoc : danhSachThuoc) {
			String element = thuoc.getMaThuoc() + " - " + thuoc.getTenThuoc() + " - "
					+ thuoc.getNhaCungCap().getTenNCC();
			dataSet.add(element);
		}
		return dataSet;
	}

	private ArrayList<String> initCandidateNhaCungCap() {
		NhaCungCap_DAO nhacungcap_dao = new NhaCungCap_DAO();
		ArrayList<NhaCungCap> danhSachNhaCungCap = nhacungcap_dao.layTatCaNhaCungCap();
		ArrayList<String> dataSet = new ArrayList<String>();
		for (NhaCungCap nhaCungCap : danhSachNhaCungCap) {
			String element = nhaCungCap.getMaNCC() + " - " + nhaCungCap.getTenNCC();
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

	private void generateThuocByMaThuoc(String maThuoc) {
		Thuoc_DAO thuoc_dao = new Thuoc_DAO();
		ArrayList<Thuoc> ds = thuoc_dao.layTatCaThuoc();
		Thuoc thuoc = new Thuoc(maThuoc);
		if (ds.contains(thuoc)) {
			thuoc = ds.get(ds.indexOf(thuoc));
			NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
			txtTenThuoc.setText(thuoc.getTenThuoc().trim());
			String loaiThuoc = String.valueOf(thuoc.getLoaiThuoc());
			cbLoaiThuoc.removeAllItems();
			cbLoaiThuoc.addItem(loaiThuoc);
			cbLoaiThuoc.setSelectedItem(loaiThuoc);
			txtDonViThuoc.setText(thuoc.getDonViThuoc().trim());
			txtDonGiaMua.setEditable(true);
			txtXuatXu.setText(thuoc.getXuatXu());
			txtSoLuongTon.setText(String.valueOf(thuoc.getSoLuongTon()));
			String maNhaCungCap = thuoc.getNhaCungCap().getMaNCC();
			cbMaNhaCungCap.addItem(maNhaCungCap);
			cbMaNhaCungCap.setSelectedItem(maNhaCungCap);
			cbMaNhaCungCap.setEditable(false);
			lblMaNhaCungCap.setText("Mã nhà cung cấp:");
			btnGenerateMaNCC.setEnabled(false);
			txtTenNhaCungCap.setText(nhaCungCap.getTenNCC().trim());
			txtEmail.setText(nhaCungCap.getEmail().trim());
			txtSoDienThoai.setText(nhaCungCap.getSoDienThoai().trim());
			textAreaDiaChi.setText(nhaCungCap.getDiaChi().trim());
			btnLuuNhaCungCap.setEnabled(false);
		}
	}

	private void generateNhaCCByMaNCC(String maNhaCungCap) {
		NhaCungCap_DAO nhacungcap_dao = new NhaCungCap_DAO();
		ArrayList<NhaCungCap> ds = nhacungcap_dao.layTatCaNhaCungCap();
		NhaCungCap nhaCungCap = new NhaCungCap(maNhaCungCap);
		if (ds.contains(nhaCungCap)) {
			nhaCungCap = ds.get(ds.indexOf(nhaCungCap));
			txtTenNhaCungCap.setText(nhaCungCap.getTenNCC().trim());
			txtEmail.setText(nhaCungCap.getEmail().trim());
			txtSoDienThoai.setText(nhaCungCap.getSoDienThoai().trim());
			textAreaDiaChi.setText(nhaCungCap.getDiaChi().trim());
			btnLuuNhaCungCap.setEnabled(false);
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

	private boolean validData() {
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
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(cbMaThuoc, "Bạn phải nhập mã thuốc", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
			cbMaThuoc.requestFocus();
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

		String tenThuoc = txtTenThuoc.getText().trim();
		if (tenThuoc.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtTenThuoc, "Bạn phải nhập tên thuốc", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtTenThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtTenThuoc.requestFocus();
			return false;
		} else {
			txtTenThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String ngaySanXuat = datePickerNgaySanXuat.getJFormattedTextField().getText();
		if (ngaySanXuat.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(datePickerNgaySanXuat, "Bạn phải chọn ngày sản xuất", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			datePickerNgaySanXuat.getJFormattedTextField()
					.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			datePickerNgaySanXuat.requestFocus();
			return false;
		} else {
			datePickerNgaySanXuat.getJFormattedTextField()
					.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String ngayHetHan = datePickerNgayHetHan.getJFormattedTextField().getText();
		if (ngayHetHan.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(datePickerNgayHetHan, "Bạn phải chọn ngày hết hạn", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			datePickerNgayHetHan.getJFormattedTextField()
					.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			datePickerNgayHetHan.requestFocus();
			return false;
		} else {
			datePickerNgayHetHan.getJFormattedTextField().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		int loaiThuoc = cbLoaiThuoc.getSelectedIndex();
		if (loaiThuoc == -1) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(cbLoaiThuoc, "Bạn phải chọn loại thuốc", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			cbLoaiThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			cbLoaiThuoc.requestFocus();
			return false;
		} else {
			cbLoaiThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String donViThuoc = txtDonViThuoc.getText().trim();
		if (donViThuoc.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtDonViThuoc, "Bạn phải nhập đơn vị thuốc", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtDonViThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtDonViThuoc.requestFocus();
			return false;
		} else {
			txtDonViThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		try {
			String donGiaMua = txtDonGiaMua.getText().trim();
			if (donGiaMua.equalsIgnoreCase("")) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(txtDonGiaMua, "Bạn phải nhập đơn giá mua", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				txtDonGiaMua.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				txtDonGiaMua.requestFocus();
				return false;
			} else {
				float float_DonGiaMua = Float.parseFloat(loaiBoDinhDangTien(donGiaMua));
				if (float_DonGiaMua < 0) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtDonGiaMua, "Đơn giá mua phải lớn hơn hoặc bằng 0", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					txtDonGiaMua.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
					txtDonGiaMua.requestFocus();
					txtDonGiaMua.selectAll();
					return false;
				} else {
					txtDonGiaMua.setText(fmt.format(float_DonGiaMua));
					txtDonGiaMua.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				}
			}
		} catch (NumberFormatException e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtDonGiaMua, "Bạn phải nhập số", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			txtDonGiaMua.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtDonGiaMua.requestFocus();
			txtDonGiaMua.selectAll();
			return false;
		}

		String xuatXu = txtXuatXu.getText().trim();
		if (xuatXu.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtXuatXu, "Bạn phải nhập xuất xứ của thuốc", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtXuatXu.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtXuatXu.requestFocus();
			return false;
		} else {
			txtXuatXu.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		try {
			Toolkit.getDefaultToolkit().beep();
			String maNCC = cbMaNhaCungCap.getSelectedItem().toString().trim();
			if (maNCC.equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(cbMaNhaCungCap, "Bạn phải nhập mã nhà cung cấp", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				cbMaNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				cbMaNhaCungCap.requestFocus();
				return false;
			} else {
				cbMaNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		} catch (NullPointerException e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(cbMaNhaCungCap, "Bạn phải nhập mã nhà cung cấp", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			cbMaNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
			cbMaNhaCungCap.requestFocus();
			return false;
		}

		if (btnGenerateMaNCC.isEnabled()) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(btnGenerateMaNCC, "Bạn chưa xác nhận mã nhà cung cấp", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			btnGenerateMaNCC.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
			btnGenerateMaNCC.requestFocus();
			return false;
		} else {
			btnGenerateMaNCC.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String tenNCC = txtTenNhaCungCap.getText().trim();
		if (tenNCC.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtTenNhaCungCap, "Bạn phải nhập tên nhà cung cấp", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtTenNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtTenNhaCungCap.requestFocus();
			return false;
		} else {
			txtTenNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String email = txtEmail.getText().trim();
		if (email.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtEmail, "Bạn phải nhập địa chỉ email", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtEmail.requestFocus();
			return false;
		} else {
			txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String soDienThoai = txtSoDienThoai.getText().trim();
		if (soDienThoai.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtSoDienThoai, "Bạn phải nhập số điện thoại", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtSoDienThoai.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtSoDienThoai.requestFocus();
			return false;
		} else {
			String regex = "^\\d{10,11}$";
			if (!RegularExpression.checkMatch(soDienThoai, regex)) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(txtSoDienThoai, "Số điện thoại phải gồm 10 đến 11 ký số", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				txtSoDienThoai.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				txtSoDienThoai.requestFocus();
				txtSoDienThoai.selectAll();
				return false;
			} else {
				txtSoDienThoai.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		}

		String diaChi = textAreaDiaChi.getText().trim();
		if (diaChi.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(textAreaDiaChi, "Bạn phải nhập địa chỉ", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			textAreaDiaChi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			textAreaDiaChi.requestFocus();
			return false;
		} else {
			textAreaDiaChi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		return true;
	}
	
	private void fillForm(int row) {
		if (row != -1) {
			String maPhieuNhapThuoc = String.valueOf(tablePhieuNhapThuoc.getValueAt(row, 0));
			PhieuNhapThuoc_DAO pnt_dao = new PhieuNhapThuoc_DAO();
			PhieuNhapThuoc phieuNhapThuoc = pnt_dao.layPhieuNhapThuoc(maPhieuNhapThuoc);
			Thuoc_DAO thuoc_dao = new Thuoc_DAO();
			Thuoc thuoc = thuoc_dao.layThuoc(phieuNhapThuoc.getThuoc().getMaThuoc());
			NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
			cbMaThuoc.setSelectedItem(thuoc.getMaThuoc());
			txtTenThuoc.setText(thuoc.getTenThuoc());
			txtNgayNhap.setText(sdf.format(phieuNhapThuoc.getNgayNhap()));
			model_ngaysanxuat.setValue(phieuNhapThuoc.getNgaySanXuat());
			model_ngayhethan.setValue(phieuNhapThuoc.getNgayHetHan());
			cbLoaiThuoc.setSelectedItem(thuoc.getLoaiThuoc());
			txtDonViThuoc.setText(thuoc.getDonViThuoc());
			txtDonGiaMua.setText(fmt.format(phieuNhapThuoc.getDonGiaMua()));
			txtXuatXu.setText(thuoc.getXuatXu());
			spinSoLuongNhap.setValue(phieuNhapThuoc.getSoLuongNhap());
			txtSoLuongTon.setText(String.valueOf(thuoc.getSoLuongTon()));
			cbMaNhaCungCap.setSelectedItem(nhaCungCap.getMaNCC());
			txtTenNhaCungCap.setText(nhaCungCap.getTenNCC());
			txtEmail.setText(nhaCungCap.getEmail());
			txtSoDienThoai.setText(nhaCungCap.getSoDienThoai());
			textAreaDiaChi.setText(nhaCungCap.getDiaChi());
		}
	}

	private void xoaTrang() {
		use_event_cbMaThuoc = false;
		use_event_cbMaNhaCungCap = false;
		txtMaPhieuNhap.setText(phatSinhMaPhieuNhapThuoc());
		cbMaThuoc.setEditable(true);
		cbMaThuoc.setSelectedItem("");
		lblMaThuoc.setText("Tìm mã thuốc:");
		btnGenerateMaThuoc.setEnabled(true);
		cbMaNhaCungCap.setEditable(true);
		cbMaNhaCungCap.setSelectedItem("");
		lblMaNhaCungCap.setText("Tìm mã nhà cung cấp:");
		btnGenerateMaThuoc.setEnabled(true);
		txtTenThuoc.setEditable(false);
		txtTenThuoc.setText("");
		datePickerNgaySanXuat.getJFormattedTextField().setText("");
		datePickerNgayHetHan.getJFormattedTextField().setText("");
		cbLoaiThuoc.setSelectedIndex(-1);
		txtDonViThuoc.setEditable(false);
		txtDonViThuoc.setText("");
		txtDonGiaMua.setEditable(false);
		txtDonGiaMua.setText("");
		txtXuatXu.setEditable(false);
		txtXuatXu.setText("");
		spinSoLuongNhap.setValue(1);
		txtSoLuongTon.setText("0");
		txtTenNhaCungCap.setEditable(false);
		txtTenNhaCungCap.setText("");
		txtEmail.setEditable(false);
		txtEmail.setText("");
		txtSoDienThoai.setEditable(false);
		txtSoDienThoai.setText("");
		textAreaDiaChi.setEditable(false);
		textAreaDiaChi.setText("");
		btnGenerateMaNCC.setEnabled(true);
		btnLuuNhaCungCap.setEnabled(false);
		btnThem.setEnabled(true);
		tablePhieuNhapThuoc.clearSelection();
		cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnGenerateMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtTenThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		datePickerNgaySanXuat.getJFormattedTextField().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		datePickerNgayHetHan.getJFormattedTextField().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		cbLoaiThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtDonViThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtDonGiaMua.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtXuatXu.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		cbMaNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnGenerateMaNCC.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtTenNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtSoDienThoai.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textAreaDiaChi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		cbMaThuoc.requestFocus();
	}

	/**
	 * Create the frame.
	 */
	public NhapThuoc_GUI(NhanVien e) {
		emp = e;
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
		btnThem.setBackground(Color.WHITE);
		btnThem.setToolTipText("");
		btnThem.setIcon(new ImageIcon("data\\icons\\add.png"));
		btnThem.setFocusable(false);
		panel.add(btnThem);
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean themThuoc = false;
				boolean themNCC = false;
				boolean themPhieuNhapThuoc = false;

				if (validData() && !btnGenerateMaThuoc.isEnabled() && !btnGenerateMaNCC.isEnabled()) {
					NhaCungCap_DAO ncc_dao = new NhaCungCap_DAO();
					if (use_phatSinhMaNhaCungCap && btnLuuNhaCungCap.isEnabled()) {
						if (ncc_dao.themNhaCungCap(taoNhaCungCap())) {
							themNCC = true;
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhà cung cấp vào CSDL",
									"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						// Nhà cung cấp đã được thêm thành công trước đó
						themNCC = true;
					}

					Thuoc_DAO thuoc_dao = new Thuoc_DAO();
					if (use_phatSinhMaThuoc) {
						if (thuoc_dao.themThuoc(taoThuoc())) {
							themThuoc = true;
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "Lỗi khi thêm thuốc vào CSDL", "Lỗi kết nối CSDL",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						Thuoc thuoc = taoThuoc();
						if (thuoc_dao.capNhatSoLuongTonCuaThuoc(thuoc.getSoLuongTon(), thuoc.getMaThuoc())) {
							themThuoc = true;
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật số lượng tồn của thuốc",
									"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
						}
					}

					PhieuNhapThuoc phieuNhapThuoc = taoPhieuNhapThuoc();
					PhieuNhapThuoc_DAO phieunhapthuoc_dao = new PhieuNhapThuoc_DAO();
					if (phieunhapthuoc_dao.themPhieuNhapThuoc(phieuNhapThuoc, emp)) {
						themPhieuNhapThuoc = true;
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Lỗi khi thêm phiếu nhập thuốc vào CSDL",
								"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
					}

					if (themThuoc && themNCC && themPhieuNhapThuoc) {
						modelPhieuNhapThuoc.addRow(new Object[] { phieuNhapThuoc.getMaPhieu(),
								phieuNhapThuoc.getThuoc().getMaThuoc(), phieuNhapThuoc.getThuoc().getTenThuoc(),
								sdf.format(phieuNhapThuoc.getNgayNhap()), sdf.format(phieuNhapThuoc.getNgaySanXuat()),
								sdf.format(phieuNhapThuoc.getNgayHetHan()), phieuNhapThuoc.getThuoc().getLoaiThuoc(),
								phieuNhapThuoc.getThuoc().getDonViThuoc(), phieuNhapThuoc.getDonGiaMua(),
								phieuNhapThuoc.getThuoc().getXuatXu(), phieuNhapThuoc.getSoLuongNhap() });
						btnLuuNhaCungCap.setEnabled(false);
						btnThem.setEnabled(false);
						btnXoa.setEnabled(true);
						float tongTienNhapThuoc = phieuNhapThuoc.getDonGiaMua() * phieuNhapThuoc.getSoLuongNhap();
						txtTongTienNhapThuoc.setText(fmt.format(tongTienNhapThuoc));
						JOptionPane.showMessageDialog(null, "Thêm phiếu nhập thuốc thành công");
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Thêm phiếu nhập thuốc không thành công", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		btnXoa = new JButton("Xóa");
		btnXoa.setEnabled(false);
		btnXoa.setBackground(Color.WHITE);
		btnXoa.setIcon(new ImageIcon("data\\icons\\delete.png"));
		btnXoa.setToolTipText("");
		btnXoa.setFocusable(false);
		panel.add(btnXoa);
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				XoaPhieuNhapThuoc_GUI xoaPhieuNhapThuoc = new XoaPhieuNhapThuoc_GUI(taoPhieuNhapThuoc(), emp);
				xoaPhieuNhapThuoc.setVisible(true);
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
		lblTitle.setIcon(new ImageIcon("data\\icons\\add-medicine-title.png"));
		lblTitle.setBounds(532, 10, 500, 93);
		panel_2.add(lblTitle);

		JPanel panel_chart = new JPanel();
		panel_chart.setBackground(Color.WHITE);
		panel_chart.setBounds(-13, 109, 1535, 645);
		panel_chart.setLayout(null);
		panel_2.add(panel_chart);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(23, 10, 1015, 374);
		panel_3.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_chart.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblThongTinNhapThuoc = new JLabel("Thông tin nhập thuốc");
		lblThongTinNhapThuoc.setForeground(SystemColor.textHighlight);
		lblThongTinNhapThuoc.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinNhapThuoc.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinNhapThuoc.setBounds(20, 10, 957, 35);
		panel_3.add(lblThongTinNhapThuoc);

		JLabel lblTenThuoc = new JLabel("Tên thuốc:");
		lblTenThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTenThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblTenThuoc.setBounds(20, 112, 126, 26);
		panel_3.add(lblTenThuoc);

		txtTenThuoc = new JTextField();
		txtTenThuoc.setEditable(false);
		txtTenThuoc.setFont(new Font("Arial", Font.PLAIN, 16));
		txtTenThuoc.setColumns(10);
		txtTenThuoc.setBackground(Color.WHITE);
		txtTenThuoc.setBounds(169, 111, 308, 29);
		panel_3.add(txtTenThuoc);
		txtTenThuoc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String tenThuoc = txtTenThuoc.getText().trim();
				if (tenThuoc.length() > 50) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtTenThuoc, "Tên thuốc không được vượt quá 50 ký tự", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					txtTenThuoc.setText(tenThuoc.substring(0, 50));
				}
			}
		});

		lblMaThuoc = new JLabel("Tìm mã thuốc:");
		lblMaThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaThuoc.setBounds(497, 68, 114, 26);
		panel_3.add(lblMaThuoc);

		JLabel lblNgayNhap = new JLabel("Ngày nhập:");
		lblNgayNhap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgayNhap.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgayNhap.setBounds(497, 112, 114, 26);
		panel_3.add(lblNgayNhap);

		p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		JLabel lblLoaiThuoc = new JLabel("Loại thuốc:");
		lblLoaiThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoaiThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblLoaiThuoc.setBounds(31, 207, 114, 26);
		panel_3.add(lblLoaiThuoc);

		model_ngaysanxuat = new UtilDateModel();
		datePanelNgaySanXuat = new JDatePanelImpl(model_ngaysanxuat, p);
		datePickerNgaySanXuat = new JDatePickerImpl(datePanelNgaySanXuat, new DateLabelFormatter());
		SpringLayout springLayout_2 = (SpringLayout) datePickerNgaySanXuat.getLayout();
		springLayout_2.putConstraint(SpringLayout.SOUTH, datePickerNgaySanXuat.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, datePickerNgaySanXuat);
		datePickerNgaySanXuat.setBounds(169, 158, 308, 27);
		datePickerNgaySanXuat.setFocusable(false);
		panel_3.add(datePickerNgaySanXuat);
		datePickerNgaySanXuat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ngaySanXuat = datePickerNgaySanXuat.getJFormattedTextField().getText();
				LocalDate date_NgaySanXuat = LocalDate.parse(ngaySanXuat, dtf);
				if (date_NgaySanXuat.isAfter(LocalDate.now())) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(datePickerNgaySanXuat,
							"Ngày sản xuất không được lớn hơn ngày hiện tại", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
					datePickerNgaySanXuat.getJFormattedTextField().setText("");
					datePickerNgayHetHan.getJFormattedTextField().setText("");
				}
			}
		});

		JLabel lblNgaySanXuat = new JLabel("Ngày sản xuất:");
		lblNgaySanXuat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgaySanXuat.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgaySanXuat.setBounds(20, 159, 126, 26);
		panel_3.add(lblNgaySanXuat);

		String[] option_cacLoaiThuoc = "Thuốc kê đơn;Thuốc không kê đơn;Thực phẩm chức năng;Chăm sóc sức khỏe"
				.split(";");
		cbLoaiThuoc = new JComboBox<String>();
		cbLoaiThuoc.setFont(new Font("Arial", Font.BOLD, 14));
		cbLoaiThuoc.setModel(new DefaultComboBoxModel<>(option_cacLoaiThuoc));
		cbLoaiThuoc.setSelectedIndex(-1);
		cbLoaiThuoc.setBackground(Color.WHITE);
		cbLoaiThuoc.setBounds(168, 207, 308, 29);
		panel_3.add(cbLoaiThuoc);

		JLabel lblDonGiaMua = new JLabel("Đơn giá mua:");
		lblDonGiaMua.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDonGiaMua.setFont(new Font("Arial", Font.BOLD, 16));
		lblDonGiaMua.setBounds(31, 257, 114, 26);
		panel_3.add(lblDonGiaMua);

		JLabel lblDonViThuoc = new JLabel("Đơn vị thuốc:");
		lblDonViThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDonViThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblDonViThuoc.setBounds(497, 207, 114, 26);
		panel_3.add(lblDonViThuoc);

		txtDonViThuoc = new JTextField();
		txtDonViThuoc.setEditable(false);
		txtDonViThuoc.setFont(new Font("Arial", Font.PLAIN, 16));
		txtDonViThuoc.setColumns(10);
		txtDonViThuoc.setBackground(Color.WHITE);
		txtDonViThuoc.setBounds(628, 204, 349, 29);
		panel_3.add(txtDonViThuoc);
		txtDonViThuoc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String donViThuoc = txtDonViThuoc.getText().trim();
				if (donViThuoc.length() > 20) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtDonViThuoc, "Đơn vị thuốc không được vượt quá 20 ký tự",
							"Cảnh báo", JOptionPane.WARNING_MESSAGE);
					txtDonViThuoc.setText(donViThuoc.substring(0, 20));
				}
			}
		});

		JLabel lblSoLuongNhap = new JLabel("Số lượng nhập:");
		lblSoLuongNhap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoLuongNhap.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoLuongNhap.setBounds(20, 305, 125, 26);
		panel_3.add(lblSoLuongNhap);

		JLabel lblXuatXu = new JLabel("Xuất xứ:");
		lblXuatXu.setHorizontalAlignment(SwingConstants.RIGHT);
		lblXuatXu.setFont(new Font("Arial", Font.BOLD, 16));
		lblXuatXu.setBounds(497, 257, 114, 26);
		panel_3.add(lblXuatXu);

		txtXuatXu = new JTextField();
		txtXuatXu.setEditable(false);
		txtXuatXu.setFont(new Font("Arial", Font.PLAIN, 16));
		txtXuatXu.setColumns(10);
		txtXuatXu.setBackground(Color.WHITE);
		txtXuatXu.setBounds(628, 256, 349, 29);
		panel_3.add(txtXuatXu);
		txtXuatXu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String xuatXu = txtXuatXu.getText().trim();
				if (xuatXu.length() > 40) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtXuatXu, "Xuất xứ không được vượt quá 40 ký tự", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					txtXuatXu.setText(xuatXu.substring(0, 40));
				}
			}
		});

		JLabel lblNgayHetHan = new JLabel("Ngày hết hạn:");
		lblNgayHetHan.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgayHetHan.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgayHetHan.setBounds(497, 159, 114, 26);
		panel_3.add(lblNgayHetHan);

		model_ngayhethan = new UtilDateModel();
		datePanelNgayHetHan = new JDatePanelImpl(model_ngayhethan, p);
		datePickerNgayHetHan = new JDatePickerImpl(datePanelNgayHetHan, new DateLabelFormatter());
		SpringLayout springLayout_3 = (SpringLayout) datePickerNgayHetHan.getLayout();
		springLayout_3.putConstraint(SpringLayout.SOUTH, datePickerNgayHetHan.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, datePickerNgayHetHan);
		datePickerNgayHetHan.setBounds(628, 159, 349, 29);
		datePickerNgayHetHan.setFocusable(false);
		panel_3.add(datePickerNgayHetHan);
		datePickerNgayHetHan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ngaySanXuat = datePickerNgaySanXuat.getJFormattedTextField().getText();
				if (!ngaySanXuat.equalsIgnoreCase("")) {
					String ngayHetHan = datePickerNgayHetHan.getJFormattedTextField().getText();
					LocalDate date_NgaySanXuat = LocalDate.parse(ngaySanXuat, dtf);
					LocalDate date_NgayHetHan = LocalDate.parse(ngayHetHan, dtf);
					if (date_NgayHetHan.isBefore(date_NgaySanXuat)) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(datePickerNgayHetHan,
								"Ngày hết hạn không thể trước ngày sản xuất", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
						datePickerNgayHetHan.getJFormattedTextField().setText("");
					} else {
						if (date_NgayHetHan.isBefore(LocalDate.now())) {
							JOptionPane.showMessageDialog(datePickerNgayHetHan, "Sản phẩm đã hết hạn", "Cảnh báo",
									JOptionPane.WARNING_MESSAGE);
							datePickerNgayHetHan.getJFormattedTextField().setText("");
						} else if (date_NgayHetHan.isEqual(LocalDate.now())) {
							JOptionPane.showMessageDialog(datePickerNgayHetHan, "Sản phẩm sẽ hết hạn trong hôm nay",
									"Cảnh báo", JOptionPane.WARNING_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(datePickerNgaySanXuat, "Bạn cần chọn ngày sản xuất trước", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					datePickerNgayHetHan.getJFormattedTextField().setText("");
				}
			}
		});

		txtDonGiaMua = new JTextField();
		txtDonGiaMua.setHorizontalAlignment(SwingConstants.RIGHT);
		txtDonGiaMua.setToolTipText("Nhấn Enter để định dạng tiền");
		txtDonGiaMua.setEditable(false);
		txtDonGiaMua.setFont(new Font("Arial", Font.PLAIN, 16));
		txtDonGiaMua.setColumns(10);
		txtDonGiaMua.setBackground(Color.WHITE);
		txtDonGiaMua.setBounds(169, 256, 308, 29);
		panel_3.add(txtDonGiaMua);
		txtDonGiaMua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String soTienNhap = txtDonGiaMua.getText().trim();
					float float_SoTienNhap = Float.parseFloat(loaiBoDinhDangTien(soTienNhap));
					txtDonGiaMua.setText(fmt.format(float_SoTienNhap));
				} catch (NumberFormatException e2) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn phải nhập số", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
					txtDonGiaMua.selectAll();
					txtDonGiaMua.requestFocus();
				}
			}
		});
		txtDonGiaMua.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String string_DonGiaMua = txtDonGiaMua.getText().trim();
				float float_DonGiaMua = Float.parseFloat(loaiBoDinhDangTien(string_DonGiaMua));
				if (string_DonGiaMua.length() > 11 && fmt.format(float_DonGiaMua).length() > 14) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtDonGiaMua, "Đơn giá mua không được vượt quá 11 ký tự", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					txtDonGiaMua.setText(string_DonGiaMua.substring(0, 11));
				}
			}
		});

		JLabel lblMaPhieuNhap = new JLabel("Mã phiếu nhập:");
		lblMaPhieuNhap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaPhieuNhap.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaPhieuNhap.setBounds(20, 66, 126, 26);
		panel_3.add(lblMaPhieuNhap);

		txtMaPhieuNhap = new JTextField(phatSinhMaPhieuNhapThuoc());
		txtMaPhieuNhap.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaPhieuNhap.setEditable(false);
		txtMaPhieuNhap.setColumns(10);
		txtMaPhieuNhap.setBackground(Color.WHITE);
		txtMaPhieuNhap.setBounds(169, 67, 308, 29);
		panel_3.add(txtMaPhieuNhap);

		JLabel lblSoLuongTon = new JLabel("Số lượng tồn:");
		lblSoLuongTon.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoLuongTon.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoLuongTon.setBounds(497, 306, 114, 26);
		panel_3.add(lblSoLuongTon);

		txtSoLuongTon = new JTextField();
		txtSoLuongTon.setText("0");
		txtSoLuongTon.setEditable(false);
		txtSoLuongTon.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoLuongTon.setColumns(10);
		txtSoLuongTon.setBackground(Color.WHITE);
		txtSoLuongTon.setBounds(628, 305, 349, 29);
		panel_3.add(txtSoLuongTon);

		//SpinnerModel modelSoLuongNhap = new SpinnerNumberModel(1, 1, 100, 1);
		SpinnerModel modelSoLuongNhap = new SpinnerNumberModel();
		modelSoLuongNhap.setValue(1);
		spinSoLuongNhap = new JSpinner(modelSoLuongNhap);
		spinSoLuongNhap.setFont(new Font("Arial", Font.PLAIN, 16));
		spinSoLuongNhap.setBounds(169, 304, 308, 29);
		//spinSoLuongNhap.setEditor(new JSpinner.DefaultEditor(spinSoLuongNhap));
		panel_3.add(spinSoLuongNhap);
		spinSoLuongNhap.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int soLuongNhap = (int) modelSoLuongNhap.getValue();
				if (soLuongNhap < 1) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Số lượng nhập phải lớn hơn hoặc bằng 1", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
					modelSoLuongNhap.setValue(1);
				}
			}
		});

		cbMaThuoc = new JComboBox();
		cbMaThuoc.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaThuoc.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaThuoc.setEditable(true);
		cbMaThuoc.setBounds(628, 69, 308, 29);
		panel_3.add(cbMaThuoc);
		cbMaThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				use_event_cbMaThuoc = true;
				ArrayList<String> cacPhanTuHienCo = initCandidateThuoc();
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
							phatSinhMaThuoc();
						}
					}
				} else {
					phatSinhMaThuoc();
				}
			}
		});

		btnGenerateMaThuoc = new JButton();
		btnGenerateMaThuoc.setToolTipText("Tìm thuốc");
		btnGenerateMaThuoc.setBackground(Color.WHITE);
		btnGenerateMaThuoc.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnGenerateMaThuoc.setBounds(946, 68, 31, 31);
		btnGenerateMaThuoc.setBorder(BorderFactory.createEmptyBorder());
		btnGenerateMaThuoc.setFocusable(false);
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
								generateThuocByMaThuoc(maThuoc);
							}
							cbMaThuoc.setSelectedItem(maThuoc);
							cbMaThuoc.setEditable(false);
							lblMaThuoc.setText("Mã thuốc:");
						} else {
							phatSinhMaThuoc();
							cbMaThuoc.setEditable(false);
							lblMaThuoc.setText("Mã thuốc:");
							txtTenThuoc.setEditable(true);
							txtTenThuoc.requestFocus();
							txtDonViThuoc.setEditable(true);
							txtDonGiaMua.setEditable(true);
							txtXuatXu.setEditable(true);
						}
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(cbMaThuoc, "Bạn phải nhập mã thuốc", "Cảnh báo",
								JOptionPane.WARNING_MESSAGE);
					}
					btnGenerateMaThuoc.setEnabled(false);
					btnGenerateMaThuoc.setBorder(BorderFactory.createEmptyBorder());
					cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				} else {
					cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
					cbMaThuoc.requestFocus();
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaThuoc,
							"Vui lòng nhấn Enter trong khung nhập liệu \"Tìm mã thuốc\" trước khi nhấn nút này",
							"Cảnh báo", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		txtNgayNhap = new JTextField(sdf.format(new Date()));
		txtNgayNhap.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNgayNhap.setEditable(false);
		txtNgayNhap.setColumns(10);
		txtNgayNhap.setBackground(Color.WHITE);
		txtNgayNhap.setBounds(628, 112, 349, 29);
		panel_3.add(txtNgayNhap);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(1048, 10, 477, 374);
		panel_4.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_chart.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblThongTinNhaCungCap = new JLabel("Thông tin nhà cung cấp");
		lblThongTinNhaCungCap.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinNhaCungCap.setForeground(SystemColor.textHighlight);
		lblThongTinNhaCungCap.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinNhaCungCap.setBounds(10, 10, 445, 41);
		panel_4.add(lblThongTinNhaCungCap);

		lblMaNhaCungCap = new JLabel("Tìm mã nhà cung cấp:");
		lblMaNhaCungCap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaNhaCungCap.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaNhaCungCap.setBounds(20, 61, 171, 26);
		panel_4.add(lblMaNhaCungCap);

		String[] option_trangthai = "Tài khoản đang mở;Tài khoản đang khóa".split(";");

		JLabel lblTenNhaCungCap = new JLabel("Tên nhà cung cấp:");
		lblTenNhaCungCap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTenNhaCungCap.setFont(new Font("Arial", Font.BOLD, 16));
		lblTenNhaCungCap.setBounds(20, 101, 171, 26);
		panel_4.add(lblTenNhaCungCap);

		txtTenNhaCungCap = new JTextField();
		txtTenNhaCungCap.setBackground(Color.WHITE);
		txtTenNhaCungCap.setEditable(false);
		txtTenNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 16));
		txtTenNhaCungCap.setColumns(10);
		txtTenNhaCungCap.setBounds(204, 102, 251, 29);
		panel_4.add(txtTenNhaCungCap);
		txtTenNhaCungCap.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String tenNhaCungCap = txtTenNhaCungCap.getText().trim();
				if (tenNhaCungCap.length() > 70) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtTenNhaCungCap, "Tên nhà cung cấp không được vượt quá 70 ký tự",
							"Cảnh báo", JOptionPane.WARNING_MESSAGE);
					txtTenNhaCungCap.setText(tenNhaCungCap.substring(0, 70));
				}
			}
		});

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Arial", Font.BOLD, 16));
		lblEmail.setBounds(20, 141, 171, 26);
		panel_4.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBackground(Color.WHITE);
		txtEmail.setEditable(false);
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 16));
		txtEmail.setColumns(10);
		txtEmail.setBounds(204, 142, 251, 29);
		panel_4.add(txtEmail);
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String email = txtEmail.getText().trim();
				if (email.length() > 40) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtEmail, "Email không được vượt quá 40 ký tự", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					txtEmail.setText(email.substring(0, 40));
				}
			}
		});

		JLabel lblSoDienThoai = new JLabel("Số điện thoại:");
		lblSoDienThoai.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoDienThoai.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoDienThoai.setBounds(20, 181, 171, 26);
		panel_4.add(lblSoDienThoai);

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setBackground(Color.WHITE);
		txtSoDienThoai.setEditable(false);
		txtSoDienThoai.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoDienThoai.setColumns(10);
		txtSoDienThoai.setBounds(204, 182, 251, 29);
		panel_4.add(txtSoDienThoai);
		txtSoDienThoai.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String soDienThoai = txtSoDienThoai.getText().trim();
				if (soDienThoai.length() > 11) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtSoDienThoai, "Số điện thoại không được vượt quá 11 ký tự",
							"Cảnh báo", JOptionPane.WARNING_MESSAGE);
					txtSoDienThoai.setText(soDienThoai.substring(0, 11));
				}
			}
		});

		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiaChi.setFont(new Font("Arial", Font.BOLD, 16));
		lblDiaChi.setBounds(20, 249, 171, 26);
		panel_4.add(lblDiaChi);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(204, 221, 251, 98);
		panel_4.add(scrollPane_1);

		textAreaDiaChi = new JTextArea();
		textAreaDiaChi.setEditable(false);
		textAreaDiaChi.setLineWrap(true);
		textAreaDiaChi.setFont(new Font("Arial", Font.PLAIN, 16));
		scrollPane_1.setViewportView(textAreaDiaChi);
		textAreaDiaChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String diaChi = textAreaDiaChi.getText().trim();
				if (diaChi.length() > 150) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(textAreaDiaChi, "Địa chỉ không được vượt quá 100 ký tự", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					textAreaDiaChi.setText(diaChi.substring(0, 150));
				}
			}
		});

		btnLuuNhaCungCap = new JButton("Lưu thông tin nhà cung cấp");
		btnLuuNhaCungCap.setEnabled(false);
		btnLuuNhaCungCap.setForeground(Color.RED);
		btnLuuNhaCungCap.setBackground(Color.WHITE);
		btnLuuNhaCungCap.setIcon(new ImageIcon("data\\icons\\save.png"));
		btnLuuNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 16));
		btnLuuNhaCungCap.setBounds(204, 329, 251, 35);
		panel_4.add(btnLuuNhaCungCap);
		btnLuuNhaCungCap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NhaCungCap_DAO ncc_dao = new NhaCungCap_DAO();
				if (ncc_dao.themNhaCungCap(taoNhaCungCap())) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Thêm nhà cung cấp thành công");
					btnLuuNhaCungCap.setEnabled(false);
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhà cung cấp vào CSDL", "Lỗi kết nối CSDL",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		cbMaNhaCungCap = new JComboBox();
		cbMaNhaCungCap.setToolTipText("Nhấn Enter để bật tự động hoàn thành");
		cbMaNhaCungCap.setEditable(true);
		cbMaNhaCungCap.setBackground(Color.WHITE);
		cbMaNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 16));
		cbMaNhaCungCap.setBounds(204, 61, 210, 29);
		panel_4.add(cbMaNhaCungCap);
		cbMaNhaCungCap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				use_event_cbMaNhaCungCap = true;
				ArrayList<String> cacPhanTuHienCo = initCandidateNhaCungCap();
				int soPhanTuHienCo = cacPhanTuHienCo.size();
				String tuHienTai = cbMaNhaCungCap.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						cbMaNhaCungCap.removeAllItems();
						for (int i = 0; i < soPhanTuHienCo; i++) {
							cbMaNhaCungCap.addItem(cacPhanTuHienCo.get(i));
						}
						cbMaNhaCungCap.setSelectedItem("");
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
							cbMaNhaCungCap.removeAllItems();
							phatSinhMaNhaCungCap();
						}
					}
				} else {
					phatSinhMaNhaCungCap();
				}
			}
		});

		btnGenerateMaNCC = new JButton();
		btnGenerateMaNCC.setIcon(new ImageIcon("data\\icons\\search24.png"));
		btnGenerateMaNCC.setToolTipText("Tìm nhà cung cấp");
		btnGenerateMaNCC.setFocusable(false);
		btnGenerateMaNCC.setBorder(BorderFactory.createEmptyBorder());
		btnGenerateMaNCC.setBackground(Color.WHITE);
		btnGenerateMaNCC.setBounds(424, 61, 31, 31);
		panel_4.add(btnGenerateMaNCC);
		btnGenerateMaNCC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (use_event_cbMaNhaCungCap) {
					String tuHienTai = cbMaNhaCungCap.getSelectedItem().toString().trim();
					int row_count = cbMaNhaCungCap.getItemCount();
					if (!tuHienTai.equalsIgnoreCase("")) {
						String[] tachTuHienTai = tuHienTai.split("-");
						String maNhaCungCap = tachTuHienTai[0].trim();
						if (row_count > 0) {
							if (row_count == 1) {
								generateNhaCCByMaNCC(maNhaCungCap);
							}
							cbMaNhaCungCap.setSelectedItem(maNhaCungCap);
							cbMaNhaCungCap.setEditable(false);
							lblMaNhaCungCap.setText("Mã nhà cung cấp:");
						} else {
							phatSinhMaNhaCungCap();
							cbMaNhaCungCap.setEditable(false);
							lblMaNhaCungCap.setText("Mã nhà cung cấp:");
							txtTenNhaCungCap.setEditable(true);
							txtEmail.setEditable(true);
							txtSoDienThoai.setEditable(true);
							textAreaDiaChi.setEditable(true);
							btnLuuNhaCungCap.setEnabled(true);
						}
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(cbMaNhaCungCap, "Bạn phải nhập mã nhà cung cấp", "Cảnh báo",
								JOptionPane.WARNING_MESSAGE);
					}
					btnGenerateMaNCC.setEnabled(false);
					btnGenerateMaNCC.setBorder(BorderFactory.createEmptyBorder());
					cbMaNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				} else {
					cbMaNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
					cbMaNhaCungCap.requestFocus();
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaNhaCungCap,
							"Vui lòng nhấn Enter trong khung nhập liệu \"Tìm mã nhà cung cấp\" trước khi nhấn nút này",
							"Cảnh báo", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 394, 1502, 200);
		panel_chart.add(scrollPane);

		String[] headers = { "Mã phiếu nhập", "Mã thuốc", "Tên thuốc", "Ngày nhập", "Ngày sản xuất", "Ngày hết hạn",
				"Loại thuốc", "Đơn vị thuốc", "Đơn giá mua", "Xuất xứ", "Số lượng nhập" };
		modelPhieuNhapThuoc = new DefaultTableModel(headers, 0);
		tablePhieuNhapThuoc = new JTable(modelPhieuNhapThuoc);
		tablePhieuNhapThuoc.getTableHeader().setBackground(Color.CYAN);
		tablePhieuNhapThuoc.getTableHeader().setFont(new Font(null, Font.PLAIN, 14));
		tablePhieuNhapThuoc.setSelectionBackground(Color.YELLOW);
		tablePhieuNhapThuoc.setSelectionForeground(Color.RED);
		tablePhieuNhapThuoc.setFont(new Font(null, Font.PLAIN, 14));
		tablePhieuNhapThuoc.setRowHeight(22);
		tablePhieuNhapThuoc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablePhieuNhapThuoc.getSelectionModel().addListSelectionListener(this);
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
		scrollPane.setViewportView(tablePhieuNhapThuoc);

		JLabel lblTongTienNhapThuoc = new JLabel("Tổng tiền nhập thuốc:");
		lblTongTienNhapThuoc.setHorizontalAlignment(SwingConstants.LEFT);
		lblTongTienNhapThuoc.setForeground(Color.RED);
		lblTongTienNhapThuoc.setFont(new Font("Arial", Font.BOLD, 18));
		lblTongTienNhapThuoc.setBounds(1034, 606, 205, 26);
		panel_chart.add(lblTongTienNhapThuoc);

		txtTongTienNhapThuoc = new JTextField();
		txtTongTienNhapThuoc.setText("0");
		txtTongTienNhapThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTongTienNhapThuoc.setForeground(Color.BLACK);
		txtTongTienNhapThuoc.setFont(new Font("Arial", Font.BOLD, 18));
		txtTongTienNhapThuoc.setEditable(false);
		txtTongTienNhapThuoc.setColumns(10);
		txtTongTienNhapThuoc.setBackground(Color.WHITE);
		txtTongTienNhapThuoc.setBounds(1236, 606, 243, 29);
		panel_chart.add(txtTongTienNhapThuoc);

		JLabel lblDonViTien = new JLabel("VND");
		lblDonViTien.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDonViTien.setFont(new Font("Arial", Font.BOLD, 18));
		lblDonViTien.setBounds(1476, 606, 49, 26);
		panel_chart.add(lblDonViTien);

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

		setTitle("Nhập thuốc");
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
}
