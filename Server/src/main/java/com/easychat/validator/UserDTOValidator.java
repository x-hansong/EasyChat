package com.easychat.validator;


import com.easychat.exception.BadRequestException;
import com.easychat.model.dto.input.UserDTO;
import com.easychat.model.error.ErrorType;
import com.easychat.repository.UserRepository;
import com.easychat.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by xhans on 2016/1/28.
 */
public class UserDTOValidator implements Validator{

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);//声明校验的类
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        String nameTest = userDTO.getName();
        String passwordTest = userDTO.getPassword();
        if (!CommonUtils.checkName(nameTest)
                || !CommonUtils.checkPassword(passwordTest)) {
           throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "invalid username or password");
        }

    }
}
