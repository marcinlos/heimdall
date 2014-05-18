package pl.edu.agh.heimdall;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import pl.edu.agh.heimdall.annotations.*;

public abstract privileged aspect Monitor {

	private pointcut internals(): within(pl.edu.agh.heimdall..*);

	abstract pointcut monitored();

	abstract pointcut tracedCatch();

	private pointcut affected(): monitored() && !internals();

	private pointcut setters(JComponent comp): set(@Trace * *) && args(comp);


	/*
	 * Object around() : affected() { tracer().beginCall(thisJoinPoint); Object
	 * value = proceed(); tracer().callReturns(value); return value; }
	 */

	after(final JComponent component): setters(component){
		System.out.println("Here!");
		if (component != null) {
			System.out.println("NN!");
			component.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					blink(component, Color.pink);
					}

				@Override
				public void mousePressed(MouseEvent arg0) {
					blink(component, Color.RED);
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					blink(component, Color.black);

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					blink(component, Color.white);
				}

				@Override
				public void mouseClicked(MouseEvent arg0) {
					blink(component, Color.blue);

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
		final Timer timer = new Timer(75,null);
		timer.addActionListener(new ActionListener() {
			private int times = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				component.setBackground(times % 2 == 0 ? blinkColor
						: oldColor);
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

}
