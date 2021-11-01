package com.momenting.momentingapp.persistence;

import com.momenting.momentingapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    @Query("select p from Post p where p.userId=?1 order by p.id desc")
    List<Post> findByUserId(String userId);

}
