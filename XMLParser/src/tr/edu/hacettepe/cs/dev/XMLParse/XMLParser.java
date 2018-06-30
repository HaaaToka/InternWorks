package tr.edu.hacettepe.cs.dev.XMLParse;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;

import javax.xml.stream.*;

import javax.swing.JTable;
import java.awt.Scrollbar;
import javax.swing.JScrollPane;


public class XMLParser extends JFrame {

	private JPanel contentPane;
	//private JTable tableStudents;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					XMLParser frame = new XMLParser();
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
	public XMLParser() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 586, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JLabel lblFileName = new JLabel("/Users/okanalan/bigSampleXML.xml");
		lblFileName.setBounds(153, 26, 402, 16);
		contentPane.add(lblFileName);
		
		JButton btnSelectFile = new JButton("Select File");
		btnSelectFile.setBounds(24, 21, 117, 29);
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
             
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 116, 521, 269);
		contentPane.add(scrollPane);
		
		DefaultTableModel modelofView = new DefaultTableModel();
		JTable tableStudents = new JTable(modelofView);
		scrollPane.setViewportView(tableStudents);

		modelofView.addColumn("ID");
		modelofView.addColumn("Name");
		modelofView.addColumn("Class");
		modelofView.addColumn("Graduate");
		

		
		JComboBox viewOptions = new JComboBox();
		viewOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long timee=System.nanoTime();
				if (modelofView.getRowCount() > 0) {
				    for (int i = modelofView.getRowCount() - 1; i > -1; i--) {
				        modelofView.removeRow(i);
				    }
				}
				try {//bigSampleXML ->>  count of student is 6.000.000
					if(viewOptions.getSelectedIndex()==1)
						xmlParseandView(lblFileName.getText(),10,modelofView);
					else if(viewOptions.getSelectedIndex()==2)
						xmlParseandView(lblFileName.getText(),100,modelofView);
					else if(viewOptions.getSelectedIndex()==3)
						xmlParseandView(lblFileName.getText(),1000,modelofView);
					else if(viewOptions.getSelectedIndex()==4)
						xmlParseandView(lblFileName.getText(),1000000,modelofView);
					
				}catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (XMLStreamException e1) {
					e1.printStackTrace();
				}
				System.out.println("#>>#>#>#>#>#>#>#>"+(System.nanoTime()-timee)/1000000+"#>>##>#>#>#>#>");
			}
		});
		viewOptions.setModel(new DefaultComboBoxModel(new String[] {"Nothing","First 10", "First 100", "First 1000","First 4000000"}));
		viewOptions.setBounds(24, 62, 279, 27);
		contentPane.add(viewOptions);
		
	}
	
	void xmlParseandView(String filePath,int countObject,DefaultTableModel model) throws FileNotFoundException, XMLStreamException {
		 
			int i=0;
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
		          break;

		          //CHARACTERS ...>xxxxx</
		        case XMLStreamConstants.CHARACTERS:
		          tagContent = reader.getText().trim();
		          break;

		          //END_ELEMENT </xxxxx>
		        case XMLStreamConstants.END_ELEMENT:
		          switch(reader.getLocalName()){
		            case "student":
		              System.out.println(currEmp);
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
}


/*
 * 
 
 Exception in thread "AWT-EventQueue-0" java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3210)
	at java.util.Arrays.copyOf(Arrays.java:3181)
	at java.util.Vector.grow(Vector.java:266)
	at java.util.Vector.ensureCapacityHelper(Vector.java:246)
	at java.util.Vector.insertElementAt(Vector.java:601)
	at javax.swing.table.DefaultTableModel.insertRow(DefaultTableModel.java:374)
	at javax.swing.table.DefaultTableModel.addRow(DefaultTableModel.java:350)
	at javax.swing.table.DefaultTableModel.addRow(DefaultTableModel.java:361)
	at tr.edu.hacettepe.cs.dev.XMLParse.XMLParser.xmlParseandView(XMLParser.java:191)
	at tr.edu.hacettepe.cs.dev.XMLParse.XMLParser$3.actionPerformed(XMLParser.java:117)
	at javax.swing.JComboBox.fireActionEvent(JComboBox.java:1258)
	at javax.swing.JComboBox.setSelectedItem(JComboBox.java:586)
	at javax.swing.JComboBox.setSelectedIndex(JComboBox.java:622)
	at javax.swing.plaf.basic.BasicComboPopup$Handler.mouseReleased(BasicComboPopup.java:861)
	at java.awt.AWTEventMulticaster.mouseReleased(AWTEventMulticaster.java:290)
	at java.awt.Component.processMouseEvent(Component.java:6533)
	at javax.swing.JComponent.processMouseEvent(JComponent.java:3324)
	at com.apple.laf.AquaComboBoxPopup$1.processMouseEvent(AquaComboBoxPopup.java:157)
	at java.awt.Component.processEvent(Component.java:6298)
	at java.awt.Container.processEvent(Container.java:2237)
	at java.awt.Component.dispatchEventImpl(Component.java:4889)
	at java.awt.Container.dispatchEventImpl(Container.java:2295)
	at java.awt.Component.dispatchEvent(Component.java:4711)
	at java.awt.LightweightDispatcher.retargetMouseEvent(Container.java:4889)
	at java.awt.LightweightDispatcher.processMouseEvent(Container.java:4526)
	at java.awt.LightweightDispatcher.dispatchEvent(Container.java:4467)
	at java.awt.Container.dispatchEventImpl(Container.java:2281)
	at java.awt.Window.dispatchEventImpl(Window.java:2746)
	at java.awt.Component.dispatchEvent(Component.java:4711)
	at java.awt.EventQueue.dispatchEventImpl(EventQueue.java:758)
	at java.awt.EventQueue.access$500(EventQueue.java:97)
	at java.awt.EventQueue$3.run(EventQueue.java:709)

 
 * 
 * */
