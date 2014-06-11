package rangarok;


public class Main {
	private String s = "jakistestk";
	private Object objs;
	public static void main(String[] args) {
		Main main = new Main();
		main.test(new Object());
		main.test(null);
		System.out.println("Ending...");
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
}
