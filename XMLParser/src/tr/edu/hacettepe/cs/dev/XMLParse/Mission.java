package tr.edu.hacettepe.cs.dev.XMLParse;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

public class Mission {

	private List<Student[]> students = new ArrayList<>();
	private final int limit = 10;
	private int pgc = 0;
	private Object lock = new Object();
	
	public void read(Reader r1) throws InterruptedException, FileNotFoundException, XMLStreamException {
		
		while(true) {
			
			synchronized(lock) {
				
				if(students.size()<=limit) {				
				
					students.add(r1.xmlParse());
					
					System.out.println(pgc++ + ". page and size"+students.get(students.size()-1).length);
					
				}
			}
			if(r1.syc==0) {
				break;
			}
		}
	}
	
	
	
	public void insert(String iprt) throws SQLException {
		
		synchronized(lock) {
			
			if(students.size()>0)
				new DatabaseConnect("demo","STUDENT",iprt).insertNewData(students.remove(0));
				//dbx.insertNewData(stus.get(ii++));
			
		}
		
	}
	
}
