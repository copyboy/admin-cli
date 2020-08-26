package com.ronhan.admin.modules.sys.util;

import com.ronhan.admin.modules.sys.domain.SysDept;
import com.ronhan.admin.modules.sys.domain.SysMenu;
import com.ronhan.admin.modules.sys.vo.DeptTreeVo;
import com.ronhan.admin.modules.sys.vo.MenuMetaVo;
import com.ronhan.admin.modules.sys.vo.MenuVo;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Admin 系统用户工具类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 15:01
 */
@UtilityClass
public class AdminUtil {


    public List<MenuVo> buildMenus(List<SysMenu> sysMenuList) {
        List<MenuVo> list = new LinkedList<>();

        sysMenuList.forEach(sysMenu -> {
                    if (sysMenu != null) {
                        List<SysMenu> menuDTOList = sysMenu.getChildren();
                        MenuVo menuVo = new MenuVo();
                        menuVo.setName(sysMenu.getName());
                        menuVo.setPath(sysMenu.getPath());
                        // 如果不是外链
                        if (sysMenu.getIsFrame()) {
                            if (sysMenu.getParentId().equals(0)) {
                                //一级目录需要加斜杠，不然访问 会跳转404页面
                                menuVo.setPath("/" + sysMenu.getPath());
                                menuVo.setComponent(StringUtils.isEmpty(sysMenu.getComponent()) ? "Layout" : sysMenu.getComponent());
                            } else if (!StringUtils.isEmpty(sysMenu.getComponent())) {
                                menuVo.setComponent(sysMenu.getComponent());
                            }
                        }
                        menuVo.setMeta(new MenuMetaVo(sysMenu.getName(), sysMenu.getIcon()));
                        if (menuDTOList != null && menuDTOList.size() != 0 && sysMenu.getType() == 0) {
                            menuVo.setChildren(buildMenus(menuDTOList));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if (sysMenu.getParentId().equals(0)) {
                            menuVo.setAlwaysShow(false);
                            menuVo.setRedirect("noredirect");
                            MenuVo menuVo1 = new MenuVo();
                            menuVo1.setMeta(menuVo.getMeta());
                            // 非外链
                            if (sysMenu.getIsFrame()) {
                                menuVo1.setPath("index");
                                menuVo1.setName(menuVo.getName());
                                menuVo1.setComponent(menuVo.getComponent());
                            } else {
                                menuVo1.setPath(sysMenu.getPath());
                            }
                            menuVo.setName(null);
                            menuVo.setMeta(null);
                            menuVo.setComponent("Layout");
                            List<MenuVo> list1 = new ArrayList<>();
                            list1.add(menuVo1);
                            menuVo.setChildren(list1);
                        }
                        list.add(menuVo);
                    }
                }
        );
        return list;
    }

    /**
     * 遍历菜单
     */
    public void findChildren(List<SysMenu> menuList, List<SysMenu> menus, int menuType) {
        for (SysMenu sysMenu : menuList) {
            List<SysMenu> children = new ArrayList<>();
            for (SysMenu menu : menus) {
                if (menuType == 1 && menu.getType() == 2) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue;
                }
                if (sysMenu.getMenuId() != null && sysMenu.getMenuId().equals(menu.getParentId())) {
                    menu.setParentName(sysMenu.getName());
                    menu.setLevel(sysMenu.getLevel() + 1);
                    if (exists(children, menu)) {
                        children.add(menu);
                    }
                }
            }
            sysMenu.setChildren(children);
            children.sort(Comparator.comparing(SysMenu::getSort));
            findChildren(children, menus, menuType);
        }
    }


    /**
     * 生成BCryptPasswordEncoder密码
     */
    public String encode(String rawPass) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPass);
    }

    /**
     * 构建部门tree
     */
    public void findChildren(List<SysDept> sysDeptList, List<SysDept> deptList) {

        for (SysDept sysDept : sysDeptList) {
            List<SysDept> children = new ArrayList<>();
            for (SysDept dept : deptList) {
                if (sysDept.getDeptId() != null
                        && sysDept.getDeptId().equals(dept.getParentId())) {
                    dept.setParentName(sysDept.getName());
                    dept.setLevel(sysDept.getLevel() + 1);
                    children.add(dept);
                }
            }
            sysDept.setChildren(children);
            findChildren(children, deptList);
        }
    }

    /**
     * 构建部门tree
     */
    public void findChildren1(List<DeptTreeVo> treeVos, List<SysDept> deptList) {

        for (DeptTreeVo sysDept : treeVos) {
            sysDept.setId(sysDept.getId());
            sysDept.setLabel(sysDept.getLabel());
            List<DeptTreeVo> children = new ArrayList<>();
            for (SysDept dept : deptList) {
                if (sysDept.getId() == dept.getParentId()) {
                    DeptTreeVo deptTreeVo1 = new DeptTreeVo();
                    deptTreeVo1.setLabel(dept.getName());
                    deptTreeVo1.setId(dept.getDeptId());
                    children.add(deptTreeVo1);
                }
            }
            sysDept.setChildren(children);
            findChildren1(children, deptList);
        }
    }

    /**
     * 判断菜单是否存在
     */
    public boolean exists(List<SysMenu> sysMenus, SysMenu sysMenu) {
        boolean exist = false;
        for (SysMenu menu : sysMenus) {
            if (menu.getMenuId().equals(sysMenu.getMenuId())) {
                exist = true;
                break;
            }
        }
        return !exist;
    }

    /**
     * 校验密码
     */
    public boolean validatePass(String oldPass, String passwordEncoderOldPass) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(oldPass, passwordEncoderOldPass);
    }
}
