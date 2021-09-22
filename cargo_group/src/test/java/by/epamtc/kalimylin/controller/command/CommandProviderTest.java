package by.epamtc.kalimylin.controller.command;

import org.testng.annotations.Test;

import by.epamtc.kalimylin.controller.command.impl.go_to.GoToWelcomePageCommand;

import static by.epamtc.kalimylin.controller.command.ParameterName.*;
import static org.testng.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

public class CommandProviderTest {
	
	private CommandProvider provider;
	private Map<ParameterName, Command> commands;
	
	@BeforeClass
	public void beforeClass() {	
		provider = new CommandProvider();
		commands = new HashMap<ParameterName, Command>();
		commands.put(WELCOME_USER, new GoToWelcomePageCommand());
	}

	@AfterClass
	public void afterClass() {
		provider = null;
		commands = null;
	}
	
	@Test(dataProvider = "commands")
	public void getCommandTest(String commandName) {
		Command command = provider.getCommand(commandName);
		assertNotNull(command);
	}

	@DataProvider
	public Object[][] commands() {
		return new Object[][] {
				{ "invalid_command" },
				{ "" },
				{ "very long command string 32 sign" },
				{ "welcome_user" }
		};
	}
}
