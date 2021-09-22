package by.epamtc.kalimylin.controller.util.helper;

import by.epamtc.kalimylin.controller.util.Message;

/**
 * Class-helper for processing boolean condition
 */
public class ConditionHelper {
	
	private ConditionHelper() {
	}
	
	/**
	 * check condition and return new object of {@link StringBuilder}
	 * with append message, which depends on the condition
	 * 
	 * @param condition  value for processing
	 * @return {@link StringBuilder} with append message 
	 */
	public static StringBuilder updateLine(boolean condition) {
		
		StringBuilder actionStatus = new StringBuilder();
		
		if (condition) {
			actionStatus.append(Message.SUCCESSFUL);
		} else {
			actionStatus.append(Message.FAILED);
		}
		return actionStatus;
	}
}
