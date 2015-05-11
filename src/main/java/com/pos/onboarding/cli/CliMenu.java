package com.pos.onboarding.cli;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Mario Pescarmona
 *
 */
public class CliMenu {
	private static final Logger log = LogManager.getLogger(CliMenu.class);

	public static void main(String[] args) {
		log.trace("Enter CliMenu constructor. method params: {}", (Object[]) args);

		new Cli(args).parse();
	}
}
