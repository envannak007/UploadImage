package com.api.cluod365.repository;

import com.api.cluod365.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity,Integer> {

}
