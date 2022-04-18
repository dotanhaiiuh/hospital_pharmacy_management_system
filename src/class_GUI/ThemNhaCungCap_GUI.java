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
import class_Entities.NhaCungCap;
import class_Entities.NhanVien;
import class_Equipment.RegularExpression;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.border.EtchedBorder;

public class ThemNhaCungCap_GUI extends JFrame implements WindowListener, ListSelectionListener {
	private JTextField txtMaNhaCungCap;

	private JTextField txtTenNhaCungCap;
	private DefaultTableModel modelNhaCungCap;
	private JTable tableNhaCungCap;

	private JTextArea textAreaDiaChi;

	private JButton btnHome;
	private JTextField txtEmail;
	private JTextField txtSoDienThoai;

	private NhanVien emp;

	private JButton btnThem;

	private String phatSinhMaNhaCungCap() {
		try {
			NhaCungCap_DAO nhacungcap_dao = new NhaCungCap_DAO();
			String maNhaCungCap_lastest = nhacungcap_dao.layMaNCCCuoiCung();
			String maNhaCungCap = "NCC";
			String stt_string_lastest = maNhaCungCap_lastest.substring(3, maNhaCungCap_lastest.length());
			int stt_int_lastest = Integer.parseInt(stt_string_lastest);
			String stt_current = String.valueOf(stt_int_lastest + 1);
			for (int i = 0; i < (5 - stt_current.length()); i++) {
				maNhaCungCap += "0";
			}
			return maNhaCungCap += stt_current;
		} catch (NullPointerException e) {
			return "NCC" + "00001";
		}
	}

	private NhaCungCap taoNhaCungCap() {
		String maNCC = txtMaNhaCungCap.getText().trim();
		String tenNCC = txtTenNhaCungCap.getText().trim();
		String email = txtEmail.getText().trim();
		String soDienThoai = txtSoDienThoai.getText().trim();
		String diaChi = textAreaDiaChi.getText().trim();
		return new NhaCungCap(maNCC, tenNCC, email, soDienThoai, diaChi);
	}

