package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.imsit.diplom.docmen.dto.UserInfoDto;
import ru.imsit.diplom.docmen.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserInfoMapper {
    User toEntity(UserInfoDto userInfoDto);

    UserInfoDto toUserInfoDto(User user);

    User updateWithNull(UserInfoDto userInfoDto, @MappingTarget User user);
}