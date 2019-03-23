package com.habib.api.rest;

import com.habib.api.CustomException.EmployeeException;
import com.habib.api.domains.APIResult;
import com.habib.api.domains.Employee;
import com.habib.api.domains.ListValue;
import com.habib.api.repository.EmployeeRepository;
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
@RequestMapping("rest-employees")
public class EmployeeRestController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> listAllValues() {
        try {
            List<Employee> list = employeeRepository.findAll();
            if (list.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
                // You many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
        } catch (Exception err) {
                throw new EmployeeException(
                        "Employee Status error", err);
        }
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {
        logger.info("Creating : {}", employee);
        try{
            employeeRepository.save(employee);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/listvalues/findById/{id}").buildAndExpand(employee.getEmployeeid()).toUri());
            // return new ResponseEntity<String>(headers, HttpStatus.CREATED);
            return ResponseEntity.ok(employee);
        }
        catch (Exception ex)
        {
            return new ResponseEntity(new APIResult(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
