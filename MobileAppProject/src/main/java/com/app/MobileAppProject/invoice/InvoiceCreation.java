package com.app.MobileAppProject.invoice;

import com.app.MobileAppProject.model.Category;
import com.app.MobileAppProject.model.Customer;
import com.app.MobileAppProject.model.Product;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class InvoiceCreation {
   public PDDocument invc;
   public int n;

//    public static void main(String[] args) throws IOException {
//        InvoiceCreation invoiceCreation=new InvoiceCreation();
//        Customer customer=new Customer(1,"Atharva","Gujar","Kothrud","Pune ",411038,"Pune","8805545888","atharvagujar.789@gmail.com","");
//        Product product=new Product(1,"Samsung M30s",999000,"Android","40mp","Snapdragon 888","64gb","4gb","Good");
//        invoiceCreation.writeInvoice(customer,product);
//    }
    public void writeInvoice( String firstName,String lastName,String addressLine1,String addressLine2 ,int postCode,String city, String phoneNumber , String emailAddress, String additionalInformation
            ,String name, double Prize, String os, String camera, String processor, String storage, String ram, String description, String imageName) throws IOException {

        //Create Document
        invc = new PDDocument();
        //Create Blank Page
        PDPage newpage = new PDPage(PDRectangle.A4);
        //Add the blank page
        invc.addPage(newpage);

//        //customer data
//        String firstName=customer.getFirstName();
//        String lastName=customer.getLastName();
//        String addressLine1=customer.getAddressLine1();
//        String addressLine2= customer.getAddressLine2();
//        int postCode=customer.getPostCode();
//        String city=customer.getCity();
//        String phoneNumber =customer.getPhoneNumber();
//        String emailAddress=customer.getEmailAddress();
//        String additionalInformation=customer.getAdditionalInformation();
//
//        //product data
//        long id=product.getId();
//        String name=product.getName();
//        double Prize=product.getPrize();
//        String os=product.getOs();
//        String camera=product.getCamera();
//        String processor=product.getProcessor();
//        String storage=product.getStorage();
//        String ram=product.getRam();
//        String description=product.getDescription();
//        String imageName=product.getImageName();

        //other data
        String paymentMode="Online Card payment/Cash on Delivary /Upi Qr Payment";
        String paymentDone="Succcess";

        //path
        String currentDir = System.getProperty("user.dir");
        String path=(currentDir+"\\src\\main\\resources\\static\\invoiceAndQr\\");
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
        String timeStamp1 = new SimpleDateFormat("ddMMyyyyHHmmss").format(new java.util.Date());

        String barPath=path+"barcode.png";
        String barCodeData =timeStamp;
        Code128Bean code128 = new Code128Bean();
        code128.setHeight(15f);
        code128.setModuleWidth(0.3);
        code128.setQuietZone(10);
        code128.doQuietZone(true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/x-png", 300, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        code128.generateBarcode(canvas, barCodeData);
        canvas.finish();
        //write to png file
        FileOutputStream fos = new FileOutputStream(barPath);
        fos.write(baos.toByteArray());
        fos.flush();
        fos.close();




        //get the page
        PDPage mypage = invc.getPage(0);
        String filePath1=path+"Invoice.pdf";

        // String filePath1="C:\\Users\\Pallavi\\Desktop\\"+"Ruturaj"+".pdf";
        try {
            //Prepare Content Stream
            PDPageContentStream cs = new PDPageContentStream(invc, mypage);

            File font =new File(path+"Rokkitt-Medium.ttf");

            PDFont myfont = PDType0Font.load(invc,font);
            //Writing Single Line text
            //Writing the Invoice title
            //Img inserting


            //barcode in pdf
            PDImageXObject barImg = PDImageXObject.createFromFile(barPath,invc);
            cs.drawImage(barImg,420,775,150,40);

            //frame Image in pdf
            PDImageXObject outerFrame= PDImageXObject.createFromFile(path+"frame.png",invc);
            cs.drawImage(outerFrame,8,80,580,780);

            //for vertical and horizontal line
            File vrlinePath=new File(path+"vrline.png");
            File hrlinePath=new File(path+"hrline.png");
            PDImageXObject vrLine=PDImageXObject.createFromFile(String.valueOf(vrlinePath),invc);
            cs.drawImage(vrLine,260,603,55,230);

            PDImageXObject hrLine=PDImageXObject.createFromFile(String.valueOf(hrlinePath),invc);
            cs.drawImage(hrLine,-145,595,815,20);

            //stamp image
            PDImageXObject stamp=PDImageXObject.createFromFile(path+"stamp.png",invc);

            cs.drawImage(stamp,430,(190 - (20 * n)),120,50);


            cs.beginText();
            cs.setFont(myfont, 7);
            cs.newLineAtOffset(450, 820);
            cs.setNonStrokingColor(Color.black);
            cs.showText("(ORIGINAL FOR RECIPIENT)");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.newLineAtOffset(270, 810);
            cs.setNonStrokingColor(Color.black);
            cs.showText("INVOICE");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 25);
            cs.setNonStrokingColor(Color.red);
            cs.newLineAtOffset(50, 780);
            cs.showText("Prime Mobile Shop");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 10);
            cs.setLeading(10f);
            cs.newLineAtOffset(50, 760);
            cs.setNonStrokingColor(Color.black);
            cs.showText("M.I.D.C BLOCK,'D' PLOT NO.P-90,CHINCHWAD,");
            cs.newLine();
            cs.showText("PUNE-411038.");
            cs.newLine();
            cs.showText("Contact No.: 8805588888");
            cs.newLine();
            cs.showText("GSTIN/UIN: 27AAKFR1522C1ZR");
            cs.newLine();
            cs.showText("Mail: info@primemobile.in");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 14);
            cs.newLineAtOffset(30, 710);
            cs.setNonStrokingColor(Color.black);
            cs.showText("--------------------------------------------------");
            cs.endText();


            cs.beginText();
            cs.setFont(myfont, 13);
            cs.newLineAtOffset(40, 700);
            cs.setNonStrokingColor(Color.black);
            cs.showText("Bill To,");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.setLeading(10f);
            cs.setNonStrokingColor(Color.black);
            cs.newLineAtOffset(50, 685);
            cs.showText("Name: " + firstName+" " + lastName);
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Phone No: " + phoneNumber);
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Address:"+ addressLine1+" "+addressLine2+""+city+" "+postCode);
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Mail:"+emailAddress);
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.newLineAtOffset(300, 775);
            cs.setNonStrokingColor(Color.black);
            cs.showText("Date: "+timeStamp.substring(0,10));
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 11);
            cs.setLeading(10f);
            cs.newLineAtOffset(300, 760);
            cs.setNonStrokingColor(Color.black);
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Invoice No.: "+timeStamp1);
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Payment Mode: UPI/Card/Cash");
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Ref.No: 300823722276");
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Note: ");
            cs.endText();

            PDImageXObject hrLine1=PDImageXObject.createFromFile(String.valueOf(hrlinePath),invc);
            cs.drawImage(hrLine1,-145,580,815,20);

            cs.beginText();
            cs.setFont(myfont, 14);
            cs.newLineAtOffset(30, 580);
            cs.setNonStrokingColor(Color.black);
            cs.showText("Sr.");
            cs.newLine();
            cs.showText("No.");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 13);
            cs.setLeading(20f);
            cs.newLineAtOffset(35, 540);
            cs.showText(String.valueOf(1));
            cs.newLine();
            cs.endText();

            PDImageXObject vrline1=PDImageXObject.createFromFile(String.valueOf(vrlinePath),invc);
            cs.drawImage(vrline1,40,597,30,(-233+ (-20 * n)));


            cs.beginText();
            cs.setFont(myfont, 14);
            cs.newLineAtOffset(90, 575);
            cs.setNonStrokingColor(Color.black);
            cs.showText("Product Descripition");
            cs.endText();
            cs.beginText();
            cs.setFont(myfont, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(65, 540);
            cs.showText(name+" ");
            cs.newLine();
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(65, 525);
            cs.showText("Ram:-"+ram+" gb Storage:-"+storage+" gb");
            cs.newLine();
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(65, 510);
            cs.showText("Processor:- "+processor);
            cs.newLine();
            cs.endText();

            PDImageXObject vrline2=PDImageXObject.createFromFile(String.valueOf(vrlinePath),invc);
            cs.drawImage(vrline2,220,597,30,(-233+ (-20 * n)));

            cs.beginText();
            cs.setFont(myfont, 14);
            cs.newLineAtOffset(260, 575);
            cs.showText("Unit Price");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(270, 540);
            cs.showText(String.valueOf(Prize));
            cs.newLine();

            cs.endText();

            PDImageXObject vrline3=PDImageXObject.createFromFile(String.valueOf(vrlinePath),invc);
            cs.drawImage(vrline3,320,597,30,(-233+ (-20 * n)));

            cs.beginText();
            cs.setFont(myfont, 14);
            cs.newLineAtOffset(350, 575);
            cs.showText("Quantity");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(370, 540);
            String prodqty="1";
            cs.showText(prodqty);
            cs.newLine();
            cs.endText();

//                cs.beginText();
//                cs.setFont(myfont, 14);
//                cs.newLineAtOffset(370, (400 - (20 * n)));
//                for (int i = 0; i <n; i++) {
//                   for(int j= productQty.get(i);j>0;j--){
//                       x++;
//                   }
//                    z=z+x;
//                   cs.showText(String.valueOf(z));
//
//                }
//                cs.endText();

            PDImageXObject vrline4=PDImageXObject.createFromFile(String.valueOf(vrlinePath),invc);
            cs.drawImage(vrline4,400,597,30,(-233+ (-20 * n)));

            cs.beginText();
            cs.setFont(myfont, 14);
            cs.newLineAtOffset(470, 575);
            cs.showText("Amount");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(470, 540);
            cs.showText(String.valueOf(Prize));
            cs.newLine();

            cs.endText();

            PDImageXObject hrLine2=PDImageXObject.createFromFile(String.valueOf(hrlinePath),invc);
            cs.drawImage(hrLine2,-145,550,815,20);

            cs.beginText();
            cs.setFont(myfont, 20);
            cs.setNonStrokingColor(Color.red);
            cs.newLineAtOffset(430, (410 - (20 * n)));
            cs.showText("Total=  ");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 20);
            //Calculating where total is to be written using number of products
            cs.newLineAtOffset(490, (410 - (20 * n)));
            cs.showText(String.valueOf(Prize));
            cs.endText();

            PDImageXObject hrLine3=PDImageXObject.createFromFile(String.valueOf(hrlinePath),invc);
            cs.drawImage(hrLine3,-145,(385 - (20 * n)),815,20);

            Numbertoword nw=new Numbertoword();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.setNonStrokingColor(Color.blue);
            cs.newLineAtOffset(30, (385 - (20 * n)));
            cs.showText("Indian Rupees "+nw.convert((int) Prize)+" Only");
            cs.endText();



            PDImageXObject hrLine4=PDImageXObject.createFromFile(String.valueOf(hrlinePath),invc);
            cs.drawImage(hrLine4,-145,(370 - (20 * n)),815,20);

            PDImageXObject hrLine5=PDImageXObject.createFromFile(String.valueOf(hrlinePath),invc);
            cs.drawImage(hrLine5,-145,(250 - (20 * n)),815,20);

            cs.beginText();
            cs.setFont(myfont, 10);
            cs.setNonStrokingColor(Color.black);
            cs.setLeading(10f);
            cs.newLineAtOffset(30, (245 - (20 * n)));
            cs.showText("Company's PAN:BLTPV7503R");
            cs.newLine();
            cs.showText("Declaration,");
            cs.newLine();
            cs.showText("We declare that this invoice shows the actual price of the" +
                    " goods described and that");
            cs.newLine();
            cs.showText("particulars are true and correct.");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 10);
            cs.setNonStrokingColor(Color.black);
            cs.newLineAtOffset(35, (185 - (20 * n)));
            cs.showText("Customer's Signature");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 10);
            cs.setNonStrokingColor(Color.black);
            cs.newLineAtOffset(445, (185 - (20 * n)));
            cs.showText("Authorised Signature");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 10);
            cs.setNonStrokingColor(Color.black);
            cs.newLineAtOffset(30, (165 - (20 * n)));
            cs.showText("*This is a Computer Generated Invoice");
            cs.endText();

            //Close the content stream
            cs.close();
            //Save the PDF
            invc.save(filePath1);
            System.out.println("Invoice Genreated Sucessfully!!");




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
