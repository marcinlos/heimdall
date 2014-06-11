package rangarok;


public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.test(new Object());
		main.test(null);
		System.out.println("Ending...");
	}
	
	private void test(Object obj){
		System.out.println(obj);
	}
}
