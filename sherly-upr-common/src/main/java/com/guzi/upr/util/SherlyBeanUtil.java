package com.guzi.upr.util;

import com.guzi.upr.model.TreeAble;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.upr.model.contants.CommonConstants.ROOT_PARENT_ID;

/**
 * @author 谷子毅
 * @date 2022/4/17
 */
@Deprecated
public class SherlyBeanUtil {

    public static <T extends TreeAble> List<T> convert(List<T> all) {
        return all.stream().filter(e -> Objects.equals(e.getParentId(), ROOT_PARENT_ID))
                .peek(e -> e.setChildren(getChildren(e, all)))
                .sorted(Comparator.comparing(T::getSort))
                .collect(Collectors.toList());
    }

    private static <T extends TreeAble> List<TreeAble> getChildren(T parent, List<T> all) {
        return all.stream()
                .filter(e -> Objects.equals(e.getParentId(), parent.getId()))
                .peek(e -> e.setChildren(getChildren(e, all)))
                .sorted(Comparator.comparing(T::getSort))
                .collect(Collectors.toList());
    }

}
