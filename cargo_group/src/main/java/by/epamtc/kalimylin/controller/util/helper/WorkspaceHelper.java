package by.epamtc.kalimylin.controller.util.helper;

import static by.epamtc.kalimylin.bean.executor.user.Role.*;
import static by.epamtc.kalimylin.controller.util.PagePath.*;

import java.util.HashMap;

import by.epamtc.kalimylin.bean.executor.user.Role;

/**
 * Class for control workspaces. Use hashMap for storage available pairs
 * (Role,reference to workspace). Values initialize in static block.
 */
public final class WorkspaceHelper {
	
	private static final HashMap<Role, String> workSpaces = 
			new HashMap<Role, String>();
	
	static {
		workSpaces.put(ADMIN, WORK_PAGE_ADMIN);
		workSpaces.put(LOGIST, WORK_PAGE_LOGIST);
		workSpaces.put(DRIVER, WORK_PAGE_DRIVER);
		workSpaces.put(MECHANIC, WORK_PAGE_MECHANIC);
		workSpaces.put(GUEST, WORK_PAGE_GUEST);
	}

	private WorkspaceHelper() {
	}
	
	/**
	 * Take role, return page path to workspace for current role
	 * 
	 * @param role  role, for which the reference is returned
	 * @return {@link String} with reference to working space 
	 * corresponding to a given role
	 */
	public static final String getWorkSpace(Role role) {

		return workSpaces.get(role);
	}
}
