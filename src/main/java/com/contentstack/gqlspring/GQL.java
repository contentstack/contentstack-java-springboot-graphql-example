package com.contentstack.gqlspring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Gql.
 */
final class GQL {

    private final int id;
    private final String tag;
    private final String[] nodeSplitter;
    private final String url;
    private final JSONObject queryJson;
    private final HttpHeaders headers;

    /**
     * Instantiates a new Gql.
     *
     * @param builder the builder
     */
    public GQL(Builder builder) {
        this.id = builder.id;
        this.tag = builder.tag;
        this.nodeSplitter = builder.nodeSplitter;
        this.url = builder.url;
        this.queryJson = builder.queryJson;
        this.headers = builder.headers;
    }

    @Override
    public String toString() {
        return "GQL{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", nodeSplitter=" + Arrays.toString(nodeSplitter) +
                ", url='" + url + '\'' +
                ", queryJson=" + queryJson +
                ", headers=" + headers +
                '}';
    }

    /**
     * Fetch json node.
     *
     * @return the json node
     * @throws JsonProcessingException the json processing exception
     */
    public JsonNode fetch() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(queryJson, this.headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode == HttpStatus.OK) {
            String dataString = responseEntity.getBody();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(dataString);
        } else {
            String dataString = responseEntity.getBody();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(dataString);
        }
    }

    /**
     * Model it.
     *
     * @param <T>            the type parameter
     * @param responseString the response string
     */
    public <T> void modelIt(String responseString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(responseString);
            JsonNode items = actualObj.get("data").get("all_product").get("items");
            List<Product> listProduct = mapper.readValue(items.toString(),
                    new TypeReference<List<Product>>() {
                    });
            System.out.println("listProduct => " + listProduct);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * The type Builder.
     */
    public static class Builder {

        private int id;
        private String tag;
        private String url;
        private JSONObject queryJson = new JSONObject();
        private HttpHeaders headers = new HttpHeaders();
        private String[] nodeSplitter = {};

        private Builder() {
        }

        /**
         * New instance builder.
         *
         * @return the builder
         */
        public static Builder newInstance() {
            return new Builder();
        }

        /**
         * Sets id.
         *
         * @param id the id
         * @return the id
         */
        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        /**
         * Sets tag.
         *
         * @param tag the tag
         * @return the tag
         */
        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        /**
         * Sets node.
         *
         * @param node the node
         * @return the node
         */
        public Builder setNode(String node) {
            if (node != null && !node.isEmpty()) {
                this.nodeSplitter = node.split(":");
            }
            List<String> targetList = Arrays.asList(this.nodeSplitter);
            for (String nodeIndex : targetList) {
                System.out.println("nodes: => " + nodeIndex);
            }
            return this;
        }

        /**
         * Sets url.
         *
         * @param url the url
         * @return the url
         */
        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * Sets query string.
         *
         * @param queryString the query string
         * @return the query string
         */
        public Builder setQueryString(String queryString) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.appendField("query", queryString);
            this.queryJson = jsonObject;
            return this;
        }

        /**
         * Sets header.
         *
         * @param access_token the access token
         * @return the header
         */
        public Builder setHeader(String access_token) {
            final HttpHeaders headers = new HttpHeaders();
            ArrayList<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            headers.setAccept(acceptableMediaTypes);
            headers.add("access_token", access_token);
            this.headers = headers;
            return this;
        }

        /**
         * Build gql.
         *
         * @return the gql
         */
        public GQL build() {
            // Do some validation part if required values are not provided
            // if (url.isEmpty()){
            // throw new Exception("Can not work without url");
            // }
            return new GQL(this);
        }

    }

}
