package com.quanpv.dao;

import com.quanpv.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    Page<Post> findAll(Pageable pageable);

    Post findTopBySlug(String  slug);

}
