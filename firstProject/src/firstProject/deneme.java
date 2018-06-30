package firstProject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class deneme {
  public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
	  int i=0;
	  
    List<Student> empList = null;
    Student currEmp = null;
    String tagContent = null;
    XMLInputFactory factory = XMLInputFactory.newInstance();
    XMLStreamReader reader = factory.createXMLStreamReader(new FileReader("/Users/okanalan/eclipse-workspace/firstProject/xml3.xml"));

    while(reader.hasNext() && i++!=(20*3)+3){
      int event = reader.next();
      switch(event){
      
      //START_ELEMENT-->  <xxxxx>
        case XMLStreamConstants.START_ELEMENT: 
          if ("student".equals(reader.getLocalName())){
            currEmp = new Student();
          }
          if("students".equals(reader.getLocalName())){
            empList = new ArrayList<>();
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
              empList.add(currEmp);
              break;
            case "id":
                currEmp.setId(tagContent);
                break;
            case "name":
              currEmp.setName(tagContent);
              break;
            case "class":
              currEmp.setclasss(tagContent);
              break;
            case "graduate":
              currEmp.setgraduate(tagContent);
              break;
          }
          break;

        case XMLStreamConstants.START_DOCUMENT:
          empList = new ArrayList<>();
          break;
      }      
    }

    for ( Student emp : empList){
      System.out.println(emp);
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
	String getclasss() {
	    return classs;
	}
	void setclasss(String classs) {
	    this.classs = classs;
	}

	String getgraduate() {
	    return graduate;
	}
	void setgraduate(String graduate) {
	    this.graduate = graduate;
	}

  @Override
  public String toString(){
    return id+" "+name+" "+classs+" "+graduate;
  }
}