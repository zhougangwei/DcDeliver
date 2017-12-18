package com.aihui.dcdeliver.bean;

import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/7/18 9:47
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/7/18$
 * @ 更新描述  ${TODO}
 */

public class LoadingBean {
   public List    list;
   public Boolean isLoading;

    public LoadingBean(List list, Boolean isLoading) {
        this.list = list;
        this.isLoading = isLoading;
    }
}
