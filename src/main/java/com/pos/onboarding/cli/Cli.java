package com.pos.onboarding.cli;

import java.util.ArrayList;
import java.util.List;

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

import com.pos.onboarding.bean.Category;
import com.pos.onboarding.persistence.CategoryManager;
import com.pos.onboarding.persistence.impl.csv.CSVCategoryManager;
import com.pos.onboarding.persistence.impl.postgres.dao.CategoryDAO;

public class Cli {
	private static final Logger log = LogManager.getLogger(Cli.class);

	private CategoryManager categoryManager = new CSVCategoryManager();
//	private CategoryManager categoryManager = new CategoryDAO();
	
	private String[] args = null;
	private Options options = new Options();

	private final static String OPTION_HELP = "h";
	private static final String OPTION_CATEGORY_SHOW = "cs";
	private static final String OPTION_CATEGORY_GET = "cg";
	private static final String OPTION_ARTICLE_SHOW = "as";
	private static final String OPTION_ARTICLE_GET = "ag";

	private final static String OPTION_HELP_LONG = "help";
	private static final String OPTION_CATEGORY_SHOW_LONG = "category-show";
	private static final String OPTION_CATEGORY_GET_LONG = "category-get";
	private static final String OPTION_ARTICLE_SHOW_LONG = "article-show";
	private static final String OPTION_ARTICLE_GET_LONG = "article-get";

	@SuppressWarnings("static-access")
	public Cli(String[] args) {
		log.trace("Enter Cli constructor. method params: {}", (Object[]) args);

		this.args = args;

		options.addOption(OPTION_HELP, OPTION_HELP_LONG, false, "Print this help screen.");
		
		OptionGroup categoryOptionGroup = new OptionGroup();
		categoryOptionGroup.addOption(OptionBuilder.withLongOpt(OPTION_CATEGORY_SHOW_LONG).hasArg(false).withDescription("Show all the categories.").create(OPTION_CATEGORY_SHOW));
		categoryOptionGroup.addOption(OptionBuilder.withLongOpt(OPTION_CATEGORY_GET_LONG).hasArg(false).withDescription("Show a category.").create(OPTION_CATEGORY_GET));
		categoryOptionGroup.setRequired(false);
		options.addOptionGroup(categoryOptionGroup);

		OptionGroup articleOptionGroup = new OptionGroup();
		articleOptionGroup.addOption(OptionBuilder.withLongOpt(OPTION_ARTICLE_SHOW_LONG).hasArg(false).withDescription("Show all the articles.").create(OPTION_ARTICLE_SHOW));
		articleOptionGroup.addOption(OptionBuilder.withLongOpt(OPTION_ARTICLE_GET_LONG).hasArg(false).withDescription("Show an article.").create(OPTION_ARTICLE_GET));
		articleOptionGroup.setRequired(false);
		options.addOptionGroup(articleOptionGroup);
	}

	public void parse() {
		log.trace("Enter parse method");
		CommandLineParser parser = new GnuParser();

		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);

			if (cmd.getOptions().length == 0) {
				help();
			} else {
				if (cmd.hasOption(OPTION_HELP)) {
					help();
				}
				
				if (cmd.hasOption(OPTION_CATEGORY_GET)) {
					showCategoryById(1);
				}
				
				if (cmd.hasOption(OPTION_CATEGORY_SHOW)) {
					showCategories(1, 100);
				}

				if (cmd.hasOption(OPTION_ARTICLE_GET)) {
					showArticleById(1);
				}
				
				if (cmd.hasOption(OPTION_ARTICLE_SHOW)) {
					showArticles(1, 100);
				}
			}
		} catch (ParseException e) {
			log.log(Level.ERROR, "Failed to parse comand line properties", e);
			help();
		}
		log.trace("Return method parse.");
	}

	private void help() {
		log.trace("Enter method help.");
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();

		formater.printHelp("pos", options, true);
		System.exit(0);
		log.trace("Return method help.");
	}
	
	private void showCategoryById(int categoryId) {
		log.trace("Enter method showCategoryById.");
		System.out.println("Sorry, feature not implemented yet !");
		System.exit(0);
		log.trace("Return method showCategoryById.");
	}
	
	private void showCategories(int pageNumber, int pageSize) {
		log.trace("Enter method showCategories.");
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
		log.trace("Return method showCategories.");
	}
	
	private void showArticleById(int articleId) {
		log.trace("Enter method showArticleById.");
		System.out.println("Sorry, feature not implemented yet !");
		System.exit(0);
		log.trace("Return method showArticleById.");
	}
	
	private void showArticles(int pageNumber, int pageSize) {
		log.trace("Enter method showArticles.");
		System.out.println("Sorry, feature not implemented yet !");
		System.exit(0);
		log.trace("Enter method showArticles.");
	}
}
