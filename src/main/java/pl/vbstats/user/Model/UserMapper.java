package pl.vbstats.user.Model;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(UserEntity userEntity) {
        if (userEntity == null) return null;

        return UserDto.builder()
                .externalId(userEntity.getExternalId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .phone(userEntity.getPhone())
                .role(userEntity.getRole())
                .active(userEntity.isActive())
                .confirmed(userEntity.isConfirmed())
                .email(userEntity.getEmail())
                .userName(userEntity.getUsername())
                .build();
    }

    public UserEntity toCreateEntity(UserRegisterRequest request) {
        if (request == null) return null;

        return UserEntity.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .phone(request.getPhone())
                .birthDate(request.getBirthDate())
                .address(request.getAddress())
                .active(false)
                .confirmed(false)
                .build();
    }

    public UserEntity toUpdateEntity(UserUpdateRequest request) {
        if (request == null) return null;

        return UserEntity.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .phone(request.getPhone())
                .birthDate(request.getBirthDate())
                .address(request.getAddress())
                .active(false)
                .confirmed(false)
                .build();
    }

}