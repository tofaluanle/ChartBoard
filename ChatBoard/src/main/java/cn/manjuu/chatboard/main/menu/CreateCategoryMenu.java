package cn.manjuu.chatboard.main.menu;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import cn.manjuu.chatboard.R;

/**
 * @auther 宋疆疆
 * @date 2016/2/2.
 */
public class CreateCategoryMenu extends ActionProvider implements MenuItem.OnMenuItemClickListener {

    public CreateCategoryMenu(Context context) {
        super(context);
    }

    /**
     * @return 需要返回null, 才能弹出子菜单.
     */
    @Override
    public View onCreateActionView() {
        return null;
    }

    /**
     * 创建子菜单
     *
     * @param subMenu
     */
    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add(0, 0, 0, R.string.create_ctg).setIcon(R.drawable.ic_launcher2).setOnMenuItemClickListener(this);
        subMenu.add(0, 1, 1, R.string.post).setIcon(R.drawable.ic_launcher2).setOnMenuItemClickListener(this);
        subMenu.add(0, 2, 1, R.string.search).setIcon(R.drawable.ic_launcher2).setOnMenuItemClickListener(this);
        subMenu.add(1, 3, 1, R.string.feedback).setIcon(R.drawable.ic_launcher2).setOnMenuItemClickListener(this);
    }

    /**
     * @return 返回true代表有子菜单
     */
    @Override
    public boolean hasSubMenu() {
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
