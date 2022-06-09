package com.guzi.upr.convert;

import com.guzi.upr.model.admin.Department;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author 谷子毅
 * @date 2022/6/9
 */
@Mapper(componentModel = "spring")
public interface UserConvert {

    UserConvert MAPPER = Mappers.getMapper(UserConvert.class);

    @Mappings({
            @Mapping(target = "departmentName", source = "department.departmentName"),
            @Mapping(target = "departmentId", source = "department.departmentId")
    })
    UserVo UserToUserVO(User user, Department department);
}
