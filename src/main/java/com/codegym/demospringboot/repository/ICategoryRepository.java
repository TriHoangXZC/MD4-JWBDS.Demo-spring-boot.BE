package com.codegym.demospringboot.repository;

import com.codegym.demospringboot.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    @Modifying
    @Query(value = "call delete_category(?1)", nativeQuery = true)
    void deleteAllProductByCategory(Long category_id);
}
