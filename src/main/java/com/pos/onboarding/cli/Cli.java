package com.pos.onboarding.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cli {
	private static final Logger log = LogManager.getLogger(Cli.class);

	private String[] args = null;
	private Options options = new Options();

	private final static String OPTION_HELP = "h";
	private final static String OPTION_VERSION = "v";
	private static final String OPTION_ARTICLE = "a";
	private static final String OPTION_CATEGORY = "c";
	private static final String OPTION_PRODUCT = "p";
	private static final String OPTION_PRODUCT_SHOW = "ps";
	private static final String OPTION_PRODUCT_GET = "pg";
	private static final String OPTION_PRODUCT_UPDATE = "pu";

	private final static String OPTION_HELP_LONG = "help";
	private final static String OPTION_VERSION_LONG = "version";
	private static final String OPTION_ARTICLE_LONG = "article";
	private static final String OPTION_CATEGORY_LONG = "category";
	private static final String OPTION_PRODUCT_LONG = "product";
	private static final String OPTION_PRODUCT_SHOW_LONG = "product-show";
	private static final String OPTION_PRODUCT_GET_LONG = "product-get";
	private static final String OPTION_PRODUCT_UPDATE_LONG = "product-update";

	@SuppressWarnings("static-access")
	public Cli(String[] args) {
		log.trace("Enter Cli constructor. method params: {}", (Object[]) args);

		this.args = args;

		options.addOption(OPTION_HELP, OPTION_HELP_LONG, false, "Print this help screen.");
		options.addOption(OPTION_VERSION, OPTION_VERSION_LONG, false, "Print the version information and exit.");
		options.addOption(OPTION_ARTICLE, OPTION_ARTICLE_LONG, false, "Actions with Articles.");
		options.addOption(OPTION_CATEGORY, OPTION_CATEGORY_LONG, false, "Actions with Categories.");
		options.addOption(OPTION_PRODUCT, OPTION_PRODUCT_LONG, false, "Actions with Products.");
		OptionGroup optionGroup = new OptionGroup();

		optionGroup.addOption(OptionBuilder.withLongOpt(OPTION_PRODUCT_SHOW_LONG).hasArg(false).withDescription("Show all the products.").create(OPTION_PRODUCT_SHOW));
		optionGroup.addOption(OptionBuilder.withLongOpt(OPTION_PRODUCT_GET_LONG).hasArg(false).withDescription("Show all the products.").create(OPTION_PRODUCT_GET));
		optionGroup.addOption(OptionBuilder.withLongOpt(OPTION_PRODUCT_UPDATE_LONG).hasArg(false).withDescription("Update the product.").create(OPTION_PRODUCT_UPDATE));
		optionGroup.setRequired(false);

		options.addOptionGroup(optionGroup);
	}

	public void parse() {
		CommandLineParser parser = new GnuParser();

		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);

			if (cmd.hasOption(OPTION_HELP)) {
				help();
			}
			
			if (cmd.hasOption(OPTION_VERSION)) {
				version();
			}

//			 if (cmd.hasOption("v")) {
//			 log.log(Level.INFO,
//			 "Using cli argument -v=" + cmd.getOptionValue("v"));
//			 // Whatever you want to do with the setting goes here
//			 } else {
//			 log.log(Level.ERROR, "MIssing v option");
//			 help();
//			 }

		} catch (ParseException e) {
			log.log(Level.ERROR, "Failed to parse comand line properties", e);
			help();
		}
	}

	private void help() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();

		formater.printHelp("pos", options, true);
		System.exit(0);
	}
	
	private void version() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();

		formater.printHelp(OPTION_VERSION, options, true);
		System.exit(0);
	}
}
