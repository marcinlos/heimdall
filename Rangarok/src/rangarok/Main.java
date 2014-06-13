package rangarok;

import java.util.ArrayList;
import java.util.Date;


public class Main {
	private String s = "jakistestk";
	private Object objs;
	
	private ArrayList<Object> test_field_list;
	protected String test_field_string;
	protected Object test_field_object;
	private Date test_field_date;
	
	public static void main(String[] args) {
		B b = new B();
		b.testFieldsFromSubclass();
		Main m = new Main();
		System.out.println(m.test_field_list.size());
		try{
			System.out.println(m.test_field_date);
		}catch(IllegalArgumentException e){
			System.out.println("It should be visible in runtime! Date was null and cannot be. Below exception connected with it:");
			System.out.println(e);
		}
		
//		main.test(new Object());
//		main.test(null);
//		System.out.println("Ending...");
	}
	
	protected void test(Object obj){
		System.out.println(s);
		System.out.println(objs);
	}
}

class B extends Main{
	@Override
	protected void test(Object obj) {
		super.test(obj);
	}
	
	public void testFieldsFromSubclass(){
		System.out.println(test_field_string);
		System.out.println(test_field_object);
	}
}
