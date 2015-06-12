package com.pos.onboarding.cli;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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

import com.pos.onboarding.beans.Category;
import com.pos.onboarding.persistance.CategoryManager;
import com.pos.onboarding.persistance.impl.csv.CSVCategoryManager;

public class Cli {
	private static final Logger log = LogManager.getLogger(Cli.class);

	private CategoryManager categoryManager = new CSVCategoryManager();
	
	private String[] args = null;
	private Options options = new Options();

	private final static String OPTION_HELP = "h";
	private final static String OPTION_VERSION = "v";
	private static final String OPTION_ARTICLE = "a";
	private static final String OPTION_CATEGORY = "c";
	private static final String OPTION_CATEGORY_SHOW = "cs";
	private static final String OPTION_CATEGORY_GET = "cg";
	private static final String OPTION_CATEGORY_UPDATE = "cu";

	private final static String OPTION_HELP_LONG = "help";
	private final static String OPTION_VERSION_LONG = "version";
	private static final String OPTION_ARTICLE_LONG = "article";
	private static final String OPTION_CATEGORY_LONG = "category";
	private static final String OPTION_CATEGORY_SHOW_LONG = "category-show";
	private static final String OPTION_CATEGORY_GET_LONG = "category-get";
	private static final String OPTION_CATEGORY_UPDATE_LONG = "category-update";

	@SuppressWarnings("static-access")
	public Cli(String[] args) {
		log.trace("Enter Cli constructor. method params: {}", (Object[]) args);

		this.args = args;

		options.addOption(OPTION_HELP, OPTION_HELP_LONG, false, "Print this help screen.");
		options.addOption(OPTION_VERSION, OPTION_VERSION_LONG, false, "Print the version information and exit.");
		options.addOption(OPTION_ARTICLE, OPTION_ARTICLE_LONG, false, "Actions with Articles.");
		options.addOption(OPTION_CATEGORY, OPTION_CATEGORY_LONG, false, "Actions with Categories.");
//		options.addOption(OPTION_PRODUCT, OPTION_PRODUCT_LONG, false, "Actions with Products.");
		
		OptionGroup categoryOptionGroup = new OptionGroup();
		categoryOptionGroup.addOption(OptionBuilder.withLongOpt(OPTION_CATEGORY_SHOW_LONG).hasArg(false).withDescription("Show all the categories.").create(OPTION_CATEGORY_SHOW));
		categoryOptionGroup.addOption(OptionBuilder.withLongOpt(OPTION_CATEGORY_GET_LONG).hasArg(false).withDescription("Show all the categories.").create(OPTION_CATEGORY_GET));
		categoryOptionGroup.addOption(OptionBuilder.withLongOpt(OPTION_CATEGORY_UPDATE_LONG).hasArg(false).withDescription("Update the categories.").create(OPTION_CATEGORY_UPDATE));
		categoryOptionGroup.setRequired(false);
		options.addOptionGroup(categoryOptionGroup);
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

			if (cmd.hasOption(OPTION_CATEGORY_SHOW)) {
				showCategories(1, 100);
			}
			
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
	
	private void showCategories(int pageNumber, int pageSize) {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();
		
		// Call service here
		List<Category> result = new ArrayList<Category>();
		

		result = categoryManager.getAllCategories(pageNumber, pageSize);

		System.out.println("+-----+--------------------------------------------------+");
		System.out.println("| Id  | Category Name                                    |");
		System.out.println("+-----+--------------------------------------------------+");
		for (Category category : result) {
			System.out.println(String.format("| %3d | %-48s |", category.getId(), category.getCategoryName()));
		}
		System.out.println("+-----+--------------------------------------------------+");

		System.exit(0);
	}
}
