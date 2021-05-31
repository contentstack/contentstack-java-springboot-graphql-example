package com.contentstack.gqlspring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Gql test application.
 */
@SpringBootApplication
@RestController
@Slf4j
@Validated
@RequestMapping(path = "api")
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


    /**
     * Gets products.
     *
     * @return the products
     * @throws JsonProcessingException the json processing exception
     */
    @GetMapping(value = "/all_blog_post")
    public JsonNode getProducts() throws JsonProcessingException {
        GQL gqlInstance = GQL.Builder.newInstance()
                .setUrl(BASE_URL)
                .setQueryString("query{\n" +
                        "  all_blog_post {\n" +
                        "    items {\n" +
                        "      date\n" +
                        "      body\n" +
                        "      title\n" +
                        "      url\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n")
                .setHeader(DeliverToken)
                .build();
        JsonNode response = gqlInstance.fetch().get("data").get("all_blog_post").get("items");
        System.out.println(response);
        return response;
    }


    /**
     * Gets about.
     *
     * @return the about
     * @throws JsonProcessingException the json processing exception
     */
    @GetMapping(value = "/all_footer")
    public JsonNode getAbout() throws JsonProcessingException {
        GQL gqlInstance = GQL.Builder.newInstance()
                .setTag("all_footer")
                .setUrl(BASE_URL)
                .setQueryString("{\n" +
                        "  all_footer {\n" +
                        "    items {\n" +
                        "      title\n" +
                        "    }\n" +
                        "  }\n" +
                        "}")
                .setHeader(DeliverToken)
                .build();
        JsonNode response = gqlInstance.fetch();
        System.out.println(response);
        return response;
    }


    /**
     * Gets banner.
     *
     * @return the banner
     * @throws JsonProcessingException the json processing exception
     */
    @GetMapping(value = "/all_header")
    public JsonNode getBanner() throws JsonProcessingException {
        GQL gqlInstance = GQL.Builder.newInstance()
                .setTag("all_header")
                .setUrl(BASE_URL)
                .setQueryString("{\n" +
                        "  all_header {\n" +
                        "    items {\n" +
                        "      title\n" +
                        "    }\n" +
                        "  }\n" +
                        "}")
                .setHeader(DeliverToken)
                .build();
        JsonNode response = gqlInstance.fetch();
        System.out.println(response);
        return response;
    }


    /**
     * Gets contact.
     *
     * @return the contact
     * @throws JsonProcessingException the json processing exception
     */
    @GetMapping(value = "/all_page")
    public JsonNode getContact() throws JsonProcessingException {
        GQL gqlInstance = GQL.Builder.newInstance()
                .setTag("all_page")
                .setUrl(BASE_URL)
                .setQueryString("{\n" +
                        "  all_page {\n" +
                        "    items {\n" +
                        "      system {\n" +
                        "        content_type_uid\n" +
                        "        created_at\n" +
                        "        created_by\n" +
                        "        locale\n" +
                        "        tags\n" +
                        "        uid\n" +
                        "        updated_at\n" +
                        "        updated_by\n" +
                        "        version\n" +
                        "      }\n" +
                        "      title\n" +
                        "      url\n" +
                        "    }\n" +
                        "  }\n" +
                        "}")
                .setHeader(DeliverToken)
                .build();
        JsonNode response = gqlInstance.fetch();
        System.out.println(response);
        return response;
    }


}
