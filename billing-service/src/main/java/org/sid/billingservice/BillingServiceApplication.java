package org.sid.billingservice;

import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.entities.bill;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.Billrepository;
import org.sid.billingservice.repository.Productitemrepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(BillingServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start (Billrepository billrepository,
							 Productitemrepository productitemrepository,
							 CustomerRestClient customerRestClient,
							 ProductItemRestClient productItemRestClient){
		return args ->{
			Customer customer=customerRestClient.getCustomerbyId(1L);
			bill bill = billrepository.save(new bill(null, new Date(),null,customer.getId()));

			PagedModel<Product> productPagedModel=productItemRestClient.pageProducts();
			productPagedModel.forEach(p->{
				ProductItem productitem=new ProductItem();
				productitem.setPrice(p.getPrice());
				productitem.setQuantity(1+new Random().nextInt(100));
				productitem.setBill(bill);
				productitem.setProductid(p.getId());
				productitemrepository.save(productitem);
			});

		};
	}

}
