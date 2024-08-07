package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.BaseResponse;
import com.itma.gestionProjet.entities.Customer;
import com.itma.gestionProjet.repositories.CustomerRepository;
import com.itma.gestionProjet.services.CustomerService;
import com.itma.gestionProjet.utils.CustomerDTO;
import com.itma.gestionProjet.utils.ExcelUtils;
import com.itma.gestionProjet.utils.FileFactory;
import com.itma.gestionProjet.utils.ImportConfig;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;


    @Override
    public BaseResponse importCustomerData(MultipartFile importFile) {


        BaseResponse baseResponse = new BaseResponse();

        Workbook workbook = FileFactory.getWorkbookStream(importFile);

        List<CustomerDTO> customerDTOList = ExcelUtils.getImportData(workbook, ImportConfig.customerImport);

        if(!CollectionUtils.isEmpty(customerDTOList)){

            saveData(customerDTOList);
            baseResponse.setCode(String.valueOf(HttpStatus.OK));
            baseResponse.setMessage("Import successfully");
        }else{
            baseResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseResponse.setMessage("Import failed");
        }

        return baseResponse;
    }

    @Override
    public BaseResponse createCustomer(CustomerDTO customerDTO) {

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode("200");
        baseResponse.setMessage("Create successfully");

        //saveData(List.of(customerDTO));

        //send mail
      //  mailService.sendMailCreateCustomer(customerDTO);
        return baseResponse;
    }

    @Override
    public List<File> getFilesFromServer() {
        return getFileFromComputer();
    }

    @Override
    public List<File> zipExcelFileFromDatabase() throws Exception {
        List<Customer> customers = customerRepository.findAll();
        String fileName = "Customer_Export" + ".xlsx";
        List<File> resultFiles = ExcelUtils.getFilesExcelStoreDataFromDatabase(customers, fileName);
        return resultFiles;
    }

    private List<File> getFileFromComputer(){
        List<File> result = new ArrayList<>();
        File file1 = new File("F:\\template\\1.PNG");
        File file2 = new File("F:\\template\\2.PNG");
        File file3 = new File("F:\\template\\3.PNG");
        File file4 = new File("F:\\template\\4.PNG");
        File excelFile = new File("F:\\template\\Customer Export.xlsx");
        result.add(file1);
        result.add(file2);
        result.add(file3);
        result.add(file4);
        result.add(excelFile);
        return result;
    }

    private void saveData(List<CustomerDTO> customerDTOList){
        for(CustomerDTO customerDTO : customerDTOList){
            Customer customer = new Customer();
          //  printCustomerData(customerDTOList);
            System.out.println("Customer Number: " + customerDTO.getCustomerNumber());
            customer.setCustomerNumber(customerDTO.getCustomerNumber());
            customer.setCustomerName(customerDTO.getCustomerName());
            customer.setContactFirstName(customerDTO.getContactFirstName());
            customer.setContactLastName(customerDTO.getContactLastName());
            customer.setPhone(customerDTO.getPhone());
            customer.setAddressLine1(customerDTO.getAddressLine1());
            customer.setAddressLine2(customerDTO.getAddressLine2());
            customer.setCity(customerDTO.getCity());
            customer.setState(customerDTO.getState());
            customer.setPostalCode(customerDTO.getPostalCode());
            customer.setCountry(customerDTO.getCountry());
            customer.setSalesRepEmployeeNumber(customerDTO.getSalesRepEmployeeNumber());
            customer.setCreditLimit(customerDTO.getCreditLimit());
            customerRepository.save(customer);
        }
    }

    private void printCustomerData(List<CustomerDTO> customerDTOList) {
        for (CustomerDTO customerDTO : customerDTOList) {
            System.out.println("Customer Number: " + customerDTO.getCustomerNumber());
            System.out.println("Customer Name: " + customerDTO.getCustomerName());
            System.out.println("Address Line 1: " + customerDTO.getAddressLine1());
            System.out.println("Address Line 2: " + customerDTO.getAddressLine2());
            System.out.println("City: " + customerDTO.getCity());
            System.out.println("Contact First Name: " + customerDTO.getContactFirstName());
            System.out.println("Contact Last Name: " + customerDTO.getContactLastName());
            System.out.println("Country: " + customerDTO.getCountry());
            System.out.println("Credit Limit: " + customerDTO.getCreditLimit());
            System.out.println("Phone: " + customerDTO.getPhone());
            System.out.println("Postal Code: " + customerDTO.getPostalCode());
            System.out.println("Sales Rep Employee Number: " + customerDTO.getSalesRepEmployeeNumber());
            System.out.println("State: " + customerDTO.getState());
            System.out.println("---------------------------------");
        }
    }

}
