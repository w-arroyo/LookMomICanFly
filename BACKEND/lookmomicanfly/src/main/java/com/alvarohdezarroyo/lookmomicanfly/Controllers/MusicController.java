package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.MusicDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Music;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ColorService;
import com.alvarohdezarroyo.lookmomicanfly.Services.MusicService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products/music")
public class MusicController {

    @Autowired
    private final MusicService musicService;
    private final AuthService authService;
    private final ProductMapper productMapper;
    private final ColorService colorService;

    public MusicController(MusicService musicService, AuthService authService, ProductMapper productMapper, ColorService colorService) {
        this.musicService = musicService;
        this.authService = authService;
        this.productMapper = productMapper;
        this.colorService = colorService;
    }

    @GetMapping("/get/")
    public ResponseEntity<MusicDTO> getMusicDTOById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toMusicDTO(musicService.getMusicById(id)));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Music>> saveMusicFromRequest(@RequestBody MusicDTO musicDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(musicDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(musicDTO,musicDTO.getFormat(),"format");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success",
                        saveMusic(productMapper.toMusic(musicDTO))));
    }

    private Music saveMusic(Music music){
        ProductValidator.checkIfCategoryIsCorrect(music.getCategory(), ProductCategory.MUSIC);
        final Music savedMusic=musicService.saveMusic(music);
        colorService.saveProductColors(savedMusic.getColors(), savedMusic.getId());
        return savedMusic;
    }

}
