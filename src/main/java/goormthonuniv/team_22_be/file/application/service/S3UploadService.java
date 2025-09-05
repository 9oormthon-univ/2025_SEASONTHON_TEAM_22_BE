package goormthonuniv.team_22_be.file.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public Uploaded uploadProfileImage(Long memberId, MultipartFile file) throws IOException {
        // 간단한 검증
        if (file.isEmpty()) throw new IllegalArgumentException("빈 파일입니다.");
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
        }

        // 파일명/키 생성
        String ext = extFromContentType(contentType);
        String key = "members/%d/profile/%s%s".formatted(memberId, UUID.randomUUID(), ext);

        PutObjectRequest putReq = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                // 공개 버킷이 아니라면 ACL은 생략. 공개로 테스트하려면 다음 라인 주석 해제
                //.acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3Client.putObject(putReq, RequestBody.fromBytes(file.getBytes()));

        // 접근 URL (퍼블릭 버킷이 아니면 이 URL로는 바로 보기 어려움)
        String objectUrl = "https://%s.s3.amazonaws.com/%s".formatted(bucket, key);
        return new Uploaded(key, objectUrl);
    }

    private String extFromContentType(String ct) {
        return switch (ct) {
            case "image/png"  -> ".png";
            case "image/jpeg" -> ".jpg";
            case "image/webp" -> ".webp";
            default -> "";
        };
    }

    public record Uploaded(String key, String url) {}
}