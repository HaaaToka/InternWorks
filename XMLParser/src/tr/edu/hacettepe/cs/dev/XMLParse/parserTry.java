package tr.edu.hacettepe.cs.dev.XMLParse;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class parserTry extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					parserTry frame = new parserTry();
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

	public parserTry() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 778, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblFileName = new JLabel("E:\\STAJ\\InternWorks\\XMLParser\\bigSampleXML.xml");
		lblFileName.setBounds(137, 26, 285, 16);
		contentPane.add(lblFileName);
		
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
		
	    final PagingModel pm = new PagingModel(10);
		
		Student[] tmpp=null;
		try {
			tmpp = xmlParseandView(lblFileName.getText(),50);
		} catch (FileNotFoundException | XMLStreamException e1) {
			e1.printStackTrace();
		}
	    pm.data=tmpp;
	    final JTable jt = new JTable(pm);
		
	    
	    
		JComboBox viewOptions = new JComboBox();
		viewOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long timee=System.nanoTime();

				try {//bigSampleXML ->>  count of student is 20.000.000
					if(viewOptions.getSelectedIndex()==1)			
						pm.changeData(xmlParseandView(lblFileName.getText(),10));				
					else if(viewOptions.getSelectedIndex()==2)
						pm.changeData(xmlParseandView(lblFileName.getText(),100));				
					else if(viewOptions.getSelectedIndex()==3)
						pm.changeData(xmlParseandView(lblFileName.getText(),1000));				
					else if(viewOptions.getSelectedIndex()==4)
						pm.changeData(xmlParseandView(lblFileName.getText(),10000000));				
					} catch (FileNotFoundException | XMLStreamException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				System.out.println("#>>#>#>#>#>#>#>#>"+(System.nanoTime()-timee)/1000000+"#>>##>#>#>#>#>");
			}
		});
		viewOptions.setModel(new DefaultComboBoxModel(new String[] {"Nothing","First 10", "First 100", "First 1000","First 4000000"}));
		viewOptions.setBounds(508, 21, 179, 27);
		contentPane.add(viewOptions);
	    
	    
	    
	    JScrollPane jsp = new JScrollPane(jt);
	    jsp.setBounds(19, 5, 682, 298);
	  
	    
		JPanel p = new JPanel();
		p.setBounds(10, 60, 729, 356);
		p.setLayout(null);
		
		p.add(jsp);
		
	    JButton btnLeft = new JButton("<");
	    btnLeft.setBounds(194, 314, 41, 23);
	    p.add(btnLeft);
	    btnLeft.setEnabled(false);

		JLabel label = new JLabel("Page Size: ");
		label.setBounds(245, 314, 53, 14);
	    p.add(label);
	    
	    final JTextField tf = new JTextField("10", 7);
	    tf.setBounds(308, 314, 62, 20);
	    p.add(tf);
	    
	    tf.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        pm.setPageSize(Integer.parseInt(tf.getText()));
	      }
	    });
	    
	    
	    
	    JButton btnRight = new JButton(">");
	    btnRight.setBounds(380, 314, 41, 23);
	    p.add(btnRight);
	    if(pm.getPageCount()<=1)
	    	btnRight.setEnabled(false);
	    
	    btnLeft.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ae) {
	          pm.pageLeft();
	          if (pm.getPageOffset() == 0) {
	            btnLeft.setEnabled(false);
	          }
	          btnRight.setEnabled(true);
	        }
	      });
	    
	    btnRight.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ae) {
	          pm.pageRight();
	          if (pm.getPageOffset() == (pm.getPageCount()-1)) {
	            btnRight.setEnabled(false);
	          }
	          btnLeft.setEnabled(true);
	        }
	      });
		
	    contentPane.add(p);
	    
	}
	
	
	Student[] xmlParseandView(String filePath,int countObject) throws FileNotFoundException, XMLStreamException {
		 
		int i=0,syc=0;
	    Student[] empList = null;
	    Student currEmp = null;
	    String tagContent = null;
	    XMLInputFactory factory = XMLInputFactory.newInstance();
	    XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(filePath));

	    while(reader.hasNext() && i++!=(20*countObject)+3){
	      int event = reader.next();
	            //System.out.println(i);
	      switch(event){
	      
	      //START_ELEMENT-->  <xxxxx>
	        case XMLStreamConstants.START_ELEMENT: 
	          if ("student".equals(reader.getLocalName())){
	            currEmp = new Student();
	          }
	          if("students".equals(reader.getLocalName())){
	            empList = new Student[countObject];
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
	          empList = new Student[countObject];
	          break;
	      }
	      
	    }

	    /*for ( Student emp : empList){
	        System.out.println(emp);
	    	//model.addRow(emp.getStudent());    	
	    }*/
	    
	    return empList;
	  }
	
}

