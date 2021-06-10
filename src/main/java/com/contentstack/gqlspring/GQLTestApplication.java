package com.contentstack.gqlspring;

import com.contentstack.gqlspring.models.AboutModel;
import com.contentstack.gqlspring.models.ArchivedModel;
import com.contentstack.gqlspring.models.BlogListModel;
import com.contentstack.gqlspring.models.BlogModel;
import com.contentstack.gqlspring.models.BlogPostModel;
import com.contentstack.gqlspring.models.ContactModel;
import com.contentstack.gqlspring.models.CustomBlogModel;
import com.contentstack.gqlspring.models.FooterModel;
import com.contentstack.gqlspring.models.HeaderModel;
import com.contentstack.gqlspring.models.HomeModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The type Gql test application.
 */
@SpringBootApplication
@Controller
@Slf4j
@Validated
// @RequestMapping(path = "/")
public class GQLTestApplication {

        private static String BASE_URL;
        private static String DeliverToken;

        /**
         * The entry point of application.
         *
         * @param args the input arguments
         */
        public static void main(String[] args) {
                loadEnvVar();
                SpringApplication.run(GQLTestApplication.class, args);
        }

        // load credentials from from .env
        private static void loadEnvVar() {
                Dotenv dotenv = Dotenv.load();
                DeliverToken = dotenv.get("_EVV_DELIVERY_TOKEN");
                String _API_KEY = dotenv.get("_ENV_API_KEY");
                String _ENV = dotenv.get("_ENV");
                BASE_URL = "https://graphql.contentstack.com/stacks/" + _API_KEY + "?environment=" + _ENV;
        }

        @GetMapping("/")
        public String home(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                        Model model) throws JsonProcessingException {
                JsonNode headerResp = getHeader();
                JsonNode homeResp = getHome();
                JsonNode footerResp = getFooter();
                ObjectMapper mapperHeader = new ObjectMapper();
                ObjectMapper mapper = new ObjectMapper();
                ObjectMapper mapperFooter = new ObjectMapper();

                if (homeResp == null || headerResp == null || footerResp == null) {
                        model.addAttribute("home", "Could not fetch Home page..");
                } else {
                        model.addAttribute("about", "home");
                        model.addAttribute("banner", "home");
                        model.addAttribute("header", Arrays
                                        .asList(mapperHeader.readValue(headerResp.toString(), HeaderModel[].class))
                                        .get(0));
                        model.addAttribute("data",
                                        Arrays.asList(mapper.readValue(homeResp.toString(), HomeModel[].class)).get(0));
                        model.addAttribute("footer", Arrays
                                        .asList(mapperFooter.readValue(footerResp.toString(), FooterModel[].class))
                                        .get(0));
                }
                return "index";
        }

        @GetMapping("/about-us")
        public String aboutUs(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                        Model model) throws JsonProcessingException {

                JsonNode headerResp = getHeader();
                JsonNode aboutResp = getAboutUs();
                JsonNode footerResp = getFooter();
                ObjectMapper mapperHeader = new ObjectMapper();
                ObjectMapper mapper = new ObjectMapper();
                ObjectMapper mapperFooter = new ObjectMapper();

                if (aboutResp == null || headerResp == null || footerResp == null) {
                        model.addAttribute("home", "Could not fetch About page..");
                } else {
                        model.addAttribute("about", "about");
                        model.addAttribute("banner", "home");
                        model.addAttribute("header", Arrays
                                        .asList(mapperHeader.readValue(headerResp.toString(), HeaderModel[].class))
                                        .get(0));
                        model.addAttribute("data", Arrays
                                        .asList(mapper.readValue(aboutResp.toString(), AboutModel[].class)).get(0));
                        model.addAttribute("footer", Arrays
                                        .asList(mapperFooter.readValue(footerResp.toString(), FooterModel[].class))
                                        .get(0));
                }
                return "about-us";
        }

