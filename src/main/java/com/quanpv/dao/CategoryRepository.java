package com.quanpv.dao;

import com.quanpv.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    Category findTop1BySlug(String slug);
}
