package cn.cr.service.impl;

import ch.qos.logback.core.db.dialect.DBUtil;
import cn.cr.dao.UserDOMapper;
import cn.cr.dao.UserPasswordDOMapper;
import cn.cr.dataobject.UserDO;
import cn.cr.dataobject.UserPasswordDO;
import cn.cr.error.BusinessException;
import cn.cr.error.EnumBusinessError;
import cn.cr.service.UserService;
import cn.cr.service.model.UserModel;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        //调用userDOMapper获取到对应的用户dataobject
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO == null) return null;
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO, userPasswordDO);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
       if(userModel == null){
           throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
       }
       if(StringUtils.isEmpty(userModel.getTelephone())
               || StringUtils.isEmpty(userModel.getName())
               || userModel.getSex() == null
               || userModel.getAge() == null){
           throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
       }
       //实现model转为dataobject方法
       UserDO userDO = convertFromModel(userModel);
       try {
           userDOMapper.insertSelective(userDO);
       }catch (DuplicateKeyException e){
           throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "手机重复注册");
       }
       userModel.setId(userDO.getId());
       UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
       userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    @Override
    public UserModel validateLogin(String telephone, String encryptPassword) throws BusinessException {
        //通过用户手机号获取手机信息
        UserDO userDO = userDOMapper.selectByTelephone(telephone);
        if(userDO == null){
            throw new BusinessException(EnumBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO, userPasswordDO);
        //比对用户信息内加密的密码是否和传输进来的密码相匹配
        if(!org.apache.commons.lang3.StringUtils.equals(encryptPassword, userModel.getEncryptPassword())){
            throw new BusinessException(EnumBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        if(userModel == null) return null;
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncryptPassword(userModel.getEncryptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    private UserDO convertFromModel(UserModel userModel){
        if(userModel == null) return null;
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO == null) return null;
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if(userPasswordDO != null){
            userModel.setEncryptPassword(userPasswordDO.getEncryptPassword());
        }
        return userModel;
    }
}
