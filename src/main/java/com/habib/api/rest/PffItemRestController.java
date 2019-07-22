package com.habib.api.rest;

import com.habib.api.CustomException.EmployeeException;
import com.habib.api.domains.*;
import com.habib.api.repository.PffInvoiceDetailRepository;
import com.habib.api.repository.PffInvoiceRepository;
import com.habib.api.repository.PffItemRepository;
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
@RequestMapping("pff-item")
public class PffItemRestController {

    private static final Logger logger = LoggerFactory.getLogger(PffItemRestController.class);

    @Autowired
    PffItemRepository pffItemRepository;

    @Autowired
    PffInvoiceRepository pffInvoiceRepository;

    @Autowired
    PffInvoiceDetailRepository pffInvoiceDetailRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<PffItem>> listAllValues(@RequestParam("status") String status) {
        try {
            List<PffItem> list=null;

            if(status.isEmpty())
            list = pffItemRepository.findAllByOrderBySortitem();
            else
                list = pffItemRepository.findAllByStatusOrderBySortitem(status);

            if (list.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
                // You many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<List<PffItem>>(list, HttpStatus.OK);
        } catch (Exception err) {
            throw new EmployeeException(
                    "PffItem Status error", err);
        }
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    public ResponseEntity<?> createListValue(@RequestBody PffItem pffItem, UriComponentsBuilder ucBuilder) {
        logger.info("Creating : {}", pffItem);
        try{
            pffItemRepository.save(pffItem);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/listvalues/findById/{id}").buildAndExpand(pffItem.getItemid()).toUri());
            // return new ResponseEntity<String>(headers, HttpStatus.CREATED);
            return ResponseEntity.ok(pffItem);
        }
        catch (Exception ex)
        {
            return new ResponseEntity(new APIResult(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/savePffInvoice/", method = RequestMethod.POST)
    public ResponseEntity<?> savePffInvoice(@RequestBody PffInvoice pffInvoice, UriComponentsBuilder ucBuilder) {
        logger.info("Creating : {}", pffInvoice);
        try{
            pffInvoiceRepository.save(pffInvoice);
            for (PffInvoiceDetail line: pffInvoice.getPffInvoiceLines()) {
                line.setInvoiceid(pffInvoice.getId());
                pffInvoiceDetailRepository.save(line);
            }
            HttpHeaders headers = new HttpHeaders();
            //headers.setLocation(ucBuilder.path("/payment/findById/{id}").buildAndExpand(payment.getPaymentid()).toUri());
            // return new ResponseEntity<String>(headers, HttpStatus.CREATED);
            return ResponseEntity.ok(pffInvoice);
        }
        catch (Exception ex)
        {
            return new ResponseEntity(new APIResult(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
