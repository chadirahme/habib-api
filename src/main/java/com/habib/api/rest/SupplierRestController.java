package com.habib.api.rest;

import com.habib.api.CustomException.EmployeeException;
import com.habib.api.domains.APIResult;
import com.habib.api.domains.Employee;
import com.habib.api.domains.Supplier;
import com.habib.api.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("rest-suppliers")
public class SupplierRestController {

    private static final Logger logger = LoggerFactory.getLogger(SupplierRestController.class);
    @Autowired
    SupplierRepository supplierRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Supplier>> listAllValues() {
        try {
            List<Supplier> list = supplierRepository.findAll();
            if (list.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
                // You many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<List<Supplier>>(list, HttpStatus.OK);
        } catch (Exception err) {
            throw new EmployeeException(
                    "Supplier Status error", err);
        }
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    public ResponseEntity<?> saveSupplier(@RequestBody Supplier supplier, UriComponentsBuilder ucBuilder) {
        logger.info("Creating : {}", supplier);
        try{
            supplierRepository.save(supplier);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/supplier/findById/{id}").buildAndExpand(supplier.getSupplierid()).toUri());
            // return new ResponseEntity<String>(headers, HttpStatus.CREATED);
            return ResponseEntity.ok(supplier);
        }
        catch (Exception ex)
        {
            return new ResponseEntity(new APIResult(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete/", method = RequestMethod.POST)
    public ResponseEntity<?> deleteSupplier(@RequestBody Supplier supplier, UriComponentsBuilder ucBuilder) {
        logger.info("delete : {}", supplier);
        try{
            supplierRepository.delete(supplier);
            //HttpHeaders headers = new HttpHeaders();
            //headers.setLocation(ucBuilder.path("/supplier/findById/{id}").buildAndExpand(supplier.getSupplierid()).toUri());
            // return new ResponseEntity<String>(headers, HttpStatus.CREATED);
            return ResponseEntity.ok(supplier);
        }
        catch (Exception ex)
        {
            return new ResponseEntity(new APIResult(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
