package net.jkcats.simplemvvm.models;

import net.jkcats.simplemvvm.network.api.ResponseData;

import java.util.List;

public class HomeModel extends ResponseData {

    public List<HomeData> data;

    public static class HomeData {
        public String desc;
        public int id;
        public String imagePath;
        public int isVisible;
        public int order;
        public String title;
        public int type;
        public String url;
    }
}