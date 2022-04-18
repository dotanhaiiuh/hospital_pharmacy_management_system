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

import javax.swing.Box;
import javax.swing.ButtonGroup;
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

import class_DAO.KhachHang_DAO;
import class_Entities.KhachHang;
import class_Entities.NhanVien;
import class_Equipment.DateLabelFormatter;
import class_Equipment.RegularExpression;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.border.EtchedBorder;

public class ThemKhacHang_GUI extends JFrame implements WindowListener, ListSelectionListener {
	private JTextField txtMaKH;

	private JTextField txtHoTenKhachHang;
	private JDatePickerImpl datePickerNgaySinh;
	private Properties p;
	private UtilDateModel model_ngaysinh;
	private JDatePanelImpl datePanelNgaySinh;
	private JTextField txtSoCMND;
	private JTextField txtEmail;
	private JTextField txtSoDienThoai;
	private DefaultTableModel modelKhachHang;
	private JTable tableKhachHang;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	private JRadioButton radNam;
	private JRadioButton radNu;
	private JTextArea textAreaDiaChi;

	private JButton btnHome;
	private JTextField txtMaTheBaoHiem;
	private JTextField textField;
	
	private NhanVien emp;

	private JButton btnThem;

	private boolean validData() {
		String hoTen = txtHoTenKhachHang.getText().trim();
		if (hoTen.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtHoTenKhachHang, "Bạn phải nhập họ và tên khách hàng", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtHoTenKhachHang.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtHoTenKhachHang.requestFocus();
			return false;
		} else {
			txtHoTenKhachHang.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String ngaySinh = datePickerNgaySinh.getJFormattedTextField().getText();
		if (ngaySinh.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(datePickerNgaySinh, "Bạn phải chọn ngày sinh", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			datePickerNgaySinh.getJFormattedTextField()
					.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			datePickerNgaySinh.requestFocus();
			return false;
		} else {
			datePickerNgaySinh.getJFormattedTextField().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String maTheBaoHiem = txtMaTheBaoHiem.getText().trim();
		if (!maTheBaoHiem.equalsIgnoreCase("")) {
			String regex = "^[A-Z]{2,2}\\d{13,13}$";
			if (!RegularExpression.checkMatch(maTheBaoHiem, regex)) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(txtMaTheBaoHiem, "Mã thẻ bảo hiểm chưa đúng định dạng", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				txtMaTheBaoHiem.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				txtMaTheBaoHiem.requestFocus();
				txtMaTheBaoHiem.selectAll();
				return false;
			} else {
				txtMaTheBaoHiem.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		}

		String soCMND = txtSoCMND.getText().trim();
		if (soCMND.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtSoCMND, "Bạn phải nhập số chứng minh nhân dân", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtSoCMND.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtSoCMND.requestFocus();
			return false;
		} else {
			String regex = "^\\d{9,12}$";
			if (!RegularExpression.checkMatch(soCMND, regex)) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(txtSoCMND, "Số CMND phải gồm 9 đến 12 ký số", "Cảnh báo",
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
			JOptionPane.showMessageDialog(txtEmail, "Bạn phải nhập email", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
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

	private void phatSinhMaKhachHang() {
		String soCMND = txtSoCMND.getText().trim();
		if (!soCMND.equalsIgnoreCase("")) {
			String maKH = "KH" + soCMND;
			txtMaKH.setText(maKH);
		}
	}

	private KhachHang taoKhachHang() {
		String maKH = txtMaKH.getText().trim();
		String hoTen = txtHoTenKhachHang.getText().trim();
		Date ngaySinh = model_ngaysinh.getValue();
		Date ngayDangKy = new Date();
		String maTheBaoHiem = txtMaTheBaoHiem.getText().trim();
		String gioiTinh = "Nam";
		if (radNu.isSelected())
			gioiTinh = "Nữ";
		String soCMND = txtSoCMND.getText().trim();
		String email = txtEmail.getText().trim();
		String soDienThoai = txtSoDienThoai.getText().trim();
		String diaChi = textAreaDiaChi.getText().trim();
		return new KhachHang(hoTen, ngaySinh, gioiTinh, soCMND, email, soDienThoai, diaChi, maKH, ngayDangKy,
				maTheBaoHiem);
	}
	
	public void xoaTrang() {
		txtMaKH.setText("");
		txtHoTenKhachHang.setText("");
		datePickerNgaySinh.getJFormattedTextField().setText("");
		txtMaTheBaoHiem.setText("");
		radNam.setSelected(true);
		txtSoCMND.setText("");
		txtEmail.setText("");
		txtSoDienThoai.setText("");
		textAreaDiaChi.setText("");
		tableKhachHang.clearSelection();
		txtHoTenKhachHang.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		datePickerNgaySinh.getJFormattedTextField().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtMaTheBaoHiem.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtSoCMND.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtSoDienThoai.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textAreaDiaChi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtHoTenKhachHang.requestFocus();
		btnThem.setEnabled(true);
	}

	/**
	 * Create the frame.
	 */
	public ThemKhacHang_GUI(NhanVien nv) {
		emp = nv;
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\add-customer.png"));
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
					KhachHang_DAO khachhang_dao = new KhachHang_DAO();
					KhachHang kh = taoKhachHang();
					if (khachhang_dao.themKhachHang(kh)) {
						modelKhachHang.addRow(new Object[] { kh.getMaKH(), kh.getHoTenKhachHang(),
								sdf.format(kh.getNgaySinh()), sdf.format(kh.getNgayDangKy()), kh.getMaTheBaoHiem(),
								kh.getGioiTinh(), kh.getSoCMND(), kh.getEmail(), kh.getSoDienThoai(), kh.getDiaChi() });
						btnThem.setEnabled(false);
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công");
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Thêm khách hàng không thành công do trùng mã khách hàng", "Lỗi ràng buộc CSDL", JOptionPane.ERROR_MESSAGE);
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
				int row = tableKhachHang.getSelectedRow();
				if (row != -1) {
					Toolkit.getDefaultToolkit().beep();
					int confirm = JOptionPane.showConfirmDialog(null,
							"Để xóa khách hàng này bạn cần chuyển sang giao diện xóa khách hàng. Bạn có muốn chuyển sang giao diện xóa khách hàng hay không?", "Chú ý",
							JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						KhachHang_DAO kh_dao = new KhachHang_DAO();
						String maKH = String.valueOf(tableKhachHang.getValueAt(row, 0));
						KhachHang khachHang = kh_dao.layKhachHang(maKH);
						XoaThongTinKhacHang_GUI xoaThongTinKhacHang = new XoaThongTinKhacHang_GUI(khachHang, emp, true);
						xoaThongTinKhacHang.setVisible(true);
						setVisible(false);
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn cần chọn khách hàng muốn xóa thông tin", "Cảnh báo",
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
				int row = tableKhachHang.getSelectedRow();
				if (row != -1) {
					Toolkit.getDefaultToolkit().beep();
					int confirm = JOptionPane.showConfirmDialog(null,
							"Để sửa thông tin khách hàng này bạn cần chuyển sang giao diện sửa thông tin khách hàng. Bạn có muốn chuyển sang giao diện sửa thông tin khách hàng hay không?", "Chú ý",
							JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						KhachHang_DAO kh_dao = new KhachHang_DAO();
						String maKH = String.valueOf(tableKhachHang.getValueAt(row, 0));
						KhachHang khachHang = kh_dao.layKhachHang(maKH);
						SuaThongTinKhacHang_GUI suaThongTinKhacHang = new SuaThongTinKhacHang_GUI(khachHang, emp, getTitle());
						suaThongTinKhacHang.setVisible(true);
						setVisible(false);
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn cần chọn khách hàng muốn sửa thông tin", "Cảnh báo",
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
		lblTitle.setIcon(new ImageIcon("data\\icons\\add-customer-title.png"));
		lblTitle.setBounds(532, 10, 512, 89);
		panel_2.add(lblTitle);

		JPanel panel_chart = new JPanel();
		panel_chart.setBackground(Color.WHITE);
		panel_chart.setBounds(-13, 109, 1535, 645);
		panel_chart.setLayout(null);
		panel_2.add(panel_chart);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(23, 10, 1502, 307);
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_chart.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblThongTinKhachHang = new JLabel("Thông tin khách hàng");
		lblThongTinKhachHang.setForeground(SystemColor.textHighlight);
		lblThongTinKhachHang.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinKhachHang.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongTinKhachHang.setBounds(20, 10, 1448, 35);
		panel_3.add(lblThongTinKhachHang);

		JLabel lblMaNhanVien = new JLabel("Mã khách hàng:");
		lblMaNhanVien.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaNhanVien.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaNhanVien.setBounds(20, 64, 136, 26);
		panel_3.add(lblMaNhanVien);

		txtMaKH = new JTextField();
		txtMaKH.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaKH.setEditable(false);
		txtMaKH.setColumns(10);
		txtMaKH.setBackground(Color.WHITE);
		txtMaKH.setBounds(179, 63, 524, 29);
		panel_3.add(txtMaKH);

		JLabel lblHoTenKhachHang = new JLabel("Tên khách hàng:");
		lblHoTenKhachHang.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHoTenKhachHang.setFont(new Font("Arial", Font.BOLD, 16));
		lblHoTenKhachHang.setBounds(768, 64, 143, 26);
		panel_3.add(lblHoTenKhachHang);

		txtHoTenKhachHang = new JTextField();
		txtHoTenKhachHang.setFont(new Font("Arial", Font.PLAIN, 16));
		txtHoTenKhachHang.setColumns(10);
		txtHoTenKhachHang.setBackground(Color.WHITE);
		txtHoTenKhachHang.setBounds(934, 63, 524, 29);
		panel_3.add(txtHoTenKhachHang);
		txtHoTenKhachHang.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String hoTenKhachHang = txtHoTenKhachHang.getText().trim();
				if (hoTenKhachHang.length() > 40) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtHoTenKhachHang, "Họ tên không được vượt quá 40 ký tự", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					txtHoTenKhachHang.setText(hoTenKhachHang.substring(0, 40));
				}
			}
		});

		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgaySinh.setBounds(20, 114, 136, 26);
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
		datePickerNgaySinh.setBounds(179, 114, 524, 27);
		datePickerNgaySinh.setFocusable(false);
		panel_3.add(datePickerNgaySinh);
		datePickerNgaySinh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ngaySinh = datePickerNgaySinh.getJFormattedTextField().getText();
				LocalDate date_NgaySinh = LocalDate.parse(ngaySinh, dtf);
				if (date_NgaySinh.isEqual(LocalDate.now()) || date_NgaySinh.isAfter(LocalDate.now())) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(datePickerNgaySinh,
							"Ngày sinh không được lớn hơn hoặc bằng ngày hiện tại", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					datePickerNgaySinh.getJFormattedTextField().setText("");
				} else if (LocalDate.now().getYear() - date_NgaySinh.getYear() < 14) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(datePickerNgaySinh, "Khách hàng chưa đủ tuổi", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					datePickerNgaySinh.getJFormattedTextField().setText("");
				}
			}
		});

		JLabel lblNgayDangKy = new JLabel("Ngày đăng ký:");
		lblNgayDangKy.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgayDangKy.setFont(new Font("Arial", Font.BOLD, 16));
		lblNgayDangKy.setBounds(768, 114, 143, 26);
		panel_3.add(lblNgayDangKy);

		radNam = new JRadioButton("Nam");
		radNam.setSelected(true);
		radNam.setBackground(Color.WHITE);
		radNam.setFont(new Font("Arial", Font.PLAIN, 16));
		radNam.setBounds(930, 161, 89, 26);
		panel_3.add(radNam);

		radNu = new JRadioButton("Nữ");
		radNu.setFont(new Font("Arial", Font.PLAIN, 16));
		radNu.setBackground(Color.WHITE);
		radNu.setBounds(1021, 161, 56, 26);
		panel_3.add(radNu);

		ButtonGroup group_gioitinh = new ButtonGroup();
		group_gioitinh.add(radNam);
		group_gioitinh.add(radNu);

		JLabel lblSoCMND = new JLabel("Số CMND:");
		lblSoCMND.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoCMND.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoCMND.setBounds(20, 209, 130, 26);
		panel_3.add(lblSoCMND);

		txtSoCMND = new JTextField();
		txtSoCMND.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoCMND.setColumns(10);
		txtSoCMND.setBackground(Color.WHITE);
		txtSoCMND.setBounds(179, 207, 524, 29);
		panel_3.add(txtSoCMND);
		txtSoCMND.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String soCMND = txtSoCMND.getText().trim();
				if (soCMND.length() > 12) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtSoCMND, "Số CMND không được vượt quá 12 ký tự", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					txtSoCMND.setText(soCMND.substring(0, 12));
				}
			}
		});
		txtSoCMND.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				phatSinhMaKhachHang();
			}
		});

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Arial", Font.BOLD, 16));
		lblEmail.setBounds(768, 209, 143, 26);
		panel_3.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 16));
		txtEmail.setColumns(10);
		txtEmail.setBackground(Color.WHITE);
		txtEmail.setBounds(934, 208, 524, 29);
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

		JLabel lblSoDienThoai = new JLabel("Số điện thoại:");
		lblSoDienThoai.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoDienThoai.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoDienThoai.setBounds(20, 259, 130, 26);
		panel_3.add(lblSoDienThoai);

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoDienThoai.setColumns(10);
		txtSoDienThoai.setBackground(Color.WHITE);
		txtSoDienThoai.setBounds(179, 258, 524, 29);
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

		JLabel lblDiaChi = new JLabel("Địa Chỉ:");
		lblDiaChi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiaChi.setFont(new Font("Arial", Font.BOLD, 16));
		lblDiaChi.setBounds(768, 259, 143, 26);
		panel_3.add(lblDiaChi);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(934, 259, 524, 26);
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

		JLabel lblMaTheBaoHiem = new JLabel("Mã thẻ bảo hiểm:");
		lblMaTheBaoHiem.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaTheBaoHiem.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaTheBaoHiem.setBounds(20, 161, 136, 26);
		panel_3.add(lblMaTheBaoHiem);

		txtMaTheBaoHiem = new JTextField();
		txtMaTheBaoHiem.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaTheBaoHiem.setColumns(10);
		txtMaTheBaoHiem.setBackground(Color.WHITE);
		txtMaTheBaoHiem.setBounds(179, 160, 524, 29);
		panel_3.add(txtMaTheBaoHiem);
		txtMaTheBaoHiem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String maTheBaoHiem = txtMaTheBaoHiem.getText().trim();
				if (maTheBaoHiem.length() > 15) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtMaTheBaoHiem, "Mã thẻ bảo hiểm không được vượt quá 15 ký tự",
							"Cảnh báo", JOptionPane.WARNING_MESSAGE);
					txtMaTheBaoHiem.setText(maTheBaoHiem.substring(0, 15));
				}
			}
		});

		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGioiTinh.setFont(new Font("Arial", Font.BOLD, 16));
		lblGioiTinh.setBounds(770, 161, 143, 26);
		panel_3.add(lblGioiTinh);

		textField = new JTextField(sdf.format(new Date()));
		textField.setFont(new Font("Arial", Font.PLAIN, 16));
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBackground(Color.WHITE);
		textField.setBounds(934, 111, 524, 29);
		panel_3.add(textField);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 327, 1502, 308);
		panel_chart.add(scrollPane);

		String[] headers = { "Mã khách hàng", "Tên khách hàng", "Ngày sinh", "Ngày đăng ký", "Mã thẻ bảo hiểm", "Giới Tính", "Số CMND",
				"Email", "Số điện thoại", "Địa chỉ" };
		modelKhachHang = new DefaultTableModel(headers, 0);
		tableKhachHang = new JTable(modelKhachHang);
		tableKhachHang.getTableHeader().setBackground(Color.CYAN);
		tableKhachHang.getTableHeader().setFont(new Font(null, Font.PLAIN, 14));
		tableKhachHang.setSelectionBackground(Color.YELLOW);
		tableKhachHang.setSelectionForeground(Color.RED);
		tableKhachHang.setFont(new Font(null, Font.PLAIN, 14));
		tableKhachHang.setRowHeight(22);
		tableKhachHang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableKhachHang.getSelectionModel().addListSelectionListener(this);
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
		scrollPane.setViewportView(tableKhachHang);

		setTitle("Thêm khách hàng");
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
