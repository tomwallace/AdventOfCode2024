package com.tomwallace.adventofcode2024.java.commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;
import org.jline.utils.AttributedString;

@Configuration
@ComponentScan("com.tomwallace.adventofcode2024")
public class ShellApplicationConfiguration implements PromptProvider {

    @Value("${app.year}")
    protected Integer appYear;

    @Override
    public final AttributedString getPrompt() {
        return new AttributedString(String.format("aoc%d:>", appYear));
    }
}
