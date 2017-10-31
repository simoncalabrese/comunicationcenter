package comunicationcenter.controller.controller.app.controller;

import comunicationcenter.controller.controller.base.AbstractController;
import comunicationcenter.service.dto.user.LoginDTO;
import comunicationcenter.service.exceptions.BaseException;
import comunicationcenter.service.services.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by simon.calabrese on 31/10/2017.
 */
@RestController
@RequestMapping(value = "/index")
public class LoginController extends AbstractController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public void login(@RequestBody final LoginDTO loginDTO) throws BaseException {
        loginService.login(loginDTO);
    }
}
