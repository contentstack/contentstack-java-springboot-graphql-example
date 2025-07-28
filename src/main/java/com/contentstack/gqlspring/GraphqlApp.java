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

import java.util.Objects;

@SpringBootApplication
@Controller
@Slf4j
@Validated
public class GraphqlApp {

    private static Contentstack contentstack;
    private static final String AllPage = "all_page";
    private static final String ABOUT = "about";
    private static final String FOOTER = "footer";
    private static final String HEADER = "header";
    private static final String HEADER_GRAPHQL = "header.graphql";
    private static final String FOOTER_GRAPHQL = "footer.graphql";
    private static final String ALL_HEADER = "all_header";
    private static final String ALL_FOOTER = "all_footer";
    private static final String BANNER = "banner";

    public static void main(String[] args) {
        contentstack = new Contentstack();
        SpringApplication.run(GraphqlApp.class, args);
    }


    @GetMapping("/")
    public String loadHomePage(Model model) {
        String headerQuery = Objects.requireNonNull(Util.load(HEADER_GRAPHQL));
        Object headerResp = contentstack.getQuery(headerQuery,
                ALL_HEADER, HeaderModel.class);

        String homeQueryString = Objects.requireNonNull(Util.load("home.graphql"));
        Object homeResp = contentstack.getQuery(homeQueryString,
                AllPage, HomeModel.class);

        String queryString = Objects.requireNonNull(Util.load(FOOTER_GRAPHQL));
        Object footerResp = contentstack.getQuery(queryString,
                ALL_FOOTER, FooterModel.class);

        model.addAttribute(ABOUT, "home");
        model.addAttribute(BANNER, "home");
        model.addAttribute(HEADER, headerResp);
        model.addAttribute("data", homeResp);
        model.addAttribute(FOOTER, footerResp);
        return "index";
    }

    @GetMapping("/about-us")
    public String aboutUs(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {


        String headerQuery = Objects.requireNonNull(Util.load(HEADER_GRAPHQL));
        Object headerResp = contentstack.getQuery(headerQuery,
                ALL_HEADER, HeaderModel.class);

        String aboutQuery = Objects.requireNonNull(Util.load("about_us.graphql"));
        Object aboutResp = contentstack.getQuery(aboutQuery,
                AllPage, HomeModel.class);

        String queryString = Objects.requireNonNull(Util.load(FOOTER_GRAPHQL));
        Object footerResp = contentstack.getQuery(queryString,
                ALL_FOOTER, FooterModel.class);


        if (headerResp == null || aboutResp == null || footerResp == null) {
            model.addAttribute("home", "Could not fetch About page..");
        } else {
            model.addAttribute(ABOUT, ABOUT);
            model.addAttribute(BANNER, "home");
            model.addAttribute(HEADER, headerResp);
            model.addAttribute("data", aboutResp);
            model.addAttribute(FOOTER, footerResp);
        }
        return "about-us";
    }

    @GetMapping("/blog")
    public String blogs(Model model) {

        String headerQuery = Objects.requireNonNull(Util.load(HEADER_GRAPHQL));
        Object headerResp = contentstack.getQuery(headerQuery,
                ALL_HEADER, HeaderModel.class);

        String blogQuery = Objects.requireNonNull(Util.load("getblogs.graphql"));
        Object blogResp = contentstack.getQuery(blogQuery,
                AllPage, BlogModel.class);


        String archivedBlogsQuery = Objects.requireNonNull(Util.load("archived.graphql"));
        Object archivedBlogsResp = contentstack.getQuery(archivedBlogsQuery,
                "all_blog_post", ArchivedModel[].class);

        String allBlogListQuery = Objects.requireNonNull(Util.load("no_archived.graphql"));
        Object allBlogListResp = contentstack.getQuery(allBlogListQuery,
                "all_blog_post", BlogListModel[].class);

        String queryString = Objects.requireNonNull(Util.load(FOOTER_GRAPHQL));
        Object footerResp = contentstack.getQuery(queryString,
                ALL_FOOTER, FooterModel.class);


        if (headerResp == null || blogResp == null || archivedBlogsResp == null || allBlogListResp == null || footerResp == null) {
            model.addAttribute("home", "Could not fetch Blog page..");
        } else {
            model.addAttribute(BANNER, "blog");
            model.addAttribute(HEADER, headerResp);
            model.addAttribute("data", blogResp);
            model.addAttribute("archived", archivedBlogsResp);
            model.addAttribute("blogList", allBlogListResp);
            model.addAttribute(FOOTER, footerResp);
        }
        return "blog";
    }


    @GetMapping("/contact-us")
    public String contact(Model model) {

        String headerQuery = Objects.requireNonNull(Util.load("header.graphql"));
        Object headerResp = contentstack.getQuery(headerQuery,
                ALL_HEADER, HeaderModel.class);

        String contactusQuery = Objects.requireNonNull(Util.load("contact_us.graphql"));
        Object contactusResp = contentstack.getQuery(contactusQuery,
                AllPage, ContactModel.class);

        String queryString = Objects.requireNonNull(Util.load(FOOTER_GRAPHQL));
        Object footerResp = contentstack.getQuery(queryString,
                ALL_FOOTER, FooterModel.class);

        if (headerResp == null || footerResp == null || contactusResp == null) {
            model.addAttribute("home", "Could not fetch Contact page..");
        } else {
            model.addAttribute(HEADER, headerResp);
            model.addAttribute("data", contactusResp);
            model.addAttribute(FOOTER, footerResp);

        }
        return "contact-us";
    }


    @GetMapping("/blog/{id}")
    public String blogPost(@PathVariable String id, Model model) {

        String headerQuery = Objects.requireNonNull(Util.load(HEADER_GRAPHQL));
        Object headerResp = contentstack.getQuery(headerQuery,
                ALL_HEADER, HeaderModel.class);

        Object blogPostResp = contentstack.blogPostById("/blog/" + id, BlogPostModel.class);

        String blogQuery = Objects.requireNonNull(Util.load("blog_query.graphql"));
        Object blogResp = contentstack.getQuery(blogQuery,
                AllPage, CustomBlogModel.class);

        String queryString = Objects.requireNonNull(Util.load(FOOTER_GRAPHQL));
        Object footerResp = contentstack.getQuery(queryString,
                ALL_FOOTER, FooterModel.class);

        if (headerResp == null || blogPostResp == null || blogResp == null || footerResp == null) {
            model.addAttribute("blog post", "Could not fetch Blog post page..");
        } else {
            model.addAttribute(BANNER, "blog");
            model.addAttribute(HEADER, headerResp);
            model.addAttribute("blogPost", blogPostResp);
            model.addAttribute("data", blogResp);
            model.addAttribute(FOOTER, footerResp);
        }
        return "blog-post";
    }


}
