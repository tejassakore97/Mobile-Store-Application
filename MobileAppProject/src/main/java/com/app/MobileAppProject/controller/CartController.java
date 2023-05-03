package com.app.MobileAppProject.controller;

import com.app.MobileAppProject.golbal.GlobalData;
import com.app.MobileAppProject.invoice.InvoiceCreation;
import com.app.MobileAppProject.invoice.QRCode;
import com.app.MobileAppProject.invoice.custData;
import com.app.MobileAppProject.model.Category;
import com.app.MobileAppProject.model.Customer;
import com.app.MobileAppProject.model.Product;
import com.app.MobileAppProject.service.CustomerService;
import com.app.MobileAppProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Controller
public class CartController {
    public List<Product> products=new LinkedList<>();
    public  List<Customer>customers=new LinkedList<>();
    @Autowired
    ProductService productService;
    @Autowired
    CustomerService customerService;
    @GetMapping("/addToCart/{id}")
    public  String addToCart(@PathVariable int id)
    {
        GlobalData.cart.add(productService.getProductById(id).get());
        Product product= productService.getProductById(id).get();

        System.out.println(product.getName() + product.getStorage());

        products.add(product);
        QRCode qrCode=new QRCode();
        qrCode.generateQrCode(productService.getProductById(id).get().getPrize());
        return "redirect:/shop";
    }
    @GetMapping("/cart")
    public  String cartGet(Model model)
    {
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrize).sum());
        model.addAttribute("cart",GlobalData.cart);
        return "cart";
    }
    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index)
    {
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }
    @GetMapping("/checkout")
    public  String checkout(Model model,Customer customer,Product product) throws IOException {
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrize).sum());
        customers.add(customer);
        System.out.println(customer.toString());
//        System.out.println(customer.getFirstName());
//        System.out.println(customer.getLastName());
//        System.out.println(customer.getAddressLine1());
//        System.out.println(customer.getAddressLine2());
//        System.out.println(customer.getPostCode());
//        System.out.println(customer.getCity());
//        System.out.println(customer.getPhoneNumber());
//        System.out.println(customer.getEmailAddress());
//        System.out.println(customer.getAdditionalInformation());
        product.setId(products.get(0).getId());
        product.setName(products.get(0).getName());
        product.setPrize(products.get(0).getPrize());
        product.setOs(products.get(0).getOs());
        product.setCamera(products.get(0).getCamera());
        product.setProcessor(products.get(0).getProcessor());
        product.setStorage(products.get(0).getStorage());
        product.setRam(products.get(0).getRam());
        product.setDescription(products.get(0).getDescription());
        //System.out.println(product.toString());
        System.out.println("Product name is"+product.getName()+product.getStorage());

        //customer data
        String firstName=customer.getFirstName();
        String lastName=customer.getLastName();
        String addressLine1=customer.getAddressLine1();
        String addressLine2= customer.getAddressLine2();
        int postCode=customer.getPostCode();
        String city=customer.getCity();
        String phoneNumber =customer.getPhoneNumber();
        String emailAddress=customer.getEmailAddress();
        String additionalInformation=customer.getAdditionalInformation();

        //product data
        long id=product.getId();
        String name=product.getName();
        double Prize=product.getPrize();
        String os=product.getOs();
        String camera=product.getCamera();
        String processor=product.getProcessor();
        String storage=product.getStorage();
        String ram=product.getRam();
        String description=product.getDescription();
        String imageName=product.getImageName();

       InvoiceCreation invoiceCreation=new InvoiceCreation();
       invoiceCreation.writeInvoice(firstName,lastName,addressLine1,addressLine2 , postCode,city,phoneNumber , emailAddress,additionalInformation
                ,name,  Prize, os, camera, processor, storage, ram, description, imageName);


        model.addAttribute("firstName",customer.getFirstName());
        model.addAttribute("lastName",customer.getLastName());
        return "checkout";
    }
    @GetMapping("/payNow")
    public  String payment()
    {
        return "payment";
    }

    @GetMapping("/genrateInvoice")
    public  String invoice() throws IOException {
        //customer
        Customer customer=new Customer();
        customer.setId(customers.get(0).getId());
        customer.setFirstName(customers.get(0).getFirstName());
        customer.setLastName(customers.get(0).getLastName());
        customer.setAddressLine1(customers.get(0).getAddressLine1());
        customer.setAddressLine2(customers.get(0).getAddressLine2());
        customer.setPostCode(customers.get(0).getPostCode());
        customer.setCity(customers.get(0).getCity());
        customer.setPhoneNumber(customers.get(0).getPhoneNumber());
        customer.setEmailAddress(customers.get(0).getEmailAddress());
        customer.setAdditionalInformation(customers.get(0).getAdditionalInformation());

        System.out.println("Customer Name is"+customer.getFirstName()+customer.getLastName());



        return "redirect:";
    }
    @GetMapping("/cash")
    public  String cod()
    {
        return "redirect:";
    }

    @GetMapping("/QR-CODE")
    public  String QRCode()
    {
        return "QRCode";
    }

    @GetMapping("/cardsuccess")
    public  String cardsuccess()
    {
        return "redirect:";
    }

    @PostMapping("/customeruser")
    public String customeruser(@ModelAttribute("Customer") Customer customer) {

        System.out.println(customer);

        customerService.customerUser(customer);

        return "successregistration";

    }
    @GetMapping("/SuccessQrPayment")
    public  String sucessQr()
    {
        return "redirect:";
    }


}