	private boolean validData() {
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

	public void xoaTrang() {
		txtMaNhaCungCap.setText(phatSinhMaNhaCungCap());
		txtTenNhaCungCap.setText("");
		txtEmail.setText("");
		txtSoDienThoai.setText("");
		textAreaDiaChi.setText("");
		tableNhaCungCap.clearSelection();
		txtTenNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtSoDienThoai.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textAreaDiaChi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtTenNhaCungCap.requestFocus();
		btnThem.setEnabled(true);
	}

	/**
	 * Create the frame.
	 */
	public ThemNhaCungCap_GUI(NhanVien nv) {
		emp = nv;
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\add1.png"));
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

		JButton btnXoaTrang = new JButton("Xóa trắng");
		btnXoaTrang.setIcon(new ImageIcon("data\\icons\\clear.png"));
		btnXoaTrang.setFocusable(false);
		panel.add(btnXoaTrang);
		btnXoaTrang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xoaTrang();
			}
		});

		btnThem = new JButton("Thêm");
		btnThem.setToolTipText("");
		btnThem.setIcon(new ImageIcon("data\\icons\\add.png"));
		btnThem.setFocusable(false);
		panel.add(btnThem);
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validData()) {
					NhaCungCap_DAO ncc_dao = new NhaCungCap_DAO();
					NhaCungCap nhaCungCap = taoNhaCungCap();
					if (ncc_dao.themNhaCungCap(nhaCungCap)) {
						modelNhaCungCap.addRow(new Object[] { nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC(),
								nhaCungCap.getEmail(), nhaCungCap.getSoDienThoai(), nhaCungCap.getDiaChi() });
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Thêm nhà cung cấp thành công");
						btnThem.setEnabled(false);
					} else {
						JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhà cung cấp vào CSDL", "Lỗi kết nối CSDL",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JButton btnXoa = new JButton("Xóa");
		btnXoa.setIcon(new ImageIcon("data\\icons\\delete.png"));
		btnXoa.setFocusable(false);
		panel.add(btnXoa);
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableNhaCungCap.getSelectedRow();
				if (row != -1) {
					Toolkit.getDefaultToolkit().beep();
					int confirm = JOptionPane.showConfirmDialog(null,
							"Để xóa nhà cung cấp này bạn cần chuyển sang giao diện xóa nhà cung cấp. Bạn có muốn chuyển sang giao diện xóa nhà cung cấp hay không?", "Chú ý",
							JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						NhaCungCap_DAO ncc_dao = new NhaCungCap_DAO();
						String maNCC = String.valueOf(tableNhaCungCap.getValueAt(row, 0));
						NhaCungCap nhaCungCap = ncc_dao.layNhaCungCap(maNCC);
						XoaThongTinNhaCungCap_GUI xoaThongTinNhaCungCap = new XoaThongTinNhaCungCap_GUI(nhaCungCap, emp, true);
						xoaThongTinNhaCungCap.setVisible(true);
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn cần chọn nhà cung cấp muốn xóa thông tin", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JButton btnSua = new JButton("Sửa");
		btnSua.setIcon(new ImageIcon("data\\icons\\edit.png"));
		btnSua.setFocusable(false);
		panel.add(btnSua);
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableNhaCungCap.getSelectedRow();
				if (row != -1) {
					Toolkit.getDefaultToolkit().beep();
					int confirm = JOptionPane.showConfirmDialog(null,
							"Để sửa thông tin nhà cung cấp này bạn cần chuyển sang giao diện sửa thông tin nhà cung cấp. Bạn có muốn chuyển sang giao diện sửa thông tin nhà cung cấp hay không?", "Chú ý",
							JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						NhaCungCap_DAO ncc_dao = new NhaCungCap_DAO();
						String maNCC = String.valueOf(tableNhaCungCap.getValueAt(row, 0));
						NhaCungCap nhaCungCap = ncc_dao.layNhaCungCap(maNCC);
						SuaThongTinNhaCungCap_GUI suaThongTinNhaCungCap = new SuaThongTinNhaCungCap_GUI(nhaCungCap, emp, getTitle());
						suaThongTinNhaCungCap.setVisible(true);
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn cần chọn nhà cung cấp muốn sửa thông tin", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
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
				else if (emp.getChucVu().equalsIgnoreCase("Nhân viên kho")) {
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
		lblTitle.setIcon(new ImageIcon("data\\icons\\add-supplier-title.png"));
		lblTitle.setBounds(452, 10, 617, 89);
		panel_2.add(lblTitle);

		JPanel panel_chart = new JPanel();
		panel_chart.setBackground(Color.WHITE);
		panel_chart.setBounds(-13, 109, 1535, 645);
		panel_chart.setLayout(null);
		panel_2.add(panel_chart);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(23, 10, 1502, 234);
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_chart.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblThongTinNhaCungCap = new JLabel("Thông tin nhà cung cấp");
		lblThongTinNhaCungCap.setForeground(SystemColor.textHighlight);
		lblThongTinNhaCungCap.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinNhaCungCap.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinNhaCungCap.setBounds(20, 10, 1448, 35);
		panel_3.add(lblThongTinNhaCungCap);

		JLabel lblMaNhaCungCap = new JLabel("Mã nhà cung cấp:");
		lblMaNhaCungCap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaNhaCungCap.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaNhaCungCap.setBounds(20, 64, 136, 26);
		panel_3.add(lblMaNhaCungCap);

		txtMaNhaCungCap = new JTextField(phatSinhMaNhaCungCap());
		txtMaNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaNhaCungCap.setEditable(false);
		txtMaNhaCungCap.setColumns(10);
		txtMaNhaCungCap.setBackground(Color.WHITE);
		txtMaNhaCungCap.setBounds(179, 63, 524, 29);
		panel_3.add(txtMaNhaCungCap);

		JLabel lblTenNhaCungCap = new JLabel("Tên nhà cung cấp:");
		lblTenNhaCungCap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTenNhaCungCap.setFont(new Font("Arial", Font.BOLD, 16));
		lblTenNhaCungCap.setBounds(768, 64, 143, 26);
		panel_3.add(lblTenNhaCungCap);

		txtTenNhaCungCap = new JTextField();
		txtTenNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 16));
		txtTenNhaCungCap.setColumns(10);
		txtTenNhaCungCap.setBackground(Color.WHITE);
		txtTenNhaCungCap.setBounds(934, 63, 524, 29);
		panel_3.add(txtTenNhaCungCap);
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

		JLabel lblSoDienThoai = new JLabel("Số điện thoại:");
		lblSoDienThoai.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoDienThoai.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoDienThoai.setBounds(768, 115, 143, 26);
		panel_3.add(lblSoDienThoai);

		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiaChi.setFont(new Font("Arial", Font.BOLD, 16));
		lblDiaChi.setBounds(13, 160, 143, 26);
		panel_3.add(lblDiaChi);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(179, 160, 1279, 62);
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
					JOptionPane.showMessageDialog(textAreaDiaChi, "Địa chỉ không được vượt quá 150 ký tự", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					textAreaDiaChi.setText(diaChi.substring(0, 150));
				}
			}
		});

		JLabel lblEmail = new JLabel("Địa chỉ email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Arial", Font.BOLD, 16));
		lblEmail.setBounds(20, 115, 136, 26);
		panel_3.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 16));
		txtEmail.setColumns(10);
		txtEmail.setBackground(Color.WHITE);
		txtEmail.setBounds(179, 114, 524, 29);
		panel_3.add(txtEmail);
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

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoDienThoai.setColumns(10);
		txtSoDienThoai.setBackground(Color.WHITE);
		txtSoDienThoai.setBounds(934, 112, 524, 29);
		panel_3.add(txtSoDienThoai);
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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 254, 1502, 381);
		panel_chart.add(scrollPane);

		String[] headers = { "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ email", "Số điện thoại", "Địa chỉ" };
		modelNhaCungCap = new DefaultTableModel(headers, 0);
		tableNhaCungCap = new JTable(modelNhaCungCap);
		tableNhaCungCap.getTableHeader().setBackground(Color.CYAN);
		tableNhaCungCap.getTableHeader().setFont(new Font(null, Font.PLAIN, 14));
		tableNhaCungCap.setSelectionBackground(Color.YELLOW);
		tableNhaCungCap.setSelectionForeground(Color.RED);
		tableNhaCungCap.setFont(new Font(null, Font.PLAIN, 14));
		tableNhaCungCap.setRowHeight(22);
		tableNhaCungCap.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableNhaCungCap.getSelectionModel().addListSelectionListener(this);
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
		scrollPane.setViewportView(tableNhaCungCap);

		setTitle("Thêm nhà cung cấp");
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
		// TODO Auto-generated method stub
		
	}
}
