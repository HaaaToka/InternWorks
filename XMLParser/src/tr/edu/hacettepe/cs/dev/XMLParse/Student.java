package tr.edu.hacettepe.cs.dev.XMLParse;

public class Student{
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
