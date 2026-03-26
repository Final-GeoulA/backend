package kr.co.ictedu.geoulA.medicalrecord;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClovaOcrService {

    @Value("${clova.ocr.secret-key}")
    private String secretKey;

    @Value("${clova.ocr.invoke-url}")
    private String invokeUrl;

    public String callOcr(MultipartFile file) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isBlank()) {
            originalFileName = "receipt.png";
        }

        String format = "png";
        if (originalFileName.contains(".")) {
            format = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
        }

        System.out.println("=== OCR 요청 시작 ===");
        System.out.println("invokeUrl = " + invokeUrl + "/general");
        System.out.println("fileName = " + originalFileName);
        System.out.println("format = " + format);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("X-OCR-SECRET", secretKey);

        JSONObject message = new JSONObject();
        message.put("version", "V2");
        message.put("requestId", UUID.randomUUID().toString());
        message.put("timestamp", System.currentTimeMillis());

        JSONArray images = new JSONArray();
        JSONObject image = new JSONObject();
        image.put("format", format);
        image.put("name", "demo");
        // image.put("type", "RECEIPT");
        images.put(image);

        message.put("images", images);

        System.out.println("message = " + message.toString());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("message", message.toString());

        // 파일 추가
        final String finalFileName = originalFileName;
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return finalFileName;
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);

        // OCR API 호출
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    invokeUrl + "/general",
                    requestEntity,
                    String.class
            );

            System.out.println("=== OCR 응답 성공 ===");
            System.out.println(response.getBody());

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }
    
    // OCR 결과 (병원명, 날짜, 금액 추출)
    public OcrParsedResult parseOcrResult(String ocrJson) {
        OcrParsedResult result = new OcrParsedResult();

        JSONObject root = new JSONObject(ocrJson);
        JSONArray images = root.optJSONArray("images");

        StringBuilder rawTextBuilder = new StringBuilder();

        if (images != null && images.length() > 0) {
            JSONObject firstImage = images.getJSONObject(0);
            JSONArray fields = firstImage.optJSONArray("fields");

            if (fields != null) {
                for (int i = 0; i < fields.length(); i++) {
                    JSONObject field = fields.getJSONObject(i);
                    String inferText = field.optString("inferText", "").trim();

                    if (!inferText.isEmpty()) {
                        rawTextBuilder.append(inferText).append("\n");
                    }
                }
            }
        }

        // 전체 OCR 텍스트
        String rawText = rawTextBuilder.toString().trim();
        result.setRawText(rawText);

        // 각각 값 추출 (병원명, 날짜, 진료비)
        result.setHospitalName(extractHospitalName(rawText));
        result.setPaymentDate(extractPaymentDate(rawText));
        result.setPrice(extractPrice(rawText));

        return result;
    }

    // ==== 병원명 추출 ====
    private String extractHospitalName(String rawText) {
        if (rawText == null || rawText.isBlank()) {
            return "";
        }

        String[] lines = rawText.split("\\r?\\n");

        for (String line : lines) {
            String trimmed = line.trim();

            // 의미 없는 줄 제거
            if (trimmed.isEmpty()) continue;
            if (trimmed.contains("영수증")) continue;
            if (trimmed.contains("카드")) continue;
            if (trimmed.contains("합계")) continue;
            if (trimmed.contains("금액")) continue;
            if (trimmed.matches(".*\\d{4}.*")) continue;

            // 병원 관련 키워드 포함 시 반환
            if (trimmed.contains("병원") || trimmed.contains("피부과") || trimmed.contains("의원") || trimmed.contains("클리닉") || trimmed.contains("이비인후과")) {
                return trimmed;
            }
        }

        return lines.length > 0 ? lines[0].trim() : "";
    }

    // ==== 날짜 추출 ====
    private String extractPaymentDate(String rawText) {
        if (rawText == null || rawText.isBlank()) {
            return "";
        }

        Pattern pattern = Pattern.compile("(\\d{4})[.\\-/년\\s]+(\\d{1,2})[.\\-/월\\s]+(\\d{1,2})");
        Matcher matcher = pattern.matcher(rawText);

        if (matcher.find()) {
            String year = matcher.group(1);
            String month = String.format("%02d", Integer.parseInt(matcher.group(2)));
            String day = String.format("%02d", Integer.parseInt(matcher.group(3)));
            return year + "-" + month + "-" + day;
        }

        return "";
    }

    // ==== 가격 추출 ====
    private Integer extractPrice(String rawText) {
        if (rawText == null || rawText.isBlank()) {
            return 0;
        }

        String[] lines = rawText.split("\\r?\\n");

        int maxPrice = 0;

        for (String line : lines) {
            String trimmed = line.trim();

            if (trimmed.isEmpty()) continue;

            // 번호/날짜 관련 줄은 통째로 제외
            if (trimmed.contains("사업자등록번호")) continue;
            if (trimmed.contains("전화번호")) continue;
            if (trimmed.contains("대표번호")) continue;
            if (trimmed.contains("승인번호")) continue;
            if (trimmed.contains("카드번호")) continue;
            if (trimmed.contains("날짜")) continue;

            // 하이픈 포함 줄은 번호일 가능성이 높아서 제외
            if (trimmed.contains("-")) continue;

            // 1순위: 합계/총액/금액/결제금액 같은 줄
            if (trimmed.contains("합계") || trimmed.contains("총액") || trimmed.contains("금액") || trimmed.contains("결제")) {
                Matcher strongMatcher = Pattern.compile("([0-9]{1,3}(?:,[0-9]{3})+|[0-9]{4,})").matcher(trimmed);
                while (strongMatcher.find()) {
                    String num = strongMatcher.group(1).replace(",", "");
                    try {
                        return Integer.parseInt(num);
                    } catch (Exception e) {
                    }
                }
            }

            // 2순위: '원' 붙은 숫자
            Matcher wonMatcher = Pattern.compile("([0-9]{1,3}(?:,[0-9]{3})+|[0-9]{4,})\\s*원").matcher(trimmed);
            while (wonMatcher.find()) {
                String num = wonMatcher.group(1).replace(",", "");
                try {
                    int value = Integer.parseInt(num);
                    if (value > maxPrice) {
                        maxPrice = value;
                    }
                } catch (Exception e) {
                }
            }

            // 3순위: 일반 숫자 중 큰 값
            Matcher normalMatcher = Pattern.compile("([0-9]{1,3}(?:,[0-9]{3})+|[0-9]{4,})").matcher(trimmed);
            while (normalMatcher.find()) {
                String num = normalMatcher.group(1).replace(",", "");
                try {
                    int value = Integer.parseInt(num);
                    if (value > maxPrice) {
                        maxPrice = value;
                    }
                } catch (Exception e) {
                }
            }
        }

        return maxPrice;
    }
    
}