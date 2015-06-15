package com.pos.onboarding.persistance.impl.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.onboarding.beans.Category;
import com.pos.onboarding.persistance.CategoryManager;
import com.pos.onboarding.util.Constants;

@Service("categoryServiceCSV")
@Transactional
public class CSVCategoryManager implements CategoryManager {
	
	
	private static final Logger log = LogManager
			.getLogger(CSVCategoryManager.class);

	Set<Category> categories = new TreeSet<Category>(new Comparator<Category>(){
        @Override
        public int compare(Category c1, Category c2) {
        	if(c1 == null) {
        		return c2 == null ? 0 : 1;
        	} else if(c2 == null) {
        		return -1;
        	} else {
            	if(c1.getId() == null) {
            		return c2.getId() == null ? 0 : 1;
            	} else if(c2.getId() == null) {
            		return -1;
            	} else {
            		if (c1.getId().longValue() < c2.getId().longValue()) return -1; 
            		if (c1.getId().longValue() > c2.getId().longValue()) return 1; 
            		return 0;
            	}
        	}
        }
});

	@Override
	public Category getCategoryById(Long categoryId) {
		log.trace("Enter method getCategoryById. Method params: {}", categoryId);

		Category result = null;
		if (categories.isEmpty()) {
			readCategoriesFromCsvFile();
		}

		for (Category category : categories) {
			if (category.getId().equals(categoryId)) {
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
		Long nextId = getNextId();
		nextId = nextId != null ? nextId : 1l;
		if (category.getId() == null) {
			category.setId(nextId);
		}

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
		log.trace("Enter method updateCategory. Method params: {}", category);

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
			categories.removeAll(categories);
			readCategoriesFromCsvFile();
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
	public List<Category> getAllCategories(int pageNumber, int pageSize) {
		log.trace("Enter method getAllCategories.");

		List<Category> result = new ArrayList<Category>();
		if (categories.isEmpty()) {
			readCategoriesFromCsvFile();
		}
		
		int fromItem = ((pageNumber <= 0 ? 1 : pageNumber) - 1) * pageSize;
		int toItem = fromItem + pageSize;
		
		int i = 0;
		for (Category category : categories) {
			if (i >= fromItem && i < toItem) {
				result.add(category);
			}
			i++;
		}

		log.trace("Return method getAllCategories. Result: {}", result);
		return result;
	}

	@Override
	public Long getCount() {
		log.trace("Enter method getCount.");

		Long result = null;
		if (categories.isEmpty()) {
			readCategoriesFromCsvFile();
		}

		result = (long) categories.size();

		log.trace("Return method getCount. Result: {}", result);
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
			} catch (Exception e) {
				log.error(
						"Error while closing fileReader/csvFileParser !!!. The error was: {}",
						e);
			}
		}
		log.trace("Return method readCategoriesFromCsvFile");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void writeCategoriesToCsvFile() {
		log.trace("Enter method writeCategoriesToCsvFile.");
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
			log.error(
					"There were errors trying to write CSV file. Error details: {}",
					e.getMessage());
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				log.error(
						"There were errors while flushing/closing fileWriter/csvPrinter. Error details: {}",
						e.getMessage());
			} catch (Exception e) {
				log.error(
						"Error while closing fileReader/csvFileParser !!!. The error was: {}",
						e);
			}
			log.trace("Return method writeCategoriesToCsvFile.");
		}
	}
	
	private Long getNextId() {
		log.trace("Enter method getNextId.");

		Long result = 1l;
		for (Category category : categories) {
			result = category.getId() > result ? category.getId() : result; 
		}
		result = result + 1;

		log.trace("Return method getNextId. Result: {}", result);
		return result;
	}

}
