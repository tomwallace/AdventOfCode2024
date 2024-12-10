package com.tomwallace.adventofcode2024.java.problems;

/***
 * Interface that defines the Advent of Code problem sets
 */
public interface IAdventProblemSet {

    /***
     * Solves Part A of the ProblemSet and provides the answer back as a string
     * @return - The answer for Part A
     */
    String partA();

    /***
     * Solves Part B of the ProblemSet and provides the answer back as a string
     * @return - The answer for Part B
     */
    String partB();

    /***
     * Description of the ProblemSet, which is used in the CLI list command to provide context.  Add [HARD] at end to signify the ProblemSets struggled with most.
     * @return - The ProblemSet description
     */
    String description();

    /***
     * The sort order of the ProblemSet, used to display the problems in the correct order with the CLI list command
     * @return - The ProblemSet sort order
     */
    Integer sortOrder();

    // TODO: Add values for difficulty - enum: EASY, MEDIUM, HARD; and Boolean for favorite - update the description javadoc
    // TODO: Display that information in the list function
}
