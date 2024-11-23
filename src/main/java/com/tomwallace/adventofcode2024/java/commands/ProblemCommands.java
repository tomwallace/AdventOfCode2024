package com.tomwallace.adventofcode2024.java.commands;

import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@ShellComponent
public class ProblemCommands {

    @Value("${app.year}")
    protected Integer appYear;

    @ShellMethod(key = "info")
    public String info() {
        return String.format("This project is a Java implementation of the %d Advent of Code puzzles, written by Eric Wastl.  The basic concept is that the author presents two problems a day from December 1 until December 25, for a total of 50 problems (an advent calendar of code problems).  The problems are self-contained with the second problem of each day being based on the first problem, with a twist.  The problems are appropriate for a variety of developer skill levels and are rather witty and fun.  You can learn more about Advent of Code here:\n", appYear) +
                "\n" +
                String.format("http://adventofcode.com/%d/about\n", appYear) +
                "\n" +
                "You can find more code from me on my Github page:\n" +
                "\n" +
                "https://github.com/tomwallace";
    }

    @ShellMethod(key = {"list", "l"})
    public String list() {
        var builder = new StringBuilder();
        builder.append(String.format("AdventOfCode" + appYear + "has the following problem sets:"));
        builder.append(System.lineSeparator());
        builder.append("Class Name - Description");
        builder.append(System.lineSeparator());
        builder.append("-----------------------------------------------------");
        builder.append(System.lineSeparator());

        for (IAdventProblemSet problemSet : getAdventProblemSets()) {
            builder.append(String.format("%s - %s", problemSet.getClass().getSimpleName(), problemSet.description()));
            builder.append(System.lineSeparator());
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    @ShellMethod(key = {"run", "r"})
    public String run(String problemName, String part) {
        var problemInstance = getAdventProblemSetByName(problemName);
        if (problemInstance == null) {
            return "Unrecognized problemName: " + problemName;
        }

        if (part.equals("PartA")) {
            return problemInstance.partA();
        } else if (part.equals("PartB")) {
            return problemInstance.partB();
        }

        return "Unrecognized run part: " + part;
    }

    private List<IAdventProblemSet> getAdventProblemSets() {
        // Get the classes that implement IAdventProblemSet
        BeanDefinitionRegistry bdr = new SimpleBeanDefinitionRegistry();
        ClassPathBeanDefinitionScanner s = new ClassPathBeanDefinitionScanner(bdr);
        s.setIncludeAnnotationConfig(false);

        TypeFilter tf = new AssignableTypeFilter(IAdventProblemSet.class);
        s.addIncludeFilter(tf);
        s.scan("com.tomwallace.adventofcode2024.java.problems.solutions");

        String[] beans = bdr.getBeanDefinitionNames();

        return Arrays.stream(beans)
                .map(bdr::getBeanDefinition)
                .map(bd -> {
                    try {
                        var clazz = Class.forName(bd.getBeanClassName());
                        var adventProblemSet = clazz.newInstance();
                        return (IAdventProblemSet) adventProblemSet;
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sorted(Comparator.comparing(IAdventProblemSet::sortOrder))
                .toList();
    }

    private IAdventProblemSet getAdventProblemSetByName(String problemName) {
        List<IAdventProblemSet> problemSets = getAdventProblemSets();
        var problemSet = problemSets.stream()
                .filter(p -> p.getClass().getSimpleName().equals(problemName))
                .findFirst();

        return problemSet.orElse(null);
    }
}
