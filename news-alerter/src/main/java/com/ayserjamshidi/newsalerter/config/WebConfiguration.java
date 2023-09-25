package com.ayserjamshidi.newsalerter.config;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper output = new ObjectMapper();
        DefaultPrettyPrinter p = new DefaultPrettyPrinter();
        DefaultPrettyPrinter.Indenter i = new DefaultIndenter("  ", "\n");

        p.indentArraysWith(i);
        p.indentObjectsWith(i);

        output.setDefaultPrettyPrinter(p);
        return output;
    }
}
