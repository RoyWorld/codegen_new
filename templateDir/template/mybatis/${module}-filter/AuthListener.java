package ${basepackage}.filter;

import cn.thinkjoy.common.domain.UserDomain;
import cn.thinkjoy.common.utils.UserContext;
import com.qtone.open.api.uic.dto.AccountDto;
import com.qtone.open.ucm.BaseRequest;
import com.qtone.open.ucm.client.IQTOAuthListener;

/**
 * Created by wdong on 15/7/31.
 */
public class AuthListener implements IQTOAuthListener {
    @Override
    public void authenticatornSuccess(AccountDto accountDto) {
        UserDomain<String> userDomain = new UserDomain<>();
        if(accountDto != null) {
            userDomain.setId(accountDto.getUid());
            userDomain.setName(accountDto.getName());
            UserContext.setCurrentUser(userDomain);
        }
    }

    @Override
    public void authenticatornError(BaseRequest baseRequest, RuntimeException ex) {

    }

    @Override
    public void logout(AccountDto currentUser) {
        UserContext.removeCurrentUser();
    }
}
