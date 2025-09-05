package goormthonuniv.team_22_be.file.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class S3PresignService {

    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /** PUT presigned URL 발급 */
    public Presigned putUrl(String key, String contentType, Duration ttl) {
        var putReq = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                // .acl("public-read") // 퍼블릭 버킷이 아니라면 필요 없음. 공개하려면 정책/서명 방식 정해서 사용
                .build();

        var presignReq = PutObjectPresignRequest.builder()
                .signatureDuration(ttl)
                .putObjectRequest(putReq)
                .build();

        PresignedPutObjectRequest presigned = s3Presigner.presignPutObject(presignReq);
        URL url = presigned.url();
        Instant expires = Instant.now().plus(ttl);
        return new Presigned(url.toString(), expires);
    }

    public record Presigned(String url, Instant expiresAt) {}
}