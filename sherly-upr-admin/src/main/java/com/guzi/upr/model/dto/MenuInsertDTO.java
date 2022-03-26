package com.guzi.upr.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Data
public class MenuInsertDTO {
    /**
     * 菜单id
     */
    @ApiModelProperty("菜单id")
    @TableId(type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单名
     */
    @ApiModelProperty("菜单名")
    private String menuName;

    /**
     * 父菜单id
     */
    @ApiModelProperty("父菜单id")
    private Long parentId;

    /**
     * 菜单路径
     */
    @ApiModelProperty("菜单路径")
    private String link;

    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    public static void main(String[] args) throws JsonProcessingException {

        MenuInsertDTO menuInsertDTO = new MenuInsertDTO();
        ArrayList<MenuParentDTO> menuParentDtos = new ArrayList<>();
        MenuParentDTO menuParentDto = new MenuParentDTO();
        menuParentDto.setMenuName("Parent1");
        menuParentDto.setSort(1);
        ArrayList<MenuParentDTO> menuParentDtos1 = new ArrayList<>();
        MenuParentDTO childMenuParentD = new MenuParentDTO();
        childMenuParentD.setMenuName("Child1");
        childMenuParentD.setSort(1);
        ArrayList<MenuParentDTO> menuParentDtos2 = new ArrayList<>();
        MenuParentDTO childMenuParent2 = new MenuParentDTO();
        childMenuParent2.setMenuName("Child11");
        childMenuParent2.setSort(1);
        menuParentDtos2.add(childMenuParent2);

        menuParentDtos.add(menuParentDto);
//        menuInsertDTO.setMenuList(menuParentDtos);

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(menuInsertDTO));
    }
}
