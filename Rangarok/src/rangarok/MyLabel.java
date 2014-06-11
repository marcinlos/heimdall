package rangarok;

import javax.swing.JLabel;

import pl.edu.agh.heimdall.annotations.Marked;

@Marked
public class MyLabel extends JLabel{
	
	public MyLabel(String myLabel) {
		super(myLabel);
	}
	
	@Override
	public void setText(String text) {
		super.setText(text);
		System.out.println("myLab - !!!!");
	}
	
	public void testMethod(){
		new StringBuilder();
	}
}
