package com.habib.api.rest;

import com.habib.api.CustomException.EmployeeException;
import com.habib.api.domains.APIResult;
import com.habib.api.domains.ApiResponse;
import com.habib.api.domains.Payment;
import com.habib.api.repository.PaymentsStatistics;
import com.habib.api.repository.PaymnetRepository;
import com.habib.api.repository.SupplierRepository;
import com.habib.api.repository.UserRepository;
import com.habib.api.services.FileService;
import com.habib.api.util.GenerateFileName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("rest-payments")
public class PaymentRestController extends AbstractRestController{

    private static final Logger logger = LoggerFactory.getLogger(PaymentRestController.class);

    @Autowired
    PaymnetRepository paymnetRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileService fileService;

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
    public ResponseEntity<?> savePayment(@RequestBody Payment payment, UriComponentsBuilder ucBuilder) {
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
    //save payment with attach
    @PostMapping("/savePaymentWithAttach/")
    @ResponseBody
    public ResponseEntity<APIResult> savePaymentWithAttach(@RequestParam("paymentid") String paymentid,@RequestParam("supplierid") String supplierid, @RequestParam("amount") String amount,
                                                      @RequestParam("paymentdate") String paymentdate,@RequestParam("userid") String userid,
                                                      @RequestParam("description") String description,@RequestParam("paidby") String paidby,
                                                      @RequestParam("file") MultipartFile file) {
        APIResult apiResult=new APIResult();
        String message = "";
        try {
            String filePath= fileService.store(file);
            if(!filePath.equals("-1"))
            {
                Payment payment= paymnetRepository.findById(Long.parseLong(paymentid)).orElse(null);
                if(payment==null)
                {
                    payment=new Payment();
                }
               // Payment payment=new Payment();
                payment.setSupplierid(Long.parseLong(supplierid));
                payment.setSupplier(supplierRepository.getOne(payment.getSupplierid()));
                payment.setUser(userRepository.getOne(Long.parseLong(userid)));
                payment.setAmount(Double.parseDouble(amount));
                payment.setDescription(description);
                java.util.Date parsePaymentdate=new SimpleDateFormat("yyyy-MM-dd").parse(paymentdate);
                payment.setPaymentdate(parsePaymentdate);
                payment.setPaidby(paidby);
                payment.setCreatedtime(convertStringToDate(getCurrentDate()));
                payment.setFilename(GenerateFileName.getNewFileName());
                payment.setFilepath(filePath);
                paymnetRepository.save(payment);

                message = "You successfully uploaded " + file.getOriginalFilename() + "!";
                apiResult.setMessage(message);
                return ResponseEntity.status(HttpStatus.OK).body(apiResult);
            }
            else
            {
                message = "Fail to upload Profile Picture" + file.getOriginalFilename() + "!";
                apiResult.setMessage(message);
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(apiResult);
            }
        } catch (Exception e) {
            message = "Fail to upload Profile Picture" + file.getOriginalFilename() + "!";
            apiResult.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(apiResult);
        }

    }

    @RequestMapping(value = "/delete/", method = RequestMethod.POST)
    public ApiResponse<?> deleteSupplier(@RequestBody Payment payment, UriComponentsBuilder ucBuilder) {
        logger.info("delete : {}", payment);
        try{
            paymnetRepository.delete(payment);
            return new ApiResponse<>(HttpStatus.OK.value(), "Payment deleted successfully.", payment);
        }
        catch (Exception ex)
        {
            return new ApiResponse<>(HttpStatus.FAILED_DEPENDENCY.value(), ex.getMessage(), null);
        }
    }

    @RequestMapping(value = "/allyear", method = RequestMethod.GET)
    public ResponseEntity<HashMap<Integer,List<PaymentsStatistics>>> findAllPaymentsByYear()
    {
      //List<PaymentsStatistics> list=paymnetRepository.findAllPaymentsByYear(2017);
        HashMap<Integer,List<PaymentsStatistics> > hashMap=new HashMap<>();
        hashMap.put(2017,paymnetRepository.findAllPaymentsByYear(2017));
        hashMap.put(2018,paymnetRepository.findAllPaymentsByYear(2018));
        return new ResponseEntity<HashMap<Integer,List<PaymentsStatistics> >>(hashMap, HttpStatus.OK);

    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
