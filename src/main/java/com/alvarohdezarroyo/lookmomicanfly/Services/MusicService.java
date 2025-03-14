package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.MusicDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Music;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.MusicRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicService {

    @Autowired
    private final MusicRepository musicRepository;

    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public MusicDTO getMusicDTOById(String id){
        return ProductMapper.toMusicDTO(musicRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Music product id does not exist.")
        ));
    }

    @Transactional
    public Music saveMusic(Music music){
        return musicRepository.save(music);
    }

}
