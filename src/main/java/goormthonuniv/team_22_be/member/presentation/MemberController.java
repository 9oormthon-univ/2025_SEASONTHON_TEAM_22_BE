package goormthonuniv.team_22_be.member.presentation;

import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.common.security.AuthUtils;
import goormthonuniv.team_22_be.member.application.dto.*;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.member.domain.service.MemberService;
import goormthonuniv.team_22_be.member.presentation.docs.MemberApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberApiDocs {

    private final MemberService memberService;

    /** 자체 회원가입 + 즉시 로그인(JWT 발급) */
    @Override
    public ResponseEntity<ApiResult<AuthResponse>> signupMember(SignUpRequest request) {
        AuthResponse response = memberService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.created(response));
    }

    /** 자체 로그인 */
    @Override
    public ResponseEntity<ApiResult<AuthResponse>> login(LoginRequest request) {
        AuthResponse response = memberService.login(request);
        return ResponseEntity.ok(ApiResult.ok(response));
    }

    /** 내 마이페이지 조회 */
    @Override
    public ResponseEntity<ApiResult<MyPageResponse>> getMyPageInfo() {
        Long memberId = AuthUtils.currentMemberIdOrThrow();
        Member me = memberService.getByIdOrThrow(memberId);
        // MyPageResponse 매핑 방식은 프로젝트 정의에 맞게 사용
        MyPageResponse body = MyPageResponse.from(me); // from(...) 없으면 빌더/생성자로 구성
        return ResponseEntity.ok(ApiResult.ok(body));
    }

    /** 내 정보 수정(닉네임/프로필 이미지) */
    @Override
    public ResponseEntity<ApiResult<UpdateMyInfoResponse>> updateMyInfo(UpdateMyInfoRequest request) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();
        // 서비스에서 수정 처리 후 응답 DTO 반환하도록 구현(추천)
        UpdateMyInfoResponse body = memberService.updateProfile(memberId, request);
        return ResponseEntity.ok(ApiResult.ok(body));
    }
}