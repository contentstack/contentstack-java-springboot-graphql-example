package com.contentstack.gqlspring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The type Gql.
 */
final class GQLWrapper {

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
    public GQLWrapper(Builder builder) {
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
        HttpEntity<JSONObject> entity = new HttpEntity<>(queryJson, this.headers);
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
     * The type Builder.
     */
    public static class Builder {

        private int id;
        private String tag;
        private String url;
        private JSONObject queryJson = new JSONObject();
        private HttpHeaders headers = new HttpHeaders();
        private final String[] nodeSplitter = {};

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
            ArrayList<MediaType> acceptableMediaTypes = new ArrayList<>();
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
        public GQLWrapper build() {
            return new GQLWrapper(this);
        }

    }

}
