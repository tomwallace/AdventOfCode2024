package com.tomwallace.adventofcode2024.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;

@SpringBootApplication
@ShellComponent
public class AdventOfCode2024Application {

    public static void main(String[] args) {
        SpringApplication.run(AdventOfCode2024Application.class, args);
    }
}
