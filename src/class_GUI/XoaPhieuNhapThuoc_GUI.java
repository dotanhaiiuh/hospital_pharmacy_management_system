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
import java.awt.event.KeyAdapter;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import class_DAO.PhieuNhapThuoc_DAO;
import class_DAO.TaiKhoan_DAO;
import class_DAO.Thuoc_DAO;
import class_Entities.NhanVien;
import class_Entities.PhieuNhapThuoc;
import class_Entities.Thuoc;
import class_Entities.NhaCungCap;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

public class XoaPhieuNhapThuoc_GUI extends JFrame implements WindowListener {
	private JTextField txtTenThuoc;
	private JTextField txtDonViThuoc;
	private JTextField txtXuatXu;
	private DefaultTableModel modelPhieuNhapThuoc;
	private JTable tablePhieuNhapThuoc;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private DecimalFormat fmt = new DecimalFormat("###,###");

	private JButton btnBack;

	private JTextField txtDonGiaMua;
	private JTextField txtMaPhieuNhap;
	private JTextField txtSoLuongTon;
	private JTextField txtTenNhaCungCap;
	private JTextField txtEmail;
	private JTextField txtSoDienThoai;

	private JTextArea textAreaDiaChi;
	private NhanVien emp;
	private PhieuNhapThuoc phieuNhapThuoc;
	private int soLanNhapSai = 0;

	private JLabel lblMaNhaCungCap;
	private JTextField txtNgayHienTai;
	private JTextField txtNgayNhap;

	private JLabel lblMaThuoc;
	private JTextField txtMaThuoc;
	private JTextField txtNgaySanXuat;
	private JTextField txtLoaiThuoc;
	private JTextField txtNgayHetHan;
	private JTextField txtSoLuongNhap;
	private JTextField txtMaNhaCungCap;
	private JTextField txtTongTienNhapThuoc;

	private String layNgayHienTai() {
		Date ngayHienTai = new Date();
		return sdf.format(ngayHienTai);
	}

	private void initForm() {
		NhanVien nhanVien = phieuNhapThuoc.getNhanVien();
		Thuoc thuoc = phieuNhapThuoc.getThuoc();
		NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
		String maPhieuNhapThuoc = phieuNhapThuoc.getMaPhieu();
		txtMaPhieuNhap.setText(maPhieuNhapThuoc);
		String maThuoc = thuoc.getMaThuoc();
		txtMaThuoc.setText(maThuoc);
		String tenThuoc = thuoc.getTenThuoc();
		txtTenThuoc.setText(tenThuoc);
		String ngayLapPhieu = sdf.format(phieuNhapThuoc.getNgayNhap());
		txtNgayNhap.setText(ngayLapPhieu);
		String ngaySanXuat = sdf.format(phieuNhapThuoc.getNgaySanXuat());
		txtNgaySanXuat.setText(ngaySanXuat);
		String ngayHetHan = sdf.format(phieuNhapThuoc.getNgayHetHan());
		txtNgayHetHan.setText(ngayHetHan);
		String loaiThuoc = thuoc.getLoaiThuoc();
		txtLoaiThuoc.setText(loaiThuoc);
		String donViThuoc = thuoc.getDonViThuoc();
		txtDonViThuoc.setText(donViThuoc);
		String donGiaMua = fmt.format(phieuNhapThuoc.getDonGiaMua());
		txtDonGiaMua.setText(donGiaMua);
		String xuatXu = thuoc.getXuatXu();
		txtXuatXu.setText(xuatXu);
		String soLuongNhap = String.valueOf(phieuNhapThuoc.getSoLuongNhap());
		txtSoLuongNhap.setText(soLuongNhap);
		String soLuongTon = String.valueOf(thuoc.getSoLuongTon());
		txtSoLuongTon.setText(soLuongTon);
		String maNhaCungCap = nhaCungCap.getMaNCC();
		txtMaNhaCungCap.setText(maNhaCungCap);
		String tenNhaCungCap = nhaCungCap.getTenNCC();
		txtTenNhaCungCap.setText(tenNhaCungCap);
		String email = nhaCungCap.getEmail();
		txtEmail.setText(email);
		String soDienThoai = nhaCungCap.getSoDienThoai();
		txtSoDienThoai.setText(soDienThoai);
		String diaChi = nhaCungCap.getDiaChi();
		textAreaDiaChi.setText(diaChi);
		modelPhieuNhapThuoc.addRow(new Object[] { maPhieuNhapThuoc, maThuoc, tenThuoc, ngayLapPhieu, ngaySanXuat,
				ngayHetHan, loaiThuoc, donViThuoc, donGiaMua, xuatXu, soLuongNhap, soLuongTon, nhanVien.getMaNV(),
				nhanVien.getHoTenNhanVien() });
		tablePhieuNhapThuoc.setModel(modelPhieuNhapThuoc);
		float tongTienNhapThuoc = phieuNhapThuoc.getDonGiaMua() * phieuNhapThuoc.getSoLuongNhap();
		txtTongTienNhapThuoc.setText(fmt.format(tongTienNhapThuoc));
	}

