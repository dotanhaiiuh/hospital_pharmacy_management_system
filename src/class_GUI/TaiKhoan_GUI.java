package class_GUI;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;

import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import class_DAO.NhanVien_DAO;
import class_DAO.TaiKhoan_DAO;
import class_Entities.NhanVien;
import class_Entities.TaiKhoan;
import class_Equipment.DateLabelFormatter;
import class_Equipment.FileTypeFilter;
import class_Equipment.MyExtension;
import class_Equipment.RegularExpression;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TaiKhoan_GUI extends JFrame implements WindowListener {
	private JTextField txtMaNV;

	private File file;
	private JLabel lblImage;
	private JTextField txtHoTenNhanVien;
	private JDatePickerImpl datePickerNgaySinh;
	private Properties p;
	private UtilDateModel model_ngaysinh;
	private JDatePanelImpl datePanelNgaySinh;
	private JTextField txtSoCMND;
	private JTextField txtEmail;
	private JTextField txtSoDienThoai;
	private JPasswordField txtMatKhauCu;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private JRadioButton radNam;
	private JRadioButton radNu;
	private JTextArea textAreaDiaChi;

	private JButton btnThemAnh;
	private JButton btnReset;
	private JButton btnBack;

	private NhanVien nhanVien;
	private int soLanNhapSai = 0;

	private JPasswordField txtXacNhanMatKhau;
	private JTextField txtChucVu;
	private JTextField txtTinhTrang;
	private JTextField txtTrangThaiTaiKhoan;
	private JPasswordField txtMatKhauMoi;
	private JTextField txtNgayVaoLam;

	private JCheckBox ckHienMatKhauCu;
	private JCheckBox ckHienMatKhauMoi;
	private JCheckBox ckHienMatKhauXacNhan;

	private void closeAllWindows() {
		Window[] windows = getWindows();
		for (Window window : windows) {
			if (window instanceof JFrame) {
				window.dispose();
			}
		}
	}

	private NhanVien taoNhanVien() {
		try {
			String maNV = txtMaNV.getText().trim();
			String hoTen = txtHoTenNhanVien.getText().trim();
			Date ngaySinh = model_ngaysinh.getValue();
			String string_NgayVaoLam = txtNgayVaoLam.getText().trim();
			Date ngayVaoLam = sdf.parse(string_NgayVaoLam);
			String chucVu = txtChucVu.getText().trim();
			String gioiTinh = "Nam";
			if (radNu.isSelected())
				gioiTinh = "N???";
			String tinhTrang = txtTinhTrang.getText().trim();
			String soCMND = txtSoCMND.getText().trim();
			String email = txtEmail.getText().trim();
			String soDienThoai = txtSoDienThoai.getText().trim();
			String diaChi = textAreaDiaChi.getText().trim();
			return new NhanVien(hoTen, ngaySinh, gioiTinh, soCMND, email, soDienThoai, diaChi, maNV, ngayVaoLam, chucVu,
					tinhTrang, new TaiKhoan());
		} catch (ParseException e) {
			return null;
		}
	}

	private boolean validData() {
		String hoTen = txtHoTenNhanVien.getText().trim();
		if (hoTen.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtHoTenNhanVien, "B???n ph???i nh???p h??? v?? t??n nh??n vi??n", "C???nh b??o",
					JOptionPane.WARNING_MESSAGE);
			txtHoTenNhanVien.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtHoTenNhanVien.requestFocus();
			return false;
		} else {
			txtHoTenNhanVien.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String ngaySinh = datePickerNgaySinh.getJFormattedTextField().getText();
		if (ngaySinh.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(datePickerNgaySinh, "B???n ph???i ch???n ng??y sinh", "C???nh b??o",
					JOptionPane.WARNING_MESSAGE);
			datePickerNgaySinh.getJFormattedTextField()
					.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			datePickerNgaySinh.requestFocus();
			return false;
		} else {
			datePickerNgaySinh.getJFormattedTextField().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String soCMND = txtSoCMND.getText().trim();
		if (soCMND.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtSoCMND, "B???n ph???i nh???p s??? ch???ng minh nh??n d??n", "C???nh b??o",
					JOptionPane.WARNING_MESSAGE);
			txtSoCMND.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtSoCMND.requestFocus();
			return false;
		} else {
			String regex = "^\\d{9,12}$";
			if (!RegularExpression.checkMatch(soCMND, regex)) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(txtSoCMND, "S??? CMND ph???i g???m 9 ?????n 12 k?? s???", "C???nh b??o",
						JOptionPane.WARNING_MESSAGE);
				txtSoCMND.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				txtSoCMND.requestFocus();
				txtSoCMND.selectAll();
				return false;
			} else {
				txtSoCMND.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		}

		String email = txtEmail.getText().trim();
		if (email.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtEmail, "B???n ph???i nh???p email", "C???nh b??o", JOptionPane.WARNING_MESSAGE);
			txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtEmail.requestFocus();
			return false;
		} else {
			txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String soDienThoai = txtSoDienThoai.getText().trim();
		if (soDienThoai.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtSoDienThoai, "B???n ph???i nh???p s??? ??i???n tho???i", "C???nh b??o",
					JOptionPane.WARNING_MESSAGE);
			txtSoDienThoai.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtSoDienThoai.requestFocus();
			return false;
		} else {
			String regex = "^\\d{10,11}$";
			if (!RegularExpression.checkMatch(soDienThoai, regex)) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(txtSoDienThoai, "S??? ??i???n tho???i ph???i g???m 10 ?????n 11 k?? s???", "C???nh b??o",
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
			JOptionPane.showMessageDialog(textAreaDiaChi, "B???n ph???i nh???p ?????a ch???", "C???nh b??o",
					JOptionPane.WARNING_MESSAGE);
			textAreaDiaChi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			textAreaDiaChi.requestFocus();
			return false;
		} else {
			textAreaDiaChi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String matKhauMoi = txtMatKhauMoi.getText().trim();
		if (txtMatKhauMoi.isEditable()) {
			if (!matKhauMoi.equalsIgnoreCase("")) {
				if (matKhauMoi.length() < 6) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtMatKhauMoi, "M???t kh???u ph???i nhi???u h??n 6 k?? t???", "C???nh b??o",
							JOptionPane.WARNING_MESSAGE);
					txtMatKhauMoi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
					txtMatKhauMoi.requestFocus();
					txtMatKhauMoi.selectAll();
					return false;
				} else {
					txtMatKhauMoi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				}
			} else {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(txtMatKhauMoi, "B???n ph???i nh???p m???t kh???u", "C???nh b??o",
						JOptionPane.WARNING_MESSAGE);
				txtMatKhauMoi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				txtMatKhauMoi.requestFocus();
				txtMatKhauMoi.selectAll();
				return false;
			}
		} else {
			txtMatKhauMoi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String matKhauXacNhan = txtXacNhanMatKhau.getText().trim();
		if (!matKhauXacNhan.trim().equals(matKhauMoi.trim())) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtXacNhanMatKhau, "M???t kh???u x??c nh???n kh??ng kh???p v???i m???t kh???u m???i",
					"C???nh b??o", JOptionPane.WARNING_MESSAGE);
			txtXacNhanMatKhau.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtXacNhanMatKhau.requestFocus();
			txtXacNhanMatKhau.selectAll();
		} else {
			txtXacNhanMatKhau.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		return true;
	}

	public void initForm() {
		lblImage.setIcon(new ImageIcon(nhanVien.getTaiKhoan().getHinhAnh()));
		txtMaNV.setText(nhanVien.getMaNV());
		txtHoTenNhanVien.setText(nhanVien.getHoTenNhanVien());
		model_ngaysinh.setValue(nhanVien.getNgaySinh());
		datePickerNgaySinh.getJFormattedTextField().setText(sdf.format(nhanVien.getNgaySinh()));
		txtNgayVaoLam.setText(sdf.format(nhanVien.getNgayVaoLam()));
		txtChucVu.setText(nhanVien.getChucVu());
		String gioiTinh = nhanVien.getGioiTinh();
		if (gioiTinh.equalsIgnoreCase("N???")) {
			radNu.setSelected(true);
		}
		txtTinhTrang.setText(nhanVien.getTinhTrang());
		txtSoCMND.setText(nhanVien.getSoCMND());
		txtEmail.setText(nhanVien.getEmail());
		txtSoDienThoai.setText(nhanVien.getSoDienThoai());
		textAreaDiaChi.setText(nhanVien.getDiaChi());
		txtTrangThaiTaiKhoan.setText(nhanVien.getTaiKhoan().getTrangThai());
		txtMatKhauCu.setText("");
		txtMatKhauMoi.setText("");
		txtMatKhauMoi.setEditable(false);
		txtXacNhanMatKhau.setText("");
		txtXacNhanMatKhau.setEditable(false);
		ckHienMatKhauCu.setSelected(false);
		ckHienMatKhauMoi.setSelected(false);
		ckHienMatKhauXacNhan.setSelected(false);
	}

	/**
	 * Create the frame.
	 */
	public TaiKhoan_GUI(NhanVien nv) {
		nhanVien = nv;
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\edit-employee.png"));

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

		btnReset = new JButton("Tr??? v??? m???c ?????nh");
		btnReset.setBackground(Color.WHITE);
		btnReset.setIcon(new ImageIcon("data\\icons\\reset-16.png"));
		btnReset.setFocusable(false);
		btnReset.setToolTipText("?????t l???i d??? li???u ban ?????u");
		panel.add(btnReset);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initForm();
			}
		});

		JButton btnSave = new JButton("L??u th??ng tin");
		btnSave.setBackground(Color.WHITE);
		btnSave.setIcon(new ImageIcon("data\\icons\\save-16.png"));
		btnSave.setFocusable(false);
		panel.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validData()) {
					Toolkit.getDefaultToolkit().beep();
					int confirm = JOptionPane.showConfirmDialog(null,
							"B???n c?? ch???c ch???n mu???n s???a th??ng tin c?? nh??n hay kh??ng?", "Ch?? ??",
							JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						NhanVien nv = taoNhanVien();
						NhanVien_DAO nv_dao = new NhanVien_DAO();
						if (nv_dao.capNhatThongTinNhanVien(nv)) {
							TaiKhoan_DAO tk_dao = new TaiKhoan_DAO();
							String matKhau = txtMatKhauMoi.getText().trim();
							if (!matKhau.equalsIgnoreCase("")) {
								if (!tk_dao.capNhatMatKhau(matKhau, nhanVien.getMaNV())) {
									Toolkit.getDefaultToolkit().beep();
									JOptionPane.showMessageDialog(null, "L???i khi c???p nh???t l???i m???t kh???u v??o CSDL",
											"L???i k???t n???i CSDL", JOptionPane.ERROR_MESSAGE);
								}
							}
							if (file != null) {
								byte[] hinhAnh = MyExtension.ConvertFile(file.getAbsolutePath());
								if (!tk_dao.capNhatHinhAnh(hinhAnh, nhanVien.getMaNV())) {
									Toolkit.getDefaultToolkit().beep();
									JOptionPane.showMessageDialog(null, "L???i khi c???p nh???t l???i ???nh v??o CSDL",
											"L???i k???t n???i CSDL", JOptionPane.ERROR_MESSAGE);
								}
							}
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null,
									"S???a th??ng tin th??nh c??ng. B???n c???n th???c hi???n ????ng nh???p l???i", "Th??ng b??o",
									JOptionPane.INFORMATION_MESSAGE);
							closeAllWindows();
							DangNhap_GUI dangNhap = new DangNhap_GUI();
							dangNhap.setVisible(true);
							setVisible(false);
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "L???i khi c???p nh???t th??ng tin v??o CSDL",
									"L???i k???t n???i CSDL", JOptionPane.ERROR_MESSAGE);
						}
					}
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

		JButton btnHoTro = new JButton("H??? tr???");
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

		btnBack = new JButton("Tr??? v??? trang tr?????c");
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

		JPanel panel_chart = new JPanel();
		panel_chart.setBackground(Color.WHITE);
		panel_chart.setBounds(0, 10, 1398, 643);
		panel_chart.setLayout(null);
		panel_2.add(panel_chart);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(10, 10, 1374, 622);
		panel_3.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_chart.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblMaNhanVien = new JLabel("M?? nh??n vi??n:");
		lblMaNhanVien.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaNhanVien.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaNhanVien.setBounds(20, 181, 114, 26);
		panel_3.add(lblMaNhanVien);

		txtMaNV = new JTextField();
		txtMaNV.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaNV.setEditable(false);
		txtMaNV.setColumns(10);
		txtMaNV.setBackground(Color.WHITE);
		txtMaNV.setBounds(144, 180, 500, 29);
		panel_3.add(txtMaNV);

		JLabel lblHoTenNhanVien = new JLabel("T??n nh??n vi??n:");
		lblHoTenNhanVien.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHoTenNhanVien.setFont(new Font("Arial", Font.BOLD, 16));
		lblHoTenNhanVien.setBounds(733, 181, 114, 26);
		panel_3.add(lblHoTenNhanVien);

		txtHoTenNhanVien = new JTextField();
		txtHoTenNhanVien.setFont(new Font("Arial", Font.PLAIN, 16));
		txtHoTenNhanVien.setColumns(10);
		txtHoTenNhanVien.setBackground(Color.WHITE);
		txtHoTenNhanVien.setBounds(857, 180, 500, 29);
		panel_3.add(txtHoTenNhanVien);
		txtHoTenNhanVien.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String hoTenNhanVien = txtHoTenNhanVien.getText().trim();
				if (hoTenNhanVien.length() > 40) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtHoTenNhanVien, "H??? t??n kh??ng ???????c v?????t qu?? 40 k?? t???", "C???nh b??o",
							JOptionPane.WARNING_MESSAGE);
					txtHoTenNhanVien.setText(hoTenNhanVien.substring(0, 40));
				}
			}
		});

		JLabel lblNgaySinh = new JLabel("Ng??y sinh:");
		lblNgaySinh.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgaySinh.setBounds(20, 225, 114, 26);
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
		datePickerNgaySinh.setBounds(144, 224, 500, 27);
		datePickerNgaySinh.setFocusable(false);
		panel_3.add(datePickerNgaySinh);
		datePickerNgaySinh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ngaySinh = datePickerNgaySinh.getJFormattedTextField().getText();
				String ngayVaoLam = txtNgayVaoLam.getText();
				LocalDate date_NgayVaoLam = LocalDate.parse(ngayVaoLam, dtf);
				LocalDate date_NgaySinh = LocalDate.parse(ngaySinh, dtf);
				if (date_NgaySinh.isEqual(LocalDate.now()) || date_NgaySinh.isAfter(LocalDate.now())) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(datePickerNgaySinh,
							"Ng??y sinh kh??ng ???????c l???n h??n ho???c b???ng ng??y hi???n t???i", "C???nh b??o",
							JOptionPane.WARNING_MESSAGE);
					datePickerNgaySinh.getJFormattedTextField().setText("");
				} else {
					if (date_NgayVaoLam.isBefore(date_NgaySinh)) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(datePickerNgaySinh, "Ng??y sinh kh??ng th??? sau ng??y v??o l??m",
								"C???nh b??o", JOptionPane.WARNING_MESSAGE);
						datePickerNgaySinh.getJFormattedTextField().setText("");
					} else {
						if (date_NgayVaoLam.getYear() - date_NgaySinh.getYear() < 18) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(datePickerNgaySinh, "B???n ch??a ????? tu???i ????? ??i l??m", "C???nh b??o",
									JOptionPane.WARNING_MESSAGE);
							datePickerNgaySinh.getJFormattedTextField().setText("");
						}
					}
				}
			}
		});

		JLabel lblNgayVaoLam = new JLabel("Ng??y v??o l??m:");
		lblNgayVaoLam.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgayVaoLam.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgayVaoLam.setBounds(733, 225, 114, 26);
		panel_3.add(lblNgayVaoLam);

		JLabel lblChucVu = new JLabel("Ch???c v???:");
		lblChucVu.setHorizontalAlignment(SwingConstants.RIGHT);
		lblChucVu.setFont(new Font("Arial", Font.BOLD, 16));
		lblChucVu.setBounds(20, 271, 114, 26);
		panel_3.add(lblChucVu);

		JLabel lblGioiTinh = new JLabel("Gi???i t??nh:");
		lblGioiTinh.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGioiTinh.setFont(new Font("Arial", Font.BOLD, 16));
		lblGioiTinh.setBounds(733, 271, 114, 26);
		panel_3.add(lblGioiTinh);

		radNam = new JRadioButton("Nam");
		radNam.setSelected(true);
		radNam.setBackground(Color.WHITE);
		radNam.setFont(new Font("Arial", Font.PLAIN, 16));
		radNam.setBounds(857, 271, 89, 26);
		panel_3.add(radNam);

		radNu = new JRadioButton("N???");
		radNu.setFont(new Font("Arial", Font.PLAIN, 16));
		radNu.setBackground(Color.WHITE);
		radNu.setBounds(948, 271, 56, 26);
		panel_3.add(radNu);

		ButtonGroup group_gioitinh = new ButtonGroup();
		group_gioitinh.add(radNam);
		group_gioitinh.add(radNu);

		JLabel lblTinhTrang = new JLabel("T??nh tr???ng:");
		lblTinhTrang.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTinhTrang.setFont(new Font("Arial", Font.BOLD, 16));
		lblTinhTrang.setBounds(20, 317, 114, 26);
		panel_3.add(lblTinhTrang);

		String[] option_trangthailamviec = "??ang l??m vi???c;???? ngh???".split(";");

		JLabel lblSoCMND = new JLabel("S??? CMND:");
		lblSoCMND.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoCMND.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoCMND.setBounds(733, 317, 114, 26);
		panel_3.add(lblSoCMND);

		txtSoCMND = new JTextField();
		txtSoCMND.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoCMND.setColumns(10);
		txtSoCMND.setBackground(Color.WHITE);
		txtSoCMND.setBounds(857, 317, 500, 29);
		panel_3.add(txtSoCMND);
		txtSoCMND.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String soCMND = txtSoCMND.getText().trim();
				if (soCMND.length() > 12) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtSoCMND, "S??? CMND kh??ng ???????c v?????t qu?? 12 k?? t???", "C???nh b??o",
							JOptionPane.WARNING_MESSAGE);
					txtSoCMND.setText(soCMND.substring(0, 12));
				}
			}
		});

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Arial", Font.BOLD, 16));
		lblEmail.setBounds(20, 363, 114, 26);
		panel_3.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 16));
		txtEmail.setColumns(10);
		txtEmail.setBackground(Color.WHITE);
		txtEmail.setBounds(144, 362, 500, 29);
		panel_3.add(txtEmail);
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String email = txtEmail.getText().trim();
				if (email.length() > 40) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtEmail, "Email kh??ng ???????c v?????t qu?? 40 k?? t???", "C???nh b??o",
							JOptionPane.WARNING_MESSAGE);
					txtEmail.setText(email.substring(0, 40));
				}
			}
		});

		JLabel lblSoDienThoai = new JLabel("S??? ??i???n tho???i:");
		lblSoDienThoai.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoDienThoai.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoDienThoai.setBounds(733, 363, 114, 26);
		panel_3.add(lblSoDienThoai);

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoDienThoai.setColumns(10);
		txtSoDienThoai.setBackground(Color.WHITE);
		txtSoDienThoai.setBounds(857, 362, 500, 29);
		panel_3.add(txtSoDienThoai);
		txtSoDienThoai.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String soDienThoai = txtSoDienThoai.getText().trim();
				if (soDienThoai.length() > 11) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtSoDienThoai, "S??? ??i???n tho???i kh??ng ???????c v?????t qu?? 11 k?? t???",
							"C???nh b??o", JOptionPane.WARNING_MESSAGE);
					txtSoDienThoai.setText(soDienThoai.substring(0, 11));
				}
			}
		});

		JLabel lblDiaChi = new JLabel("?????a Ch???:");
		lblDiaChi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiaChi.setFont(new Font("Arial", Font.BOLD, 16));
		lblDiaChi.setBounds(20, 422, 114, 26);
		panel_3.add(lblDiaChi);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(144, 410, 1213, 61);
		panel_3.add(scrollPane_1);

		textAreaDiaChi = new JTextArea();
		textAreaDiaChi.setLineWrap(true);
		textAreaDiaChi.setFont(new Font("Arial", Font.PLAIN, 16));
		scrollPane_1.setViewportView(textAreaDiaChi);
		textAreaDiaChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String diaChi = textAreaDiaChi.getText().trim();
				if (diaChi.length() > 150) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(textAreaDiaChi, "?????a ch??? kh??ng ???????c v?????t qu?? 150 k?? t???", "C???nh b??o",
							JOptionPane.WARNING_MESSAGE);
					textAreaDiaChi.setText(diaChi.substring(0, 150));
				}
			}
		});

		JLabel lblTrangThaiTaiKhoan = new JLabel("Tr???ng th??i:");
		lblTrangThaiTaiKhoan.setBounds(20, 494, 114, 26);
		panel_3.add(lblTrangThaiTaiKhoan);
		lblTrangThaiTaiKhoan.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTrangThaiTaiKhoan.setFont(new Font("Arial", Font.BOLD, 16));

		JLabel lblMatKhauCu = new JLabel("M???t kh???u c??:");
		lblMatKhauCu.setBounds(733, 494, 114, 26);
		panel_3.add(lblMatKhauCu);
		lblMatKhauCu.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMatKhauCu.setFont(new Font("Arial", Font.BOLD, 16));

		txtMatKhauCu = new JPasswordField();
		txtMatKhauCu.setBounds(857, 493, 500, 29);
		panel_3.add(txtMatKhauCu);
		txtMatKhauCu.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMatKhauCu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String password = MyExtension.ConvertHashToString(txtMatKhauCu.getText().trim());
					if (password.equals(nhanVien.getTaiKhoan().getMatKhau().trim())) {
						txtMatKhauCu.setText("Kh??ng cho ph??p hi???n th???");
						txtMatKhauMoi.setEditable(true);
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(txtMatKhauCu, "M???t kh???u b???n nh???p ch??a ????ng", "C???nh b??o",
								JOptionPane.WARNING_MESSAGE);
						txtMatKhauCu.selectAll();
						soLanNhapSai++;
						if (soLanNhapSai >= 3) {
							TaiKhoan_DAO taikhoan_dao = new TaiKhoan_DAO();
							if (taikhoan_dao.khoaTaiKhoan(nhanVien.getMaNV())) {
								Toolkit.getDefaultToolkit().beep();
								JOptionPane.showMessageDialog(null,
										"T??i kho???n c???a b???n ???? b??? kh??a do nh???p sai m???t kh???u x??a qu?? 3 l???n li??n ti???p",
										"T??i kho???n b??? kh??a do vi ph???m ch??nh s??ch b???o m???t", JOptionPane.ERROR_MESSAGE);
							}
							closeAllWindows();
							DangNhap_GUI dangNhap = new DangNhap_GUI();
							dangNhap.setVisible(true);
							setVisible(false);
						}
					}
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
			}
		});

		ckHienMatKhauCu = new JCheckBox("Hi???n m???t kh???u");
		ckHienMatKhauCu.setBounds(1238, 528, 119, 21);
		ckHienMatKhauCu.setHorizontalAlignment(SwingConstants.RIGHT);
		ckHienMatKhauCu.setFont(new Font("Arial", Font.PLAIN, 14));
		ckHienMatKhauCu.setBackground(Color.WHITE);
		panel_3.add(ckHienMatKhauCu);
		ckHienMatKhauCu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ckHienMatKhauCu.isSelected())
					txtMatKhauCu.setEchoChar((char) 0);
				else
					txtMatKhauCu.setEchoChar('*');
			}
		});

		JLabel lblXacNhanMatKhau = new JLabel("X??c nh???n l???i m???t kh???u:");
		lblXacNhanMatKhau.setBounds(675, 556, 172, 26);
		lblXacNhanMatKhau.setHorizontalAlignment(SwingConstants.RIGHT);
		lblXacNhanMatKhau.setFont(new Font("Arial", Font.BOLD, 16));
		panel_3.add(lblXacNhanMatKhau);

		txtXacNhanMatKhau = new JPasswordField();
		txtXacNhanMatKhau.setEditable(false);
		txtXacNhanMatKhau.setBounds(857, 555, 500, 29);
		txtXacNhanMatKhau.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_3.add(txtXacNhanMatKhau);
		txtXacNhanMatKhau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String matKhauMoi = txtMatKhauMoi.getText().trim();
				String matKhauXacNhan = txtXacNhanMatKhau.getText().trim();
				if (!matKhauXacNhan.trim().equals(matKhauMoi.trim())) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtXacNhanMatKhau, "M???t kh???u x??c nh???n kh??ng kh???p v???i m???t kh???u m???i",
							"C???nh b??o", JOptionPane.WARNING_MESSAGE);
					txtXacNhanMatKhau.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
					txtXacNhanMatKhau.requestFocus();
					txtXacNhanMatKhau.selectAll();
				} else {
					txtXacNhanMatKhau.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				}
			}
		});

		ckHienMatKhauXacNhan = new JCheckBox("Hi???n m???t kh???u");
		ckHienMatKhauXacNhan.setBounds(1238, 591, 119, 21);
		ckHienMatKhauXacNhan.setHorizontalAlignment(SwingConstants.RIGHT);
		ckHienMatKhauXacNhan.setFont(new Font("Arial", Font.PLAIN, 14));
		ckHienMatKhauXacNhan.setBackground(Color.WHITE);
		panel_3.add(ckHienMatKhauXacNhan);
		ckHienMatKhauXacNhan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ckHienMatKhauXacNhan.isSelected())
					txtXacNhanMatKhau.setEchoChar((char) 0);
				else
					txtXacNhanMatKhau.setEchoChar('*');
			}
		});

		lblImage = new JLabel("");
		lblImage.setBounds(1223, 9, 134, 134);
		lblImage.setIcon(new ImageIcon("data\\icons\\avatar.png"));
		panel_3.add(lblImage);

		btnThemAnh = new JButton("C???p nh???t ???nh");
		btnThemAnh.setIcon(new ImageIcon(
				"D:\\Phat trien ung dung\\DoTanHai_17008871\\QuanLyQuayThuocBenhVien\\data\\icons\\edit-16.png"));
		btnThemAnh.setForeground(Color.RED);
		btnThemAnh.setBounds(1223, 140, 134, 29);
		panel_3.add(btnThemAnh);
		btnThemAnh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser jFileChooser = new JFileChooser("D:\\Phat trien ung dung\\image");
				jFileChooser.setDialogTitle("Please select a photo");
				jFileChooser.setMultiSelectionEnabled(false);
				jFileChooser.setFileFilter(new FileTypeFilter(".jpg", "JPG"));
				jFileChooser.setFileFilter(new FileTypeFilter(".png", "PNG"));
				int result = jFileChooser.showOpenDialog(null);
				if (result == jFileChooser.APPROVE_OPTION) {
					file = jFileChooser.getSelectedFile();
					lblImage.setIcon(new ImageIcon(file.getAbsolutePath()));
				}
			}
		});

		btnThemAnh.setFont(new Font("Arial", Font.BOLD, 12));
		btnThemAnh.setFocusable(false);
		btnThemAnh.setBackground(Color.YELLOW);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon("data\\icons\\title-information.png"));
		lblNewLabel.setBounds(10, 10, 1214, 160);
		panel_3.add(lblNewLabel);

		txtChucVu = new JTextField();
		txtChucVu.setText((String) null);
		txtChucVu.setFont(new Font("Arial", Font.BOLD, 16));
		txtChucVu.setEditable(false);
		txtChucVu.setColumns(10);
		txtChucVu.setBackground(Color.WHITE);
		txtChucVu.setBounds(144, 270, 500, 29);
		panel_3.add(txtChucVu);

		txtTinhTrang = new JTextField();
		txtTinhTrang.setText((String) null);
		txtTinhTrang.setFont(new Font("Arial", Font.BOLD, 16));
		txtTinhTrang.setEditable(false);
		txtTinhTrang.setColumns(10);
		txtTinhTrang.setBackground(Color.WHITE);
		txtTinhTrang.setBounds(144, 318, 500, 29);
		panel_3.add(txtTinhTrang);

		txtTrangThaiTaiKhoan = new JTextField();
		txtTrangThaiTaiKhoan.setText((String) null);
		txtTrangThaiTaiKhoan.setFont(new Font("Arial", Font.BOLD, 16));
		txtTrangThaiTaiKhoan.setEditable(false);
		txtTrangThaiTaiKhoan.setColumns(10);
		txtTrangThaiTaiKhoan.setBackground(Color.WHITE);
		txtTrangThaiTaiKhoan.setBounds(144, 493, 500, 29);
		panel_3.add(txtTrangThaiTaiKhoan);

		JLabel lblMatKhauMoi = new JLabel("M???t kh???u m???i:");
		lblMatKhauMoi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMatKhauMoi.setFont(new Font("Arial", Font.BOLD, 16));
		lblMatKhauMoi.setBounds(20, 556, 114, 26);
		panel_3.add(lblMatKhauMoi);

		txtMatKhauMoi = new JPasswordField();
		txtMatKhauMoi.setEditable(false);
		txtMatKhauMoi.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMatKhauMoi.setBounds(144, 556, 500, 29);
		panel_3.add(txtMatKhauMoi);
		txtMatKhauMoi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String matKhau = txtMatKhauMoi.getText().trim();
				if (matKhau.length() > 36) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtMatKhauMoi, "M???t kh???u kh??ng ???????c v?????t qu?? 36 k?? t???", "C???nh b??o",
							JOptionPane.WARNING_MESSAGE);
					txtMatKhauMoi.setText(matKhau.substring(0, 36));
					txtMatKhauMoi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
					txtMatKhauMoi.requestFocus();
					txtMatKhauMoi.selectAll();
				}
			}
		});
		txtMatKhauMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String matKhau = txtMatKhauMoi.getText().trim();
				if (matKhau.length() < 6) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtMatKhauMoi, "M???t kh???u ph???i nhi???u h??n 6 k?? t???", "C???nh b??o",
							JOptionPane.WARNING_MESSAGE);
					txtMatKhauMoi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
					txtMatKhauMoi.requestFocus();
					txtMatKhauMoi.selectAll();
					txtXacNhanMatKhau.setText("");
					txtXacNhanMatKhau.setEditable(false);
				} else {
					txtMatKhauMoi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
					txtXacNhanMatKhau.setText("");
					txtXacNhanMatKhau.setEditable(true);
				}
			}
		});

		ckHienMatKhauMoi = new JCheckBox("Hi???n m???t kh???u");
		ckHienMatKhauMoi.setHorizontalAlignment(SwingConstants.RIGHT);
		ckHienMatKhauMoi.setFont(new Font("Arial", Font.PLAIN, 14));
		ckHienMatKhauMoi.setBackground(Color.WHITE);
		ckHienMatKhauMoi.setBounds(525, 591, 119, 21);
		panel_3.add(ckHienMatKhauMoi);
		ckHienMatKhauMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ckHienMatKhauMoi.isSelected())
					txtMatKhauMoi.setEchoChar((char) 0);
				else
					txtMatKhauMoi.setEchoChar('*');
			}
		});

		txtNgayVaoLam = new JTextField();
		txtNgayVaoLam.setEditable(false);
		txtNgayVaoLam.setText((String) null);
		txtNgayVaoLam.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNgayVaoLam.setColumns(10);
		txtNgayVaoLam.setBackground(Color.WHITE);
		txtNgayVaoLam.setBounds(857, 222, 500, 29);
		panel_3.add(txtNgayVaoLam);

		setTitle("Th??ng tin nh??n vi??n");
		setSize(1408, 743);
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
		if (JOptionPane.showConfirmDialog(this, "B???n c?? ch???c ch???n mu???n tho??t ???ng d???ng hay kh??ng?", "X??c nh???n",
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
