package goormthonuniv.team_22_be.file.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3PresignService {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region}")
    private String region;

    /** 프로필 이미지 업로드용 Pre-signed URL 발급 */
    public PresignResponse createProfileUploadUrl(Long memberId, String contentType) {
        String key = "members/%d/profile/%s".formatted(memberId, UUID.randomUUID() + extFromContentType(contentType));

        // presigner는 별도 객체
        try (S3Presigner presigner = S3Presigner.create()) {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentType)
                    // (선택) Cache-Control / ACL 등 필요시
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(3)) // 유효기간 짧게
                    .putObjectRequest(objectRequest)
                    .build();

            PresignedPutObjectRequest presigned = presigner.presignPutObject(presignRequest);
            URL url = presigned.url();

            // 실제 접근 URL (공개 버킷 아님 → 프론트는 업로드 후 이 경로를 저장만, 액세스는 CloudFront 또는 signed URL 정책 고려)
            String objectUrl = "https://%s.s3.%s.amazonaws.com/%s".formatted(bucket, region, key);

            return new PresignResponse(url.toString(), objectUrl, key);
        }
    }

    private String extFromContentType(String contentType) {
        return switch (contentType) {
            case "image/png" -> ".png";
            case "image/jpeg" -> ".jpg";
            case "image/webp" -> ".webp";
            default -> "";
        };
    }

    public record PresignResponse(String uploadUrl, String objectUrl, String key){}

}
