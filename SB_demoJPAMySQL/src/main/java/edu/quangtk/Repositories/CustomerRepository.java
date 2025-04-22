package edu.quangtk.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.quangtk.Models.Customer;
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
