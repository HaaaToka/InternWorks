package hesapMakinası;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;


public class HesapMak extends JFrame {

	private JPanel contentPane;
	private JTextField txtF1;
	private JTextField txtF2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HesapMak frame = new HesapMak();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HesapMak() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		String islemler="0";
		Double sonuc = 0.0;
		
		JLabel lbl1 = new JLabel("Girilen Değer");
		lbl1.setBounds(6, 6, 87, 16);
		contentPane.add(lbl1);
		
		txtF1 = new JTextField();
		txtF1.setBounds(113, 1, 157, 26);
		contentPane.add(txtF1);
		txtF1.setColumns(10);
		
		txtF2 = new JTextField();
		txtF2.setColumns(10);
		txtF2.setBounds(113, 27, 157, 26);
		contentPane.add(txtF2);
		
		JLabel lbl2 = new JLabel("Sonuc");
		lbl2.setBounds(6, 32, 87, 16);
		contentPane.add(lbl2);
		
		JButton button1 = new JButton("1");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(button1);
			}
		});
		button1.setBounds(6, 65, 58, 29);
		contentPane.add(button1);
		
		
		JButton button2 = new JButton("2");
		button2.setBounds(59, 65, 58, 29);
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(button2);
			}
		});
		contentPane.add(button2);
		
		JButton button3 = new JButton("3");
		button3.setBounds(113, 65, 58, 29);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(button3);
			}
		});
		contentPane.add(button3);
		
		JButton button4 = new JButton("4");
		button4.setBounds(6, 98, 58, 29);
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(button4);
			}
		});
		contentPane.add(button4);
		
		JButton button5 = new JButton("5");
		button5.setBounds(59, 98, 58, 29);
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(button5);
			}
		});
		contentPane.add(button5);
		
		JButton button6 = new JButton("6");
		button6.setBounds(113, 98, 58, 29);
		button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(button6);
			}
		});
		contentPane.add(button6);
		
		JButton button7 = new JButton("7");
		button7.setBounds(6, 128, 58, 29);
		button7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(button7);
			}
		});
		contentPane.add(button7);
		
		JButton button8 = new JButton("8");
		button8.setBounds(59, 128, 58, 29);
		button8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(button8);
			}
		});
		contentPane.add(button8);
		
		JButton button9 = new JButton("9");
		button9.setBounds(113, 128, 58, 29);
		button9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(button9);
			}
		});
		contentPane.add(button9);
		
		JButton button0 = new JButton("0");
		button0.setBounds(59, 156, 58, 29);
		button0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(button0);
			}
		});
		contentPane.add(button0);
		
		JButton resultButton = new JButton("=");
		resultButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		resultButton.setBounds(69, 186, 95, 40);
		contentPane.add(resultButton);
		resultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp=txtF1.getText();
				if(temp.compareTo("")!=0) {
					txtF2.setText(Double.toString(fourOperationMath(temp)));
				}
			}
		});
		
		JButton plusButton = new JButton("+");
		plusButton.setBounds(221, 65, 58, 29);
		plusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(plusButton);
			}
		});
		contentPane.add(plusButton);
		
		JButton minusButton = new JButton("-");
		minusButton.setBounds(221, 98, 58, 29);
		minusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(minusButton);
			}
		});
		contentPane.add(minusButton);
		
		JButton divideButton = new JButton("/");
		divideButton.setBounds(221, 128, 58, 29);
		divideButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(divideButton);
			}
		});
		contentPane.add(divideButton);
		
		JButton multiButton = new JButton("*");
		multiButton.setBounds(221, 156, 58, 29);
		multiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concatValue(multiButton);
			}
		});
		contentPane.add(multiButton);
		
		JButton clearButton = new JButton("C");
		clearButton.setBounds(221, 192, 58, 29);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtF1.setText("");txtF2.setText("");
			}
		});
		contentPane.add(clearButton);
		
		
	}

	protected void concatValue(JButton multiButton) {
		String temp=txtF1.getText();
		temp=temp.concat(multiButton.getText());
		txtF1.setText(temp);
	}
	
	protected double fourOperationMath(String text) {
		double result=0.0;
		Stack<String> figures = new Stack<>();
		Stack<String> temp = new Stack<>();
		String temp1="",temp2;
		text+=")";
		
		for(int i=0;i<text.length();i++) {
			if(Character.isDigit(text.charAt(i))) 
				temp1+=text.charAt(i);
			else {
				figures.push(temp1);
				temp1="";
				figures.push(Character.toString(text.charAt(i)));
			}
		}figures.pop();		
		
		while(figures.size()!=0) {
			temp2=figures.pop();
			if(temp2.compareTo("*")==0){
				   temp1=temp.pop();
				   temp2=figures.pop();
				   temp.push(Double.toString(Double.parseDouble(temp1)*Double.parseDouble(temp2)));
			   }
			else if(temp2.compareTo("/")==0) {
				   temp1=temp.pop();
				   temp2=figures.pop();
				   temp.push(Double.toString(Double.parseDouble(temp2)/Double.parseDouble(temp1)));
			}
			else
				temp.push(temp2);
		}

		while(temp.size()!=0) {
			temp2=temp.pop();
			if(temp2.compareTo("+")==0) {
				result+=Double.parseDouble(temp.pop());
			}
			else if(temp2.compareTo("-")==0) {
				result-=Double.parseDouble(temp.pop());
			}
			else
				result+=Double.parseDouble(temp2);
		}
		
		return result;
	}
	
}
