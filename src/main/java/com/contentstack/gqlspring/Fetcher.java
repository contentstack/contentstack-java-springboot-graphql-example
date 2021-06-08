package com.contentstack.gqlspring;

import com.contentstack.gqlspring.models.BlogModel;
import com.contentstack.gqlspring.models.IndexModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Arrays;
import java.util.List;

/**
 * The type Data fetcher.
 */
public class Fetcher {


    private static String BASE_URL;
    private static String DeliverToken;

    /**
     * Instantiates a new Data fetcher.
     */
    public Fetcher() {
        loadEnvVar();
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
     * loadIndexPage will load default html page
     * as its not specifies any name in Get Mapping
     *
     * @return the loadIndexPage
     * @throws JsonProcessingException the json processing exception
     */
    public List<IndexModel> loadIndexPage() throws JsonProcessingException {
        GraphQlWrapper gqlInstance = GraphQlWrapper.Builder.newInstance()
                .setTag("all_footer")
                .setUrl(BASE_URL)
                .setQueryString("query{ all_footer { items { logoConnection { edges { node { url filename } } } navigation { link { href title } } title social { social_share { link { title href } iconConnection { edges { node { url filename } } } } } } } }")
                .setHeader(DeliverToken)
                .build();
        JsonNode response = gqlInstance.fetch().get("data").get("all_footer").get("items").get(0);
        ObjectMapper mapper = new ObjectMapper();
        List<IndexModel> indexModels = Arrays.asList(mapper.readValue(response.toString(), IndexModel[].class));
        return indexModels;
    }


    /**
     * Gets products.
     *
     * @return the products
     * @throws JsonProcessingException the json processing exception
     */
    public List<BlogModel> getBlogs() throws JsonProcessingException {
        GraphQlWrapper gqlInstance = GraphQlWrapper.Builder.newInstance()
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
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(response.toString(), BlogModel[].class));
    }


    /**
     * Gets about.
     *
     * @return the about
     * @throws JsonProcessingException the json processing exception
     */
    public String getFooter() throws JsonProcessingException {
        GraphQlWrapper gqlInstance = GraphQlWrapper.Builder.newInstance()
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
        JsonNode response = gqlInstance.fetch().get("data").get("all_footer").get("items");
        return response.toString();
    }


    /**
     * Gets banner.
     *
     * @return the banner
     * @throws JsonProcessingException the json processing exception
     */
    public String getHeader() throws JsonProcessingException {
        GraphQlWrapper gqlInstance = GraphQlWrapper.Builder.newInstance()
                .setTag("all_header")
                .setUrl(BASE_URL)
                .setQueryString("{\n" +
                        "  all_header {\n" +
                        "    items {\n" +
                        "      title\n" +
                        "      logoConnection {\n" +
                        "        edges {\n" +
                        "          node {\n" +
                        "            title\n" +
                        "            url\n" +
                        "          }\n" +
                        "        }\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }\n" +
                        "}")
                .setHeader(DeliverToken)
                .build();
        JsonNode response = gqlInstance.fetch().get("data").get("all_header").get("items");
        return response.toString();
    }


    /**
     * Gets contact.
     *
     * @return the contact
     * @throws JsonProcessingException the json processing exception
     */
    public String allPages() throws JsonProcessingException {
        GraphQlWrapper gqlInstance = GraphQlWrapper.Builder.newInstance()
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
        JsonNode response = gqlInstance.fetch().get("data").get("all_page").get("items");
        return response.toString();
    }


}
