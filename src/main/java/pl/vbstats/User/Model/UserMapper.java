package pl.vbstats.User.Model;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) return null;

        return UserDto.builder()
                .externalId(user.getExternalId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .role(user.getRole())
                .active(user.isActive())
                .confirmed(user.isConfirmed())
                .email(user.getEmail())
                .userName(user.getUsername())
                .build();
    }

    public User toEntity(UserRegisterRequest request) {
        if (request == null) return null;

        return User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .phone(request.getPhone())
                .birthDate(request.getBirthDate())
                .address(request.getAddress())
                .active(false)
                .confirmed(false)
                .role(UserRole.CLUB_OWNER)
                .build();
    }
}