	/**
	 * Create the frame.
	 */
	public XoaPhieuNhapThuoc_GUI(PhieuNhapThuoc p, NhanVien e) {
		phieuNhapThuoc = p;
		emp = e;
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\icons8-delete-bin-24.png"));
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

		JButton btnXoa = new JButton("Xóa phiếu nhập thuốc");
		btnXoa.setBackground(Color.WHITE);
		btnXoa.setIcon(new ImageIcon("data\\icons\\delete.png"));
		panel.add(btnXoa);
		btnXoa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_D) && (e.getModifiersEx() == KeyEvent.ALT_DOWN_MASK))
				{
					String matKhauXoa = JOptionPane.showInputDialog("Nhập mật khẩu xóa:").trim();
					if (!matKhauXoa.equalsIgnoreCase("")) {
						if (matKhauXoa.equals("Delete")) {
							String maPhieuNhapThuoc = txtMaPhieuNhap.getText().trim();
							String maThuoc = txtMaThuoc.getText().trim();
							int soLuongNhap = Integer.parseInt(txtSoLuongNhap.getText().trim());
							int soLuongTonHienTai = Integer.parseInt(txtSoLuongTon.getText().trim());
							int soLuongTonMoi = soLuongTonHienTai - soLuongNhap;
							PhieuNhapThuoc_DAO phieunhapthuoc_dao = new PhieuNhapThuoc_DAO();
							Thuoc_DAO thuoc_dao = new Thuoc_DAO();
							if (soLuongTonMoi >= 0) {
								if (thuoc_dao.capNhatSoLuongTonCuaThuoc(soLuongTonMoi, maThuoc)) {
									if (phieunhapthuoc_dao.xoaMotPhieuNhapThuoc(maPhieuNhapThuoc)) {
										JOptionPane.showMessageDialog(null, "Xóa phiếu nhập thuốc thành công");
										if (emp.getChucVu().equalsIgnoreCase("Quản lý nhà thuốc")) {
											QuanLy_GUI quanLy = new QuanLy_GUI(emp);
											quanLy.setVisible(true);
											setVisible(false);
										}
										else if (emp.getChucVu().equalsIgnoreCase("Nhân viên bán thuốc")) {
											NhanVien_GUI nhanVien_GUI = new NhanVien_GUI(emp);
											nhanVien_GUI.setVisible(true);
											setVisible(false);
										}
										else if (emp.getChucVu().equalsIgnoreCase("Nhân viên kho")) {
											NhanVienKho_GUI nhanVienKho_GUI = new NhanVienKho_GUI(emp);
											nhanVienKho_GUI.setVisible(true);
											setVisible(false);
										}
									} else {
										Toolkit.getDefaultToolkit().beep();
										JOptionPane.showMessageDialog(null,
												"Lỗi khi xóa phiếu nhập thuốc này trong CSDL", "Lỗi kết nối CSDL",
												JOptionPane.ERROR_MESSAGE);
									}
								} else {
									Toolkit.getDefaultToolkit().beep();
									JOptionPane.showMessageDialog(null,
											"Lỗi khi cập nhật lại số lượng tồn của thuốc này trong CSDL",
											"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
								}
							} else {
								if (thuoc_dao.capNhatSoLuongTonCuaThuoc(0, maThuoc)) {
									if (phieunhapthuoc_dao.xoaMotPhieuNhapThuoc(maPhieuNhapThuoc)) {
										JOptionPane.showMessageDialog(null, "Xóa phiếu nhập thuốc thành công");
										if (emp.getChucVu().equalsIgnoreCase("Quản lý nhà thuốc")) {
											QuanLy_GUI quanLy = new QuanLy_GUI(emp);
											quanLy.setVisible(true);
											setVisible(false);
										}
									} else {
										Toolkit.getDefaultToolkit().beep();
										JOptionPane.showMessageDialog(null,
												"Lỗi khi xóa phiếu nhập thuốc này trong CSDL", "Lỗi kết nối CSDL",
												JOptionPane.ERROR_MESSAGE);
									}
								} else {
									Toolkit.getDefaultToolkit().beep();
									JOptionPane.showMessageDialog(null,
											"Lỗi khi cập nhật lại số lượng tồn của thuốc này trong CSDL",
											"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
								}
							}
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
			}
		});
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Toolkit.getDefaultToolkit().beep();
				int confirm_1 = JOptionPane.showConfirmDialog(null,
						"Bạn có chắc chắn muốn xóa phiếu nhập thuốc này hay không?", "Chú ý",
						JOptionPane.YES_NO_OPTION);
				if (confirm_1 == JOptionPane.YES_OPTION) {
					String string_NgayLapPhieu = txtNgayNhap.getText();
					LocalDate ngayLapPhieu = LocalDate.parse(string_NgayLapPhieu, dtf);
					int soLuongNhap = Integer.parseInt(txtSoLuongNhap.getText().trim());
					int soLuongTonHienTai = Integer.parseInt(txtSoLuongTon.getText().trim());
					String maThuoc = txtMaThuoc.getText().trim();
					Thuoc_DAO thuoc_dao = new Thuoc_DAO();
					String maPhieuNhapThuoc = txtMaPhieuNhap.getText().trim();
					PhieuNhapThuoc_DAO phieunhapthuoc_dao = new PhieuNhapThuoc_DAO();
					int soLuongTonMoi = soLuongTonHienTai - soLuongNhap;
					if (ngayLapPhieu.equals(LocalDate.now())) {
						if (soLuongTonMoi >= 0) {
							if (thuoc_dao.capNhatSoLuongTonCuaThuoc(soLuongTonMoi, maThuoc)) {
								if (phieunhapthuoc_dao.xoaMotPhieuNhapThuoc(maPhieuNhapThuoc)) {
									Toolkit.getDefaultToolkit().beep();
									JOptionPane.showMessageDialog(null, "Xóa phiếu nhập thuốc thành công");
									if (emp.getChucVu().equalsIgnoreCase("Quản lý nhà thuốc")) {
										QuanLy_GUI quanLy = new QuanLy_GUI(emp);
										quanLy.setVisible(true);
										setVisible(false);
									}
									else if (emp.getChucVu().equalsIgnoreCase("Nhân viên bán thuốc")) {
										NhanVien_GUI nhanVien_GUI = new NhanVien_GUI(emp);
										nhanVien_GUI.setVisible(true);
										setVisible(false);
									}
									else if (emp.getChucVu().equalsIgnoreCase("Nhân viên kho")) {
										NhanVienKho_GUI nhanVienKho_GUI = new NhanVienKho_GUI(emp);
										nhanVienKho_GUI.setVisible(true);
										setVisible(false);
									}
								} else {
									Toolkit.getDefaultToolkit().beep();
									JOptionPane.showMessageDialog(null, "Lỗi khi xóa phiếu nhập thuốc này trong CSDL",
											"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
								}
							} else {
								Toolkit.getDefaultToolkit().beep();
								JOptionPane.showMessageDialog(null,
										"Lỗi khi cập nhật lại số lượng tồn của thuốc này trong CSDL",
										"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null,
									"Không thể xóa phiếu nhập thuốc do sản phẩm đã bán đi. Vui lòng thu hồi sản phẩm trong ngày hôm nay để xóa phiếu",
									"Lỗi không thể xóa phiếu nhập thuốc khi đã bán", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null,
								"Bạn chỉ có thể xóa phiếu nhập thuốc đã lập trong ngày hôm nay",
								"Lỗi xóa phiếu nhập thuốc quá thời hạn quy định", JOptionPane.ERROR_MESSAGE);
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
				else if (emp.getChucVu().equalsIgnoreCase("Nhân viên kho")) {
					NhanVienKho_GUI nhanVienKho = new NhanVienKho_GUI(emp);
					nhanVienKho.setVisible(true);
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
		btnBack.setToolTipText("Tho\u00E1t");
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
		lblTitle.setIcon(new ImageIcon("data\\icons\\delete-import-medicine-title.png"));
		lblTitle.setBounds(441, 10, 665, 93);
		panel_2.add(lblTitle);

		JPanel panel_chart = new JPanel();
		panel_chart.setBackground(Color.WHITE);
		panel_chart.setBounds(-13, 109, 1535, 645);
		panel_chart.setLayout(null);
		panel_2.add(panel_chart);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(23, 10, 1015, 345);
		panel_3.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_chart.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblThongTinNhapThuoc = new JLabel("Thông tin phiếu nhập thuốc");
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

		lblMaThuoc = new JLabel("Mã thuốc:");
		lblMaThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaThuoc.setBounds(497, 68, 114, 26);
		panel_3.add(lblMaThuoc);

		JLabel lblNgayNhap = new JLabel("Ngày nhập:");
		lblNgayNhap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgayNhap.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgayNhap.setBounds(497, 112, 114, 26);
		panel_3.add(lblNgayNhap);

		JLabel lblLoaiThuoc = new JLabel("Loại thuốc:");
		lblLoaiThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoaiThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		lblLoaiThuoc.setBounds(31, 207, 114, 26);
		panel_3.add(lblLoaiThuoc);

		JLabel lblNgaySanXuat = new JLabel("Ngày sản xuất:");
		lblNgaySanXuat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgaySanXuat.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgaySanXuat.setBounds(20, 159, 126, 26);
		panel_3.add(lblNgaySanXuat);

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

		JLabel lblNgayHetHan = new JLabel("Ngày hết hạn:");
		lblNgayHetHan.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgayHetHan.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgayHetHan.setBounds(497, 159, 114, 26);
		panel_3.add(lblNgayHetHan);

		txtDonGiaMua = new JTextField();
		txtDonGiaMua.setToolTipText("Nhấn Enter để định dạng tiền");
		txtDonGiaMua.setEditable(false);
		txtDonGiaMua.setFont(new Font("Arial", Font.PLAIN, 16));
		txtDonGiaMua.setColumns(10);
		txtDonGiaMua.setBackground(Color.WHITE);
		txtDonGiaMua.setBounds(169, 254, 308, 29);
		panel_3.add(txtDonGiaMua);

		JLabel lblMaPhieuNhap = new JLabel("Mã phiếu nhập:");
		lblMaPhieuNhap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaPhieuNhap.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaPhieuNhap.setBounds(20, 66, 126, 26);
		panel_3.add(lblMaPhieuNhap);

		txtMaPhieuNhap = new JTextField();
		txtMaPhieuNhap.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaPhieuNhap.setEditable(false);
		txtMaPhieuNhap.setColumns(10);
		txtMaPhieuNhap.setBackground(Color.WHITE);
		txtMaPhieuNhap.setBounds(169, 67, 308, 29);
		panel_3.add(txtMaPhieuNhap);

		JLabel lblSoLuongTon = new JLabel("Số lượng tồn:");
		lblSoLuongTon.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoLuongTon.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoLuongTon.setBounds(497, 305, 114, 26);
		panel_3.add(lblSoLuongTon);

		txtSoLuongTon = new JTextField();
		txtSoLuongTon.setEditable(false);
		txtSoLuongTon.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoLuongTon.setColumns(10);
		txtSoLuongTon.setBackground(Color.WHITE);
		txtSoLuongTon.setBounds(628, 302, 349, 29);
		panel_3.add(txtSoLuongTon);

		txtNgayNhap = new JTextField();
		txtNgayNhap.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNgayNhap.setEditable(false);
		txtNgayNhap.setColumns(10);
		txtNgayNhap.setBackground(Color.WHITE);
		txtNgayNhap.setBounds(628, 112, 349, 29);
		panel_3.add(txtNgayNhap);

		txtMaThuoc = new JTextField();
		txtMaThuoc.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaThuoc.setEditable(false);
		txtMaThuoc.setColumns(10);
		txtMaThuoc.setBackground(Color.WHITE);
		txtMaThuoc.setBounds(628, 67, 349, 29);
		panel_3.add(txtMaThuoc);

		txtNgaySanXuat = new JTextField();
		txtNgaySanXuat.setToolTipText("Nhấn Enter để định dạng tiền");
		txtNgaySanXuat.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNgaySanXuat.setEditable(false);
		txtNgaySanXuat.setColumns(10);
		txtNgaySanXuat.setBackground(Color.WHITE);
		txtNgaySanXuat.setBounds(169, 158, 308, 29);
		panel_3.add(txtNgaySanXuat);

		txtLoaiThuoc = new JTextField();
		txtLoaiThuoc.setToolTipText("Nhấn Enter để định dạng tiền");
		txtLoaiThuoc.setFont(new Font("Arial", Font.BOLD, 16));
		txtLoaiThuoc.setEditable(false);
		txtLoaiThuoc.setColumns(10);
		txtLoaiThuoc.setBackground(Color.WHITE);
		txtLoaiThuoc.setBounds(169, 204, 308, 29);
		panel_3.add(txtLoaiThuoc);

		txtNgayHetHan = new JTextField();
		txtNgayHetHan.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNgayHetHan.setEditable(false);
		txtNgayHetHan.setColumns(10);
		txtNgayHetHan.setBackground(Color.WHITE);
		txtNgayHetHan.setBounds(628, 158, 349, 29);
		panel_3.add(txtNgayHetHan);

		txtSoLuongNhap = new JTextField();
		txtSoLuongNhap.setToolTipText("Nhấn Enter để định dạng tiền");
		txtSoLuongNhap.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoLuongNhap.setEditable(false);
		txtSoLuongNhap.setColumns(10);
		txtSoLuongNhap.setBackground(Color.WHITE);
		txtSoLuongNhap.setBounds(169, 302, 308, 29);
		panel_3.add(txtSoLuongNhap);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(1048, 10, 477, 345);
		panel_4.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_chart.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblThongTinNhaCungCap = new JLabel("Thông tin nhà cung cấp");
		lblThongTinNhaCungCap.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinNhaCungCap.setForeground(SystemColor.textHighlight);
		lblThongTinNhaCungCap.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinNhaCungCap.setBounds(10, 10, 445, 41);
		panel_4.add(lblThongTinNhaCungCap);

		lblMaNhaCungCap = new JLabel("Mã nhà cung cấp:");
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
		txtTenNhaCungCap.setBounds(204, 97, 251, 29);
		panel_4.add(txtTenNhaCungCap);

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
		txtEmail.setBounds(204, 136, 251, 29);
		panel_4.add(txtEmail);

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
		txtSoDienThoai.setBounds(204, 175, 251, 29);
		panel_4.add(txtSoDienThoai);

		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiaChi.setFont(new Font("Arial", Font.BOLD, 16));
		lblDiaChi.setBounds(20, 256, 171, 26);
		panel_4.add(lblDiaChi);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(204, 214, 251, 121);
		panel_4.add(scrollPane_1);

		textAreaDiaChi = new JTextArea();
		textAreaDiaChi.setEditable(false);
		textAreaDiaChi.setLineWrap(true);
		textAreaDiaChi.setFont(new Font("Arial", Font.PLAIN, 16));
		scrollPane_1.setViewportView(textAreaDiaChi);

		txtMaNhaCungCap = new JTextField();
		txtMaNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaNhaCungCap.setEditable(false);
		txtMaNhaCungCap.setColumns(10);
		txtMaNhaCungCap.setBackground(Color.WHITE);
		txtMaNhaCungCap.setBounds(204, 58, 251, 29);
		panel_4.add(txtMaNhaCungCap);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 365, 1502, 231);
		panel_chart.add(scrollPane);

		String[] headers = { "Mã phiếu nhập", "Mã thuốc", "Tên thuốc", "Ngày nhập", "Ngày sản xuất", "Ngày hết hạn",
				"Loại thuốc", "Đơn vị thuốc", "Đơn giá mua", "Xuất xứ", "Số lượng nhập", "Số lượng tồn", "Mã nhân viên",
				"Tên nhân viên" };
		modelPhieuNhapThuoc = new DefaultTableModel(headers, 0);
		tablePhieuNhapThuoc = new JTable(modelPhieuNhapThuoc);
		tablePhieuNhapThuoc.getTableHeader().setBackground(Color.CYAN);
		tablePhieuNhapThuoc.getTableHeader().setFont(new Font(null, Font.PLAIN, 14));
		tablePhieuNhapThuoc.setSelectionBackground(Color.YELLOW);
		tablePhieuNhapThuoc.setSelectionForeground(Color.RED);
		tablePhieuNhapThuoc.setFont(new Font(null, Font.PLAIN, 14));
		tablePhieuNhapThuoc.setRowHeight(22);
		tablePhieuNhapThuoc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

		setTitle("Xóa phiếu nhập thuốc");
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
}
