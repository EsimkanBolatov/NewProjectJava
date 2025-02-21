package com.nerzon.course.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nerzon.course.DTO.CatDTO;
import com.nerzon.course.entity.Cat;
import com.nerzon.course.repository.CatRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "main_methods")
@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {
    private final CatRepo catRepo;
    private final ObjectMapper objectMapper;

    @Operation(
            summary = "кладет нового котика на базу",
            description = "Получает DTO кота и билдером собирает и сохраняет сущность в базу"
    )

    @PostMapping("/api/add")
    public void addCat(@RequestBody CatDTO catDTO){
        log.info(
                "New row: " + catRepo.save(
                        Cat.builder()
                            .age(catDTO.getAge())
                            .weight(catDTO.getWeight())
                            .name(catDTO.getName())
                            .build())
        );
    }

    @SneakyThrows
    @GetMapping("/api/all")
    public List<Cat> getAll(){
        return catRepo.findAll();
    }

    @GetMapping("/api")
    public Cat getCat(@RequestParam int id){
        return catRepo.findById(id).orElseThrow();
    }

    @DeleteMapping("/api")
    public void deleteCat(@RequestParam int id){
        catRepo.deleteById(id);
    }

    @PutMapping("/api/add")
    public String changeCat(@RequestBody Cat cat){
        if (!catRepo.existsById(cat.getId())){
            return "No such row";
        }
        return catRepo.save(cat).toString();
    }


}
