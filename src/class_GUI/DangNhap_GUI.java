package class_GUI;

import class_DAO.NhanVien_DAO;
import class_Entities.NhanVien;
import class_Equipment.MyExtension;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.awt.Color;

import javax.swing.*;
import java.awt.*;

public class DangNhap_GUI extends JFrame {
	private JPanel contentPane;
	private JTextField txtUser;
	private JPasswordField txtPassword;

	private static NhanVien employee;

	public void dangNhap() {
		try {
			String username = txtUser.getText().trim();
			String password = MyExtension.ConvertHashToString(txtPassword.getText().trim());
			NhanVien_DAO nv_dao = new NhanVien_DAO();
			employee = nv_dao.layNhanVien(username);
			if (employee == null) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên nào có mã vừa nhập");
			} else {
				if (password.equals(employee.getTaiKhoan().getMatKhau().trim())) {
					txtPassword.setText("Không cho phép hiển thị");
					if (employee.getTaiKhoan().getTrangThai().trim().equalsIgnoreCase("Tài khoản đang mở")) {
						if (employee.getChucVu().trim().equalsIgnoreCase("Quản lý nhà thuốc")) {
							QuanLy_GUI ql = new QuanLy_GUI(employee);
							ql.setVisible(true);
							setVisible(false);
						} else if (employee.getChucVu().trim().equalsIgnoreCase("Nhân Viên kho")) {
							NhanVienKho_GUI nvk = new NhanVienKho_GUI(employee);
							nvk.setVisible(true);
							setVisible(false);
						}
						else if (employee.getChucVu().trim().equalsIgnoreCase("Nhân Viên bán thuốc")) {
							NhanVien_GUI nv = new NhanVien_GUI(employee);
							nv.setVisible(true);
							setVisible(false);
						}
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null,
								"Tài khoản của bạn hiện đang bị khóa. Vui lòng liên hệ quản lý để mở lại",
								"Tài khoản bị khóa", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(txtPassword, "Mật khẩu bạn nhập chưa đúng", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public DangNhap_GUI() {
		JLabel background;
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\pictures\\hospital.png"));
		setTitle("Phần mềm quản lý nhà thuốc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 926, 565);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();

		ImageIcon img = new ImageIcon("data\\pictures\\hospital.png");
		getContentPane().setLayout(null);
		setResizable(false);

		txtUser = new JTextField();
		txtUser.setText("NV20200001");
		txtUser.setToolTipText("Nhập tên đăng nhập");
		txtUser.setHorizontalAlignment(SwingConstants.LEFT);
		txtUser.setBounds(382, 224, 216, 23);
		txtUser.setColumns(10);
		getContentPane().add(txtUser);

		txtPassword = new JPasswordField();
		txtPassword.setToolTipText("Nhập mật khẩu");
		txtPassword.setHorizontalAlignment(SwingConstants.LEFT);
		txtPassword.setColumns(10);
		txtPassword.setBounds(382, 263, 216, 23);
		txtPassword.setText("123456");
		getContentPane().add(txtPassword);
		txtPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dangNhap();
			}
		});

		JCheckBox ckHienMatKhau = new JCheckBox("Hiện mật khẩu");
		ckHienMatKhau.setBackground(new Color(248, 248, 255));
		ckHienMatKhau.setBounds(490, 295, 108, 20);
		getContentPane().add(ckHienMatKhau);
		ckHienMatKhau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ckHienMatKhau.isSelected())
					txtPassword.setEchoChar((char) 0);
				else
					txtPassword.setEchoChar('*');
			}
		});

		JLabel lblUserName = new JLabel("Tài khoản:");
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setForeground(Color.BLUE);
		lblUserName.setBounds(288, 224, 84, 23);
		getContentPane().add(lblUserName);

		JLabel lblPassword = new JLabel("Mật khẩu:");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(Color.BLUE);
		lblPassword.setBounds(288, 263, 84, 23);
		getContentPane().add(lblPassword);

		JButton btnLogin = new JButton("Đăng nhập");
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLogin.setIcon(new ImageIcon("data\\icons\\login.png"));
		btnLogin.setForeground(new Color(0, 128, 0));
		btnLogin.setBounds(288, 332, 115, 35);
		btnLogin.setFocusable(false);
		getContentPane().add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dangNhap();
			}
		});

		JButton btnCancel = new JButton("Thoát");
		btnCancel.setIcon(new ImageIcon("data\\icons\\exit.png"));
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancel.setForeground(Color.RED);
		btnCancel.setBounds(501, 332, 115, 35);
		btnCancel.setFocusable(false);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});

		getContentPane().add(btnCancel);

		JButton btnAbout = new JButton("About?");
		btnAbout.setForeground(Color.BLACK);
		btnAbout.setFont(new Font("Tahoma", Font.ITALIC, 9));
		btnAbout.setBounds(10, 495, 80, 20);
		btnAbout.setFocusable(false);
		btnAbout.setBackground(Color.WHITE);
		getContentPane().add(btnAbout);
		btnAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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

		JButton btnContact = new JButton("Contact?");
		btnContact.setFont(new Font("Tahoma", Font.ITALIC, 9));
		btnContact.setBounds(100, 495, 80, 20);
		btnContact.setFocusable(false);
		btnContact.setBackground(Color.WHITE);
		getContentPane().add(btnContact);
		btnContact.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(25, 25, 112));
		panel_1.setBounds(252, 139, 402, 70);
		getContentPane().add(panel_1);

		JLabel lblNewLabel = new JLabel();
		panel_1.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon("data\\icons\\logo3.png"));
		lblNewLabel.setBackground(new Color(0, 0, 139));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(165, 42, 42));
		lblNewLabel.setFont(new Font("DialogInput", Font.BOLD, 28));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 248, 255));
		panel.setBounds(252, 207, 402, 198);
		getContentPane().add(panel);

		background = new JLabel("", img, JLabel.CENTER);
		background.setToolTipText("");
		background.setForeground(Color.RED);
		background.setBounds(0, 0, 920, 536);
		getContentPane().add(background);
	}
}
