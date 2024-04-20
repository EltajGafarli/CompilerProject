package com.example.sdpproject.entity.enums;

import lombok.Getter;

@Getter
public enum ProgrammingLanguages {
    JAVA(4, "java"),
    C_LANGUAGE(5, "c"),
    CPP(5, "cpp"),
    PHP(4, "php"),
    PYTHON3(4, "python3"),
    GO(4, "go"),
    NODEJS(4, "nodejs"),
    KOTLIN(3, "kotlin"),
    RUBY(4, "ruby");

    final int versionIndex;

    final String languageCode;

    ProgrammingLanguages(int versionIndex, String languageCode) {
        this.languageCode = languageCode;
        this.versionIndex = versionIndex;
    }

}
