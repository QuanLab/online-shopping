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


    public Post findBySlug(String slug) {
        return repository.findTopBySlug(slug);
    }

    /**
     *
     * @return
     */
    public Page<Post> getLast(Integer offset, Integer limit) {
        Sort sort = new Sort(
                new Sort.Order(Sort.Direction.DESC, "id")
        );
        Pageable pageable = PageRequest.of(offset, limit, sort);
        return repository.findAll(pageable);
    }


    public void save(Post post) {
        repository.save(post);
    }

}
