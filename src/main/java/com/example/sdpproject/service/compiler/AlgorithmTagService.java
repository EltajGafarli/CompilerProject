package com.example.sdpproject.service.compiler;

import java.util.List;

public interface AlgorithmTagService {
    String addAlgorithmTag(String difficulty);

    List<String> getAlgorithmTags();

    String getAlgorithmTagByName(String name);

    String updateAlgorithmTag(String name, String newTagName);

    String deleteAlgorithmTag(String tag);
}
