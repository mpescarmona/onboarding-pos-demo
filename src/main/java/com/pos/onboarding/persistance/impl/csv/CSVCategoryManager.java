package com.pos.onboarding.persistance.impl.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.beans.Category;
import com.pos.onboarding.bl.ArticleBl;
import com.pos.onboarding.persistance.CategoryManager;
import com.pos.onboarding.util.Constants;

public class CSVCategoryManager implements CategoryManager {
	private static final Logger log = LogManager.getLogger(ArticleBl.class);

	Set<Category> categories = new HashSet<Category>();

	@Override
	public Category getCategoryById(Long categoryId) {
		log.trace("Enter method getCategoryById. Method params: {}", categoryId);

		Category result = null;
		if (categories.isEmpty()) {
			readCategoriesFromCsvFile();
		}

		for (Category category : categories) {
			if (category.getId() == categoryId) {
				result = category;
				break;
			}
		}

		log.trace(
				"Return method getCategoryById. Method params: {}. Result: {}",
				categoryId, result);

		return result;
	}

	@Override
	public Category createCategory(Category category) {
		log.trace("Enter method createCategory. Method params: {}", category);

		if (categories.isEmpty()) {
			readCategoriesFromCsvFile();
		}
		Category result = getCategoryById(category.getId());
		if (result != null) {
			log.error("The provided category already exists");
		} else {
			categories.remove(result);
			categories.add(category);
			writeCategoriesToCsvFile();
		}

		log.trace("Return method createCategory. Method params: {}.", category);
		return category;
	}

	@Override
	public boolean updateCategory(Category category) {
		log.trace("Enter method createCategory. Method params: {}", category);

		boolean result = false;
		if (categories.isEmpty()) {
			readCategoriesFromCsvFile();
		}
		Category existingCategory = getCategoryById(category.getId());
		if (existingCategory == null) {
			log.debug("The provided category does not exists. Adding to the Category list.");
			categories.add(category);
			writeCategoriesToCsvFile();
		} else {
			log.debug("The provided category exists. Updating the changes to the Category list.");
			categories.remove(existingCategory);
			categories.add(category);
			writeCategoriesToCsvFile();
			result = true;
		}

		log.trace(
				"Return method updateCategory. Method params: {}. Result: {}",
				category, result);
		return result;
	}

	@Override
	public boolean removeCategory(Long categoryId) {
		log.trace("Enter method removeCategory. Method params: {}", categoryId);

		boolean result = false;
		if (categories.isEmpty()) {
			readCategoriesFromCsvFile();
		}
		Category existingCategory = getCategoryById(categoryId);
		if (existingCategory == null) {
			log.error(
					"The provided category does not exists. Method prams: {}. Result: {}",
					categoryId, result);
		} else {
			categories.remove(existingCategory);
			writeCategoriesToCsvFile();
			result = true;
			log.debug(
					"The provided category was successfully removed. Method prams: {}. Result: {}",
					categoryId, result);
		}

		log.trace(
				"Return method removeCategory. Method params: {}. Result: {}",
				categoryId, result);
		return result;
	}

	@Override
	public List<Category> getAllCategories() {
		log.trace("Enter method getAll.");

		List<Category> result = new ArrayList<Category>();
		if (categories.isEmpty()) {
			readCategoriesFromCsvFile();
		}

		for (Category category : categories) {
			result.add(category);
		}

		log.trace("Return method getAll. Result: {}", result);

		return result;
	}

	private void readCategoriesFromCsvFile() {
		log.trace("Enter method readCategoriesFromCsvFile.");

		Reader reader = null;
		CSVParser parser = null;

		try {
			reader = new FileReader(Constants.CATEGORY_CSV_STORE);
			parser = new CSVParser(reader,
					CSVFormat.EXCEL
							.withHeader(Constants.CATEGORY_FILE_HEADER_MAPPING));
			List<CSVRecord> records = parser.getRecords();
			for (int i = 1; i < records.size(); i++) {
				CSVRecord record = records.get(i);
				Long id = Long.parseLong(record
						.get(Constants.CATEGORY_COLUMN_ID));
				String categoryName = record
						.get(Constants.CATEGORY_COLUMN_CATEGORY_NAME);
				Category category = new Category();
				category.setId(id);
				category.setCategoryName(categoryName);
				categories.add(category);
			}
		} catch (FileNotFoundException e) {
			log.error("The Category CSV file does not exists.");
		} catch (IOException e) {
			log.error(
					"An error occurred when tried to retrieve categories from {}. The error was: {}",
					Constants.CATEGORY_CSV_STORE, e);
		} finally {
			try {
				parser.close();
				reader.close();
			} catch (IOException e) {
				log.error(
						"Error while closing fileReader/csvFileParser !!!. The error was: {}",
						e);
			}

		}

		log.trace("Return method readCategoriesFromCsvFile");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void writeCategoriesToCsvFile() {
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;

		try {

			// initialize FileWriter object
			fileWriter = new FileWriter(Constants.CATEGORY_CSV_STORE, false);

			// initialize CSVPrinter object
			csvFilePrinter = new CSVPrinter(fileWriter,
					CSVFormat.EXCEL
							.withHeader(Constants.CATEGORY_FILE_HEADER_MAPPING));

			// Write a new Category object list to the CSV file
			for (Category category : categories) {
				List categoryDataRecord = new ArrayList();
				categoryDataRecord.add(String.valueOf(category.getId()));
				categoryDataRecord.add(category.getCategoryName());
				csvFilePrinter.printRecord(categoryDataRecord);
			}

			log.debug("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out
						.println("Error while flushing/closing fileWriter/csvPrinter !!!");
				e.printStackTrace();
			}
		}
	}

}
