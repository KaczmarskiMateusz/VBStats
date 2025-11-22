package pl.vbstats.user.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.vbstats.config.JwtAuthFilter;
import pl.vbstats.user.Model.ChangePasswordRequest;
import pl.vbstats.user.Model.ResetPasswordRequest;
import pl.vbstats.user.Model.UserDto;
import pl.vbstats.user.Model.UserUpdateRequest;
import pl.vbstats.user.Service.UserService;

@RestController
@RequestMapping("/app/users/me")
@RequiredArgsConstructor
public class MeUserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getMe(
            @AuthenticationPrincipal JwtAuthFilter.ExternalUserPrincipal principal) {
        UserDto userDto = userService.findUserByExternalId(principal.externalId());
        return ResponseEntity.ok(userDto);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateMe(@Valid @RequestBody UserUpdateRequest request,
                                            @AuthenticationPrincipal JwtAuthFilter.ExternalUserPrincipal principal) {
        UserDto userDto = userService.updateUser(request, principal.externalId());
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changeMyPassword(@Valid @RequestBody ChangePasswordRequest request,
                                                 @AuthenticationPrincipal JwtAuthFilter.ExternalUserPrincipal principal) {
        userService.changePassword(request, principal.externalId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetMyPassword(
            @Valid @RequestBody ResetPasswordRequest request,
            @AuthenticationPrincipal JwtAuthFilter.ExternalUserPrincipal principal) {
        userService.resetPassword(request, principal.externalId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal JwtAuthFilter.ExternalUserPrincipal principal) {
        userService.deleteUserById(principal.externalId(), principal.externalId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/enable")
    public ResponseEntity<Void> enableMe(@AuthenticationPrincipal JwtAuthFilter.ExternalUserPrincipal principal) {
        userService.enableUser(principal.externalId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/disable")
    public ResponseEntity<Void> disableMe(@AuthenticationPrincipal JwtAuthFilter.ExternalUserPrincipal principal) {
        userService.disableUser(principal.externalId());
        return ResponseEntity.noContent().build();
    }

}