        @GetMapping("/blog")
        public String blogs(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                        Model model) throws JsonMappingException, JsonProcessingException {

                JsonNode headerResp = getHeader();
                JsonNode blogResp = getBlogs();
                JsonNode archivedBlogs = getArchivedBlog();
                JsonNode blogList = getBlogList();
                JsonNode footerResp = getFooter();
                ObjectMapper mapperHeader = new ObjectMapper();
                ObjectMapper mapper = new ObjectMapper();
                ObjectMapper mapperArchive = new ObjectMapper();
                ObjectMapper mapperBlogList = new ObjectMapper();
                ObjectMapper mapperFooter = new ObjectMapper();

                if (blogResp == null || headerResp == null || footerResp == null) {
                        model.addAttribute("home", "Could not fetch Blog page..");
                } else {
                        model.addAttribute("banner", "blog");
                        model.addAttribute("archived",Arrays
                        .asList(mapperArchive.readValue(archivedBlogs.toString(), ArchivedModel[].class)));
                        model.addAttribute("blogList",Arrays
                        .asList(mapperBlogList.readValue(blogList.toString(), BlogListModel[].class)));
                        model.addAttribute("header", Arrays
                                        .asList(mapperHeader.readValue(headerResp.toString(), HeaderModel[].class))
                                        .get(0));
                        model.addAttribute("data",
                                        Arrays.asList(mapper.readValue(blogResp.toString(), BlogModel[].class)).get(0));
                        model.addAttribute("footer", Arrays
                                        .asList(mapperFooter.readValue(footerResp.toString(), FooterModel[].class))
                                        .get(0));
                }
                return "blog";
        }

        @GetMapping("/contact-us")
        public String contact(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                        Model model) throws JsonMappingException, JsonProcessingException {

                JsonNode headerResp = getHeader();
                JsonNode contactResp = getContactUs();
                JsonNode footerResp = getFooter();
                ObjectMapper mapperHeader = new ObjectMapper();
                ObjectMapper mapper = new ObjectMapper();
                ObjectMapper mapperFooter = new ObjectMapper();

                if (headerResp == null || footerResp == null) {
                        model.addAttribute("home", "Could not fetch Contact page..");
                } else {
                        model.addAttribute("header", Arrays
                                        .asList(mapperHeader.readValue(headerResp.toString(), HeaderModel[].class))
                                        .get(0));
                        model.addAttribute("data", Arrays
                                        .asList(mapper.readValue(contactResp.toString(), ContactModel[].class)).get(0));
                        model.addAttribute("footer", Arrays
                                        .asList(mapperFooter.readValue(footerResp.toString(), FooterModel[].class))
                                        .get(0));
                }
                return "contact-us";
        }

        @GetMapping("/blog/{id}")
        public String blogPost(@RequestParam(name = "name", required = false, defaultValue = "World") String name, @PathVariable String id, Model model) throws JsonMappingException, JsonProcessingException {
                
                JsonNode headerResp = getHeader();
                JsonNode blogPost = getBlogPost("/blog/" + id);
                JsonNode blogQuery = getBlogQuery();
                JsonNode footerResp = getFooter();
                ObjectMapper mapperHeader = new ObjectMapper();
                ObjectMapper mapper = new ObjectMapper();
                ObjectMapper queryMapper = new ObjectMapper();
                ObjectMapper mapperFooter = new ObjectMapper();

                if (blogPost== null || headerResp == null || footerResp == null || blogQuery == null) {
                        model.addAttribute("blog post", "Could not fetch Blog post page..");
                } else {
                        model.addAttribute("banner", "blog");
                        model.addAttribute("data",
                        Arrays.asList(queryMapper.readValue(blogQuery.toString(), CustomBlogModel[].class)).get(0));
                        model.addAttribute("header", Arrays
                                        .asList(mapperHeader.readValue(headerResp.toString(), HeaderModel[].class))
                                        .get(0));
                        model.addAttribute("blogPost", Arrays
                                        .asList(mapper.readValue(blogPost.toString(), BlogPostModel[].class)).get(0));
                        model.addAttribute("footer", Arrays
                                        .asList(mapperFooter.readValue(footerResp.toString(), FooterModel[].class))
                                        .get(0));
                }
                return"blog-post";
        }

