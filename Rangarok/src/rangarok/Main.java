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
		Main m = new Main();

		// Get test
		writeCaption("get test");
		b.testFieldsFromSubclass();
		System.out.println(m.test_field_list.size());
		try {
			System.out.println(m.test_field_date);
		} catch (IllegalArgumentException e) {
			System.out
					.println("It should be visible in runtime! Date was null and cannot be. Below is exception connected with it:");
			System.out.println(e);
		}

		// Once test
		writeCaption("once test");
		m.onceLog();
		m.onceLog();
		writeSeparator();
		m.onceAdditional();
		m.onceAdditional();
		writeSeparator();
		m.onceAvoid();
		m.onceAvoid();
		writeSeparator();
		m.onceException();
		try {
			m.onceException();
		} catch (IllegalStateException e) {
			System.out
					.println("It should be visible in runtime! Second invocation of method. Below is exception connected with it:");
			System.out.println(e);
		}

		// main.test(new Object());
		// main.test(null);
		// System.out.println("Ending...");
	}

	private static void writeCaption(String messg) {
		System.out.println(String.format("--------%s--------",
				messg.toUpperCase()));
	}

	private static void writeSeparator() {
		System.out.println("----------------");
	}

	protected void test(Object obj) {
		System.out.println(s);
		System.out.println(objs);
	}

	public void clean() {
		System.out.println("Cleaned state");
	}

	public void checkState() {
		System.out.println("State checked");
	}

	private void onceLog() {
		System.out.println("Olog");
	}

	private void onceException() {
		System.out.println("OExec");
	}

	private void onceAdditional() {
		System.out.println("OAdd");
	}

	protected void onceAvoid() {
		System.out.println("OAv");
	}
}

class B extends Main {
	@Override
	protected void test(Object obj) {
		super.test(obj);
	}

	public void testFieldsFromSubclass() {
		System.out.println(test_field_string);
		System.out.println(test_field_object);
	}
}
