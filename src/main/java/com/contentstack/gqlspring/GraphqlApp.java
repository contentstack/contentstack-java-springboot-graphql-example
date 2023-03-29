package com.contentstack.gqlspring;

import com.contentstack.gqlspring.models.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.logging.Logger;

@SpringBootApplication
@Controller
@Validated
public class GraphqlApp {

    private static Contentstack contentstack;
    private static final Logger LOGGER = Logger.getLogger(GraphqlApp.class.getName());
    private String copyrightMessage = "Copyright © 2022. LogoIpsum. All rights reserved.";

    public static void main(String[] args) {
        contentstack = new Contentstack();
        SpringApplication.run(GraphqlApp.class, args);
    }

    @GetMapping("/home")
    public String loadHomePage(Model model) {
        String headerQuery = Objects.requireNonNull(Util.load(Constant.GL_HEADER));
        Object headerResp = contentstack.getQuery(headerQuery,
                Constant.ALL_HEADER, HeaderModel.class);

        String homeQueryString = Objects.requireNonNull(Util.load(Constant.GL_HOME));
        Object homeResp = contentstack.getQuery(homeQueryString,
                Constant.ALL_PAGE, HomeModel.class);

//       String queryString = Objects.requireNonNull(Util.load(Constant.GL_FOOTER));
//
//        Object footerResp = contentstack.getQuery(queryString,
//                Constant.ALL_FOOTER, FooterModel.class);
//        FooterModel modelFooter = (FooterModel) footerResp;

        model.addAttribute(Constant.ABOUT, "home");
        model.addAttribute(Constant.BANNER, "home");
        model.addAttribute(Constant.HEADER, headerResp);
        model.addAttribute(Constant.DATA, homeResp);
        // TODO: Get Copyright here
        model.addAttribute(Constant.FOOTER, copyrightMessage);
        // String result = modelFooter.getHTML();
        //LOGGER.log(Level.INFO, modelFooter.copyright.toString());
        return "index";
    }

    @GetMapping("/about-us")
    public String aboutUs(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                          Model model) {

        String headerQuery = Objects.requireNonNull(Util.load(Constant.GL_HEADER));
        Object headerResp = contentstack.getQuery(headerQuery,
                Constant.ALL_FOOTER, HeaderModel.class);

        String aboutQuery = Objects.requireNonNull(Util.load(Constant.GL_ABOUT_US));
        Object aboutResp = contentstack.getQuery(aboutQuery,
                Constant.ALL_PAGE, HomeModel.class);

        String queryString = Objects.requireNonNull(Util.load(Constant.GL_FOOTER));
        FooterModel footerResp = (FooterModel) contentstack.getQuery(queryString,
                Constant.ALL_FOOTER, FooterModel.class);

        if (headerResp == null || aboutResp == null || footerResp == null) {
            model.addAttribute("home", "Could not fetch About page..");
        } else {
            model.addAttribute(Constant.ABOUT, "about");
            model.addAttribute(Constant.BANNER, "home");
            model.addAttribute(Constant.HEADER, headerResp);
            model.addAttribute(Constant.DATA, aboutResp);
            // TODO: put private message here
            model.addAttribute(Constant.FOOTER, copyrightMessage);

        }
        return "about-us";
    }

