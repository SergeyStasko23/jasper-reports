package ru.stacy.services;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stacy.entities.Product;
import ru.stacy.repositories.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductService  {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public String generateReport() {
        try {
            List<Product> products = findAll();

            String reportPath = "C:\\Users\\heath\\IdeaProjects\\jasper-reports\\src\\main\\resources\\reports";

            // Compile the Jasper report from .jrxml to .jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath + "\\products.jrxml");

            // Get your data source
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(products);

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("createdBy", "stacy.cmd");

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);

            // Export the report to a PDF file
            JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath + "\\products.pdf");

            System.out.println("Done");
            return "Report successfully generated @path= " + reportPath;
        } catch(JRException e) {
            e.printStackTrace();
            return "Error --> check the console log";
        }
    }
}
