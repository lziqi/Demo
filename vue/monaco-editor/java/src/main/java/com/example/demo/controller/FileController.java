package com.example.demo.controller;

import com.example.demo.entity.File;
import com.example.demo.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/{name}")
    public File getFile(@PathVariable String name){
        return fileRepository.getByName(name);
    }

    @PutMapping("/{name}")
    public File putFile(@PathVariable String name,@RequestBody File file){
        return fileRepository.save(file);
    }
}
