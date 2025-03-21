package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.*;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Format;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Material;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Season;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import org.mapstruct.Mapper;

@Mapper
public class ProductMapper {

    public static void fillProductDTOFields(ProductDTO productDTO, Product product){
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setActive(product.getActive());
        productDTO.setManufacturer(product.getManufacturer().getName());
        productDTO.setCategory(product.getCategory().name());
        productDTO.setSubcategory(product.getSubcategory().name());
        productDTO.setReleaseYear(product.getReleaseYear());
        productDTO.setColorList(product.getColors());
    }

    public static void fillProductFields(Product product, ProductDTO productDTO){
        product.setName(productDTO.getName());
        product.setActive(true);
        product.setReleaseYear(productDTO.getReleaseYear());
        product.setCategory(ProductValidator.checkIfProductCategoryExists(productDTO.getCategory()));
        product.setSubcategory(ProductValidator.checkIfProductSubcategoryExists(productDTO.getSubcategory()));
        ProductValidator.checkIfSubcategoryBelongsToACategory(product.getCategory(),product.getSubcategory());
    }

    public static ProductSummaryDTO toSummary(Product product){
        final ProductSummaryDTO productSummaryDTO=new ProductSummaryDTO();
        productSummaryDTO.setId(product.getId());
        productSummaryDTO.setName(product.getName());
        productSummaryDTO.setYear(product.getReleaseYear());
        productSummaryDTO.setManufacturer(product.getManufacturer().getName());
        return productSummaryDTO;
    }

    public static SneakersDTO toSneakersDTO(Sneakers sneakers){
        final SneakersDTO sneakersDTO=new SneakersDTO();
        fillProductDTOFields(sneakersDTO,sneakers);
        sneakersDTO.setSku(sneakers.getSku());
        return sneakersDTO;
    }

    public static Sneakers toSneakers(SneakersDTO sneakersDTO){
        final Sneakers sneakers=new Sneakers();
        fillProductFields(sneakers,sneakersDTO);
        sneakers.setSku(sneakersDTO.getSku());
        return sneakers;
    }

    public static ClothingDTO toClothingDTO(Clothing clothing){
        final ClothingDTO clothingDTO=new ClothingDTO();
        fillProductDTOFields(clothingDTO,clothing);
        clothingDTO.setSeason(clothing.getSeason().name());
        return clothingDTO;
    }

    public static Clothing toClothing(ClothingDTO clothingDTO){
        Clothing clothing=new Clothing();
        fillProductFields(clothing,clothingDTO);
        clothing.setSeason(Season.getSeasonFromName(clothingDTO.getSeason()));
        return clothing;
    }

    public static AccessoryDTO toAccessoryDTO(Accessory accessory){
        final AccessoryDTO accessoryDTO=new AccessoryDTO();
        fillProductDTOFields(accessoryDTO, accessory);
        accessoryDTO.setMaterial(accessory.getMaterial().name());
        return accessoryDTO;
    }

    public static Accessory toAccessory(AccessoryDTO accessoryDTO){
        final Accessory accessory =new Accessory();
        fillProductFields(accessory,accessoryDTO);
        accessory.setMaterial(Material.getMaterialFromName(accessoryDTO.getMaterial()));
        return accessory;
    }

    public static CollectibleDTO toCollectibleDTO(Collectible collectible){
        final CollectibleDTO collectibleDTO=new CollectibleDTO();
        fillProductDTOFields(collectibleDTO,collectible);
        collectibleDTO.setCollectionName(collectible.getCollectionName());
        return collectibleDTO;
    }

    public static Collectible toCollectible(CollectibleDTO collectibleDTO){
        Collectible collectible=new Collectible();
        fillProductFields(collectible,collectibleDTO);
        collectible.setCollectionName(collectibleDTO.getCollectionName());
        return collectible;
    }

    public static SkateboardDTO toSkateboardDTO(Skateboard skateboard){
        final SkateboardDTO skateboardDTO=new SkateboardDTO();
        fillProductDTOFields(skateboardDTO,skateboard);
        skateboardDTO.setLength(skateboard.getLength());
        return skateboardDTO;
    }

    public static Skateboard toSkateboard(SkateboardDTO skateboardDTO){
        Skateboard skateboard=new Skateboard();
        fillProductFields(skateboard,skateboardDTO);
        skateboard.setLength(skateboardDTO.getLength());
        return skateboard;
    }

    public static MusicDTO toMusicDTO(Music music){
        final MusicDTO musicDTO=new MusicDTO();
        fillProductDTOFields(musicDTO,music);
        musicDTO.setFormat(music.getFormat().name());
        return musicDTO;
    }

    public static Music toMusic(MusicDTO musicDTO){
        final Music music=new Music();
        fillProductFields(music,musicDTO);
        music.setFormat(Format.getFormatByName(musicDTO.getFormat()));
        return music;
    }

}
