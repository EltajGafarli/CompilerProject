package com.example.sdpproject.controller.compiler;

import com.example.sdpproject.service.compiler.AlgorithmTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/algorithmtag")
@RequiredArgsConstructor
public class AlgorithmTagController {
    private final AlgorithmTagService algorithmTagService;

    @PostMapping
    public ResponseEntity<String> addDifficulty(@RequestParam String tag) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        algorithmTagService.addAlgorithmTag(tag)
                );
    }

    @GetMapping
    public ResponseEntity<List<String>> getAlgorithmTags() {
        return ResponseEntity
                .ok(
                        algorithmTagService.getAlgorithmTags()
                );
    }

    @GetMapping(path = "/{name}")
    public ResponseEntity<String> getAlgorithmTagByName(@PathVariable String name) {
        return ResponseEntity
                .ok(
                        algorithmTagService.getAlgorithmTagByName(name)
                );
    }


    @PutMapping(path = "/update")
    public ResponseEntity<String> updatedAlgorithmTag(@RequestParam String tag, @RequestBody String newTagName) {
        return ResponseEntity
                .ok(
                        algorithmTagService.updateAlgorithmTag(tag, newTagName)
                );
    }


    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteAlgorithmTag(@RequestParam String tag) {
        return ResponseEntity
                .ok(
                        this.algorithmTagService.deleteAlgorithmTag(tag)
                );
    }

}
