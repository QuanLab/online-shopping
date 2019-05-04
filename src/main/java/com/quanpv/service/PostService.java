package com.quanpv.service;


import com.quanpv.dao.PostRepository;
import com.quanpv.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public Iterable<Post> findAll() {
        return repository.findAll();
    }

    public Iterable<Post> findAll(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return repository.findAll(pageable);
    }

    public Post findById(Integer id) {
        return repository.findById(id).orElseGet(null);
    }

    public Post findBySlug(String slug) {
        return repository.findTopBySlug(slug);
    }

    public Page<Post> getLast(Integer offset, Integer limit) {
        Sort sort = new Sort(
                new Sort.Order(Sort.Direction.DESC, "id")
        );
        Pageable pageable = PageRequest.of(offset, limit, sort);
        return repository.findAll(pageable);
    }


    public Post save(Post post) {
        return repository.save(post);
    }

}
