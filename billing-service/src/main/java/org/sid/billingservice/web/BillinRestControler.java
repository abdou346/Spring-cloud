package org.sid.billingservice.web;

import org.sid.billingservice.entities.bill;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.Billrepository;
import org.sid.billingservice.repository.Productitemrepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
public class BillinRestControler {
    private Billrepository billrepository;
    private Productitemrepository productitemrepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;

    public BillinRestControler(Billrepository billrepository, Productitemrepository productitemrepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient) {
        this.billrepository = billrepository;
        this.productitemrepository = productitemrepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }

    @GetMapping(path = "/fullBill/{id}")
    public bill getbill (@PathVariable(name = "id") Long id){
        bill bill = billrepository.findById(id).get();
        bill.getProductItems().forEach(productItem -> {
            Product product = productItemRestClient.getProductById(productItem.getProductid());
            //productItem.setProduct(product);
            productItem.setProductName(product.getName());
        });
        return bill;
    }
    @GetMapping(path = "/fullBills")
    public List<bill> getBills(){
        List<bill> bills = billrepository.findAll();
        bills.forEach((bill -> {
            bill.getProductItems().forEach(productItem -> {
                Product product = productItemRestClient.getProductById(productItem.getProductid());
                productItem.setProductName(product.getName());
            });
        }));
        return bills;
    }


}
