package com.guzi.upr.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 谷子毅
 * @date 2022/6/9
 */
@Mapper(componentModel = "spring")
public interface UserConvert {

    UserConvert MAPPER = Mappers.getMapper(UserConvert.class);
}
