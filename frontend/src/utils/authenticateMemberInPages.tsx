import { DecodedToken } from "../features/auth/authApi";
import { Member, clearAuth, setMember } from "../features/auth/authSlice";
import jwt from "jsonwebtoken";
import { appCookies } from "../pages/_app";

export default function authenticateMemberInPages(
  store: any,
  refreshToken: string | undefined
) {
  let member: Member | null = null;
  if (refreshToken) {
    // 토큰 파싱
    const decodedToken = jwt.decode(refreshToken) as DecodedToken;
    const isExpired =
      new Date(decodedToken.exp * 1000).getTime() <= new Date().getTime();
    // refresh token이 없거나 만료된 경우(고민 좀 더..)
    if (!refreshToken || isExpired) {
      // Redux Store에서 유저 정보 삭제
      store.dispatch(clearAuth());
      // 쿠키 삭제
      appCookies.remove("ACCESS_TOKEN");
      appCookies.remove("REFRESH_TOKEN");
    } else {
      // 아니면 member 정보 저장
      member = {
        type: decodedToken.type,
        id: decodedToken.id,
        email: decodedToken.email,
        name: decodedToken.name,
        picture_url: decodedToken.picture_url,
        gender: decodedToken.gender,
        age_group: decodedToken.age_group,
        height: decodedToken.height,
        weight: decodedToken.weight,
        mbti_id: decodedToken.mbti_id,
        personal_color_id: decodedToken.personal_color_id,
      };

      // Redux store에 유저 정보 dispatch
      console.log(member);
      store.dispatch(setMember(member));
    }
  }
}
