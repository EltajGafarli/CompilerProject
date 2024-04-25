package com.example.sdpproject.entity.enums;

public enum ConversationTopic {
    BASIC_SYNTAX("Basic Syntax and Operators"),
    CONTROL_STRUCTURES("Control Structures (if, switch, loops)"),
    FUNCTIONS_METHODS("Functions and Methods"),
    BASIC_DATA_STRUCTURES("Basic Data Structures (Arrays, Lists)"),
    STRING_MANIPULATION("String Manipulation and Processing"),
    RECURSION("Introduction to Recursion"),
    SORTING_ALGORITHMS("Basic Sorting Algorithms"),
    SEARCHING_ALGORITHMS("Basic Searching Algorithms"),
    COMBINATORIAL_LOGIC("Combinatorial Logic (Permutations, Combinations)"),
    BASIC_GRAPH_THEORY("Introduction to Graph Theory (BFS, DFS)"),
    GREEDY_ALGORITHMS("Introduction to Greedy Algorithms"),
    DYNAMIC_PROGRAMMING_BASIC("Basic Dynamic Programming (Fibonacci, Knapsack)"),
    DATA_MANIPULATION("Data Manipulation and Analysis"),
    FILE_IO("File Input/Output Operations"),
    DEBUGGING_TECHNIQUES("Debugging Techniques and Best Practices");

    private final String displayName;

    ConversationTopic(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

