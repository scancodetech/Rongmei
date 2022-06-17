package com.rongmei.parameters.boxegg;

public class BoxEgg {

    private String  coverUrl;//封面图路径

    private String  boxEggName;//盒蛋名称


    public BoxEgg() {
    }

    public BoxEgg(String coverUrl, String boxEggName) {
        this.coverUrl = coverUrl;
        this.boxEggName = boxEggName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getBoxEggName() {
        return boxEggName;
    }

    public void setBoxEggName(String boxEggName) {
        this.boxEggName = boxEggName;
    }
}
