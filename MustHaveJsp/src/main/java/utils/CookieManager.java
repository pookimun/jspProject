package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager { // 쿠키 관리자(생성, 검색, 변경)

	// 이름, 값, 유지기간 조건으로 새로운 쿠키 생성
	public static void makeCookie(HttpServletResponse response, String cName, String cValue, int cTime) {
		Cookie cookie = new Cookie(cName, cValue); // 쿠키 생성
		cookie.setPath("/"); // 경로 설정
		cookie.setMaxAge(cTime); // 유지기간 설정
		response.addCookie(cookie); // 응답 객체에 추가
	}
	
	//명시한 이름의 쿠키를 찾아 값 리턴
	public static String readCookie(HttpServletRequest request, String cName) {
		String cookieValue = ""; //리턴값
		
		Cookie[] cookies = request.getCookies(); 
		if (cookies != null) {
			for (Cookie c : cookies) {
				String cookieName = c.getName();
				if (cookieName.equals(cName)) {
					cookieValue = c.getValue();
				}
			}
		}
		return cookieValue;
	}//readCookie()

	//명시한 이름의 쿠키 삭제
	public static void deleteCookie(HttpServletResponse response, String cName) {
		makeCookie(response, cName, "", 0);
	}
}
