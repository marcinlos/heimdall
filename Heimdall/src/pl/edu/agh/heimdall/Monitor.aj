package pl.edu.agh.heimdall;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.Border;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.EdgedBalloonStyle;
import pl.edu.agh.heimdall.annotations.ImpactOn;
import pl.edu.agh.heimdall.annotations.ListenerTrace;
import pl.edu.agh.heimdall.annotations.Marked;
import rangarok.MyLabel;
import javax.swing.JLabel;

public abstract privileged aspect Monitor{
	
	Border b;
	
	private pointcut internals(): within(pl.edu.agh.heimdall..*);
	
	private pointcut ballons(): within(net.java.balloontip..*);

	abstract pointcut monitored();

	abstract pointcut tracedCatch();

	private pointcut affected(): monitored() && !internals();

	private pointcut setters(JComponent comp): set(@ListenerTrace * *) && args(comp) && !ballons();
	
	private pointcut newComp(JComponent comp): this(comp) && call(*.new(..));
	
	after(JComponent c): newComp(c){
		b = c.getBorder();
	}
	
	
	private pointcut marked(JComponent comp, JComponent targ): @target(Marked) &&  this(comp) && target(targ) && call( * *(..)) && !ballons();

	private pointcut impactOns(): call (@ImpactOn * *(..)) || @within(ImpactOn) && !ballons();
	
	private pointcut newLabels(): call (JLabel.new (..)) && !ballons() && !internals();
	
	private pointcut imp(JComponent comp, JComponent comp2): !ballons() && cflowbelow(impactOns()) && target(comp) && call(* *(..)) && this(comp2) && if(comp!=comp2);
	
	/*
	 * Object around() : affected() { tracer().beginCall(thisJoinPoint); Object
	 * value = proceed(); tracer().callReturns(value); return value; }
	 */
	Object around(): newLabels(){
		System.out.println("Ne lab");
		return new MyLabel("abc");
	}

	
	Object around(JComponent comp, JComponent comp2): imp(comp, comp2){
//		System.out.println("IMP");
//		System.out.println(thisJoinPoint.getSignature());
//		comp.setOpaque(true);
//		blink(comp, Color.CYAN);
//		System.out.println(comp.getClass().getCanonicalName());
		Object retVal = proceed(comp, comp2);
		
		createTip(comp2, comp, "IMPACT", thisJoinPoint.getSignature().getName());
		return retVal;
	}
	
	
	Object around(final JComponent comp, final JComponent targ): marked(comp, targ){
//		System.out.println("Marked");
//		System.out.println(thisJoinPoint.getSignature().getName());
//		System.out.println(comp.getClass().getCanonicalName());
//		System.out.println(targ.getClass().getCanonicalName());
//		final Color oldColor = comp.getBackground();
//		final Border border = b;
//		try {
//			SwingUtilities.invokeAndWait(new Runnable() {
//
//				@Override
//				public void run() {
//					comp.setBorder(BorderFactory.createLineBorder(Color.WHITE, 120));
//					CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(border, BorderFactory.createLineBorder(Color.WHITE, 10, true));
//					comp.setBorder(compoundBorder);
//				}
//			});
//		} catch (InvocationTargetException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	
		
//		Timer tim= new Timer(1000, new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("Return to old col");
//				comp.setBorder(null);				
//				//System.out.println(border);
//			}
//		});
//		tim.setRepeats(false);
//		
//		CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(border, BorderFactory.createLineBorder(Color.PINK, 10, true));
//		comp.setBorder(compoundBorder);
//		Object value = null;
//		
//		tim.start();
		Object value = proceed(comp, targ);
		System.out.println("MTip");
		createMarkedTip(comp, targ, thisJoinPoint.getSignature().getName());
		System.out.println("MTipE");
//		
//		try {
//			SwingUtilities.invokeAndWait(new Runnable() {
//
//				@Override
//				public void run() {
//					comp.setBorder(border);
//				}
//			});
//		} catch (InvocationTargetException | InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		//comp.setBackground(oldColor);
		return value;
	}

	after(final JComponent component): setters(component){
		if (component != null) {
			component.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent arg0) {
					//createListenerTip(component, arg0.getSource());
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					createListenerTip(component, arg0.getSource());
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					//createListenerTip(component, arg0.getSource());

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					//createListenerTip(component, arg0.getSource());
				}

				@Override
				public void mouseClicked(MouseEvent arg0) {
					//createListenerTip(component, arg0.getSource());
				}
			});
			component.addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent arg0) {
					System.out.println("FL");

				}

				@Override
				public void focusGained(FocusEvent arg0) {
					System.out.println("FG");

				}
			});
		}
	}

	private void blink(final JComponent component, final Color blinkColor) {
		final Color oldColor = component.getBackground();
		final Timer timer = new Timer(75, null);
		timer.addActionListener(new ActionListener() {
			private int times = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				component.setBackground(times % 2 == 0 ? blinkColor : oldColor);
				if (times == 3) {
					timer.stop();
				}
				++times;

			}
		});
		timer.setRepeats(true);
		timer.start();
	}

	after() throwing (Exception e): affected() {
		tracer().callThrows(e);
	}

	before(Throwable e): tracedCatch() && handler(Throwable+) && args(e) {
		tracer().exceptionCatched(thisJoinPoint, e);
	}

	private Tracer tracer() {
		return Heimdall.getTracer();
	}

	after() throwing (Exception e): affected() {
		tracer().callThrows(e);
	}

	before(Throwable e): tracedCatch() && handler(Throwable+) && args(e) {
		tracer().exceptionCatched(thisJoinPoint, e);
	}

	
	private BalloonTip createTip(JComponent component, JComponent point, String header, String methodName){
		BalloonTipStyle edgedLook = new EdgedBalloonStyle(Color.WHITE, Color.BLUE);
		String balloonText = String.format("%s\nCall time: %s\nCaller: %s\nCallee: %s.%s()",header,  new Date(), component.getClass().getCanonicalName(), point.getClass().getCanonicalName(), methodName);
		return new BalloonTip(component, new JTextArea(balloonText), edgedLook, true);
	}

	private BalloonTip createMarkedTip(JComponent component, JComponent target, String methodName){
		BalloonTipStyle edgedLook = new EdgedBalloonStyle(Color.WHITE, Color.BLUE);
		String balloonText = String.format("MARKED\nCall time: %s\nMarked: %s\nImpacting: %s.%s()",  new Date(), target.getClass().getCanonicalName(), component.getClass().getCanonicalName(),methodName);
		return new BalloonTip(target, new JTextArea(balloonText), edgedLook, true);
	}
	
	private BalloonTip createListenerTip(JComponent component, Object source){
		BalloonTipStyle edgedLook = new EdgedBalloonStyle(Color.WHITE, Color.BLUE);
		String balloonText = String.format("LISTENER\nCall time: %s\nCaller: %s\nCallee: %s", new Date(), component.getClass().getCanonicalName(), source.getClass().getCanonicalName());
		return new BalloonTip(component, new JTextArea(balloonText), edgedLook, true);
	}
}
