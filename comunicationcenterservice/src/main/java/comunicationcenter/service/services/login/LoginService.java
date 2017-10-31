package comunicationcenter.service.services.login;

import comunicationcenter.service.dto.user.LoginDTO;
import comunicationcenter.service.dto.user.UserDTO;
import comunicationcenter.service.exceptions.BaseException;
import comunicationcenter.service.services.AbstractService;
import comunicationcenter.service.services.ApiEnum;
import org.springframework.stereotype.Service;

/**
 * Created by simon.calabrese on 31/10/2017.
 */
@Service
public class LoginService extends AbstractService {
    public void login(final LoginDTO loginDTO) throws BaseException {
        final UserDTO user = doPostCall(UserDTO.class, ApiEnum.LOGIN,loginDTO);
    }
}
