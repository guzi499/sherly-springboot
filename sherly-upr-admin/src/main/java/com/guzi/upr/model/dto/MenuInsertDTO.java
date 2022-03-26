package com.guzi.upr.model.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Data
public class MenuInsertDTO {
    private List<MenuParentDto> menuList;

    public static void main(String[] args) throws JsonProcessingException {

        MenuInsertDTO menuInsertDTO = new MenuInsertDTO();
        ArrayList<MenuParentDto> menuParentDtos = new ArrayList<>();
        MenuParentDto menuParentDto = new MenuParentDto();
        menuParentDto.setMenuName("Parent1");
        menuParentDto.setSort(1);
        ArrayList<MenuParentDto> menuParentDtos1 = new ArrayList<>();
        MenuParentDto childMenuParentD = new MenuParentDto();
        childMenuParentD.setMenuName("Child1");
        childMenuParentD.setSort(1);
        ArrayList<MenuParentDto> menuParentDtos2 = new ArrayList<>();
        MenuParentDto childMenuParent2 = new MenuParentDto();
        childMenuParent2.setMenuName("Child11");
        childMenuParent2.setSort(1);
        menuParentDtos2.add(childMenuParent2);
        childMenuParentD.setChildren(menuParentDtos2);
        menuParentDtos1.add(childMenuParentD);
        menuParentDto.setChildren(menuParentDtos1);
        menuParentDtos.add(menuParentDto);
        menuInsertDTO.setMenuList(menuParentDtos);

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(menuInsertDTO));
    }
}
