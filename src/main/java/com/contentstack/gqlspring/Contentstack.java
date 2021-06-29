package com.contentstack.gqlspring;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;

public class Contentstack {


    private static String BASE_URL;
    private static String deliverToken;

    // Loads everytime when new instance is created for the Contentstack class
    public Contentstack() {
        loadEnvVar();
    }

    public static <T> T convertToObject(Class<T> clazz, String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (T) mapper.readValue(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Loads credential from from .env
    private void loadEnvVar() {
        Dotenv dotenv = Dotenv.load();
        deliverToken = dotenv.get("_EVV_DELIVERY_TOKEN");
        String _API_KEY = dotenv.get("_ENV_API_KEY");
        String _ENV = dotenv.get("_ENV");
        String _HOST = dotenv.get("_HOST");
        BASE_URL = "https://" + _HOST + "/stacks/" + _API_KEY + "?environment=" + _ENV;
    }

    public Object getQuery(@NotNull String query, @NotNull String nodeBy, Class<?> cls) {

        try {
            if (query.isEmpty() || nodeBy.isEmpty()) {
                throw new Exception("Please provide a valid node type");
            }

            GraphqlBuilder gqlInstance = GraphqlBuilder.Builder.newInstance()
                    .setTag(nodeBy)
                    .setUrl(BASE_URL)
                    .setQueryString(query)
                    .setHeader(deliverToken)
                    .build();

            JsonNode jsonNode = gqlInstance.fetch().get("data").get(nodeBy).get("items").get(0);
            //String jsonFile = new ObjectMapper().writeValueAsString(jsonNode);
            return convertToObject(cls, jsonNode.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//    private Object convertToObject(String string, Class<?> cls) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            Object typeObj = mapper.readValue(string, cls);
//            return typeObj;
//            //return Arrays.asList(new ObjectMapper().readValue(string, cls)).get(0);
//            //return Collections.singletonList(new ObjectMapper().readValue(string, cls)).get(0);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public Object getBlogPost(String id, Class<?> cls) {
        try {
            GraphqlBuilder graphqlBuilderInstance = GraphqlBuilder.Builder.newInstance()
                    .setTag("all_blog_post")
                    .setUrl(BASE_URL)
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
                    .setHeader(deliverToken).build();
            JsonNode strResponse = graphqlBuilderInstance.fetch().get("data").get("all_blog_post").get("items");
            return convertToObject(cls, strResponse.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


}