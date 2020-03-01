package org.nico.mocker.resp;

import java.util.List;

public class ListVo<T> {

    private List<T> list;
    
    private long total;

    public ListVo(List<T> list, long total) {
        this.list = list;
        this.total = total;
    }

    public static <T> ListVo<T> list(long total){
        return new ListVo<T>(null, total);
    }
    
    public static <T> ListVo<T> list(List<T> list, long total){
        return new ListVo<T>(list, total);
    }
    
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
    
}
