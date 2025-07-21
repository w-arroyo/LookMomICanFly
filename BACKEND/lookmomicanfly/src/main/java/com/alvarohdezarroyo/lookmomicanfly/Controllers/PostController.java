package com.alvarohdezarroyo.lookmomicanfly.Controllers;

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
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        GlobalValidator.checkIfTwoFieldsAreEmpty(request.getUserId(), request.getNewField());
        authService.checkFraudulentRequest(request.getUserId());
        postService.deactivatePost(request.getNewField(), request.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PostMapping("/size")
    public ResponseEntity<Map<String,Integer>> getASizeBestPost(@RequestParam String size, @RequestBody GetPostRequestDTO request){
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        PostValidator.checkIfGetPostRequestFieldsAreEmpty(request);
        PostValidator.checkIfEntityExists(request.getEntity());
        GlobalValidator.checkIfAFieldIsEmpty(size);
        final Size sizeAsEnum=ProductValidator.checkIfASizeExists(size);
        ProductValidator.checkIfSizeBelongsToACategory(sizeAsEnum,ProductValidator.checkIfProductCategoryExists(request.getCategory()));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("post",
                        postService.getASizeBestPost(request.getProductId(),request.getEntity(), sizeAsEnum)));
    }

}
