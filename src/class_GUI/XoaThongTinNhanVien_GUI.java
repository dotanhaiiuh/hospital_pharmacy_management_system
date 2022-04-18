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
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

import javax.swing.border.MatteBorder;
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
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class XoaThongTinNhanVien_GUI extends JFrame implements WindowListener {
	private JTextField txtMaNV;

	private JLabel lblImage;
	private JTextField txtHoTenNhanVien;
	private JDatePickerImpl datePickerNgaySinh;
	private Properties p;
	private UtilDateModel model_ngaysinh;
	private JDatePanelImpl datePanelNgaySinh;
	private UtilDateModel model_ngayvaolam;
	private JDatePanelImpl datePanelNgayVaoLam;
	private JDatePickerImpl datePickerNgayVaoLam;
	private JTextField txtSoCMND;
	private JTextField txtEmail;
	private JTextField txtSoDienThoai;
	private JPasswordField txtPassword;
	private DefaultTableModel modelNhanVien;
	private JTable tableNhanVien;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	private JComboBox<String> cbChucVu;
	private JComboBox<String> cbTinhTrang;
	private JRadioButton radNam;
	private JRadioButton radNu;
	private JComboBox<String> cbTrangThai;
	private JTextArea textAreaDiaChi;

	private JButton btnBack;
	private JTextField txtNgayHienTai;

	private NhanVien emp;
	private NhanVien nhanVien;

	private String layNgayHienTai() {
		Date ngayHienTai = new Date();
		return sdf.format(ngayHienTai);
	}

	public void initForm() {
		txtMaNV.setText(nhanVien.getMaNV());
		txtHoTenNhanVien.setText(nhanVien.getHoTenNhanVien());
		model_ngaysinh.setValue(nhanVien.getNgaySinh());
		datePickerNgaySinh.getJFormattedTextField().setText(sdf.format(nhanVien.getNgaySinh()));
		model_ngayvaolam.setValue(nhanVien.getNgayVaoLam());
		datePickerNgayVaoLam.getJFormattedTextField().setText(sdf.format(nhanVien.getNgayVaoLam()));
		cbChucVu.setSelectedItem(nhanVien.getChucVu());
		String gioiTinh = nhanVien.getGioiTinh();
		if (gioiTinh.equalsIgnoreCase("Nữ")) {
			radNu.setSelected(true);
		}
		cbTinhTrang.setSelectedItem(nhanVien.getTinhTrang());
		txtSoCMND.setText(nhanVien.getSoCMND());
		txtEmail.setText(nhanVien.getEmail());
		txtSoDienThoai.setText(nhanVien.getSoDienThoai());
		textAreaDiaChi.setText(nhanVien.getDiaChi());
		cbTrangThai.setSelectedItem(nhanVien.getTaiKhoan().getTrangThai());
		lblImage.setIcon(new ImageIcon(nhanVien.getTaiKhoan().getHinhAnh()));
	}

	/**
	 * Create the frame.
	 */
	public XoaThongTinNhanVien_GUI(NhanVien nhanVienCanChinhSua, NhanVien nhanVienThucHienChinhSua, boolean chuyenTiepTuGiaoDienThem) {
		emp = nhanVienThucHienChinhSua;
		nhanVien = nhanVienCanChinhSua;
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\delete-employee.png"));
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

		JButton btnXoa = new JButton("Xóa thông tin nhân viên");
		btnXoa.setBackground(Color.WHITE);
		btnXoa.setIcon(new ImageIcon("data\\icons\\delete.png"));
		btnXoa.setFocusable(false);
		panel.add(btnXoa);
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Toolkit.getDefaultToolkit().beep();
				int confirm_1 = JOptionPane.showConfirmDialog(null,
						"Bạn có chắc chắn muốn xóa nhân viên này hay không? Các phiếu nhập thuốc và hóa đơn do nhân viên này lập sẽ bị xóa theo và không thể khôi phục", "Chú ý", JOptionPane.YES_NO_OPTION);
				if (confirm_1 == JOptionPane.YES_OPTION) {
					NhanVien_DAO nv_dao = new NhanVien_DAO();
					String maNV = txtMaNV.getText().trim();
					if (maNV.equalsIgnoreCase(emp.getMaNV())) {
						Toolkit.getDefaultToolkit().beep();
						int confirm_2 = JOptionPane.showConfirmDialog(null,
								"Bạn đang chọn xóa tài khoản của chính bạn, bạn sẽ mất thông tin và quyền truy cập trên hệ thống. Bạn có muốn tiếp tục xóa hay không?", "Chú ý", JOptionPane.YES_NO_OPTION);
						if (confirm_2 == JOptionPane.YES_OPTION) {
							if (nv_dao.xoaMotNhanVien(maNV)) {
								Toolkit.getDefaultToolkit().beep();
								JOptionPane.showMessageDialog(null, "Xóa thông tin thành công");
								if (!chuyenTiepTuGiaoDienThem) {
									TimThongTinNhanVien_GUI timThongTinNhanVien = new TimThongTinNhanVien_GUI(emp, getTitle());
									timThongTinNhanVien.setVisible(true);
									setVisible(false);
								} else {
									ThemNhanVien_GUI themNhanVien = new ThemNhanVien_GUI(emp);
									themNhanVien.setVisible(true);
									setVisible(false);
								}
							} else {
								Toolkit.getDefaultToolkit().beep();
								JOptionPane.showMessageDialog(null, "Lỗi khi xóa thông tin của bạn trong CSDL", "Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
							}
						}
					} else {
						if (nv_dao.xoaMotNhanVien(maNV)) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "Xóa thông tin nhân viên thành công");
							if (!chuyenTiepTuGiaoDienThem) {
								TimThongTinNhanVien_GUI timThongTinNhanVien = new TimThongTinNhanVien_GUI(emp, getTitle());
								timThongTinNhanVien.setVisible(true);
								setVisible(false);
							} else {
								ThemNhanVien_GUI themNhanVien = new ThemNhanVien_GUI(emp);
								themNhanVien.setVisible(true);
								setVisible(false);
							}
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "Lỗi khi xóa thông tin nhân viên trong CSDL", "Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
						}
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
				setVisible(false);
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
		lblTitle.setIcon(new ImageIcon("data\\icons\\delete-employee-title.png"));
		lblTitle.setBounds(374, 10, 785, 89);
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

		JLabel lblThongTinNhanVien = new JLabel("Thông tin nhân viên");
		lblThongTinNhanVien.setForeground(SystemColor.textHighlight);
		lblThongTinNhanVien.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinNhanVien.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinNhanVien.setBounds(20, 22, 957, 35);
		panel_3.add(lblThongTinNhanVien);

		JLabel lblMaNhanVien = new JLabel("Mã nhân viên:");
		lblMaNhanVien.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaNhanVien.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaNhanVien.setBounds(20, 68, 114, 26);
		panel_3.add(lblMaNhanVien);

		txtMaNV = new JTextField();
		txtMaNV.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaNV.setEditable(false);
		txtMaNV.setColumns(10);
		txtMaNV.setBackground(Color.WHITE);
		txtMaNV.setBounds(144, 67, 308, 29);
		panel_3.add(txtMaNV);

		JLabel lblHoTenNhanVien = new JLabel("Tên nhân viên:");
		lblHoTenNhanVien.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHoTenNhanVien.setFont(new Font("Arial", Font.BOLD, 16));
		lblHoTenNhanVien.setBounds(497, 68, 114, 26);
		panel_3.add(lblHoTenNhanVien);

		txtHoTenNhanVien = new JTextField();
		txtHoTenNhanVien.setEditable(false);
		txtHoTenNhanVien.setFont(new Font("Arial", Font.PLAIN, 16));
		txtHoTenNhanVien.setColumns(10);
		txtHoTenNhanVien.setBackground(Color.WHITE);
		txtHoTenNhanVien.setBounds(628, 67, 349, 29);
		panel_3.add(txtHoTenNhanVien);

		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgaySinh.setBounds(20, 114, 114, 26);
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
		datePickerNgaySinh.setBounds(144, 113, 308, 27);
		datePickerNgaySinh.setFocusable(false);
		panel_3.add(datePickerNgaySinh);

		JLabel lblNgayVaoLam = new JLabel("Ngày vào làm:");
		lblNgayVaoLam.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgayVaoLam.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgayVaoLam.setBounds(495, 114, 114, 26);
		panel_3.add(lblNgayVaoLam);

		model_ngayvaolam = new UtilDateModel();
		datePanelNgayVaoLam = new JDatePanelImpl(model_ngayvaolam, p);
		datePickerNgayVaoLam = new JDatePickerImpl(datePanelNgayVaoLam, new DateLabelFormatter());
		SpringLayout springLayout_2 = (SpringLayout) datePickerNgayVaoLam.getLayout();
		springLayout_2.putConstraint(SpringLayout.SOUTH, datePickerNgayVaoLam.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, datePickerNgayVaoLam);
		datePickerNgayVaoLam.setBounds(628, 114, 349, 27);
		datePickerNgayVaoLam.setFocusable(false);
		panel_3.add(datePickerNgayVaoLam);

		JLabel lblChucVu = new JLabel("Chức vụ:");
		lblChucVu.setHorizontalAlignment(SwingConstants.RIGHT);
		lblChucVu.setFont(new Font("Arial", Font.BOLD, 16));
		lblChucVu.setBounds(20, 160, 114, 26);
		panel_3.add(lblChucVu);

		String[] option_chucvu = "Nhân viên bán thuốc;Nhân viên kho;Quản lý nhà thuốc".split(";");
		cbChucVu = new JComboBox<String>();
		cbChucVu.setFont(new Font("Arial", Font.BOLD, 14));
		cbChucVu.setModel(new DefaultComboBoxModel<>(option_chucvu));
		cbChucVu.setBackground(Color.WHITE);
		cbChucVu.setBounds(144, 160, 308, 29);
		panel_3.add(cbChucVu);

		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGioiTinh.setFont(new Font("Arial", Font.BOLD, 16));
		lblGioiTinh.setBounds(495, 163, 114, 26);
		panel_3.add(lblGioiTinh);

		radNam = new JRadioButton("Nam");
		radNam.setSelected(true);
		radNam.setBackground(Color.WHITE);
		radNam.setFont(new Font("Arial", Font.PLAIN, 16));
		radNam.setBounds(628, 163, 89, 26);
		panel_3.add(radNam);

		radNu = new JRadioButton("Nữ");
		radNu.setFont(new Font("Arial", Font.PLAIN, 16));
		radNu.setBackground(Color.WHITE);
		radNu.setBounds(719, 163, 56, 26);
		panel_3.add(radNu);

		ButtonGroup group_gioitinh = new ButtonGroup();
		group_gioitinh.add(radNam);
		group_gioitinh.add(radNu);

		JLabel lblTinhTrang = new JLabel("Tình trạng:");
		lblTinhTrang.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTinhTrang.setFont(new Font("Arial", Font.BOLD, 16));
		lblTinhTrang.setBounds(20, 209, 114, 26);
		panel_3.add(lblTinhTrang);

		String[] option_trangthailamviec = "Đang làm việc;Đã nghỉ".split(";");
		cbTinhTrang = new JComboBox<String>();
		cbTinhTrang.setFont(new Font("Arial", Font.BOLD, 14));
		cbTinhTrang.setModel(new DefaultComboBoxModel<>(option_trangthailamviec));
		cbTinhTrang.setBackground(Color.WHITE);
		cbTinhTrang.setBounds(144, 209, 308, 29);
		panel_3.add(cbTinhTrang);

		JLabel lblSoCMND = new JLabel("Số CMND:");
		lblSoCMND.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoCMND.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoCMND.setBounds(497, 210, 114, 26);
		panel_3.add(lblSoCMND);

		txtSoCMND = new JTextField();
		txtSoCMND.setEditable(false);
		txtSoCMND.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoCMND.setColumns(10);
		txtSoCMND.setBackground(Color.WHITE);
		txtSoCMND.setBounds(628, 209, 349, 29);
		panel_3.add(txtSoCMND);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Arial", Font.BOLD, 16));
		lblEmail.setBounds(20, 258, 114, 26);
		panel_3.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setEditable(false);
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 16));
		txtEmail.setColumns(10);
		txtEmail.setBackground(Color.WHITE);
		txtEmail.setBounds(144, 257, 308, 29);
		panel_3.add(txtEmail);

		JLabel lblSoDienThoai = new JLabel("Số điện thoại:");
		lblSoDienThoai.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoDienThoai.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoDienThoai.setBounds(497, 259, 114, 26);
		panel_3.add(lblSoDienThoai);

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setEditable(false);
		txtSoDienThoai.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoDienThoai.setColumns(10);
		txtSoDienThoai.setBackground(Color.WHITE);
		txtSoDienThoai.setBounds(628, 258, 349, 29);
		panel_3.add(txtSoDienThoai);
		

		JLabel lblDiaChi = new JLabel("Địa Chỉ:");
		lblDiaChi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiaChi.setFont(new Font("Arial", Font.BOLD, 16));
		lblDiaChi.setBounds(20, 304, 114, 26);
		panel_3.add(lblDiaChi);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(144, 303, 833, 61);
		panel_3.add(scrollPane_1);

		textAreaDiaChi = new JTextArea();
		textAreaDiaChi.setEditable(false);
		textAreaDiaChi.setLineWrap(true);
		textAreaDiaChi.setFont(new Font("Arial", Font.PLAIN, 16));
		scrollPane_1.setViewportView(textAreaDiaChi);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(1048, 10, 477, 374);
		panel_4.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_chart.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblThngTinng = new JLabel("Thông tin đăng nhập");
		lblThngTinng.setHorizontalAlignment(SwingConstants.CENTER);
		lblThngTinng.setForeground(SystemColor.textHighlight);
		lblThngTinng.setFont(new Font("Arial", Font.BOLD, 22));
		lblThngTinng.setBounds(10, 20, 445, 41);
		panel_4.add(lblThngTinng);

		JLabel lblTrangThaiTaiKhoan = new JLabel("Trạng thái tài khoản:");
		lblTrangThaiTaiKhoan.setHorizontalAlignment(SwingConstants.LEFT);
		lblTrangThaiTaiKhoan.setFont(new Font("Arial", Font.BOLD, 16));
		lblTrangThaiTaiKhoan.setBounds(10, 89, 171, 26);
		panel_4.add(lblTrangThaiTaiKhoan);

		String[] option_trangthai = "Tài khoản đang mở;Tài khoản đang khóa".split(";");
		cbTrangThai = new JComboBox<String>();
		cbTrangThai.setModel(new DefaultComboBoxModel<>(option_trangthai));
		cbTrangThai.setBackground(Color.WHITE);
		cbTrangThai.setFont(new Font("Arial", Font.BOLD, 14));
		cbTrangThai.setBounds(10, 125, 251, 29);
		panel_4.add(cbTrangThai);

		lblImage = new JLabel("");
		lblImage.setIcon(new ImageIcon("data\\icons\\avatar.png"));
		lblImage.setBounds(302, 89, 134, 134);
		panel_4.add(lblImage);

		JButton btnCapNhatThongTinDangNhap = new JButton("Xóa thông tin đăng nhập");
		btnCapNhatThongTinDangNhap.setBackground(Color.WHITE);
		btnCapNhatThongTinDangNhap.setFont(new Font("Arial", Font.PLAIN, 16));
		btnCapNhatThongTinDangNhap.setBounds(10, 297, 445, 41);
		panel_4.add(btnCapNhatThongTinDangNhap);

		JLabel lblMatKhau = new JLabel("Mật khẩu:");
		lblMatKhau.setHorizontalAlignment(SwingConstants.LEFT);
		lblMatKhau.setFont(new Font("Arial", Font.BOLD, 16));
		lblMatKhau.setBounds(10, 164, 114, 26);
		panel_4.add(lblMatKhau);

		txtPassword = new JPasswordField();
		txtPassword.setEditable(false);
		txtPassword.setText("<Không cho phép hiển thị>");
		txtPassword.setFont(new Font("Arial", Font.PLAIN, 16));
		txtPassword.setBounds(10, 200, 251, 29);
		panel_4.add(txtPassword);

		JCheckBox ckHienMatKhau = new JCheckBox("Hiện mật khẩu");
		ckHienMatKhau.setFont(new Font("Arial", Font.PLAIN, 14));
		ckHienMatKhau.setBackground(Color.WHITE);
		ckHienMatKhau.setBounds(142, 241, 119, 21);
		panel_4.add(ckHienMatKhau);
		ckHienMatKhau.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ckHienMatKhau.isSelected())
					txtPassword.setEchoChar((char) 0);
				else
					txtPassword.setEchoChar('*');
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 394, 1502, 241);
		panel_chart.add(scrollPane);

		String[] headers = { "Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Ngày vào làm", "Chức vụ", "Giới Tính",
				"Tình trạng", "Số CMND", "Email", "Số điện thoại", "Địa chỉ" };
		modelNhanVien = new DefaultTableModel(headers, 0);
		modelNhanVien.addRow(new Object[] { nhanVien.getMaNV(), nhanVien.getHoTenNhanVien(),
				sdf.format(nhanVien.getNgaySinh()), sdf.format(nhanVien.getNgayVaoLam()), nhanVien.getChucVu(),
				nhanVien.getGioiTinh(), nhanVien.getTinhTrang(), nhanVien.getSoCMND(), nhanVien.getEmail(),
				nhanVien.getSoDienThoai(), nhanVien.getDiaChi() });
		tableNhanVien = new JTable(modelNhanVien);
		tableNhanVien.getTableHeader().setBackground(Color.CYAN);
		tableNhanVien.getTableHeader().setFont(new Font(null, Font.PLAIN, 14));
		tableNhanVien.setSelectionBackground(Color.YELLOW);
		tableNhanVien.setSelectionForeground(Color.RED);
		tableNhanVien.setFont(new Font(null, Font.PLAIN, 14));
		tableNhanVien.setRowHeight(22);
		tableNhanVien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableNhanVien.setCellSelectionEnabled(false);
		scrollPane.setViewportView(tableNhanVien);

		JLabel lblXemNgay = new JLabel("Ngày hiện tại:");
		lblXemNgay.setHorizontalAlignment(SwingConstants.LEFT);
		lblXemNgay.setFont(new Font("Arial", Font.BOLD, 16));
		lblXemNgay.setBounds(1195, 26, 113, 26);
		panel_2.add(lblXemNgay);

		txtNgayHienTai = new JTextField();
		String[] ngayHienTai = layNgayHienTai().split("-");
		String string_NgayHienTai = "Ngày " + ngayHienTai[0] + " tháng " + ngayHienTai[1] + " năm " + ngayHienTai[2];
		txtNgayHienTai.setText(string_NgayHienTai);
		txtNgayHienTai.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNgayHienTai.setEditable(false);
		txtNgayHienTai.setColumns(10);
		txtNgayHienTai.setBackground(Color.WHITE);
		txtNgayHienTai.setBounds(1194, 62, 316, 29);
		panel_2.add(txtNgayHienTai);

		setTitle("Xóa thông tin nhân viên");
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
