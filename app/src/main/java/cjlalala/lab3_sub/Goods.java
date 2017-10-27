package cjlalala.lab3_sub;

public class Goods {
    /*各个商品的独立信息都使用一个私有变量进行存储*/
    private String name;
    private String price;
    private String letter;
    private String type;
    private String information;
    private int image;
    /*一个构造函数*/
    public Goods(String name, String letter, String price, String type, String information, int image) {
        this.name = name;
        this.letter = letter;
        this.price = price;
        this.type = type;
        this.information = information;
        this.image = image;
    }
    /*对应的get函数，获得私有变量的值*/
    public String getName() {
        return name;
    }
    public String getLetter() {
        return letter;
    }
    public String getPrice() {
        return price;
    }
    public String getType() {
        return type;
    }
    public String getInformation() { return information; }
    public int getImage() {return image; }
}
