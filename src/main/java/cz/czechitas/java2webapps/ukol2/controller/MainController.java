package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Controller
public class MainController {
    private final Random random;
    //quotes from citaty.txt saved in this List
    private final List<String> listOfQuotes;

    public MainController() throws IOException {
        random = new Random();
        listOfQuotes = readAllLines("citaty.txt");
    }
    @GetMapping("/")
    public ModelAndView vyberObrazekACitat() {
        ModelAndView result = new ModelAndView("index");
        int citatCislo = random.nextInt(8);
        int obrazekCislo = random.nextInt(10);

        result.addObject("citat", listOfQuotes.get(citatCislo));
        result.addObject("obrazek", String.format("/images/%d.jpg", obrazekCislo));
        return result;
    }
    private static List<String> readAllLines(String resource) throws IOException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(resource);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }
}
