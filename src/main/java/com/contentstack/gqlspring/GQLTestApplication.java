package com.contentstack.gqlspring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Slf4j
@Validated
@RequestMapping(path = "api")
public class GQLTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GQLTestApplication.class, args);
    }


    @GetMapping(value = "/product")
    public JsonNode getProducts() throws JsonProcessingException {
        GQL gqlInstance = GQL.Builder.newInstance()
                .setUrl("https://graphql.contentstack.com/stacks/***REMOVED***?environment=production")
                .setQueryString("{ all_product { items { title description } } }")
                .setHeader("***REMOVED***")
                .build();
        JsonNode response = gqlInstance.fetch().get("data").get("all_product").get("items");;
        System.out.println(response);
        return response;
    }


    @GetMapping(value = "/about")
    public JsonNode getAbout() throws JsonProcessingException {
        GQL gqlInstance = GQL.Builder.newInstance()
                .setTag("about")
                .setUrl("https://graphql.contentstack.com/stacks/***REMOVED***?environment=development")
                .setQueryString("query {\n" +
                        "  home(uid: \"***REMOVED***\") {\n" +
                        "    about {\n" +
                        "      title\n" +
                        "      left_description\n" +
                        "      right_description\n" +
                        "    }\n" +
                        "  }\n" +
                        "}")
                .setHeader("***REMOVED***")
                .build();
        JsonNode response = gqlInstance.fetch();
        System.out.println(response);
        return response;
    }


    @GetMapping(value = "/banner")
    public JsonNode getBanner() throws JsonProcessingException {
        GQL gqlInstance = GQL.Builder.newInstance()
                .setTag("banner")
                .setUrl("https://graphql.contentstack.com/stacks/***REMOVED***?environment=development")
                .setQueryString("query {\n" +
                        "  home(uid: \"***REMOVED***\") {\n" +
                        "    banner {\n" +
                        "      description\n" +
                        "      title\n" +
                        "      imageConnection {\n" +
                        "        edges {\n" +
                        "          node {\n" +
                        "            url\n" +
                        "          }\n" +
                        "        }\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n")
                .setHeader("***REMOVED***")
                .build();
        JsonNode response = gqlInstance.fetch();
        System.out.println(response);
        return response;
    }


    @GetMapping(value = "/contact")
    public JsonNode getContact() throws JsonProcessingException {
        GQL gqlInstance = GQL.Builder.newInstance()
                .setTag("banner")
                .setUrl("https://graphql.contentstack.com/stacks/***REMOVED***?environment=development")
                .setQueryString("query {\n" +
                        "  home(uid: \"***REMOVED***\") {\n" +
                        "    contact {\n" +
                        "      address\n" +
                        "      email\n" +
                        "      phone_number\n" +
                        "      title\n" +
                        "    }\n" +
                        "  }\n" +
                        "}")
                .setHeader("***REMOVED***")
                .build();
        JsonNode response = gqlInstance.fetch();
        System.out.println(response);
        return response;
    }


}
