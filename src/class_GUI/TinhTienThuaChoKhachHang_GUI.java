package class_GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;

import javax.swing.border.EtchedBorder;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class TinhTienThuaChoKhachHang_GUI extends JFrame implements WindowListener {

	private JPanel contentPane;
	private JTextField txtTongTienThanhToan;
	private JTextField txtMienGiam;
	private JTextField txtTongTienConLai;
	private JTextField txtSoTienKhachDua;
	private JTextField txtSoTienThua;
	
	private DecimalFormat fmt = new DecimalFormat("###,###");

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

	/**
	 * Create the frame.
	 */
	public TinhTienThuaChoKhachHang_GUI(String tongTienThanhToan, String mienGiam, String tongTienConLai) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("data\\icons\\icons8-calculate-24.png"));
		setTitle("Tính tiền thừa cho khách hàng");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1240, 589);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 10, 1206, 102);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(new ImageIcon("data\\icons\\logo3.png"));
		lblLogo.setBounds(10, 10, 300, 82);
		panel.add(lblLogo);
		
		JLabel lblTitle = new JLabel();
		lblTitle.setIcon(new ImageIcon("data\\icons\\redundancy-money-title.png"));
		lblTitle.setBounds(320, 10, 875, 82);
		panel.add(lblTitle);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(10, 119, 1206, 358);
		
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblThngTinThanh = new JLabel("Thông tin thanh toán");
		lblThngTinThanh.setHorizontalAlignment(SwingConstants.CENTER);
		lblThngTinThanh.setForeground(SystemColor.textHighlight);
		lblThngTinThanh.setFont(new Font("Arial", Font.BOLD, 36));
		lblThngTinThanh.setBounds(10, 10, 1186, 51);
		panel_1.add(lblThngTinThanh);
		
		JLabel lblTongTienThanhToan = new JLabel("Tổng tiền thanh toán:");
		lblTongTienThanhToan.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTongTienThanhToan.setForeground(Color.RED);
		lblTongTienThanhToan.setFont(new Font("Arial", Font.BOLD, 24));
		lblTongTienThanhToan.setBounds(20, 78, 253, 35);
		panel_1.add(lblTongTienThanhToan);
		
		txtTongTienThanhToan = new JTextField(tongTienThanhToan);
		txtTongTienThanhToan.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTongTienThanhToan.setForeground(Color.BLACK);
		txtTongTienThanhToan.setFont(new Font("Arial", Font.BOLD, 22));
		txtTongTienThanhToan.setEditable(false);
		txtTongTienThanhToan.setColumns(10);
		txtTongTienThanhToan.setBackground(Color.WHITE);
		txtTongTienThanhToan.setBounds(283, 78, 840, 35);
		panel_1.add(txtTongTienThanhToan);
		
		JLabel lblDonViTien = new JLabel("VND");
		lblDonViTien.setHorizontalAlignment(SwingConstants.LEFT);
		lblDonViTien.setFont(new Font("Arial", Font.BOLD, 24));
		lblDonViTien.setBounds(1133, 80, 63, 31);
		panel_1.add(lblDonViTien);
		
		JLabel lblMienGiam = new JLabel("Số tiền miễn giảm:");
		lblMienGiam.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMienGiam.setForeground(new Color(148, 0, 211));
		lblMienGiam.setFont(new Font("Arial", Font.BOLD, 24));
		lblMienGiam.setBounds(20, 139, 253, 35);
		panel_1.add(lblMienGiam);
		
		txtMienGiam = new JTextField(mienGiam);
		txtMienGiam.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMienGiam.setForeground(new Color(148, 0, 211));
		txtMienGiam.setFont(new Font("Arial", Font.BOLD, 22));
		txtMienGiam.setEditable(false);
		txtMienGiam.setColumns(10);
		txtMienGiam.setBackground(Color.WHITE);
		txtMienGiam.setBounds(283, 139, 840, 35);
		panel_1.add(txtMienGiam);
		
		JLabel lblDonViTien_1 = new JLabel("VND");
		lblDonViTien_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDonViTien_1.setFont(new Font("Arial", Font.BOLD, 24));
		lblDonViTien_1.setBounds(1133, 141, 63, 31);
		panel_1.add(lblDonViTien_1);
		
		JLabel lblTongTienConLai = new JLabel("Tổng tiền còn lại:");
		lblTongTienConLai.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTongTienConLai.setForeground(Color.BLUE);
		lblTongTienConLai.setFont(new Font("Arial", Font.BOLD, 24));
		lblTongTienConLai.setBounds(20, 199, 253, 35);
		panel_1.add(lblTongTienConLai);
		
		txtTongTienConLai = new JTextField(tongTienConLai);
		txtTongTienConLai.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTongTienConLai.setForeground(Color.BLUE);
		txtTongTienConLai.setFont(new Font("Arial", Font.BOLD, 22));
		txtTongTienConLai.setEditable(false);
		txtTongTienConLai.setColumns(10);
		txtTongTienConLai.setBackground(Color.WHITE);
		txtTongTienConLai.setBounds(283, 199, 840, 35);
		panel_1.add(txtTongTienConLai);
		
		JLabel lblDonViTien_1_1 = new JLabel("VND");
		lblDonViTien_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDonViTien_1_1.setFont(new Font("Arial", Font.BOLD, 24));
		lblDonViTien_1_1.setBounds(1133, 201, 63, 31);
		panel_1.add(lblDonViTien_1_1);
		
		JLabel lblSoTienKhachDua = new JLabel("Số tiền khách đưa:");
		lblSoTienKhachDua.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoTienKhachDua.setForeground(new Color(255, 0, 255));
		lblSoTienKhachDua.setFont(new Font("Arial", Font.BOLD, 24));
		lblSoTienKhachDua.setBounds(20, 254, 253, 35);
		panel_1.add(lblSoTienKhachDua);
		
		txtSoTienKhachDua = new JTextField();
		txtSoTienKhachDua.setToolTipText("Nhấn Enter để định dạng tiền");
		txtSoTienKhachDua.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSoTienKhachDua.setForeground(Color.RED);
		txtSoTienKhachDua.setFont(new Font("Arial", Font.BOLD, 22));
		txtSoTienKhachDua.setColumns(10);
		txtSoTienKhachDua.setBackground(Color.WHITE);
		txtSoTienKhachDua.setBounds(283, 254, 840, 35);
		panel_1.add(txtSoTienKhachDua);
		txtSoTienKhachDua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String soTienKhachDua = txtSoTienKhachDua.getText().trim();
				float float_SoTienKhachDua = Float.parseFloat(loaiBoDinhDangTien(soTienKhachDua));
				txtSoTienKhachDua.setText(fmt.format(float_SoTienKhachDua));
			}
		});
		
		JLabel lblDonViTien_1_1_1 = new JLabel("VND");
		lblDonViTien_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDonViTien_1_1_1.setFont(new Font("Arial", Font.BOLD, 24));
		lblDonViTien_1_1_1.setBounds(1133, 256, 63, 31);
		panel_1.add(lblDonViTien_1_1_1);
		
		JLabel lblSoTienThua = new JLabel("Số tiền thừa:");
		lblSoTienThua.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoTienThua.setForeground(new Color(0, 128, 128));
		lblSoTienThua.setFont(new Font("Arial", Font.BOLD, 24));
		lblSoTienThua.setBounds(20, 308, 253, 35);
		panel_1.add(lblSoTienThua);
		
		txtSoTienThua = new JTextField();
		txtSoTienThua.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSoTienThua.setForeground(new Color(0, 128, 0));
		txtSoTienThua.setFont(new Font("Arial", Font.BOLD, 22));
		txtSoTienThua.setColumns(10);
		txtSoTienThua.setBackground(Color.WHITE);
		txtSoTienThua.setBounds(283, 308, 840, 35);
		panel_1.add(txtSoTienThua);
		
		JLabel lblDonViTien_1_1_1_1 = new JLabel("VND");
		lblDonViTien_1_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDonViTien_1_1_1_1.setFont(new Font("Arial", Font.BOLD, 24));
		lblDonViTien_1_1_1_1.setBounds(1133, 310, 63, 31);
		panel_1.add(lblDonViTien_1_1_1_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(10, 487, 1206, 56);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnTinhTienThua = new JButton("Tính tiền thừa cho khách hàng");
		btnTinhTienThua.setBackground(Color.WHITE);
		btnTinhTienThua.setIcon(new ImageIcon("data\\icons\\icons8-calculate-24.png"));
		btnTinhTienThua.setForeground(Color.RED);
		btnTinhTienThua.setFont(new Font("Arial", Font.PLAIN, 22));
		btnTinhTienThua.setFocusable(false);
		btnTinhTienThua.setBounds(282, 10, 418, 36);
		panel_2.add(btnTinhTienThua);
		btnTinhTienThua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tongTienConLai = txtTongTienConLai.getText().trim();
				String soTienKhachDua = txtSoTienKhachDua.getText().trim();
				float float_SoTienKhachDua = Float.parseFloat(loaiBoDinhDangTien(soTienKhachDua));
				float float_TongTienConLai = Float.parseFloat(loaiBoDinhDangTien(tongTienConLai));
				float soTienThua = float_SoTienKhachDua - float_TongTienConLai;
				if (soTienThua>=0) {
					JOptionPane.showMessageDialog(null, "Số tiền trả lại cho khách là: " + fmt.format(soTienThua) + " VND");
					txtSoTienThua.setText(fmt.format(soTienThua));
				}
				else
				{
					JOptionPane.showMessageDialog(txtSoTienKhachDua, "Khách đưa chưa đủ tiền", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		JButton btnBack = new JButton("Quay lại trang trước đó");
		btnBack.setBackground(Color.WHITE);
		btnBack.setIcon(new ImageIcon("data\\icons\\back-icon.png"));
		btnBack.setForeground(Color.BLUE);
		btnBack.setFont(new Font("Arial", Font.PLAIN, 22));
		btnBack.setMnemonic(KeyEvent.VK_T);
		btnBack.setFocusable(false);
		btnBack.setBounds(710, 10, 412, 36);
		panel_2.add(btnBack);
		setLocationRelativeTo(null);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
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
