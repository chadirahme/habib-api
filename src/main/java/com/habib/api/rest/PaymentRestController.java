package com.habib.api.rest;

import com.habib.api.CustomException.EmployeeException;
import com.habib.api.domains.APIResult;
import com.habib.api.domains.Payment;
import com.habib.api.domains.Supplier;
import com.habib.api.repository.PaymnetRepository;
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
@RequestMapping("rest-payments")
public class PaymentRestController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRestController.class);

    @Autowired
    PaymnetRepository paymnetRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Payment>> listAllValues() {
        try {
            List<Payment> list = paymnetRepository.findAll();
            if (list.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
                // You many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<List<Payment>>(list, HttpStatus.OK);
        } catch (Exception err) {
            throw new EmployeeException(
                    "Payment Status error", err);
        }
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    public ResponseEntity<?> saveSupplier(@RequestBody Payment payment, UriComponentsBuilder ucBuilder) {
        logger.info("Creating : {}", payment);
        try{
            paymnetRepository.save(payment);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/payment/findById/{id}").buildAndExpand(payment.getPaymentid()).toUri());
            // return new ResponseEntity<String>(headers, HttpStatus.CREATED);
            return ResponseEntity.ok(payment);
        }
        catch (Exception ex)
        {
            return new ResponseEntity(new APIResult(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
