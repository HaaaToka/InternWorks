package tr.edu.hacettepe.cs.dev.XMLParse;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class Reader{
	
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
	
	int syc;
	Student[] xmlParse() throws FileNotFoundException, XMLStreamException {
		 
		int i=0;syc=0;
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


