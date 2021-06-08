package com.contentstack.gqlspring;

import com.contentstack.gqlspring.models.BlogModel;
import com.contentstack.gqlspring.models.IndexModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@SpringBootApplication
@Controller
public class MainApp {


    private static Fetcher dataFetcher;


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        dataFetcher = new Fetcher();
        SpringApplication.run(MainApp.class, args);
    }


    /**
     * Gets index.
     *
     * @param model the model
     * @return the index
     * @throws JsonProcessingException the json processing exception
     */
    @RequestMapping(value = "/")
    public String getIndex(Model model) throws JsonProcessingException {
        List<IndexModel> dataList = dataFetcher.loadIndexPage();
        model.addAttribute("index", dataList);
        return "index";
    }


    /**
     * Gets blogs.
     *
     * @param model the model
     * @return the blogs
     * @throws JsonProcessingException the json processing exception
     */
    @RequestMapping(value = "/blogs")
    public String getBlogs(Model model) throws JsonProcessingException {
        List<BlogModel> appBlogs = dataFetcher.getBlogs();
        model.addAttribute("blog", appBlogs);
        return "blog";
    }

    /**
     * Gets footer.
     *
     * @param model the model
     * @return the footer
     * @throws JsonProcessingException the json processing exception
     */
    @RequestMapping(value = "/footer")
    public String getFooter(Model model) throws JsonProcessingException {
        String appFooter = dataFetcher.getFooter();
        model.addAttribute("footer", appFooter);
        return "footer";
    }

    /**
     * Gets header.
     *
     * @param model the model
     * @return the header
     * @throws JsonProcessingException the json processing exception
     */
    @RequestMapping(value = "/header")
    public String getHeader(Model model) throws JsonProcessingException {
        String appHeader = dataFetcher.getHeader();
        model.addAttribute("header", appHeader);
        return "header";
    }

    /**
     * All pages string.
     *
     * @param model the model
     * @return the string
     * @throws JsonProcessingException the json processing exception
     */
    @RequestMapping(value = "/pages")
    public String allPages(Model model) throws JsonProcessingException {
        String appPages = dataFetcher.allPages();
        model.addAttribute("pages", appPages);
        return "pages";
    }


}