    @GetMapping("/blog")
    public String blogs(Model model) {

        String headerQuery = Objects.requireNonNull(Util.load(Constant.GL_HEADER));
        Object headerResp = contentstack.getQuery(headerQuery,
                Constant.ALL_HEADER, HeaderModel.class);

        String blogQuery = Objects.requireNonNull(Util.load(Constant.GL_GET_BLOCK));
        Object blogResp = contentstack.getQuery(blogQuery,
                Constant.ALL_PAGE, BlogModel.class);

        String archivedBlogsQuery = Objects.requireNonNull(Util.load(Constant.GL_ARCHIVED));
        Object archivedBlogsResp = contentstack.getQuery(archivedBlogsQuery,
                Constant.ALL_BLOG_POST, ArchivedModel[].class);

        String allBlogListQuery = Objects.requireNonNull(Util.load(Constant.GL_NO_ARCHIVED));
        Object allBlogListResp = contentstack.getQuery(allBlogListQuery,
                Constant.ALL_BLOG_POST, BlogListModel[].class);

        String queryString = Objects.requireNonNull(Util.load(Constant.GL_FOOTER));
        FooterModel footerResp = (FooterModel) contentstack.getQuery(queryString,
                Constant.ALL_FOOTER, FooterModel.class);

        if (headerResp == null || blogResp == null || archivedBlogsResp == null || allBlogListResp == null
                || footerResp == null) {
            model.addAttribute(Constant.HOME, "Could not fetch Blog page..");
        } else {
            model.addAttribute(Constant.BANNER, "blog");
            model.addAttribute(Constant.HEADER, headerResp);
            model.addAttribute(Constant.DATA, blogResp);
            model.addAttribute(Constant.ARCHIVED, archivedBlogsResp);
            model.addAttribute(Constant.BLOG_LIST, allBlogListResp);
            //TODO: Put copyright message here
            model.addAttribute(Constant.FOOTER, copyrightMessage);

        }
        return "blog";
    }

    @GetMapping("/contact-us")
    public String contact(Model model) {

        String headerQuery = Objects.requireNonNull(Util.load(Constant.GL_HEADER));
        Object headerResp = contentstack.getQuery(headerQuery,
                Constant.ALL_HEADER, HeaderModel.class);

        String contactusQuery = Objects.requireNonNull(Util.load(Constant.GL_CONTACT_US));
        Object contactusResp = contentstack.getQuery(contactusQuery,
                Constant.ALL_PAGE, ContactModel.class);

        //       String queryString = Objects.requireNonNull(Util.load(Constant.ALL_FOOTER));
//        FooterModel footerResp = (FooterModel) contentstack.getQuery(queryString,
//                Constant.ALL_FOOTER, FooterModel.class);


        model.addAttribute(Constant.HEADER, headerResp);
        model.addAttribute(Constant.DATA, contactusResp);
        //TODO: Put copyright message here
        model.addAttribute(Constant.FOOTER, copyrightMessage);

        return "contact-us";
    }

    @GetMapping("/blog/{id}")
    public String blogPost(@PathVariable String id, Model model) {

        String headerQuery = Objects.requireNonNull(Util.load(Constant.GL_HEADER));
        Object headerResp = contentstack.getQuery(headerQuery,
                Constant.ALL_HEADER, HeaderModel.class);

        Object blogPostResp = contentstack.blogPostById("/blog/" + id, BlogPostModel.class);

        String blogQuery = Objects.requireNonNull(Util.load(Constant.GL_BLOCK_QUERY));
        Object blogResp = contentstack.getQuery(blogQuery,
                Constant.ALL_PAGE, CustomBlogModel.class);

        String queryString = Objects.requireNonNull(Util.load(Constant.ALL_FOOTER));
        FooterModel footerResp = (FooterModel) contentstack.getQuery(queryString,
                Constant.ALL_FOOTER, FooterModel.class);

        if (headerResp == null || blogPostResp == null || blogResp == null || footerResp == null) {
            model.addAttribute("blog post", "Could not fetch Blog post page..");
        } else {
            model.addAttribute(Constant.BANNER, "blog");
            model.addAttribute(Constant.HEADER, headerResp);
            model.addAttribute(Constant.BLOGPOST, blogPostResp);
            model.addAttribute(Constant.DATA, blogResp);
            //TODO: put copyright message
            model.addAttribute(Constant.FOOTER, copyrightMessage);
        }
        return "blog-post";
    }


//    private FooterModel getCopyrightMsg(FooterModel footerResp){
//        if (((LinkedHashMap<?, ?>) footerResp.copyright).containsKey("json")) {
//            footerResp.copyright = ((LinkedHashMap<?, ?>) footerResp.copyright).get("json");
//        }
//        return footerResp;
//    }

}
