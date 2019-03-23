package com.habib.api.rest;

import com.habib.api.domains.APIResult;
import com.habib.api.domains.ApiResponse;
import com.habib.api.domains.ListValue;
import com.habib.api.repository.ListValueRepository;
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
@RequestMapping("listvalues")
public class ListValueRestController {
    private static final Logger logger = LoggerFactory.getLogger(ListValueRestController.class);
    @Autowired
    private ListValueRepository listValueRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<ListValue>> listAllValues() {
        List<ListValue> listValues = listValueRepository.findAll();
        if (listValues.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<ListValue>>(listValues, HttpStatus.OK);
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ResponseEntity<ListValue> findByEmail(@PathVariable("id") long id) {
        ListValue listValue = listValueRepository.findById(id).orElse(null);
        if (listValue ==null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<ListValue>(listValue, HttpStatus.OK);
    }

    @RequestMapping(value = "/allFeildsValuesById/{id}", method = RequestMethod.GET)
    public ApiResponse<List<ListValue>> getListValueById(@PathVariable("id") int id) {
        try {
            List<ListValue> listValues = listValueRepository.findByListId(id);
            if (listValues.isEmpty())
                return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "list fetched not found.", listValues);
            else
                return new ApiResponse<>(HttpStatus.OK.value(), "list fetched successfully.", listValues);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), ex.getMessage(), null);
        }
    }

    @RequestMapping(value = "/allFeildsValues/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ListValue>> listAllFeildValues(@PathVariable("id") int id) {
        List<ListValue> listValues = listValueRepository.findByListId(id);
        if (listValues.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<ListValue>>(listValues, HttpStatus.OK);
    }

    @RequestMapping(value = "/listvalue/", method = RequestMethod.POST)
    public ResponseEntity<?> createListValue(@RequestBody ListValue listValue, UriComponentsBuilder ucBuilder) {
        logger.info("Creating : {}", listValue);
        try{
            listValueRepository.save(listValue);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/listvalues/findById/{id}").buildAndExpand(listValue.getId()).toUri());
           // return new ResponseEntity<String>(headers, HttpStatus.CREATED);
            return ResponseEntity.ok(listValue);
        }
        catch (Exception ex)
        {
            return new ResponseEntity(new APIResult(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
