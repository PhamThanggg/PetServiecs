package com.project.petService.services.util;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GeocodingService {
    @Value("${google.api.key}")
    private String apiKey;

    public double[] getCoordinates(String address) {
        try {
            String apiKey = "37c1916cae104f2cbb35803884973bc0";
            String url = "https://api.opencagedata.com/geocode/v1/json?q=" +
                    URLEncoder.encode(address, StandardCharsets.UTF_8) +
                    "&key=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            // Phân tích phản hồi JSON
            JSONObject jsonObject = new JSONObject(response);
            JSONArray results = jsonObject.getJSONArray("results");

            if (results.length() > 0) {
                JSONObject firstResult = results.getJSONObject(0);
                double lat = firstResult.getJSONObject("geometry").getDouble("lat");
                double lon = firstResult.getJSONObject("geometry").getDouble("lng");

                return new double[]{lat, lon};
            } else {
                System.out.println("Không tìm thấy kết quả cho địa chỉ này.");
            }
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
