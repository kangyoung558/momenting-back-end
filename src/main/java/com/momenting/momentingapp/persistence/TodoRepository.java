package com.momenting.momentingapp.persistence;

import com.momenting.momentingapp.dto.TodoDto;
import com.momenting.momentingapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {


    List<Todo> findByUserId(String userid);
}
