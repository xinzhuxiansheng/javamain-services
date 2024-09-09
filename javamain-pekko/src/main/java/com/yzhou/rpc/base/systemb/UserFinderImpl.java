package com.yzhou.rpc.base.systemb;

import com.yzhou.rpc.base.common.UserFinder;

public class UserFinderImpl implements UserFinder {
    @Override
    public String getUserNameById(int id){
        return "userName-mock";
    }
    @Override
    public int getAgeById(int id){
        return 100+id;
    }
}
