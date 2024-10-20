package com.project.petService.services.user;

import com.project.petService.constant.PredefinedRole;
import com.project.petService.dtos.requests.users.UserCreationRequest;
import com.project.petService.dtos.requests.users.UserUpdateRequest;
import com.project.petService.dtos.response.users.UserInfo;
import com.project.petService.dtos.response.users.UserResponse;
import com.project.petService.entities.Role;
import com.project.petService.entities.User;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.UserMapper;
import com.project.petService.repositories.RoleRepository;
import com.project.petService.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if(!request.getPassword().equals(request.getRepassword()))
            throw new AppException(ErrorCode.REPASSWORD_NOT_BLANK);

        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findByName(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PostAuthorize("returnObject.id.toString() == authentication.principal.getClaimAsString('id') or hasRole('ADMIN')")
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

       User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('MANAGE_ACCOUNT')")
    public Page<UserResponse> getAllUsers(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        return userRepository.findAll(pageRequest).map(userMapper::toUserResponse);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('MANAGE_ACCOUNT')")
    public Page<UserResponse> searchUsers(String search, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<UserResponse> pageUser;
        if(search == null){
            pageUser = userRepository.findAll(pageRequest).map(userMapper::toUserResponse);
        }else{
            pageUser = userRepository.findByFullNameContaining(search, pageRequest).map(userMapper::toUserResponse);
        }
        return pageUser;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('MANAGE_ACCOUNT')")
    public Long getCountUsers() {
        return userRepository.count();
    }

    @Override
    @PostAuthorize("returnObject.id.toString() == authentication.principal.getClaimAsString('id') or hasRole('ADMIN')")
    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('MANAGE_ACCOUNT')")
    public UserResponse updateRole(String id, Set<Long> roleIds) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        Set<Role> listRole = roleRepository.findByIdIn(roleIds);
        Set<Long> foundIds = listRole.stream().map(Role::getId).collect(Collectors.toSet());
        List<Long> missingGenreId = roleIds.stream()
                .filter(ids -> !foundIds.contains(ids)).toList();
        if(!missingGenreId.isEmpty()){
            String errorMessage = missingGenreId.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            throw new RuntimeException("Id not exists " + errorMessage);
        }

        user.setRoles(listRole);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('MANAGE_ACCOUNT')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public User registerUser(UserInfo userGG) {
        Optional<User> user = userRepository.findByEmail(userGG.getEmail());
        if (user.isEmpty()) {
            User userData = User.builder()
                    .email(userGG.getEmail())
                    .fullName(userGG.getName())
                    .image(userGG.getPicture())
                    .build();
            return userRepository.save(userData);
        } else {
            User existingUser = user.get();
            existingUser.setFullName(userGG.getName());
            existingUser.setImage(userGG.getPicture());
            return userRepository.save(existingUser);
        }
    }
}
