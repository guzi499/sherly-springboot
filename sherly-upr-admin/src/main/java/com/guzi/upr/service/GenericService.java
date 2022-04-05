package com.guzi.upr.service;

import com.guzi.upr.manager.MenuManager;
import com.guzi.upr.manager.RoleManager;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.model.admin.Menu;
import com.guzi.upr.model.admin.Role;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.vo.BasicInfoVO;
import com.guzi.upr.model.vo.BasicMenuInfoVO;
import com.guzi.upr.model.vo.BasicRoleInfoVO;
import com.guzi.upr.model.vo.BasicUserInfoVO;
import com.guzi.upr.util.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/4/5
 */
@Service
public class GenericService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private MenuManager menuManager;

    /**
     *
     * @return
     */
    public BasicInfoVO getBasicData() {
        Long userId = ThreadLocalUtil.get().getUserId();

        User user = userManager.getById(userId);
        BasicUserInfoVO userVO = new BasicUserInfoVO();
        BeanUtils.copyProperties(user, userVO);

        List<Role> roles = roleManager.listByUserId(userId);
        List<BasicRoleInfoVO> roleVOList = roles.stream().map(e -> {
            BasicRoleInfoVO basicRoleInfoVO = new BasicRoleInfoVO();
            BeanUtils.copyProperties(e, basicRoleInfoVO);
            return basicRoleInfoVO;
        }).collect(Collectors.toList());

        List<Long> roleIds = roles.stream().map(Role::getRoleId).collect(Collectors.toList());
        List<Menu> menus = menuManager.listByRoleIds(roleIds);
        List<BasicMenuInfoVO> menuVOList = menus.stream().filter(e -> e.getParentId() == 0).map(e -> {
            BasicMenuInfoVO basicMenuInfoVO = new BasicMenuInfoVO();
            BeanUtils.copyProperties(e, basicMenuInfoVO);
            basicMenuInfoVO.setChildren(getChildren(e, menus));
            return basicMenuInfoVO;
        }).collect(Collectors.toList());

        BasicInfoVO basicInfoVO = new BasicInfoVO();
        basicInfoVO.setBasicUserInfoVO(userVO);
        basicInfoVO.setBasicRoleInfoVO(roleVOList);
        basicInfoVO.setBasicMenuInfoVO(menuVOList);

        return basicInfoVO;
    }

    private List<BasicMenuInfoVO> getChildren(Menu parent, List<Menu> all) {
        return all.stream()
                .filter(e -> e.getParentId().equals(parent.getMenuId()))
                .map(e -> {
                    BasicMenuInfoVO basicMenuInfoVO = new BasicMenuInfoVO();
                    BeanUtils.copyProperties(e, basicMenuInfoVO);
                    basicMenuInfoVO.setChildren(getChildren(e, all));
                    return basicMenuInfoVO;
                }).collect(Collectors.toList());
    }
}
