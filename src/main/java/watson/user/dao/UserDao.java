package watson.user.dao;

import watson.user.model.HPEmployee;
import watson.user.model.HPUser;

public interface UserDao {

    public HPUser getHPUserByDomainUserName (String domainUserName, String instance);

    public String submitRequest(HPEmployee hpEmployee, String instance, String comments);

    public boolean userExists(String domainUserName, String instance);

}
