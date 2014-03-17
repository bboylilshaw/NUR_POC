package watson.admin.dao;

import watson.admin.model.HPUser;

public interface HPUserDao {
    public HPUser getHPUserByDomainUserName (String domainUserName, String instance);
}
