package AppLinkers.BingX.admin.controller;

import AppLinkers.BingX.admin.dto.UpdateUserLoginPwReq;
import AppLinkers.BingX.admin.dto.UserLoginReq;
import AppLinkers.BingX.admin.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String loginPage(Model model) {

        model.addAttribute("userLoginReq", new UserLoginReq());
        return "common/login";
    }

    /**
     * 로그 아웃 서비스
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login";
    }

    /**
     * 비밀번호 변경 페이지
     */
    @GetMapping("/admin/changePW/form")
    public String changePWPage(UpdateUserLoginPwReq updateUserLoginPwReq) {
        return "admin/password_change";
    }

    /**
     * 비밀번호 수정 서비스
     */
    @PutMapping("/admin/changePW")
    public String changePW(UpdateUserLoginPwReq updateUserLoginPwReq) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        authService.changeUserLoginPw(updateUserLoginPwReq, userLoginId);

        return "redirect:/admin/announce";
    }

}
