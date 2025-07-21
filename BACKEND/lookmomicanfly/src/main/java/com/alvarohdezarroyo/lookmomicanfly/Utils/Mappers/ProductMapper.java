package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.*;
import com.alvarohdezarroyo.lookmomicanfly.Enums.FootballCategory;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Format;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Material;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Season;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Services.ColorService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ManufacturerService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductMapper {
    @Autowired
    private final ManufacturerService manufacturerService;
    private final ColorService colorService;

    public ProductMapper(ManufacturerService manufacturerService, ColorService colorService) {
        this.manufacturerService=manufacturerService;
        this.colorService=colorService;
    }

    public void fillProductDTOFields(ProductDTO productDTO, Product product){
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setActive(product.getActive());
        productDTO.setManufacturer(product.getManufacturer().getName());
        productDTO.setCategory(product.getCategory().name());
        productDTO.setSubcategory(product.getSubcategory().name());
        productDTO.setReleaseYear(product.getReleaseYear());
        productDTO.setColors(product.getColors().stream().map(Color::getName).toArray(String[]::new));
    }

    public void fillProductFields(Product product, ProductDTO productDTO){
        product.setName(productDTO.getName());
        product.setActive(true);
        product.setReleaseYear(productDTO.getReleaseYear());
        product.setManufacturer(manufacturerService.getManufacturerByName(productDTO.getManufacturer()));
        product.setColors(new ArrayList<>());
        Arrays.stream(productDTO.getColors()).forEach(color-> {
            product.getColors().add(colorService.findColorByName(color));
        });
        product.setCategory(ProductValidator.checkIfProductCategoryExists(productDTO.getCategory()));
        product.setSubcategory(ProductValidator.checkIfProductSubcategoryExists(productDTO.getSubcategory()));
        ProductValidator.checkIfSubcategoryBelongsToACategory(product.getCategory(),product.getSubcategory());
    }

    public ProductSummaryDTO toSummary(Product product){
        final ProductSummaryDTO productSummaryDTO=new ProductSummaryDTO();
        productSummaryDTO.setId(product.getId());
        productSummaryDTO.setName(product.getName());
        productSummaryDTO.setYear(product.getReleaseYear());
        productSummaryDTO.setManufacturer(product.getManufacturer().getName());
        productSummaryDTO.setCategory(product.getCategory().name());
        return productSummaryDTO;
    }

    public SneakersDTO toSneakersDTO(Sneakers sneakers){
        final SneakersDTO sneakersDTO=new SneakersDTO();
        fillProductDTOFields(sneakersDTO,sneakers);
        sneakersDTO.setSku(sneakers.getSku());
        return sneakersDTO;
    }

    public Sneakers toSneakers(SneakersDTO sneakersDTO){
        final Sneakers sneakers=new Sneakers();
        fillProductFields(sneakers,sneakersDTO);
        sneakers.setSku(sneakersDTO.getSku());
        return sneakers;
    }

    public ClothingDTO toClothingDTO(Clothing clothing){
        final ClothingDTO clothingDTO=new ClothingDTO();
        fillProductDTOFields(clothingDTO,clothing);
        clothingDTO.setSeason(clothing.getSeason().name());
        return clothingDTO;
    }

    public Clothing toClothing(ClothingDTO clothingDTO){
        Clothing clothing=new Clothing();
        fillProductFields(clothing,clothingDTO);
        clothing.setSeason(Season.getSeasonFromName(clothingDTO.getSeason()));
        return clothing;
    }

    public AccessoryDTO toAccessoryDTO(Accessory accessory){
        final AccessoryDTO accessoryDTO=new AccessoryDTO();
        fillProductDTOFields(accessoryDTO, accessory);
        accessoryDTO.setMaterial(accessory.getMaterial().name());
        return accessoryDTO;
    }

    public Accessory toAccessory(AccessoryDTO accessoryDTO){
        final Accessory accessory =new Accessory();
        fillProductFields(accessory,accessoryDTO);
        accessory.setMaterial(Material.getMaterialFromName(accessoryDTO.getMaterial()));
        return accessory;
    }

    public CollectibleDTO toCollectibleDTO(Collectible collectible){
        final CollectibleDTO collectibleDTO=new CollectibleDTO();
        fillProductDTOFields(collectibleDTO,collectible);
        collectibleDTO.setCollectionName(collectible.getCollectionName());
        return collectibleDTO;
    }

    public Collectible toCollectible(CollectibleDTO collectibleDTO){
        Collectible collectible=new Collectible();
        fillProductFields(collectible,collectibleDTO);
        collectible.setCollectionName(collectibleDTO.getCollectionName());
        return collectible;
    }

    public SkateboardDTO toSkateboardDTO(Skateboard skateboard){
        final SkateboardDTO skateboardDTO=new SkateboardDTO();
        fillProductDTOFields(skateboardDTO,skateboard);
        skateboardDTO.setLength(skateboard.getLength());
        return skateboardDTO;
    }

    public Skateboard toSkateboard(SkateboardDTO skateboardDTO){
        Skateboard skateboard=new Skateboard();
        fillProductFields(skateboard,skateboardDTO);
        skateboard.setLength(skateboardDTO.getLength());
        return skateboard;
    }

    public MusicDTO toMusicDTO(Music music){
        final MusicDTO musicDTO=new MusicDTO();
        fillProductDTOFields(musicDTO,music);
        musicDTO.setFormat(music.getFormat().name());
        return musicDTO;
    }

    public Music toMusic(MusicDTO musicDTO){
        final Music music=new Music();
        fillProductFields(music,musicDTO);
        music.setFormat(Format.getFormatByName(musicDTO.getFormat()));
        return music;
    }

    public Electronic toElectronic(ElectronicDTO electronicDTO){
        final Electronic electronic=new Electronic();
        fillProductFields(electronic,electronicDTO);
        electronic.setCaution(electronicDTO.isCaution());
        return electronic;
    }

    public ElectronicDTO toElectronicDTO(Electronic electronic){
        final ElectronicDTO electronicDTO=new ElectronicDTO();
        fillProductDTOFields(electronicDTO,electronic);
        electronicDTO.setCaution(electronic.isCaution());
        return electronicDTO;
    }

    public Football toFootball(FootballDTO footballDTO){
        final Football football=new Football();
        fillProductFields(football,footballDTO);
        football.setScope(FootballCategory.getFootballCategoryByName(footballDTO.getScope()));
        return football;
    }

    public FootballDTO toFootballDTO(Football football){
        final FootballDTO footballDTO=new FootballDTO();
        fillProductDTOFields(footballDTO,football);
        footballDTO.setScope(football.getScope().name());
        return footballDTO;
    }

    public List<ProductSummaryDTO> toSummaryList(List<Product> productList) {
        if(productList==null)
            return new ArrayList<>();
        return productList.stream().map(this::toSummary).toList();
    }

}
