import { DecodedToken } from "@/src/features/auth/authApi";
import { Member, setMember } from "@/src/features/auth/authSlice";
import { useQueryParams } from "@/src/hooks/useQueryParams";
import { setToken } from "@/src/utils/tokenManager";
import jwt from "jsonwebtoken";
import { useRouter } from "next/router";
import { useEffect } from "react";
import { useDispatch } from "react-redux";

const SECRET = process.env.NEXT_PUBLIC_JWT_SECRET
  ? process.env.NEXT_PUBLIC_JWT_SECRET
  : "";

export default function OAuthRedirect() {
  const router = useRouter();
  const dispatch = useDispatch();
  const queryParams = useQueryParams();

  useEffect(() => {
    // url에서 받은 토큰으로 로그인 처리
    const accessToken = queryParams.get("access_token");
    const refreshToken = queryParams.get("refresh_token");
    const secret = SECRET;

    // 쿠키에 토큰 집어넣고 메인 페이지로 이동
    const navigateToLogin = async () => {
      if (accessToken && refreshToken) {
        try {
          const decodedAccess = jwt.verify(accessToken, secret);
          const decodedRefresh = jwt.verify(refreshToken, secret);
          const decodedAccessToken = decodedAccess as DecodedToken;
          const decodedRefreshToken = decodedRefresh as DecodedToken;
          const accessExp = decodedAccessToken.exp;
          const refreshExp = decodedRefreshToken.exp;
          setToken("ACCESS_TOKEN", accessToken, accessExp);
          setToken("REFRESH_TOKEN", refreshToken, refreshExp);
          let member: Member;
          member = {
            type: decodedRefreshToken.type,
            id: decodedRefreshToken.id,
            email: decodedRefreshToken.email,
            name: decodedRefreshToken.name,
            grade: decodedRefreshToken.grade,
            picture_url: decodedRefreshToken.picture_url,
            gender: decodedRefreshToken.gender,
            age_group: decodedRefreshToken.age_group,
            height: decodedRefreshToken.height,
            weight: decodedRefreshToken.weight,
            mbti_id: decodedRefreshToken.mbti_id,
            personal_color_id: decodedRefreshToken.personal_color_id,
          };
          dispatch(setMember(member));
        } catch (err) {
          console.error(err);
          // 토큰 검증에 실패했을 때의 처리 로직
        }
      }
      // 멤버 로그인 정보 store에 저장 후 리플레이스
      router.replace("/", undefined, { shallow: true });
    };

    navigateToLogin();
  }, [queryParams, router]);

  return <></>;
}
