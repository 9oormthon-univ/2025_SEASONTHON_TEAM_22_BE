package goormthonuniv.team_22_be.member.presentation.docs;

import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.member.application.dto.*;
import goormthonuniv.team_22_be.member.domain.model.Member;
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
            description = """
                    새로운 사용자를 등록합니다.
                    - 이메일(또는 아이디) 중복 불가
                    - 비밀번호는 서버에서 해시 저장
                    - 성공 시 회원 정보 + JWT 토큰을 반환합니다.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "회원가입 성공",
                            content = @Content(schema = @Schema(implementation = MemberResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 (유효성 검증 실패)",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "이미 존재하는 계정(이메일/아이디 중복)",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    @PostMapping("/signup")
    ResponseEntity<ApiResult<MemberResponse>> signupMember(
            @Valid @RequestBody SignUpRequest request
    );

    @Operation(
            summary = "자체 로그인",
            description = """
                    아이디/이메일 + 비밀번호로 로그인합니다.
                    - 성공 시 회원 정보 + JWT 토큰을 반환합니다.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그인 성공",
                            content = @Content(schema = @Schema(implementation = MemberResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 (유효성 검증 실패)",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패 (비밀번호 불일치 등)",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    @PostMapping("/login")
    ResponseEntity<ApiResult<MemberResponse>> login(
            @Valid @RequestBody LoginRequest request
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
    ResponseEntity<ApiResult<MyPageResponse>> getMyPageInfo();

    @Operation(
            summary = "마이페이지 정보 수정",
            description = """
                    현재 로그인한 사용자의 닉네임/프로필 이미지를 수정합니다.
                    """,
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
    ResponseEntity<ApiResult<UpdateMyInfoResponse>> updateMyInfo(@Valid @RequestBody UpdateMyInfoRequest request);
}