class PagingModel extends AbstractTableModel {

	static String[] headers = { "ID", "Name", "Class","Graduate" };
	
	  protected int pageSize;	
	  protected int pageOffset;
	  protected Student[] data;
	
	  public PagingModel(int size) {
		    pageSize = size;
		    //data[0]=new Student();data[0].setId("1");data[0].setName("2");data[0].setClasss("3");data[0].setGraduate("4");
	  }
	
	  // Return values appropriate for the visible table part.
	  public int getRowCount() {
	    return Math.min(pageSize, data.length);
	  }
	
	  public int getColumnCount() {
	    return headers.length;
	  }
	
	  // Work only on the visible part of the table.
	  public Object getValueAt(int row, int col) {
	    int realRow = row + (pageOffset * pageSize);
	    switch(col){
	    	case 0:
	    		return data[realRow].getId();
	    	case 1:
	    		return data[realRow].getName();
	    	case 2:
	    		return data[realRow].getClasss();
	    	case 3:
	    		return data[realRow].getGraduate();
	    }
	    return "okan";
	  }
	
	  public String getColumnName(int col) {
	    return headers[col];
	  }
	
	  // Use this method to figure out which page you are on.
	  public int getPageOffset() {
	    return pageOffset;
	  }
	
	  public int getPageCount() {
	    return (int) Math.ceil((double) data.length / pageSize);
	  }
	  
	  public void changeData(Student[] dat){
		  data=dat;
	  }
	
	  // Use this method if you want to know how big the real table is . . . we
	  // could also write "getRealValueAt()" if needed.
	  public int getRealRowCount() {
	    return data.length;
	  }
	
	  public int getPageSize() {
	    return pageSize;
	  }
	
	  public void setPageSize(int s) {
	    if (s == pageSize) {
	      return;
	    }
	    int oldPageSize = pageSize;
	    pageSize = s;
	    pageOffset = (oldPageSize * pageOffset) / pageSize;
	    fireTableDataChanged();
	  }
	
	  // Update the page offset and fire a data changed (all rows).
	  public void pageRight() {
	    if (pageOffset < getPageCount() - 1) {
	      pageOffset++;
	      fireTableDataChanged();
	    }
	  }
	
	  // Update the page offset and fire a data changed (all rows).
	  public void pageLeft() {
	    if (pageOffset > 0) {
	      pageOffset--;
	      fireTableDataChanged();
	    }
	  }
}

class Student{
	  private String id;
	  private String name;
	  private String classs;
	  private String graduate;
	  
		String getId() {
		    return id;
		}
		void setId(String id) {
		    this.id = id;
		}
		String getName() {
		    return name;
		}
		void setName(String name) {
		    this.name = name;
		}
		String getClasss() {
		    return classs;
		}
		void setClasss(String classs) {
		    this.classs = classs;
		}

		String getGraduate() {
		    return graduate;
		}
		void setGraduate(String graduate) {
		    this.graduate = graduate;
		}
		
		String[] getStudent() {
			return new String[] {getId(),getName(),getClasss(),getGraduate()};
		}
		

	  @Override
	  public String toString(){
	    return id+" "+name+" "+classs+" "+graduate;
	  }
}