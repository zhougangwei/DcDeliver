package com.aihui.dcdeliver.commponent.jdaddressselector;

import java.util.List;

/**
 * Created by dun on 17/2/9.
 */

public interface ISelectAbleList<T extends ISelectAble>  {
    public List<T> getList1();

    public List<T> getList0();

    public List<T> getList2();

    public List<T> getList(Integer integer);


}
