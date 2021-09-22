package by.epamtc.kalimylin.controller.util.helper;

import static by.epamtc.kalimylin.controller.util.AttributeAndParameter.*;

import java.util.Stack;

import by.epamtc.kalimylin.controller.util.PagePath;

/**
 * Class for processing and control request log condition and entries.
 */
public class ControllerHelper {
	
	private ControllerHelper() {}
	
	/**
	 * Check request log condition, create new {@link Stack} if current = null
	 * Add default request to log, if log is empty.
	 * Default request is command go to welcome page. 
	 * 
	 * @param requestLog  current request log
	 * @return {@link Stack} with request log
	 */
	public static Stack<String> checkLog(Stack<String> requestLog) {
		
		if ((requestLog == null) || (requestLog.size() == 0)) {
			requestLog = new Stack<>();
			requestLog.push(PagePath.GO_TO_WELCOME_USER);
		}		
		return requestLog;		
	}
	
	/**
	 * Clears request line from value message, before transferring the request
	 * line to log.
	 * 
	 * @param reqLine  current line to clears
	 * @return {@link String} clears request line
	 */
	public static  String clearRequestLine(String reqLine) {
		
		if (reqLine.contains(PARAM_MESSAGE)) {
			return reqLine.substring(0, reqLine.indexOf(PARAM_MESSAGE));
		}
		return reqLine;	
	}
}
