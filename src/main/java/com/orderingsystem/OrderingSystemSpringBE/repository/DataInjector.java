package com.orderingsystem.OrderingSystemSpringBE.repository;

import com.orderingsystem.OrderingSystemSpringBE.entity.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

// == Initial  DATA LOADER TO DB from Excel files ==

//@Component
public class DataInjector implements CommandLineRunner {
	
	public Logger log = LoggerFactory.getLogger(this.getClass());
	
	private CategoryRepository categoryRepository;
	private ProductRepository productRepository;
	private CustomerRepository customerRepository;
	private OrderRepository orderRepository;
	private OrderItemRepository orderItemRepository;

	@Autowired
	public DataInjector(
			CategoryRepository categoryRepository,
			ProductRepository productRepository,
			CustomerRepository customerRepository,
			OrderRepository orderRepository,
			OrderItemRepository orderItemRepository
	) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.customerRepository = customerRepository;
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
	}


	@Override
	public void run(String... args) throws Exception {
		log.info("CommandLineRunner -- Data Injector starts >>");

		log.info("CommandLineRunner -- Categories... >>");

		if (categoryRepository.findAll().isEmpty()) {
			String fileLocation = "categories.xlsx";

			Workbook workbook = new XSSFWorkbook(new FileInputStream(fileLocation));
			Sheet sheet = workbook.getSheet("categories");

			for (Row row : sheet) {
				log.info(">> data row of " + row.getCell(0).getStringCellValue());
				if (row.getRowNum() != 0) {
					categoryRepository.save(
							new Category(
									row.getCell(0).getStringCellValue()
							)
					);
				}
			}
		}

		log.info("CommandLineRunner -- Products... >>");

		if (productRepository.findAll().isEmpty()) {
			String fileLocation = "products.xlsx";

			Workbook workbook = new XSSFWorkbook(new FileInputStream(fileLocation));
			Sheet sheet = workbook.getSheet("products");

			for (Row row : sheet) {
				log.info(">> data row of " + row.getCell(0).getStringCellValue());
				if (row.getRowNum() != 0) {
					Long id = Double.valueOf( row.getCell(3).getNumericCellValue() ).longValue();
					try {
						productRepository.save(
							new Product(
									row.getCell(0).getStringCellValue(),
									row.getCell(1).getStringCellValue(),
									row.getCell(2).getNumericCellValue(),
									categoryRepository.findById( id ).orElseThrow()
									));
					} catch (Exception e) {
						log.error("Inserting row " + row.getRowNum() + " into DB failed");
					}
				}
			}
		}

		log.info("CommandLineRunner -- Customers... >>");

		if (customerRepository.findAll().isEmpty()) {
			String fileLocation = "customers.xlsx";

			Workbook workbook = new XSSFWorkbook(new FileInputStream(fileLocation));
			Sheet sheet = workbook.getSheet("customers");

			for (Row row : sheet) {
				log.info(">> data row of " + row.getCell(0).getStringCellValue());
				if (row.getRowNum() != 0) {
					customerRepository.save(
							new Customer(
									row.getCell(0).getStringCellValue(),
									row.getCell(1).getStringCellValue(),
									row.getCell(2).getStringCellValue()
							)
					);
				}
			}
		}

//		log.info("CommandLineRunner -- Orders... >>");
//
//		if (orderRepository.findAll().isEmpty()) {
//			String fileLocation = "orders.xlsx";
//
//			Workbook workbook = new XSSFWorkbook(new FileInputStream(fileLocation));
//			Sheet sheet = workbook.getSheet("orders");
//
//			for (Row row : sheet) {
////				log.info(">> data row of " + row.getCell(0).getStringCellValue());
//				if (row.getRowNum() != 0) {
//					Long id = Double.valueOf( row.getCell(1).getNumericCellValue() ).longValue();
//					log.info(">> id: " + id.toString());
//					try {
//						orderRepository.save(
//								new Order(
//										row.getCell(0).getDateCellValue(),
//										customerRepository.findById( id ).orElseThrow()
//								));
//					} catch (Exception e) {
//						log.error("Inserting row " + row.getRowNum() + " into DB failed");
//					}
//				}
//			}
//		}

		log.info("CommandLineRunner -- Order items... >>");

		if (orderItemRepository.findAll().isEmpty()) {
			String fileLocation = "order-items.xlsx";

			Workbook workbook = new XSSFWorkbook(new FileInputStream(fileLocation));
			Sheet sheet = workbook.getSheet("order-items");

			for (Row row : sheet) {
//				log.info(">> data row of " + row.getCell(0).getStringCellValue());
				if (row.getRowNum() != 0) {
					Long order_id = Double.valueOf( row.getCell(0).getNumericCellValue() ).longValue();
					Long prod_id = Double.valueOf( row.getCell(1).getNumericCellValue() ).longValue();
					log.info(">> id: " + order_id);
					try {
						orderItemRepository.save(
								new OrderItem(
										orderRepository.findById( order_id ).orElseThrow(),
										productRepository.findById( prod_id ).orElseThrow(),
										row.getCell(2).getNumericCellValue()
								));
					} catch (Exception e) {
						log.error("Inserting row " + row.getRowNum() + " into DB failed");
					}
				}
			}
		}
		
		log.info("CommandLineRunner -- Data Injector ends <<");


//		Only for test if necessary:  The mere printing of Excel data - as a TEST that the structure is working well (i.g. types)

//		for (Row row : sheet) {
//			if (row.getRowNum() == 0) {
//				log.info("-- Header --");
//				log.info(row.toString());
//			} else {
//				for (Cell cell : row) {
//					if (cell.getCellType() == CellType.STRING)
//						log.info(String.valueOf(cell.getColumnIndex())+" - "+cell.getStringCellValue());
//					if (cell.getCellType() == CellType.NUMERIC)
//						log.info(String.valueOf(cell.getColumnIndex())+" - "+String.valueOf((int) cell.getNumericCellValue()));
//				}
//			}
//		}

	}

}
