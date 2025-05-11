package edu.quangtk.services;

     import edu.quangtk.entity.Category;
     import edu.quangtk.repositories.CategoryRepository;
     import org.springframework.beans.factory.annotation.Autowired;
     import org.springframework.stereotype.Service;

     @Service
     public class DatabaseCheckService {
         @Autowired
         private CategoryRepository categoryRepository;

         public String checkDatabaseConnection() {
             try {
                 categoryRepository.count(); // Thực hiện một truy vấn đơn giản
                 return "Database connection is successful!";
             } catch (Exception e) {
                 return "Database connection failed: " + e.getMessage();
             }
         }
     }