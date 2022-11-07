package org.sid.billingservice.web;

import org.sid.billingservice.entities.bill;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.Billrepository;
import org.sid.billingservice.repository.Productitemrepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        bill bill=billrepository.findById(id).get();
        Customer customer=customerRestClient.getCustomerbyId(bill.getCustomerid());
        bill.setCustomer(customer);
        bill.getProductitems().forEach(pi->{
            Product product=productItemRestClient.getProductById(pi.getProductid());
            //pi.setProduct(product);
            pi.setProductName(product.getName());
        });
        return bill;
    }


}