        /**
         * Gets header.
         *
         * @return the header
         * @throws JsonProcessingException the json processing exception
         */
        // @GetMapping(value = "/header")
        public JsonNode getHeader() {
                try {
                        GQL gqlInstance = GQL.Builder.newInstance().setTag("all_header").setUrl(BASE_URL)
                                        .setQueryString("{all_header {\n" + "    items {\n" + "      logoConnection {\n"
                                                        + "        edges {\n" + "          node {\n"
                                                        + "            filename\n" + "            title\n"
                                                        + "            url\n" + "          }\n" + "        }\n"
                                                        + "      }\n" + "      navigation_menu {\n" + "        label\n"
                                                        + "        page_referenceConnection {\n" + "          edges {\n"
                                                        + "            node {\n" + "              ... on Page {\n"
                                                        + "                title\n" + "                url\n"
                                                        + "              }\n" + "            }\n" + "          }\n"
                                                        + "        }\n" + "      }\n" + "      notification_bar {\n"
                                                        + "        announcement_text\n" + "        show_announcement\n"
                                                        + "      }\n" + "      title\n" + "    }\n" + "  }}")
                                        .setHeader(DeliverToken).build();
                        JsonNode header = gqlInstance.fetch().get("data").get("all_header").get("items");
                        return header;
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        return null;
                }
        }

