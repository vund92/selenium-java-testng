package javaTester;

public class Topic_02_Data_Type {

	    static int count = 0;

	    public Topic_02_Data_Type() {

	    }

	    public void visit() {
	        count++;
	        this.print();
	    }

	    public void print() {
	        System.out.println("count = " + count);
	    }

	    public static void main(String[] args) {
	    	Topic_02_Data_Type c1 = new Topic_02_Data_Type();
	        c1.visit();
	        Topic_02_Data_Type c2 = new Topic_02_Data_Type();
	        c2.visit();
	        Topic_02_Data_Type c3 = new Topic_02_Data_Type();
	        c3.visit();
	    }

}
