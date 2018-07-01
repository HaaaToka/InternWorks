package hesapMakinasi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.FlowLayout;

public class PagingTester2 extends JFrame {

  public PagingTester2() {
    super("Paged JTable Test");
    setSize(702, 479);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    final PagingModel pm = new PagingModel();
    final JTable jt = new JTable(pm);

    // Use our own custom scrollpane.
    JScrollPane jsp = new JScrollPane(jt);
    getContentPane().add(jsp, BorderLayout.CENTER);

    // Property features testing
    JPanel p = new JPanel();
    p.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
    
    JButton btnLeft = new JButton("<");
    p.add(btnLeft);
    btnLeft.setEnabled(false);
    
    JLabel label = new JLabel("Page Size: ");
    p.add(label);
    
    final JTextField tf = new JTextField("100", 7);
    p.add(tf);
    
    tf.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        pm.setPageSize(Integer.parseInt(tf.getText()));
      }
    });
    
    JButton btnRight = new JButton(">");
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
    
    getContentPane().add(p,BorderLayout.SOUTH);
    
  }

  public static void main(String args[]) {
    PagingTester2 pt = new PagingTester2();
    pt.setVisible(true);
  }
}

class PagingModel extends AbstractTableModel {

	  protected int pageSize;	
	  protected int pageOffset;
	  protected Record[] data;
	
	  public PagingModel() {
	    this(1000, 10);
	  }
	
	  public PagingModel(int numRows, int size) {
	    data = new Record[numRows];
	    pageSize = size;
	
	    // Fill our table with random data (from the Record() constructor).
	    for (int i = 0; i < data.length; i++) {
	      data[i] = new Record();
	    }
	  }
	
	  // Return values appropriate for the visible table part.
	  public int getRowCount() {
	    return Math.min(pageSize, data.length);
	  }
	
	  public int getColumnCount() {
	    return Record.getColumnCount();
	  }
	
	  // Work only on the visible part of the table.
	  public Object getValueAt(int row, int col) {
	    int realRow = row + (pageOffset * pageSize);
	    return data[realRow].getValueAt(col);
	  }
	
	  public String getColumnName(int col) {
	    return Record.getColumnName(col);
	  }
	
	  // Use this method to figure out which page you are on.
	  public int getPageOffset() {
	    return pageOffset;
	  }
	
	  public int getPageCount() {
	    return (int) Math.ceil((double) data.length / pageSize);
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

//Record.java
//A simple data structure for use with the PagingModel demo.
//

class Record {
  static String[] headers = { "Record Number", "Batch Number", "Reserved" };

  static int counter;

  String[] data;

  public Record() {
    data = new String[] { "" + (counter++),
        "" + System.currentTimeMillis(), "Reserved" };
  }

  public String getValueAt(int i) {
    return data[i];
  }

  public static String getColumnName(int i) {
    return headers[i];
  }

  public static int getColumnCount() {
    return headers.length;
  }
}