        /**
         * Gets footer.
         *
         * @return the footer
         * @throws JsonProcessingException the json processing exception
         */
        // @GetMapping(value = "/footer")
        public JsonNode getFooter() {
                try {
                        GQL gqlInstance = GQL.Builder.newInstance().setTag("all_footer").setUrl(BASE_URL)
                                        .setQueryString("{\n" +
                                                "  all_footer {\n" +
                                                "    items {\n" +
                                                "      copyright\n" +
                                                "      title\n" +
                                                "      social {\n" +
                                                "        social_share {\n" +
                                                "          iconConnection {\n" +
                                                "            edges {\n" +
                                                "              node {\n" +
                                                "                url\n" +
                                                "              }\n" +
                                                "            }\n" +
                                                "          }\n" +
                                                "          link {\n" +
                                                "            title\n" +
                                                "            href\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "      navigation {\n" +
                                                "        link {\n" +
                                                "          title\n" +
                                                "          href\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "      logoConnection {\n" +
                                                "        edges {\n" +
                                                "          node {\n" +
                                                "            url\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}")
                                        .setHeader(DeliverToken).build();
                        JsonNode footer = gqlInstance.fetch().get("data").get("all_footer").get("items");
                        return footer;
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        return null;
                }
        }

        /**
         * Gets Home.
         *
         * @return the home
         * @throws JsonProcessingException the json processing exception
         */

        private JsonNode getHome() {
                try {
                        GQL gqlInstance = GQL.Builder.newInstance().setTag("all_page").setUrl(BASE_URL)
                                        .setQueryString("{\n" +
                                                "  all_page(where: {url: \"/\"}) {\n" +
                                                "    items {\n" +
                                                "      title\n" +
                                                "      page_components {\n" +
                                                "        ... on PagePageComponentsFromBlog {\n" +
                                                "          __typename\n" +
                                                "          from_blog {\n" +
                                                "            title_h2\n" +
                                                "            view_articles {\n" +
                                                "              title\n" +
                                                "              href\n" +
                                                "            }\n" +
                                                "            featured_blogsConnection {\n" +
                                                "              edges {\n" +
                                                "                node {\n" +
                                                "                  ... on BlogPost {\n" +
                                                "                    title\n" +
                                                "                    featured_imageConnection {\n" +
                                                "                      edges {\n" +
                                                "                        node {\n" +
                                                "                          url\n" +
                                                "                        }\n" +
                                                "                      }\n" +
                                                "                    }\n" +
                                                "                    body\n" +
                                                "                    url\n" +
                                                "                    date\n" +
                                                "                  }\n" +
                                                "                }\n" +
                                                "              }\n" +
                                                "            }\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "        ... on PagePageComponentsHeroBanner {\n" +
                                                "          __typename\n" +
                                                "          hero_banner {\n" +
                                                "            banner_description\n" +
                                                "            banner_title\n" +
                                                "            bg_color\n" +
                                                "            call_to_action {\n" +
                                                "              href\n" +
                                                "              title\n" +
                                                "            }\n" +
                                                "            banner_imageConnection {\n" +
                                                "              edges {\n" +
                                                "                node {\n" +
                                                "                  url\n" +
                                                "                }\n" +
                                                "              }\n" +
                                                "            }\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "        ... on PagePageComponentsSection {\n" +
                                                "          __typename\n" +
                                                "          section {\n" +
                                                "            imageConnection {\n" +
                                                "              edges {\n" +
                                                "                node {\n" +
                                                "                  url\n" +
                                                "                }\n" +
                                                "              }\n" +
                                                "            }\n" +
                                                "            title_h2\n" +
                                                "            description\n" +
                                                "            call_to_action {\n" +
                                                "              href\n" +
                                                "              title\n" +
                                                "            }\n" +
                                                "            image_alignment\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "        ... on PagePageComponentsSectionWithBuckets {\n" +
                                                "          __typename\n" +
                                                "          section_with_buckets {\n" +
                                                "            title_h2\n" +
                                                "            buckets {\n" +
                                                "              description\n" +
                                                "              title_h3\n" +
                                                "              call_to_action {\n" +
                                                "                href\n" +
                                                "                title\n" +
                                                "              }\n" +
                                                "              iconConnection {\n" +
                                                "                edges {\n" +
                                                "                  node {\n" +
                                                "                    url\n" +
                                                "                  }\n" +
                                                "                }\n" +
                                                "              }\n" +
                                                "            }\n" +
                                                "            description\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "        ... on PagePageComponentsSectionWithCards {\n" +
                                                "          __typename\n" +
                                                "          section_with_cards {\n" +
                                                "            cards {\n" +
                                                "              title_h3\n" +
                                                "              description\n" +
                                                "              call_to_action {\n" +
                                                "                href\n" +
                                                "                title\n" +
                                                "              }\n" +
                                                "            }\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}\n")
                                        .setHeader(DeliverToken).build();
                        JsonNode home = gqlInstance.fetch().get("data").get("all_page").get("items");
                        return home;
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        return null;
                }
        }

        /**
         * Gets banner.
         *
         * @return the banner
         * @throws JsonProcessingException the json processing exception
         */

        private JsonNode getAboutUs() {
                try {
                        GQL gqlInstance = GQL.Builder.newInstance().setTag("all_page").setUrl(BASE_URL)
                                        .setQueryString("{all_page(where: { url: \"/about-us\" }) {\n" + "    items {\n"
                                                        + "      title\n" + "      url\n" + "      seo {\n"
                                                        + "        enable_search_indexing\n" + "        keywords\n"
                                                        + "        meta_description\n" + "        meta_title\n"
                                                        + "      }\n" + "      page_components {\n"
                                                        + "        ... on PagePageComponentsHeroBanner {\n"
                                                        + "          __typename\n" + "          hero_banner {\n"
                                                        + "            banner_description\n"
                                                        + "            banner_title\n" + "            bg_color\n"
                                                        + "            call_to_action {\n" + "              href\n"
                                                        + "              title\n" + "            }\n"
                                                        + "            banner_imageConnection {\n"
                                                        + "              edges {\n" + "                node {\n"
                                                        + "                  title\n" + "                  url\n"
                                                        + "                  filename\n" + "                }\n"
                                                        + "              }\n" + "            }\n" + "          }\n"
                                                        + "        }\n"
                                                        + "        ... on PagePageComponentsSectionWithBuckets {\n"
                                                        + "          __typename\n"
                                                        + "          section_with_buckets {\n"
                                                        + "            title_h2\n" + "            description\n"
                                                        + "            buckets {\n" + "              title_h3\n"
                                                        + "              description\n"
                                                        + "              call_to_action {\n" + "                href\n"
                                                        + "                title\n" + "              }\n"
                                                        + "              iconConnection {\n"
                                                        + "                edges {\n" + "                  node {\n"
                                                        + "                    url\n" + "                    title\n"
                                                        + "                    filename\n" + "                  }\n"
                                                        + "                }\n" + "              }\n"
                                                        + "            }\n" + "          }\n" + "        }\n"
                                                        + "        ... on PagePageComponentsSection {\n"
                                                        + "          __typename\n" + "          section {\n"
                                                        + "            title_h2\n" + "            image_alignment\n"
                                                        + "            description\n" + "            call_to_action {\n"
                                                        + "              href\n" + "              title\n"
                                                        + "            }\n" + "            imageConnection {\n"
                                                        + "              edges {\n" + "                node {\n"
                                                        + "                  title\n" + "                  url\n"
                                                        + "                  filename\n" + "                }\n"
                                                        + "              }\n" + "            }\n" + "          }\n"
                                                        + "        }\n" + "        ... on PagePageComponentsOurTeam {\n"
                                                        + "          __typename\n" + "          our_team {\n"
                                                        + "            title_h2\n" + "            description\n"
                                                        + "            employees {\n" + "              name\n"
                                                        + "              designation\n"
                                                        + "              imageConnection {\n"
                                                        + "                edges {\n" + "                  node {\n"
                                                        + "                    title\n" + "                    url\n"
                                                        + "                    filename\n" + "                  }\n"
                                                        + "                }\n" + "              }\n"
                                                        + "            }\n" + "          }\n" + "        }\n"
                                                        + "      }\n" + "    }\n" + "  }}")
                                        .setHeader(DeliverToken).build();
                        JsonNode aboutUs = gqlInstance.fetch().get("data").get("all_page").get("items");
                        return aboutUs;
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        return null;
                }
        }

        /**
         * Gets contact.
         *
         * @return the contact
         * @throws JsonProcessingException the json processing exception
         */

        private JsonNode getContactUs() {
                try {
                        GQL gqlInstance = GQL.Builder.newInstance().setTag("all_page").setUrl(BASE_URL)
                                        .setQueryString("{\n" + "  all_page(where: {url: \"/contact-us\"}) {\n"
                                                        + "    items {\n" + "      title\n" + "      url\n"
                                                        + "      seo {\n" + "        meta_title\n"
                                                        + "        meta_description\n" + "        keywords\n"
                                                        + "        enable_search_indexing\n" + "      }\n"
                                                        + "      page_components {\n"
                                                        + "        ... on PagePageComponentsSectionWithHtmlCode {\n"
                                                        + "          __typename\n"
                                                        + "          section_with_html_code {\n"
                                                        + "            description\n" + "            html_code\n"
                                                        + "            html_code_alignment\n" + "            title\n"
                                                        + "          }\n" + "        }\n" + "      }\n" + "    }\n"
                                                        + "  }\n" + "}")
                                        .setHeader(DeliverToken).build();
                        JsonNode contactUs = gqlInstance.fetch().get("data").get("all_page").get("items");
                        return contactUs;
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        return null;
                }
        }

        // /**
        // * Gets blog.
        // *
        // * @return the blog
        // * @throws JsonProcessingException the json processing exception
        // */

        private JsonNode getBlogs() {
                try {
                        GQL gqlInstance = GQL.Builder.newInstance().setTag("all_page").setUrl(BASE_URL)
                                        .setQueryString("{\n" + "  all_page(where: { url: \"/blog\" }) {\n"
                                                        + "    items {\n" + "      title\n" + "      url\n"
                                                        + "      seo {\n" + "        enable_search_indexing\n"
                                                        + "        keywords\n" + "        meta_description\n"
                                                        + "        meta_title\n" + "      }\n"
                                                        + "      page_components {\n"
                                                        + "        ... on PagePageComponentsHeroBanner {\n"
                                                        + "          __typename\n" + "          hero_banner {\n"
                                                        + "            banner_description\n"
                                                        + "            banner_title\n" + "            bg_color\n"
                                                        + "            call_to_action {\n" + "              href\n"
                                                        + "              title\n" + "            }\n"
                                                        + "            banner_imageConnection {\n"
                                                        + "              edges {\n" + "                node {\n"
                                                        + "                  title\n" + "                  url\n"
                                                        + "                  filename\n" + "                }\n"
                                                        + "              }\n" + "            }\n" + "          }\n"
                                                        + "        }\n" + "        ... on PagePageComponentsWidget {\n"
                                                        + "          __typename\n" + "          widget {\n"
                                                        + "            title_h2\n" + "            type\n"
                                                        + "          }\n" + "        }\n" + "      }\n" + "    }\n"
                                                        + "  }\n" + "}")
                                        .setHeader(DeliverToken).build();
                        JsonNode blog = gqlInstance.fetch().get("data").get("all_page").get("items");
                        return blog;
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        return null;
                }
        }

        private JsonNode getBlogQuery() {
                try {
                        GQL gqlInstance = GQL.Builder.newInstance().setTag("all_page").setUrl(BASE_URL)
                                        .setQueryString("{\n" +
                                                "  all_page(where: {url: \"/blog\"}) {\n" +
                                                "    items {\n" +
                                                "      title\n" +
                                                "      page_components {\n" +
                                                "        ... on PagePageComponentsWidget {\n" +
                                                "          __typename\n" +
                                                "          widget {\n" +
                                                "            title_h2\n" +
                                                "            type\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "        ... on PagePageComponentsHeroBanner {\n" +
                                                "          __typename\n" +
                                                "          hero_banner {\n" +
                                                "            banner_description\n" +
                                                "            banner_title\n" +
                                                "            bg_color\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}")
                                        .setHeader(DeliverToken).build();
                        JsonNode blog = gqlInstance.fetch().get("data").get("all_page").get("items");
                        return blog;
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        return null;
                }
        }

        /**
         * Gets blog post.
         *
         * @return the blog pot
         * @throws JsonProcessingException the json processing exception
         */
        private JsonNode getBlogPost(String id) {
                try {
                        GQL gqlInstance = GQL.Builder.newInstance().setTag("all_blog_post").setUrl(BASE_URL)
                                        .setQueryString("{\n" +
                                                "  all_blog_post(where: {url:"+ "\""+ id + "\"" +"}) {\n" +
                                                "    items {\n" +
                                                "      title\n" +
                                                "      url\n" +
                                                "      body\n" +
                                                "      date\n" +
                                                "      seo {\n" +
                                                "        enable_search_indexing\n" +
                                                "        keywords\n" +
                                                "        meta_description\n" +
                                                "        meta_title\n" +
                                                "      }\n" +
                                                "      related_postConnection {\n" +
                                                "        edges {\n" +
                                                "          node {\n" +
                                                "            ... on BlogPost {\n" +
                                                "              title\n" +
                                                "              url\n" +
                                                "              body\n" +
                                                "              authorConnection {\n" +
                                                "                edges {\n" +
                                                "                  node {\n" +
                                                "                    ... on Author {\n" +
                                                "                      title\n" +
                                                "                    }\n" +
                                                "                  }\n" +
                                                "                }\n" +
                                                "              }\n" +
                                                "            }\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "      authorConnection {\n" +
                                                "        edges {\n" +
                                                "          node {\n" +
                                                "            ... on Author {\n" +
                                                "              title\n" +
                                                "            }\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}")
                                        .setHeader(DeliverToken).build();
                        JsonNode blogPost = gqlInstance.fetch().get("data").get("all_blog_post").get("items");
                        return blogPost;
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        return null;
                }
        }
        private JsonNode getArchivedBlog() {
                try {
                        GQL gqlInstance = GQL.Builder.newInstance().setTag("all_blog_post").setUrl(BASE_URL)
                                        .setQueryString("{\n" +
                                                "  all_blog_post(where: {is_archived: true}) {\n" +
                                                "    items {\n" +
                                                "      title\n" +
                                                "      url\n" +
                                                "      body\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}")
                                        .setHeader(DeliverToken).build();
                        JsonNode archivedPost = gqlInstance.fetch().get("data").get("all_blog_post").get("items");
                        return archivedPost;
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        return null;
                }
        }
        private JsonNode getBlogList() {
                try {
                        GQL gqlInstance = GQL.Builder.newInstance().setTag("all_blog_post").setUrl(BASE_URL)
                                        .setQueryString("{\n" +
                                                "  all_blog_post(where: {is_archived: false}) {\n" +
                                                "    items {\n" +
                                                "      title\n" +
                                                "      url\n" +
                                                "      body\n" +
                                                "      date\n" +
                                                "      authorConnection {\n" +
                                                "        edges {\n" +
                                                "          node {\n" +
                                                "            ... on Author {\n" +
                                                "              title\n" +
                                                "            }\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "      featured_imageConnection {\n" +
                                                "        edges {\n" +
                                                "          node {\n" +
                                                "            url\n" +
                                                "          }\n" +
                                                "        }\n" +
                                                "      }\n" +
                                                "    }\n" +
                                                "  }\n" +
                                                "}")
                                        .setHeader(DeliverToken).build();
                        JsonNode blogList = gqlInstance.fetch().get("data").get("all_blog_post").get("items");
                        return blogList;
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        return null;
                }
        }
}
