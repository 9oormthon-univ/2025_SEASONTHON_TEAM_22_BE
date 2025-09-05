package goormthonuniv.team_22_be.file.presentation;

import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.common.security.AuthUtils;
import goormthonuniv.team_22_be.file.application.S3PresignService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {

    private final S3PresignService s3PresignService;

    @Operation(summary = "프로필 이미지 업로드용 Pre-Signed URL 발급")
    @PostMapping("/profile/presign")
    public ResponseEntity<ApiResult<S3PresignService.PresignResponse>> presignProfile(
            @RequestParam @NotBlank String contentType
    ) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();
        var res = s3PresignService.createProfileUploadUrl(memberId, contentType);
        return ResponseEntity.ok(ApiResult.ok(res));
    }

}
