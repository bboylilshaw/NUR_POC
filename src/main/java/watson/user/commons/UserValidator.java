package watson.user.commons;

import watson.user.dao.UserDaoImpl;

import javax.annotation.Resource;

public class UserValidator {

    private static UserDaoImpl hpUserDao;

    @Resource
    public void setHpUserDao(UserDaoImpl hpUserDao) {
        this.hpUserDao = hpUserDao;
    }

    public static boolean checkUserExists(String domainUserName, String instance){
        if (hpUserDao.getHPUserByDomainUserName(domainUserName, instance) == null){
            return false;
        }
        return true;
    }

}
