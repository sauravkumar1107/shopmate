package com.shopmate.product.service;

import com.shopmate.product.model.InvocationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JavaHttpClientService {

    private final HttpClient httpClient;

    public JavaHttpClientService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMinutes(4))
                .build();
    }

    public String callApi(InvocationRequest invocationRequest) {
        try {
            // 1️⃣ Extract method and base URL
            String endpoint = invocationRequest.getEndpoint();
            if (!endpoint.contains("/")) {
                throw new IllegalArgumentException("Invalid endpoint format. Expected: METHOD/http://...");
            }

            String[] parts = endpoint.split("/", 2);
            String method = parts[0].toUpperCase();
            String baseUrl = parts[1];

            // Reconstruct baseUrl (split removed "http://")
            baseUrl = "http://" + baseUrl.substring(baseUrl.indexOf("localhost"));
//            System.out.println("BaseUrl: " + baseUrl);

            // 2️⃣ Replace path variables like {id}
            Map<String, String> params = invocationRequest.getParams();
            baseUrl = replacePathVariables(baseUrl, params);

            // 3️⃣ Append remaining params as query params
            String finalUrl = appendQueryParams(baseUrl, params);
//            System.out.println("finalUrl: " + finalUrl);

            // 4️⃣ Build request
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(finalUrl))
                    .timeout(Duration.ofMinutes(4))
                    .header("Accept", "application/json");

            HttpRequest request = switch (method) {
                case "GET" -> requestBuilder.GET().build();
                case "DELETE" -> requestBuilder.DELETE().build();
                default -> throw new UnsupportedOperationException("Unsupported HTTP method: " + method);
            };

            // 5️⃣ Execute
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != HttpStatus.OK.value()) {
                throw new RuntimeException("API call failed: " + response.statusCode() + " -> " + response.body());
            }

            return response.body();

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error calling API: " + e.getMessage(), e);
        }
    }

    // 🔹 Replace placeholders like {id} in URL
    private String replacePathVariables(String url, Map<String, String> params) {
        if (params == null || params.isEmpty()) return url;

        Pattern pattern = Pattern.compile("\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(url);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = params.get(key);
            if (value == null) {
                throw new IllegalArgumentException("Missing value for path variable: " + key);
            }
            matcher.appendReplacement(sb, URLEncoder.encode(value, StandardCharsets.UTF_8));
            params.remove(key); // Remove used key
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    // 🔹 Append leftover params as query parameters
    private String appendQueryParams(String baseUrl, Map<String, String> params) {
        if (params == null || params.isEmpty()) return baseUrl;

        StringBuilder sb = new StringBuilder(baseUrl);
        if (!baseUrl.contains("?")) sb.append("?");

        params.forEach((key, value) -> {
            sb.append(URLEncoder.encode(key, StandardCharsets.UTF_8))
                    .append("=")
                    .append(URLEncoder.encode(value, StandardCharsets.UTF_8))
                    .append("&");
        });

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
