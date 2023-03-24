package com.contentstack.gqlspring;

import com.contentstack.gqlspring.models.ArchivedModel;
import com.contentstack.gqlspring.models.BlogListModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Contentstack {

    private static final Logger LOGGER = Logger.getLogger(Contentstack.class.getName());
    private static String baseUrl;
    private static final String ITEMS = "items";
    private static String deliveryToken;

    // Loads everytime when new instance is created for the Contentstack class
    public Contentstack() {
        LOGGER.setLevel(Level.WARNING);

        loadEnvVar();
    }

    public static <T> T convertToObject(Class<T> clazz, String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, clazz);
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getLocalizedMessage());
            return null;
        }
    }

    // Loads credential from .env
    private static void loadEnvVar() {
        Dotenv dotenv = Dotenv.load();
        deliveryToken = dotenv.get("_EVV_DELIVERY_TOKEN");
        String apiKey = dotenv.get("_ENV_API_KEY");
        String env = dotenv.get("_ENV");
        String host = dotenv.get("_HOST");
        baseUrl = "https://" + host + "/stacks/" + apiKey + "?environment=" + env;
    }

    public Object getQuery(@NotNull String query, @NotNull String nodeBy, Class<?> cls) {

        try {
            if (query.isEmpty() || nodeBy.isEmpty()) {
                throw new IllegalArgumentException("Please provide a valid node type");
            }

            GraphqlBuilder gqlInstance = GraphqlBuilder.Builder.newInstance()
                    .setTag(nodeBy)
                    .setUrl(baseUrl).setQueryString(query)
                    .setHeader(deliveryToken).build();

            if (cls.isAssignableFrom(BlogListModel[].class) || cls.isAssignableFrom(ArchivedModel[].class)) {
                JsonNode jsonNode = gqlInstance.fetch().get("data").get(nodeBy).get(ITEMS);
                return toListObject(cls, jsonNode.toString());
            }

            JsonNode jsonNode = gqlInstance.fetch().get("data").get(nodeBy).get(ITEMS).get(0);
            if (nodeBy.equalsIgnoreCase("all_footer")) {
                LOGGER.log(Level.INFO, jsonNode.toString());
            }
            return convertToObject(cls, jsonNode.toString());

        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getLocalizedMessage());
        }
        return null;
    }

    private Object toListObject(Class<?> cls, String string) {
        try {
            return Collections.singletonList(new ObjectMapper().readValue(string, cls)).get(0);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.INFO, e.getLocalizedMessage());
        }
        return null;
    }

    public Object blogPostById(String id, Class<?> cls) {
        try {
            GraphqlBuilder graphqlBuilderInstance = GraphqlBuilder.Builder.newInstance()
                    .setTag("all_blog_post")
                    .setUrl(baseUrl)
                    .setQueryString("{\n" +
                            "  all_blog_post(where: {url:" + "\"" + id + "\"" + "}) {\n" +
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
                    .setHeader(deliveryToken).build();
            JsonNode strResponse = graphqlBuilderInstance.fetch().get("data").get("all_blog_post").get(ITEMS).get(0);
            return convertToObject(cls, strResponse.toString());
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getLocalizedMessage());
            return null;
        }
    }

}