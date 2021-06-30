package com.contentstack.gqlspring;

import com.contentstack.gqlspring.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Objects;

@SpringBootApplication
@Controller
@Slf4j
@Validated
public class GraphqlApp {

    private static Contentstack contentstack;

    public static void main(String[] args) {
        contentstack = new Contentstack();
        SpringApplication.run(GraphqlApp.class, args);
    }


    @GetMapping("/")
    public String loadHomePage(Model model) {
        String headerQuery = Objects.requireNonNull(Util.load("header.graphql"));
        Object headerResp = contentstack.getQuery(headerQuery,
                "all_header", HeaderModel.class);

        String homeQueryString = Objects.requireNonNull(Util.load("home.graphql"));
        Object homeResp = contentstack.getQuery(homeQueryString,
                "all_page", HomeModel.class);

        String queryString = Objects.requireNonNull(Util.load("footer.graphql"));
        Object footerResp = contentstack.getQuery(queryString,
                "all_footer", FooterModel.class);

        model.addAttribute("about", "home");
        model.addAttribute("banner", "home");
        model.addAttribute("header", headerResp);
        model.addAttribute("data", homeResp);
        model.addAttribute("footer", footerResp);
        return "index";
    }

    @GetMapping("/about-us")
    public String aboutUs(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {


        String headerQuery = Objects.requireNonNull(Util.load("header.graphql"));
        Object headerResp = contentstack.getQuery(headerQuery,
                "all_header", HeaderModel.class);

        String aboutQuery = Objects.requireNonNull(Util.load("about_us.graphql"));
        Object aboutResp = contentstack.getQuery(aboutQuery,
                "all_page", HomeModel.class);

        String queryString = Objects.requireNonNull(Util.load("footer.graphql"));
        Object footerResp = contentstack.getQuery(queryString,
                "all_footer", FooterModel.class);


        if (headerResp == null || aboutResp == null || footerResp == null) {
            model.addAttribute("home", "Could not fetch About page..");
        } else {
            model.addAttribute("about", "about");
            model.addAttribute("banner", "home");
            model.addAttribute("header", headerResp);
            model.addAttribute("data", aboutResp);
            model.addAttribute("footer", footerResp);
        }
        return "about-us";
    }

    @GetMapping("/blog")
    public String blogs(Model model) {

        String headerQuery = Objects.requireNonNull(Util.load("header.graphql"));
        Object headerResp = contentstack.getQuery(headerQuery,
                "all_header", HeaderModel.class);

        String blogQuery = Objects.requireNonNull(Util.load("getblogs.graphql"));
        Object blogResp = contentstack.getQuery(blogQuery,
                "all_page", BlogModel.class);


        String archivedBlogsQuery = Objects.requireNonNull(Util.load("archived.graphql"));
        Object archivedBlogsResp = contentstack.getQuery(archivedBlogsQuery,
                "all_blog_post", ArchivedModel[].class); // TODO: inList it

        String allBlogListQuery = Objects.requireNonNull(Util.load("no_archived.graphql"));
        Object allBlogListResp = contentstack.getQuery(allBlogListQuery,
                "all_blog_post", BlogListModel[].class); // TODO: inList it

        String queryString = Objects.requireNonNull(Util.load("footer.graphql"));
        Object footerResp = contentstack.getQuery(queryString,
                "all_footer", FooterModel.class);


        if (headerResp == null || blogResp == null || archivedBlogsResp == null || allBlogListResp == null || footerResp == null) {
            model.addAttribute("home", "Could not fetch Blog page..");
        } else {
            model.addAttribute("banner", "blog");
            model.addAttribute("standardDate", new Date());

            model.addAttribute("header", headerResp);
            model.addAttribute("data", blogResp);
            model.addAttribute("archived", archivedBlogsResp);
            model.addAttribute("blogList", allBlogListResp);
            model.addAttribute("footer", footerResp);
        }
        return "blog";
    }


    @GetMapping("/contact-us")
    public String contact(Model model) {

        String headerQuery = Objects.requireNonNull(Util.load("header.graphql"));
        Object headerResp = contentstack.getQuery(headerQuery,
                "all_header", HeaderModel.class);

        String contactusQuery = Objects.requireNonNull(Util.load("contact_us.graphql"));
        Object contactusResp = contentstack.getQuery(contactusQuery,
                "all_page", ContactModel.class);

        String queryString = Objects.requireNonNull(Util.load("footer.graphql"));
        Object footerResp = contentstack.getQuery(queryString,
                "all_footer", FooterModel.class);

        if (headerResp == null || footerResp == null | contactusResp == null) {
            model.addAttribute("home", "Could not fetch Contact page..");
        } else {
            model.addAttribute("header", headerResp);
            model.addAttribute("data", contactusResp);
            model.addAttribute("footer", footerResp);

        }
        return "contact-us";
    }


    @GetMapping("/blog/{id}")
    public String blogPost(@PathVariable String id, Model model) {

        String headerQuery = Objects.requireNonNull(Util.load("header.graphql"));
        Object headerResp = contentstack.getQuery(headerQuery,
                "all_header", HeaderModel.class);

        Object blogPostResp = contentstack.blogPostById("/blog/" + id, BlogPostModel.class);

        String blogQuery = Objects.requireNonNull(Util.load("blog_query.graphql"));
        Object blogResp = contentstack.getQuery(blogQuery,
                "all_page", CustomBlogModel.class);

        String queryString = Objects.requireNonNull(Util.load("footer.graphql"));
        Object footerResp = contentstack.getQuery(queryString,
                "all_footer", FooterModel.class);

        if (headerResp == null || blogPostResp == null || blogResp == null || footerResp == null) {
            model.addAttribute("blog post", "Could not fetch Blog post page..");
        } else {
            model.addAttribute("banner", "blog");
            model.addAttribute("standardDate", new Date());

            model.addAttribute("header", headerResp);
            model.addAttribute("blogPost", blogPostResp);
            model.addAttribute("data", blogResp);
            model.addAttribute("footer", footerResp);
        }
        return "blog-post";
    }


}
