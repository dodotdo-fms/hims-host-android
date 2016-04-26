package com.dodotdo.himsadmin.model;

/**
 * Created by Omjoon on 16. 4. 21..
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ViewModels {
    List<ViewModel> models;

    public ViewModels() {
        models = new ArrayList<ViewModel>();
    }

    public <T> void add(T data, int viewType) {
        ViewModel<T> model = new ViewModel(data, viewType);
        models.add(model);
    }


    public <T> void addAll(Collection<T> dataSet, int viewType) {
        for (T data : dataSet) {
            ViewModel<T> model = new ViewModel(data, viewType);
            models.add(model);
        }
    }



    public <T> ArrayList getList()
    {  ArrayList<T> list = new ArrayList<T>();
        for(ViewModel d : models){
            try{
                list.add((T)d.getData());
            }catch (Exception e){}
        }

        return list;
    }

    public int size() {
        return models.size();
    }

    public ViewModel get(int position) {
        return models.get(position);
    }

    public <T> void add(int position, T data, int viewType) {
        ViewModel<T> model = new ViewModel(data, viewType);
        models.add(position, model);
    }

    public ViewModel getLast() {

        if (models.size() == 0) {
            return null;
        } else {
            return models.get(models.size() - 1);
        }
    }

    public void clear(){
        models.clear();
    }

    /**
     * Created by Minchul on 2015-02-26.
     */
    public static class ViewModel<T> {
        final T data;
        final int viewType;

        public ViewModel(T data, int viewType) {
            this.data = data;
            this.viewType = viewType;
        }

        public int getViewType() {
            return viewType;
        }

        public T getData() {
            return data;
        }
    }
}
