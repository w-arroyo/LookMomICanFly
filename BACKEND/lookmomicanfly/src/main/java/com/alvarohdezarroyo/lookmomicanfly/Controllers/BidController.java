package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Config.AppConfig;
import com.alvarohdezarroyo.lookmomicanfly.DTO.BidDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PostContainerDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PostSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.SuccessfulRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import com.alvarohdezarroyo.lookmomicanfly.Models.Order;
import com.alvarohdezarroyo.lookmomicanfly.Models.Product;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.BidRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.UpdatePostRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.PostMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.TransactionMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.PaymentValidator;
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
@RequestMapping("/api/bids")
public class BidController {

    @Autowired
    private final AuthService authService;
    private final PostService postService;
    private final PostMapper postMapper;
    private final TransactionMapper transactionMapper;
    private final BidService bidService;
    private final ProductService productService;
    private final PaymentValidator paymentValidator;

    public BidController(AuthService authService, PostService postService, PostMapper postMapper, TransactionMapper transactionMapper, BidService bidService, ProductService productService, PaymentValidator paymentValidator) {
        this.authService = authService;
        this.postService = postService;
        this.postMapper = postMapper;
        this.transactionMapper = transactionMapper;
        this.bidService = bidService;
        this.productService = productService;
        this.paymentValidator = paymentValidator;
    }

    @GetMapping("/get/")
    public ResponseEntity<BidDTO> getBidById(@RequestParam String bidId, @RequestParam String userId) throws Exception {
        GlobalValidator.checkIfTwoFieldsAreEmpty(bidId,userId);
        authService.checkFraudulentRequest(userId);
        final Bid foundBid=bidService.findBidById(bidId);
        GlobalValidator.checkIfDataBelongToRequestingUser(userId,foundBid.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(postMapper.toBidDTO(foundBid));
    }

    @GetMapping("/get-all/")
    public ResponseEntity<PostSummaryDTO[]> getAllUserBids(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final List<Bid> bids=bidService.getAllUserBids(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(postMapper.bidListToSummaryDTO(bids));
    }

    @GetMapping("/get/product/")
    public ResponseEntity<List<PostContainerDTO>> getAllProductBids(@RequestParam String productId){
        GlobalValidator.checkIfAFieldIsEmpty(productId);
        final Product product=productService.findProductById(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(postMapper.bidListToContainer(
                                findAllProductBids(product)
                        ));
    }

    private Map<String,Bid> findAllProductBids(Product product){
        final Map<String,Bid> bids=new HashMap<>();
        Size.getSizesByCategory(product.getCategory()).forEach(
                size -> {
                    bids.put(size.getValue(),bidService.getHighestBid(product.getId(),size));
                }
        );
        return bids;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveBid(@RequestBody BidRequestDTO bidRequest) throws Exception {
        GlobalValidator.checkIfRequestBodyIsEmpty(bidRequest);
        PostValidator.checkIfPostFieldsAreEmpty(bidRequest);
        GlobalValidator.checkIfANumberIsGreaterThan(bidRequest.getAmount(), 1);
        authService.checkFraudulentRequest(bidRequest.getUserId());
        paymentValidator.checkIfPaymentIntentIdIsValid(bidRequest.getPaymentIntentId());
        final Object bidOrOrder=postService.saveBid(
                postMapper.toBid(bidRequest)
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(returnOrderOrBid(bidOrOrder));
    }

    @GetMapping("/highest-bid/")
    public ResponseEntity<SuccessfulRequestDTO> getHighestBidAmount(@RequestParam String productId, @RequestParam String size){
        GlobalValidator.checkIfTwoFieldsAreEmpty(productId,size);
        final Integer amount=bidService.getHighestBidAmount(productId, ProductValidator.checkIfASizeExists(size));
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessfulRequestDTO(
                        PostValidator.returnAmountAsString(amount))
        );
    }

    @GetMapping("get/operational")
    public ResponseEntity<SuccessfulRequestDTO> getOperationalFee(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessfulRequestDTO(AppConfig.getOperationalBuyingFee()+"")
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateBidAmount(@RequestBody UpdatePostRequestDTO bidToUpdate) throws Exception {
        GlobalValidator.checkIfRequestBodyIsEmpty(bidToUpdate);
        PostValidator.checkPostToUpdateFields(bidToUpdate);
        authService.checkFraudulentRequest(bidToUpdate.getUserId());
        final Object updatedBidOrOrder=postService.updateBid(bidToUpdate.getPostId(),bidToUpdate.getAmount(), bidToUpdate.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(returnOrderOrBid(updatedBidOrOrder));
    }

    private Object returnOrderOrBid(Object bidOrOrder) throws Exception {
        if(bidOrOrder instanceof Bid)
            return postMapper.toBidDTO((Bid) bidOrOrder);
        return transactionMapper.orderToTransactionSummaryDTO((Order) bidOrOrder);

    }

}
