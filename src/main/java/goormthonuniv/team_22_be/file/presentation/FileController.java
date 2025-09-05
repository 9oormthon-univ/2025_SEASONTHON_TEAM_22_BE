package goormthonuniv.team_22_be.file.presentation;

import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.common.security.AuthUtils;
import goormthonuniv.team_22_be.file.application.dto.PresignRequestDto;
import goormthonuniv.team_22_be.file.application.dto.PresignResponseDto;
import goormthonuniv.team_22_be.file.application.service.S3PresignService;
import goormthonuniv.team_22_be.file.application.service.S3UploadService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {

    private final S3PresignService s3PresignService;
    private final S3UploadService s3UploadService;


    @Operation(summary = "프로필 이미지 업로드용 Presigned URL 발급")
    @PostMapping("/profile/presign")
    public ResponseEntity<ApiResult<PresignResponseDto>> presignProfile(
            @Valid @RequestBody PresignRequestDto req
    ) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        // 확장자 결정 (filename 기준)
        String ext = extractExt(req.filename()); // e.g. "png"
        if (ext.isBlank()) {
            // filename에 확장자가 없으면 content-type으로 보정
            ext = switch (req.contentType()) {
                case "image/png"  -> "png";
                case "image/jpeg" -> "jpg";
                case "image/webp" -> "webp";
                default -> "bin";
            };
        }

        // 업로드 대상 키 구성: members/{memberId}/profile/{UUID}.{ext}
        String key = "members/%d/profile/%s.%s".formatted(memberId, UUID.randomUUID(), ext);

        var presigned = s3PresignService.putUrl(key, req.contentType(), Duration.ofMinutes(5));

        var body = new PresignResponseDto(
                presigned.url(), // PUT 업로드할 URL
                key,             // 업로드된 오브젝트 키 (DB 저장용)
                presigned.expiresAt() // 만료시각
        );
        return ResponseEntity.ok(ApiResult.ok(body));
    }

    private String extractExt(String filename) {
        if (filename == null) return "";
        int dot = filename.lastIndexOf('.');
        return (dot > -1 && dot < filename.length() - 1) ? filename.substring(dot + 1) : "";
    }

    /** 멀티파트 업로드 */
    @PostMapping(value = "/profile/upload", consumes = "multipart/form-data")
    public ResponseEntity<ApiResult<UploadResponse>> uploadProfile(@RequestPart("file") MultipartFile file) throws Exception {
        Long memberId = AuthUtils.currentMemberIdOrThrow();
        var up = s3UploadService.uploadProfileImage(memberId, file);

        return ResponseEntity.ok(ApiResult.ok(new UploadResponse(up.key(), up.url())));
    }

    public record UploadResponse(String key, String url) {}
}