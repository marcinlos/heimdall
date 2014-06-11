package pl.edu.agh.heimdall.statistics;

import com.google.common.base.Preconditions;

public class MethodInvocation {
	private String fullyQualifiedName;
	private int invocationsNumber;
	
	public static MethodInvocation create(
			String fullyQualifiedName, int invocationsNumber) {
		return new MethodInvocation(fullyQualifiedName, invocationsNumber);
	}
	
	private MethodInvocation(String fullyQualifiedName, int invocationsNumber) {
		this.fullyQualifiedName = Preconditions.checkNotNull(fullyQualifiedName);
		this.invocationsNumber = Preconditions.checkNotNull(invocationsNumber);
	}

	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}

	public int getInvocationsNumber() {
		return invocationsNumber;
	}
}
