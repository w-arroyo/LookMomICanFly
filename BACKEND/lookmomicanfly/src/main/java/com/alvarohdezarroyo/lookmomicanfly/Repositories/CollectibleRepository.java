package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.Collectible;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectibleRepository extends JpaRepository<Collectible,String> {
}
