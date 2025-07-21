package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.*;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.AskRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.BidRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.PostRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.PostValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PostMapper {

    @Value("${app.sellingShippingFee}")
    private Double sellingShippingFee;
    @Value("${app.operationalFee}")
    private Double operationalFee;

    @Autowired
    private final ProductService productService;
    private final UserValidator userValidator;
    private final AddressService addressService;
    private final SellingFeeService sellingFeeService;
    private final ShippingOptionService shippingOptionService;
    private final ProductMapper productMapper;
    private final BankAccountService bankAccountService;
    private final PaymentService paymentService;

    public PostMapper(ProductService productService, UserValidator userValidator, AddressService addressService, SellingFeeService sellingFeeService, ShippingOptionService shippingOptionService, ProductMapper productMapper, BankAccountService bankAccountService, PaymentService paymentService) {
        this.productService = productService;
        this.userValidator = userValidator;
        this.addressService = addressService;
        this.sellingFeeService = sellingFeeService;
        this.shippingOptionService = shippingOptionService;
        this.productMapper = productMapper;
        this.bankAccountService = bankAccountService;
        this.paymentService = paymentService;
    }

    private void fillPostFields(PostRequestDTO postRequestDto, Post post){
        post.setActive(true);
        post.setFinalized(false);
        post.setUser(userValidator.returnUserById(postRequestDto.getUserId()));
        post.setProduct(productService.findProductById(postRequestDto.getProductId()));
        post.setAddress(addressService.getAddressById(postRequestDto.getAddressId()));
        post.setAmount(postRequestDto.getAmount());
        post.setSize(ProductValidator.checkIfASizeExists(postRequestDto.getSize()));
    }

    private PostSummaryDTO toSummaryDTO(Post post){
        return new PostSummaryDTO(post.getId(),post.getSize().getValue(),post.getAmount(),
                productMapper.toSummary(
                        productService.findProductById(post.getProduct().getId())
                ));
    }

    public PostSummaryDTO[] askListToSummaryDTO(List<Ask> asks){
        return asks.stream().map(this::toSummaryDTO).toArray(size -> new PostSummaryDTO[asks.size()]);
    }

    public PostSummaryDTO[] bidListToSummaryDTO(List<Bid> bids){
        return bids.stream().map(this::toSummaryDTO).toArray(size -> new PostSummaryDTO[bids.size()]);
    }

    private void fillPostDTOFields(PostDTO postDTO, Post post) throws Exception {
        postDTO.setId(post.getId());
        postDTO.setSize(post.getSize().getValue());
        postDTO.setAmount(post.getAmount());
        postDTO.setAddressDTO(AddressMapper.toDTO(post.getAddress()));
        postDTO.setProductSummaryDTO(productMapper.toSummary(post.getProduct()));
    }

    public Ask toAsk(AskRequestDTO askRequest){
        final Ask ask=new Ask();
        fillPostFields(askRequest,ask);
        ask.setShippingFee(sellingShippingFee);
        ask.setSellingFee(sellingFeeService.checkIfThereIsADefaultFee(askRequest.getUserId()));
        final BankAccount bankAccount= bankAccountService.findById(askRequest.getBankAccountId());
        PostValidator.checkIfBankAccountBelongsToUser(askRequest.getUserId(),bankAccount.getUser().getId());
        ask.setBankAccount(bankAccount);
        return ask;
    }

    public Bid toBid(BidRequestDTO bidRequest){
        final Bid bid=new Bid();
        fillPostFields(bidRequest,bid);
        bid.setOperationalFee(operationalFee);
        bid.setShippingOption(shippingOptionService.getShippingOptionById(bidRequest.getShippingOptionId()));
        final Payment payment=paymentService.findPaymentByPaymentIntentId(bidRequest.getPaymentIntentId());
        bid.setPayment(payment);
        return bid;
    }

    public AskDTO toAskDTO(Ask ask) throws Exception {
        final AskDTO askDTO=new AskDTO();
        fillPostDTOFields(askDTO,ask);
        askDTO.setShippingFee(ask.getShippingFee());
        askDTO.setSellingFee(SellingFeeMapper.toDTO(ask.getSellingFee()));
        return askDTO;
    }

    public BidDTO toBidDTO(Bid bid) throws Exception {
        final BidDTO bidDTO=new BidDTO();
        fillPostDTOFields(bidDTO,bid);
        bidDTO.setShippingOptionDTO(ShippingOptionMapper.toDTO(bid.getShippingOption()));
        bidDTO.setOperatingFee(bid.getOperationalFee());
        return bidDTO;
    }

    private PostContainerDTO toContainer(String size, Post post){
        if(post!=null){
            return new PostContainerDTO(size, post.getAmount());
        }
        return new PostContainerDTO(size,null);
    }

    public List<PostContainerDTO> askListToContainer(Map<String,Ask> asks){
      final List<PostContainerDTO> containers=new ArrayList<>();
      for(String size: asks.keySet())
          containers.add(toContainer(size,asks.get(size)));
      return containers;
    }

    public List<PostContainerDTO> bidListToContainer(Map<String,Bid> bids){
        final List<PostContainerDTO> bidsContainer=new ArrayList<>();
        for(String size: bids.keySet())
            bidsContainer.add(toContainer(size,bids.get(size)));
        return bidsContainer;
    }

}
