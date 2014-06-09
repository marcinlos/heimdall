package rangarok;


public class Main {
	public static void main(String[] args) {
		new Main().test(new Object());
		new Main().test(null);
		System.out.println("Ending...");
	}
	
	private void test(Object obj){
		System.out.println(obj);
	}
}
