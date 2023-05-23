import { IncomingMessage, ServerResponse } from "http";
import jwt from "jsonwebtoken";
import { DecodedToken } from "../features/auth/authApi";
import { Member, setMember } from "../features/auth/authSlice";
import { AppStore } from "../features/store";

//서버
export default function authenticateMemberInPages(
  store: AppStore,
  refreshToken: string | undefined,
  res: ServerResponse<IncomingMessage>
) {
  let member: Member | null = null;
  if (refreshToken) {
    // 토큰 파싱
    const decodedToken = jwt.decode(refreshToken) as DecodedToken;
    // member 정보 저장
    member = {
      type: decodedToken.type,
      id: decodedToken.id,
      email: decodedToken.email,
      name: decodedToken.name,
      grade: decodedToken.grade,
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
