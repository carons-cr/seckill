package cn.cr.service;

import cn.cr.error.BusinessException;
import cn.cr.service.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
}
