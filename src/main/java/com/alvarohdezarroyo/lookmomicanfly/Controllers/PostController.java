package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.HighestLowestPostDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PostSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.ChangeUserFieldsRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.GetPostRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.PostService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.PostValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private final AuthService authService;
    private final PostService postService;

    public PostController(AuthService authService, PostService postService) {
        this.authService = authService;
        this.postService = postService;
    }

    @PutMapping("/deactivate")
    public ResponseEntity<String> deactivatePost(@RequestBody ChangeUserFieldsRequestDTO request){
        checkIfBodyIsEmpty(request);
        authService.checkFraudulentRequest(request.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(postService.deactivatePost(request.getNewField(), request.getUserId()));
    }

    @PostMapping("/get-all-summary")
    public ResponseEntity<Map<String, PostSummaryDTO[]>> getAllUserBidsSummaryDTO(@RequestBody ChangeUserFieldsRequestDTO request){
        checkIfBodyIsEmpty(request);
        PostValidator.checkIfEntityExists(request.getNewField());
        authService.checkFraudulentRequest(request.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("posts", postService.getAllUserActivePosts(request.getUserId(), request.getNewField())));
    }

    @PostMapping("/sizes")
    public ResponseEntity<Map<String, HighestLowestPostDTO[]>> getAllSizesPosts(@RequestBody GetPostRequestDTO getRequest){
        checkIfGetPostBodyIsEmpty(getRequest);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("posts",postService.getAllHighestLowestPost(getRequest.getProductId(),ProductValidator.checkIfProductCategoryExists(getRequest.getCategory()), getRequest.getEntity())));
    }

    @PostMapping("/size")
    public ResponseEntity<Map<String,Integer>> getASizeBestPost(@RequestParam String size, @RequestBody GetPostRequestDTO request){
        checkIfGetPostBodyIsEmpty(request);
        GlobalValidator.checkIfAFieldIsEmpty(size);
        final Size sizeAsEnum=ProductValidator.checkIfASizeExists(size);
        ProductValidator.checkIfSizeBelongsToACategory(sizeAsEnum,ProductValidator.checkIfProductCategoryExists(request.getCategory()));
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("post", postService.getASizeBestPost(request.getProductId(),request.getEntity(), sizeAsEnum)));
    }

    private void checkIfBodyIsEmpty(ChangeUserFieldsRequestDTO request){
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        GlobalValidator.checkIfTwoFieldsAreEmpty(request.getUserId(), request.getNewField());
    }

    private void checkIfGetPostBodyIsEmpty(GetPostRequestDTO request){
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        PostValidator.checkIfGetPostRequestFieldsAreEmpty(request);
        PostValidator.checkIfEntityExists(request.getEntity());
    }

}
