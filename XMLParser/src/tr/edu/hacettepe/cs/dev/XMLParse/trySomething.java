package tr.edu.hacettepe.cs.dev.XMLParse;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class trySomething extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					trySomething frame = new trySomething();
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
	public trySomething() throws Exception{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 479, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(10, 118, 453, 220);
	    contentPane.add(scrollPane);
	    
		DefaultTableModel modelofView = new DefaultTableModel();
		JTable tableStudents = new JTable(modelofView);
		scrollPane.setViewportView(tableStudents);

		modelofView.addColumn("ID");
		modelofView.addColumn("Name");
		modelofView.addColumn("Class");
		modelofView.addColumn("Graduate");
		
		JLabel lblFileName = new JLabel("E:\\STAJ\\bigSampleXML.xml");
		lblFileName.setBounds(137, 26, 440, 16);
		contentPane.add(lblFileName);
		
		Reader rr = new Reader();
		rr.setFilePath(lblFileName.getText());
		rr.setPageSize(10);
		
		JButton btnSelectFile = new JButton("Select File");
		btnSelectFile.setBounds(10, 20, 117, 29);
		contentPane.add(btnSelectFile);
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = jfc.showOpenDialog(null);		
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					lblFileName.setText(jfc.getSelectedFile().getAbsolutePath());				
				}
			}
		});
	    

		JLabel label = new JLabel("Page Size: ");
		label.setBounds(137, 348, 86, 24);
	    contentPane.add(label);
	    
	    final JTextField tf = new JTextField("10", 7);
	    tf.setBounds(223, 350, 138, 20);
	    contentPane.add(tf);
	    tf.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ae) {
				try {
					rr.setPageSize(Integer.parseInt(tf.getText()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				modelRefresher(modelofView,rr,1);

		      }
		    });
	    
	    JButton btnRight = new JButton(">");
	    btnRight.setBounds(373, 350, 41, 23);
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelRefresher(modelofView,rr,1);
				rr.setPageNumber(rr.getCurrentPageNumber()+1);
				System.out.println(rr.getCurrentPageNumber());
			}
		});
	    contentPane.add(btnRight);
	    
	    JButton btnLeft = new JButton("<");
	    btnLeft.setBounds(73, 349, 51, 29);
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rr.getCurrentPageNumber()>1) {
					modelRefresher(modelofView,rr,-1);
					System.out.println(rr.getCurrentPageNumber());
				}
			}
		});

	    contentPane.add(btnLeft);
	   
	}
	
	void modelRefresher(DefaultTableModel tbl,Reader ridit,int norp) {
		
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				modelRemover(tbl);
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if(norp==1)
						modelWriter(tbl,ridit.xmlParse());
					else
						modelWriter(tbl,ridit.backer());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		}catch(InterruptedException e1) {}

		
	}
	
	void modelRemover(DefaultTableModel tbl) {
		if (tbl.getRowCount() > 0) {
		    for (int i = tbl.getRowCount() - 1; i > -1; i--) {
		        tbl.removeRow(i);
		    }
		}
	}
	void modelWriter(DefaultTableModel tbl,Student[] students) {
		for(Student stu:students){
			//System.out.println(stu.toString());
			tbl.addRow(stu.getStudent());
		}
	}
}

class Reader{
	
	private XMLInputFactory factory;
	private XMLStreamReader reader;
	private void updateFile(String path) throws Exception {
		
		factory=XMLInputFactory.newInstance();
		reader = factory.createXMLStreamReader(new FileReader(path));
	}
	
	private String filePath;
	private int pageSize;
	private int currentPageNumber;
	
	public Reader() {
		currentPageNumber=0;
	}
	
	void setPageSize(int size)  throws Exception{
		pageSize=size;
		updateFile(filePath);
		setPageNumber(0);
	}
	int getPageSize() {
		return pageSize;
	}
	
	void setPageNumber(int number) {
		currentPageNumber=number;
	}
	int getCurrentPageNumber() {
		return currentPageNumber;
	}
	
	void setFilePath(String path) throws Exception{
		filePath=path;
		updateFile(path);
	}
	String getFilePath() {
		return filePath;
	}
	
	Student[] backer() throws Exception {
		updateFile(filePath);
		int crpn=getCurrentPageNumber();

		for(int i=0;i<crpn-2;i++) {
			this.xmlParse();
			//System.out.println(i);
		}
		setPageNumber(crpn-1);
		return xmlParse();
		
	}
	
	
	Student[] xmlParse() throws FileNotFoundException, XMLStreamException {
		 
		int i=0,syc=0;
	    Student[] empList = new Student[getPageSize()];
	    Student currEmp = null;
	    String tagContent = null;
	    
	    while(reader.hasNext() && syc!=getPageSize()){
	      int event = reader.next();
	      //System.out.println(i);
	      switch(event){
	      
	      //START_ELEMENT-->  <xxxxx>
	        case XMLStreamConstants.START_ELEMENT: 
	          if ("student".equals(reader.getLocalName())){
	            currEmp = new Student();
	          }
	          break;

	          //CHARACTERS ...>xxxxx</
	        case XMLStreamConstants.CHARACTERS:
	          tagContent = reader.getText().trim();
	          break;

	          //END_ELEMENT </xxxxx>
	        case XMLStreamConstants.END_ELEMENT:
	          switch(reader.getLocalName()){
	            case "student":
	              empList[syc++]=currEmp;
	              break;
	            case "id":
	                currEmp.setId(tagContent);
	                break;
	            case "name":
	              currEmp.setName(tagContent);
	              break;
	            case "class":
	              currEmp.setClasss(tagContent);
	              break;
	            case "graduate":
	              currEmp.setGraduate(tagContent);
	              break;
	          }
	          break;

	        case XMLStreamConstants.START_DOCUMENT:
	          empList = new Student[getPageSize()];
	          break;
	      }
	      
	    }
	   
	    return empList;
	  }
	
	
}
