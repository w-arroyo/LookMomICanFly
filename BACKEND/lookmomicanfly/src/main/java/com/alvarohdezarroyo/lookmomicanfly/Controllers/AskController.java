package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AskDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PostContainerDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PostSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.SuccessfulRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Models.Product;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sale;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.AskRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.UpdatePostRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.PostMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.TransactionMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.PostValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/asks")
public class AskController {

    @Autowired
    private final AuthService authService;
    private final PostService postService;
    private final TransactionMapper transactionMapper;
    private final PostMapper postMapper;
    private final AskService askService;
    private final ProductService productService;


    public AskController(AuthService authService, PostService postService, TransactionMapper transactionMapper, PostMapper postMapper, AskService askService, ProductService productService) {
        this.authService = authService;
        this.postService = postService;
        this.transactionMapper = transactionMapper;
        this.postMapper = postMapper;
        this.askService = askService;
        this.productService = productService;
    }

    @GetMapping("/get/")
    public ResponseEntity<AskDTO> getAskById(@RequestParam String userId, @RequestParam String askId) throws Exception {
        GlobalValidator.checkIfTwoFieldsAreEmpty(userId,askId);
        authService.checkFraudulentRequest(userId);
        final Ask foundAsk=askService.findAskById(askId);
        GlobalValidator.checkIfDataBelongToRequestingUser(userId,foundAsk.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(postMapper.toAskDTO(foundAsk));
    }

    @GetMapping("/get-all/")
    public ResponseEntity<PostSummaryDTO[]> getAllUserAsks(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final List<Ask> userAsks=askService.getAllUserAsks(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(postMapper.askListToSummaryDTO(userAsks));
    }

    @GetMapping("/get/product/")
    public ResponseEntity<List<PostContainerDTO>> getAllProductAsks(@RequestParam String productId){
        GlobalValidator.checkIfAFieldIsEmpty(productId);
        final Product product=productService.findProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(postMapper.askListToContainer(
                                findAllProductAsks(product))
        );
    }

    private Map<String,Ask> findAllProductAsks(Product product){
        Map<String,Ask> asks=new HashMap<>();
        Size.getSizesByCategory(product.getCategory()).forEach(
                currentSize -> {
                    asks.put(currentSize.getValue(),
                            askService.getLowestAsk(product.getId(),currentSize));
                }
        );
        return asks;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveAsk(@RequestBody AskRequestDTO askRequest) throws Exception {
        GlobalValidator.checkIfRequestBodyIsEmpty(askRequest);
        PostValidator.checkIfPostFieldsAreEmpty(askRequest);
        GlobalValidator.checkIfANumberIsGreaterThan(askRequest.getAmount(),1);
        authService.checkFraudulentRequest(askRequest.getUserId());
        final Object askOrSale=postService.saveAsk(
                postMapper.toAsk(askRequest)
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(returnAskOrSale(askOrSale));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateAsk(@RequestBody UpdatePostRequestDTO askToUpdate) throws Exception {
        GlobalValidator.checkIfRequestBodyIsEmpty(askToUpdate);
        PostValidator.checkPostToUpdateFields(askToUpdate);
        authService.checkFraudulentRequest(askToUpdate.getUserId());
        final Object updatedAskOrSale=postService.updateAsk(askToUpdate.getPostId(),askToUpdate.getAmount(), askToUpdate.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(returnAskOrSale(updatedAskOrSale));
    }

    @GetMapping("/lowest-ask/")
    public ResponseEntity<SuccessfulRequestDTO> getLowestAskAmount(@RequestParam String productId, @RequestParam String size){
        GlobalValidator.checkIfTwoFieldsAreEmpty(productId,size);
        final Size sizeToCheck=ProductValidator.checkIfASizeExists(size);
        final Integer amount=askService.getLowestAskAmount(productId, sizeToCheck);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessfulRequestDTO(
                        PostValidator.returnAmountAsString(amount)
                ));
    }

    @GetMapping("/get/lowest-ask/")
    public ResponseEntity<SuccessfulRequestDTO> getLowestAsk(@RequestParam String productId){
        GlobalValidator.checkIfAFieldIsEmpty(productId);
        final Ask ask= askService.getLowestAskNoMatterTheSize(
                productService.findProductById(productId)
                        .getId()
        );
        String amount=null;
        if(ask!=null)
            amount=PostValidator.returnAmountAsString(ask.getAmount());
        return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessfulRequestDTO(amount));
    }

    private Object returnAskOrSale(Object askOrSale) throws Exception {
        if(askOrSale instanceof Ask)
            return postMapper.toAskDTO((Ask) askOrSale);
        return transactionMapper.saleToTransactionSummaryDTO((Sale) askOrSale);
    }



}
