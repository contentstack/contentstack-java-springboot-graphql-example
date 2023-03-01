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

@SpringBootApplication
@Controller
@Validated
public class GraphqlApp {

    private static Contentstack contentstack;

    public static void main(String[] args) {
        contentstack = new Contentstack();
        SpringApplication.run(GraphqlApp.class, args);
    }

    @GetMapping("/")
    public String loadHomePage(Model model) {
        String headerQuery = Objects.requireNonNull(Util.load(Constant.GL_HEADER));
        Object headerResp = contentstack.getQuery(headerQuery,
                Constant.ALL_HEADER, HeaderModel.class);

        String homeQueryString = Objects.requireNonNull(Util.load(Constant.GL_HOME));
        Object homeResp = contentstack.getQuery(homeQueryString,
                Constant.ALL_PAGE, HomeModel.class);

        String queryString = Objects.requireNonNull(Util.load(Constant.GL_FOOTER));
        Object footerResp = contentstack.getQuery(queryString,
                Constant.ALL_FOOTER, FooterModel.class);

        if (footerResp instanceof FooterModel) {
//            String[] keyPath = {"json"};
//            Utils.render(((FooterModel) footerResp).copyright.get(""), keyPath, new Option() {
//                @Override
//                public String renderOptions(JSONObject embeddedObject, Metadata metadata) {
//                    return null;
//                }
//
//                @Override
//                public String renderMark(MarkType markType, String renderText) {
//                    return null;
//                }
//
//                @Override
//                public String renderNode(String nodeType, JSONObject nodeObject, NodeCallback callback) {
//                    return null;
//                }
//            });
        }
        model.addAttribute(Constant.ABOUT, "home");
        model.addAttribute(Constant.BANNER, "home");
        model.addAttribute(Constant.HEADER, headerResp);
        model.addAttribute(Constant.DATA, homeResp);
        model.addAttribute(Constant.FOOTER, footerResp);
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
        Object footerResp = contentstack.getQuery(queryString,
                Constant.ALL_FOOTER, FooterModel.class);

        if (headerResp == null || aboutResp == null || footerResp == null) {
            model.addAttribute("home", "Could not fetch About page..");
        } else {
            model.addAttribute(Constant.ABOUT, "about");
            model.addAttribute(Constant.BANNER, "home");
            model.addAttribute(Constant.HEADER, headerResp);
            model.addAttribute(Constant.DATA, aboutResp);
            model.addAttribute(Constant.FOOTER, footerResp);
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
        Object footerResp = contentstack.getQuery(queryString,
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
            model.addAttribute(Constant.FOOTER, footerResp);
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

        String queryString = Objects.requireNonNull(Util.load(Constant.ALL_FOOTER));
        Object footerResp = contentstack.getQuery(queryString,
                Constant.ALL_FOOTER, FooterModel.class);

        if (headerResp == null || footerResp == null || contactusResp == null) {
            model.addAttribute(Constant.HOME, "Could not fetch Contact page..");
        } else {
            model.addAttribute(Constant.HEADER, headerResp);
            model.addAttribute(Constant.DATA, contactusResp);
            model.addAttribute(Constant.FOOTER, footerResp);

        }
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
        Object footerResp = contentstack.getQuery(queryString,
                Constant.ALL_FOOTER, FooterModel.class);

        if (headerResp == null || blogPostResp == null || blogResp == null || footerResp == null) {
            model.addAttribute("blog post", "Could not fetch Blog post page..");
        } else {
            model.addAttribute(Constant.BANNER, "blog");
            model.addAttribute(Constant.HEADER, headerResp);
            model.addAttribute(Constant.BLOGPOST, blogPostResp);
            model.addAttribute(Constant.DATA, blogResp);
            model.addAttribute(Constant.FOOTER, footerResp);
        }
        return "blog-post";
    }

}
