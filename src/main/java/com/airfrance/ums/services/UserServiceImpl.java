/**
 *
 */
package com.airfrance.ums.services;

import com.airfrance.ums.controllers.UserResponseDto;
import com.airfrance.ums.dto.UserDto;
import com.airfrance.ums.dto.UserResultCode;
import com.airfrance.ums.entities.AppConfig;
import com.airfrance.ums.entities.User;
import com.airfrance.ums.exception.BadRequestException;
import com.airfrance.ums.repository.MongoAppConfigRepository;
import com.airfrance.ums.repository.UserRepository;
import com.airfrance.ums.utility.Constants;
import com.airfrance.ums.utility.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author io
 *
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private MongoAppConfigRepository mongoAppConfigRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MongoAppConfigRepository mongoAppConfigRepository) {
        this.userRepository = userRepository;
        this.mongoAppConfigRepository = mongoAppConfigRepository;
    }

    @Override
    public UserResponseDto checkFields(UserDto userDto, boolean updateOperation) throws BadRequestException {
        UserResponseDto userResponseDto = new UserResponseDto();

        if (Objects.isNull(userDto)){

            userResponseDto.setCode(UserResultCode.INVALID_REQUEST_FORMAT.getStatusCode());
            userResponseDto.setContent(UserResultCode.INVALID_REQUEST_FORMAT.getStatusDescription());
            logger.error("{} {}: no information about user",Constants.LOG_MESSAGE_CANNOT ,updateOperation ? "update":"create");
            return userResponseDto;
        }
        Optional<User> byId = Optional.ofNullable(null);
        UserDto userDtoToUpdate= null;
        if (updateOperation && userRepository.existsById(userDto.getUserId())){
            byId = userRepository.findById(userDto.getUserId());
            userDtoToUpdate = UserDto.builder().userId(userDto.getUserId())
                    .birthday(Objects.isNull(userDto.getBirthday()) ? byId.get().getBirthday():userDto.getBirthday())
                    .name(Objects.isNull(userDto.getName()) ? byId.get().getName():userDto.getName())
                    .surname(Objects.isNull(userDto.getSurname()) ? byId.get().getSurname():userDto.getSurname())
                    .country(Objects.isNull(userDto.getCountry()) ? byId.get().getCountry():userDto.getCountry())
                    .email(Objects.isNull(userDto.getEmail()) ? byId.get().getEmail():userDto.getEmail())
                    .build();
            userDto = userDtoToUpdate;
        }
        if (updateOperation && !userRepository.existsById(userDto.getUserId())) {
            userResponseDto.setCode(UserResultCode.REQUEST_NOT_FOUND.getStatusCode());
            userResponseDto.setContent(UserResultCode.REQUEST_NOT_FOUND.getStatusDescription());
            logger.warn("{} update User does not exist!",Constants.LOG_MESSAGE_CANNOT );
            return userResponseDto;
        }

        if (!Utils.isOver18User(userDto.getBirthday())) {
            userResponseDto.setCode(UserResultCode.USER_NOT_AUTHORIZED.getStatusCode());
            userResponseDto.setContent(UserResultCode.USER_NOT_AUTHORIZED.getStatusDescription());
            logger.warn("{} create User under 18 years",Constants.LOG_MESSAGE_CANNOT );
            return userResponseDto;
        }
        AppConfig appConfigByConfigName = mongoAppConfigRepository.findAppConfigByConfigName(Constants.ALLOWED_COUNTRY);
        if (Objects.isNull(userDto.getCountry()) || !Utils.isAllowedCountry(appConfigByConfigName,userDto.getCountry())) {
            userResponseDto.setCode(UserResultCode.USER_NOT_AUTHORIZED.getStatusCode());
            userResponseDto.setContent(UserResultCode.USER_NOT_AUTHORIZED.getStatusDescription());
            logger.warn("{} create User out from France ",Constants.LOG_MESSAGE_CANNOT );
            return userResponseDto;
        }
        if (Objects.isNull(userDto.getName()) || Objects.isNull(userDto.getSurname())) {
            userResponseDto.setCode(UserResultCode.USER_NOT_AUTHORIZED.getStatusCode());
            userResponseDto.setContent(UserResultCode.USER_NOT_AUTHORIZED.getStatusDescription());
            logger.warn("{} create User with empty name or surname ",Constants.LOG_MESSAGE_CANNOT );
            return userResponseDto;
        }
        boolean checkMailNull = Objects.isNull(userDto.getEmail());
        if(checkMailNull){
            userResponseDto.setCode(UserResultCode.INVALID_REQUEST_FORMAT.getStatusCode());
            userResponseDto.setContent(UserResultCode.INVALID_REQUEST_FORMAT.getStatusDescription());
            logger.warn("{} create/update User with empty email ",Constants.LOG_MESSAGE_CANNOT );
            return userResponseDto;

        }

        boolean checkMailExist = Objects.nonNull(userRepository.findUserByEmail(userDto.getEmail()));
        if(checkMailExist && !updateOperation ){
            userResponseDto.setCode(UserResultCode.EMAIL_ALREADY_EXIST_ERROR.getStatusCode());
            userResponseDto.setContent(UserResultCode.EMAIL_ALREADY_EXIST_ERROR.getStatusDescription());
            logger.warn("{} create User with email: {} already exist",Constants.LOG_MESSAGE_CANNOT,userDto.getEmail() );
            return userResponseDto;
        }

        if( updateOperation && checkMailExist && !userDto.getEmail().equalsIgnoreCase(byId.get().getEmail()) ){
            userResponseDto.setCode(UserResultCode.EMAIL_ALREADY_EXIST_ERROR.getStatusCode());
            userResponseDto.setContent(UserResultCode.EMAIL_ALREADY_EXIST_ERROR.getStatusDescription());
            logger.warn("{} update User with  email: {} already exist",Constants.LOG_MESSAGE_CANNOT,userDto.getEmail() );
            return userResponseDto;
        }

        userResponseDto.setCode(UserResultCode.SUCCESS.getStatusCode());
        userResponseDto.setContent(UserResultCode.SUCCESS.getStatusDescription());
        return userResponseDto;
    }

    @Override
    public UserResponseDto createUser(UserDto userDto, boolean isUpdateOperation) throws BadRequestException {
        UserResponseDto userResponseDto = new UserResponseDto();
        try {
             userResponseDto = checkFields(userDto,isUpdateOperation);
            if(UserResultCode.SUCCESS.getStatusCode().equals(userResponseDto.getCode())){
                User user =   isUpdateOperation ? userRepository.save(dtoToUser(userDto,true)) :
                        userRepository.insert(dtoToUser(userDto,false));

            }
        } catch (BadRequestException e) {
            userResponseDto.setCode(UserResultCode.USER_ERROR.getStatusCode());
            userResponseDto.setContent(UserResultCode.USER_ERROR.getStatusDescription());
            throw new BadRequestException(e);
        }

        return  userResponseDto ;
    }

    @Override
    public User dtoToUser(UserDto userDto, boolean isUpdateOperation) {
        logger.info("Converting {} to User", userDto);
        User user= null;
        if(Objects.nonNull(userDto)){
            user = new User(userDto.getName(), userDto.getSurname(), userDto.getEmail(), userDto.getBirthday(), userDto.getCountry());
            user.setUserId(isUpdateOperation ? userDto.getUserId():null);
        }


        return user;
    }

    @Override
    public Optional<User> getUserById(String userId) {

        return userRepository.findById(userId);
    }

    @Override
    public List<User> getAllUser() {

        return userRepository.findAll();
    }

}
