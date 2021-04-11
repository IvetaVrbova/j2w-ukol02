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


    public MainController()throws IOException {
        random = new Random();
        listOfQuotes = readAllLines("citaty.txt");
    }


    @GetMapping("/")

    public ModelAndView vyberObrazekACitat(){
        ModelAndView result = new ModelAndView("citat");
        // random number of the quote from 0 to 7 - there are 8 quotes in total but the indices of the list are from 0 to 7
        int citatCislo = random.nextInt(8);
        // random number from 0 to 9 - those are the names of the image files
        int obrazekCislo = random.nextInt(10);

        result.addObject("citat", listOfQuotes.get(citatCislo));
        result.addObject("obrazek", String.format("/images/%d.jpg", obrazekCislo));

        return result;
    }
    private static List<String> readAllLines(String resource)throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InputStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try(InputStream inputStream=classLoader.getResourceAsStream(resource);
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }
}
//    public ModelAndView nactiCitat() {
//        List<String> seznamCitatu = Arrays.asList("The Classic Mystery Game where you are the detective, the victim, and the murderer.",
//                "A user interface is like a joke. If you have to explain it, it's not that good.",
//                "To replace programmers with robots, clients will have to accurately describe what they want. We're safe.",
//                "I have a joke on programming but it only works on my computer.",
//                "99 little bugs in the code, 99 bugs in the code. Take one down, patch it around. 127 little bugs in the code…",
//                "When I wrote this code, only God & I understood what it did. Now… Only God knows.",
//                "Programmer (noun.): A machine that turns coffee into code",
//                "Real programmers count from 0.");
////
////        int index = random.nextInt(seznamCitatu.size());
//////        int index2 = random.nextInt(seznamObrazku.size());
//        int nahodneCislo = random.nextInt(9)+1;
//        ModelAndView result = new ModelAndView("index");
//
//        result.addObject("citat");
//        result.addObject("obrazek", String.format("/images/obr%d.jpg",nahodneCislo));
//        return result;
//    }
//}
