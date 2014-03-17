package watson.user.dao;

import watson.user.model.HPUser;

public interface UserDao {
    public HPUser getHPUserByDomainUserName (String domainUserName, String instance);
}
