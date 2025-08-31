package goormthonuniv.team_22_be.member.presentation;

import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.member.application.dto.MyPageResponse;
import goormthonuniv.team_22_be.member.application.dto.SignUpRequest;
import goormthonuniv.team_22_be.member.application.dto.SignUpSuccessResponse;
import goormthonuniv.team_22_be.member.application.dto.UpdateMyInfoRequest;
import goormthonuniv.team_22_be.member.application.dto.UpdateMyInfoResponse;
import goormthonuniv.team_22_be.member.presentation.docs.MemberApiDocs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController implements MemberApiDocs {

    @Override
    public ResponseEntity<ApiResult<SignUpSuccessResponse>> signupMember(SignUpRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResult<MyPageResponse>> getMyPageInfo() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResult<UpdateMyInfoResponse>> updateMyInfo(UpdateMyInfoRequest request) {
        return null;
    }
}
