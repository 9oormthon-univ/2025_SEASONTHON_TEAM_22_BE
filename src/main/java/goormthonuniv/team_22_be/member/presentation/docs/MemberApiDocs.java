package goormthonuniv.team_22_be.member.presentation.docs;

import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.member.application.dto.MyPageResponse;
import goormthonuniv.team_22_be.member.application.dto.SignUpRequest;
import goormthonuniv.team_22_be.member.application.dto.SignUpSuccessResponse;
import goormthonuniv.team_22_be.member.application.dto.UpdateMyInfoRequest;
import goormthonuniv.team_22_be.member.application.dto.UpdateMyInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "사용자 정보 및 회원가입 관련 API")
@RequestMapping("/api/v1/members")
public interface MemberApiDocs {

    @Operation(
            summary = "자체 회원가입",
            description = "새로운 사용자를 등록합니다. 이메일은 중복될 수 없으며, 비밀번호는 암호화되어 저장됩니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "회원가입 성공",
                            content = @Content(schema = @Schema(implementation = SignUpSuccessResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 (유효성 검증 실패)",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "이미 존재하는 이메일",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    @PostMapping("/signup")
    ResponseEntity<ApiResult<SignUpSuccessResponse>> signupMember(
            @Valid @RequestBody SignUpRequest request
    );

    @Operation(
            summary = "마이페이지 정보 조회",
            description = "현재 로그인한 사용자의 마이페이지 정보를 조회합니다.",
            security = { @SecurityRequirement(name = "BearerAuth") },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = MyPageResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패 (JWT 토큰 없음/만료)",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    @GetMapping("/me")
    ResponseEntity<ApiResult<MyPageResponse>> getMyPageInfo(
//        TODO 회원가입, 로그인 기능 완성
//        @Parameter(hidden = true)
//        @AuthenticationPrincipal UserPrincipal authenticationPrincipal
    );

    @Operation(
            summary = "마이페이지 정보 수정",
            description = "현재 로그인한 사용자의 마이페이지 정보를 수정합니다.",
            security = { @SecurityRequirement(name = "BearerAuth") },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "수정 성공",
                            content = @Content(schema = @Schema(implementation = UpdateMyInfoResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 (유효성 검증 실패)",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패 (JWT 토큰 없음/만료)",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    @PutMapping("/me")
    ResponseEntity<ApiResult<UpdateMyInfoResponse>> updateMyInfo(
            @Valid @RequestBody UpdateMyInfoRequest request
//        TODO 회원가입, 로그인 기능 완성
//        @Parameter(hidden = true)
//        @AuthenticationPrincipal UserPrincipal authenticationPrincipal
    );
}
