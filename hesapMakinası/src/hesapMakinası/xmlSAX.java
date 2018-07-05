package hesapMakinası;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class xmlSAX {

    public static void main(String[] args) {
        String fileName = "/Users/okanalan/Desktop/bigSampleXML.xml";
        List<student> empList = parseXML(fileName);
        for(student emp : empList){
            System.out.println(emp.toString());  
        }
    }

    private static List<student> parseXML(String fileName) {
        List<student> empList = new ArrayList<>();
        student emp = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
        	int i=0;
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
               if (xmlEvent.isStartElement()){
                   StartElement startElement = xmlEvent.asStartElement();
                   if(startElement.getName().getLocalPart().equals("student")){
                       emp = new student();
                       //Get the 'id' attribute from student element
                   }
                   else if(startElement.getName().getLocalPart().equals("id")){
                       xmlEvent = xmlEventReader.nextEvent();
                       emp.setId(xmlEvent.asCharacters().getData());
                   }
                   else if(startElement.getName().getLocalPart().equals("name")){
                       xmlEvent = xmlEventReader.nextEvent();
                       emp.setName(xmlEvent.asCharacters().getData());
                   }else if(startElement.getName().getLocalPart().equals("class")){
                       xmlEvent = xmlEventReader.nextEvent();
                       emp.setclasss(xmlEvent.asCharacters().getData());
                   }else if(startElement.getName().getLocalPart().equals("graduate")){
                       xmlEvent = xmlEventReader.nextEvent();
                       emp.setgraduate(xmlEvent.asCharacters().getData());
                   }
               }
               //if student end element is reached, add student object to list
               if(xmlEvent.isEndElement()){
                   EndElement endElement = xmlEvent.asEndElement();
                   if(endElement.getName().getLocalPart().equals("student")){
                       empList.add(emp);
                   }
               }
               //event counter=i
               //how many records you want=x ((3*4)+2)*(x+1)
               if(i++==((3*4)+2)*2) break;
            }
            
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return empList;
    }
    
}


class student {
	private String id;
	private String name;
	private String classs;
	private String graduate;
	
	public String getId() {
	    return id;
	}
	public void setId(String id) {
	    this.id = id;
	}
	public String getName() {
	    return name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	public String getclasss() {
	    return classs;
	}
	public void setclasss(String classs) {
	    this.classs = classs;
	}

	public String getgraduate() {
	    return graduate;
	}
	public void setgraduate(String graduate) {
	    this.graduate = graduate;
	}
	
	@Override
	public String toString() {
	    return "student:: ID="+this.id+" Name=" + this.name +" classs=" + this.classs+" graduate=" + this.graduate;
	}

}